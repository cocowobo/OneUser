package com.peipao8.vehiclelock.LYangCode.BeenResolver.TwoBeen;

import java.io.Serializable;

/**
 * 附近的人的位置Been
 */
public class NearbyPeopleLocation implements Serializable {

    public String lag;//经度
    public String lat;//纬度
    public String province;//省
    public String city;//市
    public String area;//区
    public String Road;//路

    @Override
    public String toString() {

        return new StringBuffer()
                .append("{")
                .append("\"lag\":\"").append(lag).append("\",")
                .append("\"lat\":\"").append(lat).append("\",")
                .append("\"province\":\"").append(province).append("\",")
                .append("\"city\":\"").append(city).append("\",")
                .append("\"area\":\"").append(area).append("\",")
                .append("\"Road\":\"").append(Road).append("\"}")
                .toString();

    }
}
