package com.huan.demomaster.net;

import org.apache.http.HttpEntity;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie2;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

/**
 * 网络控制台
 */
public class NetControl {

	public final String TAG = getClass().getName();
	public static final String TAG_RELOGIN = "NETCONTROL_RELOGIN";

	public String token;
	private Context ctx;
	PersistentCookieStore cookie;
	private AsyncHttpClient client;

	public NetControl(Context ctx) {
		this.ctx = ctx;
		cookie = new PersistentCookieStore(ctx);
	}

	public void postJsonReq(String url, HttpEntity entity, HttpHandler handler) {
		createClient().post(ctx, url, entity, RequestParams.APPLICATION_JSON, handler);
	}

	public void postReq(String url, RequestParams params, AsyncHttpResponseHandler handler) {
		AsyncHttpClient client = createClient();
		client.post(url, params, handler);
	}

	public void postJsonParamsReq(String url, RequestParams params, HttpHandler handler) {
		AsyncHttpClient client = createClient();
		params.setUseJsonStreamer(true);// 使用json格式
		client.post(url, params, handler);
	}

	public void postJsonHttpEntity(String url, HttpEntity params, HttpHandler handler) {
		AsyncHttpClient client = createClient();
		client.post(ctx, url, params, "application/json", handler);
	}

	public RequestHandle getReq(String url, BinaryHttpResponseHandler handler) {
		return createClient().get(this.ctx, url, handler);
	}

	public void getReq(String url, HttpHandler handler) {
		createClient().get(url, handler);
	}

	public void getReq(String url, RequestParams params, HttpHandler handler) {
		createClient().get(url, params, handler);
	}
	public void getReq(String url, RequestParams params, AsyncHttpResponseHandler handler) {
		createClient().get(url, params, handler);
	}
	
	private AsyncHttpClient createClient() {
		if (client == null) {
			client = new AsyncHttpClient();
		}
		if (cookie != null)
			client.setCookieStore(cookie);
		return client;
	}

	// 登录成功后会赋值
	public void setToken(String token) {
		this.token = token;
		Cookie newcookie = new BasicClientCookie2("token", token);
		cookie.addCookie(newcookie);
	}

	public String getToken() {
		return token;
	}

}
