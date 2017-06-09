package com.peipao8.vehiclelock.model.runData;

import java.io.Serializable;

/**
 * 跑步信息记录对象。每个 人的总共跑步时间，距离，消耗
 */
public class RunCount implements Serializable {
        
    /** 跑步次数 */
    public String runNum;
        
    /** 总消耗 */
    public String CalorieCount;
        
    /** 总里程 */
    public String KilometerCount;
        
    /** 总耗时 */
    public String DurationCount;
        
    /** 最长距离跑步ID */
    public String MaxkilometerRunningId;
        
    /** 最长距离公里数 */
    public double MaxkilometerCount;
        
    /** 最长距离时间 */
    public String MaxkilometerStartTime;
        
    /** 最快配速跑步ID */
    public String MaxhighRunningPaceRunningId;
        
    /** 最快配速 */
    public double MaxhighRunningPace;
        
    /** 最快配速时间 */
    public String MaxhighRunningPaceStartTime;
}
