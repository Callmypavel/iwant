package quanta.iwant.activity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.ContentEntity;
import quanta.iwant.entity.Data;
import quanta.iwant.entity.DataBaseOperator;
import quanta.iwant.entity.UserEntity;
import quanta.iwant.fragment.ContentFragment;
import quanta.iwant.fragment.RepostFragment;
import quanta.iwant.network.Connect;


import quanta.iwant.view.CollectSuccessToast;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ContentActivity extends FragmentActivity{
	//游标移动的距离
	int delta;
	//连接类，用于连接网络
	Connect connect;
	//viewpager，用于装载fragment
	ViewPager mViewPager;
	//游标
	ImageView cursor;
	//游标动画
	Animation animation;
	//当前页面页码
	int currentItem = 0;
	ContentEntity contentEntity;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.content);
		//initdata();
		initView();
	}
	
	/*
	 * 从数据库中获取内容信息并初始化数据
	 */
	private void initdata() {
		String[] strings = new String[]{"title","picture","content","contentId","url"};
		//初始化数据库操作类
		DataBaseOperator dataBaseOperator = new DataBaseOperator(this, "iwant.db");
		//从数据库中获得内容实体
		contentEntity = dataBaseOperator.getContentEntities(strings, "contentId=?",  new String[]{Data.contentId}, null, null, null).get(0);
	}
	
    /*
     * 初始化控件
     */
	private void initView() {

		//初始化游标
		cursor = (ImageView) findViewById(R.id.content_cursor);
		
		//根据屏幕分辨率设置游标宽度
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		delta = dm.widthPixels / 2;
		Matrix mtMatrix = new Matrix();
		mtMatrix.postTranslate(0, 0);
		cursor.setImageMatrix(mtMatrix);
		
		//初始化contentFragment
		final ContentFragment contentFragment = new ContentFragment();
		contentFragment.setcontentEntity(contentEntity);
		
		//初始化repostFragment
		final RepostFragment repostFragment = new RepostFragment();
		repostFragment.seturl(contentEntity.getUrl());
		repostFragment.setContext(this);
		
		//初始化适配器
		FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {		
			@Override
			public int getCount() {
				return 2;
			}
			
			@Override
			public Fragment getItem(int arg0) {
				//根据页码得到fragment
				if(arg0==0){
				return contentFragment;
				}
				if (arg0 == 1) {
				return repostFragment;
				}
				return null;
			}
		};
		
		//初始化并设置viewpager
		mViewPager = (ViewPager) findViewById(R.id.content_viewpager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mViewPager.setCurrentItem(arg0);
				if (currentItem == arg0) {
				} else if (currentItem > arg0) {
					animation = new TranslateAnimation(delta, 0, 0, 0);
				} else if (currentItem < arg0) {
					animation = new TranslateAnimation(0, delta, 0, 0);
				}
				currentItem = arg0;
				animation.setFillAfter(true);// True:停留在停止位置
				animation.setDuration(300);
				cursor.startAnimation(animation);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	/*
	 * 处理点击内容字样事件
	 */
	public void content(View v){
		//将页面置为第一页，初始化动画
		if (currentItem == 1) {
			mViewPager.setCurrentItem(0);
			animation = new TranslateAnimation(delta, 0, 0, 0);
		}
		animation.setFillAfter(true);// True:停在终止位置
		animation.setDuration(300);
		//开始动画
		cursor.startAnimation(animation);
		currentItem = 0;
	}
	/*
	 * 处理点击转发字样事件
	 */
	public void repost(View v){
		//将页面置为第二页，初始化动画
		if (currentItem == 0) {
			mViewPager.setCurrentItem(1);
			animation = new TranslateAnimation(0, delta, 0, 0);
		}
		animation.setFillAfter(true);// True:停在终止位置
		animation.setDuration(300);
		//开始动画
		cursor.startAnimation(animation);
		currentItem = 1;
	}
	/*
	 * 处理点击返回按键事件
	 */
	public void back(View v){
		this.finish();
	}
	/*
	 * 处理点击收藏按键事件
	 */
	public void content_collect(View v){
		Handler mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Data.SUCCESS:
					//添加收藏成功
					//关闭线程,显示收藏成功
					connect.close();
					CollectSuccessToast.makeText(ContentActivity.this, R.drawable.collect, Toast.LENGTH_SHORT).show();	
					break;
				case Data.FAILURE:
					//连接失败
					//关闭线程
					connect.close();
					Toast.makeText(ContentActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();
					break;
				case Data.TIMEOUT:
					//连接超时
					//关闭线程
					connect.close();
					Toast.makeText(ContentActivity.this, "网络连接超时", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};
		//建立连接，类型为添加收藏
		connect = new Connect(Data.addCol,UserEntity.userid,contentEntity.getcontentId());
		connect.sendRequest(mhandler);
	}
	public void sendComment(View v){
		//初始化评论编辑框
		EditText comment = (EditText) findViewById(R.id.comment_edit);
		Handler mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Data.SUCCESS:
					//发送评论成功
					//关闭线程
					connect.close();
					Toast.makeText(ContentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();				
					break;
				case Data.FAILURE:
					//网络连接失败
					//关闭线程
					connect.close();
					Toast.makeText(ContentActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();		
					break;
				case Data.TIMEOUT:
					//连接超时
					//关闭线程
					connect.close();
					Toast.makeText(ContentActivity.this, "网络连接超时", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};
		//建立连接，类型为评论
		connect = new Connect(Data.comment,UserEntity.userid,comment.getText().toString(),contentEntity.getcontentId());
		connect.sendRequest(mhandler);
	}
}
	
