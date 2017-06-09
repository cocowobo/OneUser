package com.peipao8.vehiclelock.LYangCode.BeenResolver;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by panxd on 2016/3/16.
 */
public class BaseResponse {

    public String     code;
    public String     msg;
    public JSONObject result ;

    public BaseResponse() {
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
