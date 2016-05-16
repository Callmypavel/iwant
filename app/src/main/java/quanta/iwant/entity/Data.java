package quanta.iwant.entity;


public class Data {
	public static String contentId;
	public static String url;
	public static boolean isRegister;
	public static boolean isLogined;
	public static String classification1 ;
	public static String classification2 ;
	public static int currentItem;
	//定义状态码
	public static final int SUCCESS = 1;
	public static final int FAILURE = 2;
	public static final int TIMEOUT = 3;
	public static final int MsgSUCCESS = 4;
	public static final int LoginSUCCESS = 5;
	public static final int ContentSUCCESS = 6;
	public static final int RegisterSUCCESS = 7;
	//定义请求码
	public static final int getMsg = 10;
	public static final int getCon = 11;
	public static final int getCom = 12;
	public static final int getCol = 13;
	public static final int delCol = 14;
	public static final int addCol = 15;
	public static final int login = 16;
	public static final int upload = 17;
	public static final int register = 18;
	public static final int comment = 19;
	
	public static String LastUpdateTime;
	
	public static String classification(){
		return classification1+classification2;
	}
}
