package com.peipao8.vehiclelock.NetRequest;

import com.peipao8.vehiclelock.LYangCode.nohttp.CallServer;
import com.peipao8.vehiclelock.LYangCode.nohttp.FastJsonRequest;
import com.peipao8.vehiclelock.LYangCode.nohttp.HttpListener;
import com.peipao8.vehiclelock.model.BaseResult;
import com.yanzhenjie.nohttp.RequestMethod;

import java.util.Map;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : 大胸平天下
 * Author     : 太阳光辉
 * Date       : 2017/3/23 9:11
 */

public class UserInforRequest {
    public static void registCheckPhone(int what, String url, Object cancalObj, Map params, HttpListener<BaseResult> callback){
        baseStringParamsRequest ( what,url,cancalObj,params ,callback);
    }
    
    public static void registGetCode(int what, String url, Object cancalObj, Map params, HttpListener<BaseResult> callback){
        baseStringParamsRequest ( what,url,cancalObj,params ,callback);
    }

    public static void userLogin(int what, String url, Object cancalObj, Map params, HttpListener<BaseResult> callback){
        baseStringParamsRequest ( what,url,cancalObj,params ,callback);
    }
    
    private static void baseStringParamsRequest(int what, String url, Object cancalObj, Map params , HttpListener<BaseResult> callback) {
        FastJsonRequest fastJsonRequest = new FastJsonRequest (url, RequestMethod.POST);     // 请求对象
        fastJsonRequest.setCancelSign ( cancalObj );
        fastJsonRequest.add ( params );
        CallServer.getRequestInstance ().add (what,fastJsonRequest,callback); //添加到请求而队列
    }
}
