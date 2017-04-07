package com.huan.demomaster.activity.news;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.bean.News;

import java.util.Date;

public class NewsAddActivity extends BaseActivity implements OnClickListener {

	private EditText et_title, et_url;
	private RadioGroup radioGroup;
	private boolean isOriginal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_add);
		init();
	}

	@Override
	public String getTitleText() {
		return "发布主题";
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void init() {
		et_title = (EditText) findViewById(R.id.et_title);
		et_url = (EditText) findViewById(R.id.et_url);
		setRightTextView("添加");
		setRightTextViewClicked(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_title.getText()==null||et_title.getText().toString().equals("")){
					showToast("标题不能为空！");
					return;
				}
				if(et_url.getText()==null||et_url.getText().toString().equals("")){
					showToast("url不能为空！");
					return;
				}
				News news = new News();
				news.setTitle(et_title.getText().toString());
				news.setUrl(et_url.getText().toString());
				news.setAuthor(MyApp.instance.getUser());
				news.setTime(new Date());
				news.setOriginal(isOriginal);
				//submitNews(news);
			}
		});
		radioGroup = (RadioGroup) findViewById(R.id.rg_isOriginal);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.rb_02){
					isOriginal = true;
				}else{
					isOriginal = false;
				}
			}
		});
	}
/*
	public void submitNews(News news) {
		showProgress("提交中...");
		RequestParams params = new RequestParams();
		params.put("username", UserDao.getUserName(this));
		params.put("password", UserDao.getPassWord(this));
		params.put("data", JSON.toJSONString(news).toString());
		MyApp.getNet().postReq(NetConstants.AddNews, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						hideProgress();
						showToast("add fail");
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						Response response = JSON.parseObject(new String(arg2),
								Response.class);
						hideProgress();
						if (response.isIsSuccess()) {
							User user = JSON.parseObject(response.getData(), User.class);
							MyApp.instance.setUser(user);
							showToast("add success");
							NewsAddActivity.this.finishUI();
						} else {
							showToast(response.getDescription());
						}
					}
				});
	}*/

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_user_head:
			break;

		default:
			break;
		}
	}
}
