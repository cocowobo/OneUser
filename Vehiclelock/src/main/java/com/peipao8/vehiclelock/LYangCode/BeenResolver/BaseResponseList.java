package com.peipao8.vehiclelock.LYangCode.BeenResolver;

import com.alibaba.fastjson.JSONArray;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : peipao8.com
 * Author     : 叶之书
 * Date       : 2016/9/14 17:08
 */
public class BaseResponseList {
    public String    code;
    public String    msg;
    public JSONArray result ;

    public BaseResponseList() {
        super ();
    }

    public boolean isSuccess(){
        if ("0000".equals(code.trim())) {
            return true;
        }
        return false;
    }

    public Object getResult() {
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
