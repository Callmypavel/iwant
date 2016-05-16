package quanta.iwant.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.example.quanta.iwant.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class WelcomeActivity extends Activity {
	//一开始即进入的界面
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		new Timer().schedule(new TimerTask() {  
            public void run() {  
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));  
                finish();  
            }  
        }, 1000);
	}
}
