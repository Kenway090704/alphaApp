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
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.phonefindpw.PhoneFindPwInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.login.LoginActivity;

import com.alpha.alphaapp.ui.widget.dialog.CustomAlertDialog;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;

/**
 * Created by kenway on 17/6/6 15:06
 * Email : xiaokai090704@126.com
 */

public class PhoneGetPwActivity3 extends BaseActivity {
    private static final String TAG = "PhoneGetPwActivity3";
    private String phone, verify;
    private TitleLayout titleLayout;
    private EditText et_pw;
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

        et_pw = (EditText) findViewById(R.id.phone_get_pw_3_et_pw);
        tv_error = (TextView) findViewById(R.id.phone_get_pw_3_tv_error);
        btn_submit = (Button) findViewById(R.id.phone_get_pw_3_btn_submit);
        dialog = new CustomAlertDialog(this);
    }

    @Override
    protected void initListener() {
        et_pw.addTextChangedListener(new TextWatcher() {
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

        dialog.setOnClickListener(new View.OnClickListener() {
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
        if (!StringUtils.isPWLine(et_pw.getText().toString())) {
            //验证手机号和验证码格式是否正确
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        String data = PhoneFindPwInfo.getJsonStrforPhoneFindPW(phone, verify, et_pw.getText().toString());
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                dealReqSuccess(result);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(getApplicationContext()).requestPostByJsonAsyn(URLConstans.URL.PHONE_FIND_PW, json, callBack);
    }

    /**
     * 处理返回的结果
     *
     * @param result
     */
    private void dealReqSuccess(String result) {
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        switch (info.getResult()) {
            case CommStants.BY_PHONE_FIND_PW.RESULT_OK:
                //弹出对话框提示密码修改成功,并返回登录页面
                dialog.show();
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_DATA_PACKAGE_ERROR:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_PHONE_ERROR:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_PHONE_INPUT_ERROR:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_PW_FORM_ERROR:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_VERIFY_INPUT_ERROR:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                break;
            case CommStants.BY_PHONE_FIND_PW.RESULT_VERIFY_ERROR:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                break;
        }

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
