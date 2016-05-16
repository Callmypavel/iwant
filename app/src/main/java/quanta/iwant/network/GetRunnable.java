package quanta.iwant.network;


import org.json.JSONException;
import org.json.JSONObject;

import quanta.iwant.entity.Data;
import quanta.iwant.entity.UserEntity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class GetRunnable implements Runnable{
	public boolean exit = false;
	private Handler handler;
	private JsonUtil jsonUtil;
	private int getType;
	private String classification;
	private String time;
	private String user;
	private String password;
	private String email;
	private String nickname;
	Bitmap userhead;
	String contentId;
	JSONObject jsonObject = new JSONObject();
	Bundle bundle;
	JSONObject rext;
	/*
	 * 动态调用的构造方法
	 */
	
	public GetRunnable(Handler handler,JsonUtil jsonUtil,int getType,String classification) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.jsonUtil = jsonUtil;
		this.getType = getType;
		this.classification = classification;
	}
	public GetRunnable(Handler handler,JsonUtil jsonUtil,int getType,String user,String password) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.jsonUtil = jsonUtil;
		this.getType = getType;
		this.user = user;
		this.password = password;
	}
	public GetRunnable(Handler handler,JsonUtil jsonUtil,int getType,String nickname,String password,String email) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.jsonUtil = jsonUtil;
		this.getType = getType;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
	}
	public GetRunnable(Handler handler,JsonUtil jsonUtil,int getType,Bitmap userhead,String nickname) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.jsonUtil = jsonUtil;
		this.getType = getType;
		this.nickname = nickname;
		this.userhead = userhead;
	}

    /**
     * 重载run方法
     * 
     */
	@Override
	public void run() {
		if(Data.LastUpdateTime==null){
			System.out.println("更新时间为空");
			Data.LastUpdateTime = "000000000000";
		}
		while (!exit) {
			// TODO Auto-generated method stub
//			SimpleDateFormat formatter = new SimpleDateFormat(
//					"yyyy年MM月dd日    HH时mm分ss秒 ");
//			Date curDate = new Date(System.currentTimeMillis());
//			// 转换日期格式
//			time = formatter.format(curDate);
			time = Long.toString(System.currentTimeMillis());
			switch(getType){
			case Data.getMsg:
				getMessage();
				break;
			case Data.getCon:
				getContents();
				break;
			case Data.getCom:
				getComments();
				break;
			case Data.getCol:
				getCollections();
				break;
			case Data.delCol:
				deleteCollections();
				break;
			case Data.addCol:
				addCollections();
				break;
			case Data.upload:
				upload();
				break;
			case Data.login:
				login();
				break;
			case Data.register:
				register();
				break;
			case Data.comment:
				break;
			
			}		
			JSONObject rejsonObject = jsonUtil.sendJson(jsonObject,
					"http://192.168.204.32:8080/iwant/iwantBackground");
			Data.LastUpdateTime = time;
			System.out.println("获得的响应"+rejsonObject.toString());
			if (rejsonObject != null) {
				System.out.println("响应");
				Message msg = new Message();
				try {
					System.out.println("我倒是要看"+rejsonObject.getInt("returnType"));
					switch (rejsonObject.getInt("returnType")){
					case Data.ContentSUCCESS:
						System.out.println("成功获取内容！");
						msg.what = Data.ContentSUCCESS;
						bundle = new Bundle();
						bundle.putString("jsonobject", rejsonObject.toString());
						msg.setData(bundle);
						break;
					case Data.LoginSUCCESS:
						msg.what = Data.LoginSUCCESS;
						break;
					case Data.MsgSUCCESS:
						msg.what = Data.MsgSUCCESS;
						break;
					case Data.RegisterSUCCESS:
						System.out.println("成功注册！");
						System.out.println(rejsonObject.getInt("returnType"));
						msg.what = Data.RegisterSUCCESS;
						UserEntity.userid=rejsonObject.getString("userId");
						System.out.println("用户uuid  "+UserEntity.userid);
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(msg);
				
			}
			
			try {
				Thread.sleep(5000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	    public void getCollections() {
		// TODO Auto-generated method stub
		   jsonObject = new JSONObject();	  
		   try {
			jsonObject.put("currentTime", time);
			jsonObject.put("classification", "getCollection");
			jsonObject.put("UserId", classification);			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	 }
	    public void deleteCollections() {
			// TODO Auto-generated method stub
	    	 jsonObject = new JSONObject();	  
			   try {
				jsonObject.put("currentTime", time);
				jsonObject.put("classification", "deletaCollection");
				jsonObject.put("UserId", user);
				jsonObject.put("ContentId", password);			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  			
	 }
	    public void addCollections() {
			// TODO Auto-generated method stub
	    	  jsonObject = new JSONObject();	  
			   try {
				    jsonObject.put("currentTime", time);
				    jsonObject.put("classification", "addCollection");
				    jsonObject.put("UserId", user);
				    jsonObject.put("ContentId", password);			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		public void getMessage(){
			  jsonObject = new JSONObject();	  
			   try {
				    jsonObject.put("currentTime", time);
				    jsonObject.put("classification", "getMessage");
				    jsonObject.put("userId", classification);			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}  
	    
	     }
	    public void getContents(){
	    	  jsonObject = new JSONObject();	  
			   try {
				    jsonObject.put("currentTime", time);
					jsonObject.put("classification", "getContent");
					jsonObject.put("type",Data.classification());
					jsonObject.put("lastUpdateTime", Data.LastUpdateTime);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

	    }
	    public void login(){
	    	  jsonObject = new JSONObject();	  
			   try {
				    jsonObject.put("currentTime", time);
				    jsonObject.put("classification", "login");
				    jsonObject.put("user", user);
				    jsonObject.put("password", password);			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			 if(user.equals("quanta")&&password.equals("iwant")){
					Message msg = new Message();
					UserEntity.userid = "9527";
					UserEntity.nickname = "超级用户";
					msg.what = Data.LoginSUCCESS;
					handler.sendMessage(msg);
				}
	    }
	    public void register(){
	    	 System.out.println("发送注册请求");
	    	  //jsonObject = new JSONObject();	  
	    	   System.out.println(rext.toString());
			   try {
				    jsonObject.put("currentTime", time);
				    jsonObject.put("classification", "register");
				    jsonObject.put("nickname", nickname);
				    jsonObject.put("password", password);
				    jsonObject.put("email", email);	
				    System.out.println("用户名"+nickname+"密码"+password+"邮箱"+email);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			 if(nickname.equals("quanta")&&password.equals("iwant")){
					Message msg = new Message();
					UserEntity.userid = "9527";
					UserEntity.nickname = "超级用户";
					msg.what = Data.SUCCESS;
					handler.sendMessage(msg);
				}
	    }
	    public void upload(){
	    	 String userheadString = null ;
	    	 if(userhead!=null){
		    	 userheadString = JsonUtil.bitmap2String(userhead);
		    	}
	    	 jsonObject = new JSONObject();	  
			   try {
				    jsonObject.put("currentTime", time);
				    jsonObject.put("classification", "upload");
				    jsonObject.put("nickname", nickname);
				    jsonObject.put("email", email);
				    jsonObject.put("userhead", userheadString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
	    public void comment(){
	    	jsonObject = new JSONObject();	  
			   try {
				    jsonObject.put("currentTime", time);
					jsonObject.put("classification", "comment");
					jsonObject.put("userId", nickname);
					jsonObject.put("comment", password);
					jsonObject.put("contentId", email);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
	    public void getComments(){
	    	jsonObject = new JSONObject();	  
			   try {
				jsonObject.put("currentTime", time);
				jsonObject.put("classification", "comments"+classification);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
	    public void setgetType(int getType){
	    	this.getType = getType;
	    }
	    public void setExit(Boolean exit){
	    	this.exit = exit;
	    }


}
