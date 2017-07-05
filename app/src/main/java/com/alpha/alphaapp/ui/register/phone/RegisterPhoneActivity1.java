package com.alpha.alphaapp.ui.register.phone;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.check.CheckAccoutLogic;
import com.alpha.alphaapp.model.other.GetPhoneVerifyLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/22 10:04
 * Email : xiaokai090704@126.com
 */

public class RegisterPhoneActivity1 extends BaseActivity {
    private static final String TAG = "RegisterAccountActivity";
    private AccountEditText aet_phone;
    private TextView tv_error;
    private Button btn_getVerify;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_phone_1;
    }

    @Override
    protected void initView() {
        aet_phone = (AccountEditText) findViewById(R.id.reg_phone_1_aet_phone);
        tv_error = (TextView) findViewById(R.id.reg_phone_1_tv_error);
        btn_getVerify = (Button) findViewById(R.id.reg_phone_1_btn_getverfify);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        aet_phone.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEditTextNull();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_getVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPhoneHasReg();
            }
        });
    }

    /**
     * 检测帐号是否注册
     */
    private void checkPhoneHasReg() {
        if (!StringUtils.isPhoneNum(aet_phone.getEditTextStr())) {
            tv_error.setText(R.string.input_valid_eleven_number);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        //判断是否存在该帐号
        final String phone = aet_phone.getEditTextStr();
        CheckAccoutLogic.OnCheckAccountCallBack callBack = new CheckAccoutLogic.OnCheckAccountCallBack() {
            @Override
            public void checkSucessed(boolean isHas, String result) {
                if (isHas) {
                    tv_error.setText(result);
                    tv_error.setVisibility(View.VISIBLE);
                } else {
                    //请求验证码,请求成功后进入第二个页面

                    doGetVerify(phone);
                    RegisterPhoneActivity2.actionStart(RegisterPhoneActivity1.this, phone);
                }
            }

            @Override
            public void checkFailed(String errorMsg) {

            }
        };
        CheckAccoutLogic.checkAccountIsHas(phone, TypeConstants.ACCOUNT_TYPE.PHONE, callBack);
    }

    /**
     * 请求验证码,请求成功后进入第二个界面
     */
    private void doGetVerify(final String phone) {
        GetPhoneVerifyLogic.OnGetVerifyCallBack callback = new GetPhoneVerifyLogic.OnGetVerifyCallBack() {
            @Override
            public void onGetVerifySuccess() {
                RegisterPhoneActivity2.actionStart(RegisterPhoneActivity1.this, phone);
            }

            @Override
            public void onGetVerifyFailed(String failMsg) {
                tv_error.setText(failMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        GetPhoneVerifyLogic.doGetPhoneVerify(phone, TypeConstants.GET_VERIFY.REGISTER, callback);
    }

    /**
     * 判断EditText是否为空
     */
    private void isEditTextNull() {
        if (Util.isNullOrBlank(aet_phone.getEditTextStr())) {
            btn_getVerify.setEnabled(Boolean.FALSE);
            btn_getVerify.setBackgroundResource(R.drawable.shape_btn_bg_gray);
            aet_phone.getImageViewRight().setVisibility(View.INVISIBLE);
        } else {
            btn_getVerify.setEnabled(Boolean.TRUE);
            btn_getVerify.setBackgroundResource(R.drawable.shape_btn_bg_blue);
            aet_phone.getImageViewRight().setVisibility(View.VISIBLE);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterPhoneActivity1.class);
        context.startActivity(intent);
    }


}
