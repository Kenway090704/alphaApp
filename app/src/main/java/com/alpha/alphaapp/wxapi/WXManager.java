/**
 * Project Name:WechatLoginDemo
 * File Name:WXManager.java
 * Package Name:com.example.chenzhengjun.wechatlogindemo
 * Date:11/5/2015
 * Copyright (c) 2015, iczjun@gmail.com All Rights Reserved.
 */
package com.alpha.alphaapp.wxapi;

import android.content.Intent;

import com.alpha.alphaapp.app.MyApplication;
import com.alpha.alphaapp.ui.login.*;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


/**
 * 微信登录关联类
 */
public class WXManager {
    private static final String TAG = "WXManager";
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?";
    private static final String URL_REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";
    private static WXManager mWXManager = new WXManager();
    private IWXAPI api;
    //微信授权后回调的信息
    private WechatAuthCallBack wxcallBack;

    public static WXManager instance() {
        return mWXManager;
    }

    /**
     * @param api the api to set
     */
    public void setApi(IWXAPI api) {
        this.api = api;
    }


    public boolean handleIntent(Intent arg0, IWXAPIEventHandler arg1) {
        if (api == null)
            return false;
        return api.handleIntent(arg0, arg1);
    }

    public boolean isWXAppInstalled() {
        if (api == null)
            return false;
        return api.isWXAppInstalled();
    }


    public boolean sendReq(BaseReq arg0, WechatAuthCallBack callBack) {
        if (api == null)
            return false;
        if (callBack != null) {
            this.wxcallBack = callBack;
        }
        return api.sendReq(arg0);
    }

    /**
     * 获取WxAccessToken
     *
     * @param code
     * @param resp
     */
    public void getAccessToken(String code, final WxAccessTokenResp resp) {
        //网络请求获取WxAccessToken
        final StringBuilder sb = new StringBuilder();
        sb.append(URL_ACCESS_TOKEN).append("appid=")
                .append(MyApplication.WX_APP_ID).append("&secret=")
                .append(MyApplication.WX_APP_SECRET).append("&code=")
                .append(code).append("&grant_type=authorization_code");

        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                //添加判读,是否为错误返回{"errcode":40029,"errmsg":"invalid code"}
                if (!result.contains("errcode")) {
                    WxAccessTokenInfo wxAccessTokenInfo = WxAccessTokenInfo.getWxAccessTokenFromJsonStr(result);
                    if (resp != null) {
                        wxcallBack.onAuthSuccess(wxAccessTokenInfo);
                        resp.onSuccess(wxAccessTokenInfo);
                    }
                } else {
                    if (resp != null) {
                        resp.onFailed("请求错误");
                    }
                    if (wxcallBack != null) {
                        wxcallBack.onAuthFailed("invalid code");
                    }
                    Log.e(TAG, result);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestGetWXData(sb.toString(), callBack);
    }

    /***
     * 重新获取WxAccessToken
     *
     * @param resp
     */
    public void refreshAccessToken(final WxAccessTokenResp resp) {
        StringBuilder sb = new StringBuilder();
        sb.append(URL_REFRESH_TOKEN).append("appid=")
                .append(MyApplication.WX_APP_ID)
                .append("&grant_type=refresh_token&refresh_token=REFRESH_TOKEN");
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                //添加判读,是否为错误返回{"errcode":40029,"errmsg":"invalid code"}
                if (!result.contains("errcode")) {
                    WxAccessTokenInfo wxAccessTokenInfo = WxAccessTokenInfo.getRefrestWxAccessTokenFromJsonStr(result);
                    if (resp != null) {
                        wxcallBack.onAuthSuccess(wxAccessTokenInfo);
                        resp.onSuccess(wxAccessTokenInfo);
                    }
                } else {
                    if (resp != null) {
                        resp.onFailed("请求错误");
                    }
                    if (wxcallBack != null) {
                        wxcallBack.onAuthFailed("invalid code");
                    }
                    Log.e(TAG, result);
                }

                WxAccessTokenInfo wxAccessTokenInfo = WxAccessTokenInfo.getWxAccessTokenFromJsonStr(result);
                resp.onSuccess(wxAccessTokenInfo);


            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestGetWXData(sb.toString(), callBack);
    }

    /**
     * 得到用户信息,暂时未使用到
     *
     * @param resp
     */
    public void getWxUserInfo(final WxAccessTokenResp resp) {

        StringBuilder sb = new StringBuilder();
        sb.append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                .append(MyApplication.getIns().getWxAccessTokenInfo().access_token)
                .append("&openid=")
                .append(MyApplication.getIns().getWxAccessTokenInfo().getOpenId());
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                //如果返回错误的时候则不可以执行
//                WxAccessTokenInfo wxAccessTokenInfo = WxAccessTokenInfo.getWxAccessTokenFromJsonStr(result);
//                resp.onSuccess(wxAccessTokenInfo);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestGetWXData(sb.toString(), callBack);
    }
}
