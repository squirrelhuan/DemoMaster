package com.huan.demomaster.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.MainActivity;
import com.huan.demomaster.bean.News;
import com.huan.demomaster.bean.Plate;
import com.huan.demomaster.bean.Topic;
import com.huan.demomaster.utils.IntentUtil;

/**
 * 
 * @author CGQ
 * @Time 2016.11.29
 */
public class MyTopicListAdapter extends BaseAdapter {

	ArrayList<Topic> mList;
	Context mContext;
	LayoutInflater mInflater;
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public MyTopicListAdapter(Context mContext, List<Topic> mList) {
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.mList = (ArrayList<Topic>) mList;
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
			convertView = mInflater.inflate(R.layout.item_simple_task_list, null);

			viewHolder.iv_author_head = (ImageView) convertView
					.findViewById(com.huan.demomaster.R.id.iv_author_head);
			viewHolder.tv_type = (TextView) convertView
					.findViewById(com.huan.demomaster.R.id.tv_type);
			viewHolder.tv_title = (TextView) convertView
					.findViewById(com.huan.demomaster.R.id.tv_title);
			viewHolder.tv_author = (TextView) convertView
					.findViewById(com.huan.demomaster.R.id.tv_author);
			viewHolder.tv_time = (TextView) convertView
					.findViewById(com.huan.demomaster.R.id.tv_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Topic mLog = mList.get(position);
		//MyApp.imageLoader.displayImage(mLog
		//		.getAuthor().getHeadimageurl(), viewHolder.iv_author_head);
		//viewHolder.tv_type.setText(mLog.getType()==3?"内测":"讨论");
	//	viewHolder.tv_type.setBackground(mContext.getResources().getDrawable(mLog.isOriginal()?R.drawable.tv_bg_stroke_red:R.drawable.tv_bg_stroke_green));
		viewHolder.tv_title.setText(mLog.getTitle());
		viewHolder.tv_author.setText(mLog.getAuthor().getNickName());
		viewHolder.tv_time.setText(formatter.format(mLog.getTime()));
		
		return convertView;
	}


	class ViewHolder {
		TextView tv_type,tv_title,tv_author,tv_time;
		ImageView iv_author_head;
	}

}
