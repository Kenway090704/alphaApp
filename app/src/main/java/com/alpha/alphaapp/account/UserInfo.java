package com.alpha.alphaapp.account;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/5/22 14:58
 * Email : xiaokai090704@126.com
 * 用户信息
 */

public class UserInfo {
    public static final String TAG = "UserInfo";
    public static final String UINFO = "uinfo";
    private String account;
    private String create_time;
    private String uuid;
    private String openid_qq;
    private String openid_weixin;
    private String openid_sina;
    private String mobile;
    private String email;
    private String country;
    private String province;
    private String city;
    private int birthday_year;
    private int birthday_mon;
    private int birthday_day;
    private String qq;
    private String interest;
    private String school;
    private String contact_addr;
    private String sell_area;
    private int parent_id;
    private String name;
    private String id_card;
    private int sex;
    private String icon;
    private String true_name;
    private String area;
    private String contact_phone;

    public static UserInfo objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), UserInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<UserInfo> arrayUserInfoFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<UserInfo>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOpenid_qq() {
        return openid_qq;
    }

    public void setOpenid_qq(String openid_qq) {
        this.openid_qq = openid_qq;
    }

    public String getOpenid_weixin() {
        return openid_weixin;
    }

    public void setOpenid_weixin(String openid_weixin) {
        this.openid_weixin = openid_weixin;
    }

    public String getOpenid_sina() {
        return openid_sina;
    }

    public void setOpenid_sina(String openid_sina) {
        this.openid_sina = openid_sina;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public int getBirthday_year() {
        return birthday_year;
    }

    public void setBirthday_year(int birthday_year) {
        this.birthday_year = birthday_year;
    }

    public int getBirthday_mon() {
        return birthday_mon;
    }

    public void setBirthday_mon(int birthday_mon) {
        this.birthday_mon = birthday_mon;
    }

    public int getBirthday_day() {
        return birthday_day;
    }

    public void setBirthday_day(int birthday_day) {
        this.birthday_day = birthday_day;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getContact_addr() {
        return contact_addr;
    }

    public void setContact_addr(String contact_addr) {
        this.contact_addr = contact_addr;
    }

    public String getSell_area() {
        return sell_area;
    }

    public void setSell_area(String sell_area) {
        this.sell_area = sell_area;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "account='" + account + '\'' +
                ", create_time='" + create_time + '\'' +
                ", uuid='" + uuid + '\'' +
                ", openid_qq='" + openid_qq + '\'' +
                ", openid_weixin='" + openid_weixin + '\'' +
                ", openid_sina='" + openid_sina + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", birthday_year=" + birthday_year +
                ", birthday_mon=" + birthday_mon +
                ", birthday_day=" + birthday_day +
                ", qq='" + qq + '\'' +
                ", interest='" + interest + '\'' +
                ", school='" + school + '\'' +
                ", contact_addr='" + contact_addr + '\'' +
                ", sell_area='" + sell_area + '\'' +
                ", parent_id=" + parent_id +
                ", name='" + name + '\'' +
                ", id_card='" + id_card + '\'' +
                ", sex=" + sex +
                ", icon='" + icon + '\'' +
                ", true_name='" + true_name + '\'' +
                ", area='" + area + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                '}';
    }
}
