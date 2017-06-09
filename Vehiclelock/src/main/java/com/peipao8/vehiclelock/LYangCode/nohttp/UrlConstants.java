package com.peipao8.vehiclelock.LYangCode.nohttp;

/**
 * Created by 田鹏 on 2017/3/20.
 */

public class UrlConstants {
    public static final String IP_HOST_URL = "http://211.159.150.238:38080/api/park/";
    public static final String  HOST_URL= "http://192.168.0.43:8080/api/park/";
    public static final String TAG = "parseNetworkResponse";
    public static final String checkUrlPhone    = UrlConstants.HOST_URL + "login/check";
    public static final String codeGetUrl = UrlConstants.HOST_URL + "login/code/";
    public static final String forgetPwdCodeUrl = UrlConstants.codeGetUrl+ "notCheck/";
    public static final String registUrl = UrlConstants.HOST_URL + "login/register";
    public static final String loginUrl = UrlConstants.HOST_URL + "login/login";
    public static final String  forgotPwd = UrlConstants.HOST_URL+"login/forgetPassword";
    public static final String  changePhone = UrlConstants.HOST_URL+"user/update/";
}
