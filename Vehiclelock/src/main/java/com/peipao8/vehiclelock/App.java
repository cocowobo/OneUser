package com.peipao8.vehiclelock;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.peipao8.vehiclelock.LYangCode.utils.SofeZuJianManager;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

import java.util.ArrayList;

/**
 * 全局的Application 类.初始化与开启全局定义等操作
 */
public class App extends Application {
    private static int     mainThreadId;
    private static Handler handler;

    public static boolean isDebug = true;
    /** 整个程序是否开启调试模式 */
    public static int mScreenWidth;
    public static int mScreenHeigh;
    public static int mStatusBarHeight;    /**状态栏高度*/

    /** 全局的上下文. */
    private static Context mContext;
    public static  App     instance;

    private static SQLiteDatabase db;


    /** 单例，返回一个实例 */
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate ();
        mContext = getApplicationContext ();

        /** 001.获得主线id */
        mainThreadId = android.os.Process.myTid ();

        /** 002.获得主线程 Handler */
        handler = new Handler ();

        /** 003.获得全局的上下文对象.方便在任何类中调用到context.对象*/
        instance = this;

        /** 004 NoHttp 网络请求框架初始化*/
        NoHttp.initialize ( this );  //全局初始化   采用默认设置。   // 使用OkHttp做底层网络请求
        NoHttp.initialize ( this, new NoHttp.Config ()
            // 设置全局连接超时时间，单位毫秒
            .setConnectTimeout ( 20 * 1000 )
            // 设置全局服务器响应超时时间，单位毫秒
            .setReadTimeout ( 20 * 1000 ).setNetworkExecutor ( new OkHttpNetworkExecutor () ) );
        Logger.setTag ( "NoHttp-QingDongLi----" );
        Logger.setDebug ( true );// 开始NoHttp的调试模式, 这样就能看到请求过程和日志

        /** 005 获取屏幕信息*/
        setScreenSize ();

        /** 006 获取状态栏高度*/
        mStatusBarHeight =  com.peipao8.vehiclelock.LYangCode.utils.ImmersiveUtils.getStatusBarHeight ( getContext () );

        /** 007 获取全局的数据库*/
        getSQLdb () ;

        /** 001 友盟推送  */
        /** 002 腾讯BuglySdk */
        //        CrashReport.initCrashReport(getApplicationContext(), "1104628924", false);
        /** 003 Xutils 注入 */

        /** 004 LeakCanary 是 Android 和 Java 内存泄露检测框架。*/
        //        LeakCanary.install(this);

        /** 006 设置该CrashHandler为程序的默认处理器*/
//        CrashHandler.getInstance().init (getContext ());  //init方法必须在activity中再次初始化，传入可用的context
        //                Thread.setDefaultUncaughtExceptionHandler( new UnCeHandler (this));//这行使用简单的处理器，崩溃信息会显示

        /** 007 初始化云通讯*/

        /** 007 初始化应用文件夹目录*/

        /** 008 初始化ImagerLoader*/

        /** 009 保存当前的聊天界面所对应的联系人、方便来消息屏蔽通知*/
        setChattingContactId ();

        /** 010 初始化友盟相关注入*/
        youMengAnn ();



        /** 012 初始化生成OkHttpClient*/


        /** 014 获取设备id*/

        /** 016 获取全局数据库*/

    }

    /** 001.获得主线id */
    public static int getMainThreadId() {
        return mainThreadId;
    }

    /** 002.获得主线程 Handler */
    public static Handler getHandler() {
        return handler;
    }

    /** 003.获得全局的上下文对象.方便在任何类中调用到context.对象 */
    public static Context getContext() {
        return mContext;
    }

    /** 009 保存当前的聊天界面所对应的联系人、方便来消息屏蔽通知 */
    private void setChattingContactId() {
    }

    /** 010 初始化友盟相关注入 */
    private void youMengAnn() {

        /*分享平台*/
        //        //微信 appid appsecret
        //        PlatformConfig.setWeixin("wx2f5e6af75174f249", "96ef6aceec8caae2f071ee8d78049a84");
        //        //新浪微博 appkey appsecret
        //        PlatformConfig.setSinaWeibo("836006847","5505cf0ce965aa01682424f833236dc8");
        //        // QQ和Qzone appid appkey
        //        PlatformConfig.setQQZone("1104628924", "3UiViRjdwvUyjFFo");


        //定位sdk注入

        //分享注入

    }

    /** 011 application之系统内存不足的回调 */
    @Override
    public void onLowMemory() {
        super.onLowMemory ();
        System.gc ();//释放资源
    }

    public static void setScreenSize() {
        //		WindowManager manager = (WindowManager)  App.getContext ().getSystemService(Context
        // .WINDOW_SERVICE);
        //		Display       display = manager.getDefaultDisplay();
        //		Point         size    = new Point();
        //		display.getSize(size);
        //		mScreenWidth = size.x;
        //		mScreenHeigh = size.y;
        //方法二
        mScreenWidth = App.getContext ().getResources ().getDisplayMetrics ().widthPixels;
        mScreenHeigh = App.getContext ().getResources ().getDisplayMetrics ().heightPixels;
    }

    /**
     * 返回配置文件的日志开关  。工具方法。放在此application中。
     * log日志工具类会静态代码块调用此方法读取 Manifest.xml中的配置开关   .lin 我已配置为true
     */
    public boolean getLoggingSwitch() {
        try {
            ApplicationInfo appInfo = getPackageManager ().getApplicationInfo ( getPackageName (), PackageManager
                .GET_META_DATA );
            boolean b = appInfo.metaData.getBoolean ( "LOGGING" );
            return b;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ();
        }
        return false;
    }

    /** 维护一个管理Activity的集合 */
    private ArrayList<Activity> mList = new ArrayList<Activity> ();

    /** 添加Activity到管理Activity的集合 */
    public void addActivity(Activity activity) {
        if (!mList.contains ( activity )) {
            mList.add ( activity );
        }
    }

    /** 移除指定Activity从管理Activity的集合 */
    public void removeActivity(Activity activity) {
        if (mList.contains ( activity )) {
            mList.remove ( activity );
        }
    }

    /** 退出程序 */
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish ();
            }
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            System.exit ( 0 );
        }
        SofeZuJianManager.getSofeManager ().AppExit ( this );
        
        //清空通知栏
        NotificationManager manger = (NotificationManager) this.getSystemService ( NOTIFICATION_SERVICE );
        manger.cancelAll ();
        //杀死该应用进程
        android.os.Process.killProcess ( android.os.Process.myPid () );   //获取PID
        System.exit ( 0 );
    }

    /** 复写此方法完成 dex 多分包支持 ，解决 方法数 65536崩溃
     *  */
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext ( context );
        MultiDex.install ( this );
    }

    public static  SQLiteDatabase  getSQLdb() {
        if (db == null) {
            com.peipao8.vehiclelock.SqlDb.helper.DbHelper dbHelper = new com.peipao8.vehiclelock.SqlDb.helper.DbHelper (mContext );
            db = dbHelper.getWritableDatabase ();
        }
        return db ;
    }
}
