package com.huan.demomaster.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.bean.Demo;

/**
 * 
 * @author CGQ
 * @Time 2016.11.29
 */
public class DemoListAdapter extends BaseAdapter {

	ArrayList<Demo> mList;
	Context mContext;
	LayoutInflater mInflater;
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public DemoListAdapter(Context mContext, List<Demo> mList) {
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.mList = (ArrayList<Demo>) mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_list_demo, null);

			viewHolder.tv_author = (TextView) convertView
					.findViewById(com.huan.demomaster.R.id.tv_author);
			viewHolder.tv_title = (TextView) convertView
					.findViewById(com.huan.demomaster.R.id.tv_title);
			viewHolder.tv_time = (TextView) convertView
					.findViewById(com.huan.demomaster.R.id.tv_time);
			viewHolder.iv_head = (ImageView) convertView
					.findViewById(com.huan.demomaster.R.id.iv_head);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Demo mLog = mList.get(position);
		viewHolder.tv_title.setText(mLog.getName());
		viewHolder.tv_author.setText(mLog.getAuthor().getNickName());
		viewHolder.tv_time.setText(formatter.format(mLog.getTime()));
		MyApp.imageLoader.displayImage(mLog.getHeadImageUrl(), viewHolder.iv_head);
		
		return convertView;
	}


	class ViewHolder {
		TextView tv_title,tv_author,tv_time;
		ImageView iv_head;
		
	}

}
