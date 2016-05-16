package quanta.iwant.activity;

import java.util.ArrayList;

import quanta.iwant.adapter.MessageAdapter;
import quanta.iwant.entity.MessageEntity;

import com.example.quanta.iwant.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MessageActivity extends Activity {
	//消息实体集合
	ArrayList<MessageEntity> messageEntities ;
	//消息适配器
	MessageAdapter messageAdapter;
	//装载消息的listview
	ListView messageListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message);
		initview();
	}
	
	/*
	 * 初始化控件
	 */
	private void initview() {	
		//展示用初始化消息
		messageEntities = new ArrayList<MessageEntity>();
		MessageEntity messageEntity = new MessageEntity("系统消息","送你一袋金克拉",BitmapFactory.decodeResource(getResources(), R.drawable.artboard));
		messageEntities.add(messageEntity);
		
		//设置listview和adapter
		LayoutInflater layoutInflater = getLayoutInflater() ;
		messageListView = (ListView) findViewById(R.id.message);
		messageAdapter = new MessageAdapter(layoutInflater, messageEntities);
		messageListView.setAdapter(messageAdapter);
		messageListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("content", messageEntities.get(position).getMessage());
				i.setClass(MessageActivity.this, MessageContentActivity.class);
				startActivity(i);
			}
		});
	}
	
	/*
	 * 处理返回按键点击事件
	 */
	public void message_back(View v){
		this.finish();
	}
	
}
