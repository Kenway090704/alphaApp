package com.alpha.alphaapp.model.phonefindpw;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/6 16:40
 * Email : xiaokai090704@126.com
 * 使用手机找回密码
 */

public class PhoneFindPwLogic {

    private static final String TAG = "PhoneFindPwLogic";

    /**
     * 用手机找回密码的字段
     *
     * @param phone
     * @param verify
     * @param newPw
     * @return
     */
    private static String getJsonStrforPhoneFindPW(String phone, String verify, String newPw) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + verify + "\",")
                .append("\"newpw\":").append("\"" + MD5.getMD5FromStr(newPw) + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 通过手机修改密码
     *
     * @param phone
     * @param pw
     * @param verify
     * @param call
     */
    public static void doByPhoneFindPw(String phone, String pw, String verify, final OnByPhoneFindPwCallBack call) {
        String data = PhoneFindPwLogic.getJsonStrforPhoneFindPW(phone, verify, pw);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                dealReqSuccess(result, call);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(call))
                    call.onFindPwFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.PHONE_FIND_PW, json, callBack);
    }

    /**
     * 处理返回的结果
     *
     * @param result
     */
    private static void dealReqSuccess(String result, OnByPhoneFindPwCallBack callBack) {
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        if (Util.isNull(info)) return;
        switch (info.getResult()) {
            case CommStants.BY_PHONE_FIND_PW.RESULT_OK:
                if (!Util.isNull(callBack))
                    callBack.onFindPwSuccessed();
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_DATA_PACKAGE_ERROR:
                if (!Util.isNull(callBack))
                    callBack.onFindPwFailed(info.getMsg());
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_PHONE_ERROR:
                if (!Util.isNull(callBack))
                    callBack.onFindPwFailed(info.getMsg());
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_PHONE_INPUT_ERROR:
                if (!Util.isNull(callBack))
                    callBack.onFindPwFailed(info.getMsg());
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_PW_FORM_ERROR:
                if (!Util.isNull(callBack))
                    callBack.onFindPwFailed(info.getMsg());
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_VERIFY_INPUT_ERROR:
                if (!Util.isNull(callBack))
                    callBack.onFindPwFailed(info.getMsg());
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_VERIFY_ERROR:
                if (!Util.isNull(callBack))
                    callBack.onFindPwFailed(info.getMsg());
                break;
            default:
                if (!Util.isNull(callBack))
                    callBack.onFindPwFailed(info.getMsg());
                break;
        }

    }

    public interface OnByPhoneFindPwCallBack {
        void onFindPwSuccessed();

        void onFindPwFailed(String failMsg);
    }

}
