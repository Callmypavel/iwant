package quanta.iwant.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.quanta.iwant.R;

import quanta.iwant.adapter.CollectAdapter;
import quanta.iwant.entity.ContentEntity;
import quanta.iwant.entity.Data;
import quanta.iwant.entity.UserEntity;
import quanta.iwant.network.Connect;
import quanta.iwant.network.JsonUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CollectActivity extends Activity {
	Handler handler;
	ArrayList<ContentEntity> contentEntities;
	ListView collectListView;
	CollectAdapter collectAdapter;
	Handler mhandler;
	String contentId;
	Connect connect;
	int Position;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.collect);
		initdata();
		initView();
	}
	/**
	 * 初始化数据，根据userId加载收藏
	 */
	private void initdata() {
		//初始化处理信息的handler
		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Data.SUCCESS:
					connect.close();
					try {
						//获取msg中的json
						JSONObject jsonObject = new JSONObject(msg.getData().getString("jsonobject").toString());
						//将json转换为内容实体集合存放在contentEntities中
						contentEntities = JsonUtil.jsonObject2contentEntities(jsonObject);
						//通知适配器刷新界面
						collectAdapter.refreshListView(contentEntities);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;
				case Data.FAILURE:
					connect.close();
					Toast.makeText(CollectActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();			
					break;
				case Data.TIMEOUT:
					connect.close();
					Toast.makeText(CollectActivity.this, "网络连接超时", Toast.LENGTH_LONG).show();			
					break;
				}
			}
		};
		connect = new Connect(Data.getCol, UserEntity.userid);
		connect.sendRequest(mhandler);
	}
	/*
	 * 初始化控件
	 */
	private void initView() {
		
		contentEntities = new ArrayList<ContentEntity>();
//没做呢
		
		LayoutInflater layoutInflater = getLayoutInflater() ;
		collectListView = (ListView) findViewById(R.id.collect_listview);
		collectAdapter = new CollectAdapter(layoutInflater, contentEntities);
		collectListView.setAdapter(collectAdapter);
		//设置触摸监听
		collectListView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				int indexX=0,indexY=0;
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					 //保存按下时的X,Y坐标
					indexX = (int) event.getX();
					indexY = (int) event.getY();
					 break;
				}
				//根据坐标找到item的位置
				Position = ((ListView)v).pointToPosition((int)indexX, (int)indexY);
				return false;
			}
		});
		//设置点击监听
		collectListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//将内容id传递给ContentActivity
				contentId = contentEntities.get(position).getcontentId();
				Intent intent = new Intent();
				intent.setClass(CollectActivity.this, ContentActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("contentId", contentId);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	/**
	 * 处理界面中删除键按下的事件
	 * @param v
	 */
	public void delete(View v){
		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Data.SUCCESS:
					//关闭线程
					connect.close();
					try {
						//删除成功，重新获取收藏的content
						JSONObject jsonObject = new JSONObject(msg.getData().getString("jsonobject").toString());
						contentEntities = JsonUtil.jsonObject2contentEntities(jsonObject);
						//刷新界面
						collectAdapter.refreshListView(contentEntities);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;
				case Data.FAILURE:
					//网络连接失败
					//关闭线程
					connect.close();
					Toast.makeText(CollectActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();					
					break;
				case Data.TIMEOUT:
					//网络连接超时
					//关闭线程
					connect.close();
					Toast.makeText(CollectActivity.this, "网络连接超时", Toast.LENGTH_LONG).show();		
					break;
				}
			}
		};
        //新建connect类，类型设置为删除收藏
		connect = new Connect(Data.delCol,UserEntity.userid,contentEntities.get(Position).getcontentId());
		connect.sendRequest(mhandler);
	}
	/**
	 * 处理界面中编辑键按下的事件
	 * @param v
	 */
	public void collect_edit(View v){
		
	}
	/**
	 * 处理界面中返回键按下的事件
	 * @param v
	 */
	public void collect_back(View v){
		this.finish();
	}
}