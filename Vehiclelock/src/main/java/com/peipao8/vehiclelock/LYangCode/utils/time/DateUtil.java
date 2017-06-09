/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.peipao8.vehiclelock.LYangCode.utils.time;

import android.annotation.SuppressLint;

import com.peipao8.vehiclelock.LYangCode.utils.log.LYangLogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 时间转换工具类
 */
public class DateUtil {
	
	public static final TimeZone         tz             = TimeZone.getTimeZone("GMT+8:00");   //时区（Time Zone)
	public static final SimpleDateFormat formatFrontOne = new SimpleDateFormat("yyyy-MM-dd");    //time=2016-11-09
	public static final SimpleDateFormat formatFrontTwo = new SimpleDateFormat("yyyy/MM/dd");    //time=2016/11/09
	public static final SimpleDateFormat formatFrontThree = new SimpleDateFormat("yyyyMMdd");    //time=20161109
	public static final SimpleDateFormat formatFrontFour = new SimpleDateFormat("yyyy年MM月dd日");  //2016年11月09日

	public static final SimpleDateFormat formatBehindOne = new SimpleDateFormat("hh:mm:ss");      //04:47:24
	public static final SimpleDateFormat formatBehindTwo = new SimpleDateFormat("HH:mm:ss");      //16:47:24
	public static final SimpleDateFormat formatBehindThree = new SimpleDateFormat("HHmmss");      //164724
	public static final SimpleDateFormat formatBehindFour = new SimpleDateFormat("HH点mm分ss秒"); //16点47分24秒

	public static final SimpleDateFormat formatOverallOne   = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   //time=2016-11-09 04:47:24
	public static final SimpleDateFormat formatOverallTwo   = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");   //time=2016/11/09 04:47:24
	public static final SimpleDateFormat formatOverallThree = new SimpleDateFormat("yyyyMMddHHmmss");        //time=20161109164724
	public static final SimpleDateFormat formatOverallFour  = new SimpleDateFormat("yyyyMMdd HH:mm:ss");     //time=20161109 16:47:24
	public static final SimpleDateFormat formatOverallFive  = new SimpleDateFormat("yyyy年MM月dd日 hh点mm分ss秒"); //time=2016年11月09日 04点47分24秒
	public static final SimpleDateFormat formatOverallSix  = new SimpleDateFormat("MM月dd日 HH-mm-ss"); //time=11月09日 04-47-24
	public static final SimpleDateFormat formatOverallSeven  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //time=11月09日 04-47-24

	/**一天的毫秒数*/
	private static final long ONEDAY = 86400000;
	/**复杂的短类型*/
	public static final int SHOW_TYPE_SIMPLE = 0;
	/**简单的短类型*/
	public static final int SHOW_TYPE_COMPLEX = 1;
	/**所有的短类型*/
	public static final int SHOW_TYPE_ALL = 2;
	/**简单的短类型*/
	public static final int SHOW_TYPE_CALL_LOG = 3;
	/**简单的短类型*/
	public static final int SHOW_TYPE_CALL_DETAIL = 4;

	/**01.获取当前当天日期凌晨的毫秒数  */
	public static long getCurrentDayTime() {
		Date d = new Date(System.currentTimeMillis());
		String formatDate = formatFrontOne.format(d);
		try {
			return ( formatFrontOne.parse(formatDate)).getTime();   /**返回截止今天凌晨的毫秒数*/
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**02.解析指定毫秒时长字符串为具体日期凌晨的时间毫秒。*/
	public static long getActiveTimelong(String result) {
		try {
			Date parse = formatFrontOne.parse(result);
			return parse.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}

	/**03.获得指定时间毫秒。*/
    public static long getDateMills(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(year, month, day);
		TimeZone tz = TimeZone.getDefault();
		calendar.setTimeZone(tz);
        return calendar.getTimeInMillis();
    }

	/**04.根据传入的时间long，和类型要求，来返回格式化后的日期。。有好多种时间类型来查看*/
	public static String getDateString(long time, int type) {
		Calendar c = Calendar.getInstance(tz);
		c.setTimeInMillis(time);                                /**指定long毫秒时间生成的日历对象*/

		long currentTime = System.currentTimeMillis();
		Calendar current_c = Calendar.getInstance(tz);
		current_c.setTimeInMillis(currentTime);                 /**此时此刻long毫秒时间生成的日历对象*/

		int currentYear = current_c.get(Calendar.YEAR);         /**获得此时此刻的当前年份*/

		int y = c.get(Calendar.YEAR);                           /**指定long毫秒时间的年，月，日，时，分，秒*/
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);

		long t = currentTime - time;                            /**计算此时此刻与指定long毫秒的时间差毫秒*/
		long t2 = currentTime - getCurrentDayTime();            /**计算此时此刻和凌晨00:00的时间差毫秒*/

		String dateStr = "";                                    /**记录用的字符串*/
		if (t < t2 && t > 0) {                                  /**指定long，处于此时此刻之前。今天凌晨之后*/
			if (type == SHOW_TYPE_SIMPLE) {                     	/*在判断，传入的类型是0还是1。这里是0*/
				dateStr = (hour < 10 ? "0" + hour : hour) + ":"     /**返回 时:分*/
						+ (minute < 10 ? "0" + minute : minute);
			} else if (type == SHOW_TYPE_COMPLEX) {                 /*判断出入是1.*/
				dateStr = "今天  " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);    /**返回 今天 时:分，和上面多个今天*/
			} else if (type == SHOW_TYPE_CALL_LOG) {                /*判断是2.*/
				dateStr = "今天  " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);    /**返回，和上面的一样，哎呀。懵逼了*/
			} else if (type == SHOW_TYPE_CALL_DETAIL) {             /*判断是3*/
				dateStr = "今天  ";                                 /**只返回今天两个字。。。。*/
			}else {                                                 /**传入没有在全局定义的量。则返回时分秒*/
				dateStr = (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute) + ":"
						+ (second < 10 ? "0" + second : second);
			}
		} else if (t < (t2 + ONEDAY) && t > 0) {                    /**不在今天返回在判断在昨天范围内不*/
			if (type == SHOW_TYPE_SIMPLE || type == SHOW_TYPE_CALL_DETAIL) {
				dateStr = "昨天  ";                                /**0和4返回昨天*/
			} else if (type == SHOW_TYPE_COMPLEX || type == SHOW_TYPE_CALL_LOG) {    /**1或者3返回昨天00:00*/
				dateStr = "昨天  " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);
			} else {                                               /**其余返回昨天 时分秒*/
				dateStr = "昨天  " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute) + ":"
						+ (second < 10 ? "0" + second : second);
			}
		} else if (y == currentYear) {                             /**当发现该时间不是今天和昨天的时候，检测是今年*/
			if (type == SHOW_TYPE_SIMPLE) {                        /**0.返回月/日*/
				dateStr = (m < 10 ? "0" + m : m) + "/" + (d < 10 ? "0" + d : d);
			} else if (type == SHOW_TYPE_COMPLEX) {                /**1.返回几月几日*/
				dateStr = (m < 10 ? "0" + m : m) + "月" + (d < 10 ? "0" + d : d)
						+ "日";
			} else if (type == SHOW_TYPE_CALL_LOG ) {   		  /**3.返回月日空格时:分*/
				dateStr = (m < 10 ? "0" + m : m) + /* 月 */"/"
						+ (d < 10 ? "0" + d : d) + /* 日 */" "
						+ (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);
			} else if (type == SHOW_TYPE_CALL_DETAIL) {            /**4返回年+/+月日*/
				dateStr = y + "/" + (m < 10 ? "0" + m : m) + "/"
						+ (d < 10 ? "0" + d : d);
			} else {                                               /**其余数返回几月几日00:00:00*/
				dateStr = (m < 10 ? "0" + m : m) + "月" + (d < 10 ? "0" + d : d)
						+ "日 " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute) + ":"
						+ (second < 10 ? "0" + second : second);
			}
		} else {                                                  /**不是今天不知昨天不是今年。则进来*/
			if (type == SHOW_TYPE_SIMPLE || type == SHOW_TYPE_CALL_DETAIL) {     /**0和4.返回年/月/日*/
				dateStr = y + "/" + (m < 10 ? "0" + m : m) + "/"
						+ (d < 10 ? "0" + d : d);
			} else if (type == SHOW_TYPE_COMPLEX ) {             /**1.返回几年几月几日*/
				dateStr = y + "年" + (m < 10 ? "0" + m : m) + "月"
						+ (d < 10 ? "0" + d : d) + "日";
			} else if (type == SHOW_TYPE_CALL_LOG) {  			 /**3.返回 年/月/日在加上两个空格*/
				dateStr = y + /* 年 */"/" + (m < 10 ? "0" + m : m) + /* 月 */"/"
						+ (d < 10 ? "0" + d : d) + /* 日 */"  " ;
			} else {                                             /**其余就返回几年几月几日00:00:00*/
				dateStr = y + "年" + (m < 10 ? "0" + m : m) + "月"
						+ (d < 10 ? "0" + d : d) + "日 "
						+ (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute) + ":"
						+ (second < 10 ? "0" + second : second);
			}
		}
		return dateStr;                                       /**返回格式后的long时间*/
	}

	/**05.返回当前时间秒数。（不是毫秒）*/
	public static long getCurrentTime() {
		return System.currentTimeMillis() / 1000;
	}

	/**06 获取当前日期和时间 */
	public static String getCurrentDateStr() {
		Date date = new Date();
		String str = formatOverallFour.format(date);
		return str;
	}
	/**06-1 根据传入格式获取当前日期和时间 */
	public static String getCurrentDateStr(SimpleDateFormat formatType) {
		Date date = new Date();
		String str = formatType.format(date);
		return str;
	}

	/**07.格式化时间。*/
	public static String formatDate(int year, int month, int day) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar (year - 1900, month, day);
		Date              d                 = gregorianCalendar.getTime ();  /**推荐使用标准日历对象来构建date*/
		return formatFrontOne.format(d);
	}
	/**07.将String类型的时间差毫秒值，转换为小数分钟。*/
	public static float getFloatMin(String longMSString) {
		long longMS = Long.parseLong ( longMSString );
		float min      = longMS / 60000;
		return min;
	}

	/**08.判断间隔时间是否超过 date 如果时间间隔大于date返回true
	 * 时间格式yyyyMMdd HH:mm:ss
	 * @param aboveDate
	 * 	上次时间字符串
	 * @param nowDate
	 * 	本次时间字符串
	 * @param date
	 * 	时间间隔(按秒计算)
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static boolean aboveCompareNowDate(String aboveDate,String nowDate,long date){
		try {
			Date above= formatOverallFour.parse(aboveDate);
			Date now= formatOverallFour.parse(nowDate);
			long diff = now.getTime() - above.getTime();
			long mm = diff /1000;
			if (date<=mm) {
				return true;
			}
		} catch (ParseException e) {
			LYangLogUtil.e ( "DateUtil类", "aboveCompareNowDate-->解析异常 e=" + e.getMessage() );   // 异常输出-----
			return false;
		}
		return false;
	}
	
	/**
	 * 验证验证码是否超时
	 * checkCodeTime 获取时间
	 * time          超时时间(min)
	 */
	public static boolean isOverTime(long aboveTime,long nowTime,int time) {
		return (nowTime-aboveTime)/1000>=time;
	}
}
