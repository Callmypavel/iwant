package quanta.iwant.activity;

import java.util.ArrayList;
import java.util.List;
import quanta.iwant.adapter.DrawerAdapter;
import quanta.iwant.entity.ContentEntity;
import quanta.iwant.entity.Data;
import quanta.iwant.entity.UserEntity;
import quanta.iwant.fragment.BaseFragment;
import quanta.iwant.fragment.HotFragment;
import quanta.iwant.fragment.RecommandFragment;
import quanta.iwant.network.Connect;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanta.iwant.R;

public class MainActivity extends FragmentActivity  {
	//抽屉布局
	DrawerLayout drawerLayout;
	//页码游标
	ImageView cursor;
	//游标移动距离
	int delta;
	//游标动画
	Animation animation;
	//fragment集合
	List<BaseFragment> fragments;
	//装载fragment的viewpager
	ViewPager mViewPager;
	//连接网络类，用以连接网络
	Connect connect;
	//handler，处理信息
	Handler mhandler;
	//内容实体集合
	ArrayList<ContentEntity> contentEntities;
	//礼物推荐fragment
	public BaseFragment recommandFragment;
	//热门礼物fragment
	public BaseFragment hotFragment;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		initHandler();
		initView();	
	}

    /*
     * 初始化handler，开始获取消息
     */
	private void initHandler() {
		// TODO Auto-generated method stub
		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case Data.ContentSUCCESS:
					Toast.makeText(MainActivity.this, "有新的消息", Toast.LENGTH_LONG).show();
					break;
				case Data.FAILURE:
					//关闭连接
					connect.close();
					Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_LONG).show();
					break;
				case Data.TIMEOUT:
					//关闭连接
					connect.close();
					Toast.makeText(MainActivity.this, "超时", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};
		//连接类型设置为获取消息
		connect = new Connect(Data.getMsg, UserEntity.userid);
		connect.sendRequest(mhandler);
	}

	/*
	 * 初始化控件
	 */
	private void initView() {
		//将当前页码置为0，分类设为推荐，一般类
		Data.currentItem = 0;
		Data.classification1 = "recommand";
		Data.classification2 = "general";
		//初始化抽屉中的listview
		ListView lv = (ListView) findViewById(R.id.list);
		//初始化分类信息
		ArrayList<String> datas = new ArrayList<String>() {
			{	
				add("赠送对象");
				add("男朋友");
				add("女朋友");
				add("父母");
				add("闺蜜");
				add("赠送原因");
				add("生日");
				add("情人节");
				add("圣诞节");
				add("毕业礼物");
				add("接受者个人性格");
				add("小清新");
				add("技术宅");
				add("重口味");
				add("运动狂");
			}
		};

		LayoutInflater layoutInflater = getLayoutInflater();
		//初始化抽屉布局
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		//初始化抽屉适配器
		DrawerAdapter myadapter = new DrawerAdapter(datas, layoutInflater,drawerLayout,this);
		//使控件不能获取焦点
		lv.setItemsCanFocus(false);
		//设置适配器
		lv.setAdapter(myadapter);
		
		
		//初始化游标和动画
		cursor = (ImageView) findViewById(R.id.cursor);
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		delta = dm.widthPixels / 2;
		Matrix mtMatrix = new Matrix();
		mtMatrix.postTranslate(0, 0);
		cursor.setImageMatrix(mtMatrix);
		
		//初始化recommandfragment
		recommandFragment = new RecommandFragment();
		recommandFragment.init(this);
		
		//初始化hotfragment
		hotFragment = new HotFragment();
		hotFragment.init(this);
		
		//初始化viewpager
		fragments = new ArrayList<BaseFragment>();
		fragments.add(recommandFragment);
		fragments.add(hotFragment);
		
		FragmentStatePagerAdapter mPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return fragments.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return fragments.get(arg0);
			}
		};
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mViewPager.setCurrentItem(arg0);
				// TODO Auto-generated method stub
				if (Data.currentItem == arg0) {
				} else if (Data.currentItem > arg0) {
					animation = new TranslateAnimation(delta, 0, 0, 0);
					System.out.println("改了分类");
					Data.classification1="hot";
				} else if (Data.currentItem < arg0) {
					animation = new TranslateAnimation(0, delta, 0, 0);
					Data.classification1="recommand";
				}
				Data.currentItem = arg0;
				animation.setFillAfter(true);// True:停留在停止位置
				animation.setDuration(300);
				cursor.startAnimation(animation);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		
	}

	/*
	 * 处理热门事件点击事件
	 */
	public void hot(View v) {
		if (Data.currentItem == 0) {
			mViewPager.setCurrentItem(1);
			animation = new TranslateAnimation(0, delta, 0, 0);
		}
		animation.setFillAfter(true);// True:停在终止位置
		animation.setDuration(300);
		cursor.startAnimation(animation);
		//改变页码和分类
		Data.currentItem = 1;
		Data.classification1="hot";
	}

	/*
	 *  处理推荐点击事件
	 */
	public void recommand(View v) {
		if (Data.currentItem == 1) {
			mViewPager.setCurrentItem(0);
			animation = new TranslateAnimation(delta, 0, 0, 0);
		}
		animation.setFillAfter(true);// True:停在终止位置
		animation.setDuration(300);
		cursor.startAnimation(animation);
		Data.currentItem = 0;
		Data.classification1="recommand";
	}
	/*
	 * 处理分类按键点击事件
	 */
	public void classification(View v) {
		drawerLayout.openDrawer(Gravity.LEFT);
	}

	/*
	 * 处理用户信息按键点击事件
	 */
	public void enterUser(View v) {
		//跳转至用户界面
		Intent i = new Intent();
		i.setClass(this, UsersActivity.class);
		startActivity(i);
	}
}
