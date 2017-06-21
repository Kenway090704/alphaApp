package com.alpha.alphaapp.ui.forgetpw.wx.hasregister;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.login.LoginLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.forgetpw.wx.noregister.WxGetPwActivityNoAccount;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.dialog.CustomLoadingDialog;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/20 14:12
 * Email : xiaokai090704@126.com
 */

public class WxGetPwActivityHasRegister1 extends BaseActivity {
    private static final String TAG = "WxGetPwActivityHasRegister1";
    private TitleLayout titleLayout;
    private TextView tv_msg;
    private Button btn_loginset;
    private CustomLoadingDialog loadingDialog;
    private String wx_openid;

    @Override
    protected int getLayoutId() {
        wx_openid = getIntent().getStringExtra("wx_openid");
        return R.layout.activity_wx_getpw_has_register_1;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.wx_getpw_has_register_1_titlelayout);
        tv_msg = (TextView) findViewById(R.id.wx_getpw_has_register_1_tv);
        btn_loginset = (Button) findViewById(R.id.wx_getpw_has_register_1_btn_loginset);
        loadingDialog = new CustomLoadingDialog(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        btn_loginset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用该微信open_id 进行登录,登录后判断是否有英文帐号,再判断进入到哪个界面
                LoginLogic.OnLoginListener listener = new LoginLogic.OnLoginListener() {
                    @Override
                    public void onLoginSuccessed() {
                        UserInfo info = AccountManager.getInstance().getUserInfo();
                        if (!Util.isNullOrBlank(info.getAccount())) {
                            WxGetPwActivityHasAccount.actionStart(WxGetPwActivityHasRegister1.this, null, null);
                        } else {
                            //进入到创建英文帐号的界面
                            WxGetPwActivityNoAccount.actionStart(WxGetPwActivityHasRegister1.this);
                        }
                    }

                    @Override
                    public void onLoginFailed(String errorMsg) {

                    }
                };
                LoginLogic.doLogin(wx_openid, null, TypeConstants.LOGIN_TYPE.AUTH_WX, listener);


            }
        });

    }

    public static void actionStart(Context context, String wx_openid, String data2) {
        Intent intent = new Intent(context, WxGetPwActivityHasRegister1.class);
        intent.putExtra("wx_openid", wx_openid);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

}
