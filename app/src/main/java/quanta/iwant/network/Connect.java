package quanta.iwant.network;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import quanta.iwant.entity.Data;
import android.graphics.Bitmap;
import android.os.Handler;

public class Connect {
	Thread thread;
	JsonUtil jsonUtil;
	HttpClient httpClient;
	int getType;
    String classification;
	String time;
	String user;
	String password;
	String email;
	String nickname;
	Bitmap userhead;
	GetRunnable getRunnable;
	/*

	 * 根据参数的数量和类型动态调用的构造方法
	 */

	public Connect(int getType,String classification) {
		this.getType =getType;
		this.classification = classification;
	}
	public Connect(int getType,String user,String password) {
		this.getType = getType;
		this.user = user;
		this.password = password;
	}
	public Connect(int getType,String nickname,String password,String email) {
		this.getType =getType;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
	}
	public Connect(int getType,Bitmap userhead,String nickname) {
		this.getType =getType;
		this.nickname = nickname;
		this.userhead = userhead;
	}
	/*
	 * 发送请求，连接网络
	 */
	public void sendRequest(Handler mhandler){
		
		HttpClient httpClient = new DefaultHttpClient();
		JsonUtil jsonUtil = new JsonUtil(httpClient, mhandler);
		switch(getType){
		case Data.getMsg:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, classification);
			//此处classification为用户id
			break;
		case Data.getCon:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, classification);

			//此处classification为分类
			break;
		case Data.getCom:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, classification);
			//此处的classification实际为contentId;
			break;
		case Data.getCol:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, classification);
			//此处的classification实际为userid;
			break;
		case Data.delCol:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, user, password);
			break;
		case Data.addCol:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, user, password);
			break;
		case Data.upload:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, userhead,nickname);
			break;
		case Data.login:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, user,password);
			break;
		case Data.register:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, nickname,password,email);
			break;
		case Data.comment:
			getRunnable = new GetRunnable(mhandler, jsonUtil, getType, nickname ,password ,email);
			break;
			//nickname实际为userId password实际为评论内容 email实际为contentId
		
		}
		thread = new Thread(getRunnable);
		thread.start();
	}
	public void close(){
		if(getRunnable!=null){
		getRunnable.exit=true;
		}
	}
	
}
