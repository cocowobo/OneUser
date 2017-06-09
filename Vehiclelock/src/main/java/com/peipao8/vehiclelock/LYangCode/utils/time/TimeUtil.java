package com.peipao8.vehiclelock.LYangCode.utils.time;

import android.os.Handler;
import android.os.Looper;
import android.text.format.Time;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ooo on 2016/1/14.
 * 时间处理类
 */
public class TimeUtil {
    /**
     * 获取完整的24小时制系统当前时间
     *
     * @return
     */
    public static String integritySystemTime() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%Y-%m-%d %H:%M:%S");
    }

    /**
     * 获取完整的24小时制系统当前时间(年月日)
     *
     * @return
     */
    public static String integritySystemTimeDay() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%Y-%m-%d");
    }

    /**
     * 获取完整的24小时制系统当前时间(时)
     *
     * @return
     */
    public static String integritySystemTimeH() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%H");
    }

    /**
     * 获取“年/月/日 时:分”的24小时制系统当前时间
     *
     * @return
     */
    public static String systemTime() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%Y-%m-%d %H:%M");
    }

    /**
     * 时间格式转换器
     *
     * @param time 需要转换的时间
     * @return
     */
    public String showTimeCount(long time) {
        String timeCount = "";
        try {
            if (time >= 360000) {
                return "00:00:00";
            }

            long hourc = time / 3600;
            String hour = "";
            if (hourc < 10) {
                hour = "0" + hourc;
            } else {
                hour = hourc + "";
            }
//            hour = hour.substring(hour.length() - 2, hour.length());

            long minuec = (time - hourc * 3600) / (60);
            String minue = "";
            if (minuec < 10) {
                minue = "0" + minuec;
            } else {
                minue = "" + minuec;
            }
//            minue = minue.substring(minue.length() - 2, minue.length());

            long secc = time - hourc * 3600 - minuec * 60;
            String sec = "";
            if (secc < 10) {
                sec = "0" + secc;
            } else {
                sec = "" + secc;
            }
//            sec = sec.substring(sec.length() - 2, sec.length());
            timeCount = hour + ":" + minue + ":" + sec;
        } catch (Exception e) {
        }
        return timeCount;
    }

    /**
     * 将时分秒字符串数据转化为毫秒数
     *
     * @param dateTime
     * @return
     */
    public long timeCount(String dateTime) {
        long timeCounts = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date d = sdf.parse(dateTime);
            timeCounts = d.getTime() + 28800000;//差值是:28800000
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeCounts;
    }

    public static String getWeek(String pTime) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

    //判断90、80...后
    public static String getZero(String s) {

        String r = "";
        //								Log.i("char tem-------------------->", "111111111"+s);
        //			if(s!=null||!s.equals("")||s!=""||s!="null"){//1991-1-1,199111,""
        if (s.contains("-")) {
            String[] ymd = s.split("-");
            char tem = ymd[0].charAt(2);
            switch (tem) {
                case '0':
                    r = "00后";
                    break;
                case '1':
                    r = "10后";
                    break;
                case '2':
                    r = "20后";
                    break;
                case '3':
                    r = "30后";
                    break;
                case '4':
                    r = "40后";
                    break;
                case '5':
                    r = "50后";
                    break;
                case '6':
                    r = "60后";
                    break;
                case '7':
                    r = "70后";
                    break;
                case '8':
                    r = "80后";
                    break;
                case '9':
                    r = "90后";
                    break;
            }
        }else{
            return "";
        }

        return r;
    }

    /**
     * 耗时操作指定时间内不能重复点击
     * @param view
     * @param millisecond 耗时操作指定时间内不能重复点击的毫秒数
     */
    public static void avoidClickRepeat(final View view, final int millisecond) {
        view.setClickable(false);
        try {
            Looper.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            new Handler ().postDelayed( new Runnable() {

                @Override
                public void run() {
                    view.setClickable(true);
                }
            }, millisecond);
        }
    }
}
