package com.alpha.alphaapp.ui.forgetpw.phone;

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
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.et.InputVerifyEditText;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.model.CountDownManager;

/**
 * Created by kenway on 17/6/6 15:06
 * Email : xiaokai090704@126.com
 */

public class PhoneGetPwActivity2 extends BaseActivity implements TextWatcher {

    private static final String TAG = "PhoneGetPwActivity1";
    private TitleLayout titleLayout;
    private InputVerifyEditText ivet;
    private TextView tv_error;
    private Button btn_resetpw;

    //从第一个页面中获取的phone,verify
    private String phone, verify;

    @Override
    protected int getLayoutId() {
        phone = getIntent().getStringExtra("phone");
        verify = getIntent().getStringExtra("verify");
        return R.layout.activity_phone_getpw_2;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.phone_get_pw_2_titlelayout);

        ivet = (InputVerifyEditText) findViewById(R.id.phone_get_pw_2_ivet);
        ivet.start();
        tv_error = (TextView) findViewById(R.id.phone_get_pw_2_tv_error);
        btn_resetpw = (Button) findViewById(R.id.phone_get_pw_2_btn_resetpw);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

        ivet.setWatcherListener(this);
        ivet.setGetVerifyTextViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //再次获取验证码
            }
        });
        btn_resetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneVerify(ivet.getText().toString())) {
                    //验证手机号和验证码格式是否正确
                    tv_error.setText(R.string.verify_form_error);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (ivet.getText().toString().equals(verify)) {
                    PhoneGetPwActivity3.actionStart(PhoneGetPwActivity2.this, phone, ivet.getText().toString());
                } else {
                    tv_error.setText(R.string.verify_code_error);
                    tv_error.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    public static void actionStart(Context context, String phone, String verifycode) {
        Intent intent = new Intent(context, PhoneGetPwActivity2.class);
        intent.putExtra("phone", phone);
        intent.putExtra("verify", verifycode);
        context.startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Util.isNull(ivet))
            ivet.cancel();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(ivet.getText())) {
            btn_resetpw.setEnabled(Boolean.FALSE);
            btn_resetpw.setBackgroundResource(R.drawable.shape_btn_bg_gray);
            ivet.getImageViewRight().setVisibility(View.INVISIBLE);
        } else {
            btn_resetpw.setEnabled(Boolean.TRUE);
            btn_resetpw.setBackgroundResource(R.drawable.shape_btn_bg_blue);
            ivet.getImageViewRight().setVisibility(View.VISIBLE);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
