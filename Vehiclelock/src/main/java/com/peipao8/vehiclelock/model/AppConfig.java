package com.peipao8.vehiclelock.model;

import com.peipao8.vehiclelock.model.location.LocationVO;

/**
 * 保存整个App 可能用到的所有信息，比如
 * 设备信息，网络请求链接，用户数据信息。
 */
public class AppConfig   {

    /** 当前位置 */
    public static LocationVO locationVO = null;

    private static final String     TAG        = "=====AppConfig";

    /** app下载地址 */
    public static        String     apkUri     = "http://api.peipao8.com:8088/HelloRunner/peipao.apk";

    /** 服务器地址 */
    public final static  String     urlAddress = "http://api.peipao8.com:8088";
    //    public final static String urlAddress = "http://192.168.0.222:8080";

    /** 七牛key */
    private static String uptoken = "";

    /** 设备ID,在欢迎页面得到权限后和七牛Uptoken一起初始化 */
    public static String deviceId = "";

    /** token */
    public static String token = "";

    /** 用户ID */
    public static String userId = "";

    /** version最新版本信息，级版本号 */
    public static int version_current;

    /** 请求失败次数 */
    public static int failedNum;

    /** 对外暴露get方法获取uptoken。并其中判空并同步请求多一层保障,注意调用此方法必须是在子线程中 */
    public static String getUptoken() {
        return uptoken;
    }


    /** AESutil加密工具类的可修改公钥 */
    public final static String key = "HelloRunner_____";

    /** 登录 */
    public final static String urlLogin = "/HelloRunner/user/login";

    /** 获取用户信息 */
    public final static String urlGetPersonInfo = "/HelloRunner/user/getpersoninfo/";

    /** 开始跑步 */
    public final static String urlStartrun = "/HelloRunner/run/start/";

    /** 删除轨迹 */
    public final static String urlDelrun = "/HelloRunner/run/del/";

    /** 结束跑步 */
    public final static String urlEndruning = "/HelloRunner/run/end/";

    /** 提交每公里跑步节点数据集合 */
    public final static String urlKilometerRunData = "/HelloRunner/run/kilometerData/";

    /** 按日查轨迹 */
    public final static String urlGetRunningByDay = "/HelloRunner/getRunningByDay/";

    /** 按用户ID查询跑步总数据 */
    public final static String urlGetRunCount = "/HelloRunner/GetRunCount/";

    /** 根据跑步ID查询轨迹 */
    public final static String urlGetRunning = "/HelloRunner/run/Get/";

    /** 按日查K */
    public final static String urlGetRunByDayList = "/HelloRunner/getRunByMonth/";

    /** 按月查K */
    public final static String urlGetRunByMonthList = "/HelloRunner/getRunbyYearList/";

    /** 获取七牛 key */
    public final static String urlQiniukey = "/HelloRunner/qiniukey";

    /** 获取附近的人 */
    public final static String urlPersons = "/HelloRunner/persons/";

    /** 获取附近的跑团 */
    public final static String urlRungroups = "/HelloRunner/rungroups/";

    /** 获取附近的活动 */
    public final static String urlActivitys = "/HelloRunner/activitys/";

    /** 修改个人信息 */
    public final static String urlEdituserinfo = "/HelloRunner/user/edituserinfo/";

    /** 创建跑团 */
    public final static String urlCreatRunGroup = "/HelloRunner/RunGroup/creat/";

    /** 解散跑团 */
    public final static String urlDelRunGroup = "/HelloRunner/RunGroup/del/";

    /** 修改跑团信息 */
    public final static String urlChangeRunGroup = "/HelloRunner/RunGroup/ChangeRunGroup/";

    /** 跑团详情 */
    public final static String urlGetRunGroup = "/HelloRunner/RunGroup/GetRunGroup/";

    /** 管理员查询所属跑团 */
    public final static String urlGetRunGroupByPublisher = "/HelloRunner/RunGroup/GetRunGroupByPublisher/";

    /** 成员ID查询所属跑团 */
    public final static String urlGetRunGroupByMemeber = "/HelloRunner/GetRunGroupByMemeber/";

    /** 加入跑团 */
    public final static String urlMemberJoin = "/HelloRunner/RunGroup/memberJoin/";

    /** 退出跑团 */
    public final static String urlMemberExit = "/HelloRunner/RunGroup/memberExit/";

    /** 审核加入成员 */
    public final static String urlAduitMember = "/HelloRunner/RunGroup/aduitMember/";

    /** 审核拒绝成员 */
    public final static String urlAduitMember1 = "/HelloRunner/RunGroup/refuseMember/";

    /** user */
    public final static String urlAddfirend = "/HelloRunner/firend/add/";

    /** 同意好友 */
    public final static String urlAgreefirend = "/HelloRunner/firend/agree/";

    /** 拒绝好友 */
    public final static String urlRefusalfirend = "/HelloRunner/firend/refusal/";

    /** 删除好友 */
    public final static String urlDelfirend = "/HelloRunner/firend/del/";

    /** 添加备注 */
    public final static String urlAddtagfirend = "/HelloRunner/firend/addTag/";

    public final static String urlFriends      = "/HelloRunner/firends/";

    /** 按日查排行(全国) */
    public final static String urlRankbyDayList = "/HelloRunner/getRankbyDayList/";
    
    
}
