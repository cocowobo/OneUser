package com.peipao8.vehiclelock.model.nearby;

import com.peipao8.vehiclelock.model.location.LocationVO;

import java.io.Serializable;

/** 附近的人  */
public class PersonInfoVO implements Serializable {

    /** 头像地址 */
    public String     headImg;
    /** 昵称 */
    public String     nickName;
    /** 身高 */
    public String     height;
    /** 体重 */
    public String     weight;
    /** 生日 */
    public String     birth;
    /** 标签 */
    public String     tag;
    /** 城市 */
    public String     city;
    /** 年龄 */
    public String     age;
    /** 性别 */
    public String     sex;
    /** 用户账号 */
    public String     userNumber;
    /** 心情 */
    public String     feeling;
    /** 经纬度信息 */
    public LocationVO locationVO;
    /** 角色 */
    public String     userRole;
    /** 用户id */
    public String     userId;
    /** 显示数据拼音的首字母 */
    public String     sortLetters;
    /** 用户vo */
    public String     money;
}
