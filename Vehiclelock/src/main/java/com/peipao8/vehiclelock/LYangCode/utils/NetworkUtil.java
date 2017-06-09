package com.peipao8.vehiclelock.LYangCode.utils;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by ooo on 2016/1/12.
 */
public class NetworkUtil {
    /**
     * 判断是否有网络连
     * @param context
     * @return
     */
    public static boolean isNetworkConnectes(Context context){
        if (context!=null) {
            ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mInfo=manager.getActiveNetworkInfo();
            if (mInfo!=null) {
                return mInfo.isAvailable();
            }
        }
        return false;
    }
    /**
     * 判断wifi连接是否可用
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context){
        if (context!=null) {
            ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mInfo=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mInfo!=null) {
                return mInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断mobile是否可用
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context){
        if (context!=null) {
            ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mInfo=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mInfo!=null) {
                return mInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPenGPS(final Context context) {
        if (context!=null) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager!=null) {
                // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
                boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                return gps;
            }
        }
        return false;
    }

    /**
     * 判断AGPS是否开启
     * @param context
     * @return
     */
    public static final boolean isOPenAGPS(final Context context){
        if (context!=null) {
            LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager!=null) {
                // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
                boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                return network;
            }
        }
        return false;
    }

    /**
     * 强制帮用户打开GPS
     * @param context
     */
    public static final void openGPSQ(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
    /**
     * 打开gps
     */
    public static final void openGPS(Context context){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try
        {
            context.startActivity(intent);
        } catch(ActivityNotFoundException ex)
        {
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }
}
