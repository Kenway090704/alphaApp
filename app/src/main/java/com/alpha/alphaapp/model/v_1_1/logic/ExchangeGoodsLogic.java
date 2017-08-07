package com.alpha.alphaapp.model.v_1_1.logic;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.SystemUtils;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/14 16:57
 * Email : xiaokai090704@126.com
 * 兑换商品(AS)
 */

public class ExchangeGoodsLogic {
    private static final String TAG="ExchangeGoodsLogic";
    /**
     * a)兑换商品
     *
     * @param goods_id      产品id
     * @param exchangeCount 兑换数量
     * @param addrId        选中地址id
     * @return
     */
    private static String getJsonforExchangeGoods(int goods_id, int exchangeCount, int addrId) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"goods_id\":").append(goods_id + ",")
                .append("\"exchange_count\":").append(exchangeCount + ",")
                .append("\"ts\":").append(System.currentTimeMillis() / 1000 + ",")
                .append("\"addr\":{")
                .append("\"id\":").append(addrId)
                .append("}")
                .append("}");
        return sb.toString();
    }

    /**
     * b)兑换商品(有使用推广员_id)
     *
     * @param goods_id      产品id
     * @param exchangeCount 兑换数量
     * @param addrId        选中地址id
     * @return
     */
    private static String getJsonforExchangeGoodsHasTuiguang_ID(int goods_id, int exchangeCount, int addrId, int tuiguangId) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"goods_id\":").append(goods_id + ",")
                .append("\"exchange_count\":").append(exchangeCount + ",")
                .append("\"ts\":").append(System.currentTimeMillis() + ",")
                .append("\"addr\":{")
                .append("\"id\":").append(addrId)
                .append("},")
                .append("\"tuiguang_id\":").append("\"" + tuiguangId + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * c)兑换商品(备注信息)
     *
     * @param goods_id      产品id
     * @param exchangeCount 兑换数量
     * @param addrId        选中地址id
     * @return
     */
    private static String getJsonforExchangeGoodsHasRemark(int goods_id, int exchangeCount, int addrId, String remark) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"goods_id\":").append(goods_id + ",")
                .append("\"exchange_count\":").append(exchangeCount + ",")
                .append("\"ts\":").append(System.currentTimeMillis() + ",")
                .append("\"addr\":{")
                .append("\"id\":").append(addrId)
                .append("},")
                .append("\"remark\":").append("\"" + remark + "\"")
                .append("}");
        return sb.toString();
    }


    /**
     * d)兑换商品(带有产品id)
     *
     * @param goods_id      产品id
     * @param exchangeCount 兑换数量
     * @param addrId        选中地址id
     * @return
     */
    private static String getJsonforExchangeGoodsHasProductID(int goods_id, int exchangeCount, int addrId, int product_id) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"goods_id\":").append(goods_id + ",")
                .append("\"exchange_count\":").append(exchangeCount + ",")
                .append("\"ts\":").append(SystemUtils.getCurrentTimeMillis() + ",")
                .append("\"addr\":{")
                .append("\"id\":").append(addrId)
                .append("},")
                .append("\"product_id\":").append(product_id)
                .append("}");
        return sb.toString();
    }

    /**
     * 兑换商品(基本)
     *
     * @param goods_id
     * @param exchangeCount
     * @param addrId
     * @param callback
     */
    public static void doExchangeGoods(int goods_id, int exchangeCount, int addrId, int product_id, OnModelCallback<Object> callback) {
        String data = null;
        LogUtils.e(TAG,"product-id=="+product_id);
            switch (product_id) {
                case TypeConstants.PRODUCT_ID.NONE_PRODUCT:
                    data = getJsonforExchangeGoods(goods_id, exchangeCount, addrId);
                    break;
                case TypeConstants.PRODUCT_ID.SPEED:

                case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:

                case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                    data = getJsonforExchangeGoodsHasProductID(goods_id, exchangeCount, addrId, product_id);
                    break;
            }


        doLogic(data, callback);

    }

    private static void doLogic(String data, final OnModelCallback<Object> callBack) {
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                doRespone(result, callBack);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.EXCHANGE_GOODS, json, call);

    }

    private static void doRespone(String result, final OnModelCallback<Object> callBack) {
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        if (Util.isNull(info)) {
            if (!Util.isNull(callBack))
                callBack.onModelFailed("无法获取数据");
            return;
        }
        switch (info.getResult()) {
            case CommStants.EXCHANGE_PRODUCT_RESULT.RESULT_OK:
                if (!Util.isNull(callBack))
                    callBack.onModelSuccessed(null);
                break;
            case CommStants.EXCHANGE_PRODUCT_RESULT.USER_NO_ENOUGH_SCORE:
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(info.getMsg());
                break;
            case CommStants.EXCHANGE_PRODUCT_RESULT.RESULT_FAILED:
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(info.getMsg());
                break;
            case CommStants.EXCHANGE_PRODUCT_RESULT.STORGE_INSUFFICIENT:
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(info.getMsg());
                break;
            case CommStants.EXCHANGE_PRODUCT_RESULT.MORE_THAN_MAX_COUNT:
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(info.getMsg());
                break;
            case CommStants.EXCHANGE_PRODUCT_RESULT.MORE_THAN_TODAY_MAX_COUNT:
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(info.getMsg());
                break;
            default:
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(info.getMsg());
                break;

        }
    }


}
