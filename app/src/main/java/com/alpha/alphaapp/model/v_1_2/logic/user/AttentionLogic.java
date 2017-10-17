package com.alpha.alphaapp.model.v_1_2.logic.user;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.UploadPicBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.alphaapp.model.v_1_2.logic.topic.TopicLogic;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.JsonUtil;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.comm.ForumStants;
import com.alpha.lib_stub.comm.URLForum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.alpha.lib_stub.comm.URLForum.FORUM_BASE_POST_URL;

/**
 * Created by kenway on 17/10/16 12:09
 * Email : xiaokai090704@126.com
 * 用户与用户之间的关注
 */

public class AttentionLogic {
    private static final String CODE="code";


    /**
     * 当前用户关注其他用户
     *
     * @param targetUid
     * @param callback
     */
    public static void addUserAttention(String targetUid, final OnModelCallback<Object> callback) {
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                try {
                    LogUtils.e(result);
//
                    JSONObject obj = new JSONObject(result);
//                    String code = obj.getString(CODE);
//                    JSONObject obj_info = obj.getJSONObject(INFO);
//                    String tid = obj_info.getString(TID);
                    if (!Util.isNull(callback)) {
                        callback.onModelSuccessed(null);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

                if (!Util.isNull(callback))
                    callback.onModelFailed(errorMsg);

            }
        };


        //post数据
        HashMap<String, String> map = ForumNetPostUtil.initBaseMap()
                .addMethod(URLForum.FORUM_METHOD.ADD_USER_ATTETNTON_URL)
                .addUid(AccountManager.getInstance().getUid())
                .addUid2(targetUid)
                .getMap();

        LogUtils.e(map.toString());
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);
    }

    /**
     * 当前用户取消关注其他用户
     *
     * @param targetUid
     * @param callback
     */
    public static void deleteUserAttention(String targetUid, final OnModelCallback<Object> callback) {
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                try {
                    LogUtils.e(result);
//                    {"code":"0","info":{"follows":"2"}}

                    JSONObject obj = new JSONObject(result);
                    String code = obj.getString(CODE);
                    if (!Util.isNull(callback)) {
                        callback.onModelSuccessed(null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

                if (!Util.isNull(callback))
                    callback.onModelFailed(errorMsg);

            }
        };


        //post数据
        HashMap<String, String> map = ForumNetPostUtil.initBaseMap()
                .addMethod(URLForum.FORUM_METHOD.CANCEL_USER_ATTETNTON_URL)
                .addUid(AccountManager.getInstance().getUid())
                .addUid2(targetUid)
                .getMap();

        LogUtils.e(map.toString());
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);
    }

}
