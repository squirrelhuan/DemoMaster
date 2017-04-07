package com.huan.demomaster.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.SwipeMenuListView.SwipeMenuListView;
import com.huan.demomaster.activity.demo.DemoDetailActivity;
import com.huan.demomaster.activity.news.NewsDetailActivity;
import com.huan.demomaster.adapter.MySimpleListAdapter;
import com.huan.demomaster.bean.News;
import com.huan.demomaster.bean.Plate;
import com.huan.demomaster.constances.NetConstants;
import com.huan.demomaster.fragment.base.BaseFragment;
import com.huan.demomaster.net.HttpHandler;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout.OnRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainFragment1 extends BaseFragment implements 
		OnItemClickListener, OnClickListener, OnItemLongClickListener {

	SwipeMenuListView listView;
	private PullToRefreshLayout mPullToRefreshLayout;
	MySimpleListAdapter adapter;
	List<News> mList = new ArrayList<News>();
	@Override
	public View setContentUI(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_main_01, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void init() {
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
				mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		});
		
		listView = (SwipeMenuListView) findViewById(R.id.list_view);
		getNetData();
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
	}
    public void getNetData(){
    	RequestParams params = new RequestParams();
		params.put("name", "6");
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
						adapter = new MySimpleListAdapter(getActivity(), mList);
						listView.setAdapter(adapter);
					}
				}
			}
		});
    }
	@Override
	public void onClick(View arg0) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
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
