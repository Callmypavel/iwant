package quanta.iwant.adapter;

import java.util.ArrayList;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.CommentsEntity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentsAdapter extends BaseAdapter {
	ArrayList<CommentsEntity> commentsEntities ;
	LayoutInflater layoutInflater;
	public CommentsAdapter(LayoutInflater inflater,ArrayList<CommentsEntity> commentsEntities){
		this.layoutInflater = inflater;
		this.commentsEntities = commentsEntities;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commentsEntities.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommentsEntity commentsEntity = commentsEntities.get(position);
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.comments_item,null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView comment = (TextView) convertView.findViewById(R.id.comment);
		ImageView head = (ImageView) convertView.findViewById(R.id.head);
		name.setText(commentsEntity.getName());
		comment.setText(commentsEntity.getComment());
		head.setImageBitmap(commentsEntity.getHead());
		name.setVisibility(View.VISIBLE);
		comment.setVisibility(View.VISIBLE);
		head.setVisibility(View.VISIBLE);
 		return convertView;
	}
	public void refreshListView(ArrayList<CommentsEntity> commentsEntities) {
		this.commentsEntities = commentsEntities;
		this.notifyDataSetChanged();
	}

}
