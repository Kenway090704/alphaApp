package com.alpha.alphaapp.model.v_1_2.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/10/12 13:49
 * Email : xiaokai090704@126.com
 */

public class SectionDeatalBean {

    private String fid;
    private String forumname;
    private String descrip;
    private String logo;
    private String banner;
    private String todaypost;
    private String posts;
    private String article;
    private String manager;
    private String fupname;

    public static SectionDeatalBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), SectionDeatalBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SectionDeatalBean> arraySectionDeatalBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SectionDeatalBean>>() {
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

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getFupname() {
        return fupname;
    }

    public void setFupname(String fupname) {
        this.fupname = fupname;
    }

    @Override
    public String toString() {
        return "SectionDeatalBean{" +
                "fid='" + fid + '\'' +
                ", forumname='" + forumname + '\'' +
                ", descrip='" + descrip + '\'' +
                ", logo='" + logo + '\'' +
                ", banner='" + banner + '\'' +
                ", todaypost='" + todaypost + '\'' +
                ", posts='" + posts + '\'' +
                ", article='" + article + '\'' +
                ", manager='" + manager + '\'' +
                ", fupname='" + fupname + '\'' +
                '}';
    }
}
