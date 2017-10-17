package com.alpha.alphaapp.model.v_1_2.logic.login;

import android.content.Context;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.OnModelLoginForumCallback;
import com.alpha.alphaapp.model.v_1_2.bean.ForumLoginSuccessBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.JsonUtil;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.comm.ForumStants;

import com.alpha.lib_stub.comm.URLForum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.alpha.lib_stub.comm.URLForum.FORUM_BASE_POST_URL;

/**
 * Created by kenway on 17/9/19 16:20
 * Email : xiaokai090704@126.com
 */

public class ForumLoginLogic {

    public static final String CODE = "code";
    public static final String INFO = "info";
    public static final String UID = "uid";

    /**
     * 登录论坛
     */
    public static void doLoginForum( final OnModelLoginForumCallback<String> callback) {


        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                /**
                 * {"code":"1001","info":"\u8bf7\u586b\u5199\u6635\u79f0"}----未注册
                 * {"code":"0","info":{"uid":"40219"}}----登录成功
                 */
                JSONObject obj = JsonUtil.stringToJson(result);
                LogUtils.e("doLoginForum==" + result);
                try {
                    String code = obj.getString(CODE);
                    if (code.equals(ForumStants.LOGIN_RESULT.RESULT_NO_REGISTER)) {
                        //未注册,进入设置论坛用户名页面
                        callback.onModelNoRegitser();

                    } else if (code.equals(ForumStants.LOGIN_RESULT.RESULT_OK)) {
                        //登录成功
                        JSONObject obj_info = obj.getJSONObject(INFO);

                        //将登录的UID保存到UID
                        AccountManager.getInstance().setUid(obj_info.getString(UID));

                        if (!Util.isNull(callback))
                            callback.onModelSuccessed(obj_info.getString(UID));

                    } else {
                        String msg = obj.getString(INFO);
                        if (!Util.isNull(callback))
                            callback.onModelFailed(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callback))
                    callback.onModelFailed(errorMsg);

            }
        };

        //post数据
        HashMap<String, String> map = ForumNetPostUtil.initBaseMap()
                .addMethod(URLForum.FORUM_METHOD.FORUM_LOGIN)
                .addSskey(AccountManager.getInstance().getSskey())
                .getMap();


        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);

    }


    /**
     * 登录论坛
     */
    public static void doSetForumName(final Context context, String name, final OnModelCallback<String> callback) {


        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                LogUtils.e(result);
                /**
                 * {"code":"0","info":{"uid":"42710"}}
                 */
                JSONObject obj = JsonUtil.stringToJson(result);
                try {
                    String code = obj.getString(CODE);
                    if (code.equals(ForumStants.LOGIN_RESULT.RESULT_NO_REGISTER)) {
                        //重新设置
                    } else if (code.equals(ForumStants.LOGIN_RESULT.RESULT_OK)) {
                        ForumLoginSuccessBean loginBean = JsonUtil.jsonToBean(result, ForumLoginSuccessBean.class);
                        //注册成功

                        //将注册成功后的论坛UID保存到AccountManager
                        AccountManager.getInstance().setUid(loginBean.getInfo().getUid());
                        if (!Util.isNull(callback))
                            callback.onModelSuccessed(loginBean.getInfo().getUid());
                    } else {

                        String msg = obj.getString(INFO);
                        if (!Util.isNull(callback))
                            callback.onModelFailed(msg);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(callback))
                    callback.onModelFailed(errorMsg);

            }
        };

        //post数据
        HashMap<String, String> map = ForumNetPostUtil.initBaseMap()
                .addMethod(URLForum.FORUM_METHOD.FORUM_SET_NAME)
                .addUserName(name)
                .addSskey(AccountManager.getInstance().getSskey())
                .getMap();
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(FORUM_BASE_POST_URL, map, call);
    }


}
