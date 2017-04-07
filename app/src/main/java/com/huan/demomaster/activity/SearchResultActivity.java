package com.huan.demomaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.huan.demomaster.R;
import com.huan.demomaster.SwipeMenuListView.SwipeMenuListView;
import com.huan.demomaster.activity.demo.DemoDetailActivity;
import com.huan.demomaster.adapter.DemoListAdapter;
import com.huan.demomaster.adapter.EditViewWithDeleteButton;
import com.huan.demomaster.bean.Demo;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener, OnItemLongClickListener {

	private TextView tv_search;
	private ImageView common_title_left;
	private EditViewWithDeleteButton ev_search;
	private PullToRefreshLayout mPullToRefreshLayout;
	SwipeMenuListView listView;
	private DemoListAdapter adapter;
	List<Demo> mList = new ArrayList<Demo>();
	private String searchKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_search_result);
		init();
	}

	private void init() {
		Bundle bundle = this.getIntent().getExtras();
		searchKey = bundle.getString("SearchKey");

		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				//getNetData();
				mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				//getNetData();
				mPullToRefreshLayout
						.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		});

		ev_search = (EditViewWithDeleteButton) findViewById(R.id.ev_search);
		ev_search.setOnClickListener(this);
		ev_search.setText(searchKey);
		tv_search = (TextView) findViewById(R.id.tv_search);
		tv_search.setOnClickListener(this);
		common_title_left = (ImageView) findViewById(R.id.common_title_left);
		common_title_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishUI();
			}
		});

		listView = (SwipeMenuListView) findViewById(R.id.list_view);
		//getNetData();
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
	}

	/*public void getNetData() {
		RequestParams params = new RequestParams();
		params.put("name", searchKey);
		MyApp.getNet().getReq(NetConstants.getDemoListByName, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						System.out.println("fail");
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String data = new String(arg2);
						List<Demo> mList1 = JSON.parseArray(data, Demo.class);
						mList.clear();
						mList.addAll(mList1);
						if (mList != null) {
							if (adapter != null) {
								adapter.notifyDataSetChanged();
							} else {
								adapter = new DemoListAdapter(
										SearchResultActivity.this, mList);
								listView.setAdapter(adapter);
							}
						}
					}
				});
	}*/

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_search:
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.ev_search:
			Intent intent2 = new Intent(this, SearchActivity.class);
			startActivity(intent2);
			this.finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent intent = new Intent(SearchResultActivity.this,
				DemoDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Demo", mList.get(position));
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
