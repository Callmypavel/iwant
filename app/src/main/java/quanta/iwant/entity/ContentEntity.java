package quanta.iwant.entity;

import com.example.quanta.iwant.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ContentEntity {
	private String title;
	private Bitmap bitmap;
	private String contentId;
	private String url;
	private String pictureUrl;
	public ContentEntity(String title,String pictureUrl,String contentId,String url) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.pictureUrl = pictureUrl;
		this.contentId = contentId;
		this.url = url;
	}
	public ContentEntity(String title,String contentId,String url) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.contentId = contentId;
		this.url = url;
		this.bitmap =BitmapFactory.decodeFile("signin.png");
	}
	public Bitmap getBitmap(){
		return this.bitmap;
	}
	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	public String getTitle(){
		return this.title;
	}
	public String getcontentId(){
		return this.contentId;
	}
	public String getUrl() {
		return this.url;
	}
	public String getpictureUrl() {
		return this.pictureUrl;
	}
}
