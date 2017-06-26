package com.alpha.alphaapp.ui.register.account;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.check.CheckAccoutLogic;
import com.alpha.alphaapp.model.login.LoginLogic;
import com.alpha.alphaapp.model.register.RegisterLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.register.phone.RegisterPhoneActivity3;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/22 10:04
 * Email : xiaokai090704@126.com
 */

public class RegisterAccountActivity extends BaseActivity {
    private static final String TAG = "RegisterAccountActivity";
    private AccountEditText aet_acc, aet_pw, aet_insurepw;
    private TextView tv_error;
    private Button btn_reg;
    private TextView tv_protocal;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_acc;
    }

    @Override
    protected void initView() {
        aet_acc = (AccountEditText) findViewById(R.id.reg_account_aet_acc);
        aet_pw = (AccountEditText) findViewById(R.id.reg_account_aet_pw);
        aet_insurepw = (AccountEditText) findViewById(R.id.reg_account_aet_insurepw);
        tv_error = (TextView) findViewById(R.id.reg_account_tv_error);
        btn_reg = (Button) findViewById(R.id.reg_account_btn_reg_and_login);
        tv_protocal= (TextView) findViewById(R.id.reg_account_tv_protocal);

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
                ToastUtils.showShort(RegisterAccountActivity.this,"进入到协议activity");
            }
        });
    }


    /**
     * 判断输入框中是否有内容
     *
     * @param aet
     */
    private void isEditTextNull(AccountEditText aet) {
        if (Util.isNullOrBlank(aet_acc.getText().toString()) || Util.isNullOrBlank(aet_pw.getText().toString()) || Util.isNullOrBlank(aet_insurepw.getText().toString())) {
            btn_reg.setEnabled(Boolean.FALSE);
            btn_reg.setBackgroundResource(R.drawable.shape_btn_bg_gray);

        } else {
            btn_reg.setEnabled(Boolean.TRUE);
            btn_reg.setBackgroundResource(R.drawable.shape_btn_bg_blue);
        }

        if (Util.isNullOrBlank(aet.getText().toString())) {
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
        if (!StringUtils.isAccountLine(aet_acc.getText().toString())) {
            tv_error.setText(R.string.account_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPWLine(aet_pw.getText().toString()) || !StringUtils.isPWLine(aet_insurepw.getText().toString())) {
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!aet_pw.getText().toString().equals(aet_insurepw.getText().toString())) {
            tv_error.setText(R.string.twice_newpw_diffrent);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }

        //判断是否存在该帐号
        final String account = aet_acc.getText().toString();
        final String pw = aet_pw.getText().toString();
        int type = TypeConstants.ACCOUNT_TYPE.ACCOUNT;

        CheckAccoutLogic.OnCheckAccountCallBack listener = new CheckAccoutLogic.OnCheckAccountCallBack() {
            @Override
            public void checkSucessed(boolean isHas, String result) {
                if (isHas) {
                    tv_error.setText(result);
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
            public void checkFailed(String errorMsg) {

            }
        };
        CheckAccoutLogic.checkAccountIsHas(account, type, listener);


    }

    /**
     * 注册帐号
     */
    private void doRegisterAccount(final String account, final String pw) {
        RegisterLogic.OnRegisterCallBack call = new RegisterLogic.OnRegisterCallBack() {
            @Override
            public void onRegisterSuccessed() {
                //注册成功后弹出对话框其实,让用户登录
                ToastUtils.showLong(RegisterAccountActivity.this, R.string.register_success);
                doLogin(account, pw);
            }

            @Override
            public void onRegisterFailed(String errorMsg) {
            }
        };
        RegisterLogic.doRegisterAccountPW(account, pw, call);
    }

    /**
     * 登录帐号
     *
     * @param account
     * @param pw
     */
    private void doLogin(String account, String pw) {
        LoginLogic.OnLoginCallBack call = new LoginLogic.OnLoginCallBack() {
            @Override
            public void onLoginSuccessed(String sskey) {

                //点击登录
                HomeActivity.actionStartClearStack(RegisterAccountActivity.this, null, null);
            }

            @Override
            public void onLoginFailed(String errorMsg) {
            }
        };
        LoginLogic.doLogin(account, pw, TypeConstants.LOGIN_TYPE.ACCONUT_PW, call);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterAccountActivity.class);
        context.startActivity(intent);
    }
}
