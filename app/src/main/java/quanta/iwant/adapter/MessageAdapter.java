package quanta.iwant.adapter;

import java.util.ArrayList;

import com.example.quanta.iwant.R;

import quanta.iwant.entity.MessageEntity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MessageAdapter extends BaseAdapter {
	private ArrayList<MessageEntity> MessageEntities;
	private LayoutInflater layoutInflater;
	private int count;
	
	public MessageAdapter(LayoutInflater layoutInflater,ArrayList<MessageEntity> MessageEntities){
		this.MessageEntities = MessageEntities;
		this.layoutInflater = layoutInflater;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated metho
		return MessageEntities.size();
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
			convertView = layoutInflater.inflate(R.layout.message_item,null);
		}
		
		MessageEntity MessageEntity = MessageEntities.get(position);
		ImageView MessageView = (ImageView) convertView.findViewById(R.id.messageview);
		TextView titleTextView = (TextView) convertView.findViewById(R.id.messagetitle);
		TextView contentTextView = (TextView) convertView.findViewById(R.id.messageContent);
		MessageView.setImageBitmap(MessageEntity.getHead());
		titleTextView.setText(MessageEntity.getName());
		contentTextView.setText(MessageEntity.getMessage());
		notifyDataSetChanged();
		
		return convertView;
	}
	public void refreshListView(ArrayList<MessageEntity> MessageEntities) {
		this.MessageEntities = MessageEntities;
		this.notifyDataSetChanged();
	}

}
