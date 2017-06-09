package com.peipao8.vehiclelock.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peipao8.vehiclelock.CustomerView.AllShapeView.CircleImageView;
import com.peipao8.vehiclelock.R;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : 大胸平天下
 * Author     : 太阳光辉
 * Date       : 2017/3/20 14:18
 */

public class PersonalCenterFragment extends BaseFragment {
    public View view ;
    public CircleImageView mCircleImageView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_personalcenter, container, false);
        mCircleImageView = (CircleImageView) view.findViewById ( R.id.center_mine_head );
        ViewGroup       parent           = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }
}
