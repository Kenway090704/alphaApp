package com.alpha.alphaapp.model.v_1_2.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/9/25 11:30
 * Email : xiaokai090704@126.com
 * 帖子内容基本类
 */

public class TopicBean {

    private String tid;
    private String fid;
    private String icon;
    private String titlefont;
    private String author;
    private String authorid;
    private String subject;
    private String type;
    private String postdate;
    private String lastpost;
    private String lastposter;
    private String hits;
    private String replies;
    private String topped;
    private String locked;
    private String digest;
    private String special;
    private String state;
    private String tpcstatus;
    private String specialsort;
    private String uid;
    private String groupid;
    private String userip;
    private String ifsign;
    private String ipfrom;
    private String content;
    private String attachlist;

    public static TopicBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), TopicBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<TopicBean> arrayTopicBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<TopicBean>>() {
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitlefont() {
        return titlefont;
    }

    public void setTitlefont(String titlefont) {
        this.titlefont = titlefont;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getLastpost() {
        return lastpost;
    }

    public void setLastpost(String lastpost) {
        this.lastpost = lastpost;
    }

    public String getLastposter() {
        return lastposter;
    }

    public void setLastposter(String lastposter) {
        this.lastposter = lastposter;
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

    public String getTopped() {
        return topped;
    }

    public void setTopped(String topped) {
        this.topped = topped;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTpcstatus() {
        return tpcstatus;
    }

    public void setTpcstatus(String tpcstatus) {
        this.tpcstatus = tpcstatus;
    }

    public String getSpecialsort() {
        return specialsort;
    }

    public void setSpecialsort(String specialsort) {
        this.specialsort = specialsort;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getUserip() {
        return userip;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getIfsign() {
        return ifsign;
    }

    public void setIfsign(String ifsign) {
        this.ifsign = ifsign;
    }

    public String getIpfrom() {
        return ipfrom;
    }

    public void setIpfrom(String ipfrom) {
        this.ipfrom = ipfrom;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachlist() {
        return attachlist;
    }

    public void setAttachlist(String attachlist) {
        this.attachlist = attachlist;
    }

    @Override
    public String toString() {
        return "TopicBean{" +
                "tid='" + tid + '\'' +
                ", fid='" + fid + '\'' +
                ", icon='" + icon + '\'' +
                ", titlefont='" + titlefont + '\'' +
                ", author='" + author + '\'' +
                ", authorid='" + authorid + '\'' +
                ", subject='" + subject + '\'' +
                ", type='" + type + '\'' +
                ", postdate='" + postdate + '\'' +
                ", lastpost='" + lastpost + '\'' +
                ", lastposter='" + lastposter + '\'' +
                ", hits='" + hits + '\'' +
                ", replies='" + replies + '\'' +
                ", topped='" + topped + '\'' +
                ", locked='" + locked + '\'' +
                ", digest='" + digest + '\'' +
                ", special='" + special + '\'' +
                ", state='" + state + '\'' +
                ", tpcstatus='" + tpcstatus + '\'' +
                ", specialsort='" + specialsort + '\'' +
                ", uid='" + uid + '\'' +
                ", groupid='" + groupid + '\'' +
                ", userip='" + userip + '\'' +
                ", ifsign='" + ifsign + '\'' +
                ", ipfrom='" + ipfrom + '\'' +
                ", content='" + content + '\'' +
                ", attachlist='" + attachlist + '\'' +
                '}';
    }
}
