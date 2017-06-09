package com.peipao8.vehiclelock.LYangCode.BeenResolver.TwoBeen;

import java.io.Serializable;

/**
 * 附近活动的位置Been
 */
public class NearbyEventLocation implements Serializable {
    public Long locationId;
    public String lag;
    public String lat;
    public String province;
    public String city;
    public String area;
    public String Road;
    public String addressHash;//md5 活动地点
    public Long publisherId;  //messageid事件ID的用法
    public String type;

}
