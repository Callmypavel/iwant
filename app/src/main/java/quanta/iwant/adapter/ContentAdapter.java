package quanta.iwant.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.ContentEntity;


import android.R.string;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentAdapter extends BaseAdapter {
	private ArrayList<ContentEntity> contentEntities;
	private Context context;
	private LayoutInflater layoutInflater;
	private int count;
	
	public ContentAdapter(LayoutInflater layoutInflater,ArrayList<ContentEntity> contentEntities){
		this.contentEntities = contentEntities;
		this.layoutInflater = layoutInflater;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contentEntities.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.content_item,null);
		}
		
		ContentEntity contentEntity = contentEntities.get(position);
		ImageView contentView = (ImageView) convertView.findViewById(R.id.contentView);
		TextView titleTextView = (TextView) convertView.findViewById(R.id.titleText);
		contentView.setImageBitmap(contentEntity.getBitmap());
		titleTextView.setText(contentEntity.getTitle());
		notifyDataSetChanged();
		
		return convertView;
	}
	
	public void refreshListView(ArrayList<ContentEntity> contentEntities) {
		System.out.println("内容实体数目"+contentEntities.size());
		this.contentEntities = contentEntities;
		this.notifyDataSetChanged();
		System.out.println("已通知更新 请祖国人民放心！");
	}

}
