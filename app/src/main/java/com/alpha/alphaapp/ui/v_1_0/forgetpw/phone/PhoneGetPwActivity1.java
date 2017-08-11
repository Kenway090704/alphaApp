package com.alpha.alphaapp.ui.v_1_0.forgetpw.phone;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.widget.et.EmptyEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.verifycode.GetPhoneVerifyLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;

/**
 * Created by kenway on 17/6/6 15:06
 * Email : xiaokai090704@126.com
 */

public class PhoneGetPwActivity1 extends BaseActivity {
    private static final String TAG = "PhoneGetPwActivity1";
    private TitleLayout titleLayout;
    private EmptyEditText et_phone;
    private ErrorTextView tv_error;
    private Button btn_getVerify;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_getpw_1;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.phone_get_pw_1_titlelayout);
        titleLayout.setTitleText(R.string.verify_msg);
        et_phone = (EmptyEditText) findViewById(R.id.phone_get_pw_1_aet_phone);
        tv_error = (ErrorTextView) findViewById(R.id.phone_get_pw_1_tv_error);
        btn_getVerify = (Button) findViewById(R.id.phone_get_pw_1_btn_getVerify);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        et_phone.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_phone.getEditTextStr())) {
                    btn_getVerify.setEnabled(Boolean.FALSE);
                    btn_getVerify.setBackgroundResource(R.drawable.shape_com_bg_gray);
                    et_phone.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    btn_getVerify.setEnabled(Boolean.TRUE);
                    btn_getVerify.setBackgroundResource(R.drawable.shape_com_bg_red);
                    et_phone.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_getVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneNum(et_phone.getEditTextStr())) {
                    //验证手机号格式是否正确
                    tv_error.setText(R.string.input_valid_eleven_number);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                String phone=et_phone.getEditTextStr();
                GetPhoneVerifyLogic.OnGetVerifyCallBack callBack =new GetPhoneVerifyLogic.OnGetVerifyCallBack() {
                    @Override
                    public void onGetVerifySuccess() {
                        PhoneGetPwActivity2.actionStart(PhoneGetPwActivity1.this, et_phone.getEditTextStr());
                    }

                    @Override
                    public void onGetVerifyFailed(String failMsg) {
                        tv_error.setText(failMsg);
                        tv_error.setVisibility(View.VISIBLE);
                    }
                };
                GetPhoneVerifyLogic.doGetPhoneVerify(phone, TypeConstants.GET_VERIFY_TYPE.GET_PW,callBack);

            }
        });
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, PhoneGetPwActivity1.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

}
