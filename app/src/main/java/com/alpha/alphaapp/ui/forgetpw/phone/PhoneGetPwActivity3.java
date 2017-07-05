package com.alpha.alphaapp.ui.forgetpw.phone;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.phonefindpw.PhoneFindPwLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.login.LoginActivity;

import com.alpha.alphaapp.ui.widget.dialog.CustomAlertDialog;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/6 15:06
 * Email : xiaokai090704@126.com
 */

public class PhoneGetPwActivity3 extends BaseActivity {
    private static final String TAG = "PhoneGetPwActivity3";
    private String phone, verify;
    private TitleLayout titleLayout;
    private AccountEditText aet_pw;
    private TextView tv_error;
    private Button btn_submit;

    private CustomAlertDialog dialog;


    @Override
    protected int getLayoutId() {
        phone = getIntent().getStringExtra("phone");
        verify = getIntent().getStringExtra("verify");
        return R.layout.activity_phone_getpw_3;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.phone_get_pw_3_titlelayout);

        aet_pw = (AccountEditText) findViewById(R.id.phone_get_pw_3_aet_pw);
        tv_error = (TextView) findViewById(R.id.phone_get_pw_3_tv_error);
        btn_submit = (Button) findViewById(R.id.phone_get_pw_3_btn_submit);
        dialog = new CustomAlertDialog(this);
        dialog.setCancelable(false);
    }

    @Override
    protected void initListener() {
        aet_pw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(btn_submit.getText())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_blue);

                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPwByPhone();

            }
        });

        dialog.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.actionStartClearStack(PhoneGetPwActivity3.this, null, null);
            }
        });
    }

    /**
     * 通过手机找到密码
     */
    private void findPwByPhone() {
        if (!StringUtils.isPWLine(aet_pw.getEditTextStr())) {
            //验证手机号和验证码格式是否正确
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        PhoneFindPwLogic.OnByPhoneFindPwCallBack callBack = new PhoneFindPwLogic.OnByPhoneFindPwCallBack() {
            @Override
            public void onFindPwSuccessed() {
                //弹出对话框提示密码修改成功,并返回登录页面
                if (!Util.isNull(dialog))
                    dialog.show();
            }

            @Override
            public void onFindPwFailed(String failMsg) {
                tv_error.setText(failMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        PhoneFindPwLogic.doByPhoneFindPw(phone, aet_pw.getEditTextStr(), verify, callBack);
    }

    @Override
    public void initData() {

    }

    public static void actionStart(Context context, String phone, String verify) {
        Intent intent = new Intent(context, PhoneGetPwActivity3.class);
        intent.putExtra("phone", phone);
        intent.putExtra("verify", verify);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }

    }
}
