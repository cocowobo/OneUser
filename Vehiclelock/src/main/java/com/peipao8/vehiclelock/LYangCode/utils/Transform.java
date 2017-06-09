package com.peipao8.vehiclelock.LYangCode.utils;

/**
 * Created by ooo on 2016/2/16.
 * 转换器
 */
public class Transform {
    /**
     * 将小数转换为分数
     *
     * @param value
     * @return
     */
    public String decimalNumberToFraction(String value) {
        String s = value.split("\\.")[0] + "'" + Integer.parseInt(value.split("\\.")[1]) * 6 + "''";
        return s;
    }

    /**
     * 将分数转换为小数
     * @param value
     * @return
     */
    public String decimalFractionToNumber(String value) {
        return (value.split("'")[0] + "." + Integer.parseInt(value.split("''")[1]) / 6);
    }

    /**
     * RGB转换为16进制
     *
     * @param red
     * @param green
     * @param blue
     * @return
     */
    private String colorTransform(int red, int green, int blue) {
        return Color2Hex(red) + Color2Hex(green) + Color2Hex(blue);
    }

    /**
     * 将hsl转换为
     *
     * @param h 色相
     * @param s 饱和度
     * @param l 亮度
     * @return
     */
    public String hslTo2Hex(float h, float s, float l) {
        float r, g, b;
        if (s == 0f) {
            r = g = b = l; // achromatic
        } else {
            float q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            float p = 2 * l - q;
            r = hueToRgb(p, q, h + 1f / 3f);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1f / 3f);
        }
        String color = colorTransform((int) (r * 255), (int) (g * 255), (int) (b * 255));
        return color;
    }

    private float hueToRgb(float p, float q, float t) {
        if (t < 0f)
            t += 1f;
        if (t > 1f)
            t -= 1f;
        if (t < 1f / 6f)
            return p + (q - p) * 6f * t;
        if (t < 1f / 2f)
            return q;
        if (t < 2f / 3f)
            return p + (q - p) * (2f / 3f - t) * 6f;
        return p;
    }


    private String Color2Hex(int n) {
        return ToHex(n >> 4) + ToHex(n);
    }

    private String ToHex(int n) {
        n = n % 16;
        if (n <= 0)
            return "0";
        if (n < 10)
            return n + "";
        if (n == 10)
            return "A";
        if (n == 11)
            return "B";
        if (n == 12)
            return "C";
        if (n == 13)
            return "D";
        if (n == 14)
            return "E";
        if (n == 15)
            return "F";
        return "";
    }
}
