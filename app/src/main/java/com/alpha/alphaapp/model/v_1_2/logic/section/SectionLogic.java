package com.alpha.alphaapp.model.v_1_2.logic.section;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.SectionDeatalBean;
import com.alpha.alphaapp.model.v_1_2.bean.SectionSimpleBean;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
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

import static com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil.VALUE_LASTPOST;
import static com.alpha.lib_stub.comm.URLForum.FORUM_BASE_POST_URL;

/**
 * Created by kenway on 17/9/20 17:34
 * Email : xiaokai090704@126.com
 * 版块相关逻辑
 */

public class SectionLogic {
    private static final String INFO = "info";
    private static final String FORUMS = "forums";
    private static final String FORUM = "forum";
    private static final String THREADS = "threads";
    private static final String COUNT = "count";
    //每次获取20条数据
    private static final int LIMIT = 19;

    /**
     * 获取版块列表
     *
     * @param callBack
     */
    public static void getSectionSimpleInfoList(final OnModelCallback<List<SectionSimpleBean>> callBack) {
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                //添加测试数据
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject obj_info = obj.getJSONObject(INFO);
                    List<SectionSimpleBean> list = SectionSimpleBean.arraySectionSimpleBeanFromData(obj_info.toString(), FORUMS);
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
                .addMethod(URLForum.FORUM_METHOD.GET_SECTION_LIST_URL)
                .getMap();
//        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestGet(URLConstans.FORUM_URL.GET_SECTION_LIST_URL, call);
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);
    }

    /**
     * 获取全部版块的热门推荐帖子列表
     *
     * @param callBack
     */
    public static void getAllSectionHotTopicList(int offset, final OnModelCallback<List<TopicListBean>> callBack) {


        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                try {

                    LogUtils.e("帖子列表===" + result.toString());
                    JSONObject obj = new JSONObject(result);
                    JSONObject obj_info = obj.getJSONObject(INFO);
                    JSONArray array = obj_info.getJSONArray(THREADS);
                    int count = Integer.parseInt(obj_info.getString(COUNT));
                    //添加测试数据
//                    List<TopicListBean> list = JsonUtil.jsonToBeanArray(array.toString(), TopicListBean.class);
                    List<TopicListBean> list = TopicListBean.arrayTopicListBeanFromData(obj_info.toString(), THREADS);

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
                .addMethod(URLForum.FORUM_METHOD.ALL_HOT)
                .addOffset(offset + "")
                .addLimit(LIMIT + "")
                .addOrder(ForumNetPostUtil.VALUE_LASTPOST)
                .getMap();


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }

    /**
     * 获取指定版块今日热门帖子列表
     * 每次获取20 条数据
     *
     * @param fid
     * @param offset
     * @param callback
     */
    public static void getSectionHotTopicList(String fid, String topicType, int offset, final OnModelCallback<List<TopicListBean>> callback) {

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                LogUtils.e(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject obj_info = obj.getJSONObject(INFO);
                    JSONArray array = obj_info.getJSONArray(THREADS);
                    if (!Util.isNullOrBlank(obj_info.getString(COUNT))) {
                        int count = Integer.parseInt(obj_info.getString(COUNT));
                    }

                    //添加测试数据
                    List<TopicListBean> list = JsonUtil.jsonToBeanArray(array.toString(), TopicListBean.class);

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
                .addMethod(URLForum.FORUM_METHOD.GET_SECTION_HOT_TOPIC_LIST_URL)
                .addFid(fid)
                .addOffset(offset + "")
                .addLimit(LIMIT + "")
                .addOrder(topicType)
                .getMap();

        LogUtils.e(map.toString());

        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }

    /**
     * 获取版块详情
     * {
     * "code":"0",
     * "info":{
     * "forum":{
     * "fid":"6",
     * "forumname":"综合讨论区",
     * "descrip":"奥飞俱乐部是奥飞娱乐论坛的综合讨论区，我们会在这里发布一些关于奥飞娱乐的官方资讯；同时，你也可以在这里讨论有关奥飞娱乐及其动漫IP的新鲜事。",
     * "logo":"http://120.76.27.29:8090/attachment/forum/logo/6.png",
     * "banner":"http://120.76.27.29:8090/attachment/forum/banner/6.jpg",
     * "todaypost":"0",
     * "posts":"753",
     * "article":"1189",
     * "manager":"",
     * "fupname":"奥飞俱乐部"
     * },
     * "todaythreads":"0",
     * "domain":""
     * }
     * }
     *
     * @param fid
     * @param callback
     */
    public static void getSectionDetailInfo(String fid, final OnModelCallback<SectionDeatalBean> callback) {

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                LogUtils.e(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONObject obj_info = obj.getJSONObject(INFO);
                    SectionDeatalBean bean = SectionDeatalBean.objectFromData(obj_info.toString(), FORUM);

                    if (!Util.isNull(callback))
                        callback.onModelSuccessed(bean);
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
                .addMethod(URLForum.FORUM_METHOD.GET_SECTION_DETAIL_URL)
                .addFid(fid)
                .getMap();

        LogUtils.e(map.toString());

        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }

}
