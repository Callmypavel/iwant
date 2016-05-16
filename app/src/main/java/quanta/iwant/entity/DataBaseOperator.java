package quanta.iwant.entity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DataBaseOperator extends SQLiteOpenHelper {
	private final static int VERSION = 1;
	private final static String CONTENTS = "contents";
	private final static String MESSAGES = "messages";
	private final static String FRIENDS = "friends";
    final private String CREATE_TABLE_SQL = "create table dict(_id integer primary key autoincrement, word, detail)"; 
	private final static String CREATE_Contents = "create table if not exists contents(_id integer primary key autoincrement,title text not null,pictureUrl text not null,contentId text not null,url text not null)";
	private final static String CREATE_Friends = "create table if not exists friends(_id integer primary key autoincrement, name text not null, head blob not null, id text not null)";
	private final static String CREATE_Messages = "create table if not exists messages(_id integer primary key autoincrement, name text not null, isComeMsg boolean not null)";
	private final static String CREATE_User = "create table if not exists user(userid text not null,nickname text not null,userhead blob not null)";
	public DataBaseOperator(Context context, String name) {
		super(context, name, null, 1);
		// TODO Auto-generated constructor stub
	}

//	public DataBaseOperator(Context context, String name,
//			CursorFactory factory, int version) {
//		super(context, name, factory, version);
//		// TODO Auto-generated constructor stub
//	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_Contents);
		db.execSQL(CREATE_Messages);
		db.execSQL(CREATE_Friends);
		db.execSQL(CREATE_User);
		db.execSQL(CREATE_TABLE_SQL); 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	public void insert(ContentValues values,String tableName){  
		// 获取SQLiteDatabase实例
		SQLiteDatabase db = getWritableDatabase();
		// 插入数据库中
		db.insert(tableName, "ab", values);
		db.close();
	}

	public void saveContentEntities(ArrayList<ContentEntity> contentEntities){
		for (ContentEntity contentEntity : contentEntities) {
			String pictureUrl = contentEntity.getUrl();
			String title = contentEntity.getTitle();
			String contentId = contentEntity.getcontentId();
			String url = contentEntity.getUrl();
			ContentValues values = new ContentValues();
			values.put("pictureUrl", pictureUrl);
			values.put("title", title);
			values.put("contentId", contentId);
			values.put("url", url);
			insert(values, CONTENTS);
		}
	}
	public void saveFriendsEntities(ArrayList<FriendsEntity> friendsEntities){
		for(FriendsEntity friendsEntity : friendsEntities){
		Bitmap head = friendsEntity.gethead();
		byte[] picture = bitmap2blob(head);
		String name = friendsEntity.getname();
		ContentValues values = new ContentValues();
		values.put("picture",picture);
		values.put("name",name);
		insert(values, FRIENDS);
		}
	}
	public void saveUserEntity(UserEntity userEntity){
		Bitmap head = UserEntity.userhead;
		byte[] userhead = bitmap2blob(head);
		String nickname = UserEntity.nickname;
		String userid = UserEntity.userid;
		ContentValues values = new ContentValues();
		values.put("userhead",userhead);
		values.put("userid",userid);
		values.put("nickname",nickname);
		insert(values, "User");
	}

	public byte[] bitmap2blob(Bitmap bitmap) {  
		 if (bitmap == null) {  
		     return null;  
	  }  
        // 最终图标要保存到浏览器的内部数据库中，系统程序均保存为SQLite格式，Browser也不例外，因为图片是二进制的所以使用字节数组存储数据库的  
	    // BLOB类  
	    final ByteArrayOutputStream os = new ByteArrayOutputStream();  
	    // 将Bitmap压缩成PNG编码，质量为100%存储          
	    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os); 
	    return os.toByteArray();  
	}
	/**
	 * 
	 * @param 列名称数组
	 * @param 查询条件子句
	 * @param 条件子句额外的参数
	 * @param 分组列
	 * @param 分组条件
	 * @param 排序列
	 * @return 内容实体集合
	 */
	
	public ArrayList<ContentEntity> getContentEntities(String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy){
		SQLiteDatabase db = getReadableDatabase(); 
		Cursor cursor = db.query(CONTENTS, columns, selection, selectionArgs, groupBy, having, orderBy) ;
		ArrayList<ContentEntity> contentEntities = new ArrayList<ContentEntity>();
		if(cursor.moveToFirst()){
	    while(!cursor.isLast()){
	    	cursor.moveToNext();
	    	String pictureUrl = cursor.getString(cursor.getColumnIndex("pictureUrl"));
		    String title = cursor.getString(cursor.getColumnIndex("title"));
		    String id = cursor.getString(cursor.getColumnIndex("contentId"));
		    String url = cursor.getString(cursor.getColumnIndex("url")); 
		    contentEntities.add(new ContentEntity(title,pictureUrl,id,url));
	    }
	    
	}
	    return contentEntities;
	}
	
	public ArrayList<FriendsEntity> getFriendsEntities(String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy){
		SQLiteDatabase db = getReadableDatabase(); 
		Cursor cursor = db.query(FRIENDS, columns, selection, selectionArgs, groupBy, having, orderBy) ;
		ArrayList<FriendsEntity> friendsEntities = new ArrayList<FriendsEntity>();
		if(cursor.moveToFirst()){
	    while(!cursor.isLast()){
	    	cursor.moveToNext();
	    	byte[] in = cursor.getBlob(cursor.getColumnIndex("head")); 
		    Bitmap head = BitmapFactory.decodeByteArray(in, 0, in.length);
		    String name = cursor.getString(cursor.getColumnIndex("name"));
		    String id = cursor.getString(cursor.getColumnIndex("id"));
		    friendsEntities.add(new FriendsEntity(id,name,head));
	    }
		}
	    return friendsEntities;
	}
}
