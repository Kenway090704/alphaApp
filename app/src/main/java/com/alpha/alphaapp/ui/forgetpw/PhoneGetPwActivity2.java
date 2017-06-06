package com.alpha.alphaapp.ui.forgetpw;

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
import com.alpha.alphaapp.model.phonefindpw.PhoneFindPwInfo;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_stub.model.CountDownManager;

/**
 * Created by kenway on 17/6/6 15:06
 * Email : xiaokai090704@126.com
 */

public class PhoneGetPwActivity2 extends BaseActivity {

    private static final String TAG = "PhoneGetPwActivity1";
    private TitleLayout titleLayout;
    private EditText et_verify;
    private TextView tv_error, tv_getVerify;
    private Button btn_resetpw;
    private CountDownManager cdm;
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
        titleLayout.setTitleText(R.string.input_account);
        et_verify = (EditText) findViewById(R.id.phone_get_pw_2_et_phoneverify);
        tv_getVerify = (TextView) findViewById(R.id.phone_get_pw_2_tv_getVerify);
        tv_error = (TextView) findViewById(R.id.phone_get_pw_2_tv_error);
        btn_resetpw = (Button) findViewById(R.id.phone_get_pw_2_btn_resetpw);
        cdm = new CountDownManager();
        cdm.setTextView(tv_getVerify);
        cdm.start();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        et_verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_verify.getText())) {
                    btn_resetpw.setEnabled(Boolean.FALSE);
                    btn_resetpw.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_resetpw.setEnabled(Boolean.TRUE);
                    btn_resetpw.setBackgroundResource(R.drawable.shape_btn_bg_blue);

                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_resetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneVerify(et_verify.getText().toString())) {
                    //验证手机号和验证码格式是否正确
                    tv_error.setText(R.string.verify_form_error);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (et_verify.getText().toString().equals(verify)) {
                    PhoneGetPwActivity3.actionStart(PhoneGetPwActivity2.this, phone, et_verify.getText().toString());
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
        cdm.cancel();
    }
}
