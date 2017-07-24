package com.alpha.alphaapp.model.mall;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/14 16:57
 * Email : xiaokai090704@126.com
 * 兑换商品
 */

public class ExchangeGoodsLogic {
    /**
     * 兑换商品
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
     * 兑换商品(有使用推广员_id)
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
     * 兑换商品(备注信息)
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
     * 兑换商品(基本)
     *
     * @param goods_id
     * @param exchangeCount
     * @param addrId
     * @param callback
     */
    public static void doExchangeGoods(int goods_id, int exchangeCount, int addrId, OnModelCallback<Object> callback) {
        String data = getJsonforExchangeGoods(goods_id, exchangeCount, addrId);
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
