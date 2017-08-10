package com.alpha.alphaapp.ui.v_1_0.modifypw.phone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.v_1_0.set.AccountSecurityActivity;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.pw.ModifyPwdLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/12 15:46
 * Email : xiaokai090704@126.com
 */

public class ModifyPwByPhoneActivity3 extends BaseActivity implements TextWatcher {
    private static final String TAG = "ModifyPwByPhoneActivity3";
    private AccountEditText et_pw;
    private ErrorTextView tv_error;
    private Button btn_save;
    private String phone, verify;

    private Dialog dialog;


    @Override
    protected int getLayoutId() {
        phone = getIntent().getStringExtra("phone");
        verify = getIntent().getStringExtra("verify");
        return R.layout.activity_modifypw_by_phone_3;
    }

    @Override
    protected void initView() {
        TitleLayout titleLayout = (TitleLayout) findViewById(R.id.modify_pbph3_titlelayou);
        titleLayout.setTitleText(R.string.has_bind_phone_edit_pw);
        et_pw = (AccountEditText) findViewById(R.id.modify_pbph3_et_pw);
        tv_error = (ErrorTextView) findViewById(R.id.modify_pbph3_tv_error);
        btn_save = (Button) findViewById(R.id.modify_pbph3_btn_save);
        dialog = DialogUtils.createSingleChoiceDialog(this, R.string.pw_reset_success, R.string.plz_use_new_login_now, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNull(dialog))
                    dialog.dismiss();
                //弹出对话框
                AccountSecurityActivity.actionStar(ModifyPwByPhoneActivity3.this, null, null);
                //将密码保存到本地
                finish();
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        et_pw.setWatcherListener(this);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealModifyPw();
            }
        });
    }

    /**
     * 处理修改密码
     */
    private void dealModifyPw() {
        if (!StringUtils.isPWLine(et_pw.getText().toString())) {
            //验证手机号和验证码格式是否正确
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        String sskey = AccountManager.getInstance().getSskey();


        OnModelCallback<Object> callback = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                if (!Util.isNull(dialog))
                    dialog.show();
            }

            @Override
            public void onModelFailed(String failedMsg) {
                tv_error.setText(failedMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        ModifyPwdLogic.doModifyPwByPhone(sskey, phone, verify, et_pw.getText().toString(), callback);

    }


    public static void actionStart(Context context, String phone, String verifycode) {
        Intent intent = new Intent(context, ModifyPwByPhoneActivity3.class);
        intent.putExtra("phone", phone);
        intent.putExtra("verify", verifycode);
        context.startActivity(intent);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (Util.isNullOrBlank(et_pw.getText().toString())) {
            btn_save.setEnabled(Boolean.FALSE);
            btn_save.setBackgroundResource(R.drawable.shape_com_bg_gray);

        } else {
            btn_save.setEnabled(Boolean.TRUE);
            btn_save.setBackgroundResource(R.drawable.shape_com_bg_red);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Util.isNull(dialog))
            dialog.dismiss();
    }
}
