package com.alpha.alphaapp.model.mall;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.app.MyApplication;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.bean.CdkDatasBean;
import com.alpha.alphaapp.model.mall.bean.ScoreLogBean;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/21 15:40
 * Email : xiaokai090704@126.com
 * <p>
 * 积分变动记录
 * (BB)查询用户产品积分log
 */

public class ScoreChangeListLogic {

    private static final String TAG = "ScoreChangeListLogic";

    /**
     * 获取积分log,如果是微信登录的,第一个字段换为"openid"
     * {"account":"kenway",
     * "account_type":2,
     * "user_ip":"0.0.0.0",
     * "terminal_type":"PHONE",
     * "ts":1500625882,
     * "start_time":"2016-07-28 14:35:49",
     * "end_time":"2017-07-21 14:35:49"}
     *
     * @return
     */
    private static String getJsonforScoreChangeRecord() {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + "kenway" + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() / 1000 + ",")
                .append("\"start_time\":").append("\"" + "2016-07-28 14:35:49" + "\",")
                .append("\"end_time\":").append("\"" + "2017-07-21 14:35:49" + "\"")
                .append("}");
        return sb.toString();
    }


    /**
     * 获取积分log
     * * 其中product_id
     * <p>没有产品---TypeConstants.PRODUCT_ID.NONE_PRODUCT</p>
     * <p>零速争霸---TypeConstants.PRODUCT_ID.SPEED</p>
     * <p>爆裂飞车---TypeConstants.PRODUCT_ID.TRANSFROM_CAR</p>
     * <p>超级飞侠---TypeConstants.PRODUCT_ID.SUPER_WAVING</p>
     *
     * @return
     */
    private static String getJsonforScoreChangeRecord(int product_id) {
        String sskey = AccountManager.getInstance().getSskey();
        AccountManager.getInstance().getUserInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"account\":").append("\"" + "kenway" + "\",")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"product_id\":").append(product_id + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() / 1000 + ",")
                .append("\"start_time\":").append("\"" + "2016-07-28 14:35:49" + "\",")
                .append("\"end_time\":").append("\"" + "2017-07-21 14:35:49" + "\"")
                .append("}");
        return sb.toString();
    }


    /**
     * 查询用户产品积分log
     *
     * @param product_id
     * @param callBack
     */
    public static void doGetUserScoreChange(final int product_id, final OnModelCallback<List<ScoreLogBean>> callBack) {
        String data = null;
        if (MyApplication.isDebug) {
            //测试时,product_id为0
            data = getJsonforScoreChangeRecord();
        } else {
            switch (product_id) {
                case TypeConstants.PRODUCT_ID.NONE_PRODUCT:
                    data = getJsonforScoreChangeRecord();
                    break;
                case TypeConstants.PRODUCT_ID.SPEED:
                    data = getJsonforScoreChangeRecord(product_id);
                    break;
                case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                    data = getJsonforScoreChangeRecord(product_id);
                    break;
                case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                    data = getJsonforScoreChangeRecord(product_id);
                    break;
            }
        }

        String json = JsonEncryptUtil.getPostJsonSignString(data);

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_SCORE_LOG_RESULT.RESULT_OK:
                        List<ScoreLogBean> beanList = ScoreLogBean.arrayScoreLogBeanFromData(result, ScoreLogBean.KEY_SCORE_LOGS);
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(beanList);
                        break;
                    case CommStants.GET_SCORE_LOG_RESULT.USER_NO_HAD:
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_SCORE_LOG, json, call);
    }
}
