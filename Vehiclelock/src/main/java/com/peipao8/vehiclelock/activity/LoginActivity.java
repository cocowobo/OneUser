package com.peipao8.vehiclelock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.peipao8.vehiclelock.BaseActivity;
import com.peipao8.vehiclelock.LYangCode.nohttp.ConstantValue;
import com.peipao8.vehiclelock.LYangCode.nohttp.HttpListener;
import com.peipao8.vehiclelock.LYangCode.nohttp.UrlConstants;
import com.peipao8.vehiclelock.LYangCode.utils.EncryptUtils;
import com.peipao8.vehiclelock.LYangCode.utils.UiUtils;
import com.peipao8.vehiclelock.LYangCode.utils.check.LoginCheckUtil;
import com.peipao8.vehiclelock.LYangCode.utils.data.DataUtils;
import com.peipao8.vehiclelock.LYangCode.utils.data.SharePreManager;
import com.peipao8.vehiclelock.LYangCode.utils.json.FastJsonUtils;
import com.peipao8.vehiclelock.LYangCode.utils.log.LYangLogUtil;
import com.peipao8.vehiclelock.NetRequest.UserInforRequest;
import com.peipao8.vehiclelock.R;
import com.peipao8.vehiclelock.model.BaseResult;
import com.peipao8.vehiclelock.model.user.LoginBeen;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : 大胸平天下
 * Author     : 太阳光辉
 * Date       : 2017/3/22 16:43
 */

public class LoginActivity extends BaseActivity {
    private final String TAG = "=====LoginActivity";
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button   btnLogin;
    @BindView(R.id.tv_issue)
    TextView tvIssue;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_webchat)
    TextView tvWebchat;
    @BindView(R.id.tv_sina)
    TextView tvSina;
    
    
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView ( R.layout.activity_login );
        ButterKnife.bind ( this );
        initData ();
        initView ();
    }
    
    private void initData() {
        isBack = true; //确认使用基类的双击返回
        String phone     = SharePreManager.getValue ( "userInfo", "phone", 0 );
        String passsword = SharePreManager.getValue ( "userInfo", "password", 0 );
        LYangLogUtil.d ( TAG, "initData--> passsword=" + passsword );    //调试输出----
        if (!DataUtils.isEmptyStrict ( phone ) && !DataUtils.isEmptyStrict ( passsword )) {
            etPhone.setText ( phone );
            etPassword.setText ( "******" );
            userLogin ( phone, EncryptUtils.encryptMD5ToString(passsword) );
        }
    }
    
    private void userLogin(String phone, String passsword) {
        Map<String, String> params = new HashMap<> ();
        params.put ( "phone", phone );
        params.put ( "passsword", passsword );
        UserInforRequest.userLogin ( ConstantValue.what1002, UrlConstants.loginUrl, cancelObject, params, new
            HttpListener<BaseResult> () {
            @Override
            public void onSucceed(int what, Response<BaseResult> response) {
                BaseResult baseResult = response.get ();
                if (baseResult.code.equals ( "10206" )) {
                    UiUtils.toast ( LoginActivity.this, "该手机号未注册" );
                } else if (baseResult.code.equals ( "10207" )) {
                    UiUtils.toast ( LoginActivity.this, "密码错误" );
                } else if (baseResult.code.equals ( "200" )) {
                    UiUtils.toast ( LoginActivity.this, "登录成功" );
                    LoginBeen loginInfo = FastJsonUtils.getSingleBean ( baseResult.body, LoginBeen.class );
                    SharePreManager.saveValue ( "userInfo", "phone", loginInfo.body.user.phone );
                    SharePreManager.saveValue ( "userInfo", "password", loginInfo.body.user.password );
                    SharePreManager.saveValue ( "userInfo", "tokenId", loginInfo.body.token.tokenId );
                    SharePreManager.saveValue ( "userInfo", "userId", loginInfo.body.token.userId );
                    startActivity ( new Intent ( LoginActivity.this, MainActivity.class ) );
                    finish ();
                } else {
                    LYangLogUtil.d ( TAG, "onSucceed--> baseResult=" + baseResult );    //调试输出----
                    UiUtils.toast ( LoginActivity.this,"登录异常" );
                }
            }
            
            @Override
            public void onFailed(int what, Response<BaseResult> response) {
                
            }
        } );
    }
    
    /** 初始化视图 */
    private void initView() {
        titleBarVisibility ( false );
    }
    
    @OnClick({R.id.tv_regist, R.id.et_phone, R.id.et_password, R.id.btn_login, R.id.tv_issue, R.id.tv_forget, R.id
        .tv_qq, R.id.tv_webchat, R.id.tv_sina})
    public void onClick(View view) {
        switch (view.getId ()) {
            case R.id.tv_regist:
                startActivity ( new Intent ( LoginActivity.this, RegisterActivity.class ) );
                break;
            case R.id.et_phone:
                break;
            case R.id.et_password:
                break;
            case R.id.btn_login:
                checkLoginInfo ();
                break;
            case R.id.tv_issue:
                break;
            case R.id.tv_forget:
                break;
            case R.id.tv_qq:
                break;
            case R.id.tv_webchat:
                break;
            case R.id.tv_sina:
                break;
        }
    }
    
    private void checkLoginInfo() {
        String phone    = etPhone.getText ().toString ().trim ();
        String password = etPassword.getText ().toString ().trim ();
        if (DataUtils.isEmptyStrict ( phone )) {
            UiUtils.toast ( "请输入手机号" );
        } else if (!LoginCheckUtil.isMobileNO ( phone )) {
            UiUtils.toast ( "请输入正确的手机号" );
        } else if (DataUtils.isEmptyStrict ( password )) {
            UiUtils.toast ( "请输入您的密码" );
        } else {
            //TODO 校验通过,访问网络开始注册.同时屏蔽所有编辑框和点击按钮,同时开启返回按钮的注册返回提示
            userLogin ( phone, EncryptUtils.encryptMD5ToString(password) );
        }
    }
}
