package com.peipao8.vehiclelock.LYangCode.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.peipao8.vehiclelock.LYangCode.utils.log.LogUtil;

import java.io.File;

/**
 * com.yuntongxun.ecdemo.common.utils in ECDemo_Android
 * Created by Jorstin on 2015/6/6.
 */
public class ExportImgUtil {

    private static final String TAG = "peipao.ExportImgUtil";
    public static void refreshingMediaScanner(Context context , String pathName) {
        if(TextUtils.isEmpty(pathName)) {
            return ;
        }
        File dir = new File(FileAccessor.EXPORT_DIR , pathName);
        Uri uri = Uri.fromFile(dir);
        Intent action = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE ,uri);
        context.sendBroadcast(action);
        LogUtil.d(TAG , String.format("refreshing media scanner on path=%s" ,new Object[]{pathName}));
    }
}
