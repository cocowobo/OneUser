/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peipao8.vehiclelock;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.peipao8.vehiclelock.LYangCode.CustomerView.dialog.ImageDialog;
import com.peipao8.vehiclelock.LYangCode.nohttp.CallServer;
import com.peipao8.vehiclelock.LYangCode.utils.SofeZuJianManager;
import com.peipao8.vehiclelock.LYangCode.utils.UiUtils;
import com.peipao8.vehiclelock.LYangCode.utils.permission.EasyPermissions;
import com.peipao8.vehiclelock.LYangCode.utils.time.DateUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;


/**
 * 基类.
 * 1.使用了协调者布局.ToolBar和AppBarLayout
 */
public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    public Object cancelObject = new Object ();
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout      mAppBarLayout;
    public  Toolbar           mToolbar;
    private FrameLayout      mContentLayout;
    
    /** 记录点击是否重复 */
    private int  flag = 0; //flag是正在进行中的任务的判断参数,后期可以改成各种判断
    private long date = 3;
    
    /** 当前日期和时间 */
    private long nowDate   ;
    /** 上次时间 */
    private long aboveDate   ;
    
    /** 是否使用BaseActivity的双击返回 */
    public boolean isBack = false;
    private AlertDialog          msgDialog;
    private AlertDialog          imgDialog;
    private SystemBarTintManager tintManager;
    
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        SofeZuJianManager.getSofeManager ().addActivity ( this );    /**添加所有activity到管理栈中*/
        setTranslucentStatus ( true );
//        createTranslucenceStateBar ( this );                        /**开启沉浸式状态栏*/
        getDelegate ().setContentView ( R.layout.activity_base );      /**加载布局*/
        
        mCoordinatorLayout = (CoordinatorLayout) findViewById ( R.id.coordinatorlayout );    /**协调者布局*/
        mAppBarLayout = (AppBarLayout) findViewById ( R.id.appbarlayout );                   /**拉伸布局*/
        mToolbar = (Toolbar) findViewById ( R.id.toolbar );
        mContentLayout = (FrameLayout) findViewById ( R.id.content );
        
        setSupportActionBar ( mToolbar );                                                    /**替换actionbar为toolbar*/
//        setBackBar ( true );
        titleBarVisibility ( true );
        onActivityCreate ( savedInstanceState );
    }
    
    /** ----此方法出现的时机是,oncreate方法中完成了前期准备工作,然后把oncreate的后续工作抽象出去,去后面实现 */
    protected abstract void onActivityCreate(Bundle savedInstanceState);
    
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window win = getWindow ();
        /**添加此属性开启的不是沉浸式,而是页面全屏,效果一样区别是,页面全屏则xml中组件设置的fitsSystemWindows等属性无效
         * 而且给状态栏主题设置的colorPrimary等颜色属性也是无效的.也即是页面全屏会造成状态栏的各种属性无效
         * 因为没有调用改变状态栏颜色的方法---> mActivity.getWindow ().setStatusBarColor ( color )(api23的)*/
        WindowManager.LayoutParams winParams = win.getAttributes ();
        final int                  bits      = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes ( winParams );
        //-------------------------------------
        tintManager = new SystemBarTintManager ( this );
        tintManager.setStatusBarTintEnabled ( true );
        tintManager.setNavigationBarTintEnabled ( true );
        setStatusBarColor ( R.color.translucent_rel );//第三方提供的兼容4.4的设置状态栏颜色方法
    }
    
    /**
     * 在4.4版本之上的设置状态栏颜色的方法.
     * 此方法是第三方库提供的,比布局主题中设置的状态栏颜色执行迟.也就是以此方法设置的颜色为准
     */
    public void setStatusBarColor(int colorID) {
        if (tintManager != null) {
            tintManager.setTintColor ( UiUtils.getColors ( colorID ) );
        }
    }
    
    /**02. findviewbyid的封装 直接使用*/
    public <T extends View> T findView(int viewId) {
        return (T) mContentLayout.findViewById ( viewId );
    }
    
    /** 03. 得到协调者布局 */
    public CoordinatorLayout getCoordinatorLayout() {
        return mCoordinatorLayout;
    }
    
    /** 04. 得到内容布局 */
    public FrameLayout getContentLayout() {
        return mContentLayout;
    }
    
    /** 05. 得到拉伸布局 */
    public AppBarLayout getAppBarLayout() {
        return mAppBarLayout;
    }
    
    /**06. 得到toolbar*/
    public Toolbar getToolbar() {
        return mToolbar;
    }
    
    /**06. 得到Actionbar
     * 当ToolBar被设置为App Bar，可通过getSupportActionBar()方法获取到一个的ActionBar对象，
     * 通过该对象引用可对ToolBar做更多操作； */
    protected void setmToolbarView(int resId) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        View view = getLayoutInflater().inflate(resId, null);
        getSupportActionBar().setCustomView(view,
        new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
    }
    
    /**
     * 隐藏或者开启沉浸式状态栏
     * 给拉伸布局拉伸到状态栏.或者取消掉拉伸到状态栏,同时设置toolbar是否显示
     */
    public void titleBarVisibility(boolean chanage) {
        if (chanage) {
            setImmerseLayout ( getToolbar (), App.mStatusBarHeight );
            getToolbar ().setVisibility ( View.VISIBLE );
        } else {
            //AppBarLayout随着toolbar消失高度为0.但是padding还在必须手动取消掉
            setImmerseLayout ( getToolbar (), -App.mStatusBarHeight );
            getToolbar ().setVisibility ( View.GONE );
        }
    }
    
    /** 07. 设置标题 */
    @Override
    public void setTitle(CharSequence title) {
        mToolbar.setTitle ( title );
    }
    
    /** 07. 设置标题(根据本地string的id) */
    @Override
    public void setTitle(int titleId) {
        mToolbar.setTitle ( titleId );
    }
    
    /** 08.设置副标题内容 */
    public void setSubtitle(CharSequence title) {
        mToolbar.setSubtitle ( title );
    }
    
    /** 08.设置副标题内容 */
    public void setSubtitle(int titleId) {
        mToolbar.setSubtitle ( titleId );
    }
    
    /** 08.设置副标题颜色 */
    public void setSubtitleTextColor(int color) {
        mToolbar.setSubtitleTextColor ( color );
    }
    
    /** 08.设置正标题颜色 */
    public void setTitleTextColor(int color) {
        mToolbar.setTitleTextColor ( color );
    }
    
    /** 08.设置是否支持返回按钮 */
    public void setBackBar(boolean isShow) {
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( isShow );
        getSupportActionBar ().setDefaultDisplayHomeAsUpEnabled ( false );
    }
    
    /** 08.设置布局内容的颜色背景 */
    public void setContentBackground(int color) {
        mContentLayout.setBackgroundResource ( color );
    }
    
    /** 09.本来以前都是在activity中直接调用的,现在发现可以这么用 */
    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews ();               /**先移除所有view.*/
//        mContentLayout.addView ( getAppBarLayout () );
//        setImmerseLayout ( this,mContentLayout,getAppBarLayout ().getHeight () );
        getLayoutInflater ().inflate ( layoutResID, mContentLayout, true );  /**加载布局文件,有root布局,并且需要依附*/
    }
    
    /** 10.直接将现成的view添加到内容布局里面,但是不带参数,这个会清楚之前添加的view */
    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews ();
        mContentLayout.addView ( view );
    }
    /** 10.直接将现成的view添加到内容布局里面,这个可以带参数,这个不会清楚之前添加的view */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.removeAllViews ();
        mContentLayout.addView ( view, params );
    }
    
    /** 11.得到布局文件代表的view对象，也就是toolbar对象，添加到appbarlayout，并且重新设置支持的toolbar */
    public void setToolBarView(int layoutResID) {
        mAppBarLayout.removeAllViews ();
        mToolbar= (Toolbar)getLayoutInflater().inflate(layoutResID, getAppBarLayout (),false);
        mAppBarLayout.addView ( mToolbar );                 //这样添加view的宽高自己提前测量好
        setSupportActionBar ( mToolbar );
    }
    
    /**
     * 11.Activity的父类里面已经封装好了menu方法。你只需要重写父类的onCreateOptionMenu（）方法和onOptionsItemSelected（）
     * 即可,继承此方法,但是我们没有继承onCreateOptionMenu方法.不知道为什么
     */
    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId () == android.R.id.home) {
            finish ();
            return true;
        }
        return onOptionsItemSelectedCompat ( item );       /**默认返回true.看后面继承者怎么写了.*/
    }
    
    public boolean onOptionsItemSelectedCompat(MenuItem item) {
        return false;
    }
    
    /** 获得内容布局 */
    public ViewGroup getContentRoot() {
        return mContentLayout;
    }
    
    public void showMessageDialog(int title, int message) {
        showMessageDialog ( getText ( title ), getText ( message ) );
    }
    
    public void showMessageDialog(int title, CharSequence message) {
        showMessageDialog ( getText ( title ), message );
    }
    
    public void showMessageDialog(CharSequence title, int message) {
        showMessageDialog ( title, getText ( message ) );
    }
    
    public void showMessageDialog(CharSequence title, CharSequence message) {
        if (msgDialog != null && !msgDialog.isShowing ()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder ( this );
            builder.setTitle ( title );
            builder.setMessage ( message );
            builder.setPositiveButton ( R.string.know, new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss ();
                }
            } );
            msgDialog = builder.show ();
        }
    }
    
    /** 显示图片dialog */
    public void showImageDialog(CharSequence title, Bitmap bitmap) {
        if (imgDialog != null && !imgDialog.isShowing ()) {
            ImageDialog imageDialog = new ImageDialog ( this );
            imageDialog.setTitle ( title );
            imageDialog.setImage ( bitmap );
            imgDialog = imageDialog.show ();
        }
    }
    
    @Override
    protected void onDestroy() {
        CallServer.getRequestInstance ().cancelBySign ( cancelObject );  //取消所有NohHttp请求
        super.onDestroy ();
    }
    
    /** 权限授予请求转交 */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
        // 将结果转发给EasyPermissions  ,下面的 权限是否请求成功回答才走
        EasyPermissions.onRequestPermissionsResult ( requestCode, permissions, grantResults, this );
    }
    
    /** 权限被授予 */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) { }
    
    /** 权限被拒绝 */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) { }
    
    
    //-----------------------------------沉浸式状态栏方法(一) 设置4.4和5.1都为全透明式状态栏,-----------------------------------
    
    /**
     * 设置4.4和5.1都为全透明式状态栏 ,下面的所有半透其实都为全透字
     * 用法.在titleview上配置
     * android:fitsSystemWindows="true"
     * android:clipToPadding="true"
     * 上面的属性,开启适配状态栏,让最上端view的padding增加状态栏高度,  (只有当程序布局延伸到状态栏才会触发生效),
     * 调用此方法传入activity,先开启透明状态栏
     * 5.1清除透明状态栏特性后,从activity中寻找窗体对象设置状态栏颜色为半透明,
     * 4.4 找到父窗体在被覆盖的状态栏下的位置放一个制定颜色的view,同时将状态栏设置为半透明色以便看到下面的view
     */
    public void createTranslucenceStateBar(Activity activity) {
        // 大于5.1  ,透明是半透明的,但是部分定制rom厂商,在系统中去掉了半透明,变成了全透明,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createStateBarColor ( activity, 0 );
            return;
        }
        // 4.4到5.1系统透明是渐变透明的,但是部分定制rom厂商,在系统中去掉了渐变透明,变成了全透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES
            .LOLLIPOP) {
            Window window = activity.getWindow ();
            //必须开始透明状态栏,才会让顶部view,嵌入状态栏下
            window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );  //设置透明状态栏 ,让顶部view上移
            window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION );
            // 原生4.4上.是渐变透明的, 再放个半透明,那就是渐变加半透明
            // 定制4.4.是全透明的,放个半透明,那就是半透明,
            //            ViewGroup contentView   = (ViewGroup) mActivity.findViewById ( android.R.id.content );
            // 找到整个窗口
            //            View      statusBarView = new View ( mActivity );      //新建布局
            //            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams ( ViewGroup.LayoutParams.MATCH_PARENT,
            //                getStatusBarHeight ( mActivity ) );     //布局的宽高参数为设置为状态栏宽高
            //            statusBarView.setBackgroundColor ( COLOR_DEFAULT );       // 布局背景色
            //            //添加布局.到状态栏位置,因为整个窗口是个fragment,则压在了原来的布局上
            //            contentView.addView ( statusBarView, lp );
        }
    }
    //-----------------------------------沉浸式状态栏方法(五) 设置 5.1都为半透明式状态栏,和透明状态栏,或者自定义颜色-----------------------------------
    
    /**
     * 设置 5.1都为半透明式状态栏的第二种方式
     * 用法.在titleview上配置
     * android:fitsSystemWindows="true"
     * android:clipToPadding="true"
     * 上面的属性,开启适配状态栏,让最上端view的padding增加状态栏高度(只有当程序布局延伸到状态栏才会触发生效),
     * 调用此方法传入activity,来获得整个窗体,并设置系统属性 --应用程序布局全屏  ,这样不用开启透明状态栏特性
     * 5.1从activity中寻找窗体对象设置状态栏颜色,color 为-1 则是白色背景,是0.则是全透明,
     * 除了-1和0之外的其他值代表的是此次设置颜色的色值由colorPrimary或者statusBarColor决定,
     * 正常色值不用管
     */
    public static void createStateBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow ().getDecorView ();
            int  option    = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN; //应用程序布局全屏
            decorView.setSystemUiVisibility ( option );
            if (color == 1) {
                return;
            }
            activity.getWindow ().setStatusBarColor ( color ); //再设置状态栏透明色
            return;
        }
    }
    
    /**
     * 设置4.4和5.1都为   全屏式透明式状态栏
     * 调用此方法传入activity,先开启透明状态栏
     * 注意:::找到了主布局对象的父view去了,是linerlayout
     */
    public void createTranslucenceStateBarFive(Activity activity) {
        Window window = activity.getWindow ();
        window.addFlags ( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
        /**主布局文件framLayout*/
        ViewGroup mContentView = (ViewGroup) activity.findViewById ( Window.ID_ANDROID_CONTENT );
        /**fragmlayout外层的系统级的linerlayout*/
        ViewGroup mContentParent = (ViewGroup) mContentView.getParent ();
        
        //最外层linerlayout ,取出第一个,要么是framlayout要么是我们手动添加到此级别的假view
        View statusBarView = mContentParent.getChildAt ( 0 );
        if (statusBarView != null && statusBarView.getLayoutParams () != null && statusBarView.getLayoutParams ()
            .height == App.mStatusBarHeight) {
            //移除假的 View
            mContentParent.removeView ( statusBarView );
        }
        //ContentView 不预留空间
        if (mContentParent.getChildAt ( 0 ) != null) {  //移除假view后或者就没有假view后,设置framlayout不适配高度
            ViewCompat.setFitsSystemWindows ( mContentParent.getChildAt ( 0 ), false );
        }
        //ChildView 不预留空间
        View mChildView = mContentView.getChildAt ( 0 ); //这是我们的布局文件的根布局
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows ( mChildView, false );  //不适配高度
        }
    }
    /** 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     * @return 返回状态栏高度的像素值。
     */
    //-----------------------------------沉浸式状态栏方法(四) 手动设置嵌入状态栏的view的padding高度,-----------------------------------
    
    /**
     * 传入view设置上padding值为状态栏高度,其余方位padding不变
     * 无需设置下面两个属性
     * android:fitsSystemWindows="true"
     * android:clipToPadding="true"
     * 因为一般是对titleview进行此方法,来设置上padding,来避免沉浸式后title上移,
     * 而上面两个配置就是为了避免上移的,所以不需要
     * 注意:一般使用此方法,应该已经开启了透明状态栏,让顶部view嵌入状态栏
     */
    public static void setImmerseLayout( View view, int topCha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int paddingLeft   = view.getPaddingLeft ();// 得到传入view当前时刻的PaddingLeft值
            int paddingRight  = view.getPaddingRight ();// 得到传入view当前时刻的PaddingRight值
            int paddingBottom = view.getPaddingBottom ();// 得到传入view当前时刻的paddingBottom值
            int paddingTop    = view.getPaddingTop () + topCha;// 得到传入view当前时刻的paddingBottom值
            view.setPadding ( paddingLeft, paddingTop, paddingRight, paddingBottom );
        }
    }
    /**设置view的上padding*/
    public static void setMarginLayout(View view, int topCha) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(0, topCha, 0,0);
        view.setLayoutParams(layoutParams);
    }
    /**设置view的在相对布局中的marginTop*/
    public static void setMarginLayout2(View view, int topCha) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(0, topCha+layoutParams.topMargin, 0,0);
        view.setLayoutParams(layoutParams);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction () == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus ();
            if (isShouldHideInput ( v, ev )) {
                hideSoftInput ( v.getWindowToken () );
            }
        }
        return super.dispatchTouchEvent ( ev );
    }
    
    /** 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏 */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && ( v instanceof EditText )) {
            int[] l = {0, 0};
            v.getLocationInWindow ( l );
            int left = l[ 0 ], top = l[ 1 ], bottom = top + v.getHeight (), right = left + v.getWidth ();
            if (event.getX () > left && event.getX () < right && event.getY () > top && event.getY () < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }
    
    /** 多种隐藏软件盘方法的其中一种 */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService ( Context.INPUT_METHOD_SERVICE );
            im.hideSoftInputFromWindow ( token, InputMethodManager.HIDE_NOT_ALWAYS );
        }
    }
    
    
    /** 双击返回退出程序 */
    public void onBackPressed() {
        if (isBack) {
            if (flag == 1) {
                //判断是否后台运行任务1，是则提醒用户先停止记步
                UiUtils.toast ( "请先结束任务1" );
            } else if (flag == 2) {
                //判断是否后台运行任务2，是则提醒用户先停止记步
                UiUtils.toast ( "请先结束任务2" );
            } else {    //未运行任务
                nowDate = System.currentTimeMillis ();
                if (DateUtil.isOverTime ( aboveDate, nowDate, 3 )) {
                    //时间间隔大于3秒
                    UiUtils.toast ( "再按一次退出陪跑" );
                    aboveDate =System.currentTimeMillis ();
                } else {
                    App.getInstance ().exit ();
                }
            }
        } else {
            this.finish ();
        }
    }
}
