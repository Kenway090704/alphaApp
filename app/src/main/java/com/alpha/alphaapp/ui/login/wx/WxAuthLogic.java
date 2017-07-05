package com.alpha.alphaapp.ui.login.wx;

import com.alpha.alphaapp.ui.login.qq.QQLoginManager;
import com.alpha.alphaapp.wxapi.WXManager;
import com.alpha.alphaapp.wxapi.WechatAuthCallBack;
import com.alpha.alphaapp.wxapi.WxAccessTokenInfo;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by kenway on 17/7/3 15:15
 * Email : xiaokai090704@126.com
 * 微信获取openId
 */

public class WxAuthLogic {

    private static final String TAG = "WxAuthLogic";
    private static WxAuthLogic manager;
    private QQLoginManager.OnQQAuthLoginCallBack logincall;

    private WxAuthLogic() {

    }

    /**
     * 获取qq登录单例模式
     *
     * @return
     */
    public static WxAuthLogic getInstance() {
        WxAuthLogic inst = manager;
        if (inst == null) {
            synchronized (QQLoginManager.class) {
                inst = manager;
                if (inst == null) {
                    inst = new WxAuthLogic();
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
                    Log.e(TAG, info.toString());
                    String wxopenid = info.getOpenId();
                    if (!Util.isNullOrBlank(wxopenid)) {
                        if (!Util.isNull(back))
                            back.onAuthSuccessed(wxopenid);
                    } else {
                        if (!Util.isNull(back))
                            back.onAuthFailed("无法获取微信Openid");
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
            Log.e(TAG, "发送请求到Wx");
            WXManager.instance().sendReq(req, callBack);
        } else {

            if (!Util.isNull(back))
                back.onAuthFailed("微信未安装");

        }
    }

    public interface OnWxAuthCallBack {
        void onAuthSuccessed(String openid);

        void onAuthFailed(String failedMsg);
    }
}
