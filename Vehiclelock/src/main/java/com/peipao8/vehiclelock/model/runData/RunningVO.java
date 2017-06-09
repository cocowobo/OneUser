package com.peipao8.vehiclelock.model.runData;

import java.io.Serializable;

/**
 * 详细跑步数据记录对象
 */
public class RunningVO implements Serializable {
    public long runningNodeId;
    /**速度*/
    public float speed = 0.0f;  //速度
    /**卡路里*/
    public float calorie = 0.0f;  //卡路里
    /**公里数*/
    public float kilometer = 0.0f; //公里数
    /**配速*/
    public float runningPace = 0.0f; //配速
    /**当前时间*/
    public String nowTime = "";  //当前时间
    /**当前坐标经度*/
    public String lat = ""; //当前坐标
    /**当前坐标维度*/
    public String lag = "";
    /**时长*/
    public String duration = ""; // 时长
    /**颜色*/
    public String color = "";
    /**是否为最后一公里结束点*/
    public boolean kilometerNode = false;
    /**数据库存储ID*/
    public Long runningId = Long.valueOf(0);  //数据库存储ID
}
