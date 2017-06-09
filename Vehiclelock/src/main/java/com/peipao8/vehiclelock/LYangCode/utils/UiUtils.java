package com.peipao8.vehiclelock.LYangCode.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.peipao8.vehiclelock.App;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 分层思想的第二层.直接对第一层的application的
 * 数据进行第二层的调用
 *
 * @author Administrator
 */
public class UiUtils {
    private static ExecutorService limitedTaskExecutor;
    private static ArrayList<String> toastMsg = new ArrayList<> ();
    //注:处理ui需要的什么样的准备
    public static int toastIndex;
    public static int toastTime = 10;
    public static int toastTimeAdd = 500;
    
    /** 001.获得当前线程id */
    public static int getThreadId() {
        return android.os.Process.myTid ();
    }
    
    /** 001-1.获得可执行线程为当前cpu个数2倍的线程池 */
    public static ExecutorService getThreadPool() {
        if (limitedTaskExecutor == null) {
            limitedTaskExecutor = Executors.newFixedThreadPool ( Runtime.getRuntime ().availableProcessors () * 2 );
        }
        return limitedTaskExecutor;
    }
    
    /** 002.将一个runable对象放在主线程中去 */
    public static void onRunUiThread(Runnable runnable) {
        if (isRunUiThread ()) {
            runnable.run ();
        } else {
            getMainThreadHandler ().post ( runnable );
        }
    }
    
    /** 003.获取第一层的上下文对象 */
    public static Context getContext() {
        return App.getContext ();
    }
    
    /** 004.获取主线程 */
    public static int getMainThreadId() {
        return App.getMainThreadId ();
    }
    
    /** 005.判断当前线程是否是主线程 */
    public static boolean isRunUiThread() {
        return getThreadId () == getMainThreadId ();
    }
    
    /** 006.获得第一层的handler用来发消息 */
    public static Handler getMainThreadHandler() {
        return App.getHandler ();
    }
    
    /** 007.获得res目录下values的保存的数据 */
    public static String getString(int resid) {
        return getContext ().getResources ().getString ( resid );
    }
    
    /** 008.获得res目录下values保存的数据 */
    public static String[] getStringArray(int resid) {
        return getContext ().getResources ().getStringArray ( resid );
    }
    
    /** 009.获得res目录下的保存的图片数据 */
    public static Drawable getDrawable(int resid) {
        return getContext ().getResources ().getDrawable ( resid );
    }
    
    /** 010.获得res目录下values保存的数据 */
    public static int getColors(int resid) {
        return getContext ().getResources ().getColor ( resid );
    }
    
    /** 011.获得res目录下的保存的数值数据 */
    public static int getDimension(int resid) {
        return getContext ().getResources ().getDimensionPixelSize ( resid );
    }
    
    /** 012-1.屏幕独立像素转为像素 */
    public static int dp2px(int dp) {
        return (int) ( dp * getContext ().getResources ().getDisplayMetrics ().density + 0.5f );
    }
    
    /** 012-2.屏幕独立像素转为像素 */
    public static int dip2px(int dpValue) {
        final float scale = Resources.getSystem ().getDisplayMetrics ().density;
        return (int) ( dpValue * scale + 0.5f );
    }
    
    /** 011.像素转为屏幕独立像素 */
    public static int px2dp(int px) {
        return (int) ( px / getContext ().getResources ().getDisplayMetrics ().density + 0.5f );
    }
    
    /** 012.获取颜色状态选择器 */
    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return getContext ().getResources ().getColorStateList ( mTabTextColorResId );
    }
    
    /** 013.根据布局文件获取 */
    public static View inflate(int layoutId) {
        return View.inflate ( getContext (), layoutId, null );
    }
    
    //-----------------------------------Toast区-----------------------------------
    private static       long    oneTime = 0;
    /** 第一次时间 */
    private static       long    twoTime = 0;
    /** 第二次时间 */
    protected static     Toast   toast   = null;
    private static       String  oldMsg  = "";
    private static final boolean isDebug = App.isDebug;
    
    /** 014-1.原生吐司方法--只用于测试1 */
    public static void toastDebug(String msg) {
        if (isDebug) {
            toast ( msg );
        }
    }

    /** 014-1.原生吐司方法--只用于测试2 */
    public static void toastDebug(Context context,String msg) {
        if (isDebug) {
            toast (context, msg );
        }
    }

    /** 014-2.原生吐司方法--只用于测试1 */
    public static void toastDebug(String msg, int time) {
        if (isDebug) {
            toast (getContext (), msg, time );
        }
    }

    /** 014-2.原生吐司方法--只用于测试2 */
    public static void toastDebug(Context context,String msg, int time) {
        if (isDebug) {
            toast ( context, msg, time );
        }
    }

    /** 014-3.原生吐司方法 1 */
    public static void toast(int id) {
        toast (getContext (), getContext ().getResources ().getString ( id ), Toast.LENGTH_SHORT );
    }
    
    /** 014-3.原生吐司方法 2 */
    public static void toast(Context context,int id) {
        toast (context, getContext ().getResources ().getString ( id ), Toast.LENGTH_SHORT );
    }

    /** 014-4.原生吐司方法 1 */
    public static void toast(int id, int time) {
        toast (getContext (), getContext ().getResources ().getString ( id ), time );
    }
    /** 014-4.原生吐司方法 2 */
    public static void toast(Context context,int id, int time) {
        toast (context, getContext ().getResources ().getString ( id ), time );
    }

    /** 014-5.原生吐司方法 1 */
    public static void toast(String msg,int time) {
        toast ( getContext (),msg, time);
    }
    /** 014-5.原生吐司方法 1 */
    public static void toast(String msg) {
        toast ( getContext (),msg, Toast.LENGTH_SHORT );
    }
    /** 014-5.原生吐司方法 3 */
    public static void toast(Context context,String msg) {
        toast ( context,msg, Toast.LENGTH_SHORT );
    }

    /** 014-6.原生吐司方法 1 */
    public static void toastTag(String tag, int id) {
        toast ( getContext (),tag + getContext ().getResources ().getString ( id ), Toast.LENGTH_SHORT );
    }

    /** 014-6.原生吐司方法 2 */
    public static void toastTag(Context context,String tag, int id) {
        toast ( context,tag + getContext ().getResources ().getString ( id ), Toast.LENGTH_SHORT );
    }
    /** 015-1.原生吐司方法 4 */
    public static void toast(final Context context, final String msg, final int time) {
        toastMsg.add ( msg );
        if (toastMsg.size () - toastIndex > 10){
            toastTimeAdd = 100 ;
        } else if (toastMsg.size () - toastIndex > 5) {
            toastTimeAdd = 250;
        } else {
            toastTimeAdd = 500;
        }
        /**如果在react-native中content随着页面的跳转被销毁,会不会报空*/
        App.getHandler ().postDelayed ( new Runnable () {
            @Override
            public void run() {
                if (toast == null) {
                    if (context == null) {
                        toast = Toast.makeText ( getContext (), toastMsg.get ( toastIndex ), time );
                    } else {
                        toast = Toast.makeText ( context , toastMsg.get ( toastIndex ), time );
                    }
                    toast.show ();
                } else {
                    toast.setText ( toastMsg.get ( toastIndex ) );                        //替换消息内容
                    toast.show ();                              //继续显示
                }
                toastIndex++;
                toastTime -= toastTimeAdd;
            }
        }, toastTime );   //第一个toast`10ms后执行
        toastTime += toastTimeAdd;  //立马给下一个toast安排时间为10+500ms后.
    }

    
    /** 017.Snackbar吐司方法一 */
    public static void showSnackbar(FragmentActivity context, @StringRes int stringId) {
        if (context == null) {
            toast ( stringId );
        } else {
            Snackbar.make ( context.getWindow ().getDecorView (), stringId, Snackbar.LENGTH_LONG ).show ();
        }
    }
    
    /** 018.Snackbar吐司方法二
     * 需要引入    //----------------控件支持包-----------
     * compile 'com.android.support:design:23.2.0'
     * 自动引入recyclerview和apppcompat v7
     */
    public static void showSnackbar(FragmentActivity context, CharSequence msg) {
        if (context == null) {
            toast ( msg.toString () );
        } else {
            Snackbar.make ( context.getWindow ().getDecorView (), msg, Snackbar.LENGTH_LONG ).show ();
        }
    }
    
    /** 019.Snackbar吐司方法一 */
    public static void showSnackbar(FragmentActivity context, String tag, @StringRes int stringId) {
        if (context == null) {
            toastTag ( tag, stringId );
        } else {
            Snackbar.make ( context.getWindow ().getDecorView (), tag + getContext ().getResources ().getString (
                stringId ), Snackbar.LENGTH_LONG ).show ();
        }
    }
    //===================================Toast区===================================
}

