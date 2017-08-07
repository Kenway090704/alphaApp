package com.alpha.alphaapp.ui.v_1_0.register.phone;

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
import com.alpha.alphaapp.model.v_1_0.login.LoginLogic;
import com.alpha.alphaapp.model.v_1_0.register.RegisterLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.v_1_0.register.UserAgreementActivity;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/22 10:04
 * Email : xiaokai090704@126.com
 */

public class RegisterPhoneActivity3 extends BaseActivity {
    private static final String TAG = "RegisterAccountActivity";
    private LoginAccountEditText aet_pw;
    private ErrorTextView tv_error;
    private Button btn_reg;
    private TextView tv_protocal;
    private String phone, verify;

    @Override
    protected int getLayoutId() {
        phone = getIntent().getStringExtra("phone");
        verify = getIntent().getStringExtra("verify");
        LogUtils.e(TAG, "phone==" + phone + ".verify==" + verify);
        return R.layout.activity_register_phone_3;
    }


    @Override
    protected void initView() {
        aet_pw = (LoginAccountEditText) findViewById(R.id.reg_phone_3_aet_pw);
        tv_error = (ErrorTextView) findViewById(R.id.reg_phone_3_tv_error);
        btn_reg = (Button) findViewById(R.id.reg_phone_3_btn_register);
        tv_protocal = (TextView) findViewById(R.id.reg_phone_3_btn_tv_protocal);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        aet_pw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEditTextNull();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                doRegister(phone, aet_pw.getEditTextStr(), verify);

            }
        });

        tv_protocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAgreementActivity.actionStart(RegisterPhoneActivity3.this);
            }
        });

    }

    /**
     * 注册
     *
     * @param phone
     * @param pw
     * @param verify
     */
    private void doRegister(final String phone, final String pw, String verify) {
        //判断密码是否正确
        if (!StringUtils.isPWLine(pw)) {
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }


        OnModelCallback<Object> back = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                //弹出对话框提示,然后确认后,登录成功
                ToastUtils.showShort(RegisterPhoneActivity3.this, "注册成功");
                doLogin(phone, pw);
            }

            @Override
            public void onModelFailed(String failedMsg) {
                tv_error.setText(failedMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        RegisterLogic.doRegisterPhone(phone, pw, verify, back);
    }

    /**
     * 登录
     */
    private void doLogin(String phone, String pw) {

        OnModelCallback<String> callback = new OnModelCallback<String>() {
            @Override
            public void onModelSuccessed(String s) {
                //进入到HomeActvity
                HomeActivity.actionStartClearStack(RegisterPhoneActivity3.this, null, null);
            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e(TAG, "failedMsg==" + failedMsg);
                ToastUtils.showLong(RegisterPhoneActivity3.this, failedMsg);
            }
        };
        LoginLogic.doLogin(phone, pw, TypeConstants.LOGIN_TYPE.PHONE_PW, callback);

    }

    /**
     * 判断EditText是否为空
     */
    private void isEditTextNull() {
        if (Util.isNullOrBlank(aet_pw.getEditTextStr())) {
            btn_reg.setEnabled(Boolean.FALSE);
            btn_reg.setBackgroundResource(R.drawable.shape_com_bg_gray);
            aet_pw.getImageViewRight().setVisibility(View.INVISIBLE);
        } else {
            btn_reg.setEnabled(Boolean.TRUE);
            btn_reg.setBackgroundResource(R.drawable.shape_bg_red);
            aet_pw.getImageViewRight().setVisibility(View.VISIBLE);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    public static void actionStart(Context context, String phone, String verify) {
        Intent intent = new Intent(context, RegisterPhoneActivity3.class);
        intent.putExtra("phone", phone);
        intent.putExtra("verify", verify);
        context.startActivity(intent);
    }

}
