package com.alpha.alphaapp.model.other;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/5/25 15:00
 * Email : xiaokai090704@126.com
 * 获取手机验证码
 */

public class GetPhoneVerifyLogic {
    /**
     * 获取手机验证码时
     *
     * @param phone
     * @param verifyType 获取验证码类型
     * @return
     */

    private static String getJsonStrPhoneVerify(String phone, int verifyType) {
        if (Util.isNullOrBlank(phone)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + verifyType + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 获取验证码
     * <p>verifyType</p>
     * <p>
     * <p>TypeConstants.GET_VERIFY_TYPE.LOGIN</p>
     * <p>TypeConstants.GET_VERIFY_TYPE.REGISTER</p>
     * <p>TypeConstants.GET_VERIFY_TYPE.GET_PW</p>
     * <p>TypeConstants.GET_VERIFY_TYPE.VERIFY_OLD_PHONE</p>
     * <p>TypeConstants.GET_VERIFY_TYPE.BIND_NEW_PHONE</p>
     * <p>TypeConstants.GET_VERIFY_TYPE.BIND_PHONE</p>
     * <p>TypeConstants.GET_VERIFY_TYPE.MODIFY_PWD_BY_PHONE</p>
     *
     * @param phone
     * @param verifyType
     * @param back       GetPhoneVerify内部的CallBack
     */
    public static void doGetPhoneVerify(String phone, int verifyType, final OnGetVerifyCallBack back) {
        String data = GetPhoneVerifyLogic.getJsonStrPhoneVerify(phone, verifyType);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                if (Util.isNull(info)) return;
                switch (info.getResult()) {
                    case CommStants.GET_PHONEVERIFY_RESULT.RESUTL_OK:
                        if (!Util.isNull(back))
                            back.onGetVerifySuccess();
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.PHOEN_ERROR:
                        if (!Util.isNull(back))
                            back.onGetVerifyFailed(info.getMsg());
                        //提示手机号码错误
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.PHONE_HAD_REGISTER:
                        if (!Util.isNull(back))
                            back.onGetVerifyFailed(info.getMsg());
                        //提示手机号码已经注册
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.PHONE_NO_REGISTER:
                        if (!Util.isNull(back))
                            back.onGetVerifyFailed(info.getMsg());
                        //提示手机号码没有注册
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.VERIFY_HAD:
                        if (!Util.isNull(back))
                            back.onGetVerifySuccess();
//                        back.onGetVerifyFailed(info.getMsg());
                        //提示验证码已经存在,也就是验证码获取成功
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.TOO_MUCH_MESSAGE:
                        if (!Util.isNull(back))
                            back.onGetVerifyFailed(info.getMsg());
                        //提示获取验证码次数太多
                        break;
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                back.onGetVerifyFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.PHONEVERIFY, json, callBack);
    }

    public interface OnGetVerifyCallBack {
        void onGetVerifySuccess();

        void onGetVerifyFailed(String failMsg);
    }

}
