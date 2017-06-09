package com.peipao8.vehiclelock.model.user;

import java.util.List;

public class LoginBeen {
    
    public int           collegeCount; // 0
    public List<String>  taskIds; //
    public List<KeyInfo> count; //
    
    
    public class KeyInfo {
        public int key; // 0
    }
    
    
    public UserInfo  userInfo;
    public TokenInfo tokenInfo;
    public LoginInfo loginInfo;
    
    
    public class UserInfo {
        public String  id; // string
        public String  password; // string
        public String  phone; // string
        public String  email; // string
        public String  nickName; // string
        public String  description; // string
        public String  smallPortrait; // string
        public String  largePortrait; // string
        public String  qq; // string
        public String  createTime; // 2017-03-21T05:11:46.377Z
        public String  birthday; // 2017-03-21T05:11:46.377Z
        public String  initialPassword; // string
        public String  imei; // string
        public int     statuspublic; //0,
        public int     userTypepublic; //0,
        public int     sexpublic; //0,
        public int     errorCountpublic; //0,
        public int     activeDayspublic; //0,
        public int     levelpublic; //0,
        public int     visiblepublic; //0,
        public int     loginCountpublic; //0,
        public boolean appealingpublic; //true,
        public boolean initPasswordpublic; //true
    }
    
    
    public class TokenInfo {
        public String tokenId; // string 
        public String userId; // string 
        public String userAgent; // string 
        public String ip; // string 
        public String app; // string 
        public String createTime; // 2017-03-21T05:11:46.377Z 
        public String expireTime; // 2017-03-21T05:11:46.377Z 
        public String lastUpdateTime; // 2017-03-21T05:11:46.378Z 
        public int    statuspublic; //0
    }
    
    
    public class LoginInfo {
        public String id; // string 
        public String userId; // string 
        public String agent; // string 
        public String ip; // string 
        public String app; // string 
        public String createTime; // 2017-03-21T05:11:46.378Z
        public String imei; // string
        public String model; // stringpublic String
        public int    longitudepublic; //0,
        public int    latitudepublic; //0,
        public int    typepublic; //0,
        public int    loginTypepublic; //0,
    }
}