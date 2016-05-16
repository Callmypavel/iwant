package quanta.iwant.activity;


import com.example.quanta.iwant.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MessageContentActivity extends Activity {
	//将要展示的详细内容
	String content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message_content);
		initdata();
		initview();
	}
	
	/*
	 * 从消息页面获取内容初始化数据
	 */
	private void initdata() {
		// TODO Auto-generated method stub
		Intent i = getIntent();
		content = i.getExtras().getString("content");	
	}
	/*
	 * 初始化控件
	 */
	private void initview() {
		TextView messagecontent = (TextView) findViewById(R.id.message_content);
		messagecontent.setText(content);
	}
	
	/*
	 * 处理返回按键按下时的事件
	 */
	public void message_content_back(View v){
		this.finish();
	}
	

}
