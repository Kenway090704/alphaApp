package com.alpha.alphaapp.model.v_1_1.logic;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.bean.CdkDatasBean;
import com.alpha.alphaapp.model.v_1_1.bean.UserScoreBean;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/20 11:51
 * Email : xiaokai090704@126.com
 * 绑定激活
 */

public class BindActiveLogic {
    private static final String TAG = "BindActiveLogic";
    private static final String KEY_SCORE = "product_score";

    /**
     * 绑定激活码
     *
     * @return
     */
    private static String getJsonforBindActiveCode(String active_code) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"active_code\":").append("\"" + active_code + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() / 1000)
                .append("}");
        return sb.toString();
    }

    /**
     * 绑定激活码,包含产品Product_ID
     * <p>没有产品---TypeConstants.PRODUCT_ID.NONE_PRODUCT</p>
     * <p>零速争霸---TypeConstants.PRODUCT_ID.SPEED</p>
     * <p>爆裂飞车---TypeConstants.PRODUCT_ID.TRANSFROM_CAR</p>
     * <p>超级飞侠---TypeConstants.PRODUCT_ID.SUPER_WAVING</p>
     *
     * @return
     */
    private static String getJsonforBindActiveCode(String active_code, int product_id) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"active_code\":").append("\"" + active_code + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() / 1000 + ",")
                .append("\"product_id\":").append(product_id)
                .append("}");
        return sb.toString();
    }

    /**
     * 检测激活码是否正确
     *
     * @return
     */
    private static String getJsonforCheckoutActiveCode(String active_code) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"active_code\":").append("\"" + active_code + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() / 1000)
                .append("}");
        return sb.toString();
    }


    /**
     * 渠道cdk检索,没有对应的product_id
     *
     * @return
     */
    private static String getJsonforActiveRecord() {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() / 1000)
                .append("}");
        return sb.toString();
    }


    /**
     * 渠道cdk检索,有对应的product_id
     * * 其中product_id
     * <p>没有产品---TypeConstants.PRODUCT_ID.NONE_PRODUCT</p>
     * <p>零速争霸---TypeConstants.PRODUCT_ID.SPEED</p>
     * <p>爆裂飞车---TypeConstants.PRODUCT_ID.TRANSFROM_CAR</p>
     * <p>超级飞侠---TypeConstants.PRODUCT_ID.SUPER_WAVING</p>
     *
     * @return
     */
    private static String getJsonforActiveRecord(int product_id) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() / 1000 + ",")
                .append("\"product_id\":").append(product_id)
                .append("}");
        return sb.toString();
    }

    /**
     * 绑定激活
     *
     * @param active_code
     * @param product_id
     * @param callBack
     */
    private static void doBindActive(String active_code, final int product_id, final OnModelCallback<UserScoreBean> callBack) {
        String data = null;
        switch (product_id) {
            case TypeConstants.PRODUCT_ID.NONE_PRODUCT:
                data = getJsonforBindActiveCode(active_code);
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
                data = getJsonforBindActiveCode(active_code, product_id);
                break;
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                data = getJsonforBindActiveCode(active_code, product_id);
                break;
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                data = getJsonforBindActiveCode(active_code, product_id);
                break;

        }
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.BIND_ACTIVE_CODE_RESULT.RESULT_OK:
                        //判断是否包含product_id,有的话调用获取对应的product_id积分
                        if (product_id == TypeConstants.PRODUCT_ID.NONE_PRODUCT) {
                            if (!Util.isNull(callBack))
                                callBack.onModelSuccessed(null);
                            break;
                        } else {
                            UserScoreBean bean = UserScoreBean.objectFromData(result, KEY_SCORE);
                            if (!Util.isNull(callBack))
                                callBack.onModelSuccessed(bean);
                            break;
                        }
                    case CommStants.BIND_ACTIVE_CODE_RESULT.ACTIVE_FAILED:
                        // 激活失败
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.ACTIVE_ERROR:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.CHANNER_ERROR:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.ACTIVE_INVALID:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.ACTIVIE_POST_DUE:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.ONLY_ACTIVE_ONE_CODE:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.YOU_ACTIVIE_THE_CODE:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.ACTIVIE_HAD_ACTIVIED:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.MORE_THAN_CODE_TIME:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.DATA_ERROR_1:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.DATA_ERROR_2:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.DATA_ERROR_3:
                        //激活码错误
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.BIND_ACTIVE_CODE_RESULT.ERROR_TOO_MUCH:
                        //激活码错误
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.BIND_ACTIVE_CODE, json, call);
    }

    /**
     * 绑定激活,先验证激活码是否正确,然后再进行激活
     * 其中product_id
     * <p>没有产品---TypeConstants.PRODUCT_ID.NONE_PRODUCT</p>
     * <p>零速争霸---TypeConstants.PRODUCT_ID.SPEED</p>
     * <p>爆裂飞车---TypeConstants.PRODUCT_ID.TRANSFROM_CAR</p>
     * <p>超级飞侠---TypeConstants.PRODUCT_ID.SUPER_WAVING</p>
     *
     * @param active_code
     * @param product_id
     * @param callBack
     */
    public static void doBindActiveCode(final String active_code, final int product_id, final OnModelCallback<UserScoreBean> callBack) {

        String data = getJsonforCheckoutActiveCode(active_code);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);

                switch (info.getResult()) {
                    case CommStants.CHECKOUT_ACTIVE_CODE_RESULT.RESULT_OK:
                        //绑定激活
                        doBindActive(active_code, product_id, callBack);
                        break;
                    case CommStants.CHECKOUT_ACTIVE_CODE_RESULT.ERROR_TOO_MUCH:
                        // 激活失败
                        if (!Util.isNull(callBack))
                            callBack.onModelFailed(info.getMsg());
                        break;
                    case CommStants.CHECKOUT_ACTIVE_CODE_RESULT.ACTIVE_CODE_ERROR:
                        // 激活码错误
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.CHECTOUT_ACTIVIT_CODE, json, call);


    }

    public static void doGetActiveRecord(final int product_id, final OnModelCallback<List<CdkDatasBean>> callBack) {
        String data = null;

        switch (product_id) {
            case TypeConstants.PRODUCT_ID.NONE_PRODUCT:
                data = getJsonforActiveRecord();
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
                data = getJsonforActiveRecord(product_id);
                break;
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                data = getJsonforActiveRecord(product_id);
                break;
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                data = getJsonforActiveRecord(product_id);
                break;
        }

        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.CHANNEL_CDK_CHECKOUT_RESULT.RESULT_OK:
                        List<CdkDatasBean> beanList = CdkDatasBean.arrayCdkDatasBeanFromData(result, CdkDatasBean.KEY_CDK_DATAS);
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(beanList);
                        break;
                    case CommStants.CHANNEL_CDK_CHECKOUT_RESULT.PARAMS_ERROR:
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.CHANNER_CHECKOUT, json, call);
    }
}
