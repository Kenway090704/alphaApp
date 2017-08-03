package com.alpha.alphaapp.model.v_1_0.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/6/19 11:34
 * Email : xiaokai090704@126.com
 */

public class CityBean implements IPickerViewData {

    private String id;
    private String cityid;
    private String city;
    private String father;
    private String type;

    public static CityBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), CityBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从字符串中获取CityBean
     *
     * @param json
     * @return
     */
    public static List<CityBean> arrayCityBeanFromStr(String json) {
        List<CityBean> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                CityBean cityBean = new CityBean();
                cityBean.setId(item.getString("id"));
                cityBean.setCityid(item.getString("cityid"));
                cityBean.setCity(item.getString("city"));
                cityBean.setFather(item.getString("father"));
                cityBean.setType(item.getString("type"));
                list.add(cityBean);
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

    public String getCityid() {
        return cityid;
    }

    private void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    private  void setCity(String city) {
        this.city = city;
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
        return "CityBean{" +
                "id='" + id + '\'' +
                ", cityid='" + cityid + '\'' +
                ", city='" + city + '\'' +
                ", father='" + father + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return getCity();
    }
}
