package quanta.iwant.view;


import com.example.quanta.iwant.R;

import quanta.iwant.fragment.BaseFragment;
import quanta.iwant.fragment.HotFragment;
import quanta.iwant.fragment.RecommandFragment;
import quanta.iwant.network.GetRunnable;
import quanta.iwant.network.JsonUtil;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class CollectionLayout extends RelativeLayout {
	protected static final int SUCCESS = 1;
	protected static final int FAILURE = 2;
	protected static final int TIMEOUT = 3;
	JsonUtil jsonUtil;
	GetRunnable getRunnable;
	View header;//顶部布局文件
	int deleteWidth;//顶部布局文件高度
	int firstVisibleItem;//当前第一个可见的item的位置
	int scrollState;//当前滚动状态
	boolean isRemark;//是否在最顶端按下
	int startx;//按下的x值
    final int open = 0;//开启状态
	final int close = 1;//关闭状态
	int state = close;//当前状态
	String classification;
	Handler handler;
	Activity context;
	BaseFragment baseFragment;
	RecommandFragment recommandFragment;
	HotFragment hotFragment;
	Boolean isRecommand;
	View delete;
	

	public CollectionLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	 public CollectionLayout(Context context) {
	 super(context);
	 // TODO Auto-generated constructor stub
	 }
	

	//初始化界面，添加顶部布局文件
	private void initView(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		delete = inflater.inflate(R.id.delete,null);
		measureView(delete);
		deleteWidth = delete.getMeasuredWidth();
		rightPadding(-deleteWidth);
	}
	/***
	 * 通知父布局占用的宽高
	 * @param view
	 */
	private void measureView(View view){
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if(p==null){
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
		}
		int width ;
		int height= ViewGroup.getChildMeasureSpec(0, 0, p.height);;
		int tempWidth = p.width;
		if(tempWidth > 0){
			width = MeasureSpec.makeMeasureSpec(tempWidth, MeasureSpec.EXACTLY
					);
		}else{
			width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(width, height);
	}
	/**
	 * 设置header布局上边距
	 * @param rightPadding
	 */
	private void rightPadding(int rightPadding){
		delete.setPadding(delete.getPaddingLeft(), delete.getPaddingTop(),
				rightPadding, header.getPaddingBottom());
		delete.invalidate();
	}	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			startx = (int) ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:		
			getParent().requestDisallowInterceptTouchEvent(true);
			onMove(ev);
			if(state == open){
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		}
		return super.onTouchEvent(ev);
	}
	private void onMove(MotionEvent ev){
		int tempx = (int) ev.getX();
		int deltax = tempx - startx;
		int rightPadding = deltax - deleteWidth;
		if(deltax > deleteWidth + 30){
			rightPadding(50);
			state = open;
		}
		rightPadding(rightPadding);
	}
}
