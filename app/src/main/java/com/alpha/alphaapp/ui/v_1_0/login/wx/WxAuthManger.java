package com.alpha.alphaapp.ui.v_1_0.login.wx;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.ui.v_1_0.login.qq.QQLoginManager;
import com.alpha.alphaapp.wxapi.WXManager;
import com.alpha.alphaapp.wxapi.WechatAuthCallBack;
import com.alpha.alphaapp.wxapi.WxAccessTokenInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kenway on 17/7/3 15:15
 * Email : xiaokai090704@126.com
 * 微信获取openId
 */

public class WxAuthManger {

    private static final String TAG = "WxAuthManger";
    private static WxAuthManger manager;
    private QQLoginManager.OnQQAuthLoginCallBack logincall;

    private WxAuthManger() {

    }

    /**
     * 获取qq登录单例模式
     *
     * @return
     */
    public static WxAuthManger getInstance() {
        WxAuthManger inst = manager;
        if (inst == null) {
            synchronized (QQLoginManager.class) {
                inst = manager;
                if (inst == null) {
                    inst = new WxAuthManger();
                    manager = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 微信授权,也就是获取Wx--Openid
     */
    public void doWxAuth(final OnWxAuthCallBack back) {
        if (WXManager.instance().isWXAppInstalled()) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            WechatAuthCallBack callBack = new WechatAuthCallBack() {

                @Override
                public void onAuthSuccess(WxAccessTokenInfo info) {
                    if (!Util.isNullOrBlank(info.getOpenId())) {
                        getWxUserInfo(info, back);
                        Log.e(TAG, info.getOpenId());
                    } else {
                        if (!Util.isNull(back))
                            back.onAuthFailed("无法获取微信Openid");
                        Log.e(TAG, "无法获取微信Openid");
                    }

                }

                @Override
                public void onAuthFailed(String errmsg) {
                    if (!Util.isNull(back))
                        back.onAuthFailed(errmsg);

                    Log.e(TAG, errmsg);
                }
            };
            //拉起微信授权，授权结果在WXEntryActivity中接收处理
            WXManager.instance().sendReq(req, callBack);
        } else {
            if (!Util.isNull(back))
                back.onAuthFailed("微信未安装");
        }
    }

    private void getWxUserInfo(final WxAccessTokenInfo info, final OnWxAuthCallBack back) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                .append(info.getAccess_token())
                .append("&openid=")
                .append(info.getOpenId());
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                try {
                    //解析获取的信息
                    JSONObject obj = new JSONObject(result);
                    String nickname = obj.optString("nickname");
                    int sex = obj.optInt("sex");//1--男
                    String icon = obj.optString("headimgurl");

                    if (!Util.isNull(back)) {
                        AccountManager.getInstance().setAuthNickName(nickname);
                        back.onAuthSuccessed(info.getOpenId(), nickname);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(back))
                    back.onAuthFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestGetWXData(sb.toString(), callBack);
    }

    public interface OnWxAuthCallBack {
        void onAuthSuccessed(String openid, String nickname);

        void onAuthFailed(String failedMsg);
    }
}
