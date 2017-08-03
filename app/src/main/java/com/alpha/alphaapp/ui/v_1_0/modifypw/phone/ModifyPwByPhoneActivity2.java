package com.alpha.alphaapp.ui.v_1_0.modifypw.phone;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.et.InputVerifyEditText;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.alphaapp.model.CountDownManager;

/**
 * Created by kenway on 17/6/12 15:46
 * Email : xiaokai090704@126.com
 */

public class ModifyPwByPhoneActivity2 extends BaseActivity implements TextWatcher {
    private static final String TAG = "ModifyPwByPhoneActivity2";
    private InputVerifyEditText ivet_verify;

    private ErrorTextView tv_error;
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
        tv_error = (ErrorTextView) findViewById(R.id.modify_pbph2_tv_error);
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
                ivet_verify.start();
            }
        });

        btn_setpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneVerify(ivet_verify.getEditTextStr())) {
                    //验证手机号和验证码格式是否正确
                    tv_error.setText(R.string.verify_form_error);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                ModifyPwByPhoneActivity3.actionStart(ModifyPwByPhoneActivity2.this, phone, ivet_verify.getEditTextStr());
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
        if (Util.isNullOrBlank(ivet_verify.getEditTextStr())) {
            btn_setpw.setEnabled(Boolean.FALSE);
            btn_setpw.setBackgroundResource(R.drawable.shape_com_bg_gray);
            ivet_verify.getImageViewRight().setVisibility(View.INVISIBLE);
        } else {
            btn_setpw.setEnabled(Boolean.TRUE);
            btn_setpw.setBackgroundResource(R.drawable.shape_bg_red);
            ivet_verify.getImageViewRight().setVisibility(View.INVISIBLE);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
