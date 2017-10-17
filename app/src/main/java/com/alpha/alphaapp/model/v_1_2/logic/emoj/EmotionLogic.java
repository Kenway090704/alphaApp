package com.alpha.alphaapp.model.v_1_2.logic.emoj;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.EmotionBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.JsonUtil;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.comm.URLForum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.alpha.lib_stub.comm.URLForum.FORUM_BASE_POST_URL;

/**
 * Created by kenway on 17/10/9 14:39
 * Email : xiaokai090704@126.com
 * 获取表情
 */

public class EmotionLogic {

    private static final String INFO = "info";
    private static final String EMOTION = "emotion";

    /**
     * 获取所有表情
     *
     * @param callBack
     */
    public static void getAllEmotionList(final OnModelCallback<List<EmotionBean>> callBack) {


        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                LogUtils.e("getEmotion==" + result.toString()) ;
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray arr_info = obj.getJSONArray(INFO);
                    JSONObject obj_info  = (JSONObject) arr_info.get(0);
                    JSONArray array = obj_info.getJSONArray(EMOTION);
                    List<EmotionBean> list = JsonUtil.jsonToBeanArray(array.toString(), EmotionBean.class);
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
                .addMethod(URLForum.FORUM_METHOD.GET_EMOTION_URL)
                .getMap();


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }
}
