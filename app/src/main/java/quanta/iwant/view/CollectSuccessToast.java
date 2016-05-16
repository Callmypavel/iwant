package quanta.iwant.view;


import com.example.quanta.iwant.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CollectSuccessToast extends Toast {

	public CollectSuccessToast(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

	}

	public static Toast makeText(Context context, int resId, int duration) {
		Toast result = new Toast(context);

		// 获得LayoutInflater
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 获得View
		View layout = inflater.inflate(R.layout.collect_success, null);
		ImageView imageView = (ImageView) layout
				.findViewById(R.id.collectSuccess);
		imageView.setImageResource(resId);
		result.setView(layout);
		result.setGravity(Gravity.CENTER, 0, 0);
		result.setDuration(duration);
		return result;
	}

}
