package com.peipao8.vehiclelock.LYangCode.utils;

import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ooo on 2016/3/15.
 * 控件工具类
 */
public class ControlUtil {
    /**
     * 获取控件宽高
     *
     * @param view
     * @return
     */
    private static List<Integer> _getControlWideAndHigh(final View view) {
        final List<Integer> integers = new ArrayList<>();
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                integers.add(view.getWidth());
                integers.add(view.getHeight());
            }
        });
        return integers;
    }


    //******************对外公布的方法******************

    /**
     * 获取控件宽高
     *
     * @param view
     * @return
     */
    public static List<Integer> getControlWideAndHigh(final View view) {
        List<Integer> integers = _getControlWideAndHigh(view);
        return integers;
    }
}
