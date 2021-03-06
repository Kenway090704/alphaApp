package com.alpha.alphaapp.model.v_1_0.login;


import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.fs.CfgFs;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.protocols.StorageConstants;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/5/23 16:59
 * Email : xiaokai090704@126.com
 */

public class LoginOutLogic {

    /**
     * 获取用户登出post json字符串
     *
     * @return
     */
    private static String getJSONStrforLoginOut(String sskey) {
        if (!Util.isNullOrBlank(sskey)) {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                    .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                    .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                    .append("}");
            return sb.toString();
        }
        return null;

    }

    /**
     * 退出当前帐号,进入登录页面
     *
     * @param sskey
     * @param back
     */
    public static void doLoginOut(String sskey, final OnModelCallback<Object> back) {
        String data = getJSONStrforLoginOut(sskey);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                //不论sskey是否过期都可以回到登录页面
                if (Util.isNull(info)) return;
                switch (info.getResult()) {
                    case CommStants.LOGINOUT_RESULT.RESULT_LOGINOUT_OK:
                    default:
                        CfgFs cfgFs = new CfgFs(StorageConstants.ACCOUNT_DATA_PATH);
                        cfgFs.reset();
                        if (!Util.isNull(back))
                            back.onModelSuccessed(null);
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                back.onModelFailed(errorMsg);
            }
        };


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.LOGINOUT, json, callBack);

    }


}
