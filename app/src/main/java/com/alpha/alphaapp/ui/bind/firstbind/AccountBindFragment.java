package com.alpha.alphaapp.ui.bind.firstbind;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.bind.BindLogic;
import com.alpha.alphaapp.model.login.LoginLogic;
import com.alpha.alphaapp.model.logout.LoginOutLogic;

import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;

import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/5 15:52
 * Email : xiaokai090704@126.com
 * 绑定帐号
 */

public class AccountBindFragment extends BaseFragment {
    private static final String TAG = "AccountBindFragment";
    private AccountEditText et_account;
    private AccountEditText et_pw;
    private Button btn_submit;
    private TextView tv_error, tv_no_bind;

    private Dialog dialog_insure_bind, dialog_bind_success;


    private int loginType;
    private String openId;

    @Override
    protected int getLayoutId() {
        loginType = ((BindAccountActivity) getActivity()).getLoginType();
        openId = ((BindAccountActivity) getActivity()).getOpenid();
        return R.layout.fragment_bind_account;
    }

    @Override
    protected void initViews(View root) {
        et_account = (AccountEditText) root.findViewById(R.id.bind_ac_aet_accout);
        et_pw = (AccountEditText) root.findViewById(R.id.bind_ac_aet_pw);
        tv_error = (TextView) root.findViewById(R.id.bind_ac_tv_error);
        btn_submit = (Button) root.findViewById(R.id.bind_ac_btn_submit);
        tv_no_bind = (TextView) root.findViewById(R.id.bind_ac_tv_no_bind);
        initDialogs();
    }

    /**
     * 初始化对话框
     */
    private void initDialogs() {
        dialog_insure_bind= DialogUtils.createTwoChoiceDialog(getActivity(), R.string.insure_bind_account, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNull(dialog_insure_bind) && dialog_insure_bind.isShowing()) {
                    dialog_insure_bind.dismiss();
                }
                onBindAccount();
            }
        });

        dialog_bind_success=DialogUtils.createSingleChoiceDialog(getActivity(), R.string.bind_success_you_use_wechat_account_login, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到HomeActivity
                if (!Util.isNull(dialog_bind_success) && dialog_bind_success.isShowing()) {
                    dialog_bind_success.dismiss();
                }
                HomeActivity.actionStart(getActivity(), null, null);
            }
        });
    }

    @Override
    protected void initEnvent() {
        et_account.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_account.getEditTextStr()) || Util.isNullOrBlank(et_pw.getEditTextStr())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_gray);
                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_blue);
                }
                if (Util.isNullOrBlank(et_account.getEditTextStr())) {
                    et_account.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    et_account.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_pw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_account.getEditTextStr()) || Util.isNullOrBlank(et_pw.getEditTextStr())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_blue);
                }

                if (Util.isNullOrBlank(et_pw.getEditTextStr())) {
                    et_pw.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    et_pw.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isAccountLine(et_account.getEditTextStr())) {
                    tv_error.setText(R.string.account_format);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (!StringUtils.isPWLine(et_pw.getEditTextStr())) {
                    tv_error.setText(R.string.pw_error_format);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }

                if (!Util.isNull(dialog_insure_bind) && !dialog_insure_bind.isShowing())
                    dialog_insure_bind.show();
            }
        });

        tv_no_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginLogic.OnLoginCallBack callBack = new LoginLogic.OnLoginCallBack() {
                    @Override
                    public void onLoginSuccessed(String sskey) {
                        //这里需要存在暂不关联
                        HomeActivity.actionStart(getActivity(), null, null);
                    }

                    @Override
                    public void onLoginFailed(String errorMsg) {
                        tv_error.setText(errorMsg);
                        tv_error.setVisibility(View.VISIBLE);
                    }
                };
                LoginLogic.doLogin(openId, null, loginType, callBack);
            }
        });
    }

    @Override
    protected void initData() {

    }


    /**
     * 先使用输入的帐号登录,然后绑定首次授权登录的第三方opneid
     */
    private void onBindAccount() {

        String account = et_account.getEditTextStr();
        String pw = et_pw.getEditTextStr();
        //如果帐号存在,登录进入,直接进入HomeActivity
        LoginLogic.OnLoginCallBack callBack = new LoginLogic.OnLoginCallBack() {
            @Override
            public void onLoginSuccessed(String sskey) {
                //获取用户信息,判读是否已经有绑定的其他的帐号
                UserInfo info = AccountManager.getInstance().getUserInfo();
                if (TypeConstants.LOGIN_TYPE.AUTH_QQ == loginType) {
                    if (Util.isNullOrBlank(info.getOpenid_qq())) {
                        BindLogic.OnBindCallBack call = new BindLogic.OnBindCallBack() {
                            @Override
                            public void onBindSuccessed() {
                                //弹出对话框,提示绑定成功!
                                if (!Util.isNull(dialog_bind_success) && !dialog_bind_success.isShowing())
                                    dialog_bind_success.show();
                            }

                            @Override
                            public void onBindFailed(String failMsg) {
                                if (!Util.isNull(dialog_insure_bind) && dialog_insure_bind.isShowing())
                                    dialog_insure_bind.dismiss();
                                tv_error.setText(failMsg);
                                tv_error.setVisibility(View.VISIBLE);
                            }
                        };
                        BindLogic.doBindWxOrQQ(sskey, openId, loginType, call);
                    } else {
                        LoginOutLogic.doLoginOut(sskey, null);
                        tv_error.setText("该英文帐号已经绑定其他QQ帐号");
                        tv_error.setVisibility(View.VISIBLE);
                    }
                } else if (TypeConstants.LOGIN_TYPE.AUTH_WX == loginType) {
                    if (Util.isNullOrBlank(info.getOpenid_qq())) {
                        BindLogic.OnBindCallBack call = new BindLogic.OnBindCallBack() {
                            @Override
                            public void onBindSuccessed() {
                                //弹出对话框,提示绑定成功!
                                if (!Util.isNull(dialog_bind_success) && !dialog_bind_success.isShowing())
                                    dialog_bind_success.show();
                            }

                            @Override
                            public void onBindFailed(String failMsg) {
                                if (!Util.isNull(dialog_insure_bind) && dialog_insure_bind.isShowing())
                                    dialog_insure_bind.dismiss();
                                tv_error.setText(failMsg);
                                tv_error.setVisibility(View.VISIBLE);
                            }
                        };
                        BindLogic.doBindWxOrQQ(sskey, openId, loginType, call);
                    } else {
                        LoginOutLogic.doLoginOut(sskey, null);
                        tv_error.setText("该英文帐号已经绑定其他微信帐号");
                        tv_error.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onLoginFailed(String errorMsg) {
                tv_error.setText(errorMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        LoginLogic.doLogin(account, pw, TypeConstants.LOGIN_TYPE.ACCONUT_PW, callBack);


    }

    @Override
    public void onPause() {
        super.onPause();
        if (!Util.isNull(dialog_insure_bind) && dialog_insure_bind.isShowing()) {
            dialog_insure_bind.dismiss();
        }
        if (!Util.isNull(dialog_bind_success) && dialog_bind_success.isShowing()) {
            dialog_bind_success.dismiss();
        }
    }
}
