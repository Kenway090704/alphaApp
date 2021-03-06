package com.alpha.alphaapp.ui.v_1_0.register.phone;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.register.CheckAccoutLogic;
import com.alpha.alphaapp.model.v_1_0.verifycode.GetPhoneVerifyLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/22 10:04
 * Email : xiaokai090704@126.com
 */

public class RegisterPhoneActivity1 extends BaseActivity {
    private static final String TAG = "RegisterAccountActivity";
    private AccountEditText aet_phone;
    private ErrorTextView tv_error;
    private Button btn_getVerify;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_phone_1;
    }

    @Override
    protected void initView() {
        aet_phone = (AccountEditText) findViewById(R.id.reg_phone_1_aet_phone);
        tv_error = (ErrorTextView) findViewById(R.id.reg_phone_1_tv_error);
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

        OnModelCallback<Boolean> callback=new OnModelCallback<Boolean>() {
            @Override
            public void onModelSuccessed(Boolean isHas) {
                if (isHas) {
                    tv_error.setText(R.string.phone_had);
                    tv_error.setVisibility(View.VISIBLE);
                } else {
                    //请求验证码,请求成功后进入第二个页面
                    doGetVerify(phone);
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e(failedMsg);

            }
        };
        CheckAccoutLogic.checkAccountIsHas(phone, TypeConstants.ACCOUNT_TYPE.PHONE, callback);
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
        GetPhoneVerifyLogic.doGetPhoneVerify(phone, TypeConstants.GET_VERIFY_TYPE.REGISTER, callback);
    }

    /**
     * 判断EditText是否为空
     */
    private void isEditTextNull() {
        if (Util.isNullOrBlank(aet_phone.getEditTextStr())) {
            btn_getVerify.setEnabled(Boolean.FALSE);
            btn_getVerify.setBackgroundResource(R.drawable.shape_com_bg_gray);
            aet_phone.getImageViewRight().setVisibility(View.INVISIBLE);
        } else {
            btn_getVerify.setEnabled(Boolean.TRUE);
            btn_getVerify.setBackgroundResource(R.drawable.shape_com_bg_red);
            aet_phone.getImageViewRight().setVisibility(View.VISIBLE);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterPhoneActivity1.class);
        context.startActivity(intent);
    }


}
