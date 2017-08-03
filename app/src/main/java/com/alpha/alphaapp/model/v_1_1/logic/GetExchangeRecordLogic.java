package com.alpha.alphaapp.model.v_1_1.logic;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.app.MyApplication;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.bean.OrderBean;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/21 16:50
 * Email : xiaokai090704@126.com
 * 获取兑换记录
 * (AV)获取订单列表
 */

public class GetExchangeRecordLogic {
    private static final String TAG = "GetExchangeRecordLogic";
    //==========================获取订单详情(兑换记录详情)===========================//

    /**
     * 兑换记录列表
     * (AV)获取订单列表
     *
     * @return
     */
    private static String getJsonforGetExchangeRecordList() {
        String sskey = AccountManager.getInstance().getSskey();
        UserInfo info = AccountManager.getInstance().getUserInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() + ",")
                .append("\"uuid\":").append("\"" + info.getUuid() + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 兑换记录列表
     * (AV)获取订单列表
     *
     * @return
     */
    private static String getJsonforGetExchangeRecordList(int product_id) {
        String sskey = AccountManager.getInstance().getSskey();
        UserInfo info = AccountManager.getInstance().getUserInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() + ",")
                .append("\"uuid\":").append("\"" + info.getUuid() + "\",")
                .append("\"product_id\":").append(product_id)
                .append("}");
        return sb.toString();
    }

    /**
     * 获取订单列表(兑换记录列表)
     *
     * @param product_id
     * @param callBack
     */
    public static void doGetScoreExchangeRecordList(final int product_id, final OnModelCallback<List<OrderBean>> callBack) {

        String data = null;

        switch (product_id) {
            case TypeConstants.PRODUCT_ID.NONE_PRODUCT:
                data = getJsonforGetExchangeRecordList();
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
                data = getJsonforGetExchangeRecordList(product_id);
                break;
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                data = getJsonforGetExchangeRecordList(product_id);
                break;
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                data = getJsonforGetExchangeRecordList(product_id);
                break;
        }


        String json = JsonEncryptUtil.getPostJsonSignString(data);

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_ORDER_LIST_RESULT.RESULT_OK:
                        List<OrderBean> beanList = OrderBean.arrayOrderBeanFromData(result, OrderBean.KEY_ORDER_LIST);
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(beanList);
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_ORDER_LIST, json, call);
    }

    //==========================获取订单详情(兑换记录详情)============================//

    /**
     * 兑换详情
     * (AV)获取订单列表
     *
     * @return
     */
    private static String getJsonforGetExchangeRecord(long order_id) {
        String sskey = AccountManager.getInstance().getSskey();
        UserInfo info = AccountManager.getInstance().getUserInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"order_id\":").append(+order_id + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() + ",")
                .append("\"uuid\":").append("\"" + info.getUuid() + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 兑换记录列表
     * (AV)获取订单列表
     *
     * @return
     */
    private static String getJsonforGetExchangeRecord(long order_id, int product_id) {
        String sskey = AccountManager.getInstance().getSskey();
        UserInfo info = AccountManager.getInstance().getUserInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"order_id\":").append(+order_id + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis() + ",")
                .append("\"uuid\":").append("\"" + info.getUuid() + "\",")
                .append("\"product_id\":").append(product_id)
                .append("}");
        return sb.toString();
    }

    /**
     * 获取订单详情(兑换记录详情)
     *
     * @param product_id
     * @param callBack
     */
    public static void doGetScoreExchangeRecord(long order_id, final int product_id, final OnModelCallback<OrderBean> callBack) {
        String data = null;
        if (MyApplication.isDebug) {
            //测试时,product_id为0
            data = getJsonforGetExchangeRecord(order_id);
        } else {
            switch (product_id) {
                case TypeConstants.PRODUCT_ID.NONE_PRODUCT:
                    data = getJsonforGetExchangeRecord(order_id);
                    break;
                case TypeConstants.PRODUCT_ID.SPEED:
                    data = getJsonforGetExchangeRecord(order_id, product_id);
                    break;
                case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                    data = getJsonforGetExchangeRecord(order_id, product_id);
                    break;
                case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                    data = getJsonforGetExchangeRecord(order_id, product_id);
                    break;
            }
        }

        String json = JsonEncryptUtil.getPostJsonSignString(data);

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_ORDER_DETAIL_RESULT.RESULT_OK:
                        OrderBean bean = OrderBean.objectFromData(result, OrderBean.KEY_ORDER_DETAIL);
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_ORDER_DETAIL, json, call);
    }
}
