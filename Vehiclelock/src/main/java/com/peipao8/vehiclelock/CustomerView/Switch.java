package com.peipao8.vehiclelock.CustomerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.peipao8.vehiclelock.R;

/**
 * Created by ooo on 2016/1/18.
 */
public class Switch extends View implements View.OnTouchListener {
    private Bitmap bg_pause;//暂停
    private Bitmap bg_continue;//继续
    private Bitmap slipper_btn;//滑动图标
    /**
     * 按下时的x和当前的x
     */
    private float downX, nowX;

    /**
     * 记录用户是否在滑动
     */
    private boolean onSlip = false;

    /**
     * 当前的状态
     */
    private int nowStatus = 0;//0:正在跑步 1：暂停 2：完成

    /**
     * 监听接口
     */
    private OnChangedListener listener;


    public Switch(Context context) {
        super(context);
        init();
    }

    public Switch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        //载入图片资源
        bg_pause = BitmapFactory.decodeResource(getResources(), R.drawable.zanting);
        bg_continue = BitmapFactory.decodeResource(getResources(), R.drawable.zanting1);
        slipper_btn = BitmapFactory.decodeResource(getResources(), R.drawable.huadong);

        setOnTouchListener(this);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x = 0;

        //根据nowX设置背景，开或者关状态
        if (nowStatus == 0 || nowStatus == 2) {
            canvas.drawBitmap(bg_pause, matrix, paint);//画出关闭时的背景
        } else {
            canvas.drawBitmap(bg_continue, matrix, paint);//画出打开时的背景
        }

        if (onSlip) {//是否是在滑动状态,
            if (nowX >= bg_pause.getWidth())//是否划出指定范围,不能让滑块跑到外头,必须做这个判断
                x = bg_pause.getWidth() - slipper_btn.getWidth() / 2;//减去滑块1/2的长度
            else
                x = nowX - slipper_btn.getWidth() / 2;
        } else {//根据当前的状态设置滑块的x值
            x = bg_pause.getWidth() / 4;
        }

        //对滑块滑动进行异常处理，不能让滑块出界
        if (x < 0) {
            x = 0;
        } else if (x > bg_pause.getWidth() - slipper_btn.getWidth()) {
            x = bg_pause.getWidth() - slipper_btn.getWidth();
        }

        //画出滑块
        canvas.drawBitmap(slipper_btn, x, 1, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (event.getX() > bg_pause.getWidth() || event.getY() > bg_pause.getHeight()) {
                    return false;
                } else {
                    onSlip = true;
                    downX = event.getX();
                    nowX = downX;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                nowX = event.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                onSlip = false;
                if (event.getX() <= bg_pause.getWidth() / 4) {
                    if (nowStatus == 0) {
                        nowStatus = 1;//暂停
                    } else {
                        nowStatus = 0;//继续
                    }
                    nowX = bg_pause.getWidth() - slipper_btn.getWidth();

                    if (listener != null) {
                        listener.OnChanged(Switch.this, nowStatus);
                    }
                } else if (event.getX() >= bg_pause.getWidth() * 3 / 4) {
                    nowStatus = 2;//完成
                    nowX = 0;

                    if (listener != null) {
                        listener.OnChanged(Switch.this, nowStatus);
                    }
                }


                break;
            }
        }
        //刷新界面
        invalidate();
        return true;
    }

    /**
     * 为WiperSwitch设置一个监听，供外部调用的方法
     *
     * @param listener
     */
    public void setOnChangedListener(OnChangedListener listener) {
        this.listener = listener;
    }


    /**
     * 设置滑动开关的初始状态，供外部调用
     *
     * @param checked
     */
    public void setChecked(int checked) {
        if (checked == 0) {
            nowX = bg_pause.getWidth();
        } else {
            nowX = 0;
        }
        nowStatus = 0;
    }

    /**
     * 回调接口
     *
     * @author len
     */
    public interface OnChangedListener {
        public void OnChanged(Switch wiperSwitch, int checkState);
    }
}
