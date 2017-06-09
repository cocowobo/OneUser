package com.peipao8.vehiclelock.CustomerView.Dialog;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peipao8.vehiclelock.CustomerView.progressBar.HorizontalProgressBarWithNumber;
import com.peipao8.vehiclelock.LYangCode.nohttp.CallServer;
import com.peipao8.vehiclelock.LYangCode.utils.UiUtils;
import com.peipao8.vehiclelock.R;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 下载弹窗,展示进度条,下载速度和执行操作
 * ##此类绑定了butterknife库
 */

public class UpdataDownload extends DialogFragment {
    private  int progress = 0;
    private  boolean isFinish = false;
    /**标题总布局*/@BindView(R.id.title_ll)
    LinearLayout                    titleLl;
    /**内容总布局*/@BindView(R.id.content_ll)
    LinearLayout                    contentLl;
    /**取消按钮*/@BindView(R.id.cancal_tv_def)
    TextView                        cancalTvDef;
    /**确定按钮*/@BindView(R.id.confirm_tv_def)
    TextView                        confirmTvDef;
    /**标题显示*/@BindView(R.id.title_tv_def)
    TextView                        titleTvDef;
    /**内容显示*/@BindView(R.id.content_tv_def)
    TextView                        contentTvDef;
    /**点击区域总布局*/@BindView(R.id.click_ll)
    LinearLayout                    clickLl;
    /**整个界面总布局*/@BindView(R.id.total_layout)
    LinearLayout                    totalLayout;
    /**整个界面总布局*/@BindView(R.id.download_progress)
    HorizontalProgressBarWithNumber downloadProgress;
    /** 下载请求 */
    private DownloadRequest                    mDownloadRequest;
    private SimpleDialogFragmentClickListenter listener ;
    private int requestCode = 0;
    
    private String downloadUrl = "http://gdown.baidu.com/data/wisegame/4f45d1baacb6ee7f/baidushoujizhushouyuan91zhu_16789458.apk";
    private int isForceInstall = 2;  //1为强制更新,2,无所谓
    private String downloadDir;
    
    @Override
    public void onStart() { //在onStart()中
        super.onStart();
        //        Window                     window = getDialog().getWindow();
        //        WindowManager.LayoutParams lp     = window.getAttributes();
        //        lp.gravity = Gravity.BOTTOM; //底部
        //        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //        window.setAttributes(lp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog ().requestWindowFeature ( Window.FEATURE_NO_TITLE );
        getDialog ().setCanceledOnTouchOutside ( false );
        // 0~1 , 1表示完全昏暗   ，背景透明度
        getDialog().getWindow().setDimAmount(0.5f);
        View inflate = inflater.inflate ( R.layout.dialogfragment_updata_download, container, false );
        ButterKnife.bind ( this, inflate );
        File dir = new File ( Environment.getExternalStorageDirectory ().getAbsolutePath () + "/Download" );
        if (!dir.exists()) {          //创建下载目录
            dir.mkdirs();
        }
        downloadDir = dir.getAbsolutePath ();
        return inflate;
    }

    /**回调接口，抽象类*/
    public abstract class SimpleDialogFragmentClickListenter{
        public void cancle(int code){
            dismiss ();
        }
        public void confirm(int code){
            dismiss ();
        }
    }

    /**设置监听。code为对应的标示，区分不同的请求，*/
    public void setListener(SimpleDialogFragmentClickListenter listener,int code){
        this.listener = listener ;
        requestCode = code;
    }

    @OnClick({R.id.cancal_tv_def, R.id.confirm_tv_def})
    public void onClick(View view) {
        switch (view.getId ()) {
            case R.id.cancal_tv_def:
                clickCancle();
                break;
            case R.id.confirm_tv_def:
                clickConfirm();
                break;
        }
    }

    /**点击取消。new匿名类的可以实现*/
    public void clickCancle() {
        if (listener == null) {
            getDialog ().dismiss ();
            if (mDownloadRequest != null) {
                mDownloadRequest.cancel ();
            }
            if (isForceInstall == 1) {
                System.exit ( 0 );
            }
        } else {
            listener.cancle (requestCode);
        }
    }
    /**点击取消。new匿名类的可以实现*/
    public void clickConfirm() {
//        if (listener == null) {
//            getDialog ().dismiss ();
//        } else {
//            listener.confirm (requestCode);
//        }
        if (isFinish) {
            installApk ();
        } else {
            udapteDownload (  );
        }
    }
    
    private void installApk() {
        String fileName      = downloadDir+"/青动力.apk";
        Intent intent   = new Intent (Intent.ACTION_VIEW);
        intent.setDataAndType( Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
        startActivity(intent);
    }
    
    public void udapteDownload() {
    
        if (mDownloadRequest != null && mDownloadRequest.isStarted() && !mDownloadRequest.isFinished()) {
            // 暂停下载。
            setButtonString_ ( "取消","继续" );
            mDownloadRequest.cancel();
        } else if (mDownloadRequest == null || mDownloadRequest.isFinished()) {// 没有开始或者下载完成了，就重新下载。
        
            /**
             * 如果使用断点续传的话，一定要指定文件名喔。
             */
            // url 下载地址。
            // fileFolder 保存的文件夹。
            // fileName 文件名。
            // isRange 是否断点续传下载。
            // isDeleteOld 如果发现存在同名文件，是否删除后重新下载，如果不删除，则直接下载成功。
            mDownloadRequest = NoHttp.createDownloadRequest(downloadUrl,downloadDir , "青动力.apk", true, true);
        
            // what 区分下载。
            // downloadRequest 下载请求对象。
            // downloadListener 下载监听。
            CallServer.getDownloadInstance().add(10000, mDownloadRequest, downloadListener);
        
        }
    }

    /**设置取消返回按钮的字体大小*/
    public void setButtonSize_( int cancleSize , int confirmSize){
        cancalTvDef.setTextSize ( cancleSize );
        confirmTvDef.setTextSize ( confirmSize );
    }
    
    /**设置下载地址*/
    public void setDownLoadUrl( String url){
        downloadUrl = url;
    }
    
    
    /**设置是否强制安装*/
    public void setForceInstll( int updataStatus){
        isForceInstall = updataStatus;
    }
    
    /**设置取消返回按钮的字体颜色*/
    public void setButtonColor_( int cancleColor , int confirmColor){
        cancalTvDef.setTextColor ( cancleColor );
        confirmTvDef.setTextColor ( confirmColor );
    }

    /**设置取消返回按钮的内容*/
    public void setButtonString_( String cancle , String confirm){
        cancalTvDef.setText ( cancle );
        confirmTvDef.setText ( confirm );
    }
    /**设置是否显示按钮,true表示是否完全隐藏*/
    public void setCancalButtonVisiable_( boolean isCancalVis ){
        if (isCancalVis) {
            cancalTvDef.setVisibility ( View.INVISIBLE );
        } else {
            cancalTvDef.setVisibility ( View.GONE );
        }
    }
    /**设置是否显示按钮,true表示是否完全隐藏*/
    public void setConfirmButtonVisiable_(  boolean isConfirmVis ){
        if (isConfirmVis) {
            confirmTvDef.setVisibility ( View.INVISIBLE);
        } else {
            confirmTvDef.setVisibility ( View.GONE);
        }
    }

    /**设置自定义标题字体颜色和大小*/
    public void setTitleColorAndSize_( int titleColor , int titleSize){
        titleTvDef.setTextColor ( titleColor );
        titleTvDef.setTextSize ( titleSize );
    }

    /**设置自定义标题的高度*/
    public void setTitleHeight_( int titleHeight){
        titleTvDef.setHeight ( titleHeight );
    }

    /**设置标题总布局的背景颜色*/
    public void setTitleBackColor_( int backColor){
        titleLl.setBackgroundColor ( backColor );
    }

    /**设置标题总布局的显示内容*/
    public void setTitleString_( String title){
        titleTvDef.setText ( title );
    }

    /**设置内容区显示内容*/
    public void setContentString_( String title){
        contentTvDef.setText ( title );
    }

    /**设置内容区显示字体大小与颜色*/
    public void setContentColorAndSize_(  int contentColor , int contentSize){
        contentTvDef.setTextColor ( contentColor );
        contentTvDef.setTextSize ( contentSize );
    }

    /**设置标题总布局的背景颜色*/
    public void setContentBackColor_( int backColor){
        contentLl.setBackgroundColor ( backColor );
    }
    /**
     * 下载监听
     */
    private DownloadListener downloadListener = new DownloadListener() {
        
        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
            if (allCount != 0) {
                progress = (int) (beforeLength * 100 / allCount);
                downloadProgress.setProgress(progress);
            }
            setButtonString_ ( "取消","暂停" );
        }
    
        @Override
        public void onProgress(int what, int progress, long fileCount, long speed) {
            downloadProgress.setProgress(progress);
        }
    
        @Override
        public void onDownloadError(int what, Exception exception) {
            String message = getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = getString(R.string.download_error_url);
            } else {
                messageContent = getString(R.string.download_error_un);
            }
            UiUtils.toast ( getActivity (),messageContent );
        }
        
        @Override
        public void onFinish(int what, String filePath) {
            if (isForceInstall == 1) {
                setButtonString_ ( "退出程序", "安装" );
            } else {
                setButtonString_ ( "返回", "安装" );
            }
            isFinish = true;
        }
        
        @Override
        public void onCancel(int what) {
            UiUtils.toast ( getActivity (),"下载取消" );
        }
    };
}

