package quanta.iwant.fragment;

import java.util.concurrent.CopyOnWriteArrayList;



import com.example.quanta.iwant.R;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class RepostFragment extends Fragment{
	Context context;
	private String url;
	private LayoutInflater inflater;
	private ClipboardManager clipboard = null;
	EditText repost;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
		return inflater.inflate(R.layout.repost_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Activity activity = getActivity();
		super.onActivityCreated(savedInstanceState);
		repost = (EditText) activity.findViewById(R.id.repost);
		repost.setText("嘿，我发现了一个很适合我的礼物，送给我吧。"+url);
	}
	public void seturl(String url) {
		// TODO Auto-generated method stub
		this.url = url;
	}
	public void copy(View v){
		
		clipboard = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData textCd = ClipData.newPlainText(null,repost.getText().toString());
		clipboard.setPrimaryClip(textCd);
		if (clipboard.hasPrimaryClip()) {
			clipboard.getPrimaryClip().getItemAt(0).getText();
		}
	}

	public void setContext(Context context) {
		// TODO Auto-generated method stub
		this.context = context;
	}
}
