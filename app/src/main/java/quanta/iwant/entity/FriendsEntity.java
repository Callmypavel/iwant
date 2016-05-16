package quanta.iwant.entity;

import android.graphics.Bitmap;

public class FriendsEntity {
	private String id;
	private String name;
	private Bitmap head;
	public FriendsEntity(String id,String name,Bitmap head){
		this.id = id;
		this.name = name;
		this.head = head;
	}
	public String getid(){
		return this.id;
	}
	public String getname(){
		return this.name;
	}
	public Bitmap gethead(){
		return this.head;
	}

}
