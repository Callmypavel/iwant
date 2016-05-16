package quanta.iwant.adapter;

import java.util.ArrayList;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.ContentEntity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectAdapter extends BaseAdapter {
	private ArrayList<ContentEntity> ContentEntities;
	private LayoutInflater layoutInflater;
	
	public CollectAdapter(LayoutInflater layoutInflater,ArrayList<ContentEntity> ContentEntities){
		this.ContentEntities = ContentEntities;
		this.layoutInflater = layoutInflater;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ContentEntities.size();
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
			convertView = layoutInflater.inflate(R.layout.collect_item,null);
		}
		
		ContentEntity ContentEntity = ContentEntities.get(position);
		ImageView collectView = (ImageView) convertView.findViewById(R.id.collectView);
		TextView titleTextView = (TextView) convertView.findViewById(R.id.collect_title);
		collectView.setImageBitmap(ContentEntity.getBitmap());
		titleTextView.setText(ContentEntity.getTitle());
		
		return convertView;
	}
	public void refreshListView(ArrayList<ContentEntity> ContentEntities) {
		this.ContentEntities = ContentEntities;
		this.notifyDataSetChanged();
	}
}
