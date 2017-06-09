package com.peipao8.vehiclelock.LYangCode.CustomerView.dialog;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peipao8.vehiclelock.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Ly 一个简单的dialogfragment。支持自定义内容
 * ##此类绑定了butterknife库
 */

public class SimpleTishi extends DialogFragment {
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //将回调位置信息保存到Bundle中
        outState.putInt ( "requestCode",requestCode );
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Object obj = this.getParentFragment () ;
            if ( this.getParentFragment () == null) {
                obj = this.getActivity ();
            }
            Class  clazz = obj.getClass();
            Method m1    = null;
            try {
                m1 = clazz.getDeclaredMethod("setDiaLogFragment",SimpleTishi.class, int.class);
                int requestCode = savedInstanceState.getInt ( "requestCode" );
                m1.invoke ( obj, SimpleTishi.this, requestCode );
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException ( "SimpleTishi对话框的调用类未生成setDiaLogFragment方法" );
            } catch (InvocationTargetException e) {
                e.printStackTrace ();
            } catch (IllegalAccessException e) {
                e.printStackTrace ();
            }
            Log.d ( "jdsf","djslafds" );
        }
    }
    
    /**标题总布局*/@BindView(R.id.title_ll)
    LinearLayout titleLl;
    /**内容总布局*/@BindView(R.id.content_ll)
    LinearLayout contentLl;
    /**取消按钮*/@BindView(R.id.cancal_tv_def)
    TextView     cancalTvDef;
    /**确定按钮*/@BindView(R.id.confirm_tv_def)
    TextView     confirmTvDef;
    /**标题显示*/@BindView(R.id.title_tv_def)
    TextView     titleTvDef;
    /**内容显示*/@BindView(R.id.content_tv_def)
    TextView     contentTvDef;
    /**点击区域总布局*/@BindView(R.id.click_ll)
    LinearLayout clickLl;
    /**整个界面总布局*/@BindView(R.id.total_layout)
    LinearLayout totalLayout;
    
    private SimpleDialogFragmentClickListenter listener ;
    private int requestCode = 0;
    
    
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
        View inflate = inflater.inflate ( R.layout.customer_dialogfragment_simple_tishi, container, false );
        ButterKnife.bind ( this, inflate );
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
        } else {
            listener.cancle (requestCode);
        }
    }
    
    /**点击取消。new匿名类的可以实现*/
    public void clickConfirm() {
        if (listener == null) {
            getDialog ().dismiss ();
        } else {
            listener.confirm (requestCode);
        }
    }
    
    /**设置取消返回按钮的字体大小*/
    public void setButtonSize_( int cancleSize , int confirmSize){
        cancalTvDef.setTextSize ( cancleSize );
        confirmTvDef.setTextSize ( confirmSize );
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
    
}


