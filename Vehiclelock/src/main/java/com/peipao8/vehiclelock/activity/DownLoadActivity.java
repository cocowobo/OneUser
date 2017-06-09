package com.peipao8.vehiclelock.activity;

import android.os.Bundle;
import android.widget.Button;

import com.peipao8.vehiclelock.BaseActivity;
import com.peipao8.vehiclelock.CustomerView.Dialog.UpdataDownload;
import com.peipao8.vehiclelock.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : 大胸平天下
 * Author     : 太阳光辉
 * Date       : 2017/3/24 11:54
 */

public class DownLoadActivity extends BaseActivity {
    @BindView(R.id.btn_start_download)
    Button btnStartDownload;
    
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView ( R.layout.activity_download );
        ButterKnife.bind ( this );
        titleBarVisibility ( false );
    }
    
    
    @OnClick(R.id.btn_start_download)
    public void onClick() {
        UpdataDownload updataDownload = new UpdataDownload ();
        updataDownload.setCancelable(false);
        updataDownload.show ( this.getFragmentManager (),"download" );
    }
}
