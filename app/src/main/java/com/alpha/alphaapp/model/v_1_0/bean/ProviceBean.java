package com.alpha.alphaapp.model.v_1_0.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/6/19 09:53
 * Email : xiaokai090704@126.com
 * 从网上解析省市县
 */

public class ProviceBean implements IPickerViewData {

    private String id;
    private String provinceid;
    private String province;
    private String type;

    public static ProviceBean objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), ProviceBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 从json字符串中获取省的集合
     *
     * @param json
     * @return
     */
    public static List<ProviceBean> arrayProvieBeanFromJson(String json) {
        List<ProviceBean> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                ProviceBean proviceBean = new ProviceBean();
                proviceBean.setId(item.getString("id"));
                proviceBean.setProvinceid(item.getString("provinceid"));
                proviceBean.setProvince(item.getString("province"));
                proviceBean.setType(item.getString("type"));
                list.add(proviceBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将没有key字符串转换为JavaBean
     * @param result
     * @return
     */
    public static List<ProviceBean> parseData(String result) {//Gson 解析
        ArrayList<ProviceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProviceBean entity =
                        gson.fromJson(data.optJSONObject(i).toString(), ProviceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceid() {
        return provinceid;
    }

    private void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getProvince() {
        return province;
    }

    private void setProvince(String province) {
        this.province = province;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProviceBean{" +
                "id='" + id + '\'' +
                ", provinceid='" + provinceid + '\'' +
                ", province='" + province + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return getProvince();
    }
}
