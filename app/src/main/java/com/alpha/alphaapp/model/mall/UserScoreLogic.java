package com.alpha.alphaapp.model.mall;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.bean.UserScoreBean;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/20 10:04
 * Email : xiaokai090704@126.com
 * 查询积分,add,del积分
 */

public class UserScoreLogic {

    private static final String TAG = "UserScoreLogic";
    private static final String KEY_SCORE = "product_score";

    /**
     * 获取用户积分信息
     *
     * @return
     */
    private static String getJsonforUserScoreInfo() {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    public static void doGetUserScoreInfo(final OnModelCallback<UserScoreBean> callBack) {
        String data = getJsonforUserScoreInfo();
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_USER_SCORE_INFO_RESULT.RESULT_OK:
                        UserScoreBean bean = UserScoreBean.objectFromData(result, KEY_SCORE);
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(bean);
                        break;
                    case CommStants.GET_USER_SCORE_INFO_RESULT.PLZ_RELOGIN:
                        //重新登录
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    default:
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_USER_SCORE_INFO, json, call);
    }


}
