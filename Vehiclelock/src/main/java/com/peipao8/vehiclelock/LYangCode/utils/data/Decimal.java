package com.peipao8.vehiclelock.LYangCode.utils.data;

import java.math.BigDecimal;

/**
 * Created by ooo on 2016/2/15.
 * 取指定位数的小数点
 */
public class Decimal {
    /**
     * 保留指定位数的小数
     *
     * @param decimal 保留几位小数
     * @param v
     * @return
     */
    public float bigDecimalFromFloat(int decimal, float v) {
        float value;
        BigDecimal bigDecimal = new BigDecimal(v);
        value = bigDecimal.setScale(decimal, BigDecimal.ROUND_HALF_UP).floatValue();
        return value;
    }
}
