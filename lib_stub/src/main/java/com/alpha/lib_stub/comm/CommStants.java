package com.alpha.lib_stub.comm;

/**
 * Created by kenway on 17/5/23 15:15
 * Email : xiaokai090704@126.com
 */

public class CommStants {

    /**
     * (A)注册时返回的result类型
     */
    public interface REGISTER_RESULT {
        /**
         * 注册成功
         */
        int RESULT_REGISTER_OK = 0;
        /**
         * 帐号已经存在
         */
        int RESULT_ACCOUNT_HAD = 1;
        /**
         * 帐号格式错误
         */
        int RESULT_ACCOUNT_ERROR = 101;
        /**
         * 验证码错误或者为空
         */
        int RESULT_VERIFY_ERROR_OR_ENPTY = 102;
        /**
         * 手机号已经注册
         */
        int RESULT_PHONE_HAD = 106;
    }

    /**
     * (B)用户登录返回result
     */
    public interface LOGIN_RESULT {
        int RESULT_LOGIN_OK = 0;
        /**
         * 帐号不存在
         */
        int RESULT_ACCOUNT_NOHAD = 1;
        /**
         * 帐号或密码错误
         */
        int RESULT_ACCOUNT_OR_PW_ERROR = 2;
        /**
         * 错误次数太多
         */
        int RESULT_TOO_ERROR = 13;
        /**
         * 帐号错误
         */
        int RESULT_ACCOUNT_ERROR = 101;
        /**
         * 验证码错误
         */
        int RESULT_VERIFY_ERROR_OR_EMPTY = 102;
    }

    /**
     * (C)用户登出返回RESULT
     */
    public interface LOGINOUT_RESULT {
        /**
         * 退出成功
         */
        int RESULT_LOGINOUT_OK = 0;
    }

    /**
     * (D)一般信息修改返回结果
     */
    public interface ACCOUNT_MODIFY_RESULT {
        int RESULT_MODIFY_OK = 0;
        /**
         * 请重新登录
         */
        int RESULT_RELOGIN = 1;
        /**
         * 更新失败
         */
        int RESULT_MODIFY_FAILED = 2;
    }

    /**
     * (F)通过密码修改密码
     */
    public interface CHANGE_PWD_BY_PWD_RESULT {
        /**
         * 修改密码成功
         */
        int RESULT_OK = 0;

        /**
         * 请重新登录
         */
        int RESULT_RELOGIN = 1;

        /**
         * 修改密码失败
         */
        int RESULT_FAIL_TO_MODIFY = 2;
    }

    /**
     * (G)检查帐号是否存在
     */
    public interface CHECKOUT_ACCOUNT_RESULT {
        /**
         * 账号存在
         */
        int RESUTL_OK = 0;
        /**
         * 账号不存在
         */
        int RESULT_ACCOUNT_NOHAD = 1;
    }


    /**
     * (H)用户绑定手机号,帐号,微信,qq
     */
    public interface BIND_ACOUNT_RESULT {
        /**
         * 绑定成功
         */
        int RESULT_OK = 0;

        /**
         * 请重新登录
         */
        int RESULT_RELOGIN = 1;

        /**
         * 手机号已绑定
         */
        int RESULT_PHONE_HAD_BIND = 106;
        /**
         * 手机号输入错误或者为空:请输入 11 位有效的手机号
         */
        int RESULT_PHONE_IS_ERROR = 101;
        /**
         * 短信验证码错误或者为空:验证码输入错误
         */
        int RESULT_VERIFY_IS_ERROR = 101;
        /**
         * ”短信验证码获取超过次数:获取验证码过于频繁,请明天再试
         */
        int RESULT_GETVERIFY_TOO_MUCH = 102;
        /**
         * 账号已存在,请重新输入
         */
        int RESULT_ACCOUT_HAD = 114;
    }

    /**
     * (I)用户绑定或者修改account(这里是当使用手机+密码登录时绑定未注册的英文帐号)
     */
    public interface BIND_OR_EDIT_ACCOUNT_RESULT {
        /**
         * 绑定成功
         */
        int RESULT_OK = 0;
        /**
         * 请重新登录
         */
        int RESULT_RELOGIN = 1;
        /**
         * 帐号存在
         */
        int RESULT_ACCOUNT_HAD = 2;
    }

    /**
     * (J)	获取用户信息
     */
    public interface GET_USETINFO_RESULT {
        /**
         * ok
         */
        int RESULT_OK = 0;
        /**
         * 请重新登录
         */
        int RESULT_PLZ_RELOGIN = 1;
        /**
         * XX账号+用户不存在
         */
        int RESULT_THE_ACCOUNT_NO_HAD = 2;
        /**
         * 不存在的用户
         */
        int RESULT_ACCOUNT_NO_HAD = 3;
        /**
         * 数据错误
         */
        int RESULT_DATA_ERROR = 4;

    }

    /**
     * (K)获取手机验证码RESULT
     */
    public interface GET_PHONEVERIFY_RESULT {
        /**
         * 验证码已发送
         */
        int RESUTL_OK = 0;
        /**
         * 该手机号码已注册
         */
        int PHONE_HAD_REGISTER = 106;
        /**
         * 该手机号暂未注册
         */
        int PHONE_NO_REGISTER = 107;
        /**
         * 验证码已存在
         */
        int VERIFY_HAD = 108;
        /**
         * 手机号码错误
         */
        int PHOEN_ERROR = 109;
        /**
         * 超过每天获取短信条数
         */
        int TOO_MUCH_MESSAGE = 110;

    }

    /**
     * (L) 手机帐号找回密码
     */

    public interface BY_PHONE_FIND_PW {
        /**
         * 修改密码成功
         */
        int RESULT_OK = 0;
        /**
         * 验证码错误
         */
        int RESULT_VERIFY_ERROR = 1;
        /**
         * 手机号错误
         */
        int RESULT_PHONE_ERROR = 2;
        /**
         * 数据包错误
         */
        int RESULT_DATA_PACKAGE_ERROR = 3;
        /**
         * 密码格式错误
         */
        int RESULT_PW_FORM_ERROR = 4;
        /**
         * 验证码输入错误，请重新输入
         */
        int RESULT_VERIFY_INPUT_ERROR = 109;
        /**
         * 账号手机号输入错误：账号或手机号输入错误或未绑定，请重新输入
         */
        int RESULT_PHONE_INPUT_ERROR = 202;
    }

    /**
     * (M) 更换绑定手机号--验证旧手机号
     */

    public interface CHANGE_BIND_PHONE_VERIFY_OLD_PHONE {

        int RESULT_OK = 0;
        /**
         * 验证码错误
         */
        int RESULT_VERIFY_ERROR = 1;
        /**
         * 手机号错误
         */
        int RESULT_PHONE_ERROR = 2;
        /**
         * 数据包错误
         */
        int RESULT_DATA_PACKAGE_ERROR = 3;
    }

    /**
     * (N) 更换绑定手机号-绑定新手机号
     */
    public interface CHANGE_BIND_PHONE_BIND_NEW_PHONE {
        int RESULT_OK = 0;
        /**
         * 验证码错误
         */
        int RESULT_VERIFY_ERROR = 1;
        /**
         * 手机号错误
         */
        int RESULT_PHONE_ERROR = 2;
        /**
         * 数据包错误
         */
        int RESULT_DATA_PACKAGE_ERROR = 3;
    }

    /**
     * (O)绑定激活码
     */
    public interface BIND_ACTIVE_CODE_RESULT {
        /**
         * 绑定成功
         */
        int RESULT_OK = 0;
        /**
         * 绑定成功,有产品积分等数据
         */
        int RESULT_OK_HAS_SCORE = 0;
        /**
         * 激活失败
         */
        int ACTIVE_FAILED = 301;
        /**
         * 激活码错误
         */
        int ACTIVE_ERROR = 302;
        /**
         * 渠道错误
         */
        int CHANNER_ERROR = 303;
        /**
         * 激活码未生效
         */
        int ACTIVE_INVALID = 304;
        /**
         * 激活码已过期
         */
        int ACTIVIE_POST_DUE = 305;
        /**
         * 只能激活一个激活码
         */
        int ONLY_ACTIVE_ONE_CODE = 306;
        /**
         * 你已经激活过该激活码了
         */
        int YOU_ACTIVIE_THE_CODE = 307;
        /**
         * 该激活码已经被激活
         */
        int ACTIVIE_HAD_ACTIVIED = 308;
        /**
         * 超过激活码可激活次数
         */
        int MORE_THAN_CODE_TIME = 309;
        /**
         * 数据错误,激活失败1
         */
        int DATA_ERROR_1 = 310;
        /**
         * 数据错误,激活失败2
         */
        int DATA_ERROR_2 = 326;
        /**
         * 数据错误,激活失败3
         */
        int DATA_ERROR_3 = 327;
        /**
         * 错误次数太多,请60min后再试
         */
        int ERROR_TOO_MUCH = 328;
    }

    /**
     * (P)检测激活码是否正确
     */

    public interface CHECKOUT_ACTIVE_CODE_RESULT {
        /**
         * 激活码正确
         */
        int RESULT_OK = 0;
        /**
         * 错误次数太多,请60min后再试
         */
        int ERROR_TOO_MUCH = 328;

        /**
         * 激活码错误
         */
        int ACTIVE_CODE_ERROR = 329;

    }

    /**
     * (Q)渠道cdk检索
     */
    public interface CHANNEL_CDK_CHECKOUT_RESULT {
        /**
         *
         */
        int RESULT_OK = 0;

        /**
         * 输入参数错误
         */
        int PARAMS_ERROR = 1;
    }

    /**
     * (T)获取收货地址
     */
    public interface GET_SHIPPING_ADDR_RESULT {
        /**
         * 获取成功
         */
        int RESULT_OK = 0;
        /**
         * 请重新登录
         */
        int PLZ_RELOGIN = 1;
    }

    /**
     * (U)添加收货地址
     */
    public interface ADD_SHIPPING_ADDR_RESULT {
        /**
         * 添加成功
         */
        int RESULT_OK = 0;
        /**
         * 请重新登录
         */
        int PLZ_RELOGIN = 1;

    }

    /**
     * (V)编辑收货地址
     */
    public interface EDIT_SHIPPING_ADDR_RESULT {
        /**
         * 编辑成功
         */
        int RESULT_OK = 0;
        /**
         * 请重新登录
         */
        int PLZ_RELOGIN = 1;
    }

    /**
     * (W)删除收货地址
     */
    public interface DEL_SHIPPING_ADDR_RESULT {
        /**
         * 删除成功
         */
        int RESULT_OK = 0;
        /**
         * 请重新登录
         */
        int PLZ_RELOGIN = 1;
    }

    /**
     * (X)设置默认收货地址
     */
    public interface SET_DEFAULT_ADDR_RESULT {
        /**
         * 设置成功
         */
        int RESULT_OK = 0;
        /**
         * 请重新登录
         */
        int PLZ_RELOGIN = 1;
    }


    /**
     * (Y)获取头像列表
     */
    public interface GET_ICON_LIST_RESULT {
        /**
         * 获取列表成功
         */
        int RESULT_OK = 0;
        /**
         * 请重新登录
         */
        int RESULT_RELOGIN = 1;

    }

    /**
     * (Z) Find out the new password by the phone verify code
     */
    public interface FIND_PWD_BY_PHONE_RESULT {
        int RESULT_OK = 0;
        int RESULT_VERIFY_ERROR = 1;
        int PHONE_WRONG = 2;
        int DATA_PACKAGE_WRONG = 3;
        int PWD_FORMAT_WRONG = 4;
    }

    /**
     * (AA)获取用户积分信息
     */
    public interface GET_USER_SCORE_INFO_RESULT {
        /**
         * 获取成功
         */
        int RESULT_OK = 0;
        /**
         * 重新登录
         */
        int PLZ_RELOGIN = 1;
        /**
         * result==3,这里不知道是什么意思
         */
        int RESULT_3 = 3;
    }

    /**
     * (AF)重置密码
     */
    public interface RESET_PW_RESULT {
        /**
         * 重置密码成功
         */
        int RESULT_OK = 0;
        /**
         * 用户不存在
         */
        int RESULT_ACCOUNT_NOHAD = 1;
    }

    /**
     * (AS)兑换商品
     */
    public interface EXCHANGE_PRODUCT_RESULT {
        /**
         * 兑换成功
         */
        int RESULT_OK = 0;
        /**
         * 用户积分不足
         */
        int USER_NO_ENOUGH_SCORE = 334;
        /**
         * 兑换失败
         */
        int RESULT_FAILED = 337;
        /**
         * 库存不足
         */
        int STORGE_INSUFFICIENT = 339;
        /**
         * 超过最大兑换次数
         */
        int MORE_THAN_MAX_COUNT = 340;
        /**
         * 超过今天最大兑换次数
         */
        int MORE_THAN_TODAY_MAX_COUNT = 341;
    }

    /**
     * (AT)获取商品列表
     */
    public interface GET_GOODS_LIST_RESULT {
        /**
         * 获取列表成功
         */
        int RESULT_OK = 0;
    }

    /**
     * (AU)获取商品详情
     */
    public interface GET_GOODS_DETAIL_RESULT {
        /**
         * 获取商品详情成功
         */
        int RESULT_OK = 0;

        /**
         * 商品不成功
         */
        int RESULT_NO_HAD = 338;
    }

    /**
     * (AV)获取订单列表
     */
    public interface GET_ORDER_LIST_RESULT {
        int RESULT_OK = 0;
    }

    /**
     * (AW)获取订单列表
     */
    public interface GET_ORDER_DETAIL_RESULT {

        int RESULT_OK = 0;

    }

    /**
     * (BB)查询用户产品积分Log
     */
    public interface GET_SCORE_LOG_RESULT {
        /**
         * 查询成功
         */
        int RESULT_OK = 0;
        /**
         * 用户不存在
         */
        int USER_NO_HAD = 1;
    }




    /**
     * (Cs)验证验证码是否正确
     */
    public interface VERIFY_MSG_CODE_RESULT {
        /**
         * 验证码正确
         */
        int RESULT_OK = 0;
        /**
         * * 验证码错误
         */
        int RESULT_ERROR = 1;
    }

}
