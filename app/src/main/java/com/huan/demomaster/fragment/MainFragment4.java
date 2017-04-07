package com.huan.demomaster.fragment;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.user.LoginActivity;
import com.huan.demomaster.activity.user.MyCollectionActivity;
import com.huan.demomaster.activity.user.MyDownLoadActivity;
import com.huan.demomaster.activity.user.MyNewsActivity;
import com.huan.demomaster.activity.user.MySettingActivity;
import com.huan.demomaster.activity.user.UserInfoActivity;
import com.huan.demomaster.fragment.base.BaseFragment;
import com.huan.demomaster.utils.UserDao;

public class MainFragment4 extends BaseFragment implements OnItemClickListener,
		OnClickListener, OnItemLongClickListener {

	private ImageView iv_user_head;
	private TextView tv_username,tv_count_news;
	private LinearLayout ll_my_news,ll_my_download,ll_my_collection,ll_my_setting;

	@Override
	public View setContentUI(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_main_04, container, false);
	}

	@Override
	public void onResume() {
		if (MyApp.instance.getUser() != null) {
			tv_username.setText(MyApp.instance.getUser().getNickName());
			MyApp.imageLoader.displayImage(MyApp.instance.getUser()
					.getHeadimageurl(), iv_user_head);
			tv_count_news.setText(MyApp.instance.getUser().getNews().size()+"");
		} else {
			tv_username.setText("未登录");
			iv_user_head.setImageResource(R.drawable.ic_launcher);
			if (UserDao.getPassWord(getActivity())!=null&&!UserDao.getPassWord(getActivity()).equals("")) {
				/*final String username = UserDao.getUserName(getActivity());
				final String password = UserDao.getPassWord(getActivity());
				RequestParams params = new RequestParams();
				params.put("username", username);
				params.put("password", password);
				MyApp.getNet().getReq(NetConstants.LOGIN, params,
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								// TODO Auto-generated method stub
								Response response = JSON.parseObject(
										new String(arg2), Response.class);
								hideProgress();
								if (response.isIsSuccess()) {
									UserDao.saveUserNamePassWord(getActivity(),
											username, password);
									User user = JSON.parseObject(
											response.getData(), User.class);
									MyApp.instance.setUser(user);
									onResume();
									showToast("login success");
								} else {
									showToast(response.getDescription());
								}
							}

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								showToast("login fail...");
								hideProgress();
							}
						});*/
			}
		}
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void init() {
		tv_username = (TextView) findViewById(R.id.tv_username);
		iv_user_head = (ImageView) findViewById(R.id.iv_user_head);
		iv_user_head.setOnClickListener(this);
		tv_count_news = (TextView) findViewById(R.id.tv_count_news);
		
		ll_my_news = (LinearLayout) findViewById(R.id.ll_my_news);
		ll_my_news.setOnClickListener(this);
		ll_my_download = (LinearLayout) findViewById(R.id.ll_my_download);
		ll_my_download.setOnClickListener(this);
		ll_my_collection = (LinearLayout) findViewById(R.id.ll_my_collection);
		ll_my_collection.setOnClickListener(this);
		ll_my_setting = (LinearLayout) findViewById(R.id.ll_my_setting);
		ll_my_setting.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		if (MyApp.instance.getUser() == null) {
			intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			return;
		}
		switch (v.getId()) {
		case R.id.iv_user_head:
			intent = new Intent(getActivity(), UserInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_my_news:
			intent = new Intent(getActivity(), MyNewsActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_my_download:
			intent = new Intent(getActivity(), MyDownLoadActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_my_collection:
			intent = new Intent(getActivity(), MyCollectionActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_my_setting:
			intent = new Intent(getActivity(), MySettingActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
