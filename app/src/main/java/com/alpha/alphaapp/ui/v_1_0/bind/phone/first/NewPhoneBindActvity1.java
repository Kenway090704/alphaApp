package com.alpha.alphaapp.ui.v_1_0.bind.phone.first;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.verifycode.GetPhoneVerifyLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/13 15:30
 * Email : xiaokai090704@126.com
 */

public class NewPhoneBindActvity1 extends BaseActivity {
    private static final String TAG = "NewPhoneBindActvity1";
    private TitleLayout titleLayout;
    private AccountEditText et_phone;
    private ErrorTextView tv_error;
    private Button btn_getVerify;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_bind_1;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.phone_bind_1_titlelayout);
        et_phone = (AccountEditText) findViewById(R.id.phone_bind_1_aet_phone);
        tv_error = (ErrorTextView) findViewById(R.id.phone_bind_1_tv_error);
        btn_getVerify = (Button) findViewById(R.id.phone_bind_1_btn_getVerify);
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
                if (Util.isNullOrBlank(et_phone.getEditTextStr())) {
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
                GetPhoneVerifyLogic.OnGetVerifyCallBack callBack = new GetPhoneVerifyLogic.OnGetVerifyCallBack() {
                    @Override
                    public void onGetVerifySuccess() {
                        NewPhoneBindActvity2.actionStart(NewPhoneBindActvity1.this, et_phone.getEditTextStr(), null);
                    }

                    @Override
                    public void onGetVerifyFailed(String failMsg) {
                        tv_error.setText(failMsg);
                        tv_error.setVisibility(View.VISIBLE);
                    }
                };
                GetPhoneVerifyLogic.doGetPhoneVerify(et_phone.getEditTextStr(), TypeConstants.GET_VERIFY_TYPE.BIND_PHONE, callBack);
            }
        });

    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, NewPhoneBindActvity1.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }


}
