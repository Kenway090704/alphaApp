package com.alpha.alphaapp.model.geticons;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/6/15 10:04
 * Email : xiaokai090704@126.com
 */

public class GetIconBean {

    private int result;
    private String msg;
    private IconListBean icon_list;

    public static GetIconBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), GetIconBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<GetIconBean> arrayGetIconBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<GetIconBean>>() {
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

    public IconListBean getIcon_list() {
        return icon_list;
    }

    public void setIcon_list(IconListBean icon_list) {
        this.icon_list = icon_list;
    }

    public static class IconListBean {
        private String icon_url;
        private List<CategoryBean> category;

        public static IconListBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), IconListBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<IconListBean> arrayIconListBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<IconListBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class CategoryBean {
            private String name;
            private String fold;
            private List<String> icons;
            /**
             * 判断是否选中
             */
            private boolean isSelected;

            public static CategoryBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), CategoryBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<CategoryBean> arrayCategoryBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<CategoryBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFold() {
                return fold;
            }

            public void setFold(String fold) {
                this.fold = fold;
            }

            public List<String> getIcons() {
                return icons;
            }

            public void setIcons(List<String> icons) {
                this.icons = icons;
            }

            @Override
            public String toString() {
                return "CategoryBean{" +
                        "name='" + name + '\'' +
                        ", fold='" + fold + '\'' +
                        ", icons=" + icons +
                        '}';
            }
        }
    }
}
