package com.huan.demomaster.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.adapter.MySimpleListAdapter.ViewHolder;
import com.huan.demomaster.bean.Demo;
import com.huan.demomaster.bean.News;
import com.huan.demomaster.bean.Reply;
import com.huan.demomaster.bean.Topic;

/**
 * 
 * @author CGQ
 * @Time 2016.11.29
 */
public class TopicDetailAdapter extends BaseAdapter {

	Topic topic;
	Context mContext;
	LayoutInflater mInflater;
	public final static int SEND_LEFT = 0;
	public final static int SEND_RIGHT = 1;
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public TopicDetailAdapter(Context mContext, Topic topic) {
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.topic = topic;
	}

	@Override
	public int getCount() {
		return topic.getReplys().size()+1;
	}

	@Override
	public Object getItem(int position) {
		if(position!=0){
			return topic.getReplys().get(position-1);
		}else{
			return topic.getReplys().get(position);
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (0 == position) {
			return SEND_LEFT;// 消息类型在左边
		} else {
			return SEND_RIGHT;// 消息类型在右边
		}
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

			switch (getItemViewType(position)) {
			case SEND_LEFT:
				convertView = mInflater.inflate(R.layout.item_detail_topic0, null);
				break;
			case SEND_RIGHT:
				convertView = mInflater.inflate(R.layout.item_detail_topic, null);
				break;
			default:
				break;
			}
			viewHolder.tv_author = (TextView) convertView.findViewById(com.huan.demomaster.R.id.tv_author);
			viewHolder.tv_content = (TextView) convertView.findViewById(com.huan.demomaster.R.id.tv_content);
			viewHolder.tv_time = (TextView) convertView.findViewById(com.huan.demomaster.R.id.tv_time);
			viewHolder.iv_head = (ImageView) convertView.findViewById(com.huan.demomaster.R.id.iv_head);
			viewHolder.cb_zan = (CheckBox) convertView.findViewById(com.huan.demomaster.R.id.cb_zan);
			viewHolder.cb_zan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					((BaseActivity) mContext).showToast(isChecked ? "赞" : "取消赞");
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (position > 0) {
			Reply mLog = topic.getReplys().get(position-1);
			viewHolder.tv_content.setText(mLog.getContent());
			viewHolder.tv_author.setText(mLog.getAuthor().getNickName());
			viewHolder.tv_time.setText(formatter.format(mLog.getTime()));
			MyApp.imageLoader.displayImage(mLog.getAuthor().getHeadimageurl(), viewHolder.iv_head);
		}else{
			viewHolder.tv_content.setText(topic.getContent());
			viewHolder.tv_author.setText(topic.getAuthor().getNickName());
			viewHolder.tv_time.setText(formatter.format(topic.getTime()));
			MyApp.imageLoader.displayImage(topic.getAuthor().getHeadimageurl(), viewHolder.iv_head);
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv_content, tv_author, tv_time, tv_title;
		ImageView iv_head;
		CheckBox cb_zan;

	}

}
