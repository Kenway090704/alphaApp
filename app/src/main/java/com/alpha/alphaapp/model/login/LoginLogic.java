package com.alpha.alphaapp.model.login;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.getuserinfo.GetUserInfoLogic;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/5/23 15:11
 * Email : xiaokai090704@126.com
 * 登录
 */

public class LoginLogic {
    /**
     * 最近登录类型
     * phone ,account ,auth
     */
    private int lastLoginType;
    /**
     * 登录后返回的sesskey
     * 可以保存24小时
     */
    private String sessKey;

    public String getSessKey() {
        return sessKey;
    }

    public void setSessKey(String sessKey) {
        this.sessKey = sessKey;
    }

    private String account;
    private String pw;
    private String user_ip;
    private String terminal_type;

    private String phone_verify;
    private String tuiguang_id;
    private String openid_qq;
    private String openid_wx;

    public String getOpenid_wx() {
        return openid_wx;
    }

    public void setOpenid_wx(String openid_wx) {
        this.openid_wx = openid_wx;
    }

    public int getLastLoginType() {
        return lastLoginType;
    }

    public void setLastLoginType(int lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }


    public String getPhone_verify() {
        return phone_verify;
    }

    public void setPhone_verify(String phone_verify) {
        this.phone_verify = phone_verify;
    }

    public String getTuiguang_id() {
        return tuiguang_id;
    }

    public void setTuiguang_id(String tuiguang_id) {
        this.tuiguang_id = tuiguang_id;
    }

    public String getOpenid_qq() {
        return openid_qq;
    }

    public void setOpenid_qq(String openid_qq) {
        this.openid_qq = openid_qq;
    }

    /**
     * 一般帐号登录
     * <p>
     * ex:{"account":"kenway","account_type":0,"pw":"123456","user_ip":"","terminal_type":""}
     * </p>
     *
     * @return
     */
    public static String getJsonStrforAccount(String account, String pw) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 手机号与密码登录
     * <p>
     * ex:{"account":"kenway","account_type":0,"pw":"123456","user_ip":"","terminal_type":""}
     * </p>
     *
     * @param account 用户名输入框架
     * @param pw      密码输入框架
     * @return
     */
    public static String getJsonStrforphoneInAccount(String account, String pw) {
        if (Util.isNullOrBlank(account) || Util.isNullOrBlank(pw)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 获取帐号注册的json字符串
     * <p>
     * ex:{“account":"tonywu","account_type":0,"pw":"8a017188e9a2803466f951f160a36c7a ",”user_ip”:"187.12.33.44","terminal_type":"PC",”tuiguang_id”:””}
     * </p>
     *
     * @return
     */
    public String getJsonStrforAccountHasTuiguangID() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + getAccount() + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(getPw()) + "\",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"tuiguang_id\":").append("\"" + getTuiguang_id() + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 手机账号登录
     * <p>
     * ex:{"account":"13883765487","account_type":1,"pw":"2b04edd88488d253d760ca03f4cd0f25","user_ip":"187.12.33.44","terminal_type":"PC","phone_verify":"123456"}
     * </p>
     *
     * @return
     */
    public static String getJsonStrforQuickLogin(String phone, String verify) {

        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + verify + "\"")
                .append("}");
        return sb.toString();
    }


    /**
     * 第三方登录  qq   account是openid
     * <p>
     * {“account":AFGHR9080,"account_type":3,user_ip:"187.12.33.44",terminal_type:"PC"}
     * <p/>
     *
     * @return
     */
    public static String getJsonStrforQQAuth(String openid) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + openid + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.AUTH + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 第三方登录 微信account是openid
     * account_type  为4
     * <p>
     * {“account":AFGHR9080,"account_type":4,user_ip:"187.12.33.44",terminal_type:"PC"}
     * <p/>
     *
     * @return
     */
    public static String getJsonStrforWXAuth(String openid) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + openid + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.AUTH_WECHAT + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 登录操作
     *
     * @param account_openid 帐号或者openid
     * @param pw_verify      密码或者验证码
     * @param loginType      登录类型
     * @param loginListener  登录监听
     */
    public static void doLogin(String account_openid, String pw_verify, int loginType, final OnLoginListener loginListener) {
        String data = null;
        switch (loginType) {
            case TypeConstants.LOGIN_TYPE.ACCONUT_PW:
                data = getJsonStrforAccount(account_openid, pw_verify);
                break;
            case TypeConstants.LOGIN_TYPE.PHONE_PW:
                data = getJsonStrforphoneInAccount(account_openid, pw_verify);
                break;
            case TypeConstants.LOGIN_TYPE.PHONE_QUICK:
                data = getJsonStrforQuickLogin(account_openid, pw_verify);
                break;
            case TypeConstants.LOGIN_TYPE.AUTH_QQ:
                data = getJsonStrforQQAuth(account_openid);
                break;
            case TypeConstants.LOGIN_TYPE.AUTH_WX:
                data = getJsonStrforWXAuth(account_openid);
                break;

        }
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                doDealLoginReqSuccess(result, loginListener);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);
    }

    /**
     * 让用户察觉的登录
     *
     * @param result
     */
    private static void doDealLoginReqSuccess(String result, final OnLoginListener listener) {
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        switch (info.getResult()) {
            case CommStants.LOGIN_RESULT.RESULT_LOGIN_OK:

                //将密码帐号与登录,是什么登录存入sharedPerferrence
                info = ResponseInfo.getRespInfoFromJsonStr(result, true);
                AccountManager.getInstance().setSskey(info.getSskey());
                GetUserInfoLogic.OnGetUserInfoCallBack callBack = new GetUserInfoLogic.OnGetUserInfoCallBack() {
                    @Override
                    public void onGetUserInfoSuccuss(UserInfo info) {
                        AccountManager.getInstance().saveUserInfo(info);
                        if (!Util.isNull(listener)) {
                            listener.onLoginSuccessed();
                        }
                    }

                    @Override
                    public void onGetUserInfoFailed(String failMsg) {

                    }
                };
                GetUserInfoLogic.doGetUserInfo(info.getSskey(), callBack);
                break;
            case CommStants.LOGIN_RESULT.RESULT_ACCOUNT_NOHAD:
                if (!Util.isNull(listener))
                    listener.onLoginFailed(info.getMsg());
                break;
            case CommStants.LOGIN_RESULT.RESULT_TOO_ERROR:
                if (!Util.isNull(listener))
                    listener.onLoginFailed(info.getMsg());
                break;
            case CommStants.LOGIN_RESULT.RESULT_ACCOUNT_ERROR:
                if (!Util.isNull(listener))
                    listener.onLoginFailed(info.getMsg());
                break;
            case CommStants.LOGIN_RESULT.RESULT_ACCOUNT_OR_PW_ERROR:
                if (!Util.isNull(listener))
                    listener.onLoginFailed(info.getMsg());
                break;
            case CommStants.LOGIN_RESULT.RESULT_VERIFY_ERROR_OR_EMPTY:
                if (!Util.isNull(listener))
                    listener.onLoginFailed(info.getMsg());
                break;

        }
    }

    @Override
    public String toString() {
        return "LoginLogic{" +
                "lastLoginType=" + lastLoginType +
                ", sessKey='" + sessKey + '\'' +
                ", account='" + account + '\'' +
                ", pw='" + pw + '\'' +
                ", user_ip='" + user_ip + '\'' +
                ", terminal_type='" + terminal_type + '\'' +
                ", phone_verify='" + phone_verify + '\'' +
                ", tuiguang_id='" + tuiguang_id + '\'' +
                ", openid_qq='" + openid_qq + '\'' +
                '}';
    }

    public interface OnLoginListener {
        void onLoginSuccessed();

        void onLoginFailed(String errorMsg);
    }
}
