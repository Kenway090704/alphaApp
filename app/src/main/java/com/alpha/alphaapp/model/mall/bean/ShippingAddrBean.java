package com.alpha.alphaapp.model.mall.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/18 15:16
 * Email : xiaokai090704@126.com
 */

public class ShippingAddrBean implements Serializable {
    private static final String TAG = "ShippingAddrBean";
    private static final String KEY_UINFO = "uinfo";
    private static final String KEY_DELIVERY_ADDR = "delivery_addr";
    public static  final  String KEY_ADDR_LIST="addr_list";

    private int id;
    private String addr_detail;
    private String post_code;
    private String name;
    private String mobile;
    private int default_addr;
    private String province;
    private String city;
    private String area;

    public static ShippingAddrBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), ShippingAddrBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param str
     * @param key--ShippingAddrBean.KEY_ADDR_LIST
     * @return
     */
    public static List<ShippingAddrBean> arrayShippingBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            jsonObject = jsonObject.getJSONObject(KEY_UINFO);
            jsonObject = jsonObject.getJSONObject(KEY_DELIVERY_ADDR);
            Type listType = new TypeToken<ArrayList<ShippingAddrBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public void setAddr_detail(String addr_detail) {
        this.addr_detail = addr_detail;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getDefault_addr() {
        return default_addr;
    }

    public void setDefault_addr(int default_addr) {
        this.default_addr = default_addr;
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

    @Override
    public String toString() {
        return "ShippingAddrBean{" +
                "id=" + id +
                ", addr_detail='" + addr_detail + '\'' +
                ", post_code='" + post_code + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", default_addr=" + default_addr +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                '}';
    }

    /**
     * 获取地址:广东省广州市天河区金惠路18号 510000
     *
     * @return
     */
    public String getAddrAll() {
        return getProvince() + getCity() + getArea() + getAddr_detail() + " " + (getPost_code().equals("null") ? "000000" : getPost_code());
    }
    /**
     * {"result":0,"msg":"","uinfo":{"delivery_addr":{"addr_list":[
     * {"id":1,"addr_detail":"金惠路18号","post_code":"51000","name":"kim","mobile":"13128914595","default_addr":0,"province":"广东省","city":"广州市","area":"天河区"},
     * {"id":2,"addr_detail":"金惠路18号","post_code":"51000","name":"kim","mobile":"13128914595","default_addr":0,"province":"广东省","city":"广州市","area":"天河区"},
     * {"id":3,"addr_detail":"金惠路18号","post_code":"51000","name":"kim","mobile":"13128914595","default_addr":0,"province":"广东省","city":"广州市","area":"天河区"},
     * {"id":4,"addr_detail":"金惠路18号","post_code":"51000","name":"kim","mobile":"13128914595","default_addr":0,"province":"广东省","city":"广州市","area":"天河区"},
     * {"id":5,"addr_detail":"金惠路18号","post_code":"51000","name":"kim","mobile":"13128914595","default_addr":1,"province":"广东省","city":"广州市","area":"天河区"}
     *
     * ]}}}

     */
}
