package com.alpha.alphaapp.ui.v_1_0.register.account;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.widget.et.LoginAccountEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.register.CheckAccoutLogic;
import com.alpha.alphaapp.model.v_1_0.login.LoginLogic;
import com.alpha.alphaapp.model.v_1_0.register.RegisterLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.v_1_0.register.UserAgreementActivity;
import com.alpha.alphaapp.ui.widget.OneTwoThreeItemView;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/22 10:04
 * Email : xiaokai090704@126.com
 */

public class RegisterAccountActivity extends BaseActivity {
    private static final String TAG = "RegisterAccountActivity";
    private OneTwoThreeItemView ott;
    private LoginAccountEditText aet_acc, aet_pw, aet_insurepw;
    private ErrorTextView tv_error;
    private Button btn_reg;
    private TextView tv_protocal;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_acc;
    }

    @Override
    protected void initView() {
        ott = (OneTwoThreeItemView) findViewById(R.id.reg_account_ott);
        aet_acc = (LoginAccountEditText) findViewById(R.id.reg_account_aet_acc);
        aet_pw = (LoginAccountEditText) findViewById(R.id.reg_account_aet_pw);
        aet_insurepw = (LoginAccountEditText) findViewById(R.id.reg_account_aet_insurepw);
        tv_error = (ErrorTextView) findViewById(R.id.reg_account_tv_error);
        btn_reg = (Button) findViewById(R.id.reg_account_btn_reg_and_login);
        tv_protocal = (TextView) findViewById(R.id.reg_account_tv_protocal);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        aet_acc.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEditTextNull(aet_acc);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aet_pw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEditTextNull(aet_pw);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aet_insurepw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEditTextNull(aet_insurepw);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegisterAndLogin();
            }
        });

        tv_protocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入到查看协议的activity中
                UserAgreementActivity.actionStart(RegisterAccountActivity.this);
            }
        });
    }


    /**
     * 判断输入框中是否有内容
     *
     * @param aet
     */
    private void isEditTextNull(LoginAccountEditText aet) {
        if (Util.isNullOrBlank(aet_acc.getEditTextStr()) || Util.isNullOrBlank(aet_pw.getEditTextStr()) || Util.isNullOrBlank(aet_insurepw.getEditTextStr())) {
            btn_reg.setEnabled(Boolean.FALSE);
            btn_reg.setBackgroundResource(R.drawable.shape_com_bg_gray);

        } else {
            btn_reg.setEnabled(Boolean.TRUE);
            btn_reg.setBackgroundResource(R.drawable.shape_bg_red);
            ott.setCurrentUI(2);

        }

        if (Util.isNullOrBlank(aet.getEditTextStr())) {
            aet.getImageViewRight().setVisibility(View.INVISIBLE);
        } else {
            aet.getImageViewRight().setVisibility(View.VISIBLE);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }


    /**
     * 注册并登录
     */
    private void doRegisterAndLogin() {
        //判断帐号是否正确
        if (!StringUtils.isAccountLine(aet_acc.getEditTextStr())) {
            tv_error.setText(R.string.account_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPWLine(aet_pw.getEditTextStr()) || !StringUtils.isPWLine(aet_insurepw.getEditTextStr())) {
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!aet_pw.getEditTextStr().equals(aet_insurepw.getEditTextStr())) {
            tv_error.setText(R.string.twice_newpw_diffrent);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }

        //判断是否存在该帐号
        final String account = aet_acc.getEditTextStr();
        final String pw = aet_pw.getEditTextStr();
        int type = TypeConstants.ACCOUNT_TYPE.ACCOUNT;


        OnModelCallback<Boolean> callback = new OnModelCallback<Boolean>() {
            @Override
            public void onModelSuccessed(Boolean isHas) {
                if (isHas) {
                    tv_error.setText(R.string.account_had);
                    tv_error.setVisibility(View.VISIBLE);
                    aet_acc.setFocusable(true);
                    aet_acc.setFocusableInTouchMode(true);
                    aet_acc.requestFocus();
                    KeyBoardUtils.openKeybord(aet_acc.getEditText(), RegisterAccountActivity.this);
                } else {
                    doRegisterAccount(account, pw);
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e(TAG, "failedMsg==" + failedMsg);
            }
        };
        CheckAccoutLogic.checkAccountIsHas(account, type, callback);


    }

    /**
     * 注册帐号
     */
    private void doRegisterAccount(final String account, final String pw) {

        OnModelCallback<Object> back = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                //注册成功后弹出对话框其实,让用户登录
                ToastUtils.showLong(RegisterAccountActivity.this, R.string.register_success);
                doLogin(account, pw);
            }

            @Override
            public void onModelFailed(String failedMsg) {

                LogUtils.e(TAG, "failed==" + failedMsg);
                ToastUtils.showToast(RegisterAccountActivity.this, failedMsg);
            }
        };
        RegisterLogic.doRegisterAccountPW(account, pw, back);
    }

    /**
     * 登录帐号
     *
     * @param account
     * @param pw
     */
    private void doLogin(String account, String pw) {


        OnModelCallback<String> callback = new OnModelCallback<String>() {
            @Override
            public void onModelSuccessed(String s) {
                //点击登录
                HomeActivity.actionStartClearStack(RegisterAccountActivity.this, null, null);
            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e(TAG, "failedMsg==" + failedMsg);
                ToastUtils.showLong(RegisterAccountActivity.this, failedMsg);
            }
        };
        LoginLogic.doLogin(account, pw, TypeConstants.LOGIN_TYPE.ACCONUT_PW, callback);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterAccountActivity.class);
        context.startActivity(intent);
    }
}
