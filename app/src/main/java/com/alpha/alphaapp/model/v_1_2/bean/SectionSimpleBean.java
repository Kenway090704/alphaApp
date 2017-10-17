package com.alpha.alphaapp.model.v_1_2.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/9/20 17:44
 * Email : xiaokai090704@126.com
 *
 * 获取父版块和子版块简单信息
 */

public class SectionSimpleBean {


    private String fid;
    private String forumname;
    private String type;
    private String todaypost;
    private String domain;
    private List<ChildBean> child;

    public static SectionSimpleBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), SectionSimpleBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SectionSimpleBean> arraySectionSimpleBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SectionSimpleBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTodaypost() {
        return todaypost;
    }

    public void setTodaypost(String todaypost) {
        this.todaypost = todaypost;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean {
        private String fid;
        private String forumname;
        private String type;
        private String todaypost;
        private String posts;
        private String article;
        private String icon;
        private String domain;
        private List<?> child;

        @Override
        public String toString() {
            return "ChildBean{" +
                    "fid='" + fid + '\'' +
                    ", forumname='" + forumname + '\'' +
                    ", type='" + type + '\'' +
                    ", todaypost='" + todaypost + '\'' +
                    ", posts='" + posts + '\'' +
                    ", article='" + article + '\'' +
                    ", icon='" + icon + '\'' +
                    ", domain='" + domain + '\'' +
                    ", child=" + child +
                    '}';
        }

        public static ChildBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), ChildBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<ChildBean> arrayChildBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<ChildBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTodaypost() {
            return todaypost;
        }

        public void setTodaypost(String todaypost) {
            this.todaypost = todaypost;
        }

        public String getPosts() {
            return posts;
        }

        public void setPosts(String posts) {
            this.posts = posts;
        }

        public String getArticle() {
            return article;
        }

        public void setArticle(String article) {
            this.article = article;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public List<?> getChild() {
            return child;
        }

        public void setChild(List<?> child) {
            this.child = child;
        }
    }

    @Override
    public String toString() {
        return "SectionSimpleBean{" +
                "fid='" + fid + '\'' +
                ", forumname='" + forumname + '\'' +
                ", type='" + type + '\'' +
                ", todaypost='" + todaypost + '\'' +
                ", domain='" + domain + '\'' +
                ", child=" + child +
                '}';
    }
}
