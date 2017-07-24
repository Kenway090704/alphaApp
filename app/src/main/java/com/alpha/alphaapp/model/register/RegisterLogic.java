package com.alpha.alphaapp.model.register;

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
 * Created by kenway on 17/5/22 15:13
 * Email : xiaokai090704@126.com
 * 帐号注册类,现在还没有但三分授权登录
 */

public class RegisterLogic {


    /**
     * 获取帐号注册的json字符串
     * <p>
     * ex:{"account":"kenway","account_type":0,"pw":"123456","user_ip":"","terminal_type":""}
     * </p>
     *
     * @return
     */
    private  static String getJsonStrforAccount(String account, String pw) {
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
     * 获取帐号注册的json字符串
     * <p>
     * ex:{“account":"tonywu","account_type":0,"pw":"8a017188e9a2803466f951f160a36c7a ",”user_ip”:"187.12.33.44","terminal_type":"PC",”tuiguang_id”:””}
     * </p>
     *
     * @return
     */
    public static String getJsonStrforAccountHasTuiguangID(String account, String pw, String tuiguang_id) {
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
     * 手机注册的json字符串
     * <p>
     * ex:{"account":"13883765487","account_type":1,"pw":"2b04edd88488d253d760ca03f4cd0f25","user_ip":"187.12.33.44","terminal_type":"PC","phone_verify":"123456"}
     * </p>
     *
     * @return
     */
    private static String getJsonStrforPhone(String phone, String pw, String verify) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + verify + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 第三方授权注册  qq_logo,微信,sina   account是openid
     * <p>
     * {“account":AFGHR9080,"account_type":3,user_ip:"187.12.33.44",terminal_type:"PC"}
     * <p/>
     *
     * @return
     */
    private static String getJsonStrforAuth(String openid, int accountType) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + openid + "\",")
                .append("\"account_type\":").append(accountType + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 帐号密码注册
     *
     * @param account
     * @param pw
     */
    public static void doRegisterAccountPW(String account, String pw, final OnRegisterCallBack back) {

        String data = RegisterLogic.getJsonStrforAccount(account, pw);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        final ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                doDealLoginReqSuccess(result, back);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(back))
                    back.onRegisterFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.REGISTER, json, callBack);
    }

    /**
     * 使用手机号密码和验证码注册
     *
     * @param phone
     * @param pw
     * @param verify
     * @param back
     */
    public static void doRegisterPhone(String phone, String pw, String verify, final OnRegisterCallBack back) {
        String data = RegisterLogic.getJsonStrforPhone(phone, pw, verify);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        final ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                doDealLoginReqSuccess(result, back);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(back))
                    back.onRegisterFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.REGISTER, json, callBack);
    }

    /**
     * 第三方授权注册
     * <p>accountType</p>
     * <p>TypeConstants.ACCOUNT_TYPE.AUTH_QQ</p>
     * <p>TypeConstants.ACCOUNT_TYPE.AUTH_WECHAT</p>
     *
     * @param openid
     * @param accountType
     * @param back
     */
    public static void doRegisterAuth(String openid, int accountType, final OnRegisterCallBack back) {

        String data = RegisterLogic.getJsonStrforAuth(openid, accountType);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        final ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                doDealLoginReqSuccess(result, back);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(back))
                    back.onRegisterFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.REGISTER, json, callBack);
    }

    /**
     * 让用户察觉的登录
     *
     * @param result
     */
    private static void doDealLoginReqSuccess(String result, final OnRegisterCallBack callBack) {
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        if (Util.isNull(info))return;
        switch (info.getResult()) {
            case CommStants.REGISTER_RESULT.RESULT_REGISTER_OK:
                if (!Util.isNull(callBack))
                    callBack.onRegisterSuccessed();
                break;
            case CommStants.REGISTER_RESULT.RESULT_ACCOUNT_ERROR:
                if (!Util.isNull(callBack))
                    callBack.onRegisterFailed(info.getMsg());
                break;
            case CommStants.REGISTER_RESULT.RESULT_ACCOUNT_HAD:
                if (!Util.isNull(callBack))
                    callBack.onRegisterFailed(info.getMsg());
                break;
            case CommStants.REGISTER_RESULT.RESULT_PHONE_HAD:
                if (!Util.isNull(callBack))
                    callBack.onRegisterFailed(info.getMsg());
                break;
            case CommStants.REGISTER_RESULT.RESULT_VERIFY_ERROR_OR_ENPTY:
                if (!Util.isNull(callBack))
                    callBack.onRegisterFailed(info.getMsg());
                break;
        }
    }

    public interface OnRegisterCallBack {
        void onRegisterSuccessed();

        void onRegisterFailed(String errorMsg);
    }

}
