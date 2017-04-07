package com.huan.demomaster.constances;


import android.os.Environment;

public class Constants{
	public static String BASE_FILE_PATH= Environment.getExternalStorageDirectory() + "/DemoMaster";

	public final static String APP_PATH_DOWNLOAD = BASE_FILE_PATH + "/download";

	public final static String APP_PATH_DOWNLOAD_HTML = BASE_FILE_PATH + "/html";

	public final static String APP_PATH_CACHE_DIR = BASE_FILE_PATH + "/cache";

	public final static String APP_PATH_PICTURE = BASE_FILE_PATH + "/picture";

	public final static String APP_PATH_VIDEO = BASE_FILE_PATH + "/video";

	public final static String HIDE_BASE_FILE_PATH = Environment.getExternalStorageDirectory() + "/.DemoMaster";

	public final static String HIDE_APP_PATH_CACHE_DIR = HIDE_BASE_FILE_PATH + "/cache";

	public interface Power {
		String SignIn = "签到";
		String SignOut = "签退";
		String Notice = "公告";
		String PersonManager = "人员管理";
		String PersonDanAn = "人员档案";
		String PersonKaoQin = "人员考勤";
		String PersonLocation = "人员定位";
		String PersonTongJi = "人员统计";
		String PersonList = "通讯录";
		String PersonKaoHe = "现场考核";
		String MapManager = "地图管理";
		String MapPerson = "人员定位";
//		String MapPerson = "人员地图";
		String MapEvent = "事件地图";
		String MapParts = "部件地图";
		String EventReport = "事件上报";
		String EventManager = "事件管理";
		String DaiChuLi = "待处理";
		String MyLog = "工作日报";
		String PartsReport = "部件上报";
		String PartsAdd = "新增部件";
		String PartsManager = "部件管理";

	}
	

}
