package com.alpha.alphaapp.model.v_1_0.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/6/19 11:44
 * Email : xiaokai090704@126.com
 */

public class AreaBean implements IPickerViewData {

    private String id;
    private String areaid;
    private String area;
    private String father;
    private String type;

    public static AreaBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), AreaBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从字符串中获取AreaBean
     *
     * @param json
     * @return
     */
    public static List<AreaBean> arrayAreaBeanFromStr(String json) {
        List<AreaBean> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                AreaBean areaBean = new AreaBean();
                areaBean.setId(item.getString("id"));
                areaBean.setAreaid(item.getString("areaid"));
                areaBean.setArea(item.getString("area"));
                areaBean.setFather(item.getString("father"));
                areaBean.setType(item.getString("type"));
                list.add(areaBean);
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

    public String getAreaid() {
        return areaid;
    }

    private void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getArea() {
        return area;
    }

    private void setArea(String area) {
        this.area = area;
    }

    public String getFather() {
        return father;
    }

    private void setFather(String father) {
        this.father = father;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AreaBean{" +
                "id='" + id + '\'' +
                ", areaid='" + areaid + '\'' +
                ", area='" + area + '\'' +
                ", father='" + father + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return getArea();
    }
}
