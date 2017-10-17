package com.alpha.alphaapp.model.v_1_2.logic.section;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.BannerBean;
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
 * Created by kenway on 17/10/11 15:34
 * Email : xiaokai090704@126.com
 */

public class BannerLogic {
    private static final String INFO = "info";
    /**
     * 获取今日热门banner数据
     *
     * @param callBack
     */
    public static void getTodayHotBanners(final OnModelCallback<List<BannerBean>> callBack) {
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    List<BannerBean> list = BannerBean.arrayBannerBeanFromData(obj.toString(), INFO);
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
                .addMethod(URLForum.FORUM_METHOD.GET_BANNER_URL)
                .addModuleid(10 + "")
                .getMap();
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);
    }
}
