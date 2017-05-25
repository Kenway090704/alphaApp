package com.alpha.alphaapp.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.alpha.alphaapp.model.login.LoginInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * 比赛数据存储类
 * gson把List转换成json类型，再利用SharedPreferences保存的。
 * Created by kenway on 16/12/19 14:51
 * Email : xiaokai090704@126.com
 */
public class SharePLoginInfo {

    private static final String TAG = "SharePLoginInfo";

    private static final String SHARED_PREFES_NAME = "userinfo-data";
    private static final String SHARED_TAG__NAME = "userinfo";
    private static SharePLoginInfo pre;
    private static Context mContext;
    private SharedPreferences preferencesData;
    private SharedPreferences.Editor editorData;

    /**
     * 获取单例
     *
     * @return
     */
    public static SharePLoginInfo getInstance(Context context) {
        mContext = context;
        if (null == pre) {
            synchronized (SharePLoginInfo.class) {
                pre = new SharePLoginInfo(mContext, SHARED_PREFES_NAME);
            }
        }
        return pre;
    }

    /**
     * 初始化这个管理类
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * @param mContext       上下文对象
     * @param preferenceName sharePerference的名字
     */
    private SharePLoginInfo(Context mContext, String preferenceName) {
        preferencesData = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editorData = preferencesData.edit();
    }


    /**
     * 保存某一场比赛的结果与信息
     *
     * @param com 当前场次比赛
     */
    public void saveLoginInfo(LoginInfo com) {
        if (null == com) {
            return;
        }
        Gson gson = new Gson();
        //转换城json数据,再保存
        String strJson = gson.toJson(com);
        editorData.putString(SHARED_TAG__NAME, strJson);
        editorData.commit();
    }

    /**
     * 获取所有比赛的对象
     *
     * @param
     * @return
     */
    public LoginInfo getLoginInfo() {

        String strJson = preferencesData.getString(SHARED_TAG__NAME, null);
        if (null == strJson) {
            return null;
        }
        Type listType = new TypeToken<LoginInfo>() {
        }.getType();
        Gson gson = new Gson();
        LoginInfo loginInfo = gson.fromJson(strJson, listType);

        return loginInfo;
    }

    /**
     * 清空所有比赛数据
     */
    public void clear() {
        LoginInfo loginInfo = new LoginInfo();
        Gson gson = new Gson();
        //转换城json数据,再保存
        String strJson = gson.toJson(loginInfo);
        editorData.putString(SHARED_TAG__NAME, strJson);
        editorData.commit();
    }


}