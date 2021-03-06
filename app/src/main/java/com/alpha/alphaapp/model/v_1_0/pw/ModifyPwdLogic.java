package com.alpha.alphaapp.model.v_1_0.pw;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by hanshuming on 2017/6/1.
 */

public class ModifyPwdLogic {

    /**
     * 通过密码修改密码
     *
     * @param sskey
     * @param oldPwd
     * @param newPwd
     * @return
     */
    private static String getJsonModifyPwdByPwdInfo(String sskey, String oldPwd, String newPwd) {
        if (Util.isNullOrBlank(sskey) || Util.isNullOrBlank(oldPwd) || Util.isNullOrBlank(newPwd)) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                    .append("\"pw\":").append("\"" + MD5.getMD5FromStr(oldPwd) + "\",")
                    .append("\"newpw\":").append("\"" + MD5.getMD5FromStr(newPwd) + "\",")
                    .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                    .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                    .append("}");
            return sb.toString();
        }

    }

    /**
     * 通过手机验证码修改密码
     *
     * @param ssKey
     * @param phone
     * @param verifyCode
     * @param newPw
     * @return
     */
    private static String getJsonModifyPwdByPhone(String ssKey, String phone, String verifyCode, String newPw) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + ssKey + "\",")
                .append("\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append("\"" + TypeConstants.ACCOUNT_TYPE.PHONE + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + verifyCode + "\",")
                .append("\"newpw\":").append("\"" + MD5.getMD5FromStr(newPw) + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 通过密码修改密码
     *
     * @param sskey
     * @param oldPwd
     * @param newPwd
     * @param callback ModifyPwCallBack
     */
    public static void doModifyPwByPw(String sskey, String oldPwd, String newPwd, final OnModelCallback<Object> callback) {
        String data = getJsonModifyPwdByPwdInfo(sskey, oldPwd, newPwd);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result);
                if (Util.isNull(responseInfo)) return;
                int resultCode = responseInfo.getResult();
                switch (resultCode) {
                    case CommStants.CHANGE_PWD_BY_PWD_RESULT.RESULT_OK:
                        if (!Util.isNull(callback))
                        callback.onModelSuccessed(null);
                        break;
                    case CommStants.CHANGE_PWD_BY_PWD_RESULT.RESULT_RELOGIN:
                        if (!Util.isNull(callback))
                        callback.onModelFailed(responseInfo.getMsg());
                        break;
                    case CommStants.CHANGE_PWD_BY_PWD_RESULT.RESULT_FAIL_TO_MODIFY:
                        if (!Util.isNull(callback))
                        callback.onModelFailed(responseInfo.getMsg());
                        break;
                    default:
                        if (!Util.isNull(callback))
                        callback.onModelFailed(responseInfo.getMsg());
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                callback.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.CHANGE_PWD_BY_PWD, json, callBack);
    }

    /**
     * 通过手机验证修改密码
     * String ssKey, String phone, String verifyCode, String newPw
     *
     * @param sskey
     * @param phone
     * @param verifyCode
     * @param newPwd
     * @param callback
     */
    public static void doModifyPwByPhone(String sskey, String phone, String verifyCode, String newPwd, final OnModelCallback<Object> callback) {
        String data = getJsonModifyPwdByPhone(sskey, phone, verifyCode, newPwd);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result);
                if (Util.isNull(responseInfo)) return;
                int resultCode = responseInfo.getResult();
                switch (resultCode) {
                    case CommStants.CHANGE_PWD_BY_PWD_RESULT.RESULT_OK:
                        if (!Util.isNull(callback))
                        callback.onModelSuccessed(null);
                        break;
                    case CommStants.CHANGE_PWD_BY_PWD_RESULT.RESULT_RELOGIN:
                        if (!Util.isNull(callback))
                        callback.onModelFailed(responseInfo.getMsg());
                        break;
                    case CommStants.CHANGE_PWD_BY_PWD_RESULT.RESULT_FAIL_TO_MODIFY:
                        if (!Util.isNull(callback))
                        callback.onModelFailed(responseInfo.getMsg());
                        break;
                    default:
                        if (!Util.isNull(callback))
                        callback.onModelFailed(responseInfo.getMsg());
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                callback.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.MODIFY_PWD_BY_PHONE, json, callBack);
    }




}
