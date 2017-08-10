package com.alpha.alphaapp.ui.v_1_0.bind.firstbind;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.userinfo.BindLogic;
import com.alpha.alphaapp.model.v_1_0.login.LoginLogic;
import com.alpha.alphaapp.model.v_1_0.login.LoginOutLogic;
import com.alpha.alphaapp.model.v_1_0.verifycode.GetPhoneVerifyLogic;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.widget.et.InputVerifyEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/5 15:52
 * Email : xiaokai090704@126.com
 * 绑定手机号码
 */

public class PhoneBindFragment extends BaseFragment {
    private static final String TAG = "PhoneBindFragment";
    private AccountEditText et_phone;
    private InputVerifyEditText ivet;
    private ErrorTextView tv_error;
    private Button btn_submit;
    private TextView tv_no_bind;
    private Dialog dialog_insure_bind, dialog_bind_success;


    private int loginType;
    private String openId;

    @Override
    protected int getLayoutId() {
        loginType = ((BindAccountActivity) getActivity()).getLoginType();
        openId = ((BindAccountActivity) getActivity()).getOpenid();
        return R.layout.fragment_bind_phone;
    }

    @Override
    protected void initViews(View root) {
        et_phone = (AccountEditText) root.findViewById(R.id.bind_phone_aet_phone);
        ivet = (InputVerifyEditText) root.findViewById(R.id.bind_phone_ivet);

        tv_error = (ErrorTextView) root.findViewById(R.id.bind_phone_tv_error);
        btn_submit = (Button) root.findViewById(R.id.bind_phone_btn_submit);
        tv_no_bind = (TextView) root.findViewById(R.id.bind_phone_tv_no_bind);
        //初始化两个对话框
        initDialogs();

    }

    /**
     * 初始化对话框
     */
    private void initDialogs() {


        dialog_insure_bind = DialogUtils.createTwoChoiceDialog(getActivity(), R.string.insure_bind_, R.string.this_account, StringUtils.getHideMidPhone(et_phone.getEditTextStr()), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNull(dialog_insure_bind) && dialog_insure_bind.isShowing()) {
                    dialog_insure_bind.dismiss();
                }
                doBindPhone();
            }
        });
        dialog_bind_success = DialogUtils.createSingleChoiceDialog(getActivity() , R.string.bind_success, R.string.you_use_wechat_phone_login, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到HomeActivity
                if (!Util.isNull(dialog_bind_success) && dialog_bind_success.isShowing()) {
                    dialog_bind_success.dismiss();
                }
                HomeActivity.actionStart(getActivity(), null, null);
                //点击确定进入HomeActivity
            }
        });
    }

    @Override
    protected void initEnvent() {
        et_phone.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_phone.getEditTextStr()) || Util.isNullOrBlank(ivet.getEditTextStr())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_com_bg_gray);

                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_com_bg_red);

                }

                if (Util.isNullOrBlank(et_phone.getEditTextStr())) {
                    et_phone.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    et_phone.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivet.setGetVerifyListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivet.getVerify(et_phone.getEditTextStr(),TypeConstants.GET_VERIFY_TYPE.LOGIN,tv_error);
            }
        });
        ivet.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_phone.getEditTextStr()) || Util.isNullOrBlank(ivet.getEditTextStr())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_com_bg_gray);

                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_com_bg_red);
                }

                if (Util.isNullOrBlank(ivet.getEditTextStr())) {
                    ivet.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    ivet.getImageViewRight().setVisibility(View.VISIBLE);
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

                userPhonePwBind();
            }
        });

        tv_no_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录未绑定,下次进入直接进入到HomeActvity页面

                OnModelCallback<String> callback = new OnModelCallback<String>() {
                    @Override
                    public void onModelSuccessed(String s) {
                        //这里需要存在暂不关联
                        HomeActivity.actionStart(getActivity(), null, null);
                    }

                    @Override
                    public void onModelFailed(String failedMsg) {
                        tv_error.setText(failedMsg);
                        tv_error.setVisibility(View.VISIBLE);
                    }
                };
                LoginLogic.doLogin(openId, null, loginType, callback);
            }
        });
    }


    private void getVerify(String phone, int verifyType, final ErrorTextView tv_error) {
        if (!StringUtils.isPhoneNum(phone)) {
            //验证手机号格式是否正确
            tv_error.setText(R.string.input_valid_eleven_number);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        GetPhoneVerifyLogic.OnGetVerifyCallBack callBack = new GetPhoneVerifyLogic.OnGetVerifyCallBack() {
            @Override
            public void onGetVerifySuccess() {
                ivet.start();
            }

            @Override
            public void onGetVerifyFailed(String failMsg) {
                tv_error.setText(failMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        GetPhoneVerifyLogic.doGetPhoneVerify(phone, verifyType, callBack);
    }

    @Override
    protected void initData() {

    }

    /**
     * 绑定帐号
     */
    private void userPhonePwBind() {
        if (!StringUtils.isPhoneNum(et_phone.getEditTextStr())) {
            tv_error.setText(R.string.input_valid_eleven_number);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPhoneVerify(ivet.getEditTextStr())) {
            tv_error.setText(R.string.verify_form_error);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        initDialogs();
        if (!Util.isNull(dialog_insure_bind) && !dialog_insure_bind.isShowing())
            dialog_insure_bind.show();
    }


    /**
     * 提交绑定信息后返回对应的字段进行处理
     */
    private void doBindPhone() {

        final String phone = et_phone.getEditTextStr();
        final String verify = ivet.getEditTextStr();
        //如果帐号存在,登录进入,直接进入HomeActivity

        OnModelCallback<String> callback = new OnModelCallback<String>() {
            @Override
            public void onModelSuccessed(String sskey) {
                //获取用户信息,判读是否已经有绑定的其他的帐号
                UserInfo info = AccountManager.getInstance().getUserInfo();
                if (TypeConstants.LOGIN_TYPE.AUTH_QQ == loginType) {
                    if (Util.isNullOrBlank(info.getOpenid_qq())) {

                        OnModelCallback<Object> call = new OnModelCallback<Object>() {
                            @Override
                            public void onModelSuccessed(Object o) {
                                //弹出对话框,提示绑定成功!
                                if (!Util.isNull(dialog_bind_success) && !dialog_bind_success.isShowing())
                                    dialog_bind_success.show();
                            }

                            @Override
                            public void onModelFailed(String failedMsg) {
                                if (!Util.isNull(dialog_insure_bind) && dialog_insure_bind.isShowing())
                                    dialog_insure_bind.dismiss();
                                tv_error.setText(failedMsg);
                                tv_error.setVisibility(View.VISIBLE);
                            }
                        };
                        BindLogic.doBindWxOrQQ(sskey, openId, loginType, call);
                    } else {
                        LoginOutLogic.doLoginOut(sskey, null);
                        tv_error.setText("该手机帐号已经绑定其他QQ帐号");
                        tv_error.setVisibility(View.VISIBLE);
                    }
                } else if (TypeConstants.LOGIN_TYPE.AUTH_WX == loginType) {
                    if (Util.isNullOrBlank(info.getOpenid_weixin())) {

                        OnModelCallback<Object> call = new OnModelCallback<Object>() {
                            @Override
                            public void onModelSuccessed(Object o) {
                                //弹出对话框,提示绑定成功!
                                if (!Util.isNull(dialog_bind_success) && !dialog_bind_success.isShowing())
                                    dialog_bind_success.show();
                            }

                            @Override
                            public void onModelFailed(String failedMsg) {
                                if (!Util.isNull(dialog_insure_bind) && dialog_insure_bind.isShowing())
                                    dialog_insure_bind.dismiss();
                                tv_error.setText(failedMsg);
                                tv_error.setVisibility(View.VISIBLE);
                            }
                        };
                        BindLogic.doBindWxOrQQ(sskey, openId, loginType, call);
                    } else {
                        LoginOutLogic.doLoginOut(sskey, null);
                        tv_error.setText("该手机帐号已经绑定其他微信帐号");
                        tv_error.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                tv_error.setText(failedMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        LoginLogic.doLogin(phone, verify, TypeConstants.LOGIN_TYPE.PHONE_QUICK, callback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!Util.isNull(ivet))
            ivet.cancel();

        if (!Util.isNull(dialog_insure_bind) && dialog_insure_bind.isShowing()) {
            dialog_insure_bind.dismiss();
        }
        if (!Util.isNull(dialog_bind_success) && dialog_bind_success.isShowing()) {
            dialog_bind_success.dismiss();
        }
    }
}
