package com.huan.demomaster.activity.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.huan.demomaster.R;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.adapter.EditViewWithDeleteButton;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private Button btn_login;
	private EditViewWithDeleteButton et_username,et_password;
	private TextView tv_register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		et_username = (EditViewWithDeleteButton) findViewById(R.id.et_username);
		et_password = (EditViewWithDeleteButton) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_register.setOnClickListener(this);
	}
	
	/*public void login(final String username,final String password){
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
					UserDao.saveUserNamePassWord(LoginActivity.this, username, password);
					User user = JSON.parseObject(response.getData(), User.class);
					MyApp.instance.setUser(user);
					showToast("login success");
					LoginActivity.this.finish();
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
	}*/
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			showProgress("登录中...");
			String username = et_username.getText().toString();
			String password = et_password.getText().toString();
			//login(username,password);
			break;
		case R.id.tv_register:
			Intent intent = new Intent(this,RegisterActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
