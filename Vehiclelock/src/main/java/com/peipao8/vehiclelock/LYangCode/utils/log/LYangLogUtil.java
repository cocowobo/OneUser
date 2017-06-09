package com.peipao8.vehiclelock.LYangCode.utils.log;

import android.util.Log;

import com.peipao8.vehiclelock.App;

/** log管理 */
public class LYangLogUtil {
	private static boolean isDebug = App.isDebug;
	/**1、Log.v 黑色的，开发调试过程中一些详细信息，不应该编译进产品中，只在开发阶段使用,VERBOSE日志级别的信息*/
	public static void v(String tag, String msg) {
		if (isDebug) {
			Log.v(tag, "-----详细信息->"+msg);
		}
	}
	/**2、Log.d是蓝色的，用于调试的信息，编译进产品，但可以在运行时关闭*/
	public static void d(String tag, String msg) {
		if (isDebug) {
			Log.d(tag, "-----调试信息->"+msg);
		}
	}
	/**3、Log.i为绿色，例如一些运行时的状态信息，这些状态信息在出现问题的时候能提供帮助*/
	public static void i(String tag, String msg) {
		if (isDebug) {
			Log.i(tag, "-----运行状态->"+msg);
		}
	}

	/**4、Log.w为橙色, 警告系统出现了异常，即将出现错误,输出大于或等于WARN日志级别的信息*/
	public static void w(String tag, String msg) {
		if (isDebug) {
			Log.w(tag, "-----警告信息->"+msg);
		}
	}

	/**5、Log.e为红色，系统已经出现了错误。.*/
	public static void e(String tag, String msg) {
		if (isDebug) {
			Log.e(tag, "-----错误信息->"+msg);
		}
	}
	/**
	 * 分段打印出较长log文本
	 * @param log        原log文本
	 * @param showCount  规定每段显示的长度（最好不要超过eclipse限制长度）
	 */
	public static void showLogCompletion(String log,int showCount){
		if (isDebug) {
			Log.i("-------------", "-----------------长打印-----");
			Log.i("-------------", "-----------------长打印------------");
			Log.i("-------------", "-----------------长打印-------------------");
			Log.i("-------------", "-----------------长打印--------------------------");
			Log.i("-------------", "-----------------长打印----------------------------------");                //多加一行空行
			printlnLog (log, showCount);
		}
	}

	private static void printlnLog(String log, int showCount) {
		if(log.length() >showCount){     //超出长度
			String show = log.substring(0, showCount);  //固定截取
			Log.i("", "-----"+show);                //打印截取 的固定长度
			Log.i("-----空行--------", "");                //多加一行空行

			if((log.length() - showCount)>showCount){   //剩下的文本还是大于规定长度
				String partLog = log.substring(showCount,log.length());  //再次截取固定长度
				printlnLog(partLog, showCount);     //递归打印
			}else{                                                     //剩下的文本小于规定长度
				String surplusLog = log.substring(showCount, log.length());   //截取剩余长度
				Log.i("", surplusLog+"");
				Log.i("-------------", "-----------------长打印----------------------------------");
				Log.i("-------------", "-----------------长打印--------------------------");
				Log.i("-------------", "-----------------长打印-------------------");
				Log.i("-------------", "-----------------长打印------------");
				Log.i("-------------", "-----------------长打印-----");
			}

		}else{
			Log.i("", "-----"+log);
			Log.i("-------------", "-----------------长打印----------------------------------");
			Log.i("-------------", "-----------------长打印--------------------------");
			Log.i("-------------", "-----------------长打印-------------------");
			Log.i("-------------", "-----------------长打印------------");
			Log.i("-------------", "-----------------长打印-----");
		}
	}

	//-----------------------------------错误信息打印-----------------------------------
	//======================================================================
}
