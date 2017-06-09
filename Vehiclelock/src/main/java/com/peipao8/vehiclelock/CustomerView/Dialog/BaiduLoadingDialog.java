package com.peipao8.vehiclelock.CustomerView.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.peipao8.vehiclelock.R;

/**
 * Ly 一个简单的dialogfragment。支持自定义内容
 */

public class BaiduLoadingDialog extends DialogFragment {

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
        View inflate = inflater.inflate ( R.layout.baidu_progress_bar, container, false );
        return inflate;
    }


}

