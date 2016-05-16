package quanta.iwant.adapter;

import java.util.ArrayList;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.FriendsEntity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListAdapter extends BaseAdapter {
	private ArrayList<FriendsEntity> friendsEntities;
	private LayoutInflater layoutInflater;
	private TextView nameTextView;
	private ImageView headImageView;
	public FriendListAdapter(LayoutInflater layoutInflater,ArrayList<FriendsEntity> friendsEntities) {
		// TODO Auto-generated constructor stub
		this.friendsEntities = friendsEntities;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.friendlist_item,null);
		}
		nameTextView = (TextView) convertView.findViewById(R.id.name);
		headImageView = (ImageView) convertView.findViewById(R.id.head);
		nameTextView.setText(friendsEntities.get(position).getname());
		headImageView.setImageBitmap(friendsEntities.get(position).gethead());
		return convertView;
	}

}
