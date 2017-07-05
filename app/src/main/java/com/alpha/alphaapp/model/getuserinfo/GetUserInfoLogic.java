package com.alpha.alphaapp.model.getuserinfo;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonUtil;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;

/**
 * Created by kenway on 17/6/8 17:42
 * Email : xiaokai090704@126.com
 * 获取用户信息
 */

public class GetUserInfoLogic {
    /**
     * 获取用户信息时提交的json数据
     *
     * @param sskey
     * @return
     */
    private static String getJsonStrForGetUserInfo(String sskey) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 获取用户信息
     *
     * @param sskey
     * @param back
     */
    public static void doGetUserInfo(String sskey, final OnGetUserInfoCallBack back) {
        String data = getJsonStrForGetUserInfo(sskey);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_USETINFO_RESULT.RESULT_OK:
                        UserInfo userinfo = UserInfo.objectFromData(result, UserInfo.UINFO);
                        AccountManager.getInstance().saveUserInfo(userinfo);
                        back.onGetUserInfoSuccuss(userinfo);
                        break;
                    case CommStants.GET_USETINFO_RESULT.RESULT_PLZ_RELOGIN:
                        back.onGetUserInfoFailed(info.getMsg());
                        break;
                    case CommStants.GET_USETINFO_RESULT.RESULT_THE_ACCOUNT_NO_HAD:
                        back.onGetUserInfoFailed(info.getMsg());
                        break;
                    case CommStants.GET_USETINFO_RESULT.RESULT_ACCOUNT_NO_HAD:
                        back.onGetUserInfoFailed(info.getMsg());
                        break;
                    case CommStants.GET_USETINFO_RESULT.RESULT_DATA_ERROR:
                        back.onGetUserInfoFailed(info.getMsg());
                        break;
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                back.onGetUserInfoFailed(errorMsg);
            }
        };


        RequestManager.getInstance(ApplicationContext.getApplication()).requestPostByJsonAsyn(URLConstans.URL.GET_USETINFO, json, callBack);

    }


    public interface OnGetUserInfoCallBack {
        void onGetUserInfoSuccuss(UserInfo info);

        void onGetUserInfoFailed(String failMsg);
    }


}
