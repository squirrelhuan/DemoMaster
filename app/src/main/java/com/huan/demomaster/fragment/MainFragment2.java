package com.huan.demomaster.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.demo.DemoDetailActivity;
import com.huan.demomaster.activity.demo.DemoListActivity;
import com.huan.demomaster.adapter.MySimpleGridAdapter;
import com.huan.demomaster.bean.Plate;
import com.huan.demomaster.constances.NetConstants;
import com.huan.demomaster.fragment.base.BaseFragment;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout;
import com.huan.demomaster.pullrefresh.PullToRefreshLayout.OnRefreshListener;
import com.huan.demomaster.pullrefresh.PullableGridView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainFragment2 extends BaseFragment implements OnItemClickListener,
		OnClickListener, OnItemLongClickListener {

	PullableGridView GridView;
	MySimpleGridAdapter adapter;
	private PullToRefreshLayout mPullToRefreshLayout;
	List<Plate> mList = new ArrayList<Plate>();

	@Override
	public View setContentUI(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_main_02, container, false);
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
				mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				getNetData();
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				mPullToRefreshLayout
						.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				getNetData();
			}
		});

		GridView = (PullableGridView) findViewById(R.id.grid_view);
		getNetData();
		GridView.setOnItemClickListener(this);
		GridView.setOnItemLongClickListener(this);
	}

	public void getNetData() {
		RequestParams params = new RequestParams();
		MyApp.getNet().getReq(NetConstants.getDemoPlate, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						System.out.println("fail");
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String data = new String(arg2);
						List<Plate> mList1 = JSON.parseArray(data, Plate.class);
						mList.clear();
						mList.addAll(mList1);
						if (mList != null) {
							if (adapter != null) {
								adapter.notifyDataSetChanged();
							} else {
								adapter = new MySimpleGridAdapter(
										getActivity(), mList);
								GridView.setAdapter(adapter);
							}
						}
					}
				});
	}

	@Override
	public void onClick(View arg0) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent intent = new Intent(getActivity(), DemoListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("Plate", mList.get(position));
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
