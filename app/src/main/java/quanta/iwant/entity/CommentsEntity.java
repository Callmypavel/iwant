package quanta.iwant.entity;

import android.graphics.Bitmap;

public class CommentsEntity {
	private String name;
	private Bitmap head;
	private String comment;
	public CommentsEntity(String name,Bitmap head,String comment) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.head = head;
		this.comment = comment;
	}
	public String getComment() {
		return comment;
	}
	public Bitmap getHead() {
		return head;
	}
	public String getName() {
		return name;
	}
}
