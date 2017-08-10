package com.alpha.alphaapp.model.v_1_0.verifycode;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.SystemUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;

/**
 * Created by kenway on 17/8/9 17:52
 * Email : xiaokai090704@126.com
 * 验证验证码是否正确
 */

public class VerifyMsgCodeLogic {
    /**
     * 验证验证码是否正确
     * <p>
     * * <p>verifyType</p>
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
     * @param verifyType 获取验证码类型
     * @return
     */

    private static String getJsonStrVerifyMsgCode(String phone, String verify, int verifyType) {
        if (Util.isNullOrBlank(phone)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + verifyType + ",")
                .append("\"ts\":").append(SystemUtils.getCurrentTimeMillis() + ",")
                .append("\"phone_verify\":").append("\"" + verify + "\"")
                .append("}");
        return sb.toString();
    }

    public static void doVerigyMsgCode(String phone, String verifyCode, int verifyType, final OnModelCallback<Object> back) {
        String data = getJsonStrVerifyMsgCode(phone, verifyCode, verifyType);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                if (Util.isNull(info)) return;
                switch (info.getResult()) {
                    case CommStants.VERIFY_MSG_CODE_RESULT.RESULT_OK:
                        if (!Util.isNull(back))
                            back.onModelSuccessed(null);
                        break;
                    case CommStants.VERIFY_MSG_CODE_RESULT.RESULT_ERROR:
                        if (!Util.isNull(back))
                            back.onModelFailed("验证码错误");
                        //提示手机号码错误
                        break;
                    default:
                        if (!Util.isNull(back))
                            back.onModelFailed(info.getMsg());
                        break;
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                back.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.VERIFY_MSG_CODE, json, callBack);
    }

}
