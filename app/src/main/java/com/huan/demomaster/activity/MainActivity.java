package com.huan.demomaster.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.demo.DemoAddActivity;
import com.huan.demomaster.activity.news.NewsAddActivity;
import com.huan.demomaster.activity.topic.TopicAddActivity;
import com.huan.demomaster.adapter.FragmentAdapter;
import com.huan.demomaster.fragment.MainFragment1;
import com.huan.demomaster.fragment.MainFragment2;
import com.huan.demomaster.fragment.MainFragment3;
import com.huan.demomaster.fragment.MainFragment4;
import com.huan.demomaster.view.Rotatable;

public class MainActivity extends BaseActivity implements
		OnCheckedChangeListener, OnClickListener {
	private FragmentAdapter fragmentAdapter;
	MainFragment1 mainFragment1;
	MainFragment2 mainFragment2;
	MainFragment3 mainFragment3;
	MainFragment4 mainFragment4;
	ViewPager viewPager;

	private RelativeLayout rel_menu_bg;
	private PopupWindow pW_menu = null;
	private GridView gridview_menu;
	private String[] Name = { "发布文章", "发布Demo", "发布应用", "面对面快传", "付款" };
	private int[] Images_ic = { R.drawable.conversation_options_multichat,
			R.drawable.conversation_options_addmember,
			R.drawable.conversation_options_qr,
			R.drawable.conversation_facetoface_qr,
			R.drawable.conversation_options_charge_icon };

	RadioGroup main_radio_group;
	private String title = "首页";

	private RelativeLayout rlCardRoot;
	private ImageView imageViewBack;
	private ImageView imageViewFront;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void init() {
		hideLeftButton();
		showRightImageView();
		main_radio_group = (RadioGroup) findViewById(R.id.main_radio_group);
		main_radio_group.setOnCheckedChangeListener(this);

		List<Fragment> fragments = new ArrayList<Fragment>();

		mainFragment1 = new MainFragment1();
		fragments.add(mainFragment1);
		mainFragment2 = new MainFragment2();
		fragments.add(mainFragment2);
		mainFragment3 = new MainFragment3();
		fragments.add(mainFragment3);
		mainFragment4 = new MainFragment4();
		fragments.add(mainFragment4);
		// fragments.add(mainFragment4);
		fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),
				fragments);
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		viewPager.setAdapter(fragmentAdapter);

		// 设置启动之后展示的fragment
		viewPager.setCurrentItem(0);
		viewPager.setOffscreenPageLimit(4);
		viewPager.addOnPageChangeListener(mOnPageChangeListener);
		// main_radio_group.check(R.id.main_radio_button_1);

		boolean canUpdate = MyApp.getPreferencesService().getBoolean(
				"UpDateApp", true);
		if (canUpdate) {
			MyApp.instance.updateApp(MainActivity.this);
		}

		/*initView();
		initData();*/
	}

	private void initView() {
		rlCardRoot = (RelativeLayout) findViewById(R.id.rl_card_root);
		imageViewBack = (ImageView) findViewById(R.id.imageView_back);
		imageViewFront = (ImageView) findViewById(R.id.imageView_front);
		imageViewBack.setOnClickListener(this);
		imageViewFront.setOnClickListener(this);
		setCameraDistance();
	}

	/**
	 * 设置数据
	 */
	public void initData() {
		String imageUri = "drawable://" + R.drawable.blue_back;
		MyApp.instance.imageLoader.displayImage(imageUri, imageViewBack);
		imageUri = "drawable://" + R.drawable.blue_front;
		MyApp.instance.imageLoader.displayImage(imageUri, imageViewFront);
		imageViewBack.setVisibility(View.VISIBLE);
		imageViewFront.setVisibility(View.INVISIBLE);
	}

	/**
	 * 翻牌
	 */
	public void cardTurnover() {
		if (View.VISIBLE == imageViewBack.getVisibility()) {
			//ViewHelper.setRotationY(imageViewFront, 180f);// 先翻转180，转回来时就不是反转的了
            
			//第一个参数fromDegrees为动画起始时的旋转角度    
			//第二个参数toDegrees为动画旋转到的角度   
			//第三个参数pivotXType为动画在X轴相对于物件位置类型  
			//第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
			//第五个参数pivotXType为动画在Y轴相对于物件位置类型   
			//第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
			RotateAnimation myAnimation_Rotate=new RotateAnimation(0.0f, +180.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
			myAnimation_Rotate.start();
			Rotatable rotatable = new Rotatable.Builder(rlCardRoot)
					.sides(R.id.imageView_back, R.id.imageView_front)
					.direction(Rotatable.ROTATE_Y).rotationCount(1).build();
			rotatable.setTouchEnable(false);
			rotatable.rotate(Rotatable.ROTATE_Y, -180, 1500);
		} else if (View.VISIBLE == imageViewFront.getVisibility()) {
			Rotatable rotatable = new Rotatable.Builder(rlCardRoot)
					.sides(R.id.imageView_back, R.id.imageView_front)
					.direction(Rotatable.ROTATE_Y).rotationCount(1).build();
			rotatable.setTouchEnable(false);
			rotatable.rotate(Rotatable.ROTATE_Y, 0, 1500);
		}
	}

	/**
	 * 改变视角距离, 贴近屏幕
	 */
	private void setCameraDistance() {
		int distance = 10000;
		float scale = getResources().getDisplayMetrics().density * distance;
		rlCardRoot.setCameraDistance(scale);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageView_back:
		case R.id.imageView_front:
			cardTurnover();
			break;
		}
	}

	// 菜单
	public void ShowMenuView(View anchor) {
		View view = null;
		if (pW_menu == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.cell_menu, null);
			// 创建popWindow
			pW_menu = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT);
		} else {
			view = pW_menu.getContentView();
		}
		gridview_menu = (GridView) view.findViewById(R.id.gridview_menu);
		rel_menu_bg = (RelativeLayout) view.findViewById(R.id.rel_menu_bg);
		rel_menu_bg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pW_menu.dismiss();
			}
		});
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		//
		// 通过for循环将图片id和列表项文字放到Map中，并添加到list集合中
		for (int i = 0; i < Name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", Images_ic[i]);
			map.put("name", Name[i]);
			listItems.add(map); // 将map对象添加到List集合中
		} // 通过for循环将图片id和列表项文字放到Map中，并添加到list集合中

		SimpleAdapter adapter = new SimpleAdapter(this, listItems,
				R.layout.cell_menu_item, new String[] { "name", "image" },
				new int[] { R.id.menu_item_name, R.id.menu_item_image });
		gridview_menu.setAdapter(adapter);
		gridview_menu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				pW_menu.dismiss();
				Intent intent = null;
				/*
				 * Bundle bundle = new Bundle(); bundle.putSerializable("News",
				 * mList.get(position)); intent.putExtras(bundle);
				 */
				switch (position) {
				case 0:
					intent = new Intent(MainActivity.this,
							NewsAddActivity.class);
					break;
				case 1:
					intent = new Intent(MainActivity.this,
							DemoAddActivity.class);
					break;
				case 2:
					intent = new Intent(MainActivity.this,
							TopicAddActivity.class);
					break;
				default:
					intent = new Intent(MainActivity.this,
							NewsAddActivity.class);
					break;
				}
				startActivity(intent);
				/*
				 * Intent intent = new Intent(this, NewsAddActivity.class);
				 * Bundle bundle = new Bundle(); bundle.putSerializable("News",
				 * mList.get(position)); intent.putExtras(bundle);
				 * startActivity(intent);
				 */
			}
		});

		// 可以聚集 ，按返回键，先popWindow，不然popWindow和activity会同时消失，估计这既是Android焦点顺序的原理吧。
		pW_menu.setFocusable(true);
		// view.setFocusable(true); // 这个很重要
		// view.setFocusableInTouchMode(true);
		// 重写onKeyListener
		pW_menu.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {
				// TODO Auto-generated method stub
				// pW_menu.dismiss();
			}
		});
		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					// pW_menu.dismiss();
					return true;
				}
				return false;
			}
		});
		pW_menu.setOutsideTouchable(true);
		// 为了按返回键消失和外部点击消失 ，不会影响背景
		// popWindow.setBackgroundDrawable(new BitmapDrawable());
		pW_menu.setBackgroundDrawable(new ColorDrawable(0x00000000));
		// popWindow.setAnimationStyle(R.style.AnimBottom);
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int xoffInPixels = windowManager.getDefaultDisplay().getWidth() / 2
				- pW_menu.getWidth() / 2;
		int xoffInDip = px2dip(this, xoffInPixels);
		// 默认位置(anchor翻译为“靠山”)
		// popWindow.showAsDropDown(anchor);

		// anchor的居中位置
		// pW_login.showAsDropDown(anchor, -xoffInDip, 0);

		// 偏移位置
		// popWindow.showAsDropDown(anchor,0,-50);
		pW_menu.showAtLocation(anchor, Gravity.TOP | Gravity.RIGHT, 30, 220);
		pW_menu.update();
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	@Override
	public String getTitleText() {
		return title;
	}

	public void setTitleText(String text) {
		title = text;
		title_txt.setText(text);
	}

	@Override
	public void showMenu(View v) {
		ShowMenuView(v);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				main_radio_group.check(R.id.main_radio_button_1);
				break;
			case 1:
				main_radio_group.check(R.id.main_radio_button_2);
				break;

			default:
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		switch (checkedId) {
		case R.id.main_radio_button_1:
			viewPager.setCurrentItem(0);
			setTitleText("首页");
			break;
		case R.id.main_radio_button_2:
			viewPager.setCurrentItem(1);
			setTitleText("分类");
			break;
		case R.id.main_radio_button_3:
			viewPager.setCurrentItem(2);
			setTitleText("学习");
			break;
		case R.id.main_radio_button_4:
			viewPager.setCurrentItem(3);
			setTitleText("个人");
			break;
		default:
			break;
		}
	}

}
