package com.alpha.alphaapp.model.v_1_2.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/10/11 15:33
 * Email : xiaokai090704@126.com
 *
 * {
 "data_id":"1089",
 "title":"开学欢乐送 | 超级飞侠为你开学助威",
 "url":"http://club.auldey.com/read.php?tid=3789&fid=2",
 "thumb":"http://club.auldey.com/attachment/module/10/90f62100cdbceab.jpg"
 }
 */

public class BannerBean {

    private String data_id;
    private String title;
    private String url;
    private String thumb;

    public static BannerBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), BannerBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<BannerBean> arrayBannerBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<BannerBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "data_id='" + data_id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}
