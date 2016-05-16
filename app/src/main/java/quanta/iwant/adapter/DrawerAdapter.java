package quanta.iwant.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.quanta.iwant.R;

import quanta.iwant.activity.MainActivity;


import quanta.iwant.entity.Data;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DrawerAdapter extends BaseAdapter {
	private TextView parentTextView;
	private TextView childTextView1;
	private TextView childTextView2;
	private TextView childTextView3;
	private TextView childTextView4;
	private LayoutInflater layoutInflater;
	private ArrayList<String> list;
	private DrawerLayout drawerLayout;
	private MainActivity context;
	public DrawerAdapter(ArrayList<String> list,LayoutInflater layoutInflater,DrawerLayout drawerLayout,MainActivity context) {
		// TODO Auto-generated constructor stub
		this.drawerLayout = drawerLayout;
		this.list = list;
		this.layoutInflater = layoutInflater; 
		this.context = context;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size()/5;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.side_item,null);
		}
		parentTextView = (TextView) convertView.findViewById(R.id.Parentitem);
		childTextView1 = (TextView) convertView.findViewById(R.id.Childitem1);
		childTextView2 = (TextView) convertView.findViewById(R.id.Childitem2);
		childTextView3 = (TextView) convertView.findViewById(R.id.Childitem3);
		childTextView4 = (TextView) convertView.findViewById(R.id.Childitem4);
		parentTextView.setText(list.get(position*5));
		childTextView1.setText(list.get(position*5+1));
		childTextView2.setText(list.get(position*5+2));
		childTextView3.setText(list.get(position*5+3));
		childTextView4.setText(list.get(position*5+4));
		childTextView1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					//点击男朋友
					Data.classification2="boyfriend";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				case 1:
					//点击生日
					Data.classification2="birthday";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				case 2:
					//点击小清新
					Data.classification2="indiepop";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				}
			}
		});
		childTextView2.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					//女朋友
					Data.classification2="girlfriend";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				case 1:
					//情人节
					Data.classification2="valuntine";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				case 2:
					//技术宅
					Data.classification2="geek";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				}
			}
		});
		childTextView3.setOnClickListener(new View.OnClickListener() {
	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					//父母
					Data.classification2="parent";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				case 1:
					//圣诞节
					Data.classification2="chrismas";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				case 2:
					//重口味
					Data.classification2="hardcore";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				}
			}
		});
		childTextView4.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					//闺蜜
					Data.classification2="bestie";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				case 1:
					//毕业礼物
					Data.classification2="graduation";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				case 2:
					//运动狂
					Data.classification2="sports";
					drawerLayout.closeDrawer(Gravity.LEFT);
					if(Data.currentItem==0){
						context.recommandFragment.refreshByclassification();
					}else{
						context.hotFragment.refreshByclassification();
					}
					
				}
			}
		});
		return convertView;
	}

}
