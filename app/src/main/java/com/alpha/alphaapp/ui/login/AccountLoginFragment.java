package com.alpha.alphaapp.ui.login;

import android.graphics.drawable.AnimationDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.anim.GifView;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.ui.login.wx.WxAuthLogic;
import com.alpha.alphaapp.ui.set.AccountSecurityActivity;
import com.alpha.alphaapp.ui.widget.et.LoginAccountEditText;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.check.CheckAccoutLogic;
import com.alpha.alphaapp.model.login.LoginLogic;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.bind.firstbind.BindAccountActivity;
import com.alpha.alphaapp.ui.forgetpw.ForgetPWGuideActivity;
import com.alpha.alphaapp.ui.login.qq.QQLoginManager;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.dialog.CustomLoadingDialog;
import com.alpha.alphaapp.ui.widget.et.PwInputEditText;
import com.alpha.alphaapp.wxapi.WXManager;
import com.alpha.alphaapp.ui.register.RegisterGuideActivity;
import com.alpha.alphaapp.wxapi.WechatAuthCallBack;
import com.alpha.alphaapp.wxapi.WxAccessTokenInfo;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by kenway on 17/5/26 11:27
 * Email : xiaokai090704@126.com
 */

public class AccountLoginFragment extends BaseFragment {
    private static final String TAG = "AccountLoginFragment";
    private LoginAccountEditText laet_user;
    private PwInputEditText piet_pw;
    private TextView tv_error;
    private Button btn_login, btn_register;
    private TextView tv_forget;
    private ImageView iv_weixin, iv_qq;
    private CustomLoadingDialog loadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_account;
    }

    @Override
    protected void initViews(View root) {
        laet_user = (LoginAccountEditText) root.findViewById(R.id.log_ac_aet_accout);
        piet_pw = (PwInputEditText) root.findViewById(R.id.log_ac_piet_pw);
        tv_error = (TextView) root.findViewById(R.id.log_ac_tv_error);
        btn_login = (Button) root.findViewById(R.id.log_ac_btn_login);
        btn_register = (Button) root.findViewById(R.id.log_ac_btn_regiser);
        tv_forget = (TextView) root.findViewById(R.id.log_ac_tv_forgetpw);
        iv_weixin = (ImageView) root.findViewById(R.id.log_ac_iv_auth_weixin);
        iv_qq = (ImageView) root.findViewById(R.id.log_ac_iv_auth_qq);
        loadingDialog = new CustomLoadingDialog(getActivity());
        loadingDialog.setCancelable(false);
    }

    @Override
    protected void initEnvent() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterGuideActivity.actionStart(getContext());
            }
        });
        laet_user.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(laet_user.getEditTextStr()) || Util.isNullOrBlank(piet_pw.getEditTextStr())) {
                    btn_login.setEnabled(Boolean.FALSE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_login.setEnabled(Boolean.TRUE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_red);
                }
                if (Util.isNullOrBlank(laet_user.getEditTextStr())) {
                    laet_user.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    laet_user.getImageViewRight().setVisibility(View.VISIBLE);
                }

                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        piet_pw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (Util.isNullOrBlank(laet_user.getEditTextStr()) || Util.isNullOrBlank(piet_pw.getEditTextStr())) {
                    btn_login.setEnabled(Boolean.FALSE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_gray);
                } else {
                    btn_login.setEnabled(Boolean.TRUE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_red);
                }

                if (Util.isNullOrBlank(piet_pw.getEditTextStr())) {
                    piet_pw.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    piet_pw.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        piet_pw.setOnFocusableListener(new PwInputEditText.OnFoucusableListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //获取焦点的时候判断是否可以显示密码,执行相关的动画
                    ((LoginActivity) getActivity()).doDealCloseEyesAnim();
                } else {
                    //  重新加载原先的动画
                    ((LoginActivity) getActivity()).startNormalAnim();
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAccountPwLogin();
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPWGuideActivity.actionStart(getActivity(), null, null);
            }
        });
        iv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                debugQQFunc("AGf88898", TypeConstants.ACCOUNT_TYPE.AUTH_QQ );
                loginQQAuth();
            }
        });
        iv_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWxAuth();
            }
        });
    }

    /**
     * 使用帐号密码登录
     */
    private void userAccountPwLogin() {
        //是不是手机号
        if (laet_user.getEditTextStr().startsWith("1")) {
            if (!StringUtils.isPhoneNum(laet_user.getEditTextStr())) {
                tv_error.setText(R.string.input_valid_eleven_number);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            if (!StringUtils.isPWLine(piet_pw.getEditTextStr())) {
                tv_error.setText(R.string.pw_error_format);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            loginAccontPw(TypeConstants.LOGIN_TYPE.PHONE_PW);

        } else {

            if (!StringUtils.isAccountLine(laet_user.getEditTextStr())) {
                tv_error.setText(R.string.account_format);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            if (!StringUtils.isPWLine(piet_pw.getEditTextStr())) {
                tv_error.setText(R.string.pw_error_format);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }

            loginAccontPw(TypeConstants.LOGIN_TYPE.ACCONUT_PW);
        }
    }

    /**
     * 使用帐号_手机和密码登录
     *
     * @param loginTpe
     */
    private void loginAccontPw(int loginTpe) {
        if (!Util.isNull(loadingDialog) && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        String account = laet_user.getEditTextStr();
        String pw = piet_pw.getEditTextStr();
        LoginLogic.OnLoginCallBack callBack = new LoginLogic.OnLoginCallBack() {
            @Override
            public void onLoginSuccessed(String sskey) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                HomeActivity.actionStartClearStack(getContext(), null, null);
            }

            @Override
            public void onLoginFailed(String errorMsg) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                tv_error.setText(errorMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        LoginLogic.doLogin(account, pw, loginTpe, callBack);
    }

    /**
     * 这是一个测试代码,后面要删除
     */
    private void debugQQFunc(final String openId, final int loginType) {
        //查看该第三方帐号是否已经注册alpha用户中心,如果有,登录并
        CheckAccoutLogic.OnCheckAccountCallBack call = new CheckAccoutLogic.OnCheckAccountCallBack() {
            @Override
            public void checkSucessed(boolean isHas, String result) {
                //判断是否是第一次登录
                if (isHas) {
                    //如果帐号存在,登录进入,直接进入HomeActivity
                    LoginLogic.OnLoginCallBack callBack = new LoginLogic.OnLoginCallBack() {
                        @Override
                        public void onLoginSuccessed(String sskey) {
                            HomeActivity.actionStartClearStack(getActivity(), null, null);
                        }

                        @Override
                        public void onLoginFailed(String errorMsg) {
                            tv_error.setText(errorMsg);
                            tv_error.setVisibility(View.VISIBLE);
                        }
                    };
                    LoginLogic.doLogin(openId, null, loginType, callBack);
                } else {
                    //如果帐号不存在,直接进入BindActivity
                    BindAccountActivity.actionStart(getActivity(), openId, loginType);

                }
            }

            @Override
            public void checkFailed(String errorMsg) {

            }
        };
        CheckAccoutLogic.checkAccountIsHas(openId, loginType, call);
    }

    @Override
    protected void initData() {

    }

    /**
     * QQ授权登录
     */
    private void loginQQAuth() {

        final CustomLoadingDialog loadingDialog = new CustomLoadingDialog(getActivity());
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        QQLoginManager.OnQQAuthLoginCallBack callBack = new QQLoginManager.OnQQAuthLoginCallBack() {
            @Override
            public void onQQAuthSuccessed(String qq_openid, String nickName) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                ToastUtils.showShort(getActivity(), "授权成功");
                UserOpenidLogin(qq_openid, TypeConstants.LOGIN_TYPE.AUTH_QQ);
            }

            @Override
            public void onQQAuthFailed(String failedMsg) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                ToastUtils.showShort(getActivity(), failedMsg);
            }
        };
        QQLoginManager.getInstance().loginQQAuth(getActivity(), callBack);
    }

    /**
     * 微信授权登录
     */
    public void loginWxAuth() {

        final CustomLoadingDialog loadingDialog = new CustomLoadingDialog(getActivity());
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        //通过拉起Wx获取Wx的openid,检测该openid是否已经注册,如果未注册
        WxAuthLogic.OnWxAuthCallBack callBack = new WxAuthLogic.OnWxAuthCallBack() {
            @Override
            public void onAuthSuccessed(String openid, String nickname) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                ToastUtils.showLong(getActivity(), "你好," + nickname);
                UserOpenidLogin(openid, TypeConstants.LOGIN_TYPE.AUTH_WX);
            }

            @Override
            public void onAuthFailed(String failedMsg) {
                if (!Util.isNull(loadingDialog) && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                ToastUtils.showShort(getActivity(), failedMsg);
            }
        };
        WxAuthLogic.getInstance().doWxAuth(callBack);
    }

    /**
     * 使用openid进行登录
     * 适用于wx,qq登录时
     *
     * @param openId
     * @param loginType
     */
    private void UserOpenidLogin(final String openId, final int loginType) {
        if (!Util.isNullOrBlank(openId)) {
            //查看该第三方帐号是否已经注册alpha用户中心,如果有,登录
            CheckAccoutLogic.OnCheckAccountCallBack call = new CheckAccoutLogic.OnCheckAccountCallBack() {
                @Override
                public void checkSucessed(boolean isHas, String result) {
                    if (isHas) {
                        //如果帐号存在,登录进入,直接进入HomeActivity
                        LoginLogic.OnLoginCallBack callBack = new LoginLogic.OnLoginCallBack() {
                            @Override
                            public void onLoginSuccessed(String sskey) {
                                ToastUtils.showShort(getActivity(), "登录成功");
                                HomeActivity.actionStartClearStack(getActivity(), null, null);
                            }

                            @Override
                            public void onLoginFailed(String errorMsg) {
                                tv_error.setText(errorMsg);
                                tv_error.setVisibility(View.VISIBLE);
                            }
                        };
                        LoginLogic.doLogin(openId, null, loginType, callBack);
                    } else {
                        //如果帐号不存在,直接进入BindActivity
                        BindAccountActivity.actionStart(getActivity(), openId, loginType);

                    }
                }

                @Override
                public void checkFailed(String errorMsg) {

                }
            };
            CheckAccoutLogic.checkAccountIsHas(openId, loginType, call);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
