package com.alpha.alphaapp.account;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/5 14:35
 * Email : xiaokai090704@126.com
 */

public class SignInfo {


    private int result;
    private String msg;
    private int sign_days;
    private int sign_nums;
    private List<SignRecordListBean> sign_record_list;

    public static SignInfo objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), SignInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从字符串中获取对象
     *
     * @param str
     * @return
     */
    public static SignInfo objectFromData(String str) {
        SignInfo info = new SignInfo();
        try {
            JSONObject jsonObject = new JSONObject(str);
            int result = jsonObject.getInt("result");
            String msg = jsonObject.getString("msg");
            int sign_days = jsonObject.getInt("sign_days");
            int sign_nums = jsonObject.getInt("sign_nums");
            List<SignRecordListBean> list = SignRecordListBean.arraySignRecordListBeanFromData(str, "sign_record_list");
            info.setResult(result);
            info.setMsg(msg);
            info.setSign_days(sign_days);
            info.setSign_nums(sign_nums);
            info.setSign_record_list(list);
            return info;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return info;
    }



    public static SignInfo parseData(String result) {
        //Gson 解析
       SignInfo info = new SignInfo();
        try {
            JSONObject data = new JSONObject(result);
            Gson gson = new Gson();
            gson.fromJson(data.toString(),SignInfo.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }
    public static List<SignInfo> arraySignInfoFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SignInfo>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSign_days() {
        return sign_days;
    }

    public void setSign_days(int sign_days) {
        this.sign_days = sign_days;
    }

    public int getSign_nums() {
        return sign_nums;
    }

    public void setSign_nums(int sign_nums) {
        this.sign_nums = sign_nums;
    }

    public List<SignRecordListBean> getSign_record_list() {
        return sign_record_list;
    }

    public void setSign_record_list(List<SignRecordListBean> sign_record_list) {
        this.sign_record_list = sign_record_list;
    }

    public static class SignRecordListBean {
        private int id;
        private String pid;
        private String p_img;
        private String p_title;
        private int sign_nums;
        private String create_time;

        @Override
        public String toString() {
            return "SignRecordListBean{" +
                    "id=" + id +
                    ", pid='" + pid + '\'' +
                    ", p_img='" + p_img + '\'' +
                    ", p_title='" + p_title + '\'' +
                    ", sign_nums=" + sign_nums +
                    ", create_time='" + create_time + '\'' +
                    '}';
        }

        public static SignRecordListBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), SignRecordListBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<SignRecordListBean> arraySignRecordListBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<SignRecordListBean>>() {
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

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getP_img() {
            return p_img;
        }

        public void setP_img(String p_img) {
            this.p_img = p_img;
        }

        public String getP_title() {
            return p_title;
        }

        public void setP_title(String p_title) {
            this.p_title = p_title;
        }

        public int getSign_nums() {
            return sign_nums;
        }

        public void setSign_nums(int sign_nums) {
            this.sign_nums = sign_nums;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    @Override
    public String toString() {
        return "SignInfo{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", sign_days=" + sign_days +
                ", sign_nums=" + sign_nums +
                ", sign_record_list=" + sign_record_list +
                '}';
    }
}
