package com.huan.demomaster.activity.news;

import org.apache.http.Header;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.bean.News;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsDetailActivity extends BaseActivity implements OnClickListener {

	private WebView webview_news;
	private News news;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_details);
		init();
	}
	@Override
	public String getTitleText() {
		Bundle bundle = getIntent().getExtras();
		news = (News) bundle.getSerializable("News");
		return news.getTitle();
	}
	@Override
	public void onResume() {
		
		super.onResume();
	}

	private void init() {
		webview_news = (WebView) findViewById(R.id.webview_news);
		 //WebView加载web资源
		webview_news.loadUrl(news.getUrl());
	        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		webview_news.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	             view.loadUrl(url);
	            return true;
	        }
	       });
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			showProgress("登录中...");
			break;
		case R.id.btn_logoff:
			MyApp.instance.setUser(null);
			showToast("注销成功！");
			NewsDetailActivity.this.finish();
			break;
		default:
			break;
		}
	}
}
