package quanta.iwant.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import quanta.iwant.activity.MainActivity;
import quanta.iwant.entity.ContentEntity;
import quanta.iwant.entity.Data;
import quanta.iwant.entity.DataBaseOperator;
import quanta.iwant.fragment.BaseFragment;
import quanta.iwant.fragment.HotFragment;
import quanta.iwant.fragment.RecommandFragment;
import quanta.iwant.network.Connect;
import quanta.iwant.network.JsonUtil;

import com.example.quanta.iwant.R;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class RefreshListView extends ListView implements OnScrollListener{
	View header;//顶部布局文件
	int headerHeight;//顶部布局文件高度
	int firstVisibleItem;//当前第一个可见的item的位置
	int scrollState;//当前滚动状态
	boolean isRemark;//是否在最顶端按下
	int startY;//按下的Y值
    int state;//当前状态
	final  int NONE = 0;//正常状态
	final  int PULL = 1;//下拉可刷新状态
	final  int RELESE = 2;//松开释放状态
	final  int REFLASH = 3;//正在刷新状态
	Handler handler;
	 Activity context;
	BaseFragment baseFragment;
    RecommandFragment recommandFragment;
	 HotFragment hotFragment;
	 Boolean isRecommand;
	 Connect connect;
    public RefreshListView(Context context){
    	super(context);
    	initView(context);
    }

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	public RefreshListView(Context context, AttributeSet attrs,int defStyle){
		super(context, attrs, defStyle);
		initView(context);
	}
	//初始化界面，添加顶部布局文件
	private void initView(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.header,null);
		measureView(header);
		headerHeight = header.getMeasuredHeight();
		topPadding(-headerHeight);
		this.addHeaderView(header);
		this.setOnScrollListener(this);
		System.out.println("已设置监听");
	}
	/***
	 * 通知父布局占用的宽高
	 * @param view
	 */
	private void measureView(View view){
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if(p==null){
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int height;
		int tempHeight = p.height;
		if(tempHeight > 0){
			height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
		}else{
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(width, height);
	}
	/**
	 * 设置header布局上边距
	 * @param topPadding
	 */
	private  void topPadding(int topPadding){
		header.setPadding(header.getPaddingLeft(), topPadding,
				header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(firstVisibleItem==0){
				isRemark = true;
				startY = (int) ev.getY();
				System.out.println("startY"+startY);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			onMove(ev);
			break;
		case MotionEvent.ACTION_UP:
			if(state==RELESE){
				state = REFLASH;
				//加载最新数据
				reflashViewByState();
			}else if(state == PULL){
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}
	/**
	 * 判断移动过程中的操作
	 * @param ev
	 */
	private void onMove(MotionEvent ev){
		if(!isRemark){
			return;
		}
		int tempY = (int) ev.getY();
		int deltaY = tempY - startY;
		int topPadding = deltaY - headerHeight;
		switch(state){
		case NONE:
			if(deltaY>0){
				state = PULL;
				reflashViewByState();
			}
			
			break;
		case PULL:
			topPadding(topPadding);
			if (deltaY > headerHeight + 30 ){
				state = RELESE;	
				reflashViewByState();
				System.out.println("切换状态");
			}
			
			break;
		case RELESE:
			topPadding(topPadding);
			if(deltaY<headerHeight+30){
				state = PULL;	
				reflashViewByState();
			}else if (deltaY<=0) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}		
			break;
		case REFLASH:
			break;
		}
	}
	/**
	 * 根据当前状态改变布局显示
	 */
	public  void reflashViewByState(){
		TextView tip = (TextView) header.findViewById(R.id.tip);
		ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
		ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
		RotateAnimation animation = new RotateAnimation(0, 180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5F,
				RotateAnimation.RELATIVE_TO_SELF, 0.5F);
		animation.setDuration(500);
		animation.setFillAfter(true);
		RotateAnimation animation1 = new RotateAnimation(180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5F,
				RotateAnimation.RELATIVE_TO_SELF, 0.5F);
		animation1.setDuration(500);
		animation1.setFillAfter(true);
		switch (state) {
		case NONE:
			topPadding(-headerHeight);
			arrow.clearAnimation();
			break;
		case PULL:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("下拉可刷新！");
			arrow.clearAnimation();
			arrow.setAnimation(animation1);
			break;
		case RELESE:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("松开可刷新！");
			arrow.clearAnimation();
			arrow.setAnimation(animation);
			break;
		case REFLASH:
			topPadding(50);
			arrow.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("正在刷新...");
			arrow.clearAnimation();
			Handler mhandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case Data.ContentSUCCESS:
						connect.close();
						try {
							Toast.makeText(context, "刷新成功", Toast.LENGTH_LONG).show();
							JSONObject jsonObject = new JSONObject(msg.getData().getString("jsonobject").toString());
							ArrayList<ContentEntity> contentEntities = JsonUtil.jsonObject2contentEntities(jsonObject);
							DataBaseOperator dataBaseOperator = new DataBaseOperator(context, "iwant.db");
							dataBaseOperator.saveContentEntities(contentEntities);
							System.out.println("contentEntities"+contentEntities);
							//context.listener.onRefresh(context.contentEntities);
							if(isRecommand&&contentEntities!=null){
								recommandFragment.refresh(contentEntities);
							}
							if(!isRecommand&&contentEntities!=null){
								hotFragment.refresh(contentEntities);
							}							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						refreshCompled();
						break;
					case Data.FAILURE:
						Toast.makeText(context, "网络连接失败", Toast.LENGTH_LONG).show();
						connect.close();
						refreshCompled();
						break;
					case Data.TIMEOUT:
						Toast.makeText(context, "网络连接超时", Toast.LENGTH_LONG).show();
						connect.close();
						refreshCompled();
						break;
					}
				}
			};
			connect = new Connect(Data.getCon, Data.classification());
			connect.sendRequest(mhandler);
			break;

		default:
			break;
		}
	}
	/**
	 * 获取完数据
	 */
	public  void refreshCompled(){
		state = NONE;
		isRemark = false;
		reflashViewByState();
		TextView lastupdatetimeTextView = (TextView) header
				.findViewById(R.id.lastUpdateTime);
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy年MM月dd日    HH时mm分ss秒 ");
		Date curDate = new Date(System.currentTimeMillis());
		String time = format.format(curDate);
		lastupdatetimeTextView.setText(time);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		this.scrollState = scrollState;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItem = firstVisibleItem;
		
	}
    public void setContext(MainActivity context){
    	this.context = context;
    	System.out.println("context" + context);
    }

	public void setActivity(Activity activity) {
		// TODO Auto-generated method stub
		context = activity;
	}
	public void setFragment(RecommandFragment recommandFragment) {
		// TODO Auto-generated method stub
		this.recommandFragment = recommandFragment;
	}
	public void setFragment(HotFragment hotFragment) {
		// TODO Auto-generated method stub
		this.hotFragment = hotFragment;
	}
	public void isRecommad(Boolean isRecommand){
		this.isRecommand = isRecommand;
	}
	
}
