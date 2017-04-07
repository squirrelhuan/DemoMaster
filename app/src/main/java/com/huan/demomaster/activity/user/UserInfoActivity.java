package com.huan.demomaster.activity.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.bean.Response;
import com.huan.demomaster.bean.User;
import com.huan.demomaster.constances.Constants;
import com.huan.demomaster.constances.NetConstants;
import com.huan.demomaster.utils.ImageUtils;
import com.huan.demomaster.utils.UserDao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends BaseActivity implements OnClickListener {
	private static final String LOG_TAG = "CGQ";
	private ImageView iv_user_head;
	private TextView tv_username;
	private Button btn_logoff;
	LinearLayout ll_user_head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		init();
	}

	@Override
	public String getTitleText() {
		return "个人中心";
	}

	@Override
	public void onResume() {
		if (MyApp.instance.getUser() != null) {
			tv_username.setText(MyApp.instance.getUser().getNickName());
			MyApp.imageLoader.displayImage(MyApp.instance.getUser()
					.getHeadimageurl(), iv_user_head);
		} else {
			tv_username.setText("未登录");
			iv_user_head.setImageResource(R.drawable.ic_launcher);
		}
		super.onResume();
	}

	private void init() {
		tv_username = (TextView) findViewById(R.id.tv_username);
		iv_user_head = (ImageView) findViewById(R.id.iv_user_head);
		iv_user_head.setOnClickListener(this);
		btn_logoff = (Button) findViewById(R.id.btn_logoff);
		btn_logoff.setOnClickListener(this);

		ll_user_head = (LinearLayout) findViewById(R.id.ll_user_head);
		ll_user_head.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_user_head:
			/*
			 * Bundle bundle = new Bundle(); bundle.putInt("default_image",
			 * R.drawable.ease_default_avatar); bundle.putParcelable("uri",
			 * null); bundle.putString("remotepath",
			 * MyApp.getNetConstants().SERVER_URL + loginUser.getImageUrl());
			 * bundle.putString("uri_path", null); bundle.putString("localUrl",
			 * Constants.APP_PATH_PICTURE + "/headImage.png");
			 * bundle.putString("secret", "");
			 */
			// IntentUtil.jump(this, ImageDetailActivity.class, bundle);
			break;
		case R.id.ll_user_head:
			SelectPhotoPopupWindow();
			break;
		case R.id.btn_logoff:
			MyApp.instance.setUser(null);
			UserDao.saveUserNamePassWord(UserInfoActivity.this, "", "");
			showToast("注销成功！");
			UserInfoActivity.this.finish();
			break;
		default:
			break;
		}
	}

	// 获取返回结果
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (pop != null) {
			pop.dismiss();
		}
		Log.d(LOG_TAG, "onActivityResult: requestCode: " + requestCode
				+ ", resultCode: " + requestCode + ", data: " + data
				+ ",resultCode:" + resultCode);
		switch (requestCode) {
		// 如果是拍照
		case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
			Log.d(LOG_TAG, "CAPTURE_IMAGE");
			if (RESULT_OK == resultCode) {
				Log.d(LOG_TAG, "RESULT_OK");
				// Check if the result includes a thumbnail Bitmap
				if (data != null) {
					// 没有指定特定存储路径的时候
					Log.d(LOG_TAG,
							"data is NOT null, file on default position.");
					// 指定了存储路径的时候（intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);）
					// Image captured and saved to fileUri specified in the
					// Intent
					if (data.hasExtra("data")) {
						// Bitmap thumbnail = data.getParcelableExtra("data");
					}
				} else {
					startPhotoZoom(fileUri);
				}
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
			break;
		// 从相册选取
		case GALLERY_Photo_REQUEST_CODE:
			Log.d(LOG_TAG, "GALLERY");
			if (resultCode == RESULT_OK) {
				if (data != null) {
					Toast.makeText(this, "Image path:\n" + data.getData(),
							Toast.LENGTH_LONG).show();
					startPhotoZoom(data.getData());
				}
			} else if (resultCode == RESULT_CANCELED) {

			}
			break;
		case CROP_SMALL_PICTURE:
			if (data != null) {
				setImageToView(data);
			}
		default:
			break;
		}
	}

	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		fileUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}

	// 选择图片dialog
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	public static final int MEDIA_TYPE_AUDIO = 3;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int GALLERY_Photo_REQUEST_CODE = 300;
	private static final int CROP_SMALL_PICTURE = 5;

	// 选择照片的popupWindow
	private void SelectPhotoPopupWindow() {
		pop = new PopupWindow(this);
		View view = getLayoutInflater().inflate(
				R.layout.item_popupwindows_as_dialog, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button btn_camera = (Button) view
				.findViewById(R.id.item_popupwindows_camera);
		Button btn_file = (Button) view
				.findViewById(R.id.item_popupwindows_Photo);
		parent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.startAnimation(AnimationUtils.loadAnimation(
						UserInfoActivity.this, R.anim.scaling_big));
			}
		});
		// 拍照
		btn_camera.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				takePicture();
			}
		});
		// 文件选取
		btn_file.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				getPictureByGallery(UserInfoActivity.this);
			}
		});
		pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		ll_popup.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.scaling_big));
	}

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		File mediaStorageDir = new File(Constants.APP_PATH_CACHE_DIR);

		// File mediaStorageDir = null;
		try {
			// This location works best if you want the created images to be
			// shared
			// between applications and persist after your app has been
			// uninstalled.
			mediaStorageDir = new File(Constants.APP_PATH_PICTURE);

			Log.d(LOG_TAG, "Successfully created mediaStorageDir: "
					+ mediaStorageDir);

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(LOG_TAG, "Error in Creating mediaStorageDir: "
					+ mediaStorageDir);
		}

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				// 在SD卡上创建文件夹需要权限：
				// <uses-permission
				// android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
				File file = new File(mediaStorageDir.getPath() + "/.nomedia");// 用来屏蔽相册读取
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Log.d(LOG_TAG,
						"failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
				// return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else if (type == MEDIA_TYPE_AUDIO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "AUD_" + timeStamp + ".mp3");
		} else {
			return null;
		}

		return mediaFile;
	}

	/** 调用系统拍照 */
	private void takePicture() {
		Log.d(LOG_TAG, "Take Picture Button Click");
		// 利用系统自带的相机应用:拍照
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// create a file to save the image
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		// 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
		// set the image file name
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	/**
	 * 调用图库获取图片
	 * 
	 * @param context
	 *            必须是Activity
	 */
	public void getPictureByGallery(Activity context) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		context.startActivityForResult(intent, GALLERY_Photo_REQUEST_CODE);
	}

	protected void setImageToView(Intent data) {
		showProgress("正在处理图片...", false);
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			// photo = ImageUtils.toRoundBitmap(photo, fileUri); //
			// ���ʱ���ͼƬ�Ѿ��������Բ�ε���
			// photo = ImageUtils.savePhoto(photo, Constants.APP_PATH_PICTURE,
			// "")
			// iv_personal_icon.setImageBitmap(photo);
			uploadPic(photo);
		}
	}

	private void uploadPic(Bitmap bitmap) {
		String path = Constants.APP_PATH_PICTURE;
		String imagePath = ImageUtils.savePhoto(bitmap,
				Constants.APP_PATH_PICTURE,
				String.valueOf(System.currentTimeMillis()));
		Log.e("imagePath", imagePath + "");
		if (imagePath != null) {
			uploadFile(imagePath);
		} else {
			showToast("图片处理失败");
		}
	}

	/** 上传文件 */
	private void uploadFile(String path) {
		showProgress("正在上传头像...", false);
		RequestParams params = new RequestParams();
		try {
			File file = new File(path);
			params.put("file", file);
			params.put("username", UserDao.getUserName(this));
			params.put("password", UserDao.getPassWord(this));
			String postUrl = NetConstants.UPLOAD_File;
			Log.d("postUrl", postUrl);
			MyApp.getNet().postReq(postUrl, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							Response response = JSON.parseObject(new String(
									arg2), Response.class);
							hideProgress();
							if (response.isIsSuccess()) {
								User user = MyApp.instance.getUser();
								user.setHeadimageurl(response.getData());
								MyApp.instance.setUser(user);
								showToast("修改成功 ");
								onResume();
							} else {
								showToast(response.getDescription());
							}
							hideProgress();
						}

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							showToast("图片上传失败");
							hideProgress();
						}
					});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
