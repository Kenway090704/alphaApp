package com.alpha.alphaapp.model.v_1_0.register;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/5/23 15:37
 * Email : xiaokai090704@126.com
 * 检测帐号是否存在
 */

public class CheckAccoutLogic {


    /**
     * 获取检测帐号是否存在帐号检测的json字符串
     *
     * @return
     */
    private static String getJsonStrCheckAccout(String account, int accountType) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(accountType)
                .append("}");
        return sb.toString();
    }

    /**
     * 检测帐号是否存在
     * <p> accountType:</p>
     * <p>TypeContansts.ACCOUNT_TYPE.ACCOUNT</p>
     * <p>TypeContansts.ACCOUNT_TYPE.PHONE</p>
     * <p> TypeContansts.ACCOUNT_TYPE.AUTH_QQ</p>
     * <p>TypeContansts.ACCOUNT_TYPE.AUTH_WECHAT</p>
     *
     * @param account     帐号
     * @param accountType 帐号类型
     * @return
     */
    public static void checkAccountIsHas(String account, int accountType, final OnModelCallback<Boolean> callback) {
        String data = getJsonStrCheckAccout(account, accountType);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                if (Util.isNull(info)) return;
                switch (info.getResult()) {
                    case CommStants.CHECKOUT_ACCOUNT_RESULT.RESUTL_OK:
                        if (!Util.isNull(callback))
                            //帐号存在
                            callback.onModelSuccessed(true);
                        break;
                    case CommStants.CHECKOUT_ACCOUNT_RESULT.RESULT_ACCOUNT_NOHAD:
                        if (!Util.isNull(callback))
                            callback.onModelSuccessed(false);
                        break;
                    default:
                        if (!Util.isNull(callback))
                            callback.onModelSuccessed(false);
                        break;

                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callback))
                    callback.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.CHECKACCOUT, json, callBack);
    }

}
