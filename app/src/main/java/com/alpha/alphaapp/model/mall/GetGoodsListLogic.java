package com.alpha.alphaapp.model.mall;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.bean.GoodsBean;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/14 16:55
 * Email : xiaokai090704@126.com
 * 获取商品列表
 */

public class GetGoodsListLogic {

    private static final String TAG = "GetGoodsListLogic";

    /**
     * 获取商品列表
     *
     * @return
     */
    private static String getJsonforGetGoodsList() {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
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
    private static String getJsonforGetGoodsListPerson() {

        String uuid = AccountManager.getInstance().getUserInfo().getUuid();
        if (Util.isNullOrBlank(uuid)) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"uuid\":").append("\"" + uuid + "\",")
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
        sb
                .append("{\"remain_count\":").append(1 + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * <p>getGoodsListType</p>
     * <p>TypeConstants.GET_GOODS_LIST_TYPE.ALL</p>
     * <p>TypeConstants.GET_GOODS_LIST_TYPE.EXCHANGE_COUNT</p>
     * <p>TypeConstants.GET_GOODS_LIST_TYPE.REMIAN_COUNT</p>
     *
     * @param getGoodsListType
     */
    public static void doGetGoodsList(int getGoodsListType, OnModelCallback<List<GoodsBean>> callback) {
        String data = null;
        switch (getGoodsListType) {
            case TypeConstants.GET_GOODS_LIST_TYPE.ALL:
                data = getJsonforGetGoodsList();
                break;

            case TypeConstants.GET_GOODS_LIST_TYPE.EXCHANGE_COUNT:
                data = getJsonforGetGoodsListPerson();
                break;

            case TypeConstants.GET_GOODS_LIST_TYPE.REMIAN_COUNT:
                data = getJsonforGetGoodsReMainCount();
                break;

        }
        doLogic(data, callback);


    }

    private static void doLogic(String data, final OnModelCallback<List<GoodsBean>> callback) {
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                doRespone(result, callback);

            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callback))
                    callback.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_GOODS_LIST, json, call);

    }

    private static void doRespone(String result,  final OnModelCallback<List<GoodsBean>> callback) {
        //如果绑定成功,弹出对话框
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        if (Util.isNull(info)) {
            return;
        }

        if (info.getResult() == CommStants.GET_GOODS_LIST_RESULT.RESULT_OK) {
            List<GoodsBean> list = GoodsBean.arrayGoodsBeanFromData(result);
            if (!Util.isNull(callback))
                callback.onModelSuccessed(list);
            Log.e(TAG, "result===" + result);
        } else {
            if (!Util.isNull(callback))
                callback.onModelFailed("无法获取商品列表");
        }
    }
}
