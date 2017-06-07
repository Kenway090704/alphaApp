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
     * 获取手机验证码
     */
    public interface GET_VERIFY {
        /**
         * 手机号登录
         */
        int LOGIN = 1;
        /**
         * 手机号注册
         */
        int REGISTER = 2;
        /**
         * 手机号找回密码
         */
        int GET_PW = 3;
        /**
         * 更换绑定手机号-验证旧手机号
         */
        int VERIFY_OLD_PHONE = 4;
        /**
         * 更换绑定手机号-绑定新手机号
         */
        int BIND_NEW_PHONE = 5;
        /**
         * 绑定手机号
         */
        int BIND_PHONE = 6;
        /**
         * 手机号修改密码
         */
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
