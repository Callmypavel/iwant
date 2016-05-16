package quanta.iwant.fragment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.quanta.iwant.R;

import quanta.iwant.activity.ContentActivity;
import quanta.iwant.activity.MainActivity;
import quanta.iwant.adapter.ContentAdapter;
import quanta.iwant.entity.ContentEntity;
import quanta.iwant.entity.Data;
import quanta.iwant.entity.DataBaseOperator;
import quanta.iwant.entity.UserEntity;
import quanta.iwant.network.Connect;
import quanta.iwant.network.DownloadRunnable;
import quanta.iwant.network.JsonUtil;
import quanta.iwant.view.RefreshListView;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RecommandFragment extends BaseFragment {
	DownloadRunnable runnable;
	ArrayList<Bitmap> bitmaps;
	Bitmap bitmap;
	ContentEntity contentEntity;
	ContentAdapter contentAdapter;
	private LayoutInflater layoutInflater;
	private ArrayList<ContentEntity> contentEntities;
	MainActivity context;
	String classification;
	Connect connect;
	Handler mhandler;
	JSONObject jsonObject;
	DataBaseOperator dataBaseOperator;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.layoutInflater = inflater;
		return inflater.inflate(R.layout.recommand, container, false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Activity activity = getActivity();
		super.onActivityCreated(savedInstanceState);
		RefreshListView lv = (RefreshListView) activity.findViewById(R.id.recommand_list);
		lv.setContext(context);
		lv.setFragment(this);
		
		contentEntities = new ArrayList<ContentEntity>();
		//从数据库中获取内容
		System.out.println("从数据库中获取内容");
		dataBaseOperator = new DataBaseOperator(activity, "iwant.db");
		String[] strings = new String[]{"title","pictureUrl","contentId","url"};
		contentEntities = dataBaseOperator.getContentEntities(strings, "contentId>?",  new String[]{"0"}, null, null, null);
		//从网络获取内容
		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Data.ContentSUCCESS:
					connect.close();
					System.out.println("接到！");
					try {
						Bundle bundle = msg.getData();	
						String data = bundle.getString("jsonobject");
						System.out.println("data"+data);
						
						jsonObject = new JSONObject(data);
					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//保存从网络上获取的内容
					dataBaseOperator.saveContentEntities(JsonUtil.jsonObject2contentEntities(jsonObject));
					System.out.println("保存完毕");
					contentEntities.addAll(JsonUtil.jsonObject2contentEntities(jsonObject));
					System.out.println("增加完毕");
					refresh(contentEntities);
					
					//获取图片
					for(final ContentEntity contentEntity : contentEntities){
						Handler handler = new Handler(){
							public void handleMessage(Message msg) {
								switch(msg.what){
								case 0x123:
									Log.v("接收成功", msg.getData().getString("Bitmap"));
									Bundle bundle = msg.getData();
									Bitmap bm = JsonUtil.String2bitmap(bundle.getString("Bitmap"));
									contentEntity.setBitmap(bm);
									Log.v("设置成功", contentEntity.getBitmap()+"");
									runnable.close();
								}
							};
						};
						runnable = new DownloadRunnable(contentEntity.getpictureUrl(), handler);
						Thread thread = new Thread(runnable);
						thread.start();
					}
					
					refresh(contentEntities);
					
					System.out.println("刷新完毕");
					Toast.makeText(activity, "有新的内容", Toast.LENGTH_LONG).show();
					break;
				case Data.FAILURE:
					//关闭连接
					connect.close();
					Toast.makeText(activity, "失败", Toast.LENGTH_LONG).show();
					break;
				case Data.TIMEOUT:
					//关闭连接
					connect.close();
					Toast.makeText(activity, "超时", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};
		//连接类型设置为获取内容
		System.out.println("连接类型设置为获取内容");
		System.out.println(Data.getCon);
		System.out.println(Data.classification());
		connect = new Connect(Data.getCon, Data.classification());
		connect.sendRequest(mhandler);	
		//若内容不为空
		if(contentEntities != null){
		//开始设置并初始化Listview
		contentAdapter = new ContentAdapter(layoutInflater, contentEntities);
		lv.setItemsCanFocus(true);
		lv.setAdapter(contentAdapter);
		lv.isRecommad(true);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Data.contentId = contentEntities.get(position-1).getcontentId();
				Data.url = contentEntities.get(position-1).getUrl();
				Intent intent = new Intent();		
				intent.setClass(context, ContentActivity.class);
				startActivity(intent);
			}
		});
		}
	}
	@Override
	public void init(MainActivity context) {
		// TODO Auto-generated method stub
		this.context = context;
	}
	@Override
	public void refresh(ArrayList<ContentEntity> contentEntities) {
		// TODO Auto-generated method stub
		for(ContentEntity contentEntity : contentEntities){
			Log.v("刷新的筒子", contentEntity.getBitmap()+"");
		}
		this.contentEntities.addAll(0,contentEntities);
		contentAdapter.refreshListView(contentEntities);
	}
	@Override
	public void refreshByclassification() {
		// TODO Auto-generated method stub
		if(Data.classification2.equals("general")){
			contentAdapter.refreshListView(contentEntities);
		}
		if(!Data.classification2.equals("general")){
			ArrayList<ContentEntity> contentEntities1 = new ArrayList<ContentEntity>(); 
			for(ContentEntity contentEntity : contentEntities){
				if(contentEntity.getcontentId().indexOf("recommand"+Data.classification2)>0){
					contentEntities1.add(contentEntity);
				}
			}
			contentEntities = contentEntities1;
		}
		contentAdapter.refreshListView(contentEntities);
	}


}
