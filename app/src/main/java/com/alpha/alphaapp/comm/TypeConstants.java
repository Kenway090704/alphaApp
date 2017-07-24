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
     * 注册类型
     */
    public interface ACCOUNT_TYPE {
        int ACCOUNT = 0;
        int PHONE = 1;
        int AUTH_QQ = 3;
        int AUTH_WECHAT = 4;
    }

    /**
     * 登录类型
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
         * QQ授权登录
         */
        int AUTH_QQ = 3;
        /**
         * 微信授权登录
         */
        int AUTH_WX = 4;
    }

    /**
     * 获取手机验证码
     */
    public interface GET_VERIFY_TYPE {
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
     * (AT)获取商品列表
     */

    public interface GET_GOODS_LIST_TYPE {
        /**
         * 获取商品列表
         */
        int ALL = 0;
        /**
         * 获取商品数量包含个人已兑换
         */
        int EXCHANGE_COUNT = 1;
        /**
         * 商品剩余数量
         */
        int REMIAN_COUNT = 2;
    }

    /**
     * (AU)获取商品详情
     */

    public interface GET_GOODS_DETAIL_TYPE {
        /**
         * 获取商品详情
         */
        int ALL = 0;
        /**
         * 获取商品详情包含个人已兑换数量exchange_count
         */
        int EXCHANGE_COUNT = 1;
        /**
         * 返回商品剩余数量
         */
        int REMIAN_COUNT = 2;
    }


    /**
     *产品类型
     */

    public interface IP_PRODUCT_TYPE {
        /**
         * 爆裂飞车
         */
        int TRANSFROM_CAR = 0;

        /**
         * 零速争霸
         */
        int SPEED = 1;
        /**
         * 超级飞侠
         */
        int SUPER_WAVING   = 2;
    }

    /**
     * 产品Proudct_ID
     */
    public  interface PRODUCT_ID{

        int   NONE_PRODUCT=0;
        /**
         * 零速争霸
         */
        int SPEED = 34;

        /**
         * 爆裂飞车2
         */
        int TRANSFROM_CAR = 91;


        /**
         * 超级飞侠
         */
        int SUPER_WAVING   = 182;
    }



}
