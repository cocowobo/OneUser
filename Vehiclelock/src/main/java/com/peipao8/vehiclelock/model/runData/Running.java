package com.peipao8.vehiclelock.model.runData;

import java.io.Serializable;
import java.util.List;

/**
 * 跑步中 跑步信息记录对象
 */
public class Running implements Serializable {

    public Running() {
    }

    public Running(Running running) {
        this.activityId = running.activityId;
        this.calorieCount = running.calorieCount;
        this.durationCount = running.durationCount;
        this.endTime = running.endTime;
        this.highRunningPace = running.highRunningPace;
        this.kilometerCount = running.kilometerCount;
        this.lowRunningPace = running.lowRunningPace;
        this.runnerId = running.runnerId;
        this.runningId = running.runningId;
        this.runningVO = running.runningVO;
        this.runType = running.runType;
        this.startTime = running.startTime;
        this.state = running.state;
    }

    /** 跑步记录ID */
    public Long   runningId       = Long.valueOf ( 0 );
    /** 跑者ID */
    public Long   runnerId        = Long.valueOf ( 0 );  //跑者ID
    /** 活动ID */
    public Long   activityId      = Long.valueOf ( 0 ); //SMALL_VALUES[((int) v) + 128] 一个256为long数组的第128位
    /** 本次总卡路里 */
    public float  calorieCount    = 0.0f;  //本次总卡路里
    /** 本次总公里数 */
    public float  kilometerCount  = 0.0f; //本次总公里数
    /** 本次总时长 */
    public String durationCount   = ""; // 本次总时长
    /** 最高配速 */
    public float  highRunningPace = 0.0f; //最高配速
    /** 最低配速 */
    public float  lowRunningPace  = 0.0f; //最低配速
    /** 用户类型 */
    public int    runType         = 0;
    /** 开始0，结束1 */
    public int    state           = 0; //     开始0，结束1
    /** 开始时间 */
    public String startTime       = "";
    /** 结束时间 */
    public String endTime         = "";
    /** 跑步数据详细信息 */
    public List<RunningVO> runningVO;
}
