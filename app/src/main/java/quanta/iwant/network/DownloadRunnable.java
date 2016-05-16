package quanta.iwant.network;



import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class DownloadRunnable implements Runnable {
	Handler mhandler;
	Boolean exit = false;
	String url;
	Bitmap bm;
	public DownloadRunnable(String url,Handler mhandler) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.mhandler = mhandler;
	}
	public void close(){
		this.exit = true;
	}
       
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!exit) {	
			Log.v("仍在运行", "ne");
				try {
					URLConnection connection = new java.net.URL(url)
							.openConnection();
					Log.v("url", url);
					InputStream is;
					is = connection.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					bm = BitmapFactory.decodeStream(bis);
					Log.v("获取位图", bm+"");
					Message msg = new Message();
					if(bm!=null){
						Log.v("获取位图", "哈哈");
						msg.what=0x123;
						Bundle bundle = new Bundle();
						bundle.putString("Bitmap", JsonUtil.bitmap2String(bm));
						msg.setData(bundle);
					}
					mhandler.sendMessage(msg);
					    
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
	}

}
