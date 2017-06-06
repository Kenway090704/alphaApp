package com.alpha.alphaapp.comm;

/**
 * Created by kenway on 17/5/25 14:11
 * Email : xiaokai090704@126.com
 * 关于设备的常数
 */

public class DeviceConstants {
    public interface TERMINAL_TYPE {
        String PHONE = "PHONE";
    }

    /**
     * 获取手机验证码的时候使用
     */
    public interface GET_VERIFY {
        int LOGIN = 1;
        int REGISTER = 2;
        int FIND_PWD = 3;
        int VERIFY_OLD_PHONE_IN_CHANGE_BINDING = 4;
        int BINDING_NEW_PHONE_IN_CHANGE_BINDING = 5;
        int BIND_PHONE = 6;
        int MODIFY_PWD_BY_PHONE = 7;
    }

    /**
     * 上次的登录类型
     */
    public interface LASTLOGIN_TYPE {
        /**
         * 快速登录
         */
        int PHONE_QUICK = 0;
        /**
         * 手机或者帐号登录
         */
        int PHONE_OR_ACCOUNT = 1;
        /**
         * 微信授权登录
         */
        int AUTH_WEXIN = 2;
        /**
         * QQ授权登录
         */
        int AUTH_QQ = 3;
    }

}
