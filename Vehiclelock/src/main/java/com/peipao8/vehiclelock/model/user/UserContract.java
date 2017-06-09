package com.peipao8.vehiclelock.model.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.peipao8.vehiclelock.LYangCode.utils.SystemMethrodUtils;
import com.peipao8.vehiclelock.LYangCode.utils.UiUtils;
import com.peipao8.vehiclelock.LYangCode.utils.data.SharePreManager;
import com.peipao8.vehiclelock.LYangCode.utils.json.FastJsonUtils;
import com.peipao8.vehiclelock.LYangCode.utils.log.LYangLogUtil;
import com.peipao8.vehiclelock.model.AppConfig;
import com.peipao8.vehiclelock.model.location.LocationVO;
import com.peipao8.vehiclelock.model.nearby.PersonInfoVO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/** 用户信息 */
public class UserContract {

    private static final String TAG ="UserContract" ;
    /** 登录类型  */
    private String loginType_ = "loginType";// 3手机注册 4学号登录
    public  String loginType;// 3手机注册 4学号登录

    /** 用户类型 */
    private String usertype_ = "usertype";
    public String usertype = ""; //第三方类型 0微信   1微博 2QQ 3手机

    /** 用户id */
    private String userId_ = "userId";
    public String userId = "";

    /** 三方id */
    private String QQ_otherLoginId_ = "QQ_otherLoginId";//qq
    public String QQ_otherLoginId = "";//qq
    private String weixin_otherLoginId_ = "weixin_otherLoginId";//微信
    private String weibo_otherLoginId_ = "weibo_otherLoginId";//微博
    public String weixin_otherLoginId = "";//微信
    public String weibo_otherLoginId = "";//微博

    /** 用户账号 */
    private String userNumber_ = "userNumber";
    public String userNumber = "";

    /** 密码 */
    private String passwordHash_ = "passwordHash";
    public String passwordHash = "";

    /** 陪跑币  积分商城返回首页刷新积分时，触发监听方法会刷新此值。 */
    private String money_="money";
    public String money;

    /** 手机号 */
    private String mobile_ = "mobile";
    public String mobile = "";

    /** 系统版本号 */
    private String osVersion_ = "osVersion";
    public String osVersion = "";

    /** 设备id */
    private String cilentDevId_ = "cilentDevId";
    public String cilentDevId = "";

    /** app版本 */
    private String appVersion_ = "appVersion";
    public String appVersion = "";

    /** 登录时间 */
    private String loginTime_ = "loginTime";
    public String loginTime = "";

    /** token 干嘛使得 */
    private String token_ = "token";
    public String token = "";

    /** 注册时间 */
    private String regTime_ = "regTime";
    public String regTime = "";

    /** 昵称 */
    private String nickName_ = "nickName";
    public String nickName = "";

    /** 性别 */
    private String sex_ = "sex";
    public String sex = "";

    /** 头像 存储 blob数据 ，好像没人引用,后面发现是单独存起来的String类型图片*/
    private String headImg_ = "headImg";
    private String headImg = "";

    /** 生日 */
    private String birth_ = "birth";
    public String birth = "";

    /** 身高 */
    private String height_ = "height";
    public String height = "";

    /** 体重 */
    private String weight_ = "weight";
    public String weight = "";

    /** 标签 */
    private String tag_ = "tag";
    public String tag = "";

    /** 心情 */
    private String feeling_ = "feeling";
    public String feeling = "";

    /** 年龄 */
    private String age_ = "age";
    public String age = "";

    /** 城市 */
    private String city_ = "city";
    public String city = "";

    /** 位置信息 */
    private String location_ = "location";
    public String location = "";

    /** 胸章 int */
    private String Medal_ = "Medal";
    public String Medal = "";

    /** 角色 */
    private String Role_ = "Role"; //用户角色     private  Role; //用户角色
    public String Role = ""; //用户角色     private  Role; //用户角色

    /** 头像链接 */
    private String headImageUrl_ = "headImageUrl"; //头像地址
    public String headImageUrl = ""; //头像地址

    @Override
    public String toString() {
        return "UserContract{" +
            "age='" + age + '\'' +
            ", loginType='" + loginType + '\'' +
            ", usertype='" + usertype + '\'' +
            ", userId='" + userId + '\'' +
            ", QQ_otherLoginId='" + QQ_otherLoginId + '\'' +
            ", weixin_otherLoginId='" + weixin_otherLoginId + '\'' +
            ", weibo_otherLoginId='" + weibo_otherLoginId + '\'' +
            ", userNumber='" + userNumber + '\'' +
            ", passwordHash='" + passwordHash + '\'' +
            ", money='" + money + '\'' +
            ", mobile='" + mobile + '\'' +
            ", osVersion='" + osVersion + '\'' +
            ", cilentDevId='" + cilentDevId + '\'' +
            ", appVersion='" + appVersion + '\'' +
            ", loginTime='" + loginTime + '\'' +
            ", token='" + token + '\'' +
            ", regTime='" + regTime + '\'' +
            ", nickName='" + nickName + '\'' +
            ", sex='" + sex + '\'' +
            ", headImg='" + headImg + '\'' +
            ", birth='" + birth + '\'' +
            ", height='" + height + '\'' +
            ", weight='" + weight + '\'' +
            ", tag='" + tag + '\'' +
            ", feeling='" + feeling + '\'' +
            ", city='" + city + '\'' +
            ", location='" + location + '\'' +
            ", Medal='" + Medal + '\'' +
            ", Role='" + Role + '\'' +
            ", headImageUrl='" + headImageUrl + '\'' +
            '}';
    }

    /** 单例 */
    private static UserContract instance = null;

    private UserContract() { }

    static {
        getInstance () ;
    }

    /** 实例 为null ，则查表 初始化 instance 不会重新查表，更新User */
    public static UserContract getInstance( ) {
        if (instance == null) {
            instance = new UserContract();
            initInstance( );//初始化
        }
        return instance;
    }
    /**清空初始化内容*/
    public static void clear( ){
        SharedPreferences pref = UiUtils.getContext ().getSharedPreferences("UserContract", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(instance.userId_, "");
        editor.putString(instance.userNumber_, "");
        editor.putString(instance.QQ_otherLoginId_, "");
        editor.putString(instance.weixin_otherLoginId_, "");
        editor.putString(instance.weibo_otherLoginId_, "");
        editor.putString(instance.mobile_, "");
        editor.putString(instance.passwordHash_, "");
        editor.putString(instance.osVersion_, "");
        editor.putString(instance.cilentDevId_, "");
        //editor.putString(instance.appVersion_, "");  //程序版本我觉得不需要清除
        editor.putString(instance.loginTime_, "");
        editor.putString(instance.token_, "");
        editor.putString(instance.regTime_, "");
        editor.putString(instance.nickName_, "");
        editor.putString(instance.sex_, "");
        editor.putString(instance.birth_,"");
        editor.putString(instance.height_, "");
        editor.putString(instance.weight_, "");
        editor.putString(instance.tag_, "");
        editor.putString(instance.feeling_, "");
        editor.putString(instance.age_, "");
        editor.putString(instance.city_, "");
        editor.putString(instance.location_, "");
        editor.putString(instance.money_, "");
        editor.putString(instance.Medal_, "");
        editor.putString(instance.Role_, "");
        editor.putString(instance.headImageUrl_, "");
        editor.putString(instance.loginType_, "");
        //-----------------------------------额外清除-----------------------------------
        editor.putString(instance.headImg_, ""); //清除头像sp存储。
        editor.commit();
        AppConfig.locationVO = null ;            //清除位置信息的头号存储位置
        setNullInstance ();
    }

    /** 实例 不为null ，用于再退出登录以后，清除本地的身份信息。  */
    private static synchronized  void setNullInstance( ) {
        if (instance != null) {
            instance = new UserContract();
            initInstance( );//初始化
        }
    }


    /** 查表 重新初始化 instance，调用此方法请先判断对象本类实例是否为空 @return */
    private static synchronized void initInstance() {
        /**查询sharedprefrence，初始化instance*/
        SharedPreferences sp = UiUtils.getContext ().getSharedPreferences("UserContract", Context.MODE_PRIVATE);
        instance.userId = sp.getString(instance.userId_, "");
        instance.money = sp.getString(instance.money_, "0");
        instance.userNumber = sp.getString(instance.userNumber_, "");
        instance.QQ_otherLoginId = sp.getString(instance.QQ_otherLoginId_, "");
        instance.weixin_otherLoginId = sp.getString(instance.weixin_otherLoginId_, "");
        instance.weibo_otherLoginId = sp.getString(instance.weibo_otherLoginId_, "");
        instance.mobile = sp.getString(instance.mobile_, "");
        instance.passwordHash = sp.getString(instance.passwordHash_, "");
        instance.osVersion = sp.getString(instance.osVersion_, "");
        instance.cilentDevId = sp.getString(instance.cilentDevId_, "");
        instance.birth = sp.getString(instance.birth_,"");
        instance.tag = sp.getString(instance.tag_,"");
        instance.appVersion = SystemMethrodUtils.getVersion ();
        instance.loginTime = sp.getString(instance.loginTime_, "");
        instance.token = sp.getString(instance.token_, "");
        instance.regTime = sp.getString(instance.regTime_, "");
        instance.nickName = sp.getString(instance.nickName_, "");
        instance.sex = sp.getString(instance.sex_, "");
        instance.height = sp.getString(instance.height_, "");
        instance.weight = sp.getString(instance.weight_, "");
        instance.feeling = sp.getString(instance.feeling_, "");
        instance.age = sp.getString(instance.age_, "");
        instance.city = sp.getString(instance.city_, "");
        instance.location = sp.getString(instance.location_, "");
        instance.Medal = sp.getString(instance.Medal_, "");
        instance.Role = sp.getString(instance.Role_, "");
        instance.headImageUrl = sp.getString(instance.headImageUrl_, "");
        instance.usertype = sp.getString(instance.usertype_, "");//用户登录类型
        instance.loginType = sp.getString(instance.loginType_, "");//用户登录类型
        LYangLogUtil.d ( TAG, "initInstance--> instance实例=" + instance.toString() );    //调试输出----       l
    }

    /** 用此对象 更新 sharedprefrence 当刷新积分币的时候会刷新对象并调用本方法，完成对象数据更新到SP bitmap 头像 */
    public static synchronized void UpdateInstanceSp(UserContract userContract) {
       /*将此对象的值（此对象的获得只可以是单例的。所以此对象就是本类对象），写入sharedprefrence*/
        SharedPreferences pref = UiUtils.getContext ().getSharedPreferences("UserContract", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(instance.userId_, userContract.userId);
        editor.putString(instance.money_, userContract.money);
        editor.putString(instance.usertype_, userContract.usertype);
        editor.putString(instance.userNumber_, userContract.userNumber);
        editor.putString(instance.QQ_otherLoginId_, userContract.QQ_otherLoginId);
        editor.putString(instance.weixin_otherLoginId_, userContract.weixin_otherLoginId);
        editor.putString(instance.weibo_otherLoginId_, userContract.weibo_otherLoginId);
        editor.putString(instance.mobile_, userContract.mobile);
        editor.putString(instance.passwordHash_, userContract.passwordHash);
        editor.putString(instance.osVersion_, userContract.osVersion);
        editor.putString(instance.cilentDevId_, userContract.cilentDevId);
        editor.putString(instance.appVersion_, userContract.appVersion);
        editor.putString(instance.loginTime_, userContract.loginTime);
        editor.putString(instance.token_, userContract.token);
        editor.putString(instance.regTime_, userContract.regTime);
        editor.putString(instance.nickName_, userContract.nickName);
        editor.putString(instance.sex_, userContract.sex);
        editor.putString(instance.birth_,userContract.birth);
        editor.putString(instance.height_, userContract.height);
        editor.putString(instance.weight_, userContract.weight);
        editor.putString(instance.tag_, userContract.tag);
        editor.putString(instance.feeling_, userContract.feeling);
        editor.putString(instance.age_, userContract.age);
        editor.putString(instance.city_, userContract.city);
        editor.putString(instance.location_, userContract.location);
        editor.putString(instance.Medal_, userContract.Medal);
        editor.putString(instance.Role_, userContract.Role);
        editor.putString(instance.headImageUrl_, userContract.headImageUrl);
        editor.putString(instance.loginType_, userContract.loginType);
        editor.commit();
        LYangLogUtil.d ( TAG, "UpdateInstance--> 使用UserContract更新本地用户信息sp文件成功"  );    //调试输出----
    }
    /** 用此对象 更新 sharedprefrence 当刷新积分币的时候会刷新对象并调用本方法，完成对象数据更新到SP bitmap 头像 */
    public static synchronized void UpdateInstanceSp(PersonInfoVO personInfoVO) {
       /*将此对象的值（此对象的获得只可以是单例的。所以此对象就是本类对象），写入sharedprefrence*/
        SharedPreferences pref = UiUtils.getContext ().getSharedPreferences("UserContract", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(instance.userId_, personInfoVO.userId);
        editor.putString(instance.money_, personInfoVO.money);
        editor.putString(instance.userNumber_, personInfoVO.userNumber);
        editor.putString(instance.nickName_, personInfoVO.nickName);
        editor.putString(instance.sex_, personInfoVO.sex);
        editor.putString(instance.birth_,personInfoVO.birth);
        editor.putString(instance.height_, personInfoVO.height);
        editor.putString(instance.weight_, personInfoVO.weight);
        editor.putString(instance.tag_, personInfoVO.tag);
        editor.putString(instance.feeling_, personInfoVO.feeling);
        editor.putString(instance.age_, personInfoVO.age);
        editor.putString(instance.city_, personInfoVO.city);
        editor.putString(instance.Role_, personInfoVO.userRole);
        editor.putString(instance.headImageUrl_, personInfoVO.headImg);
        editor.commit();
        LYangLogUtil.d ( TAG, "UpdateInstance--> 使用PersonInfoVO更新本地用户信息sp文件成功"  );    //调试输出----
    }

    /** 设置用户头像 */
    public void setSpBase64Head( Bitmap bitmap) {
        SharePreManager.saveValue ("UserContract",headImg_,bitmapToBase64(bitmap)  );
    }

    /** 获取用户头像 */
    public Bitmap GetHeadBitmap( ) {
        return base64ToBitmap(SharePreManager.getValue ("UserContract",headImg_,0));
    }

    /** 更新位置信息
     * @param locationVO 将要设置的对象  */
    public void setLocation(LocationVO locationVO) {
        String obgString = FastJsonUtils.getObgString ( locationVO );
        instance.location = obgString;
        AppConfig.locationVO = locationVO;
        SharePreManager.saveValue ( "UserContract" ,location_,obgString );
    }

    /** 获取用户位置信息 */
    public static LocationVO getLocation( ) {
        return FastJsonUtils.getSingleBean (SharePreManager.getValue ( "UserContract", instance.location_, 0 ),
            LocationVO.class );
    }

    /** bitmap转为base64
     * @param bitmap @return */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /** base64转为bitmap
     * @param base64Data @return */
    public static Bitmap base64ToBitmap(String base64Data) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
    }

    /** 将用户信息转换成一个 个人对象*/
    public static PersonInfoVO getPersonInfoVO( ) {
        PersonInfoVO personInfoVO = new PersonInfoVO ();
        personInfoVO.headImg = instance.headImageUrl;
        personInfoVO.userRole = instance.Role;
        personInfoVO.userNumber = instance.userNumber;
        personInfoVO.age = instance.age;
        personInfoVO.birth = instance.birth;
        personInfoVO.city = instance.city;
        personInfoVO.feeling = instance.feeling;
        personInfoVO.height = instance.height;
        personInfoVO.locationVO = instance.getLocation ();
        personInfoVO.sex = instance.sex;
        personInfoVO.tag = instance.tag;
        personInfoVO.weight = instance.weight;
        personInfoVO.nickName = instance.nickName;
        personInfoVO.userId = instance.userId;
        personInfoVO.money = instance.money;
        return personInfoVO;
    }

}
