package com.alpha.alphaapp.model.changebindphone;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/14 11:50
 * Email : xiaokai090704@126.com
 * 更换绑定QQ
 */

public class ChangeBindQQInfo {
    private static final String TAG="ChangeBindPhoneLogic";
    /**
     * 获取验证旧手机的字符串
     *
     * @param sskey
     * @param phone
     * @param verifyCode
     * @return
     */
    public static String getJsonStrChangeBindPhone(String sskey, String phone, String verifyCode) {
        if (Util.isNullOrBlank(phone) && Util.isNullOrBlank(sskey)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append("\"" + CommStants.ACCOUNT_TYPE.PHONE + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + verifyCode + "\"")
                .append("}");
        return sb.toString();
    }
    /**
     * 验证旧手机
     *
     * @param sskey
     * @param phone
     * @param verifyCode
     * @param back       GetPhoneVerify内部的CallBack
     */
    public static void doVerifyOldPhone(String sskey, String phone, String verifyCode, final VerfifyCallBack back) {

        String data = getJsonStrChangeBindPhone(sskey, phone, verifyCode);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.CHANGE_BIND_PHONE_VERIFY_OLD_PHONE.RESULT_OK:
                        // 进入第二个页面
                        back.onVerifySuccess();
                        break;
                    case CommStants.CHANGE_BIND_PHONE_VERIFY_OLD_PHONE.RESULT_VERIFY_ERROR:
                        back.onVerifyFailed(info.getMsg());

                        //提示手机号码错误
                        break;
                    case CommStants.CHANGE_BIND_PHONE_VERIFY_OLD_PHONE.RESULT_PHONE_ERROR:
                        back.onVerifyFailed(info.getMsg());
                        //提示手机号码已经注册
                        break;
                    case CommStants.CHANGE_BIND_PHONE_VERIFY_OLD_PHONE.RESULT_DATA_PACKAGE_ERROR:
                        back.onVerifyFailed(info.getMsg());
                        //提示手机号码没有注册
                        break;
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                back.onVerifyFailed(errorMsg);
            }
        };


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.CHANGE_BING_PHONE_VERIFY_OLD_PHONE, json, callBack);
    }

    /**
     * 绑定新手机
     *
     * @param sskey
     * @param phone
     * @param verifyCode
     * @param back       GetPhoneVerify内部的CallBack
     */
    public static void doBindNewPhone(String sskey, String phone, String verifyCode, final BindCallBack back) {

        String data = getJsonStrChangeBindPhone(sskey, phone, verifyCode);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.CHANGE_BIND_PHONE_BIND_NEW_PHONE.RESULT_OK:
                        back.onBindSuccess();
                        break;
                    case CommStants.CHANGE_BIND_PHONE_BIND_NEW_PHONE.RESULT_PHONE_ERROR:
                        back.onBindFailed(info.getMsg());
                        break;
                    case CommStants.CHANGE_BIND_PHONE_BIND_NEW_PHONE.RESULT_VERIFY_ERROR:
                        back.onBindFailed(info.getMsg());
                        break;
                    case CommStants.CHANGE_BIND_PHONE_BIND_NEW_PHONE.RESULT_DATA_PACKAGE_ERROR:
                        back.onBindFailed(info.getMsg());
                        break;
                }
            }
            @Override
            public void onReqFailed(String errorMsg) {
                back.onBindFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.CHANGE_BING_PHONE_BIND_NEW_PHONE, json, callBack);
    }

    /**
     * 验证旧手机的接口回调
     */
    public interface VerfifyCallBack {
        void onVerifySuccess();
        void onVerifyFailed(String failMsg);
    }

    /**
     * 绑定新手机的接口回调
     */
    public interface BindCallBack {
        void onBindSuccess();
        void onBindFailed(String failMsg);
    }
}
