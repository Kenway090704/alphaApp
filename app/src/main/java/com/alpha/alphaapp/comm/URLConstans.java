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
         * 注册用户
         */
        String REGISTER = URL_ROOT + "register";
        /**
         * 登录
         */
        String LOGIN = URL_ROOT + "login";
        /**
         * 退出
         */
        String LOGINOUT = URL_ROOT + "logout";
        /**
         * 修改用户信息
         */
        String MODIFY_ACCOUNTINFO = URL_ROOT + "edit";
        /**
         * 检测帐号是否存在
         */
        String CHECKACCOUT = URL_ROOT + "checkaccount";
        /**
         * 获取用户信息
         */
        String USETINFO = URL_ROOT + "info";

        /**
         * 获取手机验证码
         */
        String PHONEVERIFY = URL_ROOT + "phoneVerify";
        /**
         * 绑定激活码
         */
        String BINDCODE = URL_ROOT + "bindCode";
        /**
         * 渠道cdk检索
         */
        String CHANNELCHECK = URL_ROOT + "channelCheck";
        /**
         * 渠道提交用户领奖记录
         */
        String AWARDLOG = URL_ROOT + "awardLog";
        /**
         * 获取收货地址
         */
        String GETADDRESS = URL_ROOT + "getAddress";
        /**
         * 添加收货地址
         */
        String ADDADDRESS = URL_ROOT + "addAddress";
        /**
         * 编辑收货地址
         */
        String EDITADDRESS = URL_ROOT + "editAddress";
        /**
         * 删除收货地址
         */
        String DELADDRESS = URL_ROOT + "delAddress";
        /**
         * 设置默认收货地址
         */
        String DELADDEFAULTADDRESSDRESS = URL_ROOT + "defaultAddress";
        /**
         * 获取头像列表
         */
        String GETICONS = URL_ROOT + "getIcons";
        /**
         * 获取用户积分信息
         */
        String GETSCOREINFO = URL_ROOT + "getScoreInfo";
        /**
         * 获用户增加积分
         */
        String ADDSCORE = URL_ROOT + "addScore";
        /**
         * 用户扣除积分
         */
        String DELSCORE = URL_ROOT + "delScore";

        /**
         * 添加好友关系
         */
        String ADDFRIEND = URL_ROOT + "addFriend";

        /**
         * 解除好友关系
         */
        String DELFRIEND = URL_ROOT + "delFriend";

        /**
         * 查询好友关系
         */
        String GETFRIENDLIST = URL_ROOT + "getFriendList";

        /**
         * 通过密码修改密码
         */
        String CHANGEPWDBYPWD = URL_ROOT + "changepw";


        /**
         * 用户绑定手机号、账号、微信、QQ
         */
        String BIND_ACCOUNT = URL_ROOT + "bind";
        /**
         * L  手机号找回密码
         */
        String PHONE_FIND_PW = URL_ROOT + "phoneFindPw";
        /**
         * Z  忘记密码--通过手机验证修改密码
         */
        String PHONE_VERIFY_EDITPW = URL_ROOT + "phoneEditPw";
    }
}
