package quanta.iwant.activity;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.Data;
import quanta.iwant.entity.UserEntity;
import quanta.iwant.network.Connect;


import quanta.iwant.network.JsonUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UserLoginActivity extends Activity {
	//账号密码
	String user;
	String password;
	//保存账号密码的sharedpreference
	SharedPreferences pref;
	Editor editor;
	//编辑框
	EditText euser;
	EditText epassword;
	//连接类，用于连接网络
	Connect connect;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);	
		initView();	
	}

	/*
	 * 初始化控件并从sharedpreference获取保存的账户和密码
	 */
	public void initView(){
		euser = (EditText) findViewById(R.id.textUserid);
		epassword = (EditText) findViewById(R.id.textPassword);
		pref = getSharedPreferences("MyPerf", MODE_PRIVATE);	
		String name = pref.getString("username", "");
		if(name==null||name.equals("")){
		}else{
			euser.setText(name);
		}
		if(password==null||password.equals("")){
		}else{
			epassword.setText(password);
		}
	}
	
	/*
	 * 处理按下登陆按键时的事件
	 *
	 */
	public void Login(View view) {
		// TODO Auto-generated method stub
		System.out.println("按下登陆按键");
		editor = pref.edit();
		user = euser.getText().toString().trim();
		password = epassword.getText().toString().trim();
	    Handler mhandler = new Handler() {
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case Data.LoginSUCCESS:
						Toast.makeText(UserLoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
						//将用户名和密码存放至sharedpreference
						editor.putString("username", user);
						editor.putString("password", password);
						editor.commit();
						try{
						JSONObject jsonObject = new JSONObject(msg.getData().getString("jsonobject").toString());
						UserEntity.userid = jsonObject.getString("userid");
						}catch(Exception e){
							
						}
						//设置为已登录状态
						Data.isLogined = true;
						finish();
						connect.close();
						break;
					case Data.FAILURE:
					    Toast.makeText(UserLoginActivity.this, "登陆失败",
							Toast.LENGTH_LONG).show();
					     // 移除错误的用户名和密码
					    editor.remove("username");
					    editor.commit();
					    connect.close();
					    break;
					case Data.TIMEOUT:
						Toast.makeText(UserLoginActivity.this, "超时", Toast.LENGTH_LONG).show();
						connect.close();
						break;
					}
				}
			};	
		//连接类型设置为登陆
		connect = new Connect(Data.login, user,password);
		connect.sendRequest(mhandler);
	}	
	/*
	 * 处理注册账号按键的点击事件
	 */
	public void register(View v){
		Intent i = new Intent();
		i.setClass(this, RegisterActivity.class);
		startActivityForResult(i, 1);
	}
	/*
	 * 判断是否账号或密码为空
	 */
	public void judgeEmpty(){
		if(user.equals("")){
			Toast.makeText(this, "账号不可为空", Toast.LENGTH_LONG).show();
		}
		if(password.equals("")){
			Toast.makeText(this, "密码不可为空", Toast.LENGTH_LONG).show();
		}
	
	}
	/*
	 * 处理关闭按键的点击事件
	 */
	public void close(View v){
		this.finish();
	}
}
