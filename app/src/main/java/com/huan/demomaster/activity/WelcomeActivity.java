package com.huan.demomaster.activity;

import com.huan.demomaster.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class WelcomeActivity extends Activity {
	private int time = 3;
	private TextView tv_time;

	private Handler handler;
	private Runnable Crunnable = new Runnable() {
		@Override
		public void run() {
			if (time == 0) {
				handler.removeCallbacks(Crunnable);
				Intent intent = new Intent(WelcomeActivity.this,
						MainActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}else{
				tv_time.setText(time+"");
				time--;
				handler.postDelayed(Crunnable, 1000);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		tv_time = (TextView) findViewById(R.id.tv_time);
		handler = new Handler(); // 当计时结束,跳转至主界面
		handler.postDelayed(Crunnable, 0);
	}
}
