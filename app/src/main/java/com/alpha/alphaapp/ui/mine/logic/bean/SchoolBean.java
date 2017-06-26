package com.alpha.alphaapp.ui.mine.logic.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/6/19 17:07
 * Email : xiaokai090704@126.com
 */

public class SchoolBean implements IPickerViewData {

    private String id;
    private String province;
    private String city;
    private String area;
    private String school;



    /**
     * 模糊搜索的关键字

     */
    private String txt;

    public static SchoolBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), SchoolBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取中学列表
     *
     * @param json
     * @return
     */
    public static List<SchoolBean> arraySchoolBeanFromData(String json) {
        List<SchoolBean> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                SchoolBean schoolBean = new SchoolBean();
                schoolBean.setId(item.optString("id"));
                schoolBean.setProvince(item.optString("province"));
                schoolBean.setCity(item.optString("city"));
                schoolBean.setArea(item.optString("area"));
                schoolBean.setSchool(item.optString("school"));
                list.add(schoolBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "SchoolBean{" +
                "id='" + id + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", school='" + school + '\'' +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return getSchool();
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
