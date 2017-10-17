package com.alpha.alphaapp.model.v_1_2.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/9/21 16:52
 * Email : xiaokai090704@126.com
 * <p>
 * TA的主题中bean类,现在缺少喜欢数,浏览数,评论数
 */

public class UserTopicListBean implements Serializable {


    private String tid;
    private String fid;
    private String forumname;
    private String icon;
    private String author;
    private String authorid;
    private String subject;
    private String postdate;

    public static UserTopicListBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), UserTopicListBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<UserTopicListBean> arrayUserTopicListBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<UserTopicListBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getForumname() {
        return forumname;
    }

    public void setForumname(String forumname) {
        this.forumname = forumname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    @Override
    public String toString() {
        return "UserTopicListBean{" +
                "tid='" + tid + '\'' +
                ", fid='" + fid + '\'' +
                ", forumname='" + forumname + '\'' +
                ", icon='" + icon + '\'' +
                ", author='" + author + '\'' +
                ", authorid='" + authorid + '\'' +
                ", subject='" + subject + '\'' +
                ", postdate='" + postdate + '\'' +
                '}';
    }
}
