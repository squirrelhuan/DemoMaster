package com.huan.demomaster.activity.user;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.adapter.EditViewWithDeleteButton;
import com.huan.demomaster.bean.Response;
import com.huan.demomaster.bean.User;
import com.huan.demomaster.constances.NetConstants;
import com.huan.demomaster.utils.UserDao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	private Button btn_register;
	private EditText et_username,et_password,et_mobile,et_mobile_code;
	@Override
	public String getTitleText() {
		return "用户注册";
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
	}

	private void init() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		et_mobile = (EditText) findViewById(R.id.et_mobile);
		et_mobile_code = (EditText) findViewById(R.id.et_mobile_code);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(this);
	}
	
	public void login(final String username,final String password){
		showProgress("登录中...");
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("password", password);
		MyApp.getNet().getReq(NetConstants.LOGIN, params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				Response response = JSON.parseObject(new String(arg2),Response.class);
				hideProgress();
				if(response.isIsSuccess()){
					UserDao.saveUserNamePassWord(RegisterActivity.this, username, password);
					User user = JSON.parseObject(response.getData(), User.class);
					MyApp.instance.setUser(user);
					showToast("login success");
					RegisterActivity.this.finish();
				}else{
					showToast(response.getDescription());
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				showToast("login fail");
				hideProgress();
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			showProgress("注册中...");

			String username = et_username.getText().toString();
			String password = et_password.getText().toString();
			String mobile = et_mobile.getText().toString();
			String mobile_code = et_mobile_code.getText().toString();
			//login(username,password);
			break;

		default:
			break;
		}
	}
}
