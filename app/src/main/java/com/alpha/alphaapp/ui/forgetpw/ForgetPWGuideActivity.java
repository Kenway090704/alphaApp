package com.alpha.alphaapp.ui.forgetpw;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.model.check.CheckAccoutLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.forgetpw.phone.PhoneGetPwActivity1;
import com.alpha.alphaapp.ui.forgetpw.wx.hasregister.WxGetPwActivityHasRegister1;
import com.alpha.alphaapp.ui.forgetpw.wx.noregister.WxGetPwActivityNoRegister1;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.wxapi.WXManager;
import com.alpha.alphaapp.wxapi.WechatAuthCallBack;
import com.alpha.alphaapp.wxapi.WxAccessTokenInfo;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by kenway on 17/6/6 14:20
 * Email : xiaokai090704@126.com
 */

public class ForgetPWGuideActivity extends BaseActivity {
    private static final String TAG = "ForgetPWGuideActivity";
    private LinearLayout layout_phone, layout_wechat;
    private TitleLayout titlelayout;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pw_guide;
    }

    @Override
    protected void initView() {

        titlelayout = (TitleLayout) findViewById(R.id.forget_pw_guide_titlelayout);
        titlelayout.setTitleText(R.string.find_pw);
        layout_phone = (LinearLayout) findViewById(R.id.forget_pw_guide_btn_phone);
        layout_wechat = (LinearLayout) findViewById(R.id.forget_pw_guide_btn_weChat);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        layout_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneGetPwActivity1.actionStart(ForgetPWGuideActivity.this, null, null);
            }
        });
        layout_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAccountIsHas("AFGHR9011");
                //正式版的时候
//                getWxOpenid();
            }
        });
    }

    /**
     * 检测帐号是否存在
     */
    private void checkAccountIsHas(final String wx_openid) {
        CheckAccoutLogic.OnCheckAccountListener listener = new CheckAccoutLogic.OnCheckAccountListener() {
            @Override
            public void checkSucessed(boolean isHas, String result) {
                if (isHas) {
                    WxGetPwActivityHasRegister1.actionStart(ForgetPWGuideActivity.this, wx_openid, null);
                } else {
                    //如果没有帐号进入到没有帐号的页面
                    WxGetPwActivityNoRegister1.actionStart(ForgetPWGuideActivity.this, wx_openid, null);
                }
            }

            @Override
            public void checkFailed(String errorMsg) {

            }
        };
        CheckAccoutLogic.checkAccountIsHas(wx_openid, CommStants.ACCOUNT_TYPE.AUTH_WECHAT, listener);
    }

    /**
     * 微信授权登录
     */
    private void getWxOpenid() {
        if (WXManager.instance().isWXAppInstalled()) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            WechatAuthCallBack callBack = new WechatAuthCallBack() {

                @Override
                public void onAuthSuccess(WxAccessTokenInfo info) {
                    jumpWxgetPw(info);
                }

                @Override
                public void onAuthFailed(String errmsg) {

                }
            };
            //拉起微信授权，授权结果在WXEntryActivity中接收处理
            WXManager.instance().sendReq(req, callBack);
        } else {
            ToastUtils.showShort(ForgetPWGuideActivity.this, R.string.wechat_not_installed);
        }
    }

    /**
     * 得到wx openid 并进入找回密码页面
     */
    private void jumpWxgetPw(WxAccessTokenInfo info) {
        String wxopenid = info.getOpenId();
        if (!Util.isNullOrBlank(wxopenid)) {
            checkAccountIsHas(wxopenid);
        }

    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ForgetPWGuideActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }
}
