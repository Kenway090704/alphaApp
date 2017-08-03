package com.alpha.alphaapp.model.v_1_1.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/21 17:01
 * Email : xiaokai090704@126.com
 * 订单bean(兑换记录)
 */

public class OrderBean implements Serializable {

    public static final String KEY_ORDER_LIST = "order_list";
    public static final String KEY_ORDER_DETAIL = "order_detail";

    /**
     * {"order_id":10014000000017684,
     * "goods_id":10014,
     * "goods_name":"特别版精准发射器",
     * "create_time":"2017-07-26 18:05:37",
     * "update_time":"2017-07-26 18:05:37",
     * "account":"kenway","uuid":"26fae07ae3c42bee3246051107b5380a",
     * "count":1,
     * "receive_name":"xk",
     * "receive_phone":"13128914595",
     * "receive_province":"北京",
     * "receive_city":"北京",
     * "receive_area":"东城区",
     * "receive_street":"",
     * "addr_detail":"adasfasdff",
     * "post_code":"100000",
     * "logistics":"",
     * "logistics_num":"",
     * "status":0,
     * "close_reason":"",
     * "score":4000,
     * "goods_type":0,
     * "goods_no":"",
     * "remark":"",
     * "product_code":"cc",
     * "channel_id":38}
     */
    private long order_id;
    private int goods_id;
    private String goods_name;
    private String create_time;
    private String update_time;
    private String account;
    private String uuid;
    private int count;
    private String receive_name;
    private String receive_phone;
    private String receive_province;
    private String receive_city;
    private String receive_area;
    private String receive_street;
    private String addr_detail;
    private String post_code;
    private String logistics;
    private String logistics_num;
    private int status;
    private String close_reason;
    private int score;
    private int goods_type;
    private String goods_no;
    private String remark;
    private String product_code;
    private int channel_id;

    public static OrderBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), OrderBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<OrderBean> arrayOrderBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<OrderBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public String getReceive_phone() {
        return receive_phone;
    }

    public void setReceive_phone(String receive_phone) {
        this.receive_phone = receive_phone;
    }

    public String getReceive_province() {
        return receive_province;
    }

    public void setReceive_province(String receive_province) {
        this.receive_province = receive_province;
    }

    public String getReceive_city() {
        return receive_city;
    }

    public void setReceive_city(String receive_city) {
        this.receive_city = receive_city;
    }

    public String getReceive_area() {
        return receive_area;
    }

    public void setReceive_area(String receive_area) {
        this.receive_area = receive_area;
    }

    public String getReceive_street() {
        return receive_street;
    }

    public void setReceive_street(String receive_street) {
        this.receive_street = receive_street;
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

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getLogistics_num() {
        return logistics_num;
    }

    public void setLogistics_num(String logistics_num) {
        this.logistics_num = logistics_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getClose_reason() {
        return close_reason;
    }

    public void setClose_reason(String close_reason) {
        this.close_reason = close_reason;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "order_id=" + order_id +
                ", goods_id=" + goods_id +
                ", goods_name='" + goods_name + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", account='" + account + '\'' +
                ", uuid='" + uuid + '\'' +
                ", count=" + count +
                ", receive_name='" + receive_name + '\'' +
                ", receive_phone='" + receive_phone + '\'' +
                ", receive_province='" + receive_province + '\'' +
                ", receive_city='" + receive_city + '\'' +
                ", receive_area='" + receive_area + '\'' +
                ", receive_street='" + receive_street + '\'' +
                ", addr_detail='" + addr_detail + '\'' +
                ", post_code='" + post_code + '\'' +
                ", logistics='" + logistics + '\'' +
                ", logistics_num='" + logistics_num + '\'' +
                ", status=" + status +
                ", close_reason='" + close_reason + '\'' +
                ", score=" + score +
                ", goods_type=" + goods_type +
                ", goods_no='" + goods_no + '\'' +
                ", remark='" + remark + '\'' +
                ", product_code='" + product_code + '\'' +
                ", channel_id=" + channel_id +
                '}';
    }
}
