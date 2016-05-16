package quanta.iwant.fragment;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.quanta.iwant.R;

import quanta.iwant.adapter.CommentsAdapter;
import quanta.iwant.entity.CommentsEntity;
import quanta.iwant.entity.ContentEntity;
import quanta.iwant.entity.Data;
import quanta.iwant.entity.UserEntity;
import quanta.iwant.network.Connect;


import quanta.iwant.network.JsonUtil;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContentFragment extends Fragment{
	EditText commentEditText;
	Connect connect;
	Handler handler;
	LayoutInflater layoutInflater;
	Context context;
	ArrayList<CommentsEntity> commentsEntities;
	CommentsAdapter commentsAdapter;
	String contentId;
	ContentEntity contentEntity;
	public void init(Context context){
		this.context = context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.layoutInflater = inflater;
		return inflater.inflate(R.layout.content_fragment, container,false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Activity activity = getActivity();	
		WebView webView = (WebView) activity.findViewById(R.id.webView);
		webView.loadUrl(contentEntity.getUrl());
		ListView comments = (ListView) activity.findViewById(R.id.comments);
		commentEditText = (EditText) activity.findViewById(R.id.comment_edit);
		//评论实体尚未初始化
		commentsAdapter = new CommentsAdapter(layoutInflater,commentsEntities);
		comments.setAdapter(commentsAdapter);
		Handler mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Data.SUCCESS:
					connect.close();
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(msg.getData().getString("jsonobject").toString());
						commentsEntities = JsonUtil.jsonObject2CommentsEntities(jsonObject);
						onRefresh(commentsEntities);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case Data.FAILURE:
					Toast.makeText(context, "网络连接失败", Toast.LENGTH_LONG).show();
					connect.close();
					break;
				case Data.TIMEOUT:
					Toast.makeText(context, "网络连接超时", Toast.LENGTH_LONG).show();
					connect.close();
					break;
				}
			}
		};

		connect = new Connect(Data.getMsg,contentId);
		connect.sendRequest(mhandler);
	}
	public void onRefresh(ArrayList<CommentsEntity> commentsEntities) {
		// TODO Auto-generated method stub
		this.commentsEntities.addAll(0,commentsEntities);
		commentsAdapter.refreshListView(commentsEntities);
	}
	public void setcontentId(String contentId){
		this.contentId=contentId;
	}
	public void setcontentEntity(ContentEntity contentEntity){
		this.contentEntity = contentEntity;
	}
	public void sendComment(View v){
		Handler mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Data.SUCCESS:
					connect.close();
					Toast.makeText(context, "评论成功", Toast.LENGTH_LONG).show();
					
					break;
				case Data.FAILURE:
					Toast.makeText(context, "网络连接失败", Toast.LENGTH_LONG).show();
					connect.close();
					break;
				case Data.TIMEOUT:
					Toast.makeText(context, "网络连接超时", Toast.LENGTH_LONG).show();
					connect.close();
					break;
				}
			}
		};

		connect = new Connect(Data.comment,UserEntity.userid,commentEditText.getText().toString(),contentId);
		connect.sendRequest(mhandler);
	}
}
