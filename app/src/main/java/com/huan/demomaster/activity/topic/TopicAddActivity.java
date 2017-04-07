package com.huan.demomaster.activity.topic;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.bean.News;
import com.huan.demomaster.bean.Response;
import com.huan.demomaster.bean.User;
import com.huan.demomaster.constances.NetConstants;
import com.huan.demomaster.utils.UserDao;
import com.huan.demomaster.view.richtext.InterceptLinearLayout;
import com.huan.demomaster.view.richtext.RichTextEditor;
import com.huan.demomaster.view.richtext.RichTextFileUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TopicAddActivity extends BaseActivity implements OnClickListener {
	private final int REQUEST_CODE_CAPTURE_CAMEIA = 100;
	private final int REQUEST_CODE_PICK_IMAGE = 200;
	private File mCameraImageFile;// 照相机拍照得到的图片
	private EditText et_title;
	private RadioGroup radioGroup;
	private boolean isOriginal;
	private LinearLayout line_rootView, line_addImg;
	private InterceptLinearLayout line_intercept;
	private RichTextEditor richText;
	private RichTextFileUtils mFileUtils;
	private boolean isKeyBoardUp, isEditTouch;// 判断软键盘的显示与隐藏

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_add);
		init();
	}

	@Override
	public String getTitleText() {
		return "发布主题";
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void init() {
		et_title = (EditText) findViewById(R.id.et_title);
		setRightTextView("添加");
		setRightTextViewClicked(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_title.getText()==null||et_title.getText().toString().equals("")){
					showToast("标题不能为空！");
					return;
				}
				
				News news = new News();
				news.setTitle(et_title.getText().toString());
				//news.setUrl(et_url.getText().toString());
				news.setAuthor(MyApp.instance.getUser());
				news.setTime(new Date());
				news.setOriginal(isOriginal);
				submitNews(news);
			}
		});
		radioGroup = (RadioGroup) findViewById(R.id.rg_isOriginal);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.rb_02){
					isOriginal = true;
				}else{
					isOriginal = false;
				}
			}
		});
		
		mFileUtils = new RichTextFileUtils(this);
		line_addImg = (LinearLayout) findViewById(R.id.line_addImg);
		line_rootView = (LinearLayout) findViewById(R.id.line_rootView);
		line_intercept = (InterceptLinearLayout) findViewById(R.id.line_intercept);
		richText = (RichTextEditor) findViewById(R.id.richText);
		initRichEdit();
	}


	private void initRichEdit() {
		ImageView img_addPicture, img_takePicture;
		img_addPicture = (ImageView) line_addImg
				.findViewById(R.id.img_addPicture);
		img_addPicture.setOnClickListener(this);
		img_takePicture = (ImageView) line_addImg
				.findViewById(R.id.img_takePicture);
		img_takePicture.setOnClickListener(this);

		et_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					isEditTouch = false;
					line_addImg.setVisibility(View.GONE);
				}
			}

		});
		richText.setLayoutClickListener(new RichTextEditor.LayoutClickListener() {
			@Override
			public void layoutClick() {
				isEditTouch = true;
				line_addImg.setVisibility(View.VISIBLE);
			}
		});

		line_rootView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						int heightDiff = line_rootView.getRootView()
								.getHeight() - line_rootView.getHeight();
						if (isEditTouch) {
							if (heightDiff > 500) {// 大小超过500时，一般为显示虚拟键盘事件,此判断条件不唯一
								isKeyBoardUp = true;
								line_addImg.setVisibility(View.VISIBLE);
							} else {
								if (isKeyBoardUp) {
									isKeyBoardUp = false;
									isEditTouch = false;
									line_addImg.setVisibility(View.GONE);
								}
							}
						}
					}
				});
	}
	

	private void openCamera() {
		try {
			File PHOTO_DIR = new File(mFileUtils.getStorageDirectory());
			if (!PHOTO_DIR.exists())
				PHOTO_DIR.mkdirs();// 创建照片的存储目录

			mCameraImageFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
			final Intent intent = getTakePickIntent(mCameraImageFile);
			startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
		} catch (ActivityNotFoundException e) {
		}
	}

	private Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	/**
	 * 用当前时间给取得的图片命名
	 */
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyy_MM_dd_HH_mm_ss");
		return dateFormat.format(date) + ".jpg";
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		if (requestCode == REQUEST_CODE_PICK_IMAGE) {
			Uri uri = data.getData();
			richText.insertImage(mFileUtils.getFilePathFromUri(uri));
		} else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA
				&& resultCode == Activity.RESULT_OK) {
			richText.insertImage(mCameraImageFile.getAbsolutePath());
		}

	}

	
	public void submitNews(News news) {
		showProgress("提交中...");
		RequestParams params = new RequestParams();
		params.put("username", UserDao.getUserName(this));
		params.put("password", UserDao.getPassWord(this));
		params.put("data", JSON.toJSONString(news).toString());
		MyApp.getNet().postReq(NetConstants.AddNews, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						hideProgress();
						showToast("add fail");
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						Response response = JSON.parseObject(new String(arg2),
								Response.class);
						hideProgress();
						if (response.isIsSuccess()) {
							User user = JSON.parseObject(response.getData(), User.class);
							MyApp.instance.setUser(user);
							showToast("add success");
							TopicAddActivity.this.finishUI();
						} else {
							showToast(response.getDescription());
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_user_head:
			break;
		case R.id.img_addPicture:
			// 打开系统相册
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");// 相片类型
			startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
			break;
		case R.id.img_takePicture:
			// 打开相机
			openCamera();
			break;
		default:
			break;
		}
	}
}
