package com.huan.demomaster.net;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 协议解析返回的通用处理类
 */
public abstract class HttpHandler extends AsyncHttpResponseHandler {

	public final String TAG = "HttpHandler";

	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

		Log.d("apps", "status:" + statusCode + "," + new String(responseBody));
		try {
			JSONObject obj = new JSONObject(new String(responseBody));
			if (obj.has("Status")) {
				String Status = obj.getString("Status");
				if ("1".equals(Status)) {
					if (!obj.isNull("Data")) {
						obj = obj.getJSONObject("Data");
						onReqSuccess(obj.toString());
					} else {
						onReqSuccess("");
					}

				} else if ("0".equals(Status)) {
					if (!obj.isNull("Memo")) {
						String Memo = obj.getString("Memo");
						onReqFail(statusCode, Memo);
						if (!obj.isNull("Data")) {
							getData(obj.getJSONObject("Data").toString());
						}
					} else {
						onReqFail(statusCode, "");
					}
				} else {
					// 发送通用错误处理
					onReqFail(statusCode, "error：" + Status);
				}
			} else {
				if (statusCode == 200) {
//					onReqSuccess(new String(responseBody));
				} else {
					onReqFail(statusCode, "error:" + new String(responseBody));
				}
			}
		} catch (JSONException jsone) {
			onFailure(-1, headers, responseBody, jsone);
		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
		if (responseBody == null) {
			responseBody = "网络异常,请稍后重试".getBytes();
		}
		String errorStr = new String(responseBody);
		onReqFail(statusCode, errorStr);
	}

	// //自定义封装， 传入的只是 data 中的数据
	protected abstract void onReqSuccess(String data);

	// 请求的网路异常
	protected abstract void onReqFail(int statusCode, String responseBody);

	public void getData(String data) {

	}
}
