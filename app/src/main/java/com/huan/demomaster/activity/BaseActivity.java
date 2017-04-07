package com.huan.demomaster.activity;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;

public abstract class BaseActivity extends FragmentActivity {

	protected static final String TAG = BaseActivity.class.getName();
	public ProgressDialog progressDialog;
	MyApp app;
	ToastUtils toastUtils;

	// public ImageLoader imageLoader;

	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ViewUtils.inject(this);
		app = (MyApp) MyApp.getInstance();
		toastUtils = app.getToastUtils();
		// imageLoader = ImageLoader.getInstance();
		initTitle();
	};

	public TextView title_txt_right;
	public TextView title_txt;
	private ImageView iv_title_left;
	private ImageView iv_title_right;

	public void initTitle() {
		LinearLayout common_title_layout = (LinearLayout) findViewById(R.id.common_title_layout);
		if (common_title_layout != null) {
			if (getTitleText() == null) {
				common_title_layout.setVisibility(View.GONE);
				return;
			}
			title_txt = (TextView) findViewById(R.id.common_title);
			title_txt.setText(getTitleText());
			iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
			iv_title_left.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finishUI();
				}
			});
			
			iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
			iv_title_right.setVisibility(View.GONE);
			iv_title_right.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showMenu(v);
				}
			});
			if (getTitleLeftHide()) {
				iv_title_left.setVisibility(View.GONE);
			}
			title_txt_right = (TextView) findViewById(R.id.tv_title_right);
			title_txt_right.setVisibility(View.GONE);
			title_txt_right.setText("" + getTitleRightText());
			if (getTitleRightText()!=null||getTitleRightText().equals("")) {
				title_txt_right.setVisibility(View.GONE);
			}
			title_txt_right.setOnClickListener(getTitleRightClick());
		}
	}
    public void showRightTextView(){
    	title_txt_right.setVisibility(View.VISIBLE);
    	iv_title_right.setVisibility(View.GONE);
    }
    public void setRightTextView(String text){
    	title_txt_right.setVisibility(View.VISIBLE);
    	title_txt_right.setText(text);
    	iv_title_right.setVisibility(View.GONE);
    }
    public void setRightTextViewClicked(OnClickListener listener){
    	title_txt_right.setOnClickListener(listener);
    }
    public void showRightImageView(){
    	title_txt_right.setVisibility(View.GONE);
    	iv_title_right.setVisibility(View.VISIBLE);
    }
	public void showMenu(View v){
		
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void finishUI() {
		finish();
	}
	
	public void showToast(String str) {
		toastUtils.showToast(str);
	}

	public void showProgress(String msg) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
		}
		progressDialog.setMessage(msg);
		progressDialog.setCancelable(true);
		progressDialog.show();
	}

	public void showProgress(String msg, boolean flag) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
		}
		progressDialog.setMessage(msg);
		progressDialog.setCancelable(flag);
		progressDialog.setCanceledOnTouchOutside(flag);
		progressDialog.show();
	}
	
	public void hideProgress() {
		if (progressDialog != null && progressDialog.isShowing())
			progressDialog.hide();
	}
	
	public void hideLeftButton(){
		iv_title_left.setVisibility(View.GONE);
	}
	
	public String getTitleText() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getTitleLeftHide() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getTitleRightText() {
		return "";
	}

	public OnClickListener getTitleRightClick() {
		return null;

	}
}
