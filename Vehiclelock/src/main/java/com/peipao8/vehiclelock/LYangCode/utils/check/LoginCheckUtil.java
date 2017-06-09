package com.peipao8.vehiclelock.LYangCode.utils.check;

import android.text.TextUtils;

public class LoginCheckUtil {

    /** 是否电话验证 */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、147、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9
         */
        String telRegex = "[1][34587]\\d{9}";// "[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7,
        // 8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty ( mobiles ))
            return false;
        else
            return mobiles.matches ( telRegex );
    }


    /**
     * 验证验证码是否超时
     * checkCodeTime 获取时间
     * time          超时时间(min)
     */
    public static boolean isCheckCodeVoerTime(long checkCodeTime,long time) {
        return (System.currentTimeMillis ()-checkCodeTime)/1000>=time*60*1000;
    }


}
