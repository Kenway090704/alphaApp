package com.alpha.alphaapp.model.v_1_2.logic.reply;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.ReplyBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.comm.ForumStants;
import com.alpha.lib_stub.comm.URLForum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.alpha.lib_stub.comm.URLForum.FORUM_BASE_POST_URL;

/**
 * Created by kenway on 17/9/25 14:08
 * Email : xiaokai090704@126.com
 * <p>
 * 评论接口
 */

public class ReplyLogic {

    private static final String CODE = "code";
    private static final String INFO = "info";
    private static final String REPLY = "reply";
    //每次获取20条数据
    private static final int LIMIT = 19;
    private static final int PAGE = 1;


    /**
     * 获取帖子的评论列表。
     *
     * @param tid
     * @param startIndex
     * @param callback
     */
    public static void getReplyList(String tid, int startIndex, final OnModelCallback<List<ReplyBean>> callback) {

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                LogUtils.e("getReplyList==" + result);


                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject obj_info = obj.getJSONObject(INFO);
                    List<ReplyBean> beanList = ReplyBean.arrayReplyBeanFromData(obj_info.toString(), REPLY);

                    if (!Util.isNull(callback))
                        callback.onModelSuccessed(beanList);
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
                .addMethod(URLForum.FORUM_METHOD.GET_TOPIC_REPLY_LIST_URL)
                .addTid(tid)
                .addOffset(startIndex + "")
                .addLimit(LIMIT + "")
                .addPage(PAGE + "")
                .getMap();

        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }

    /**
     * 对帖子进行评论。
     *
     * @param tid
     * @param uid
     * @param title
     */
    public static void sendNewRelpy(String tid, String uid, String title, String content, final OnModelCallback<Object> callback) {

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                //{"code":"0","info":{"pid":"40353"}}
                try {
                    JSONObject obj = new JSONObject(result);
                    String code = obj.getString(CODE);
                    if (code.equals(ForumStants.SEND_REPLY_RESULT.RESULT_OK)) {
                        if (!Util.isNull(callback))
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
                .addMethod(URLForum.FORUM_METHOD.SEND_NEW_REPLY_URL)
                .addTid(tid)
                .addUid(uid)
                .addTitle(title)
                .addContent(content)
                .getMap();

        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);
    }
}
