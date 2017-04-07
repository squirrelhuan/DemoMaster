package com.huan.demomaster.activity.user;

import org.apache.http.Header;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.adapter.ToggleButton;
import com.huan.demomaster.adapter.ToggleButton.OnToggleChanged;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MySettingActivity extends BaseActivity implements 
		 OnClickListener {

	LinearLayout ll_only_wifi;// wifi模式
	LinearLayout ll_app_update;// 自动更新
	LinearLayout ll_download_path;// 存储路径
	LinearLayout ll_help_and_suggest;// 帮助与建议
	LinearLayout ll_about_app;// 关于
	ToggleButton button_only_wifi, button_app_update;
	@Override
	public String getTitleText() {
		return "设置";
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_setting);
		init();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	public void init() {
		ll_only_wifi = (LinearLayout) findViewById(R.id.ll_only_wifi);
		ll_only_wifi.setOnClickListener(this);
		ll_download_path = (LinearLayout) findViewById(R.id.ll_download_path);
		ll_download_path.setOnClickListener(this);
		ll_app_update = (LinearLayout) findViewById(R.id.ll_app_update);
		ll_app_update.setOnClickListener(this);
		ll_help_and_suggest = (LinearLayout) findViewById(R.id.ll_help_and_suggest);
		ll_help_and_suggest.setOnClickListener(this);
		ll_about_app = (LinearLayout) findViewById(R.id.ll_about_player);
		ll_about_app.setOnClickListener(this);
		
		button_only_wifi = (ToggleButton) findViewById(R.id.button_only_wifi);
		button_only_wifi.toggle(MyApp.getPreferencesService().getBoolean(
				"IsNetWifi",false));
		button_only_wifi.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				MyApp.getPreferencesService().save("IsNetWifi", on);
			}
		});
		button_app_update = (ToggleButton) findViewById(R.id.button_app_update);
		button_app_update.toggle(MyApp.getPreferencesService().getBoolean("UpDateApp",true));
		button_app_update.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				MyApp.getPreferencesService().save("UpDateApp", on);
			}
		});
		
	}
   /* public void getNetData(){
    	RequestParams params = new RequestParams();
		params.put("name", "6");
		//params.put("index", 1);
		MyApp.getNet().getReq(NetConstants.DemoNews, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				System.out.println("fail");
				
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String data = new String(arg2);
				List<News> mList1 = JSON.parseArray(data, News.class);
				mList.clear();
				mList.addAll(mList1);
				if (mList != null) {
					if(adapter!=null){
						adapter.notifyDataSetChanged();
					}else{
						adapter = new MySimpleListAdapter(MyNewsActivity.this, mList);
						listView.setAdapter(adapter);
					}
				}
			}
		});
    }*/
	@Override
	public void onClick(View arg0) {
		
	}

}
