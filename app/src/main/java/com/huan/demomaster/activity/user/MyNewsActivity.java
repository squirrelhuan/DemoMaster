package com.huan.demomaster.activity.user;

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
import com.huan.demomaster.activity.news.NewsDetailActivity;
import com.huan.demomaster.adapter.MySimpleListAdapter;
import com.huan.demomaster.bean.News;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MyNewsActivity extends BaseActivity implements 
		OnItemClickListener, OnClickListener, OnItemLongClickListener {

	SwipeMenuListView listView;
	private PullToRefreshLayout mPullToRefreshLayout;
	MySimpleListAdapter adapter;
	List<News> mList = new ArrayList<News>();
	@Override
	public String getTitleText() {
		return "我的主题";
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_news);
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
		
		listView = (SwipeMenuListView) findViewById(R.id.list_view);
		mList = MyApp.instance.getUser().getNews();
		for (News news:mList) {
			news.setAuthor(MyApp.instance.getUser());
		}
		adapter = new MySimpleListAdapter(MyNewsActivity.this, mList);
		listView.setAdapter(adapter);
		//getNetData();
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(MyNewsActivity.this, NewsDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("News", mList.get(position));
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
