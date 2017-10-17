package com.alpha.alphaapp.model.v_1_2.logic.user;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.model.v_1_2.bean.UserTopicListBean;
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
 * Created by kenway on 17/10/16 15:06
 * Email : xiaokai090704@126.com
 */

public class ForumUserLogic {

    private static final String INFO = "info";
    private static final String FORUMS = "forums";
    private static final String FORUM = "forum";
    private static final String THREADS = "threads";
    private static final String COUNT = "count";
    //每次获取20条数据
    private static final int LIMIT = 19;

//    GET_USER_TOPICS

    /**
     * 获取TA的主题
     * 每次获取20 条数据
     *
     * @param uid
     * @param offset
     * @param callback
     */
    public static void getUserTopics(String uid, int offset, final OnModelCallback<List<UserTopicListBean>> callback) {

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                LogUtils.e(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject obj_info = obj.getJSONObject(INFO);
                    JSONArray array = obj_info.getJSONArray(THREADS);
                    //将json转换为数组
                    List<UserTopicListBean> list = JsonUtil.jsonToBeanArray(array.toString(), UserTopicListBean.class);
                    if (!Util.isNull(callback))
                        callback.onModelSuccessed(list);
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
                .addMethod(URLForum.FORUM_METHOD.GET_USER_TOPICS_URL)
                .addUid(uid)
                .addOffset(offset + "")
                .addLimit(LIMIT + "")
                .getMap();

        LogUtils.e(map.toString());

        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }


    /**
     * 获取TA的回复
     * 每次获取20 条数据
     *OnModelCallback<List<UserTopicListBean>> callback  当有他的回复接口后需要跳转
     * @param uid
     * @param offset
     * @param callback
     */
    public static void getUserReplys(String uid, int offset, final OnModelCallback<List<UserTopicListBean>> callback) {

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                LogUtils.e(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject obj_info = obj.getJSONObject(INFO);
                    JSONArray array = obj_info.getJSONArray(THREADS);
                    //将json转换为数组
                    List<UserTopicListBean> list = JsonUtil.jsonToBeanArray(array.toString(), UserTopicListBean.class);
                    if (!Util.isNull(callback))
                        callback.onModelSuccessed(list);
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
                .addMethod(URLForum.FORUM_METHOD.GET_USER_TOPICS_URL)
                .addUid(uid)
                .addOffset(offset + "")
                .addLimit(LIMIT + "")
                .getMap();

        LogUtils.e(map.toString());

        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }

    /**
     * 获取TA的喜欢
     * 每次获取20 条数据
     *OnModelCallback<List<UserTopicListBean>> callback  当有他的回复接口后需要跳转
     * @param uid
     * @param offset
     * @param callback
     */
    public static void getUserLikes(String uid, int offset, final OnModelCallback<List<UserTopicListBean>> callback) {

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                LogUtils.e(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject obj_info = obj.getJSONObject(INFO);
                    JSONArray array = obj_info.getJSONArray(THREADS);
                    //将json转换为数组
                    List<UserTopicListBean> list = JsonUtil.jsonToBeanArray(array.toString(), UserTopicListBean.class);
                    if (!Util.isNull(callback))
                        callback.onModelSuccessed(list);
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
                .addMethod(URLForum.FORUM_METHOD.GET_USER_TOPICS_URL)
                .addUid(uid)
                .addOffset(offset + "")
                .addLimit(LIMIT + "")
                .getMap();

        LogUtils.e(map.toString());

        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }
}
