package com.alpha.lib_stub.comm;


/**
 * Created by kenway on 17/5/23 17:20
 * Email : xiaokai090704@126.com
 * <p>
 * URL常数
 */

public class URLConstans {

    /**
     * release
     */
//    public static  String URL_ROOT = "Http://openapi.gdalpha.com:9998/user/";
    /**
     * debug
     */
//    public static final String URL_ROOT = "Http://passport.gdalpha.com:9998/user/";

    //内网测试url,为了测试激活记录中有product_id
    public static final String URL_ROOT = "http://192.168.199.102:8089/user/";


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

        /**
         * 默认头像
         */
        String ICON_DEFAULT = "http://cdn.gdalpha.com/icon/100/9901.jpg";
    }

    /**
     * 三个产品的官网地址
     */
    public interface OFFICAL_WEB_URL {
        /**
         * 爆裂飞车官网
         */
        String TRANSFORM_CAR = "http://fc2.auldey.com";
        /**
         * 零速争霸
         */
        String SPEED = "http://z.auldey.com";
        /**
         * 超级飞侠
         */
        String SUPER_WAVING = "http://sw.auldey.com";
    }

    /**
     * 获取商品goods图片的url
     */
    public interface GOODS_PICTURE_URL {
        /**
         * https://bms.gdalpha.com/Public/uploads/CoverImages/2017-06-19/Ci_8251149785560010000.jpg
         */
        String GOODS_ICON = "https://bms.gdalpha.com/";
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
        String CHANGE_PWD_BY_PWD = URL_ROOT + "changepw";

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
         * (O)绑定激活
         */

        String BIND_ACTIVE_CODE = URL_ROOT + "bindCode";

        /**
         * (P)检测激活码是否正确
         */
        String CHECTOUT_ACTIVIT_CODE = URL_ROOT + "checkCode";

        /**
         * (Q)渠道cdk检索
         */
        String CHANNER_CHECKOUT = URL_ROOT + "channelCheck";
        /**
         * (T)获取收货地址
         */
        String GET_SHIPPING_ADDR = URL_ROOT + "getAddress";
        /**
         * (U)添加收货地址
         */
        String ADD_SHIPING_ADDR = URL_ROOT + "addAddress";
        /**
         * (V)编辑收货地址
         */
        String EDIT_SHIPPING_ADDR = URL_ROOT + "editAddress";
        /**
         * (W)删除收货地址
         */
        String DEL_SHIPPING_ADDR = URL_ROOT + "delAddress";
        /**
         * (X)设置默认收货地址
         */
        String SET_DEFUALT_ADDR = URL_ROOT + "defaultAddress";

        /**
         * (Y)获取头像列表
         */
        String GET_ICONS_LIST = URL_ROOT + "getIcons";
        /**
         * (Z) Modify the password by the phone
         */
        String MODIFY_PWD_BY_PHONE = URL_ROOT + "phoneEditPw";
        /**
         * (AA)获取用户积分信息
         */
        String GET_USER_SCORE_INFO = URL_ROOT + "getScoreInfo";
        /**
         * (AB)用户增加积分
         */
        String USER_ADD_SCORE = URL_ROOT + "addScore";
        /**
         * (AC)用户扣除积分
         */
        String USER_REDUCE_SCORE = URL_ROOT + "delScore";
        /**
         * (AF) 重置密码
         */
        String RESET_PW = URL_ROOT + "resetPw";

        /**
         * (AT)获取商品列表
         */

        String GET_GOODS_LIST = URL_ROOT + "goodsList";
        /**
         * (AS)兑换商品
         */

        String EXCHANGE_GOODS = URL_ROOT + "exchange";
        /**
         * (AU)获取商品详情
         */
        String GET_GOODS_DETAIL = URL_ROOT + "goodsDetail";

        /**
         * (AV)获取订单列表
         */

        String GET_ORDER_LIST = URL_ROOT + "orderList";

        /**
         * (AW)获取订单详情
         */
        String GET_ORDER_DETAIL = URL_ROOT + "orderDetail";
        /**
         * (BB)查询用户产品积分Log
         */
        String GET_SCORE_LOG = URL_ROOT + "getScoreLog";
        /**
         * (CI)用户中心签到
         */
        String SIGN = URL_ROOT + "sign";
        /**
         * (CJ)获取用户中心签到信息
         */
        String GET_SINGINFO = URL_ROOT + "getSignInfo";
        /**
         * (CS)验证验证码是否正确
         */
        String VERIFY_MSG_CODE = URL_ROOT + "checkVerify";
    }


}
