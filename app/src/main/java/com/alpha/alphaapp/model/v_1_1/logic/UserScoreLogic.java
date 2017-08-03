package com.alpha.alphaapp.model.v_1_1.logic;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.lib_sdk.app.tool.SystemUtils;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.bean.UserScoreBean;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/20 10:04
 * Email : xiaokai090704@126.com
 * 查询积分
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

    /**
     * 获取用户积分信息
     * <p>product_id</p>
     * <p> TypeConstants.PRODUCT_ID.TRANSFROM_CAR</p>
     * <p>  TypeConstants.PRODUCT_ID.SPEED</p>
     * <p> TypeConstants.PRODUCT_ID.SUPER_WAVING</p>
     *
     * @param product_id
     * @return
     */
    private static String getJsonforUserScoreInfo(int product_id) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(SystemUtils.getCurrentTimeMillis() + ",")
                .append("\"product_id\":").append(product_id)
                .append("}");
        return sb.toString();
    }

    /**
     * <p>product_id</p>
     * <p>
     * <p> 没有产品id---TypeConstants.PRODUCT_ID.NONE_PRODUCT</p>
     * <p> 爆裂飞车---TypeConstants.PRODUCT_ID.TRANSFROM_CAR</p>
     * <p> 零速争霸---TypeConstants.PRODUCT_ID.SPEED</p>
     * <p> 超级飞侠---TypeConstants.PRODUCT_ID.SUPER_WAVING</p>
     *
     * @param product_id
     * @param callBack
     */
    public static void doGetUserScoreInfo(int product_id, final OnModelCallback<UserScoreBean> callBack) {
        String data = null;
        switch (product_id) {
            //没有产品id时
            case TypeConstants.PRODUCT_ID.NONE_PRODUCT:
                data = getJsonforUserScoreInfo();
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                data = getJsonforUserScoreInfo(product_id);
                break;

        }
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {

            private UserScoreBean bean;

            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_USER_SCORE_INFO_RESULT.RESULT_OK:
                        bean = UserScoreBean.objectFromData(result, KEY_SCORE);
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(bean);
                        break;
                    case CommStants.GET_USER_SCORE_INFO_RESULT.PLZ_RELOGIN:
                        //重新登录
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.GET_USER_SCORE_INFO_RESULT.RESULT_3:
                        //现在不知道这里是什么意思
                        bean = UserScoreBean.objectFromData(result, KEY_SCORE);
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(bean);
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
