package com.huan.demomaster.activity.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.huan.demomaster.MyApp;
import com.huan.demomaster.R;
import com.huan.demomaster.activity.BaseActivity;
import com.huan.demomaster.bean.ADInfo;
import com.huan.demomaster.bean.Demo;
import com.huan.demomaster.constances.NetConstants;
import com.huan.demomaster.utils.ViewFactory;
import com.huan.demomaster.view.cycleviewpager.CycleViewPager;
import com.huan.demomaster.view.cycleviewpager.CycleViewPager.ImageCycleViewListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DemoDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tv_isOriginal, tv_title, tv_author, tv_time;
	private ImageView iv_demo_icon;
	private Demo demo;

	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ADInfo> infos = new ArrayList<ADInfo>();
	private CycleViewPager cycleViewPager;

	private String[] imageUrls = {
			"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
			"http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
			"http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
			"http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
			"http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_details);
		init();
	}

	@Override
	public String getTitleText() {
		Bundle bundle = getIntent().getExtras();
		demo = (Demo) bundle.getSerializable("Demo");
		return "demo详细";
	}

	@Override
	public void onResume() {

		super.onResume();
	}

	private void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_isOriginal = (TextView) findViewById(R.id.tv_isOriginal);
		tv_author = (TextView) findViewById(R.id.tv_author);
		tv_time = (TextView) findViewById(R.id.tv_time);
		iv_demo_icon = (ImageView) findViewById(R.id.iv_demo_icon);

		tv_title.setText(demo.getName());
		tv_author.setText(demo.getAuthor().getNickName());
		tv_time.setText(demo.getTime().toString());
		tv_isOriginal.setText(demo.isOriginal() ? "原创" : "转载");
		// tv_isOriginal.setBackground(getResources().getDrawable(demo.isOriginal()?R.drawable.tv_bg_stroke_red:R.drawable.tv_bg_stroke_green));
		MyApp.imageLoader.displayImage(demo.getHeadImageUrl(), iv_demo_icon);
		initialize();
	}

	private void initialize() {

		cycleViewPager = (CycleViewPager) getFragmentManager()
				.findFragmentById(R.id.fragment_cycle_viewpager_content);

		for (int i = 0; i < imageUrls.length; i++) {
			ADInfo info = new ADInfo();
			info.setUrl(imageUrls[i]);
			info.setContent("图片-->" + i);
			infos.add(info);
		}

		// 将最后一个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1)
				.getUrl()));
		for (int i = 0; i < infos.size(); i++) {
			views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
		}
		// 将第一个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));

		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views, infos, mAdCycleViewListener);
		// 设置轮播
		cycleViewPager.setWheel(true);

		// 设置轮播时间，默认5000ms
		cycleViewPager.setTime(2000);
		// 设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			if (cycleViewPager.isCycle()) {
				position = position - 1;
				Toast.makeText(DemoDetailActivity.this,
						"position-->" + info.getContent(), Toast.LENGTH_SHORT)
						.show();
			}
			
		}

	};
	
	public void getNetData() {
		RequestParams params = new RequestParams();
		params.put("id", demo.getId());
		MyApp.getNet().getReq(NetConstants.getDemoById, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						System.out.println("fail");
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						String data = new String(arg2);
						Demo demo = JSON.parseObject(data, Demo.class);
						refreshUI(demo);
					}

				});
	}

	private void refreshUI(Demo demo2) {
		tv_title.setText(demo2.getName());
		tv_author.setText(demo2.getAuthor().getNickName());
		tv_time.setText(demo2.getTime().toString());
		tv_isOriginal.setText(demo2.isOriginal() ? "原创" : "转载");
		// tv_isOriginal.setBackground(getResources().getDrawable(demo2.isOriginal()?R.drawable.tv_bg_stroke_red:R.drawable.tv_bg_stroke_green));
		MyApp.imageLoader.displayImage(demo.getHeadImageUrl(), iv_demo_icon);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			showProgress("登录中...");
			break;
		case R.id.btn_logoff:
			MyApp.instance.setUser(null);
			showToast("注销成功！");
			DemoDetailActivity.this.finish();
			break;
		default:
			break;
		}
	}
}
