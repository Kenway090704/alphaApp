package com.alpha.alphaapp.model.check;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.result.ResponseInfo;
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

    private String account;
    private int account_type;

    /**
     * 获取检测帐号是否存在帐号检测的json字符串
     *
     * @return
     */
    private static String getJsonStrCheckAccout(String account, int accountType) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(accountType)
                .append("}");
        return sb.toString();
    }

    /**
     * 检测帐号是否存在
     * <p> accountType:</p>
     * <p>CommStants.ACCOUNT_TYPE.ACCOUNT</p>
     * <p>CommStants.ACCOUNT_TYPE.PHONE</p>
     * <p> CommStants.ACCOUNT_TYPE.AUTH</p>
     * <p>CommStants.ACCOUNT_TYPE.AUTH_WECHAT</p>
     * @param account     帐号
     * @param accountType 帐号类型
     * @return
     */
    public static void checkAccountIsHas(String account, int accountType, final OnCheckAccountListener listener) {
        String data = getJsonStrCheckAccout(account, accountType);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.CHECKOUT_ACCOUNT_RESULT.RESUTL_OK:
                        if (!Util.isNull(listener))
                            //帐号存在
                            listener.checkSucessed(true, info.getMsg());
                        break;
                    case CommStants.CHECKOUT_ACCOUNT_RESULT.RESULT_ACCOUNT_NOHAD:

                        if (!Util.isNull(listener))
                            listener.checkSucessed(false, info.getMsg());
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(listener))
                    listener.checkFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.CHECKACCOUT, json, callBack);
    }


    public interface OnCheckAccountListener {
        /**
         * @param isHas 帐号是否存在 true 已存在   false--不存在
         */
        void checkSucessed(boolean isHas, String result);

        void checkFailed(String errorMsg);
    }
}
