package quanta.iwant.entity;

import android.graphics.Bitmap;

public class MessageEntity {
	private String name;
	private String message;
	private Bitmap head;
	

	public MessageEntity(String name, String text, Bitmap head) {
		super();
		this.name = name;
		this.message = text;
		this.head = head;
	}

	public String getName() {
		return name;
	}
	public String getMessage() {
		return message;
	}

	public Bitmap getHead() {
		return head;
	}

}
