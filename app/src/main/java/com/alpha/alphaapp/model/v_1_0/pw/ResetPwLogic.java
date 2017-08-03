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
 * Created by kenway on 17/6/9 18:12
 * Email : xiaokai090704@126.com
 * 重置密码,在找回密码中使用当微信有绑定英文帐号时使用
 */

public class ResetPwLogic {
    /**
     * 获取重置密码的字符串,微信绑定的是帐号
     *
     * @param account
     * @param pw
     * @return
     */
    private static String getJsonStrForAccount(String account, String pw) {
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
     * 获取重置密码的字符串,微信绑定的是手机
     *
     * @param phone
     * @param pw
     * @return
     */
    public static String getJsonStrForPhone(String phone, String pw) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    public static void doResetPw(String account, String pw, final OnModelCallback<Object> call) {
        String data = ResetPwLogic.getJsonStrForAccount(account, pw);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.RESET_PW_RESULT.RESULT_OK:
                        if (!Util.isNull(call))
                            call.onModelSuccessed(null);
                        break;
                    case CommStants.RESET_PW_RESULT.RESULT_ACCOUNT_NOHAD:
                        if (!Util.isNull(call))
                            call.onModelFailed(info.getMsg());
                        break;
                    default:
                        if (!Util.isNull(call))
                            call.onModelFailed(info.getMsg());
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(call))
                    call.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.RESET_PW, json, callBack);
    }



}
