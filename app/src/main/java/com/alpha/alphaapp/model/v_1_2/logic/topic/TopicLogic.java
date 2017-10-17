package com.alpha.alphaapp.model.v_1_2.logic.topic;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.OnModelHasProgressCallBack;
import com.alpha.alphaapp.model.v_1_2.bean.TopicBean;
import com.alpha.alphaapp.model.v_1_2.bean.UploadPicBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.ReqProgressCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.JsonUtil;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.comm.ForumStants;
import com.alpha.lib_stub.comm.URLForum;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.alpha.lib_stub.comm.URLForum.FORUM_BASE_POST_URL;

/**
 * Created by kenway on 17/9/20 15:55
 * Email : xiaokai090704@126.com
 * 帖子相关逻辑
 */

public class TopicLogic {
    private static final String INFO = "info";
    private static final String CODE = "code";
    private static final String TID = "tid";
    private static final String AID = "aid";

    private static final String IMG_START = "[img]";
    private static final String IMG_END = "[/img]";

    /**
     * 获取帖子内容等信息部分
     *
     * @param tid
     * @param callback
     */
    public static void getTopic(String tid, final OnModelCallback<TopicBean> callback) {

        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    TopicBean bean = TopicBean.objectFromData(obj.toString(), INFO);
                    LogUtils.e("TopicBean" + bean.toString());
                    if (!Util.isNull(callback)) {
                        callback.onModelSuccessed(bean);
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
                .addMethod(URLForum.FORUM_METHOD.GET_TOPIC_CONTENT_URL)
                .addTid(tid)
                .getMap();
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }

    /**
     * 发布新帖
     *
     * @param fid
     * @param subject
     * @param content
     * @param callback
     */
    public static void sendNewTopic(String fid, String subject, String content, List<UploadPicBean> list, final OnModelCallback<Object> callback) {
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                try {
                    LogUtils.e(result);
//                    {"code":"0","info":{"tid":"4026"}}
                    JSONObject obj = new JSONObject(result);
                    String code = obj.getString(CODE);
                    JSONObject obj_info = obj.getJSONObject(INFO);
                    String tid = obj_info.getString(TID);

                    if (ForumStants.SEND_TOPIC_RESULT.RESULT_OK.equals(code) && !Util.isNull(tid)) {
                        if (!Util.isNull(callback)) {
                            callback.onModelSuccessed(null);
                        }
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
         //处理帖子内容
        String content_last = TopicLogic.doContentTxt(content, list);
        //处理aids(缩略图)
        List<String> list_aid = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list_aid.add(list.get(i).getAid());
        }
        String josn_aid = JsonUtil.getJSONStrFromList(list_aid);//["12356","12357"]

        //post数据
        HashMap<String, String> map = ForumNetPostUtil.initBaseMap()
                .addMethod(URLForum.FORUM_METHOD.CREATE_NEW_TOPIC_URL)
                .addUid(AccountManager.getInstance().getUid())
                .addFid(fid)
                .addSubject(subject)
                .addContent(content_last)
                .addFlashatts(josn_aid)
                .getMap();

        LogUtils.e(map.toString());
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);
    }


    /**
     * 发布新帖时,将从相册和照相获取的图片上传,(有进度)
     *
     * @param fid
     * @param imgPath
     * @param imgPath
     * @param callback
     */
    public static void sendNewTopicUploadPicHasProgress(String fid, String imgPath, final OnModelHasProgressCallBack<UploadPicBean> callback) {

        final ReqProgressCallBack<String> call = new ReqProgressCallBack<String>() {
            @Override
            public void onProgress(long total, long current) {
                if (!Util.isNull(callback))
                    callback.onModelProgress(total, current);
            }

            @Override
            public void onReqSuccess(String result) {
                try {

                    /**
                     * {"code":"0",
                     * "info":
                     * {"aid":"12159",
                     * "path":"http:\/\/120.76.27.29:8090\/attachment\/1709\/thread\/2_40219_e7487a5b3aef758.png",
                     * "thumbpath":"http:\/\/120.76.27.29:8090\/attachment\/thumb\/mini\/1709\/thread\/2_40219_e7487a5b3aef758.png"}}
                     */
                    //处理返回的json 数据
                    JSONObject obj = new JSONObject(result);
                    String code = obj.getString(CODE);
                    if (ForumStants.SEND_TOPIC_UPLOAD_PIC_RESULT.RESULT_OK.equals(code)) {
                        UploadPicBean bean = UploadPicBean.objectFromData(obj.toString(), INFO);
                        if (!Util.isNull(callback)) {
                            callback.onModelSuccessed(bean);
                        }
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

        File file = new File(imgPath);
        //post数据
        HashMap<String, String> map = ForumNetPostUtil.initBaseMap()
                .addMethod(URLForum.FORUM_METHOD.CREATE_NEW_TOPIC_UPLOAD_PIC)
                .addUid(AccountManager.getInstance().getUid())
                .addFid(fid)
                .addFileName(file.getName())
                .getMap();

        HashMap<String, Object> map_file = new HashMap<>();
        map_file.put("PIC", file);
        map_file.putAll(map);
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).upLoadFileHasProgress(FORUM_BASE_POST_URL, map_file, call);
    }

    /**
     * 处理发帖内容部分
     *
     * @param txt
     * @param list
     */
    public static String doContentTxt(String txt, List<UploadPicBean> list) {
        if (!Util.isNull(list) && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                String s = IMG_START + list.get(i).getPath() + IMG_END;
                txt += s;
            }
        }
        return txt;
    }


}
