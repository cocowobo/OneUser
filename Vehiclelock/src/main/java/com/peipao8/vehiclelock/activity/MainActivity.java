package com.peipao8.vehiclelock.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peipao8.vehiclelock.BaseActivity;
import com.peipao8.vehiclelock.R;
import com.peipao8.vehiclelock.fragment.MyVehiclelockFragment;
import com.peipao8.vehiclelock.fragment.PersonalCenterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    
    @BindView(R.id.fragment_container)
    FrameLayout  fragmentContainer;
    @BindView(R.id.tv_my_vehiclelock)
    TextView     tvMyVehiclelock;
    @BindView(R.id.tv_personal_conter)
    TextView     tvPersonalConter;
    @BindView(R.id.ll_home_view_pranet)
    LinearLayout llHomeViewPranet;
    @BindView(R.id.toolbar_iv_left)
    ImageView    toolbarIvLeft;
    @BindView(R.id.toolbar_tv_title)
    TextView     toolbarTvTitle;
    @BindView(R.id.toolbar_iv_right)
    ImageView    toolbarIvRight;
    //---------------------Fragment相关-----------------------------------------
    /** FragmentManager管理器 */
    private FragmentManager        fm;//FragmentManager管理器
    /** 此事务引用使用时候必须重新生成赋值给本事务 */
    private FragmentTransaction    transaction;
    private PersonalCenterFragment personalCenterFragment;
    private MyVehiclelockFragment  myVehiclelockFragment;
    
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setToolBarView ( R.layout.activity_toolbar_main );
        setContentView ( R.layout.activity_main );
        ButterKnife.bind ( this );
        initView ();
        titleBarVisibility ( true );
        initFragment ();
        initListener();
    }
    
    private void initListener() {
        fragmentContainer.getViewTreeObserver ().addOnPreDrawListener ( new ViewTreeObserver.OnPreDrawListener () {
            @Override
            public boolean onPreDraw() {
                int height = getAppBarLayout ().getHeight ();
                setMarginLayout ( myVehiclelockFragment.getView (),height );
                setMarginLayout2 ( personalCenterFragment.mCircleImageView,height );
                fragmentContainer.getViewTreeObserver ().removeOnPreDrawListener ( this );
                return false;
            }
        } );
    }
    
    private void initFragment() {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        myVehiclelockFragment = new MyVehiclelockFragment ();
        personalCenterFragment = new PersonalCenterFragment ();
        // 添加显示第一个fragment
        transaction.add(R.id.fragment_container, myVehiclelockFragment) ;
        transaction.add(R.id.fragment_container, personalCenterFragment) ;
        transaction.hide ( myVehiclelockFragment) ;
        transaction.show ( personalCenterFragment ) ;
        transaction.commit();
    }
    
    private void initView() {
        
    }
    
    
    @OnClick({R.id.tv_my_vehiclelock, R.id.tv_personal_conter,R.id.toolbar_iv_left, R.id.toolbar_iv_right})
    public void onClick(View view) {
        switch (view.getId ()) {
            case R.id.tv_my_vehiclelock:
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(personalCenterFragment );
                trx.show(myVehiclelockFragment).commit();
                tvMyVehiclelock.setSelected(true);
                tvPersonalConter.setSelected(false);
                break;
            case R.id.tv_personal_conter:
                FragmentTransaction trx2 = getSupportFragmentManager().beginTransaction();
                trx2.hide(myVehiclelockFragment );
                trx2.show(personalCenterFragment).commit();
                tvMyVehiclelock.setSelected(false);
                tvPersonalConter.setSelected(true);
                break;
            case R.id.toolbar_iv_left:
                break;
            case R.id.toolbar_iv_right:
                break;
        }
    }
}
