package com.alpha.alphaapp.model.v_1_2.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/9/25 12:12
 * Email : xiaokai090704@126.com
 * 评论的Bean
 */

public class ReplyBean {


    private String pid;
    private String fid;
    private String tid;
    private String disabled;
    private String ischeck;
    private String ifshield;
    private String replies;
    private String useubb;
    private String usehtml;
    private String aids;
    private String rpid;
    private String subject;
    private String content;
    private String like_count;
    private String sell_count;
    private String created_time;
    private String created_username;
    private String created_userid;
    private String created_ip;
    private String reply_notice;
    private String modified_time;
    private String modified_username;
    private String modified_userid;
    private String modified_ip;
    private String reminds;
    private String word_version;
    private String ipfrom;
    private String manage_remind;
    private String topped;
    private String app_mark;
    private String lou;
    private String icon;

    public static ReplyBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), ReplyBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ReplyBean> arrayReplyBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ReplyBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getIfshield() {
        return ifshield;
    }

    public void setIfshield(String ifshield) {
        this.ifshield = ifshield;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getUseubb() {
        return useubb;
    }

    public void setUseubb(String useubb) {
        this.useubb = useubb;
    }

    public String getUsehtml() {
        return usehtml;
    }

    public void setUsehtml(String usehtml) {
        this.usehtml = usehtml;
    }

    public String getAids() {
        return aids;
    }

    public void setAids(String aids) {
        this.aids = aids;
    }

    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getSell_count() {
        return sell_count;
    }

    public void setSell_count(String sell_count) {
        this.sell_count = sell_count;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getCreated_username() {
        return created_username;
    }

    public void setCreated_username(String created_username) {
        this.created_username = created_username;
    }

    public String getCreated_userid() {
        return created_userid;
    }

    public void setCreated_userid(String created_userid) {
        this.created_userid = created_userid;
    }

    public String getCreated_ip() {
        return created_ip;
    }

    public void setCreated_ip(String created_ip) {
        this.created_ip = created_ip;
    }

    public String getReply_notice() {
        return reply_notice;
    }

    public void setReply_notice(String reply_notice) {
        this.reply_notice = reply_notice;
    }

    public String getModified_time() {
        return modified_time;
    }

    public void setModified_time(String modified_time) {
        this.modified_time = modified_time;
    }

    public String getModified_username() {
        return modified_username;
    }

    public void setModified_username(String modified_username) {
        this.modified_username = modified_username;
    }

    public String getModified_userid() {
        return modified_userid;
    }

    public void setModified_userid(String modified_userid) {
        this.modified_userid = modified_userid;
    }

    public String getModified_ip() {
        return modified_ip;
    }

    public void setModified_ip(String modified_ip) {
        this.modified_ip = modified_ip;
    }

    public String getReminds() {
        return reminds;
    }

    public void setReminds(String reminds) {
        this.reminds = reminds;
    }

    public String getWord_version() {
        return word_version;
    }

    public void setWord_version(String word_version) {
        this.word_version = word_version;
    }

    public String getIpfrom() {
        return ipfrom;
    }

    public void setIpfrom(String ipfrom) {
        this.ipfrom = ipfrom;
    }

    public String getManage_remind() {
        return manage_remind;
    }

    public void setManage_remind(String manage_remind) {
        this.manage_remind = manage_remind;
    }

    public String getTopped() {
        return topped;
    }

    public void setTopped(String topped) {
        this.topped = topped;
    }

    public String getApp_mark() {
        return app_mark;
    }

    public void setApp_mark(String app_mark) {
        this.app_mark = app_mark;
    }

    public String getLou() {
        return lou;
    }

    public void setLou(String lou) {
        this.lou = lou;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ReplyBean{" +
                "pid='" + pid + '\'' +
                ", fid='" + fid + '\'' +
                ", tid='" + tid + '\'' +
                ", disabled='" + disabled + '\'' +
                ", ischeck='" + ischeck + '\'' +
                ", ifshield='" + ifshield + '\'' +
                ", replies='" + replies + '\'' +
                ", useubb='" + useubb + '\'' +
                ", usehtml='" + usehtml + '\'' +
                ", aids='" + aids + '\'' +
                ", rpid='" + rpid + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", like_count='" + like_count + '\'' +
                ", sell_count='" + sell_count + '\'' +
                ", created_time='" + created_time + '\'' +
                ", created_username='" + created_username + '\'' +
                ", created_userid='" + created_userid + '\'' +
                ", created_ip='" + created_ip + '\'' +
                ", reply_notice='" + reply_notice + '\'' +
                ", modified_time='" + modified_time + '\'' +
                ", modified_username='" + modified_username + '\'' +
                ", modified_userid='" + modified_userid + '\'' +
                ", modified_ip='" + modified_ip + '\'' +
                ", reminds='" + reminds + '\'' +
                ", word_version='" + word_version + '\'' +
                ", ipfrom='" + ipfrom + '\'' +
                ", manage_remind='" + manage_remind + '\'' +
                ", topped='" + topped + '\'' +
                ", app_mark='" + app_mark + '\'' +
                ", lou='" + lou + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
