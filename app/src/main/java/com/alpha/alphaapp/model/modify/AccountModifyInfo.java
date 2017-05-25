package com.alpha.alphaapp.model.modify;

import com.alpha.alphaapp.comm.CommStants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/5/23 15:19
 * Email : xiaokai090704@126.com
 * 一般用户信息修改
 */

public class AccountModifyInfo {

    private String sskey;
    private UserInfoBean user_info;
    private String user_ip;
    private String terminal_type;

    public static AccountModifyInfo objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), AccountModifyInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<AccountModifyInfo> arrayAccountModifyInfoFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<AccountModifyInfo>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getSskey() {
        return sskey;
    }

    public void setSskey(String sskey) {
        this.sskey = sskey;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
    }

    public static class UserInfoBean {
        private int sex;
        private String city;
        private String icon;
        private String name;
        private String true_name;

        public static UserInfoBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), UserInfoBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<UserInfoBean> arrayUserInfoBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<UserInfoBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTrue_name() {
            return true_name;
        }

        public void setTrue_name(String true_name) {
            this.true_name = true_name;
        }
    }

    /**
     * 修改用户信息的json数据
     *<p>
     *  {"sskey":"a8cdedfe4c4e8ad08ade919ef1be1957","user_info":{"sex":1, "city":"广州" ,"icon":"401", "name":"sam", "true_name":"李某"}, "user_ip":"187.12.33.44","terminal_type":"PC"}
     *</>
     * @return
     */
    public String getJsonStrModifyUserInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + getSskey() + "\",")
                      .append("\"user_info\":{")
                      .append("\"sex\":").append(getUser_info().getSex() + ",")
                      .append("\"city\":").append("\"" + getUser_info().getCity() + "\",")
                      .append("\"icon\":").append("\"" + getUser_info().getIcon() + "\",")
                      .append("\"name\":").append("\"" + getUser_info().getName() + "\",")
                      .append("\"true_name\":").append("\"" + getUser_info().getTrue_name() + "\"")
                      .append("},")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.AUTH + ",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + getTerminal_type() + "\"")
                .append("}");
        return sb.toString();
    }

}
