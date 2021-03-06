package com.huan.demomaster.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.huan.demomaster.R;
import com.huan.demomaster.SwipeMenuListView.SwipeMenuListView;
import com.huan.demomaster.activity.topic.TopicDetailActivity;
import com.huan.demomaster.adapter.MyTopicListAdapter;
import com.huan.demomaster.bean.Topic;
import com.huan.demomaster.fragment.base.BaseFragment;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MainFragment3 extends BaseFragment implements 
		OnItemClickListener, OnClickListener, OnItemLongClickListener {

	SwipeMenuListView listView;
	private PullToRefreshLayout mPullToRefreshLayout;
	MyTopicListAdapter adapter;
	List<Topic> mList = new ArrayList<Topic>();
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
		//getNetData();
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
	}
    /*public void getNetData(){
    *//*	for (int i = 0; i < 5; i++) {
			Topic talk = new Topic();
			talk.setAuthor(MyApp.instance.getUser());
			talk.setTitle("内测招募啦，快啦报名吧！");
			talk.setContent("招募美容");
			talk.setTime(new Date());
			mList.add(talk);
		}
    	adapter = new MyTalkListAdapter(getActivity(), mList);
		listView.setAdapter(adapter);*//*
    	RequestParams params = new RequestParams();
		//params.put("name", "6");
		MyApp.getNet().getReq(NetConstants.getTopic, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				System.out.println("fail");
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String data = new String(arg2);
				List<Topic> mList1 = JSON.parseArray(data, Topic.class);
				mList.clear();
				mList.addAll(mList1);
				if (mList != null) {
					if(adapter!=null){
						adapter.notifyDataSetChanged();
					}else{
						adapter = new MyTopicListAdapter(getActivity(), mList);
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
		Intent intent = new Intent(getActivity(), TopicDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Topic", mList.get(position));
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
