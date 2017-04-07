package com.huan.demomaster.utils;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huan.demomaster.R;
import com.huan.demomaster.view.DialogButton;

public class DialogUtils {

	public static DialogUtils getInstance() {
		return new DialogUtils();
	}

	// 选择图片dialog
	private static PopupWindow pop = null;
	private static LinearLayout ll_popup;

	// 选择照片的popupWindow
	public static PopupWindow showPopUpWindow(final Context activity,
			String title,OnClickListener listener_left,OnClickListener listener_right) {
		pop = new PopupWindow(activity);
		// LayoutInflater inflater = (LayoutInflater)context.getSystemService
		// (Context.LAYOUT_INFLATER_SERVICE);
		View view = ((Activity) activity).getLayoutInflater().inflate(
				R.layout.item_popupwindows_as_dialog, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		DialogButton btn_left = (DialogButton) view
				.findViewById(R.id.item_popupwindows_camera);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(title);
		btn_left.setPop(pop);
		btn_left.setText("取消");
		DialogButton btn_right = (DialogButton) view
				.findViewById(R.id.item_popupwindows_Photo);
		btn_right.setText("确定");
		btn_right.setPop(pop);
		parent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.startAnimation(AnimationUtils.loadAnimation(activity,
						R.anim.scaling_big));
			}
		});
		// left
		if (listener_left == null) {
			listener_left = new OnClickListener() {
				@Override
				public void onClick(View v) {
					pop.dismiss();
				}
			};
		}
		// right
		if (listener_right == null) {
			listener_right = new OnClickListener() {
				@Override
				public void onClick(View v) {
					pop.dismiss();
				}
			};
		}
		btn_left.setOnClickListener(listener_left);
		btn_right.setOnClickListener(listener_right);
		pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		ll_popup.startAnimation(AnimationUtils.loadAnimation(activity,
				R.anim.scaling_big));
		return pop;
	}
	
	// 选择照片的popupWindow
		public static ProgressBar showPopUpWindowProgress(final Context activity,
				String title,OnClickListener listener_left,OnClickListener listener_right) {
			pop = new PopupWindow(activity);
			// LayoutInflater inflater = (LayoutInflater)context.getSystemService
			// (Context.LAYOUT_INFLATER_SERVICE);
			View view = ((Activity) activity).getLayoutInflater().inflate(
					R.layout.item_popupwindows_as_progressdialog, null);

			ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
			pop.setWidth(LayoutParams.MATCH_PARENT);
			pop.setHeight(LayoutParams.WRAP_CONTENT);
			pop.setBackgroundDrawable(new BitmapDrawable());
			pop.setFocusable(true);
			pop.setOutsideTouchable(true);
			pop.setContentView(view);

			RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
			DialogButton btn_left = (DialogButton) view
					.findViewById(R.id.item_popupwindows_camera);
			ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_download);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_title.setText(title);
			btn_left.setPop(pop);
			btn_left.setText("取消");
			DialogButton btn_right = (DialogButton) view
					.findViewById(R.id.item_popupwindows_Photo);
			btn_right.setText("确定");
			btn_right.setPop(pop);
			parent.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pop.dismiss();
					ll_popup.startAnimation(AnimationUtils.loadAnimation(activity,
							R.anim.scaling_big));
				}
			});
			// left
			if (listener_left == null) {
				listener_left = new OnClickListener() {
					@Override
					public void onClick(View v) {
						pop.dismiss();
					}
				};
			}
			// right
			if (listener_right == null) {
				listener_right = new OnClickListener() {
					@Override
					public void onClick(View v) {
						pop.dismiss();
					}
				};
			}
			btn_left.setOnClickListener(listener_left);
			btn_right.setOnClickListener(listener_right);
			pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(activity,
					R.anim.scaling_big));
			return progressBar;
		}

}
