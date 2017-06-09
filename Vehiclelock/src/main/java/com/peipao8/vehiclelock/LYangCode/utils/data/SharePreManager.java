package com.peipao8.vehiclelock.LYangCode.utils.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.peipao8.vehiclelock.LYangCode.utils.UiUtils;

/**
 * SharedPreferences数据保存管理，save 保存。get 取出
 */
public class SharePreManager {

	/**保存Float类型文件*/
	public static void saveValue( String fileName,String saveKey, float saveValue) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences(fileName,Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putFloat(saveKey, saveValue);
		edit.commit();
	}

	/**保存Long类型文件*/
	public static void saveValue( String fileName,String saveKey, long saveValue) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences(fileName,Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putLong (saveKey, saveValue);
		edit.commit();
	}

	/**获得Long类型文件*/
	public static Long getLongValue( String fileName,String key) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences(fileName,Context.MODE_PRIVATE);
		Long llong = preferences.getLong (key,0);
		return llong;
	}

	/**保存int类型文件*/
	public static void saveValue( String fileName,String saveKey, int saveValue) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences(fileName,Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putInt(saveKey, saveValue);
		edit.commit();
	}

	/**保存String字符串类型*/
	public static void saveValue( String fileName,String saveKey, String saveValue) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences(fileName,Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString(saveKey, saveValue);
		edit.commit();
	}

	/**保存Boolean保存判断*/
	public static void saveValue( String fileName,String saveKey, boolean saveValue) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences(fileName,Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(saveKey, saveValue);
		edit.commit();
	}

	/**取出 存储 的sp 值
	 * @param flag 0 取出String 1取出int 类型 2取出 float 类型     */
	public static String getValue( String fileName,String saveKey ,int flag) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences(fileName,Context.MODE_PRIVATE);
		String value = "";
		switch (flag) {
			case 0:
				value = preferences.getString(saveKey, "");
				break;
			case 1:
				int in=preferences.getInt(saveKey, 0);
				value=in+"";
				break;
			case 2:
				float fl=preferences.getFloat(saveKey, 0);
				value=fl+"";
				break;
			case 3:
				Long llong=preferences.getLong (saveKey, 0);
				value=llong+"";
				break;
		}
		return value;
	}

	/**取出Boolean保存判断文件*/
	public static boolean getboolean( String fileName,String saveKey) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences(fileName,Context.MODE_PRIVATE);
		boolean value = preferences.getBoolean(saveKey, false);
		return value;
	}

	/**移除指定key对应的值*/
	public static void remove( String fileName,String key) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences(fileName,Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.remove(key);
		edit.commit();
	}

	/**设置是否是第一次打开应用
	 * @param /设置false 为非第一次打开
	 */
	public static void saveIsFirst( String key, boolean value) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences("IsFirst",Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	/**软件被打开过吗
	 * @return 软件被打开过吗
	 */
	public static boolean getIsFirst( String key) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences("IsFirst",Context.MODE_PRIVATE);
		boolean isfirst = preferences.getBoolean(key, false);
		return isfirst;
	}

	/**保存各种 正反 标记位
	 * @param /设置false 为非第一次打开
	 */
	public static void saveStateFlag( String key, boolean value) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences("StateFlag",Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	/**取出 正反 标记位
	 * @return 是否不是第一次打开了
	 */
	public static boolean getStateFlag( String key) {
		SharedPreferences preferences = UiUtils.getContext () .getSharedPreferences("StateFlag",Context.MODE_PRIVATE);
		boolean isfirst = preferences.getBoolean(key, false);
		return isfirst;
	}

}
