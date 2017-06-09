package com.peipao8.vehiclelock.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.peipao8.vehiclelock.App;
import com.peipao8.vehiclelock.BaseActivity;
import com.peipao8.vehiclelock.LYangCode.utils.SystemMethrodUtils;
import com.peipao8.vehiclelock.LYangCode.utils.UiUtils;
import com.peipao8.vehiclelock.LYangCode.utils.data.FileOperationManager;
import com.peipao8.vehiclelock.LYangCode.utils.data.SharePreManager;
import com.peipao8.vehiclelock.LYangCode.utils.log.LYangLogUtil;
import com.peipao8.vehiclelock.LYangCode.utils.permission.EasyPermissions;
import com.peipao8.vehiclelock.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeAndGuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    
    @BindView(R.id.version_tv)
    TextView  versionTv;
    @BindView(R.id.guide_viewPager)
    ViewPager guideViewPager;
    @BindView(R.id.welcome_imageView)
    ImageView welcomeImageView;
    
    private boolean misScrolled;
    //-----------------------------------请求权限标识code-----------------------------------
    public static final  int    REQUEST_CODE1 = 101;
    public static final  int    REQUEST_CODE2 = 102;
    public static final  int    REQUEST_CODE3 = 103;
    public static final  int    REQUEST_CODE4 = 104;
    /** 欢迎图 */
    private              int[]  pictureName   = new int[]{R.drawable.welcome1, R.drawable.welcome2, R.drawable
        .welcome3, R.drawable.welcome4};
    private static final String TAG           = "=====WelcomeAndGuideActivity";
    
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView ( R.layout.activity_welcome_and_guide );
        ButterKnife.bind ( this );
        initData ();
        initView ();
    }
    
    private void initData() {
        isBack = true; //确认使用基类的双击返回
        versionTv.setText ( SystemMethrodUtils.getVersion () );
        /**3 清除错误奔溃日志 */
        FileOperationManager.clearAllLog ( this );
        SharePreManager.saveStateFlag ( "isGetLocationPermission", false );  // 重置定位权限是否获取了标记
    }
    
    /** 初始化视图 */
    private void initView() {
        titleBarVisibility ( false );
        if (!SharePreManager.getIsFirst ( "isStartedApp" )) {    //软件被打开过吗？     /*没有打开过就进来*/
            welcomeImageView.setAnimation ( getTranslateAnimation () );    /*每次的欢迎页*/
            guideViewPager.setAdapter ( new ImagePagerAdapter () );
            guideViewPager.addOnPageChangeListener ( this );
        } else {
            requestPermiss ();
        }
    }
    
    
    /** 加载一个补间动画，向上退出的动画，并监听 */
    private Animation getTranslateAnimation() {
        final Animation animation = AnimationUtils.loadAnimation ( this, R.anim.tween_up_exit );
        animation.setAnimationListener ( new Animation.AnimationListener () {
            @Override
            public void onAnimationStart(Animation animation) { }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                /**动画结束，gone掉 Imageview 即 欢迎页 */
                welcomeImageView.setVisibility ( View.GONE );
                requestPermiss ();
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) { }
        } );
        return animation;
    }
    
    private void requestPermiss() { /**2.检查权限*/
        if (EasyPermissions.hasPermissions ( this, Manifest.permission.ACCESS_FINE_LOCATION )) {
            // 有权限    // 记录定位权限已经获取了,后面的定位回调会用
            SharePreManager.saveStateFlag ( "isGetLocationPermission", true );
            requestExternalStorage ();
        } else {
            EasyPermissions.requestPermissions ( this, "请求定位权限为用户提供定位,记录轨迹,无此权限软件无法使用,去开启吗?", REQUEST_CODE1, Manifest
                .permission.ACCESS_FINE_LOCATION );
        }
    }
    
    private void requestExternalStorage() { /**2.检查权限*/
        if (EasyPermissions.hasPermissions ( this, Manifest.permission.READ_EXTERNAL_STORAGE )) {
            // 有权限    // 读取存储卡上传头像等获取了,后面的上传头像图片会用
            SharePreManager.saveStateFlag ( "isGetExternalPermission", true );
            requestBluetoohPermission ();
        } else {
            EasyPermissions.requestPermissions ( this, "请求读取存储卡上传头像等，无此权限软件无法使用,去开启吗?", REQUEST_CODE2, Manifest
                .permission.READ_EXTERNAL_STORAGE );
        }
    }
    
    /** 紧接着请求蓝牙权限 */
    private void requestBluetoohPermission() { /**3.紧接着请求蓝牙权限*/
        if (EasyPermissions.hasPermissions ( this, Manifest.permission.ACCESS_COARSE_LOCATION )) {
            requestDeviceIDPermission ();
        } else {
            EasyPermissions.requestPermissions ( this, "请求使用蓝牙权限,禁止此权限," + "软件将无法使用导致退出,是否去开启此权限?", REQUEST_CODE3,
                Manifest.permission.ACCESS_COARSE_LOCATION );
        }
    }
    
    /** 紧接着请求设备id */
    private void requestDeviceIDPermission() { /**紧接着请求设备id*/
        if (EasyPermissions.hasPermissions ( this, Manifest.permission.READ_PHONE_STATE )) {
            // 有权限    // 获取手机imei权限已经获取了,后面的定位回调会用
            //            AppConfig.deviceId = DeviceId.getDeviceId(this);
            if (SharePreManager.getIsFirst ( "isStartedApp" )) {    //软件被打开过吗？     /*有打开过就进来*/
                startMainActivity ( 2000 );
            }
        } else {
            EasyPermissions.requestPermissions ( this, "请求获取设备标示,禁止此权限," + "软件将无法使用导致退出,是否去开启此权限?", REQUEST_CODE4,
                Manifest.permission.READ_PHONE_STATE );
        }
    }
    
    /** 权限被拒绝 */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == 101) {
            UiUtils.toast ( "禁止获取设备位置会导致无法定位,可手动开启" );
            requestExternalStorage ();
        } else if (requestCode == 102) {
            UiUtils.toast ( "禁止读取存储卡权限，软件将无法使用，等待退出......" );
            welcomeImageView.postDelayed ( new Runnable () {
                @Override
                public void run() {
                    App.getInstance ().exit ();
                }
            }, 1000 );
        } else if (requestCode == 103) {
            UiUtils.toast ( "禁止获取蓝牙使用权限，软件将无法使用，等待退出......" );
            welcomeImageView.postDelayed ( new Runnable () {
                @Override
                public void run() {
                    App.getInstance ().exit ();
                }
            }, 1000 );
        } else if (requestCode == 104) {
            UiUtils.toast ( "禁止获取设备ID权限，软件将无法使用，等待退出......" );
            welcomeImageView.postDelayed ( new Runnable () {
                @Override
                public void run() {
                    App.getInstance ().exit ();
                }
            }, 1000 );
        }
    }
    
    /** 检查没有权限后，请求权限的同意回调，同意都会走进来 */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted ( requestCode, perms );
        if (requestCode == 101) {
            requestExternalStorage ();
        } else if (requestCode == 102) {
            requestBluetoohPermission ();
        } else if (requestCode == 103) {
            requestDeviceIDPermission ();
        } else if (requestCode == 104) {
            LYangLogUtil.d ( TAG, "onPermissionsGranted--> 所有启动权限已经获取=" + requestCode );    //调试输出----
            if (SharePreManager.getIsFirst ( "isStartedApp" )) {    //软件被打开过吗？     /*有打开过就进来*/
                /** 初始化获取七牛uptoken。初始化。*/
                startMainActivity ( 1000 );
            }
        }
    }
    
    /** //获取到权限就开启主页 */
    private void startMainActivity(int time) {
        new Handler ().postDelayed (  ()-> {
                //不是第一次进入,启动闪屏进入主页面动画
                startActivity ( new Intent ( WelcomeAndGuideActivity.this, LoginActivity.class ) );
//                startActivity ( new Intent ( WelcomeAndGuideActivity.this, DownLoadActivity.class ) );
//                startActivity ( new Intent ( WelcomeAndGuideActivity.this, MainActivity.class ) );
                //overridePendingTransition ( R.anim.slide_in_down, R.anim.slide_out_up );
                WelcomeAndGuideActivity.this.finish ();
                }
        , time );
    }
    
    @SuppressWarnings("deprecation")
    private class ImagePagerAdapter extends PagerAdapter {
        
        @Override
        public int getCount() {
            if (pictureName == null) {
                return 0;
            } else {
                return pictureName.length;
            }
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        
        @Override
        public void destroyItem(View container, int position, Object object) {
            ImageView itemView = (ImageView) object;
            ( (ViewGroup) container ).removeView ( itemView );
        }
        
        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged ();
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int       path     = pictureName[ position ];
            ImageView itemView = new ImageView ( WelcomeAndGuideActivity.this );
            itemView.setScaleType ( ImageView.ScaleType.CENTER_CROP );
            itemView.setImageResource ( path );
            container.addView ( itemView );
            return itemView;
        }
    }
    
    
    //===================================ViewPage监听===================================
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
    
    @Override
    public void onPageSelected(int position) { }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:      /**拖动页面*/
                misScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:      /**拖动到位*/
                misScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:          /**拖动完的空闲*/
                if (guideViewPager.getCurrentItem () == guideViewPager.getAdapter ().getCount () - 1 && !misScrolled) {
                    SharePreManager.saveIsFirst ( "isStartedApp", true );
                    startMainActivity ( 1 );
                    WelcomeAndGuideActivity.this.finish ();
                }
                misScrolled = true;
                break;
        }
    }
}
