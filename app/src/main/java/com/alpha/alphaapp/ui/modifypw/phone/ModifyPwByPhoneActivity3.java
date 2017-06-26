package com.alpha.alphaapp.ui.modifypw.phone;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.modifyPassword.ModifyPwdLogic;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/12 15:46
 * Email : xiaokai090704@126.com
 */

public class ModifyPwByPhoneActivity3 extends BaseActivity implements TextWatcher {
    private static final String TAG = "ModifyPwByPhoneActivity3";
    private EditText et_pw;
    private TextView tv_error;
    private Button btn_save;
    private String phone, verify;


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
        et_pw = (EditText) findViewById(R.id.modify_pbph3_et_pw);
        tv_error = (TextView) findViewById(R.id.modify_pbph3_tv_error);
        btn_save = (Button) findViewById(R.id.modify_pbph3_btn_save);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        et_pw.addTextChangedListener(this);
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
        if (AccountManager.isSskeyIsNul(ModifyPwByPhoneActivity3.this)) {
            return;
        }
        String sskey = AccountManager.getInstance().getSskey();
        ModifyPwdLogic.OnModifyPwCallBack callBack = new ModifyPwdLogic.OnModifyPwCallBack() {
            @Override
            public void modifyPwSuccess() {
                ToastUtils.showShort(ModifyPwByPhoneActivity3.this, "密码修改成功");
                AccountManager.getInstance().exitLogin(ModifyPwByPhoneActivity3.this);
                finish();
            }
            @Override
            public void modifyPwFailed(String failMsg) {
                tv_error.setText(failMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        ModifyPwdLogic.doModifyPwByPhone(sskey,phone,verify,et_pw.getText().toString(),callBack);

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
            btn_save.setBackgroundResource(R.drawable.shape_btn_bg_gray);

        } else {
            btn_save.setEnabled(Boolean.TRUE);
            btn_save.setBackgroundResource(R.drawable.shape_btn_bg_blue);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
