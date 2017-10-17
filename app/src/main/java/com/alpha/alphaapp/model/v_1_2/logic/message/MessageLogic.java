package com.alpha.alphaapp.model.v_1_2.logic.message;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.comm.URLForum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.alpha.lib_stub.comm.URLForum.FORUM_BASE_POST_URL;

/**
 * Created by kenway on 17/10/16 18:53
 * Email : xiaokai090704@126.com
 */

public class MessageLogic {

    //每次获取20条数据
    private static final int LIMIT = 19;

    /**
     * 发送和回复私信
     *
     * @param callBack
     */
    public static void sendMessage(String touid, String content, final OnModelCallback<Object> callBack) {


        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                try {
                    LogUtils.e(result);
//                    {"code":"0","info":{"dialogid":"1295"}}
                  //  {"code":"-1","info":"发给自己是没什么意义的哦"}
                    JSONObject obj = new JSONObject(result);



                    if (!Util.isNull(callBack))
                        callBack.onModelSuccessed(null);
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
                .addMethod(URLForum.FORUM_METHOD.SEND_MESSAGE_URL)
                .addFromUid(AccountManager.getInstance().getUid())
                .addToUid(touid)
                .addContent(content)
                .getMap();


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }

    /**
     * 获取未读消息数
     *
     * @param callBack
     */
    public static void getNoReadMessageCount(final OnModelCallback<Object> callBack) {


        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                try {
                    LogUtils.e(result);
                    JSONObject obj = new JSONObject(result);


                    if (!Util.isNull(callBack))
                        callBack.onModelSuccessed(null);
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
                .addMethod(URLForum.FORUM_METHOD.GET_NO_READ_MESSAGE_COUNT_URL)
                .addUid(AccountManager.getInstance().getUid())
                .getMap();


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }

    /**
     * 获取消息列表
     *
     * @param callBack
     */
    public static void getMessageList(int offset, final OnModelCallback<Object> callBack) {


        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                try {
                    LogUtils.e(result);
//
                    JSONObject obj = new JSONObject(result);


                    if (!Util.isNull(callBack))
                        callBack.onModelSuccessed(null);
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
                .addMethod(URLForum.FORUM_METHOD.GET_MESSAGE_LIST_URL)
                .addUid(AccountManager.getInstance().getUid())
                .addOffset(offset + "")
                .addLimit(LIMIT + "")
                .getMap();


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }
}
