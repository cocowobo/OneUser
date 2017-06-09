package com.peipao8.vehiclelock.LYangCode.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import android.util.Log;


public class StringUtil {

    /**
     * 验证中文名
     *
     * @param trueName
     */
    public static boolean isTrueName(String trueName) {
        String TRUENAME_REGEX = "[\u4e00-\u9fa5]{2,4}";
        return trueName.matches ( TRUENAME_REGEX );
    }

    //	/**
    //	 * 邮件验证
    //	 * @param email
    //	 * @return
    //	 */
    //	public static boolean isEmail(String email){
    //		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    //		return email.matches(EMAIL_REGEX);
    //	}

    /**
     * 手机号验证
     *
     * @param mobiles
     */
    public static boolean isMobileNumber(String mobiles) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        //		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
        p = Pattern.compile ( "^(13[0-9]|14[0-9]|17[0-9]|15[0-9]|18[0-9])\\d{8}$" );// 验证手机号
        m = p.matcher ( mobiles );
        b = m.matches ();
        return b;
    }

    /**
     * 将任意对象的字段转换为json字符串形式
     *
     * @param obj
     */
    public static String reflect(Object obj) {
        StringBuffer result = new StringBuffer ();
        if (obj == null)
            return result.toString ();
        Field[] fields = obj.getClass ().getDeclaredFields ();
        String  type1  = "int,Integer,float,double,long,short,byte,java.lang.Float,java.lang.Double,java.lang.Long," + "java.lang.Short,java.lang.Byte,";
        String  type2  = "java.lang.String,boolean,char,java.lang.String,java.lang.Boolean,java.lang.Character,";
        result.append ( "{" );
        for (int j = 0; j < fields.length; j++) {
            fields[ j ].setAccessible ( true );
            // 字段名
            result.append ( "\"" + fields[ j ].getName () + "\":" );
            // 字段值
            if (type1.contains ( fields[ j ].getType ().getName () + "," ) || type2.contains ( fields[ j ].getType ()
				.getName () + "," )) {
                try {
                    String ss = ( fields[ j ].get ( obj ) + "" ).trim ();
                    if (ss == "null" || ss == null || fields[ j ].get ( obj ) == null) {
                        if (j == fields.length - 1) {
                            result.append ( fields[ j ].get ( obj ) + "" );
                        } else {
                            result.append ( fields[ j ].get ( obj ) + "," );
                        }
                    } else if (ss == "") {
                        if (j == fields.length - 1) {
                            result.append ( "null" );
                        } else {
                            result.append ( "null," );
                        }
                    } else {
                        if (type1.contains ( fields[ j ].getType ().getName () + "," )) {
                            if (j == fields.length - 1) {
                                result.append ( fields[ j ].get ( obj ) + "" );
                            } else {
                                result.append ( fields[ j ].get ( obj ) + "," );
                            }
                        } else {
                            if (j == fields.length - 1) {
                                result.append ( "\"" + fields[ j ].get ( obj ) + "\"" );
                            } else {
                                result.append ( "\"" + fields[ j ].get ( obj ) + "\"," );
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace ();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace ();
                }
            }
        }
        result.append ( "}" );
        return result.toString ();
    }

    /**
     * 根据uri获取真实路径
     *
     * @param contentUri
     * @param context
     */
    public static String getRealPathFromURI(Uri contentUri, Context context) {
        String res = null;
        //		Log.i("JsonToString", "JsonToString----------->"+contentUri);
        //		Log.i("JsonToString", "JsonToString1----------->"+mContext);
        String[] proj   = {MediaStore.Images.Media.DATA};
        Cursor   cursor = context.getContentResolver ().query ( contentUri, proj, null, null, null );
        if (cursor.moveToFirst ()) {//cursor为null
            int column_index = cursor.getColumnIndexOrThrow ( MediaStore.Images.Media.DATA );
            res = cursor.getString ( column_index );
        }
        cursor.close ();
        return res;
    }

    /**
     * 根据uri获取真实路径
     *
     * @param contentUri
     * @param context
     */
    public static String getRealPathFromURI1(Uri contentUri, Context context) {
        String res = null;
        //		Log.i("JsonToString", "JsonToString----------->"+contentUri);


        //		Log.i("JsonToString", "JsonToString1----------->"+mContext);
        Cursor cursor = context.getContentResolver ().query ( contentUri, null, null, null, null );
        //第一行第二列保存路径strRingPath
        res = cursor.getString ( 1 );
        cursor.close ();
        cursor.close ();
        return res;
    }

    /**
     * 封装字节数组与参数
     *
     * @param json
     * @param image
     */
    public static byte[] getPacket(String json, byte[] image) {
        byte[] jsonb   = json.getBytes ();
        int    length  = image.length + jsonb.length;
        byte[] bytes   = new byte[ length + 1 ];
        byte[] lengthb = InttoByteArray ( jsonb.length, 1 );
        System.arraycopy ( lengthb, 0, bytes, 0, 1 );
        System.arraycopy ( jsonb, 0, bytes, 1, jsonb.length );
        System.arraycopy ( image, 0, bytes, 1 + jsonb.length, image.length );
        return bytes;
    }

    /**
     * 将int转换为字节数组
     *
     * @param iSource
     * @param iArrayLen
     */
    public static byte[] InttoByteArray(int iSource, int iArrayLen) {

        byte[] bLocalArr = new byte[ iArrayLen ];
        for (int i = 0; ( i < 4 ) && ( i < iArrayLen ); i++) {
            bLocalArr[ i ] = (byte) ( iSource >> 8 * i & 0xFF );
        }
        return bLocalArr;
    }

    /**
     * 将byte数组bRefArr转为一个整数,字节数组的低位是整型的低字节位
     *
     * @param bRefArr
     */
    public static int BytestoInt(byte[] bRefArr) {

        int  iOutcome = 0;
        byte bLoop;
        for (int i = 0; i < bRefArr.length; i++) {
            bLoop = bRefArr[ i ];
            iOutcome += ( bLoop & 0xFF ) << ( 8 * i );
        }
        return iOutcome;
    }

    /**
     * MD5加密
     *
     * @param encypStr
     * @param charset
     */
    public static String GetMD5(String encypStr, String charset) {
        String retStr      = "";
        byte[] inputBye;
        byte[] outputBye;
        char   hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            inputBye = encypStr.getBytes ();
            MessageDigest md5 = MessageDigest.getInstance ( "MD5" );
            md5.update ( inputBye );
            outputBye = md5.digest ();
            int  j     = outputBye.length;
            char str[] = new char[ j * 2 ];
            int  k     = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = outputBye[ i ];
                str[ k++ ] = hexDigits[ byte0 >>> 4 & 0xf ];
                str[ k++ ] = hexDigits[ byte0 & 0xf ];
            }
            return new String ( str );
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        }
        return retStr;
    }

    /**
     * 去掉json字符串的方法头
     *
     * @param json
     * @param title
     */
    public static String JsonToString(String json, String title) {

        String result = "fail";
        //		Log.i("JsonToString", "JsonToString----------->"+json);

        try {
            JSONObject jsonObject = new JSONObject ( json );
            //			Log.i("jsonObject", "jsonObject----------->"+jsonObject);
            result = jsonObject.getString ( title );
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        return result;
    }

    /**
     * 将json转换为对象
     *
     * @param json
     */
    public static Object JsonToObject(String json, Object obj) {
        Object object = null;
        if (obj == null)
            return null;
        //			JSONObject jsonObject =new JSONObject(json);
        Field[] fields = obj.getClass ().getDeclaredFields ();
        for (int j = 0; j < fields.length; j++) {
            fields[ j ].setAccessible ( true );
        }
        return object;
    }

    /**
     * 将json转换为list对象
     *
     * @param jsonString
     * @param clazz
     */
    public static List<Object> JsonToArray(String jsonString, Class<?> clazz) {
        //         JSONArray array = JSONArray.fromObject(jsonString);
        List<Object> objects = new ArrayList<Object> ();
        //         for(int i = 0; i < array.size(); i++){
        //             JSONObject jsonObject = array.getJSONObject(i);
        //             objects.add(JSONObject.toBean(jsonObject, clazz));
        //         }
        return objects;
    }

    //全脚转半角
    public static String ToDBC(String input) {
        //		Log.i("input", ""+input);
        char[] c = input.toCharArray ();
        for (int i = 0; i < c.length; i++) {
            if (c[ i ] == 12288) {
                c[ i ] = (char) 32;
                continue;
            }
            if (c[ i ] > 65280 && c[ i ] < 65375)
                c[ i ] = (char) ( c[ i ] - 65248 );
        }
        return new String ( c );
    }

    /**
     * @param s      时间字符串	，如：2015年09月20日 00时00分00秒
     * @param format 时间格式  如：yyyy年MM月dd日 HH时mm分ss秒
     * @return 毫秒值
     */
    @SuppressLint("SimpleDateFormat")
    public static long getmillisecond(String s, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy年MM月dd日 HH时mm分ss秒" );
        long             ss        = 0;
        try {
            Date date = formatter.parse ( s );

            ss = date.getTime ();
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return ss;
    }
    //	/***
    //	 * 比较版本号
    //	 * 格式：x.x.x
    //	 */
    //	public static boolean isUpdate(String s){
    ////		boolean re = false;
    //		String[] net = s.split(".");
    //		String[] nat = (AppConfig.userName.substring(AppConfig.userName.lastIndexOf("#"))).split(".");
    //		for(int i=0;i<3;i++){
    //			if(Integer.parseInt(net[i])>Integer.parseInt(nat[i])){
    //				return true;
    //			}
    //		}
    //
    //		return false;
    //	}

    /**
     * 函数内部有指定的地址
     */
    public static void getUrl(Context context) {
        String url    = "http://a.app.qq.com/o/simple.jsp?pkgname=com.peipao8.vehiclelock";
        Intent intent = new Intent ();
        intent.setAction ( "android.intent.action.VIEW" );
        Uri content_url = Uri.parse ( url );
        intent.setData ( content_url );
        context.startActivity ( intent );
    }


    /********************************************************************/
    //该方法将你输入的字符串，通过md5加密，返回一个加密後的字符串
    public static String MD5Encrypt(String inStr) {

        MessageDigest md     = null;
        String        outStr = null;
        try {
            md = MessageDigest.getInstance ( "MD5" );//可以选中其他的算法如SHA
            byte[] digest = md.digest ( inStr.getBytes () );
            //返回的是byet[]，要转化为String存储比较方便
            //			outStr = bytetoString(digest);
            outStr = toHexString ( digest );
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace ();
        }
        return outStr;
    }

    public static String bytetoString(byte[] digest) {

        String str     = "";
        String tempStr = "";
        for (int i = 1; i < digest.length; i++) {
            tempStr = ( Integer.toHexString ( digest[ i ] & 0xff ) );
            if (tempStr.length () == 1) {
                str = str + "0" + tempStr;
            } else {
                str = str + tempStr;
            }
        }
        //		Log.i("md5", str);
        //		Log.i("md5", str.toLowerCase());
        return str.toLowerCase ();
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String toHexString(byte[] b) {
        //String to  byte
        StringBuilder sb = new StringBuilder ( b.length * 2 );
        for (int i = 0; i < b.length; i++) {
            sb.append ( HEX_DIGITS[ ( b[ i ] & 0xf0 ) >>> 4 ] );
            sb.append ( HEX_DIGITS[ b[ i ] & 0x0f ] );
        }
        //		return sb.toString();    //
        return sb.toString ().toLowerCase ();
    }

    /********************************************************************/

}

