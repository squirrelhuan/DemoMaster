package com.huan.demomaster.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.huan.demomaster.bean.News;
import com.huan.demomaster.bean.User;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



/**
 * 用户数据操作类
 */
public class UserDao {

	private static String UserDaoTable = "zrsoftHuanWei";
	private static String UserInfoObject = "UserInfoObject";

	/**
	 * 获得用户名
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserName(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		String username = preferences.getString("UserName", "");
		return username;
	}

	/**
	 * 获得密码
	 * 
	 * @param context
	 * @return
	 */
	public static String getPassWord(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		String password = preferences.getString("PassWord", "");
		return password;
	}
	
	/**
	 * 是否记住密码
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getRememberPassWord(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		boolean isRemember = preferences.getBoolean("RememberPassWord", false);
		return isRemember;
	}

	/**
	 * 保存是否保存密码
	 * 
	 * @param context
	 * @param username
	 * @param password
	 */
	public static void saveRememberPassWord(Context context, boolean isRemember) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("RememberPassWord", isRemember);
		editor.commit();
	}
	
	/**
	 * 保存用户名密码
	 * 
	 * @param context
	 * @param username
	 * @param password
	 */
	public static void saveUserPassWord(Context context, String password) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("PassWord", password);
		editor.commit();
	}

	/**
	 * 保存用户名密码
	 * 
	 * @param context
	 * @param username
	 * @param password
	 */
	public static void saveUserNamePassWord(Context context, String username, String password) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("UserName", username);
		editor.putString("PassWord", password);
		editor.commit();
	}

	/**
	 * 保存用户信息
	 * 
	 * @param context
	 * @param userinfo
	 */
	public static void saveUserInfo(Context context, String userinfo) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("UserInfo", userinfo);
		editor.commit();
	}

	/**
	 * 保存用户信息对象
	 * 
	 * @param context
	 * @param userinfo
	 */
	public static void saveUserInfo(Context context, User userinfo) {
		PreferencesService service = new PreferencesService(context);
		service.saveObject(UserInfoObject, userinfo);
	}

	/**
	 * 获取用户信息对象
	 * 
	 * @param context
	 * @return
	 */
	public static User getUserInfoModel(Context context) {
		User model = null;
		PreferencesService service = new PreferencesService(context);
		model = (User) service.deSerialization(UserInfoObject);
		return model;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserInfo(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		return preferences.getString("UserInfo", "");
	}
	
	/**
	 * 保存搜索记录
	 * 
	 * @param context
	 * @return
	 */
	public static boolean saveSearchTags(Context context,List<String> mlist) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("SearchTags", mlist==null?"[]":JSON.toJSONString(mlist));
		editor.commit();
		return true;
	}
	
	/**
	 * 获得搜索记录
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getSearchTags(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(UserDaoTable, Activity.MODE_PRIVATE);
		String text = preferences.getString("SearchTags", null);
		List<String> mList = null;
		if(text!=null){
			mList = JSON.parseArray(text, String.class);
		}else{
			mList = new ArrayList<String>();
		}
		return mList;
	}
}
