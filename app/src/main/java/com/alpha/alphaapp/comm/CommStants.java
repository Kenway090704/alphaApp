package com.alpha.alphaapp.comm;

/**
 * Created by kenway on 17/5/23 15:15
 * Email : xiaokai090704@126.com
 */

public class CommStants {
    /**
     * 注册与登录类型
     */
    public interface ACCOUNT_TYPE {
        int ACCOUNT = 0;
        int PHONE = 1;
        int AUTH = 3;
        int AUTH_WECHAT = 4;
    }

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
    public interface ACCOUNTMODIFY_RESULT {
        int RESULT_MODIFY_OK = 0;
        /**
         * 请重新登录
         */
        int RESULT_AGAIN_LOGIN = 1;
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
        /**
         * 请输入一个您想检测的用户名
         */
        int RESULT_INPUT_ACCOUNT = 2;
        /**
         * 账号输入错误,须为字母开头,6-12 位字母或数字
         */
        int RESULT_ACCOUNT_ERROR = 101;
        /**
         * 手机号输入错误或者为空:请输入 11 位有效手机号
         */
        int RESULT_PHONE_ERROR = 101;
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
         * 手机号已绑定:手机已经被绑定,请重新输入
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
     * (Z) Find out the new password by the phone verify code
     */
    public interface FIND_PWD_BY_PHONE_RESULT {
        int RESULT_OK = 0;
        int USER_NOT_EXIST = 1;
        int PHONE_WRONG = 2;
        int DATA_PACKAGE_WRONG = 3;
        int PWD_FORMAT_WRONG = 4;
    }
}
