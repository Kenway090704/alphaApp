package com.alpha.alphaapp.ui.modifypw.phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.other.GetPhoneVerifyInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.forgetpw.phone.PhoneGetPwActivity2;
import com.alpha.alphaapp.ui.forgetpw.phone.PhoneGetPwActivity3;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.et.InputVerifyEditText;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.model.CountDownManager;

/**
 * Created by kenway on 17/6/12 15:46
 * Email : xiaokai090704@126.com
 */

public class ModifyPwByPhoneActivity2 extends BaseActivity implements TextWatcher {
    private static final String TAG = "ModifyPwByPhoneActivity2";
    private InputVerifyEditText ivet_verify;

    private TextView tv_error;
    private Button btn_setpw;
    private String phone;

    private CountDownManager cdm;

    @Override
    protected int getLayoutId() {
        phone = getIntent().getStringExtra("phone");
        return R.layout.activity_modifypw_by_phone_2;
    }

    @Override
    protected void initView() {
        TitleLayout titleLayout = (TitleLayout) findViewById(R.id.modify_pbph2_titlelayou);
        titleLayout.setTitleText(R.string.has_bind_phone_edit_pw);
        ivet_verify = (InputVerifyEditText) findViewById(R.id.modify_pbph2_ivet_phoneverify);
        ivet_verify.start();//开始倒计时
        tv_error = (TextView) findViewById(R.id.modify_pbph2_tv_error);
        btn_setpw = (Button) findViewById(R.id.modify_pbph2_btn_setpw);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        ivet_verify.setWatcherListener(this);
        ivet_verify.setGetVerifyTextViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //再次获取新的验证码
            }
        });

        btn_setpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneVerify(ivet_verify.getText().toString())) {
                    //验证手机号和验证码格式是否正确
                    tv_error.setText(R.string.verify_form_error);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                ModifyPwByPhoneActivity3.actionStart(ModifyPwByPhoneActivity2.this, phone, ivet_verify.getText().toString());
            }
        });
    }


    public static void actionStart(Context context, String phone, String verifycode) {
        Intent intent = new Intent(context, ModifyPwByPhoneActivity2.class);
        intent.putExtra("phone", phone);
        intent.putExtra("verify", verifycode);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cdm != null) {
            cdm.cancel();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (Util.isNullOrBlank(ivet_verify.getText().toString())) {
            btn_setpw.setEnabled(Boolean.FALSE);
            btn_setpw.setBackgroundResource(R.drawable.shape_btn_bg_gray);
            ivet_verify.getImageViewRight().setVisibility(View.INVISIBLE);
        } else {
            btn_setpw.setEnabled(Boolean.TRUE);
            btn_setpw.setBackgroundResource(R.drawable.shape_btn_bg_blue);
            ivet_verify.getImageViewRight().setVisibility(View.INVISIBLE);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
