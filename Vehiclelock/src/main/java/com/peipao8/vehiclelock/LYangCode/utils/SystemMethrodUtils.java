package com.peipao8.vehiclelock.LYangCode.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 仿写一个栈管理类，管理Activity吧
 */
public class SystemMethrodUtils {

	/**最后一次点击的时间*/
	private static long lastClickTime;

	/** 是否快速点击了*/
	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if ( time - lastClickTime < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/** 获取版本号  @return 当前应用的版本号 */
	public static String getVersion() {
		try {
			PackageManager manager = UiUtils.getContext ().getPackageManager ();
			PackageInfo    info    = manager.getPackageInfo ( UiUtils.getContext ().getPackageName (), 0 );
			String         version = info.versionName;
			return "版本号:" + version;
		} catch (Exception e) {
			e.printStackTrace ();
			return "获取版本号失败";
		}
	}
	/** 获取版本号  @return 当前应用的版本号 */
	public static int getVersionCode() {
		try {
			PackageManager manager = UiUtils.getContext ().getPackageManager ();
			PackageInfo    info    = manager.getPackageInfo ( UiUtils.getContext ().getPackageName (), 0 );
			int         version = info.versionCode;
			return  version;
		} catch (Exception e) {
			e.printStackTrace ();
			return 1000;
		}
	}
}
