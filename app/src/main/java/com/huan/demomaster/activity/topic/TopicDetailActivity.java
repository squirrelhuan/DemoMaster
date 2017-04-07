package com.huan.demomaster.activity.topic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.SwipeMenuListView.SwipeMenuListView;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.activity.SearchActivity;
import com.huan.demomaster.activity.demo.DemoDetailActivity;
import com.huan.demomaster.adapter.DemoListAdapter;
import com.huan.demomaster.adapter.EditViewWithDeleteButton;
import com.huan.demomaster.adapter.TopicDetailAdapter;
import com.huan.demomaster.bean.Demo;
import com.huan.demomaster.bean.News;
import com.huan.demomaster.bean.Plate;
import com.huan.demomaster.bean.Response;
import com.huan.demomaster.bean.Topic;
import com.huan.demomaster.bean.User;
import com.huan.demomaster.constances.NetConstants;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout.OnRefreshListener;
import com.huan.demomaster.utils.UserDao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class TopicDetailActivity extends BaseActivity implements
		 OnClickListener, OnItemLongClickListener {

	private PullToRefreshLayout mPullToRefreshLayout;
	SwipeMenuListView listView;
	private TopicDetailAdapter adapter;
	private Topic topic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_detail);
		init();
	}

	@Override
	public String getTitleText() {
		Bundle bundle = getIntent().getExtras();
		topic = (Topic) bundle.getSerializable("Topic");
		return topic.getTitle();
	}

	@Override
	public void onResume() {

		super.onResume();
	}

	private void init() {

		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				getNetData();
				mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				getNetData();
				mPullToRefreshLayout
						.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		});

		listView = (SwipeMenuListView) findViewById(R.id.list_view);
		getNetData();
		listView.setOnItemLongClickListener(this);
	}

	public void getNetData() {
		RequestParams params = new RequestParams();
		params.put("id", topic.getId());
		MyApp.getNet().getReq(NetConstants.getTopicById, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						System.out.println("fail");
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String data = new String(arg2);
						Topic topic1 = JSON.parseObject(data, Topic.class);
						topic = null;
						topic = topic1;
						if (topic != null) {
							if (adapter != null) {
								adapter.notifyDataSetChanged();
							} else {
								adapter = new TopicDetailAdapter(
										TopicDetailActivity.this, topic);
								listView.setAdapter(adapter);
							}
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_logoff:
			MyApp.instance.setUser(null);
			showToast("注销成功！");
			TopicDetailActivity.this.finish();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
