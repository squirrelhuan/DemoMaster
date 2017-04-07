package com.huan.demomaster.adapter;

import com.huan.demomaster.pullrefresh.Pullable;

import android.content.Context;
import android.widget.GridView;

public class PullGridView extends GridView implements Pullable{

	public PullGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canPullDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPullUp() {
		// TODO Auto-generated method stub
		return false;
	}

}
