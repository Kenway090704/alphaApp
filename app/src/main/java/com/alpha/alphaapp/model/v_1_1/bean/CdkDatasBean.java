package com.alpha.alphaapp.model.v_1_1.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/20 18:35
 * Email : xiaokai090704@126.com
 */

public class CdkDatasBean {

    private static final String TAG = "CdkDatasBean";
    private static final String KEY_CDK_INFO = "cdk_info";
    public static final String KEY_CDK_DATAS = "cdk_datas";

    private String lot_number;
    private List<CdkItemsBean> cdk_items;

    public static CdkDatasBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), CdkDatasBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param str
     * @param key -- cdk_datas
     * @return
     */
    public static List<CdkDatasBean> arrayCdkDatasBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            jsonObject = jsonObject.getJSONObject(KEY_CDK_INFO);
            Type listType = new TypeToken<ArrayList<CdkDatasBean>>() {
            }.getType();
            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getLot_number() {
        return lot_number;
    }

    public void setLot_number(String lot_number) {
        this.lot_number = lot_number;
    }

    public List<CdkItemsBean> getCdk_items() {
        return cdk_items;
    }

    public void setCdk_items(List<CdkItemsBean> cdk_items) {
        this.cdk_items = cdk_items;
    }

    public static class CdkItemsBean {


        private String lot_number;
        private String code;
        private int status;
        private String active_time;
        private int channel_id;

        @Override
        public String toString() {
            return "CdkItemsBean{" +
                    "lot_number='" + lot_number + '\'' +
                    ", code='" + code + '\'' +
                    ", status=" + status +
                    ", active_time='" + active_time + '\'' +
                    ", channel_id=" + channel_id +
                    '}';
        }

        public static CdkItemsBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), CdkItemsBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<CdkItemsBean> arrayCdkItemsBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<CdkItemsBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getActive_time() {
            return active_time;
        }

        public void setActive_time(String active_time) {
            this.active_time = active_time;
        }

        public int getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(int channel_id) {
            this.channel_id = channel_id;
        }

        public String getLot_number() {
            return lot_number;
        }

        public void setLot_number(String lot_number) {
            this.lot_number = lot_number;
        }
    }

    @Override
    public String toString() {
        return "CdkDatasBean{" +
                "lot_number='" + lot_number + '\'' +
                ", cdk_items=" + cdk_items +
                '}';
    }
}
