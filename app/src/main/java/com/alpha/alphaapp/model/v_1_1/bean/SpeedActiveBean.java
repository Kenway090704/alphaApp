package com.alpha.alphaapp.model.v_1_1.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/28 12:15
 * Email : xiaokai090704@126.com
 */

public class SpeedActiveBean {


    private String lot_nums;
    private int status;
    private String type;
    private String serial;
    private String name;
    private String product;
    private String bannber;

    public static SpeedActiveBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), SpeedActiveBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SpeedActiveBean> arraySpeedActiveBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SpeedActiveBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getLot_nums() {
        return lot_nums;
    }

    public void setLot_nums(String lot_nums) {
        this.lot_nums = lot_nums;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBannber() {
        return bannber;
    }

    public void setBannber(String bannber) {
        this.bannber = bannber;
    }


    @Override
    public String toString() {
        return "SpeedActiveBean{" +
                "lot_nums='" + lot_nums + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", serial='" + serial + '\'' +
                ", name='" + name + '\'' +
                ", product='" + product + '\'' +
                ", bannber='" + bannber + '\'' +
                '}';
    }
}
