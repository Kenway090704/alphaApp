package com.alpha.alphaapp.ui.set;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.bind.BindLogic;
import com.alpha.alphaapp.model.check.CheckAccoutLogic;
import com.alpha.alphaapp.ui.AccountChangeActivity;
import com.alpha.alphaapp.ui.bind.account.EnAccountSetingHasPwActivity;
import com.alpha.alphaapp.ui.bind.account.EnAccountSetingNoPwActivity;
import com.alpha.alphaapp.ui.bind.phone.change.ChangePhoneBindActvity1;
import com.alpha.alphaapp.ui.bind.phone.first.NewPhoneBindActvity1;
import com.alpha.alphaapp.ui.login.qq.QQLoginManager;
import com.alpha.alphaapp.ui.login.wx.WxAuthLogic;
import com.alpha.alphaapp.ui.modifypw.phone.ModifyPwByPhoneActivity1;
import com.alpha.alphaapp.ui.modifypw.ModifyPwByPwActivity;
import com.alpha.alphaapp.ui.widget.dialog.CustomLoadingDialog;
import com.alpha.alphaapp.ui.widget.set.AccountBindItemView;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.wxapi.WxAccessTokenInfo;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * 帐号和绑定设置界面
 */
public class AccountSecurityActivity extends AccountChangeActivity {
    private static final String TAG = "AccountSecurityActivity";
    private TitleLayout titleLayout;

    private TextView tv_curent;

    private AccountBindItemView abi_alpha, abi_phone, abi_wx, abi_qq, abi_pweditpw, abi_phoneeditpw;

    private UserInfo info;

    @Override
    protected int getLayoutId() {
        info = AccountManager.getInstance().getUserInfo();
        return R.layout.activity_account_security;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.account_sec_titlelayout);
        titleLayout.setTitleText(R.string.settings_account_bind_set);
        tv_curent = (TextView) findViewById(R.id.account_sec_tv_current);
        abi_alpha = (AccountBindItemView) findViewById(R.id.account_sec_abi_alpha_acc);
        abi_phone = (AccountBindItemView) findViewById(R.id.account_sec_abi_phone);
        abi_wx = (AccountBindItemView) findViewById(R.id.account_sec_abi_weixin);
        abi_qq = (AccountBindItemView) findViewById(R.id.account_sec_abi_qq);
        abi_pweditpw = (AccountBindItemView) findViewById(R.id.account_sec_abi_pweditpw);
        abi_phoneeditpw = (AccountBindItemView) findViewById(R.id.account_sec_abi_phoneeditpw);
        setCurrentAccount();
    }

    /**
     * 设置当前帐号信息
     */
    private void setCurrentAccount() {
        // 判断是登录的类型,然后选择对应的帐号显示
        String format = getResources().getString(R.string.current_account) + "   ";
        int accountType = AccountManager.getInstance().getLoginType();
        switch (AccountManager.getInstance().getLoginType()) {
            case TypeConstants.LOGIN_TYPE.PHONE_QUICK:
                tv_curent.setText(format + info.getMobile());
                break;
            case TypeConstants.LOGIN_TYPE.PHONE_PW:
                tv_curent.setText(format + info.getMobile());
                break;
            case TypeConstants.LOGIN_TYPE.ACCONUT_PW:
                tv_curent.setText(format + info.getAccount());
                break;
            case TypeConstants.LOGIN_TYPE.AUTH_WX:
                tv_curent.setText(format + AccountManager.getInstance().getAuthNickName());
                break;
            case TypeConstants.LOGIN_TYPE.AUTH_QQ:
                tv_curent.setText(format + AccountManager.getInstance().getAuthNickName());
                break;
        }
        setUIData(info);
    }

    private void setUIData(UserInfo info) {
        //是否有帐号信息,没有的话显示立即设置,有的话,已绑定
        if (!Util.isNullOrBlank(info.getAccount())) {
            abi_alpha.setMsg(info.getAccount());
            abi_alpha.setRightTxtAndBg("已绑定", R.drawable.shape_btn_bg_gray);
        } else {
            abi_alpha.setRightTxtAndBg("立即设置", R.drawable.shape_btn_bg_blue);
        }
        //信息中没有绑定手机号
        if (!Util.isNullOrBlank(info.getMobile())) {
            abi_phone.setMsg(info.getMobile());
            abi_phone.setRightTxtAndBg("更改绑定", R.drawable.shape_btn_bg_blue);
            abi_phoneeditpw.setRightTxtAndBg("修改", R.drawable.shape_btn_bg_blue);
        } else {
            abi_phone.setRightTxtAndBg("+ 绑定", R.drawable.shape_btn_bg_blue);
            abi_phoneeditpw.setRightTxtAndBg("未绑定手机", R.drawable.shape_btn_bg_gray);
        }
        //微信这里要使用getName,现在使用openid
        if (!Util.isNullOrBlank(info.getOpenid_weixin())) {
            abi_wx.setMsg(info.getOpenid_weixin());
            abi_wx.setRightTxtAndBg("已绑定", R.drawable.shape_btn_bg_gray);
        } else {
            abi_wx.setRightTxtAndBg("+ 绑定", R.drawable.shape_btn_bg_blue);
        }
        if (!Util.isNullOrBlank(info.getOpenid_qq())) {
            abi_qq.setMsg(info.getOpenid_qq());
            abi_qq.setRightTxtAndBg("已绑定", R.drawable.shape_btn_bg_gray);
        } else {
            abi_qq.setRightTxtAndBg("+ 绑定", R.drawable.shape_btn_bg_blue);
        }
        if (!Util.isNullOrBlank(info.getMobile()) || !Util.isNullOrBlank(info.getAccount())) {
            abi_pweditpw.setRightTxtAndBg("修改", R.drawable.shape_btn_bg_blue);
        } else {
            abi_pweditpw.setRightTxtAndBg("未绑定帐号", R.drawable.shape_btn_bg_gray);
        }
    }

    @Override
    public void initData() {
        //获取当前帐号的信息

    }

    @Override
    protected void initListener() {

        abi_alpha.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loginType = AccountManager.getInstance().getLoginType();
                //如果是第三方授权登录,判断是否已经设置了英文帐号,如果没有设置,进入设置页面
                if (loginType == TypeConstants.LOGIN_TYPE.AUTH_QQ || loginType == TypeConstants.LOGIN_TYPE.AUTH_WX || loginType == TypeConstants.LOGIN_TYPE.PHONE_QUICK) {
                    if (Util.isNullOrBlank(info.getAccount()))
                        EnAccountSetingNoPwActivity.actionStart(AccountSecurityActivity.this, null, null);
                } else if (loginType == TypeConstants.LOGIN_TYPE.PHONE_PW) {
                    //手机+密码登录,如果没有设置,进入设置页面
                    if (Util.isNullOrBlank(info.getAccount()))
                        EnAccountSetingHasPwActivity.actionStart(AccountSecurityActivity.this, null, null);
                }
            }
        });
        abi_phone.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNullOrBlank(info.getMobile())) {
                    ChangePhoneBindActvity1.actionStart(AccountSecurityActivity.this, null, null);
                } else {
                    NewPhoneBindActvity1.actionStart(AccountSecurityActivity.this, null, null);
                }
            }
        });
        abi_wx.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNullOrBlank(info.getOpenid_weixin())) {
                    loginWxAuth();
                }
            }
        });
        abi_qq.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNullOrBlank(info.getOpenid_qq())) {
                    loginQQAuth();
                }


            }
        });
        abi_pweditpw.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //acount_pw,phone+pw登录都可以修改,然后如果是微信,qq登录,只要有手机号或者account就可以进行修改密码
                if (!Util.isNullOrBlank(info.getMobile()) || !Util.isNullOrBlank(info.getAccount()))
                    ModifyPwByPwActivity.actionStart(AccountSecurityActivity.this, null, null);

            }
        });
        abi_phoneeditpw.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNullOrBlank(info.getMobile()))
                    ModifyPwByPhoneActivity1.actionStart(AccountSecurityActivity.this, null, null);
            }
        });
    }

    private void loginQQAuth() {
        final CustomLoadingDialog loadingDialog = new CustomLoadingDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        //通过拉起QQ获取QQ的openid,检测该openid是否已经注册,如果未注册,
        QQLoginManager.OnQQAuthLoginCallBack callBack = new QQLoginManager.OnQQAuthLoginCallBack() {
            @Override
            public void onQQAuthSuccessed(String qq_openid, String nickName) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                Log.e(TAG, "qq_openid==" + qq_openid);
                checkAuthBind(qq_openid, TypeConstants.ACCOUNT_TYPE.AUTH_QQ);

            }


            @Override
            public void onQQAuthFailed(String failedMsg) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                ToastUtils.showShort(AccountSecurityActivity.this, failedMsg);
            }
        };
        QQLoginManager.getInstance().loginQQAuth(AccountSecurityActivity.this, callBack);
    }

    private void loginWxAuth() {
        final CustomLoadingDialog loadingDialog = new CustomLoadingDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        //通过拉起Wx获取Wx的openid,检测该openid是否已经注册,如果未注册
        WxAuthLogic.OnWxAuthCallBack callBack = new WxAuthLogic.OnWxAuthCallBack() {
            @Override
            public void onAuthSuccessed(String openid, String nickname) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                checkAuthBind(openid, TypeConstants.ACCOUNT_TYPE.AUTH_WECHAT);
            }

            @Override
            public void onAuthFailed(String failedMsg) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                ToastUtils.showShort(AccountSecurityActivity.this, failedMsg);
            }
        };
        WxAuthLogic.getInstance().doWxAuth(callBack);
    }

    /**
     * 检测QQ/Wx帐号是否存在,并绑定
     */
    private void checkAuthBind(final String openid, final int accountType) {
        final String sskey = AccountManager.getInstance().getSskey();
        CheckAccoutLogic.OnCheckAccountCallBack call = new CheckAccoutLogic.OnCheckAccountCallBack() {
            @Override
            public void checkSucessed(boolean isHas, String result) {
                if (isHas) {
                    ToastUtils.showShort(AccountSecurityActivity.this, result);
                } else {
                    BindLogic.OnBindCallBack call = new BindLogic.OnBindCallBack() {
                        @Override
                        public void onBindSuccessed() {
                            ToastUtils.showShort(AccountSecurityActivity.this, "绑定成功");
                        }

                        @Override
                        public void onBindFailed(String failMsg) {
                            ToastUtils.showShort(AccountSecurityActivity.this, "绑定失败," + failMsg);
                        }
                    };
                    BindLogic.doBindWxOrQQ(sskey, openid, accountType, call);
                }
            }

            @Override
            public void checkFailed(String errorMsg) {
                ToastUtils.showShort(AccountSecurityActivity.this, "绑定失败," + errorMsg);
            }
        };
        CheckAccoutLogic.checkAccountIsHas(openid, accountType, call);
    }

    public static void actionStar(Context context, String data1, String data2) {
        Intent intent = new Intent(context, AccountSecurityActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onAccountUpdate(UserInfo info) {
        setUIData(info);
    }
}
