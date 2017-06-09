package com.peipao8.vehiclelock.model;

public class BaseResult {

    /** 响应码 */
    public String code;

    /** 响应消息 */
    public String message;

    /** 响应结果 */
    public String body ;

    /** 响应描述 */
    public String exceptionDescription ;
    
    @Override
    public String toString() {
        return "BaseResult{" + "code='" + code + '\'' + ", message='" + message + '\'' + ", body='" + body + '\'' +
            ", exceptionDescription='" + exceptionDescription + '\'' + '}';
    }
}
