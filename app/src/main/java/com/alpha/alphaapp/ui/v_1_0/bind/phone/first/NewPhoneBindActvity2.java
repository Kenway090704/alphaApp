package com.alpha.alphaapp.ui.v_1_0.bind.phone.first;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.userinfo.BindLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.set.AccountSecurityActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.et.InputVerifyEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/13 15:30
 * Email : xiaokai090704@126.com
 */

public class NewPhoneBindActvity2 extends BaseActivity implements TextWatcher {
    private static final String TAG = "NewPhoneBindActvity2";
    private TitleLayout titleLayout;
    private InputVerifyEditText ivet;
    private ErrorTextView tv_error;
    private Button btn_bind;

    private String phone;

    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        phone = getIntent().getStringExtra("phone");
        return R.layout.activity_phone_bind_2;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.phone_bind_2_titlelayout);

        ivet = (InputVerifyEditText) findViewById(R.id.phone_bind_2_ivet);
        ivet.start();
        tv_error = (ErrorTextView) findViewById(R.id.phone_bind_2_tv_error);
        btn_bind = (Button) findViewById(R.id.phone_bind_2_btn_bind);

        dialog = DialogUtils.createSingleChoiceDialog(this, R.string.bind_success, R.string.you_use_phone_login, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AccountSecurityActivity.actionStar(NewPhoneBindActvity2.this, null, null);
                finish();
            }
        });



    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        ivet.setGetVerifyTextViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //再一次获取验证码
            }
        });
        ivet.setWatcherListener(this);
        btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneVerify(ivet.getEditTextStr())) {
                    //验证手机号格式是否正确
                    tv_error.setText(R.string.verify_form_error);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                String sskey = AccountManager.getInstance().getSskey();
                String verify = ivet.getEditTextStr();


                OnModelCallback<Object> call = new OnModelCallback<Object>() {
                    @Override
                    public void onModelSuccessed(Object o) {
                        //弹出对话框,提示绑定成功!
                        //弹出对话框架提示绑定成功,然后重新加载一次信息
                        AccountManager.getInstance().loadUserinfo();
                        dialog.show();
                    }

                    @Override
                    public void onModelFailed(String failedMsg) {
                        tv_error.setText(failedMsg);
                        tv_error.setVisibility(View.VISIBLE);
                    }
                };
                BindLogic.doBindAccountOrPhone(sskey, phone, verify, TypeConstants.ACCOUNT_TYPE.PHONE, call);
            }
        });

    }

    public static void actionStart(Context context, String phone, String data2) {
        Intent intent = new Intent(context, NewPhoneBindActvity2.class);
        intent.putExtra("phone", phone);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Util.isNull(ivet))
            ivet.cancel();
        if (!Util.isNull(dialog))
            dialog.dismiss();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (Util.isNullOrBlank(ivet.getEditTextStr())) {
            btn_bind.setEnabled(Boolean.FALSE);
            btn_bind.setBackgroundResource(R.drawable.shape_com_bg_gray);
            ivet.getImageViewRight().setVisibility(View.INVISIBLE);
        } else {
            btn_bind.setEnabled(Boolean.TRUE);
            btn_bind.setBackgroundResource(R.drawable.shape_bg_red);
            ivet.getImageViewRight().setVisibility(View.VISIBLE);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
