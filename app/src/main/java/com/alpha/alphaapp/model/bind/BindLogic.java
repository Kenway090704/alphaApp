package com.alpha.alphaapp.model.bind;

import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/5 17:24
 * Email : xiaokai090704@126.com
 * 该类是用于qq,weixin首次授权绑定帐号,和手机号
 */

public class BindLogic {


    /**
     * 该字符串是用户使用微信授权登录时,可添加一个未注册的英文帐号
     * 绑定帐号
     *
     * @param sskey
     * @param account
     * @param pw
     * @return
     */
    private static String getJsonforBindAccount(String sskey, String account, String pw) {
        if (Util.isNullOrBlank(sskey) && Util.isNullOrBlank(account) && Util.isNullOrBlank(pw))
            return null;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 绑定手机号提交的字符串
     *
     * @param sskey
     * @param phone
     * @param verifyCode
     * @return
     */
    public static String getJsonforBindPhone(String sskey, String phone, String verifyCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + verifyCode + "\"")
                .append("}");
        return sb.toString();
    }


    /**
     * 绑定微信和qq提交的字符串
     *
     * @param sskey
     * @param opneid
     * @param accountType
     * @return
     */
    private static String getJsonforBindAuth(String sskey, String opneid, int accountType) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"account\":").append("\"" + opneid + "\",")
                .append("\"account_type\":").append(accountType + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * <p>accountType</p>
     * <p>TypeConstants.ACCOUNT_TYPE.AUTH_WECHAT</p>
     * <p>TypeConstants.ACCOUNT_TYPE.AUTH_QQ</p>
     * 绑定微信和QQ
     */
    public static void doBindWxOrQQ(String sskey, String openid, int accountType, final OnBindCallBack callBack) {
        String data = getJsonforBindAuth(sskey, openid, accountType);
        doBind(callBack, data);
    }


    /**
     * 绑定手机和帐号
     * <p>
     * * <p>accountType</p>
     * <p> TypeConstants.ACCOUNT_TYPE.ACCOUNT</p>
     * <p> TypeConstants.ACCOUNT_TYPE.PHONE</p>
     */
    public static void doBindAccountOrPhone(String sskey, String account_phone, String pw_verify, int accountType, final OnBindCallBack callBack) {
        String data = null;
        switch (accountType) {
            case TypeConstants.ACCOUNT_TYPE.ACCOUNT:
                data = getJsonforBindAccount(sskey, account_phone, pw_verify);
                break;
            case TypeConstants.ACCOUNT_TYPE.PHONE:
                data = getJsonforBindPhone(sskey, account_phone, pw_verify);
                break;
        }
        doBind(callBack, data);
    }

    /**
     * 绑定
     *
     * @param callBack
     * @param data
     */
    private static void doBind(final OnBindCallBack callBack, String data) {
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                doRespone(result, callBack);

            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callBack))
                    callBack.onBindFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.BIND, json, call);
    }

    private static void doRespone(String result, OnBindCallBack call) {

        //如果绑定成功,弹出对话框
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        if (Util.isNull(info)){
            return;
        }
        switch (info.getResult()) {
            case CommStants.BIND_ACOUNT_RESULT.RESULT_OK:
                // 刷新用户信息
                AccountManager.getInstance().loadUserinfo();
                if (!Util.isNull(call))
                    call.onBindSuccessed();
                break;
            case CommStants.BIND_ACOUNT_RESULT.RESULT_ACCOUT_HAD:
                if (!Util.isNull(call))
                    call.onBindFailed(info.getMsg());
                break;
            case CommStants.BIND_ACOUNT_RESULT.RESULT_RELOGIN:
                if (!Util.isNull(call))
                    call.onBindFailed(info.getMsg());
                //重新登录
                break;
            case CommStants.BIND_ACOUNT_RESULT.RESULT_GETVERIFY_TOO_MUCH:
//                        手机验证码手机号错误

                if (!Util.isNull(call))
                    call.onBindFailed(info.getMsg());
                break;
//                    case  CommStants.BIND_ACOUNT_RESULT.RESULT_PHONE_IS_ERROR:
//                        break;

            case CommStants.BIND_ACOUNT_RESULT.RESULT_VERIFY_IS_ERROR:
                if (!Util.isNull(call))
                    call.onBindFailed(info.getMsg());
                break;
            case CommStants.BIND_ACOUNT_RESULT.RESULT_PHONE_HAD_BIND:
                if (!Util.isNull(call))
                    call.onBindFailed(info.getMsg());
                break;
        }

    }

    public interface OnBindCallBack {
        /**
         * 绑定成功后
         */
        void onBindSuccessed();

        /**
         * 绑定失败
         *
         * @param failMsg
         */
        void onBindFailed(String failMsg);
    }


}
