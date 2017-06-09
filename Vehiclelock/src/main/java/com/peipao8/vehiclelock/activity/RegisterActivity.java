package com.peipao8.vehiclelock.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.peipao8.vehiclelock.BaseActivity;
import com.peipao8.vehiclelock.LYangCode.nohttp.ConstantValue;
import com.peipao8.vehiclelock.LYangCode.nohttp.HttpListener;
import com.peipao8.vehiclelock.LYangCode.nohttp.UrlConstants;
import com.peipao8.vehiclelock.LYangCode.utils.UiUtils;
import com.peipao8.vehiclelock.LYangCode.utils.check.LoginCheckUtil;
import com.peipao8.vehiclelock.LYangCode.utils.data.DataUtils;
import com.peipao8.vehiclelock.LYangCode.utils.log.LYangLogUtil;
import com.peipao8.vehiclelock.NetRequest.UserInforRequest;
import com.peipao8.vehiclelock.R;
import com.peipao8.vehiclelock.model.BaseResult;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.peipao8.vehiclelock.R.id.et_phone;


public class RegisterActivity extends BaseActivity implements HttpListener<BaseResult> {
    private static final String TAG          = "RegisterActivity";
    @BindView(R.id.toolbar_iv_left)
    ImageView toolbarIvLeft;
    @BindView(R.id.toolbar_tv_title)
    TextView  toolbarTvTitle;
    @BindView(R.id.toolbar_iv_right)
    ImageView toolbarIvRight;
    @BindView(R.id.toolbar)
    Toolbar   toolbar;
    @BindView(R.id.tv_upload_image)
    TextView  tvUploadImage;
    @BindView(et_phone)
    EditText  etPhone;
    @BindView(R.id.et_confirm_number)
    EditText  etConfirmNumber;
    @BindView(R.id.view_confirm)
    View      viewConfirm;
    @BindView(R.id.btn_get_confirm_code)
    Button    btnGetConfirmCode;
    @BindView(R.id.et_password)
    EditText  etPassword;
    @BindView(R.id.et_password_confirm)
    EditText  etPasswordConfirm;
    @BindView(R.id.tv_read)
    TextView  tvRead;
    @BindView(R.id.btn_regist)
    Button    btnRegist;
    
    private String mCheckCode     = "";
    private long   mCheckCodeTime = 0;
    private String mPassword;
    private String mPasswordConfirm;
    private String mPhone;
    private String mConfirmNumber;
    
    //获取验证码倒计时
    private CountDownTimer timer = new CountDownTimer ( 60000, 1000 ) {
        
        @Override
        public void onTick(long millisUntilFinished) {
            btnGetConfirmCode.setClickable ( false );
            btnGetConfirmCode.setText ( millisUntilFinished / 1000 + "秒" );
        }
        
        @Override
        public void onFinish() {
            btnGetConfirmCode.setClickable ( true );
            btnGetConfirmCode.setText ( "发送验证码" );
        }
    };
    
    
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setToolBarView ( R.layout.activity_toolbar_register );
        setContentView ( R.layout.activity_register );
        ButterKnife.bind ( this );
        titleBarVisibility ( true );
        initListener();
    }
    
    private void initListener() {
        btnRegist.getViewTreeObserver ().addOnPreDrawListener ( new ViewTreeObserver.OnPreDrawListener () {
            @Override
            public boolean onPreDraw() {
                int height = getAppBarLayout ().getHeight ();
                setImmerseLayout ( getContentLayout (),height );
                btnRegist.getViewTreeObserver ().removeOnPreDrawListener ( this );
                return false;
            }
        } );
    }
    
    
    @OnClick({R.id.toolbar_iv_left, R.id.tv_upload_image, R.id.et_confirm_number, R.id.btn_get_confirm_code, R.id
        .et_password, R.id.et_password_confirm, R.id.btn_regist})
    public void onClick(View view) {
        switch (view.getId ()) {
            case R.id.toolbar_iv_left:
                finish ();
                break;
            case R.id.tv_upload_image:
                uploadImage ();
                break;
            case R.id.et_confirm_number:
                break;
            case R.id.btn_get_confirm_code:
                //获取验证码
                mPhone = etPhone.getText ().toString ();
                if (LoginCheckUtil.isMobileNO ( mPhone )) {
                    checkPhoneInServlet ( mPhone );
                } else {
                    UiUtils.toast ( "请输入正确的手机号" );
                }
                break;
            case R.id.et_password:
                
                break;
            case R.id.et_password_confirm:
                
                break;
            case R.id.btn_regist:
                //注册
                checkRegist ();
                break;
        }
    }
    
    private void checkRegist() {
        if (DataUtils.isEmptyStrict ( mCheckCode )) {
            if (LoginCheckUtil.isMobileNO ( etPhone.getText ().toString ().trim () )) {
                UiUtils.toast ( "请先获取验证码" );
            } else {
                UiUtils.toast ( "请输入正确的手机号" );
            }
        } else if (LoginCheckUtil.isCheckCodeVoerTime ( mCheckCodeTime, 10 )) {
            UiUtils.toast ( "验证码已失效,请重新获取" );
        } else if (!LoginCheckUtil.isMobileNO ( mPhone )) {
            UiUtils.toast ( "请重新获取验证码" );
        } else if (DataUtils.isEmptyStrict ( etConfirmNumber.getText ().toString ().trim () )) {
            UiUtils.toast ( "请输入您收到的验证码" );
        } else if (!mCheckCode.equals ( etConfirmNumber.getText ().toString ().trim ()  )) {
            UiUtils.toast ( "请输入正确的验证码" );
        } else if (DataUtils.isEmptyStrict ( etPassword.getText ().toString ().trim () )) {
            UiUtils.toast ( "请编辑您的密码" );
        } else if (DataUtils.isEmptyStrict ( etPasswordConfirm.getText ().toString ().trim () )) {
            UiUtils.toast ( "请先再次输入您的密码" );
        } else if (!etPasswordConfirm.getText ().toString ().trim ().equals ( etPassword.getText ().toString ().trim
            () )) {
            UiUtils.toast ( "两次输入的密码不一致,请核对密码" );
        } else {
            //TODO 校验通过,访问网络开始注册.同时屏蔽所有编辑框和点击按钮,同时开启返回按钮的注册返回提示
            registerUser ( mPhone,etPassword.getText ().toString ().trim (),mCheckCode );
        }
    }
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy ();
        timer.cancel ();
    }
    
    /**
     * 电话号码查重
     * @param phone
     */
    public void checkPhoneInServlet(String phone) {
        Map<String, String> params = new HashMap<> ();
        params.put ( "phone", phone );
        UserInforRequest.registCheckPhone ( ConstantValue.what1000, UrlConstants.checkUrlPhone, cancelObject, params,
            new HttpListener<BaseResult> () {
            @Override
            public void onSucceed(int what, Response<BaseResult> response) {
                BaseResult baseResult = response.get ();
                if (baseResult.code.equals ( "10201")) {
                    UiUtils.toast ( RegisterActivity.this,"该手机号已注册" );
                } else if (baseResult.code.equals ( "10213" )) {
                    UiUtils.toast ( RegisterActivity.this, "错误的电话格式" );
                } else {
                    getCheckCode ( phone );
                }
            }
            
            @Override
            public void onFailed(int what, Response<BaseResult> response) {
                
            }
        } );
    }
    
    /**
     * 获取验证码
     * @param phone
     */
    public void getCheckCode(String phone) {
        Map<String, String> params = new HashMap<> ();
        params.put ( "phone", phone );
        UserInforRequest.registGetCode ( ConstantValue.what1000, UrlConstants.codeGetUrl + phone, cancelObject, params,
            new HttpListener<BaseResult> () {
            @Override
            public void onSucceed(int what, Response<BaseResult> response) {
                BaseResult baseResult = response.get ();
                if (baseResult.code.equals ( "10201")) {
                    UiUtils.toast ( RegisterActivity.this,"该手机号已注册" );
                } else if (baseResult.code.equals ( "10211" )) {
                    UiUtils.toast ( RegisterActivity.this, "缺少参数" );
                }else if (baseResult.code.equals ( "10213" )) {
                    UiUtils.toast ( RegisterActivity.this, "错误的电话格式" );
                } else {
                    mCheckCode = baseResult.body;
                    mCheckCodeTime = System.currentTimeMillis ();
                    timer.start();
                }
            }
            
            @Override
            public void onFailed(int what, Response<BaseResult> response) {
                
            }
        } );
    }
    
    /**
     * 电话号码查重
     * @param phone
     */
    public void registerUser(String phone,String password,String verCode) {
        Map<String, String> params = new HashMap<> ();
        params.put ( "phone", phone );
        params.put ( "password", password );
        params.put ( "verCode", verCode );
        UserInforRequest.registGetCode ( ConstantValue.what1000, UrlConstants.registUrl, cancelObject, params,
            new HttpListener<BaseResult> () {
            @Override
            public void onSucceed(int what, Response<BaseResult> response) {
                BaseResult baseResult = response.get ();
                if (baseResult.code.equals ( "10201")) {
                    UiUtils.toast ( RegisterActivity.this,"该手机号已注册" );
                } else if (baseResult.code.equals ( "10211" )) {
                    UiUtils.toast ( RegisterActivity.this, "缺少参数" );
                }else if (baseResult.code.equals ( "10213" )) {
                    UiUtils.toast ( RegisterActivity.this, "错误的电话格式" );
                } else {
                    LYangLogUtil.d ( TAG, "onSucceed--> baseResult=" + baseResult.body );
                }
            }
            
            @Override
            public void onFailed(int what, Response<BaseResult> response) {
                
            }
        } );
    }
    
    
    private void uploadImage() {
    }
    
    
    @Override
    public void onSucceed(int what, Response<BaseResult> response) {
        
    }
    
    @Override
    public void onFailed(int what, Response<BaseResult> response) {
        
    }
}
