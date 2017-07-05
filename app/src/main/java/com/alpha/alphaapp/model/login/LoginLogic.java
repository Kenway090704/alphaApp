package com.alpha.alphaapp.model.login;

import android.app.Activity;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.lib_sdk.app.protocols.StorageConstants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonUtil;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.getuserinfo.GetUserInfoLogic;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.login.LoginActivity;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.core.thread.ThreadPool;
import com.alpha.lib_sdk.app.fs.CfgFs;
import com.alpha.lib_sdk.app.log.Log;
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

    private static final String TAG = "LoginLogic";

    /**
     * 最近登录类型
     * <p>lastLoginType</p>
     * <p>TypeConstants.LOGIN_TYPE.ACCONUT_PW</p>
     * <p>TypeConstants.LOGIN_TYPE.PHONE_PW</p>
     * <p>TypeConstants.LOGIN_TYPE.PHONE_QUICK</p>
     * <p>TypeConstants.LOGIN_TYPE.AUTH_WX</p>
     * <p>TypeConstants.LOGIN_TYPE.AUTH_QQ</p>
     */
    private static int lastLoginType = -1;

    public int getLastLoginType() {
        return lastLoginType;
    }

    /**
     * 一般帐号登录
     * <p>
     * ex:{"account":"kenway","account_type":0,"pw":"123456","user_ip":"","terminal_type":""}
     * </p>
     *
     * @return
     */
    private static String getJsonStrforAccount(String account, String pw) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 帐号密码自动登录
     * <p>
     * ex:{"account":"kenway","account_type":0,"pw":"123456","user_ip":"","terminal_type":""}
     * </p>
     * 该处的密码是MD5格式的
     *
     * @return
     */
    private static String getJsonStrforAutoAccount(String account, String pw) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"pw\":").append("\"" + pw + "\",")
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
    private static String getJsonStrforphoneInAccount(String account, String pw) {
        if (Util.isNullOrBlank(account) || Util.isNullOrBlank(pw)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 手机号与密码自动登录
     * <p>
     * ex:{"account":"kenway","account_type":0,"pw":"123456","user_ip":"","terminal_type":""}
     * </p>
     *
     * @param account 用户名输入框架
     * @param pw      密码输入框架
     * @return
     */
    private static String getJsonStrforAutophoneInAccount(String account, String pw) {
        if (Util.isNullOrBlank(account) || Util.isNullOrBlank(pw)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + pw + "\",")
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
    public String getJsonStrforAccountHasTuiguangID(String account, String pw, String tuiguang_id) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"tuiguang_id\":").append("\"" + tuiguang_id + "\"")
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
    private static String getJsonStrforQuickLogin(String phone, String verify) {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.PHONE + ",")
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
    private static String getJsonStrforQQAuth(String openid) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + openid + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
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
    private static String getJsonStrforWXAuth(String openid) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + openid + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_WECHAT + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 登录操作
     * <p>loginType</p>
     * <p>TypeConstants.LOGIN_TYPE.ACCONUT_PW</p>
     * <p>TypeConstants.LOGIN_TYPE.PHONE_PW</p>
     * <p>TypeConstants.LOGIN_TYPE.PHONE_QUICK</p>
     * <p>TypeConstants.LOGIN_TYPE.AUTH_WX</p>
     * <p>TypeConstants.LOGIN_TYPE.AUTH_QQ</p>
     *
     * @param account_openid 帐号或者openid
     * @param pw_verify      密码或者验证码
     * @param loginType      登录类型
     * @param loginListener  登录监听
     */
    public static void doLogin(final String account_openid, final String pw_verify, final int loginType, final OnLoginCallBack loginListener) {
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
                doDealLoginReqSuccess(result, loginType, account_openid, pw_verify, loginListener);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);
    }

    /**
     * 自动登录操作,当上一次的登录为帐号密码或者手机密码登录时,
     */
    private static void doAutoLogin(final String account, final String pw, final int loginType, final OnLoginCallBack loginListener) {
        String data = null;
        switch (loginType) {
            case TypeConstants.LOGIN_TYPE.ACCONUT_PW:
                data = getJsonStrforAutoAccount(account, pw);
                break;
            case TypeConstants.LOGIN_TYPE.PHONE_PW:
                data = getJsonStrforAutophoneInAccount(account, pw);
                break;
        }
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                doDealLoginReqSuccess(result, loginType, account, pw, loginListener);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(loginListener))
                    loginListener.onLoginFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);
    }

    /**
     * @param result
     * @param loginType
     * @param username
     * @param pw        这里的密码需要使用MD5是MD5加密后的字符串
     * @param listener
     */
    private static void doDealLoginReqSuccess(String result, int loginType, String username, String pw, final OnLoginCallBack listener) {
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        switch (info.getResult()) {
            case CommStants.LOGIN_RESULT.RESULT_LOGIN_OK:
                final ResponseInfo info2 = ResponseInfo.getRespInfoFromJsonStr(result, true);
                //将密码帐号与登录,是什么登录存入sharedPerferrence
                CfgFs cfgFs = new CfgFs(StorageConstants.ACCOUNT_DATA_PATH);
                if (loginType == TypeConstants.LOGIN_TYPE.PHONE_PW || loginType == TypeConstants.LOGIN_TYPE.ACCONUT_PW) {
                    if (StringUtils.isPWLine(pw)) {
                        String md5Pw = MD5.getMD5FromStr(pw);
                        cfgFs.setString(StorageConstants.Info_Key.USER_NAME, username);
                        cfgFs.setString(StorageConstants.Info_Key.MD5_PASSWORD, MD5.getMD5FromStr(pw));
                        cfgFs.setInt(StorageConstants.Info_Key.LOGIN_TYPE, loginType);
                    }
                } else {
                    cfgFs.reset();
                }
                //获取用户信息
                AccountManager.getInstance().setSskey(info2.getSskey());
                AccountManager.getInstance().setLoginType(loginType);
                GetUserInfoLogic.OnGetUserInfoCallBack callBack = new GetUserInfoLogic.OnGetUserInfoCallBack() {
                    @Override
                    public void onGetUserInfoSuccuss(UserInfo info) {
                        AccountManager.getInstance().saveUserInfo(info);
                        if (!Util.isNull(listener))
                            listener.onLoginSuccessed(info2.getSskey());
                    }

                    @Override
                    public void onGetUserInfoFailed(String failMsg) {

                    }
                };
                GetUserInfoLogic.doGetUserInfo(info2.getSskey(), callBack);
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
                //帐号或者密码错误
                if (!Util.isNull(listener))
//                    listener.onLoginFailed(info.getMsg());
                listener.onLoginFailed("帐号或者密码错误,请重新输入。");
                break;
            case CommStants.LOGIN_RESULT.RESULT_VERIFY_ERROR_OR_EMPTY:
                if (!Util.isNull(listener))
                    listener.onLoginFailed(info.getMsg());
                break;

        }
    }


    public interface OnLoginCallBack {
        void onLoginSuccessed(String sskey);

        void onLoginFailed(String errorMsg);
    }

    /**
     * 判断是否可以自动登录
     * 上一次使用帐号和密码(手机密码)登录后就可以使用自动登录
     *
     * @return
     */
    public static boolean isAutoLogin(final Activity activity) {
        //从acc.data文件中获取帐号和密码
        CfgFs cfgFs = new CfgFs(StorageConstants.ACCOUNT_DATA_PATH);
        String username = cfgFs.getString(StorageConstants.Info_Key.USER_NAME, null);
        String password = cfgFs.getString(StorageConstants.Info_Key.MD5_PASSWORD, null);
        int loginType = cfgFs.getInt(StorageConstants.Info_Key.LOGIN_TYPE, -1);
        Log.e(TAG, "userName==" + username + ",password==" + password + ",loginType==" + loginType);
        if (username == null || password == null || loginType == -1) {
            Log.e(TAG, "username or password is null");
            return false;
        }
        if (loginType == TypeConstants.LOGIN_TYPE.AUTH_QQ || loginType == TypeConstants.LOGIN_TYPE.AUTH_WX || loginType == TypeConstants.LOGIN_TYPE.PHONE_QUICK) {
            Log.e(TAG, "LoginType不是帐号密码和手机密码登录");
            return false;
        }
        OnLoginCallBack callBack = new OnLoginCallBack() {
            @Override
            public void onLoginSuccessed(String sskey) {
                //自动登录成功后进入到Home页面
                Log.e(TAG, "自动登录成功,进入HomeActivity");
                ThreadPool.post(new Runnable() {
                    @Override
                    public void run() {
                        HomeActivity.actionStart(activity, null, null);
                    }
                }, 3000);

            }

            @Override
            public void onLoginFailed(String errorMsg) {
                //如果登录失败进入登录页面
                Log.e(TAG, "自动登录失败,进入LoginActivity中");
                LoginActivity.actionStartClearStack(activity, null, null);
            }
        };
        doAutoLogin(username, password, loginType, callBack);
        return true;
    }
}
