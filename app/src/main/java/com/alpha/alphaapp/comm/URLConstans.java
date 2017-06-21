package com.alpha.alphaapp.comm;

/**
 * Created by kenway on 17/5/23 17:20
 * Email : xiaokai090704@126.com
 * <p>
 * URL常数
 */

public class URLConstans {

    private static final String URL_ROOT = "Http://passport.gdalpha.com:9998/user/";

    /**
     * 获取省、市、县的url
     */
    public interface GET_ADDR_URL {
        /**
         * 省  无参数
         */
        String PROVINCE = "https://my.gdalpha.com/user/get_province.html";
        /**
         * 市    参数：	 p:广东省
         */
        String CITY = "https://my.gdalpha.com/user/get_city.html";
        /**
         * 区     参数： 	 c:深圳
         */
        String AREA = "https://my.gdalpha.com/user/get_area.html";
        /**
         * 学校
         * <p>参数：
         * p:广东省
         * c:深圳
         * area:福田区
         * text:模糊搜索名字
         * </p>
         */
        String SCHOOL = "https://my.gdalpha.com/user/get_school.html";

    }

    public interface GET_ICON {
        /**
         * 图片大小 60*60
         */
        String ICON60 = "http://cdn.gdalpha.com/icon/60/";
        /**
         * 图片大小 100*100
         */
        String ICON100 = "http://cdn.gdalpha.com/icon/100/";
        /**
         * 图片大小 200*200
         */
        String ICON200 = "http://cdn.gdalpha.com/icon/200/";
        /**
         * 图片大小 300*300
         */
        String ICON300 = "http://cdn.gdalpha.com/icon/300/";
    }

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
        String GET_USETINFO = URL_ROOT + "info";

        /**
         * (K) 获取手机验证码
         */
        String PHONEVERIFY = URL_ROOT + "phoneVerify";

        /**
         * (L)  手机号找回密码
         */
        String PHONE_FIND_PW = URL_ROOT + "phoneFindPw";

        /**
         * (M) 更换绑定手机号-验证旧手机号
         */
        String CHANGE_BING_PHONE_VERIFY_OLD_PHONE = URL_ROOT + "changeBindPhone1";
        /**
         * (N) 更换绑定手机号-绑定新手机号
         */
        String CHANGE_BING_PHONE_BIND_NEW_PHONE = URL_ROOT + "changeBindPhone2";

        /**
         * (Y)获取头像列表
         */
        String GET_ICONS_LIST = URL_ROOT + "getIcons";
        /**
         * (Z) Modify the password by the phone
         */
        String MODIFY_PWD_BY_PHONE = URL_ROOT + "phoneEditPw";

        /**
         * (AF) 重置密码
         */
        String RESET_PW = URL_ROOT + "resetPw";
    }


}
