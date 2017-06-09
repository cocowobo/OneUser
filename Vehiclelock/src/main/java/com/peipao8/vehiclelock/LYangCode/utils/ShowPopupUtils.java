package com.peipao8.vehiclelock.LYangCode.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.ArrayList;

/**
 * Description:弹出窗口类，可以是PopupWindows ，警告框， -------
 * Copyright  : Copyright (c) 2016
 * Company    : 大胸平天下
 * Author     : 太阳光辉
 * Date       : 2016/8/22 13:58
 */
public class ShowPopupUtils {

	/**弹出的窗口的依附界面*/
	private Activity context;
	/**父窗口View 即 DecorView。*/
	private View parent;
	private Handler mHandler ;
	private int msgWhat ;
	private static ArrayList<PopupWindow> popupWindowsList;
	/** 采用静态代码块初始化图片*/
	static {
		popupWindowsList = new ArrayList<> () ;
	}
	/**
	 * 构造函数
	 * @param context  弹出的窗口的依附界面
	 * @param mHandler  弹出窗口向指定Handler 发送消息
	 * @param msgWhat  指定Handler 发送消息的区分标记
	 */
	public ShowPopupUtils(Activity context, Handler mHandler, int msgWhat){
		this.context = context;//01--上下文
		this.parent = context.getWindow ().getDecorView ();//的父view.是指整个窗口view
		this.mHandler = mHandler ;
		this.msgWhat = msgWhat ;
	}

	/**
	 * 展示最简单的展示PopupWindows
	 * @param dispayView 要展示的布局
	 */
	public static void showPopupWindow(View dispayView ) {
		// 一个自定义的布局，作为显示的内容
		if ((PopupWindow)dispayView.getTag () == null) {
			PopupWindow popupWindownew = new PopupWindow (
				dispayView,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
			dispayView.setTag (popupWindownew);
		}else{
			return;
		}
		((PopupWindow)dispayView.getTag ()).setContentView(dispayView);
		((PopupWindow)dispayView.getTag ()).setFocusable(false);
		((PopupWindow)dispayView.getTag ()).setTouchable(true);
		((PopupWindow)dispayView.getTag ()).setOutsideTouchable(true);
		((PopupWindow)dispayView.getTag ()).setBackgroundDrawable(new ColorDrawable (Color.TRANSPARENT));
		((PopupWindow)dispayView.getTag ()).showAsDropDown(dispayView, 0, -1);
//
//		((PopupWindow)dispayView.getTag ()).setOnDismissListener(new PopupWindow.OnDismissListener() {
//			@Override
//			public void onDismiss() {
//
//			}
//		});
	}

}
