package com.peipao8.vehiclelock.LYangCode.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.peipao8.vehiclelock.LYangCode.utils.data.DataUtils;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 整个App 的管理，包括，Activity的管理，Service的管理，程序的退出，杀死，
 */
public class SofeZuJianManager {

	private static SofeZuJianManager instance;
	private SofeZuJianManager() {	}
	/** 001--单一模式获得本类的实例 */
	public static synchronized SofeZuJianManager getSofeManager() {
		if (instance == null) {
			instance = new SofeZuJianManager ();
		}
		return instance;
	}

	private static Stack<Activity> activityStack;//01--mActivity 管理 栈

	static {  // 静态代码块，只执行一次，只要本类被使用或者被new ，都会走过来而且最先调用
		activityStack = new Stack<Activity>();
	}

	/** 04--当前管理的栈中是否具有你传入的activity类名的对象*/
	public boolean isHaveActivity(Class<?> cls){
		boolean flag = false;
		for(int i=0;i<activityStack.size();i++){
			if(activityStack.get(i).getClass().equals(cls)){
				flag = true;
			}
		}
		return flag;
	}

	/** 05--添加activity到栈中最后一位，用继承的add方法*/
	public void addActivity(Activity activity) {
		if (activityStack.contains(activity)){
			return;
		}
		//将指定元素添加到此向量的末尾。
		activityStack.add(activity);
	}

	/** 05--添加activity到栈中最后一位，用push方法*/
	public void pushActivity(Activity activity) {
		if (activityStack.contains(activity)){
			return;
		}
		//把项压入堆栈顶部。
		activityStack.push(activity);
	}

	/** 06--返回最后添加到栈中的activity*/
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/** 07--结束最后添加到栈中的activity*/
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/** 08--移除传入的activity对象并关闭传入对象*/
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/** 08--从栈中取出所有activity挨个对比你传入的类文件类名，相匹配的单个或多个对象关闭并移除 */
	public void finishActivity(Class<?> cls) {
		for(int i=0;i<activityStack.size();i++){
			Activity activity =activityStack.get(i);
			try {
				if (activity.getClass()!=null&&activity.getClass().equals(cls)) {
					finishActivity(activity);
				}
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		
	}

	/** 09--结束所有Activity保留登录界面.并且移除出其他activity从task
	 * 需要保留的页面可以自己配置 */
	public void finishAllActivityExcludeLogin(String keepActivity) {
		if (DataUtils.isEmptyStrict (keepActivity)) {
		    keepActivity = "LoginActivity" ;  //默认保留登录界面
		}
		ArrayList<Activity> activityList = new ArrayList<Activity>(activityStack);
		for (int i = 0, size = activityList.size(); i < size; i++) {
			Activity activity = activityList.get(i);
			if (null !=activity) {
				if(!activity.getClass().getSimpleName().equals(keepActivity)) {
					activity.finish();
					activityStack.remove(activity);
				}
			}
		}
	}

	/** 010--获得一共管理了多少个activity */
	public Integer getCount() {
		return activityStack.size();
	}

	/** 011--关闭所有activity,并清空管理栈*/
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			Activity activity = activityStack.get(i) ;
			if (null != activity ) {
				activity.finish();
			}
		}
		activityStack.clear();
	}

	/** 012--程序关闭所有管理的activity然后退出,关闭,*/
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(10);
		} catch (Exception e) {
		}
	}

	/** 013--结束除了第一个加入的activity以外的所有Activity
	 */
	public void finishUpActivity() {
		int size = activityStack.size();
		for (int i = 0;i < size-1; i++) {//执行长度减一次，依次取最顶部的
			//移除堆栈顶部的对象，并作为此函数的值返回该对象。
			activityStack.pop().finish(); //移除最顶部，然后返回最顶部的对象来finish掉。
		}
	}

}