package com.peipao8.vehiclelock.model.location;

import java.io.Serializable;

/**
 * Created by PC on 2016/3/14.
 */
public class LocationVO implements Serializable {

    public String lag;//经度
    public String lat;//纬度
    public String province;//省
    public String city;//市
    public String area;//区
    public String road;//路

    public String getLag() {
        return lag;
    }

    public String getLat() {
        return lat;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getroad() {
        return road;
    }

    public void setLag(String lag) {
        this.lag = lag;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setroad(String road) {
        road = road;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("{")
                .append("\"lag\":\"").append(lag).append("\",")
                .append("\"lat\":\"").append(lat).append("\",")
                .append("\"province\":\"").append(province).append("\",")
                .append("\"city\":\"").append(city).append("\",")
                .append("\"area\":\"").append(area).append("\",")
                .append("\"road\":\"").append(road).append("\"}")
                .toString();

    }
}
