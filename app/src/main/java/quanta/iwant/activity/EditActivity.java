package quanta.iwant.activity;


import com.example.quanta.iwant.R;

import quanta.iwant.entity.Data;
import quanta.iwant.network.Connect;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditActivity extends Activity {
	//连接类
	Connect connect;
	//用户头像
	Bitmap userhead;
	//加载图片结果码
	private static int RESULT_LOAD_IMAGE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
	}
	
	/*
	 * 处理点击修改头像按键的事件
	 */
	public void userhead(View v){
		//调用系统图库
		Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);

	}
	
	/*
	 * 系统回调函数，用于接收从图库中选择的图片的位图资源
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//获得图库中返回的图片
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
  
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
  
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            
            //初始化预览图片控件
            ImageView preview = (ImageView) findViewById(R.id.preview);
            //获得头像
            userhead = BitmapFactory.decodeFile(picturePath);
            //设置预览图片
            preview.setImageBitmap(userhead);
            preview.setVisibility(View.VISIBLE);
  
        }

	}
	
	/*
	 * 处理点击保存按键的事件
	 */
	public void save(View v){
		//从输入框中获得更改后的名字
		EditText nickname = (EditText) findViewById(R.id.nickname);
		String nicknameString = nickname.getText().toString();
		//初始化handler
		Handler mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Data.SUCCESS:
					//上传成功
					//关闭连接线程
					connect.close();
					Toast.makeText(EditActivity.this, "上传成功", Toast.LENGTH_LONG).show();
					break;
				case Data.FAILURE:
					//上传失败
					//关闭连接线程
					connect.close();
					Toast.makeText(EditActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();			
					break;
				case Data.TIMEOUT:
					//上传超时
					//关闭连接线程
					connect.close();
					Toast.makeText(EditActivity.this, "网络连接超时", Toast.LENGTH_LONG).show();					
					break;
				}
			}
		};
		//初始化连接，类型设置为上传
		connect = new Connect(Data.upload, userhead,nicknameString);
		connect.sendRequest(mhandler);
	}
	
	/*
	 * 处理返回按键点击事件
	 */
	public void back(View v){
		this.finish();
	}
	
}
