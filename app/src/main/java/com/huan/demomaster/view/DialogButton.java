package com.huan.demomaster.view;

import java.lang.reflect.Method;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;


public class DialogButton extends Button{

	private PopupWindow pop;
	
	public DialogButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DialogButton(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public DialogButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setPop(PopupWindow pop){
		this.pop = pop;
	}
	@Override
	public void setOnClickListener(final OnClickListener l) {
		OnClickListener l2 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				OnClickListener ref = l;
				try {
					Method method = ref.getClass().getMethod("onClick",
							new Class[] { View.class });
					System.out.println("反射方法名" + method.getName());
					method.invoke(l,
							new Object[] { DialogButton.this });
					pop.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		super.setOnClickListener(l2);
	}
}
