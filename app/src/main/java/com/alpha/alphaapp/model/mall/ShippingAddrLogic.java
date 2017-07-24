package com.alpha.alphaapp.model.mall;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.bean.ShippingAddrBean;
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
 * Created by kenway on 17/7/14 18:17
 * Email : xiaokai090704@126.com
 * 收获地址管理
 */

public class ShippingAddrLogic {


    private static final String TAG = "ShippingAddrLogic";

    // =============================获取收货地址==============================//

    /**
     * 获取收货地址,最多有五个
     *
     * @return
     */
    private static String getJsonforGetAddress() {
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
     * 获取收获地址
     */
    public static void doGetAddress(final OnModelCallback<List<ShippingAddrBean>> callBack) {
        String data = getJsonforGetAddress();
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_SHIPPING_ADDR_RESULT.RESULT_OK:
                        List<ShippingAddrBean> list = ShippingAddrBean.arrayShippingBeanFromData(result, ShippingAddrBean.KEY_ADDR_LIST);
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(list);

                        break;
                    case CommStants.GET_SHIPPING_ADDR_RESULT.PLZ_RELOGIN:
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_SHIPPING_ADDR, json, call);
    }


    // =============================添加收货地址==============================//

    /**
     * 添加收货地址,最多添加5个
     * {“sskey”:”a8cdedfe4c4e8ad08ade919ef1be1957”,“user_ip:”187.12.33.44”,“terminal_type”:”PHONE” ,
     * "addr":{"id":0,"province":"guangdong","city":"guangzhou","area":"tianhe","addr_detail":"xxx","post_code":"51000","name":"kim","mobile":"13580452690","default_addr":1} , "ts":1464338401}
     *
     * @return
     */
    private static String getJsonforAddAddress(ShippingAddrBean bean) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"addr\":")
                .append("{")
                .append("\"id\":").append(bean.getId() + ",")
                .append("\"province\":").append("\"" + bean.getProvince() + "\",")
                .append("\"city\":").append("\"" + bean.getCity() + "\",")
                .append("\"area\":").append("\"" + bean.getArea() + "\",")
                .append("\"addr_detail\":").append("\"" + bean.getAddr_detail() + "\",")
                .append("\"post_code\":").append("\"" + bean.getPost_code() + "\",")
                .append("\"name\":").append("\"" + bean.getName() + "\",")
                .append("\"mobile\":").append("\"" + bean.getMobile() + "\",")
                .append("\"default_addr\":").append(bean.getDefault_addr())
                .append("},")
//                .append(JsonUtil.beanToJson(bean))
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 添加收货地址
     */
    public static void doAddAddress(ShippingAddrBean bean, final OnModelCallback<Object> callBack) {
        String data = getJsonforAddAddress(bean);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_SHIPPING_ADDR_RESULT.RESULT_OK:
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(null);
                        break;
                    case CommStants.GET_SHIPPING_ADDR_RESULT.PLZ_RELOGIN:
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.ADD_SHIPING_ADDR, json, call);
    }


    // =============================编辑收货地址==============================//

    /**
     * 编辑收货地址
     *
     * @return
     */
    private static String getJsonforEditAddress(ShippingAddrBean bean) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"addr\":")
                .append("{")
                .append("\"id\":").append(bean.getId() + ",")
                .append("\"province\":").append("\"" + bean.getProvince() + "\",")
                .append("\"city\":").append("\"" + bean.getCity() + "\",")
                .append("\"area\":").append("\"" + bean.getArea() + "\",")
                .append("\"addr_detail\":").append("\"" + bean.getAddr_detail() + "\",")
                .append("\"post_code\":").append("\"" + bean.getPost_code() + "\",")
                .append("\"name\":").append("\"" + bean.getName() + "\",")
                .append("\"mobile\":").append("\"" + bean.getMobile() + "\",")
                .append("\"default_addr\":").append(bean.getDefault_addr())
                .append("},")
//                .append(JsonUtil.beanToJson(bean))
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 编辑收货地址
     */
    public static void doEditAddress(ShippingAddrBean bean, final OnModelCallback<Object> callBack) {
        String data = getJsonforEditAddress(bean);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_SHIPPING_ADDR_RESULT.RESULT_OK:
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(null);
                        break;
                    case CommStants.GET_SHIPPING_ADDR_RESULT.PLZ_RELOGIN:
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.EDIT_SHIPPING_ADDR, json, call);
    }


    // =============================删除收货地址==============================//

    /**
     * 删除收货地址
     *
     * @return
     */
    private static String getJsonforDelAddress(ShippingAddrBean bean) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"addr\":")
                .append("{")
                .append("\"id\":").append(bean.getId() + "},")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 删除收货地址
     */
    public static void doDelAddress(ShippingAddrBean bean, final OnModelCallback<Object> callBack) {
        String data = getJsonforDelAddress(bean);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_SHIPPING_ADDR_RESULT.RESULT_OK:
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(null);
                        break;
                    case CommStants.GET_SHIPPING_ADDR_RESULT.PLZ_RELOGIN:
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.DEL_SHIPPING_ADDR, json, call);
    }


    // =============================设置默认收货地址==============================//

    /**
     * 设置默认收货地址
     *
     * @return
     */
    private static String getJsonforSetDefaultAddress(ShippingAddrBean bean) {
        String sskey = AccountManager.getInstance().getSskey();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"addr\":")
                .append("{")
                .append("\"id\":").append(bean.getId() + "},")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 设置默认收货地址
     */
    public static void doSetDefaultAddress(ShippingAddrBean bean, final OnModelCallback<Object> callBack) {
        String data = getJsonforSetDefaultAddress(bean);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_SHIPPING_ADDR_RESULT.RESULT_OK:
                        if (!Util.isNull(callBack))
                            callBack.onModelSuccessed(null);
                        break;
                    case CommStants.GET_SHIPPING_ADDR_RESULT.PLZ_RELOGIN:
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
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.SET_DEFUALT_ADDR, json, call);
    }



    /**
     * 从获取的地址集合中获取默认地址
     *
     * @param list
     * @return
     */
    public static ShippingAddrBean getDefaultShippingAdd(List<ShippingAddrBean> list) {
        if (Util.isNull(list)) return null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDefault_addr() == 1) {
                return list.get(i);
            }
        }
        return null;
    }


}
