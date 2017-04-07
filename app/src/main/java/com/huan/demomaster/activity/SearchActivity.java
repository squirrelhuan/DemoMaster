package com.huan.demomaster.activity;

import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.adapter.EditViewWithDeleteButton;
import com.huan.demomaster.adapter.FlowLayout;
import com.huan.demomaster.utils.UserDao;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchActivity extends BaseActivity implements OnClickListener {

	private TextView tv_search;
	private ImageView common_title_left,iv_clear_tags;
	private EditViewWithDeleteButton ev_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_search);
		init();
	}

	private void init() {
		tv_search = (TextView) findViewById(R.id.tv_search);
		tv_search.setOnClickListener(this);
		iv_clear_tags = (ImageView) findViewById(R.id.iv_clear_tags);
		iv_clear_tags.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserDao.saveSearchTags(SearchActivity.this, new ArrayList<String>());
				initHistoryViews();
			}
		});
		common_title_left = (ImageView) findViewById(R.id.common_title_left);
		common_title_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishUI();
			}
		});

		TextWatcher textWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		};
		ev_search = (EditViewWithDeleteButton) findViewById(R.id.ev_search);
		ev_search.addTextChangedListener(textWatcher);
		list_host = new ArrayList<String>();
		list_host.add("android");
		list_host.add("ios");
		list_host.add("html5");
		initHostViews();
		initHistoryViews();
	}

	FlowLayout fl_search_host;
	FlowLayout fl_search_history;

	private void initHostViews() {
		// TODO Auto-generated method stub
		fl_search_host = (FlowLayout) findViewById(R.id.fl_search_host);
		fl_search_host.removeAllViews();
		MarginLayoutParams lp = new MarginLayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 10;
		lp.rightMargin = 10;
		lp.topMargin = 8;
		lp.bottomMargin = 8;
		for (int i = 0; i < list_host.size(); i++) {
			TextView view = new TextView(this);
			view.setPadding(15, 5, 15, 5);
			view.setText(list_host.get(i));
			// exceptions_list.get(i).getSimpleName()
			view.setTextColor(Color.WHITE);
			view.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.tv_bg_stroke_gray));

			OnClickListener mClickListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					search(((TextView) v).getText().toString());
				}
			};
			view.setOnClickListener(mClickListener);
			fl_search_host.addView(view, lp);
		}
	}

	private List<String> list_history;
	private List<String> list_host;
	private int tags_size = 20;

	private void initHistoryViews() {
		list_history = UserDao.getSearchTags(this);
		fl_search_history = (FlowLayout) findViewById(R.id.fl_search_history);
		fl_search_history.removeAllViews();
		MarginLayoutParams lp = new MarginLayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 10;
		lp.rightMargin = 10;
		lp.topMargin = 8;
		lp.bottomMargin = 8;
		for (int i = 0; i < list_history.size(); i++) {
			TextView view = new TextView(this);
			view.setPadding(15, 5, 15, 5);
			view.setText(list_history.get(i));
			view.setTextColor(Color.WHITE);
			view.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.tv_bg_stroke_gray));
			OnClickListener mClickListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					search(((TextView) v).getText().toString());
				}
			};
			view.setOnClickListener(mClickListener);
			fl_search_history.addView(view, lp);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_search:
			search(ev_search.getText().equals("")?(list_history.size()==0?list_host.get(0):list_history.get(0)):ev_search.getText());
			break;
		default:
			break;
		}
	}

	private void search(String text) {
		if (!list_host.contains(text)) {
			if (list_history.contains(text)) {// 包含则删除重新置顶
				for (int i = 0; i < tags_size; i++) {
					if (list_history.get(i).equals(text)) {
						list_history.remove(i);
						list_history.add(0, text);
						break;
					}
				}
			} else{
				if(list_history.size()>=tags_size){
					list_history.remove(tags_size - 1);
				}
				list_history.add(0, text);
			}
		}
		UserDao.saveSearchTags(this, list_history);
		Intent intent = new Intent(this, SearchResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("SearchKey", text);
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
	}
}
