package com.peipao8.vehiclelock.Behavior;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : 大胸平天下
 * Author     : 太阳光辉
 * Date       : 2017/3/22 13:57
 */

public class PersonalCenterBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    
    public PersonalCenterBehavior() {
        super ();
    }
    
    /**
     * 判断child的布局是否依赖dependency
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        //根据逻辑判断rs的取值
        //返回false表示child不依赖dependency，ture表示依赖
        return dependency instanceof NestedScrollView;
    }
    
    /**
     * 当dependency发生改变时（位置、宽高等），执行这个函数
     * 返回true表示child的位置或者是宽高要发生改变，否则就返回false
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Toolbar child, View dependency) {
        //child要执行的具体动作
        return true;
    }
    
    private void setPosition(View v, int x, int y) {
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        v.setLayoutParams(layoutParams);
    }
    
}
