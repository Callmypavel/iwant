package quanta.iwant.activity;

import java.util.ArrayList;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.DataBaseOperator;
import quanta.iwant.entity.FriendsEntity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class FriendListActivity extends Activity{
	//好友实体集合
	private ArrayList<FriendsEntity> friendsEntities;
	//放好友的listview
	private ListView friendListView;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.id.FriendlistListView);
		initdata();
		initview();
	}
	
	/*
	 * 初始化数据
	 */
	public void initdata(){
		// 从数据库中获取好友实体集合
		DataBaseOperator dataBaseOperator = new DataBaseOperator(this,
				"friends.db");
		friendsEntities = dataBaseOperator.getFriendsEntities(null, null, null,
				null, null, null);
		if (friendsEntities == null) {
			// 新建线程从服务器获取好友列表
			// TODO Auto-generated method stub
		}
	}
	
	/*
	 * 初始化控件
	 */
	public void initview(){
		//初始化listview
		friendListView = (ListView) findViewById(R.id.FriendlistListView);
		// TODO Auto-generated method stub
	}

}
