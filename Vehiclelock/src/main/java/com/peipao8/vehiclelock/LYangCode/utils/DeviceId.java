package com.peipao8.vehiclelock.LYangCode.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ooo on 2016/3/14.
 * 设备ID工具类
 */
public class DeviceId {

    private static final int MY_PERMISSION_READ_PHONE_STATE = 1;
    private static String m_szBTMAC;

    /** 获取设备ID */
    public static String getDeviceId () {
        //THE IMEI 仅仅只对Android手机有效
        TelephonyManager TelephonyMgr = (TelephonyManager) UiUtils.getContext ().getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();

        //Pseudo-Unique ID 在任何Android手机中都有效
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10
                + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
                + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
                + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
                + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
                + Build.USER.length() % 10;

        //The Android ID 通常被认为不可信，因为它有时为null
        String m_szAndroidID = Settings.Secure.getString(
            UiUtils.getContext ().getContentResolver(), Settings.Secure.ANDROID_ID);

        //The WLAN MAC Address string 另一个唯一ID
        WifiManager wm = (WifiManager) UiUtils.getContext ().getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();

        //The BT MAC Address string 只在有蓝牙的设备上运行
        BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (m_BluetoothAdapter != null) {
            m_szBTMAC = m_BluetoothAdapter.getAddress();
        }

        //拼接上面五种结果值并进行MD5加密
        String m_szLongID = szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        byte p_md5Data[] = m.digest();
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            if (b <= 0xF)
                m_szUniqueID += "0";
            m_szUniqueID += Integer.toHexString(b);
        }
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }

}
