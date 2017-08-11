package com.alpha.alphaapp.ui.v_1_0.register.phone;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.verifycode.VerifyMsgCodeLogic;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.widget.et.InputVerifyEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/22 10:04
 * Email : xiaokai090704@126.com
 */

public class RegisterPhoneActivity2 extends BaseActivity {
    private static final String TAG = "RegisterAccountActivity";

    private InputVerifyEditText ivet;
    private ErrorTextView tv_error;
    private Button btn_setpw;
    private String phone;

    @Override
    protected int getLayoutId() {
        phone = getIntent().getStringExtra("phone");
        return R.layout.activity_register_phone_2;
    }

    @Override
    protected void initView() {

        ivet = (InputVerifyEditText) findViewById(R.id.reg_phone_2_ivet);

        tv_error = (ErrorTextView) findViewById(R.id.reg_phone_2tv_error);
        btn_setpw = (Button) findViewById(R.id.reg_phone_2_btn_setpw);

    }

    @Override
    public void initData() {
        ivet.start();

    }

    @Override
    protected void initListener() {
        ivet.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(ivet.getEditTextStr())) {
                    btn_setpw.setEnabled(Boolean.FALSE);
                    btn_setpw.setBackgroundResource(R.drawable.shape_com_bg_gray);
                    ivet.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    btn_setpw.setEnabled(Boolean.TRUE);
                    btn_setpw.setBackgroundResource(R.drawable.shape_com_bg_red);
                    ivet.getImageViewRight().setVisibility(View.VISIBLE);
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
                ivet.getVerify(phone,TypeConstants.GET_VERIFY_TYPE.REGISTER,tv_error);
            }
        });

        btn_setpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpNextActivity();
            }
        });
    }


    private void jumpNextActivity() {
        if (!StringUtils.isPhoneVerify(ivet.getEditTextStr())) {
            //验证手机号和验证码格式是否正确
            tv_error.setText(R.string.verify_form_error);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }

        //验证验证码是否正确
        OnModelCallback<Object> callback = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                //进入第三页
                RegisterPhoneActivity3.actionStart(RegisterPhoneActivity2.this, phone, ivet.getEditTextStr());
            }

            @Override
            public void onModelFailed(String failedMsg) {
                tv_error.setText(failedMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        VerifyMsgCodeLogic.doVerigyMsgCode(phone, ivet.getEditTextStr(), TypeConstants.GET_VERIFY_TYPE.REGISTER, callback);

    }

    public static void actionStart(Context context, String phone) {
        Intent intent = new Intent(context, RegisterPhoneActivity2.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Util.isNull(ivet))
            ivet.cancel();
    }


}
