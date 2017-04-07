package com.huan.demomaster.activity.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.SwipeMenuListView.SwipeMenuListView;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.activity.SearchActivity;
import com.huan.demomaster.adapter.DemoListAdapter;
import com.huan.demomaster.adapter.EditViewWithDeleteButton;
import com.huan.demomaster.bean.Demo;
import com.huan.demomaster.bean.Plate;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class DemoListActivity extends BaseActivity implements OnItemClickListener, OnClickListener, OnItemLongClickListener {

	private EditViewWithDeleteButton ev_search;
	private PullToRefreshLayout mPullToRefreshLayout;
	SwipeMenuListView listView;
	private DemoListAdapter adapter;
	private Plate plate;
	List<Demo> mList = new ArrayList<Demo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_list);
		init();
	}
	@Override
	public String getTitleText() {
		Bundle bundle = getIntent().getExtras();
		plate = (Plate) bundle.getSerializable("Plate");
		return plate.getName()+"板块";
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
				//getNetData();
				mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				//getNetData();
				mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		});
		
		
		ev_search = (EditViewWithDeleteButton) findViewById(R.id.ev_search);
		ev_search.setOnClickListener(this);
		
		listView = (SwipeMenuListView) findViewById(R.id.list_view);
		/*for(int i=0;i<30;i++){
			Demo demo = new Demo();
			demo.setId(i);
			demo.setName("demo"+i);
			demo.setAuthor(new User());
			mList.add(demo);
		}
		adapter = new DemoListAdapter(DemoListActivity.this, mList);
		listView.setAdapter(adapter);*/
		//getNetData();
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
	}
/*
	public void getNetData() {
		RequestParams params = new RequestParams();
		params.put("plateid", plate.getId());
		MyApp.getNet().getReq(NetConstants.getDemoListByPlateid, params,
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
								adapter = new DemoListAdapter(DemoListActivity.this, mList);
								listView.setAdapter(adapter);
							}
						}
					}
				});
	}*/

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ev_search:
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_logoff:
			MyApp.instance.setUser(null);
			showToast("注销成功！");
			DemoListActivity.this.finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(DemoListActivity.this, DemoDetailActivity.class);
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
