package com.peipao8.vehiclelock.LYangCode.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by ooo on 2016/1/15.
 */
public class AnimationUtil {

    /**
     * 水平位移动画效果
     *
     * @param view
     * @param bl          是否反方向执行
     * @param duration    动画持续时间
     * @param repeatCount 重复次数
     * @param startOffset 延迟时间
     */
    public void standardSlideView(final View view, boolean bl, int duration, int repeatCount, int startOffset) {
        int width = view.getWidth();
        TranslateAnimation animation;
        if (bl) {
            animation = new TranslateAnimation(0, -width, 0, 0);
        } else {
            animation = new TranslateAnimation(0, width, 0, 0);
        }
//        float fromXDelta 动画开始的点离当前View X坐标上的差值
//        float toXDelta 动画结束的点离当前View X坐标上的差值
//        float fromYDelta 动画开始的点离当前View Y坐标上的差值
//        float toYDelta 动画开始的点离当前View Y坐标上的差值
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(duration);//设置动画持续时间
        animation.setRepeatCount(repeatCount);//设置重复次数
        animation.setStartOffset(startOffset);//设置延迟时间
//        if (bl) {
//            animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
//        }
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(View.GONE);
            }
        });
        view.startAnimation(animation);
    }

    /**
     * 垂直位移动画效果
     *
     * @param view
     * @param bl          是否反方向执行
     * @param duration    动画持续时间
     * @param repeatCount 重复次数
     * @param startOffset 延迟时间
     */
    public void verticalSlideView(final View view, final boolean bl, int duration, int repeatCount, int startOffset) {
        int height = view.getHeight();
        TranslateAnimation animation;
        if (bl) {
            animation = new TranslateAnimation(0, 0, 0, -height);
        } else {
            animation = new TranslateAnimation(0, 0, 0, height);
        }
//        float fromXDelta 动画开始的点离当前View X坐标上的差值
//        float toXDelta 动画结束的点离当前View X坐标上的差值
//        float fromYDelta 动画开始的点离当前View Y坐标上的差值
//        float toYDelta 动画开始的点离当前View Y坐标上的差值
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(duration);//设置动画持续时间
        animation.setRepeatCount(repeatCount);//设置重复次数
        animation.setStartOffset(startOffset);//设置延迟时间
//        if (bl) {
//            animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
//        }
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(View.GONE);
            }
        });
        view.startAnimation(animation);
    }

    /**
     * 缩放动画效果
     *
     * @param view
     * @param bl          动画执行完后是否停留在执行完的状态
     * @param duration    动画持续时间
     * @param repeatCount 重复次数
     * @param startOffset 执行前的等待时间
     * @param fromX       动画起始时 X坐标上的伸缩尺寸
     * @param toX         动画结束时 X坐标上的伸缩尺寸
     * @param fromY       动画起始时Y坐标上的伸缩尺寸
     * @param toY         动画结束时Y坐标上的伸缩尺寸
     * @param pivotXValue 动画相对于物件的X坐标的开始位置
     * @param pivotYValue 动画相对于物件的Y坐标的开始位置
     */
    public void scaleAnimationView(final View view, final boolean bl, int duration, int repeatCount, int startOffset,
                                   float fromX, float toX, float fromY, float toY, float pivotXValue, float pivotYValue) {
        /** 设置缩放动画 */
        final ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(duration);//设置动画持续时间
        animation.setRepeatCount(repeatCount);//设置重复次数
        animation.setFillAfter(bl);//动画执行完后是否停留在执行完的状态
        animation.setStartOffset(startOffset);//执行前的等待时间
        view.setAnimation(animation);
        //开始动画
        animation.startNow();
    }

    /**
     * 旋转动画效果
     *
     * @param view
     * @param duration    动画持续时间
     * @param fromDegrees 旋转的开始角度
     * @param toDegrees   旋转的结束角度
     * @param pivotXType  X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT
     * @param pivotXValue X坐标的伸缩值
     * @param pivotYType  Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT
     * @param pivotYValue Y坐标的伸缩值
     * @param repeatCount 重复次数
     * @param bl          动画执行完后是否停留在执行完的状态
     * @param startOffset 执行前的等待时间
     */
    public void rotateAnimationView(View view, int duration, float fromDegrees, float toDegrees, int pivotXType, float pivotXValue,
                                    int pivotYType, float pivotYValue, int repeatCount, boolean bl, int startOffset) {
        /** 设置旋转动画 */
        final RotateAnimation animation = new RotateAnimation(fromDegrees, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);//设置动画持续时间

        animation.setRepeatCount(repeatCount);//设置重复次数
        animation.setFillAfter(bl);//动画执行完后是否停留在执行完的状态
        animation.setStartOffset(startOffset);//执行前的等待时间
        view.setAnimation(animation);
//开始动画
        animation.startNow();
    }
}
