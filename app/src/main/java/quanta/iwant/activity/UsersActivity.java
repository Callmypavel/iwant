package quanta.iwant.activity;

import javax.security.auth.login.LoginException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.Data;


import quanta.iwant.entity.UserEntity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UsersActivity extends Activity {
	ImageView userHead;
	Button signin;
	TextView nickname;
	ImageView myMessage;
	ImageView mycollection;
	ImageView edit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user);
	    try {
			initView();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 恢复页面的回调方法，刷新页面
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		try {
			refreshViewByState();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 初始化控件
	 */
	private void initView() throws JSONException {
	    signin = (Button) findViewById(R.id.signin);
		nickname = (TextView) findViewById(R.id.nickname);
		userHead = (ImageView) findViewById(R.id.userHead);
		myMessage = (ImageView) findViewById(R.id.mynews);
	    mycollection = (ImageView) findViewById(R.id.mycollection);
		edit = (ImageView) findViewById(R.id.edit);
		refreshViewByState();
	}
	
	/*
	 * 返回按键的处理
	 */
	public void getBack(View v){
		this.finish();
	}
	
	/*
	 * 登陆按键的处理
	 */
	public void signin(View v){
		Intent intent = new Intent();
		intent.setClass(UsersActivity.this, UserLoginActivity.class);
		startActivityForResult(intent, 1);
	}
	/*
	 * 根据登录状态刷新页面
	 */
	public void refreshViewByState() throws JSONException{
		if(!Data.isLogined){
			userHead.setVisibility(View.GONE);
			signin.setVisibility(View.VISIBLE);
			nickname.setVisibility(View.GONE);
			edit.setVisibility(View.GONE);
			myMessage.setImageResource(R.drawable.umessage);
			mycollection.setImageResource(R.drawable.ucollection);
		}
		if(Data.isLogined){
			userHead.setVisibility(View.VISIBLE);
			userHead.setImageResource(R.drawable.defaulthead);
			signin.setVisibility(View.GONE);
			nickname.setVisibility(View.VISIBLE);
			nickname.setText(UserEntity.nickname);
			edit.setVisibility(View.VISIBLE);
			myMessage.setImageResource(R.drawable.mynews);
			mycollection.setImageResource(R.drawable.mycollection);
		}
		if(Data.isRegister){
			userHead.setVisibility(View.VISIBLE);
			userHead.setImageResource(R.drawable.defaulthead);
			signin.setVisibility(View.GONE);
			nickname.setTag(UserEntity.nickname);
			nickname.setVisibility(View.VISIBLE);
			edit.setVisibility(View.VISIBLE);
			myMessage.setImageResource(R.drawable.mynews);
			mycollection.setImageResource(R.drawable.mycollection);
		}
	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if(resultCode==RESULT_OK){
//			Intent intent = getIntent();
//			intent.putExtras(data.getExtras());
//			setResult(RESULT_OK,data);
//		}
//	}
	
	/*
	 * 点击编辑按键
	 */
	public void edit(View v){
		Intent intent = new Intent();
		intent.setClass(UsersActivity.this, EditActivity.class);
		startActivity(intent);
	}
	/*
	 * 点击我的消息
	 */
	public void Mymessage(View v){
		if(!Data.isLogined){
			Toast.makeText(this, "您尚未登陆，请先登陆",Toast.LENGTH_SHORT).show();
		}else{
			Intent intent = new Intent();
			intent.setClass(UsersActivity.this, MessageActivity.class);
			startActivity(intent);
		}
	}
	/*
	 * 点击我的收藏
	 */
	public void Mycollections(View v) {
		if(!Data.isLogined){
			Toast.makeText(this, "您尚未登陆，请先登陆",Toast.LENGTH_SHORT).show();
		}else{
			Intent intent = new Intent();
			intent.setClass(UsersActivity.this, CollectActivity.class);
			startActivity(intent);
		}
	}
	
}
