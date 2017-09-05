package com.alpha.alphaapp.model.v_1_2.bean;

import com.alpha.alphaapp.model.v_1_0.bean.ProviceBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/8/15 15:05
 * Email : xiaokai090704@126.com
 */

public class ForumBean {
    private String url;
    private String title;
    private String space;
    private String avatar_s;
    private String author;
    private String like;
    private String hits;
    private String replies;
    private String tid;
    private String intro;
    private String __style;

    public static ForumBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), ForumBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 将没有key字符串转换为JavaBean
     * @param result
     * @return
     */
    public static List<ForumBean> parseData(String result) {//Gson 解析
        ArrayList<ForumBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ForumBean entity =
                        gson.fromJson(data.optJSONObject(i).toString(), ForumBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
    public static List<ForumBean> arrayForumBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ForumBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getAvatar_s() {
        return avatar_s;
    }

    public void setAvatar_s(String avatar_s) {
        this.avatar_s = avatar_s;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String get__style() {
        return __style;
    }

    public void set__style(String __style) {
        this.__style = __style;
    }
}
