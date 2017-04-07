package com.huan.demomaster;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;

import com.huan.demomaster.bean.App;
import com.huan.demomaster.bean.User;
import com.huan.demomaster.constances.Constants;
import com.huan.demomaster.utils.DialogUtils;
import com.huan.demomaster.utils.PreferencesService;
import com.huan.demomaster.utils.ToastUtils;
import com.huan.demomaster.utils.XUtils;
import com.huan.mylog.MyLog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

public class MyApp extends Application {
	public static String AppId = "a51f0fcbc53b417a8d090da3b29b192c";
	public static String LOG_TAG = "CGQ";
	private static ToastUtils toastUtils;
	public static ImageLoader imageLoader;
	public static MyApp instance;
	public static int versionCode;

	private static PreferencesService preferencesService;
	private User user;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		toastUtils = new ToastUtils(this);

		MyLog myLog = new MyLog(this);
		myLog.initialization();
		
		initImageLoader(this);
		initDir();
	}

	/*public void updateApp(final Context context) {
		RequestParams params = new RequestParams();
		getVersionCode();
		MyApp.getNet().getReq(NetConstants.Update, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						Response response = JSON.parseObject(new String(arg2),
								Response.class);
						if (response.isIsSuccess()) {
							App app = JSON.parseObject(response.getData(),
									App.class);
							if (versionCode < app.getVersionCode()) {
								showUpdateAppDialog(context, app);
							}
						}
					}
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
					}
				});
	}*/

	private static void showUpdateAppDialog(final Context context, final App app) {
		DialogUtils.getInstance().showPopUpWindow(context,"检测到软件有新的版本，是否立即更新？", null, new OnClickListener() {
			@Override
			public void onClick(View v) {
				XUtils.xUtilsHttpUtilDonLoadFile(context,app.getUrl(),Constants.APP_PATH_DOWNLOAD);
			}
		});
	}

	// 获取本地的版本号
	private int getVersionCode() {
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void initImageLoader(Context context) {
		// File cacheDir = StorageUtils.getOwnCacheDirectory(getContext(),
		// Constants.APP_PATH_CACHE_DIR);
		// cacheDir.mkdirs();

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisc(false) // 设置下载的图片是否缓存在SD卡中
		.build();
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.defaultDisplayImageOptions(options);
		// config.diskCache(new UnlimitedDiskCache(cacheDir));
		// Logger.d("test", "cacheDir = " + cacheDir.getAbsolutePath());
		config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app
		ImageLoader.getInstance().init(config.build());
		imageLoader = ImageLoader.getInstance();
		initDir();
		initPreferencesService();
	}

	// 创建文件夹
	private void initDir() {
		makeDir(Constants.APP_PATH_DOWNLOAD);
		makeDir(Constants.APP_PATH_DOWNLOAD_HTML);
		makeDir(Constants.APP_PATH_PICTURE);
	}

	private void makeDir(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static MyApp getInstance() {
		return instance;
	}

	private void initPreferencesService() {
		preferencesService = new PreferencesService(getContext());
	}

	public static PreferencesService getPreferencesService() {
		return preferencesService = new PreferencesService(getContext());
	}

	public static Context getContext() {
		return instance.getApplicationContext();
	}

	public static Resources getRes() {
		return getContext().getResources();
	}

	public ToastUtils getToastUtils() {
		return toastUtils;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
