package quanta.iwant.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.CommentsEntity;
import quanta.iwant.entity.ContentEntity;
import quanta.iwant.entity.Data;
import quanta.iwant.entity.UserEntity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

public class JsonUtil {
	JSONObject jsonObject = null;
	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse httpResponse;
	private StringBuilder stringBuilder;
	private BufferedReader bufferedReader;
	Handler handler;
	public JsonUtil(HttpClient httpClient,Handler handler) {
		this.httpClient = httpClient;
		this.handler = handler;
	}

	JSONObject sendJson(JSONObject jsonObject, String url) {
		try {
			httpPost = new HttpPost(url);
			System.out.println("新建完毕");
//			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
//			Iterator<String> it = urlParams.keySet().iterator();
//			System.out.println("urlParams.keySet().iterator()"
//					+ urlParams.keySet().iterator());
//			while (it.hasNext()) {
//				String key = it.next();
//				String value = urlParams.get(key);
//				postParams.add(new BasicNameValuePair(key, value));
//			}
//			httpPost.setEntity(new UrlEncodedFormEntity(postParams, HTTP.UTF_8));
			StringEntity stringEntity = new StringEntity(jsonObject.toString());
			httpPost.setEntity(stringEntity);
			try {
				httpResponse = httpClient.execute(httpPost);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					bufferedReader = new BufferedReader(new InputStreamReader(
							httpResponse.getEntity().getContent()));
					for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
							.readLine()) {
						stringBuilder = new StringBuilder();
						stringBuilder.append(s);
					}
					try {
						jsonObject = new JSONObject(stringBuilder.toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return jsonObject;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				System.out.println("协议异");
			} catch (ConnectTimeoutException e) {
				e.printStackTrace();
				System.out.println("连接超时");
			} catch (IOException e){
				e.printStackTrace();
				handler.sendEmptyMessage(Data.FAILURE);
			} 
					
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("编码模式异常");
		}
		return null;
	}

	public static HashMap<String, String> jsonObject2HashMap(
			JSONObject jsonObject) {
		if (jsonObject != null) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			Iterator<String> iterator = jsonObject.keys();
			try {
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = jsonObject.getString(key);
					hashMap.put(key, value);
				}
			} catch (Exception e) {
				Log.w("apputil", "jsonobject2hasmap");
			}
			return hashMap;
		}
		return null;
	}
	public static Bitmap getBitmap(JSONObject jsonObject){
		try {
			String url = jsonObject.getString("pictureUrl");
			URLConnection connection = new java.net.URL(url).openConnection();
			InputStream is;
			is = connection.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			Bitmap bm = BitmapFactory.decodeStream(bis);
			is.close();
			bis.close();
			return bm;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1){
			e1.printStackTrace();
		}
		return null;
	}
	public static ArrayList<ContentEntity> jsonObject2contentEntities(JSONObject jsonObject){
		int num;ContentEntity contentEntity;
		ArrayList<ContentEntity> contentEntities = new ArrayList<ContentEntity>();
		try {
			num = jsonObject.getInt("number");
			for(int i=0;i<num;i++){
				System.out.println("开始便");
				System.out.println(jsonObject.toString());
				System.out.println(jsonObject.getJSONObject("json"+(i+1)).toString());
				JSONObject jsonObject1 = jsonObject.getJSONObject("json"+(i+1));
				contentEntity = new ContentEntity(jsonObject1.getString("title"),jsonObject1.getString("pictureUrl"),jsonObject1.getString("contentId"),jsonObject1.getString("url"));
				contentEntities.add(contentEntity);			
			}
			System.out.println("尝试成功而返回");
			return contentEntities;
		} catch (JSONException e1) {
			System.out.println("json转换异常");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("还是要返回");
		return contentEntities;
		
	}

	public static String bitmap2String(Bitmap bitmap) {
		// 将Bitmap转换成字符串
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, Base64.DEFAULT);
		return string;
	}
	 public static Bitmap String2bitmap(String st){  
	    Bitmap bitmap = null;  
	    try{  
	       byte[] bitmapArray;  
	       bitmapArray = Base64.decode(st, Base64.DEFAULT);  
	       bitmap =BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);  
	       return bitmap;  
	     }catch (Exception e){  
	       return null;  
	   }  
 }  

	public static UserEntity jsonObject2UserEntity(JSONObject jsonObject){
		UserEntity userEntity = null;
		try{
		userEntity = new UserEntity(jsonObject.getString("userid"), jsonObject.getString("nickname"), getBitmap(jsonObject));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return userEntity;
		
	}
	public static ArrayList<CommentsEntity> jsonObject2CommentsEntities(JSONObject jsonObject){
		ArrayList<CommentsEntity> commentsEntities = new ArrayList<CommentsEntity>();
		int num;
		try {
			num = jsonObject.getInt("number");
			for (int i = 1; i < num; i++) {
				JSONObject jsonObject1 = jsonObject.getJSONObject("json"
						+ Integer.toString(i));
				CommentsEntity commentsEntity = new CommentsEntity(
						jsonObject1.getString("name"), getBitmap(jsonObject1),
						jsonObject1.getString("comments"));
				commentsEntities.add(commentsEntity);
			}
			return commentsEntities;
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return commentsEntities;
		
	}
}
