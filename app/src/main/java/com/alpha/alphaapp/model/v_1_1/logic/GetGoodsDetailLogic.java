package com.alpha.alphaapp.model.v_1_1.logic;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.bean.GoodsBean;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/14 16:56
 * Email : xiaokai090704@126.com
 * 获取商品详情
 */

public class GetGoodsDetailLogic {

    private static final String TAG = "GetGoodsDetailLogic";

    /**
     * 获取商品详情,通过商品goods_id
     *
     * @return
     */
    private static String getJsonforGetGoodsDetail(int goods_id) {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"goods_id\":").append(goods_id+",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }
    /**
     * 获取商品列表(返回的数据包含个人已兑换数量)
     *
     * @return
     */
    private static String getJsonforGetGoodsDetailExChangeCount(int goods_id) {

        String uuid = AccountManager.getInstance().getUserInfo().getUuid();
        if (Util.isNullOrBlank(uuid)) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"uuid\":").append("\"" + uuid + "\",")
                .append("\"goods_id\":").append(goods_id + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 获取商品列表(剩余数量)
     *
     * @return
     */
    private static String getJsonforGetGoodsReMainCount() {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"remain_count\":").append(1 + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * <p>getGoodsListType</p>
     * <p>TypeConstants.GET_GOODS_DETAIL_RESULT.ALL</p>
     * <p>TypeConstants.GET_GOODS_DETAIL_RESULT.EXCHANGE_COUNT</p>
     * <p>TypeConstants.GET_GOODS_DETAIL_RESULT.REMIAN_COUNT</p>
     *
     * @param goods_id
     */
    public static void doGetGoodsDetail(int goods_id,int goodDetailType ,final OnModelCallback<GoodsBean> callback) {
        String data = null;
        switch (goodDetailType) {
            case TypeConstants.GET_GOODS_DETAIL_TYPE.ALL:
                data = getJsonforGetGoodsDetail(goods_id);
                break;
            case TypeConstants.GET_GOODS_DETAIL_TYPE.EXCHANGE_COUNT:
                data = getJsonforGetGoodsDetailExChangeCount(goods_id);
                break;
            case TypeConstants.GET_GOODS_DETAIL_TYPE.REMIAN_COUNT:
                data = getJsonforGetGoodsReMainCount();
                break;

        }
        doLogic(data,  callback);


    }

    private static void doLogic(String data, final OnModelCallback<GoodsBean> callback) {
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                doRespone(result,  callback);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callback))
                    callback.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_GOODS_DETAIL, json, call);

    }

    private static void doRespone(String result, final OnModelCallback<GoodsBean> callback) {

        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        if (Util.isNull(info)) {
            return;
        }

        if (info.getResult() == CommStants.GET_GOODS_DETAIL_RESULT.RESULT_OK) {
           GoodsBean bean = GoodsBean.objectFromData(result);
            if (!Util.isNull(callback))
                callback.onModelSuccessed(bean);
            LogUtils.e(TAG, "result===" + result);
        } else {
            if (!Util.isNull(callback))
                callback.onModelFailed("无法获取商品详细信息");
        }


    }

}
