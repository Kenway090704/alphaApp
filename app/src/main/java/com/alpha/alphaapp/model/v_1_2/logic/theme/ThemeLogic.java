package com.alpha.alphaapp.model.v_1_2.logic.theme;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.ThemeBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.comm.URLForum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.alpha.lib_stub.comm.URLForum.FORUM_BASE_POST_URL;

/**
 * Created by kenway on 17/10/12 18:35
 * Email : xiaokai090704@126.com
 */

public class ThemeLogic {
    private static final String INFO = "info";


    /**
     * 获取话题列表
     *
     * @param callBack
     */
    public static void getThemes(final OnModelCallback<List<ThemeBean>> callBack) {
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    List<ThemeBean> list = ThemeBean.arrayThemeBeanFromData(obj.toString(), INFO);
                    if (!Util.isNull(callBack))
                        callBack.onModelSuccessed(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callBack))
                    callBack.onModelFailed(errorMsg);
            }
        };

        //post数据
        HashMap<String, String> map = ForumNetPostUtil.initBaseMap()
                .addMethod(URLForum.FORUM_METHOD.GET_THEME_URL)
                .getMap();
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);
    }
}
