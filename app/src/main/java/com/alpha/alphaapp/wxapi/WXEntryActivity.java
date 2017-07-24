package com.alpha.alphaapp.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.app.MyApplication;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by kenway on 17/6/7 12:44
 * Email : xiaokai090704@126.com
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler, WxAccessTokenResp {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WXManager.instance().handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        //微信发送的请求将回调到onReq方法

    }

    @Override
    public void onResp(BaseResp resp) {
        //发送到微信请求的响应结果将回调到onResp方法
        int result = 0;
        String strMsg;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                if (resp instanceof SendAuth.Resp) {
                    SendAuth.Resp aures = (SendAuth.Resp) resp;
                    String code = aures.code;
                    //微信授权返回结果，处理授权后操作,获取微信token
                    strMsg=getResources().getString( R.string.errcode_success);
                    WXManager.instance().getAccessToken(code,this,strMsg);
                    return;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                strMsg=getResources().getString( R.string.errcode_cancel);
                if(resp instanceof  SendAuth.Resp)
                    WXManager.instance().getAccessToken(null,this,strMsg);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                strMsg=getResources().getString( R.string.errcode_deny);
                if(resp instanceof  SendAuth.Resp)
                    WXManager.instance().getAccessToken(null, this,strMsg);
                break;
            default:
                result = R.string.errcode_unknown;
                strMsg=getResources().getString( R.string.errcode_unknown);
                if(resp instanceof  SendAuth.Resp)
                    WXManager.instance().getAccessToken(null, this,strMsg);
                break;
        }
        finish();
        ToastUtils.showShort(WXEntryActivity.this, result);
    }

    @Override
    public void onSuccess(WxAccessTokenInfo wxAccessTokenInfo) {
        MyApplication.getIns().setWxAccessTokenInfo(wxAccessTokenInfo);
        finish();
    }

    @Override
    public void onFailed(String statusCode) {
        ToastUtils.showShort(WXEntryActivity.this, R.string.net_error_text);
        finish();
    }
}
