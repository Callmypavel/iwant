package quanta.iwant.activity;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.Data;
import quanta.iwant.entity.UserEntity;
import quanta.iwant.network.Connect;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{
	//注册时的四个编辑框
	EditText email;
	EditText nickname;
	EditText password;
	EditText passwordagain;
	
	//从框中获得的信息
	String emailString;
	String nicknameString;
	String passwordString;
	String passwordagainString;
	
	//连接类
	Connect connect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		initView();
	}

	/*
     * 初始化控件
     */
	private void initView() {
		// TODO Auto-generated method stub
	    email = (EditText) findViewById(R.id.email);
	    nickname = (EditText) findViewById(R.id.nickname);
	    password = (EditText) findViewById(R.id.register_password);
	    passwordagain = (EditText) findViewById(R.id.passwordagain);
	}
	/*
	 * 处理点击完成按键时的事件
	 */
	public void finish(View v){
		//从编辑框中获取字符串
		emailString = email.getText().toString();
		nicknameString = nickname.getText().toString();
		passwordString = password.getText().toString();
		passwordagainString = passwordagain.getText().toString();
		//判断是否有空信息
		judgeEmpty();
		if (!emailString.equals("") && !nicknameString.equals("")
				&& !passwordString.equals("") && !passwordagain.equals("")) {
			//若都不为空
			//初始化线程
			Handler mhandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case Data.SUCCESS:
						connect.close();
//						try {
//							if(msg.getData().getString("jsonobject").toString()!=null){
//							JSONObject jsonObject = new JSONObject(msg.getData().getString("jsonobject").toString());
//							UserEntity.userid = jsonObject.getString("userid");
//							}
							UserEntity.nickname = nicknameString;
							UserEntity.userhead = BitmapFactory.decodeResource(getResources(), R.drawable.defaulthead);
							Data.isRegister = true;
							Intent intent = new Intent();
							intent.setClass(RegisterActivity.this, UsersActivity.class);
							startActivity(intent);
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
						break;
					case Data.FAILURE:
						//网络连接失败
						connect.close();
						Toast.makeText(RegisterActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();		
						break;
					case Data.TIMEOUT:
						connect.close();
						//网络连接超时
						Toast.makeText(RegisterActivity.this, "网络连接超时", Toast.LENGTH_LONG).show();		
						break;
					}
				}
			};
			//将连接类型设置为注册
			connect = new Connect(Data.register, nicknameString,passwordString,emailString);
			connect.sendRequest(mhandler);
		}
	}
	/*
	 * 判断信息是否为空
	 */
	public void judgeEmpty(){
		if(emailString.equals("")){
			Toast.makeText(this, "注册邮箱不可为空", Toast.LENGTH_LONG).show();
		}
		if(nicknameString.equals("")){
			Toast.makeText(this, "账号不可为空", Toast.LENGTH_LONG).show();
		}
		if(passwordString.equals("")){
			Toast.makeText(this, "密码不可为空", Toast.LENGTH_LONG).show();
		}
		if(passwordagainString.equals("")){
			Toast.makeText(this, "请再次输入密码", Toast.LENGTH_LONG).show();
		}
		if(!passwordString.equals(passwordagainString)){
			Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_LONG).show();
			password.setText("");
			passwordagain.setText("");
		}
	}
	/*
	 * 处理点击关闭按键的事件
	 */
	public void close(View v){
		this.finish();
	}

}
