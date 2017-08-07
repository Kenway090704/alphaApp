package com.alpha.alphaapp.ui.v_1_0.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.widget.et.LoginAccountEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.login.LoginLogic;
import com.alpha.alphaapp.model.v_1_0.verifycode.GetPhoneVerifyLogic;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.widget.dialog.CustomLoadingDialog;
import com.alpha.alphaapp.ui.widget.et.InputVerifyEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/5/26 11:28
 * Email : xiaokai090704@126.com
 */

public class QuickLoginFragment extends BaseFragment {
    private static final String TAG = "QuickLoginFragment";
    private LoginAccountEditText et_phone;
    private InputVerifyEditText ivet;
    private ErrorTextView tv_error;
    private Button btn_login;


    private CustomLoadingDialog loadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_quick;
    }

    @Override
    protected void initViews(View root) {
        et_phone = (LoginAccountEditText) root.findViewById(R.id.log_fast_aet_phone);
        ivet = (InputVerifyEditText) root.findViewById(R.id.log_fast_ivet);
        tv_error = (ErrorTextView) root.findViewById(R.id.log_fast_tv_error);
        btn_login = (Button) root.findViewById(R.id.log_fast_btn_login);
        loadingDialog = new CustomLoadingDialog(getActivity());

    }

    @Override
    protected void initEnvent() {
        ivet.setGetVerifyTextViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoneVerify();
            }
        });
        et_phone.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_phone.getEditTextStr()) || Util.isNullOrBlank(ivet.getEditTextStr())) {
                    btn_login.setEnabled(Boolean.FALSE);
                    btn_login.setBackgroundResource(R.drawable.shape_com_bg_gray);
                } else {
                    btn_login.setEnabled(Boolean.TRUE);
                    btn_login.setBackgroundResource(R.drawable.shape_bg_red);
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
        ivet.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_phone.getEditTextStr()) || Util.isNullOrBlank(ivet.getEditTextStr())) {
                    btn_login.setEnabled(Boolean.FALSE);
                    btn_login.setBackgroundResource(R.drawable.shape_com_bg_gray);
                } else {
                    btn_login.setEnabled(Boolean.TRUE);
                    btn_login.setBackgroundResource(R.drawable.shape_bg_red);

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
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPhoneQuickLogin();
            }
        });
    }

    /**
     * 获取手机验证码
     */
    private void getPhoneVerify() {
        //获取验证码
        if (!StringUtils.isPhoneNum(et_phone.getEditTextStr())) {
            //验证手机号码格式是否正确
            tv_error.setText(R.string.input_valid_eleven_number);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        ivet.start();//开始倒计时
        //获取手机验证码
        String phone = et_phone.getEditTextStr();
        GetPhoneVerifyLogic.OnGetVerifyCallBack callBack = new GetPhoneVerifyLogic.OnGetVerifyCallBack() {
            @Override
            public void onGetVerifySuccess() {

            }

            @Override
            public void onGetVerifyFailed(String failMsg) {

            }
        };
        GetPhoneVerifyLogic.doGetPhoneVerify(phone, TypeConstants.GET_VERIFY_TYPE.LOGIN, callBack);
    }

    /**
     * 手机快速登录
     */
    private void doPhoneQuickLogin() {
        if (!StringUtils.isPhoneNum(et_phone.getEditTextStr()) ||
                !StringUtils.isPhoneVerify(ivet.getEditTextStr())) {
            //验证手机号和验证码格式是否正确
            tv_error.setText(R.string.account_or_verify_error);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!Util.isNull(loadingDialog)) {
            loadingDialog.show();
        }
        String phone = et_phone.getEditTextStr();
        String vrerify = ivet.getEditTextStr();


        OnModelCallback<String> callback = new OnModelCallback<String>() {
            @Override
            public void onModelSuccessed(String s) {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                HomeActivity.actionStart(getContext(), null, null);
                getActivity().finish();
            }

            @Override
            public void onModelFailed(String failedMsg) {
                if (!Util.isNull(loadingDialog)) {
                    loadingDialog.dismiss();
                }
                tv_error.setText(failedMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        LoginLogic.doLogin(phone, vrerify, TypeConstants.LOGIN_TYPE.PHONE_QUICK, callback);
    }

    @Override
    protected void initData() {
        //刷新界面
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!Util.isNull(loadingDialog)) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!Util.isNull(ivet))
            ivet.cancel();
    }
}
