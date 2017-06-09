package com.peipao8.vehiclelock.SqlDb.helper;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.peipao8.vehiclelock.LYangCode.utils.log.LYangLogUtil;

/**
 * Description: 传感器管理类，实现传感器事件监听
 * Copyright  : Copyright (c) 2015
 * Company    : 给予
 * Author     : 叶之书
 * Date       : 2016/7/18 9:34
 */
public class SensorManagerHelper implements SensorEventListener {

    private static final int SPEED_SHRESHOLD = 5000;         // 速度阈值，当摇晃速度达到这值后产生作用
    private static final int UPTATE_INTERVAL_TIME = 1000;        // 两次检测的时间间隔
    // 手机上一个位置时重力感应坐标
    private float lastX;
    private float lastY;
    private float lastZ;
    private  long  startValue = 0 ;           //开始计数
    private  int  totalChanageX = 0 ;//x的 3次变化总值
    private  int  totalChanageY = 0 ;//y的 3次变化总值
    private  int  totalChanageZ = 0 ;//z的 3次变化总值
    private  int  totalChanage = 0 ;//3次总值的变化值
    /**x 平均值*/
    private  int  averageX = 0 ;//x 平均值
    /**y 平均值*/
    private  int  averageY = 0 ;//y 平均值
    /**z 平均值*/
    private  int  averageZ = 0 ;//z 平均值
    /**每次xyz 相加*/
    public float totalValue;    // 每次xyz 相加
    /**4次总值3次相减的平均值*/
    public float averageTotal;
    public float[] xChanage = new float[3] ;
    public float[] yChanage = new float[3] ;
    public float[] zChanage = new float[3] ;
    public float[] totalVlue = new float[4] ;    //可以存4个值

    private long lastUpdateTime;                // 上次检测时间
    private SensorManager sensorManager;        // 传感器管理器
    private Sensor sensor;  // 传感器
    private OnShakeListener onShakeListener;          // 重力感应监听器  摇动监听
    private Context context;                         // 上下文对象context

    public SensorManagerHelper(Context context) {       // 构造器
        this.context = context;             // 获得监听对象
        start();
    }

    /**开始 ，将传感器硬件与本类处理绑定注册*/
    public void start() {
        sensorManager = (SensorManager) context         // 获得传感器管理器
                        .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);   // 获得重力加速度传感器
        }
        if (sensor != null) {       // 注册
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    // 停止检测
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    /** 摇晃监听接口*/
    public interface OnShakeListener {
        public void onShake();
    }

    // 设置重力感应监听器让子类实现
    public void setOnShakeListener(OnShakeListener listener) {
        onShakeListener = listener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {//  accuracy精度
    }

    /**
     * 重力感应器感应获得变化数据
     * android.hardware.SensorEventListener#onSensorChanged(android.hardware
     * .SensorEvent)
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        long currentUpdateTime = System.currentTimeMillis();    // 现在检测时间
        long timeInterval = currentUpdateTime - lastUpdateTime;         // 两次检测的时间间隔

        if (timeInterval < UPTATE_INTERVAL_TIME){   //检测间隔是否1秒         不是就返回
            return;
        }
        lastUpdateTime = currentUpdateTime;             //现在的时间变成上次时间
        // 获得x,y,z坐标
        float  currentX = event.values[0];
        float  currentY = event.values[1];
        float  currentZ = event.values[2];

        xChanage[(int)startValue%3] = Math.abs ( currentX - lastX );     // x 值的变化值 保存到本地
        yChanage[(int)startValue%3] = Math.abs ( currentY - lastY );     // y 值的变化值 保存到本地
        zChanage[(int)startValue%3] = Math.abs ( currentZ - lastZ );     // z 值的变化值 保存到本地
        totalVlue[(int)startValue%4] = totalValue = Math.abs(currentX)+Math.abs(currentY) +Math.abs(currentZ); // 总值保存到本地
        startValue ++ ;                                // 总共计时
        lastX = currentX ;
        lastY = currentY ;
        lastZ = currentZ ;
        for (int i = 0; i < xChanage.length; i++) {        //遍历保存的每一秒的变化值.并叠加成总值
            totalChanageX += xChanage[i];
        }
        averageX = totalChanageX/xChanage.length ;         // 计算x值3秒的平均波动值
        totalChanageX = 0 ;                                //计算完成后重置此值.

        for (int i = 0; i < yChanage.length; i++) {        //遍历保存的每一秒的变化值.并叠加成总值
            totalChanageY += yChanage[i];
        }
        averageY = totalChanageY/yChanage.length ;         // 计算y值3秒的平均波动值
        totalChanageY = 0 ;                                //计算完成后重置此值.

        for (int i = 0; i < zChanage.length; i++) {        //遍历保存的每一秒的变化值.并叠加成总值
            totalChanageZ += zChanage[i];
        }
        averageZ = totalChanageZ/zChanage.length ;         // 计算z值3秒的平均波动值
        totalChanageZ = 0 ;                                //计算完成后重置此值.

        if (totalVlue[3] != 0) {
            averageTotal = (Math.abs ( totalVlue[3] - totalVlue[2] )
                +Math.abs ( totalVlue[2] - totalVlue[1] )
                +Math.abs ( totalVlue[1] - totalVlue[0] ))/3;
        }
        //3条轴，都有个特性，趋向水平者绝对值数值越大
        //翻转手机只会让其中的一条轴趋向水平，一条不趋向水平，一条基本不动。
        //3条轴还有个特性，就是当 某个轴上的位移速度越大， 其值越大，
        //3条轴，每个取值值10~-10 之间。
        //当手机水平放置着不动的时候，其值在9到11之间，基本上是一个轴的值，无位移增加值
        //当手机放在斜面 的时候，有两条轴发生改变，其绝对值相加基本上在10到15之间，
        //当手机来回翻转，因为手的轻微摇动，基本上在15到20.    有手机轻微位移增加，
        //当手机摇一摇的时候，                                位移增加值不可小视
        // 直接把3个的绝对值加起来最高值一般就超过了20，当然这样算也只能是大概算法。
        // P：注意当手机被用户拿在手里 步行的时候，基本上和来回翻转差不多
        // 获得x,y,z坐标

        LYangLogUtil.d (TAG, "onSensorChanged--> 传感器数据="
            + "***变化--X="+currentX+"----y="+currentY+"----z="
            +currentZ+"---总值="+ totalValue+"总值的3次变化平均值"+averageTotal) ;

        //  totalValue < 12 && totalValue > 9 //     水平放置着不动
        //  totalValue < 16 && totalValue > 12 //    晃动。倾斜。
        //  totalValue < 20 && totalValue > 16 //    轻微晃动
        //  totalValue > 20                      // 运动中
    }

    private static final String TAG = "=====SensorManagerHelper" ;
}
