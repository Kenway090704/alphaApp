package com.alpha.alphaapp.ui.v_1_0.forgetpw;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.v_1_0.login.wx.WxAuthManger;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.alphaapp.model.v_1_0.register.CheckAccoutLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.forgetpw.phone.PhoneGetPwActivity1;
import com.alpha.alphaapp.ui.v_1_0.forgetpw.wx.hasregister.WxGetPwActivityHasRegister1;
import com.alpha.alphaapp.ui.v_1_0.forgetpw.wx.noregister.WxGetPwActivityNoRegister1;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.dialog.CustomLoadingDialog;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

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
                //拉起微信,获取openid,然后判断进入哪个页面
                //正式版的时候
                loginWxAuth();

//                checkAccountIsHas("arg000001012");
            }
        });
    }


    /**
     * 检测帐号是否存在
     */
    private void checkAccountIsHas(final String wx_openid) {
        OnModelCallback<Boolean>  callback=new OnModelCallback<Boolean>() {
            @Override
            public void onModelSuccessed(Boolean aBoolean) {
                if (aBoolean) {
                    WxGetPwActivityHasRegister1.actionStart(ForgetPWGuideActivity.this, wx_openid, null);
                } else {
                    //如果没有帐号进入到没有帐号的页面
                    WxGetPwActivityNoRegister1.actionStart(ForgetPWGuideActivity.this, wx_openid, null);
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e( failedMsg);
            }
        };
        CheckAccoutLogic.checkAccountIsHas(wx_openid, TypeConstants.ACCOUNT_TYPE.AUTH_WECHAT, callback);
    }


    /**
     * 微信授权登录
     */
    public void loginWxAuth() {

        final CustomLoadingDialog loadingDialog = new CustomLoadingDialog(this);

        loadingDialog.show();

        //通过拉起Wx获取Wx的openid,检测该openid是否已经注册,如果未注册
        WxAuthManger.OnWxAuthCallBack callBack = new WxAuthManger.OnWxAuthCallBack() {
            @Override
            public void onAuthSuccessed(String openid, String nickname) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }

                jumpWxgetPw(openid);
            }

            @Override
            public void onAuthFailed(String failedMsg) {

                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                LogUtils.e(failedMsg);
            }
        };
        WxAuthManger.getInstance().doWxAuth(callBack);
    }


    /**
     * 得到wx openid 并进入找回密码页面
     */
    private void jumpWxgetPw(String openid) {

        if (!Util.isNullOrBlank(openid)) {
            checkAccountIsHas(openid);
        }

    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ForgetPWGuideActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }
}
