package com.alpha.alphaapp.model.v_1_0.userinfo;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.bean.GetIconBean;
import com.alpha.lib_stub.comm.CommStants;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.alphaapp.model.v_1_0.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
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
            StringBuilder sb = new StringBuilder();
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
    public static void doGetIconList(String sskey, final OnModelCallback<List<GetIconBean.IconListBean.CategoryBean>> back) {
        final String data = getJsonStrForIconList(sskey);
        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                if (Util.isNull(info)) return;
                switch (info.getResult()) {
                    case CommStants.GET_ICON_LIST_RESULT.RESULT_OK:

                        GetIconBean.IconListBean iconListBean = GetIconBean.IconListBean.objectFromData(result, GetIconBean.ICON_LIST);
                        if (Util.isNull(iconListBean)) return;

                        List<GetIconBean.IconListBean.CategoryBean> list = iconListBean.getCategory();
                        if (back != null)
                            back.onModelSuccessed(list);
                        break;
                    case CommStants.GET_ICON_LIST_RESULT.RESULT_RELOGIN:
                        if (back != null)
                            back.onModelFailed(info.getMsg());
                        break;
                    default:
                        if (back != null)
                            back.onModelFailed(info.getMsg());
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (back != null)
                    back.onModelFailed(errorMsg);
            }
        };

        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.GET_ICONS_LIST, json, callBack);

    }


}
