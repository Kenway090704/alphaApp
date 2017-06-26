package com.alpha.alphaapp.model.logout;


import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.getuserinfo.GetUserInfoLogic;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.login.LoginActivity;
import com.alpha.alphaapp.ui.set.SettingsActivity;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.fs.CfgFs;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.protocols.StorageConstants;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

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
    public static String getJSONStrforLoginOut(String sskey) {
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
    public static void doLoginOut(String sskey, final LoginOutCallBack back) {
        String data = getJSONStrforLoginOut(sskey);
        String json = JsonUtil.getPostJsonSignString(data);
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
                        back.onLoginOutSuccuss();
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                back.onLoginOutFailed(errorMsg);
            }
        };


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.LOGINOUT, json, callBack);

    }


    public interface LoginOutCallBack {
        void onLoginOutSuccuss();

        void onLoginOutFailed(String failMsg);
    }
}
