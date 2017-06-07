package com.alpha.alphaapp.comm;

/**
 * Created by kenway on 17/5/23 17:20
 * Email : xiaokai090704@126.com
 * <p>
 * URL常数
 */

public class URLConstans {

    private static final String URL_ROOT = "Http://passport.gdalpha.com:9998/user/";

    public interface URL {
        /**
         * (A)注册用户
         */
        String REGISTER = URL_ROOT + "register";
        /**
         * (B) 用户登录
         */
        String LOGIN = URL_ROOT + "login";
        /**
         * (C)	用户登出
         */
        String LOGINOUT = URL_ROOT + "logout";
        /**
         * (D)用户一般信息修改
         */
        String MODIFY_ACCOUNTINFO = URL_ROOT + "edit";

        /**
         * (F)	用户密码修改
         */
        String CHANGEPWDBYPWD = URL_ROOT + "changepw";

        /**
         * (G)	检查账户是否存在
         */
        String CHECKACCOUT = URL_ROOT + "checkaccount";


        /**
         * (H)用户绑定手机号、账号、微信、QQ
         */
        String BIND = URL_ROOT + "bind";

        /**
         * (J)	获取用户信息
         */
        String USETINFO = URL_ROOT + "info";

        /**
         * (K) 获取手机验证码
         */
        String PHONEVERIFY = URL_ROOT + "phoneVerify";

        /**
         * (L)  手机号找回密码
         */
        String PHONE_FIND_PW = URL_ROOT + "phoneFindPw";


        /**
         * (Z) Modify the password by the phone
         */
        String MODIFYPWDBYPHONE = URL_ROOT + "phoneEditPw";
    }
}
