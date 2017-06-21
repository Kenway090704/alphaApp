package com.alpha.alphaapp.model.geticons;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.logout.LoginOutLogic;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/6/15 10:02
 * Email : xiaokai090704@126.com
 * 获取头像列表
 */

public class GetIconListLogic {

    private static final String TAG = "GetIconListLogic";

    private static String getJsonStrForIconList(String sskey) {
        if (!Util.isNullOrBlank(sskey)) {
            StringBuffer sb = new StringBuffer();
            sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                    .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                    .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                    .append("\"ts\":").append("\"" + System.currentTimeMillis() + "\"")
                    .append("}");
            return sb.toString();
        }
        return null;
    }

    /**
     * 获取头像列表
     *
     * @param sskey
     * @param back
     */
    public static void doGetIconList(String sskey, final GetIconCallBack back) {
        String data = getJsonStrForIconList(sskey);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_ICON_LIST_RESULT.RESULT_OK:

                        GetIconBean.IconListBean iconListBean = GetIconBean.IconListBean.objectFromData(result, "icon_list");
                        String base = iconListBean.getIcon_url();
                        List<GetIconBean.IconListBean.CategoryBean> list = iconListBean.getCategory();
                        if (back != null)
                            back.onGetIconListSuccuss(base, list);
                        break;
                    case CommStants.GET_ICON_LIST_RESULT.RESULT_RELOGIN:
                        if (back != null)
                            back.onGetIconListFailed(info.getMsg());
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (back != null)
                    back.onGetIconListFailed(errorMsg);
            }
        };


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_ICONS_LIST, json, callBack);

    }


    public interface GetIconCallBack {
        void onGetIconListSuccuss(String baseUrl, List<GetIconBean.IconListBean.CategoryBean> list);

        void onGetIconListFailed(String failMsg);
    }
}
