package quanta.iwant.entity;

import android.graphics.Bitmap;

public class UserEntity {
    public static String userid;
	public static String nickname;
	public static Bitmap userhead;
	/**
	 * 
	 * @param userid
	 * @param nickname
	 * @param userhead
	 */
	public UserEntity(String userid,String nickname,Bitmap userhead) {
		// TODO Auto-generated constructor stub
		UserEntity.userid = userid;
		UserEntity.nickname = nickname;
		UserEntity.userhead = userhead;
	}


}
