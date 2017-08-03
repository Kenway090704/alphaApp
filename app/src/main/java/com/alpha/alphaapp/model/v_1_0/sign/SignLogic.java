package com.alpha.alphaapp.model.v_1_0.sign;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.SignInfo;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/5 14:12
 * Email : xiaokai090704@126.com
 */

public class SignLogic {

    private static final String TAG = "SignLogic";

    /**
     * 签到post数据
     * @param product_id
     * @return
     */
    private static String getJsonStrSign(String product_id) {
        UserInfo info = AccountManager.getInstance().getUserInfo();
        Log.e(TAG, "uuid==" + info.getUuid());
        if (Util.isNullOrBlank(info.getUuid()))
            return null;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"uuid\":").append("\"" + info.getUuid() + "\",")
                .append("\"product_id\":").append(product_id + ",")
                .append("\"p_img\":").append("\"" + "\",")
                .append("\"p_title\":").append("\"" + "\",")
                .append("\"sign_nums\":").append(1)
                .append("}");
        return sb.toString();
    }

    /**
     * 获取用户信息post数据
     *
     * @return
     */
    private static String getJsonStrForGetSignInfo() {
        UserInfo info = AccountManager.getInstance().getUserInfo();
        Log.e(TAG, "uuid==" + info.getUuid());
        if (Util.isNullOrBlank(info.getUuid()))
            return null;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"uuid\":").append("\"" + info.getUuid() + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 用户中心签到
     *
     * @param product_id
     */
    public static void doSign(String product_id, final OnSignCallBack call) {
        String data = getJsonStrSign(product_id);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                if (Util.isNull(info)) return;
                switch (info.getResult()) {
                    case CommStants.SIGN_RESULT.RESULT_OK:
                        if (!Util.isNull(call))
                            //帐号存在
                            call.onSignSuccessed();
                        break;
                    case CommStants.SIGN_RESULT.RESULT_PLZ_TOMORROW:
                        if (!Util.isNull(call))
                            call.onSignFailed(info.getMsg());
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(call))
                    call.onSignFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.SIGN, json, callBack);
    }


    /**
     * 获取签到信息
     */
    public static void doGetSignInfo(final OnGetSignInfoCallBack call) {
        String data = getJsonStrForGetSignInfo();
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                if (!Util.isNullOrBlank(result)) {
                    //将获取的数据转换为SignInfo
//                    SignInfo info = SignInfo.objectFromData(result);
//                    Log.e(TAG,"SignInfo.objectFromData(result)=="+info.toString());
                    SignInfo info=SignInfo.parseData(result);
                    if (!Util.isNull(call))
                        call.onGetSignInfoSuccessed(info);
                } else {
                    if (!Util.isNull(call))
                        call.onGetSignInfoFailed("无法获取签到信息");
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(call))
                    call.onGetSignInfoFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_SINGINFO, json, callBack);
    }

    /**
     * 签到接口
     */
    public interface OnSignCallBack {
        void onSignSuccessed();

        void onSignFailed(String failMsg);
    }

    /**
     * 获取签到信息接口
     */
    public interface OnGetSignInfoCallBack {
        void onGetSignInfoSuccessed(SignInfo info);

        void onGetSignInfoFailed(String failMsg);
    }
}
