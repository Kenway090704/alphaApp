package com.alpha.alphaapp.model.setenaccount;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonUtil;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/14 14:39
 * Email : xiaokai090704@126.com
 * (I)用户绑定或者修改account
 */

public class SetEnAccountLogic {

    /**
     * 用户使用手机号和密码登录时,添加一个未注册的英文帐号
     *
     * @param sskey
     * @param account
     * @return
     */
    private static String getJsonStrSetEnAccuontForHasPw(String sskey, String account) {
        if (Util.isNullOrBlank(account) && Util.isNullOrBlank(sskey)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append("\"" + TypeConstants.ACCOUNT_TYPE.ACCOUNT + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 使用微信授权登录,添加一个未注册的英文帐号
     *
     * @param sskey
     * @param account
     * @param pw
     * @return
     */
    private static String getJsonStrSetEnAccuontForNoPw(String sskey, String account, String pw) {
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
     * 当使用phone +pw 登录时,设置未注册的英文帐号
     *
     * @param sskey
     * @param phone
     * @param back  SetEnAccountLogic内部的CallBack
     */
    public static void doSetEnAccountForHasPw(String sskey, String phone, final SetEnAccountCallBack back) {

        String data = getJsonStrSetEnAccuontForHasPw(sskey, phone);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.BIND_OR_EDIT_ACCOUNT_RESULT.RESULT_OK:
                        // 进入第二个页面
                        back.onSetEnAccountSuccuss();
                        break;
                    case CommStants.BIND_OR_EDIT_ACCOUNT_RESULT.RESULT_RELOGIN:
                        back.onSetEnAccountFailed(info.getMsg());

                        //提示手机号码错误
                        break;
                    case CommStants.BIND_OR_EDIT_ACCOUNT_RESULT.RESULT_ACCOUNT_HAD:
                        back.onSetEnAccountFailed(info.getMsg());
                        //提示手机号码已经注册
                        break;
                    default:
                        back.onSetEnAccountFailed(info.getMsg());
                        break;
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                back.onSetEnAccountFailed(errorMsg);
            }
        };


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.BIND, json, callBack);
    }

    /**
     * 当使用第三方授权登录时,设置未注册的英文帐号
     *
     * @param sskey
     * @param phone
     * @param pw
     * @param back
     */
    public static void doSetEnAccountForNoPw(String sskey, String phone, String pw, final SetEnAccountCallBack back) {
        String data = getJsonStrSetEnAccuontForNoPw(sskey, phone, pw);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                if (Util.isNull(info))return;
                switch (info.getResult()) {
                    case CommStants.BIND_OR_EDIT_ACCOUNT_RESULT.RESULT_OK:
                        back.onSetEnAccountSuccuss();
                        break;
                    case CommStants.BIND_OR_EDIT_ACCOUNT_RESULT.RESULT_RELOGIN:
                        back.onSetEnAccountFailed(info.getMsg());
                        break;
                    case CommStants.BIND_OR_EDIT_ACCOUNT_RESULT.RESULT_ACCOUNT_HAD:
                        back.onSetEnAccountFailed(info.getMsg());
                        break;
                    default:
                        back.onSetEnAccountFailed(info.getMsg());
                        break;
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                back.onSetEnAccountFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.BIND, json, callBack);
    }


    public interface SetEnAccountCallBack {
        void onSetEnAccountSuccuss();
        void onSetEnAccountFailed(String failMsg);
    }

}
