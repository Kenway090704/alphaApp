package com.alpha.alphaapp.comm;

/**
 * Created by kenway on 17/5/25 14:11
 * Email : xiaokai090704@126.com
 * 包含获取验证码的类型,上次登录的类型
 */

public class TypeConstants {
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
      登录类型
     */
    public interface LOGIN_TYPE {
        /**
         * 快速登录
         */
        int PHONE_QUICK = 0;
        /**
         * 手机密码登录
         */
        int PHONE_PW = 1;

        /**
         * 帐号密码登录
         */
        int ACCONUT_PW = 2;
        /**
         * 微信授权登录
         */
        int AUTH_WX = 3;
        /**
         * QQ授权登录
         */
        int AUTH_QQ = 4;
    }

}
