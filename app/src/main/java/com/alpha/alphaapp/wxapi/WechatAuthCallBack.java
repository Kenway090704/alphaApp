package com.alpha.alphaapp.wxapi;

/**
 * Created by kenway on 17/6/7 16:16
 * Email : xiaokai090704@126.com
 * 发起微信授权登录的接口回调
 */

public interface WechatAuthCallBack {
    /**
     * 返回一个授权信息实体类包含opneid,access_token
     * <p>
     *{
     "access_token":"ACCESS_TOKEN",
     "expires_in":7200,
     "refresh_token":"REFRESH_TOKEN",
     "openid":"OPENID",
     "scope":"SCOPE",
     "unionid":"o6_bmasdasdsad6_2sgVt7hMZOPfL"
     }
     * </>
     * @param info
     */
    void onAuthSuccess(WxAccessTokenInfo info);

    void onAuthFailed(String errmsg);
}
