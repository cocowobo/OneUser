package com.peipao8.vehiclelock.LYangCode.utils.data;

import android.text.TextUtils;

/**
 * 处理数据的工具类，
 * 判空，转码，加密，解密，
 */
public class DataUtils {

	/**字符串的严格(Strict)判空*/
	public static boolean isEmptyStrict(String s) {
		if (TextUtils.isEmpty (s))
			return true;
		if (s.trim().length() == 0)
			return true;
		if(s.equals("null"))
			return true;
		return false;
	}
}
