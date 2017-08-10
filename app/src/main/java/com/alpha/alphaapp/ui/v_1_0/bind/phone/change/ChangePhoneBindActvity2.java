package com.alpha.alphaapp.ui.v_1_0.bind.phone.change;

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
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.userinfo.ChangeBindPhoneLogic;
import com.alpha.alphaapp.model.v_1_0.verifycode.GetPhoneVerifyLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.login.LoginActivity;
import com.alpha.alphaapp.ui.widget.OneTwoThreeItemView;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.et.InputVerifyEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/13 15:30
 * Email : xiaokai090704@126.com
 * 更改绑定手机的第二个页面
 */

public class ChangePhoneBindActvity2 extends BaseActivity {
    private static final String TAG = "ChangePhoneBindActvity2";
    private TitleLayout titleLayout;
    private OneTwoThreeItemView ott;
    private AccountEditText et_phone;
    private InputVerifyEditText ivet;
    private ErrorTextView tv_error;
    private Button btn_bind;

    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone_bind_2;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.change_phone_bind_2_titlelayout);
        ott = (OneTwoThreeItemView) findViewById(R.id.change_phone_bind_2_ott);
        et_phone = (AccountEditText) findViewById(R.id.change_phone_bind_2_aet_phone);
        ivet = (InputVerifyEditText) findViewById(R.id.change_phone_bind_2_ivet);
        tv_error = (ErrorTextView) findViewById(R.id.change_phone_bind_2_tv_error);
        btn_bind = (Button) findViewById(R.id.change_phone_bind_2_btn_bind);

        dialog = DialogUtils.createSingleChoiceDialog(this, R.string.bind_success, R.string.you_use_phone_relogin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNull(dialog) && dialog.isShowing())
                    dialog.dismiss();
                //进入登录界面和退出登录是不是一回事。。。
                LoginActivity.actionStartClearStack(ChangePhoneBindActvity2.this, null, null);
                finish();
            }
        });
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
                if (Util.isNullOrBlank(et_phone.getEditTextStr()) && Util.isNullOrBlank(ivet.getEditTextStr())) {
                    btn_bind.setEnabled(Boolean.FALSE);
                    btn_bind.setBackgroundResource(R.drawable.shape_com_bg_gray);

                } else {
                    btn_bind.setEnabled(Boolean.TRUE);
                    btn_bind.setBackgroundResource(R.drawable.shape_com_bg_red);

                }

                if (Util.isNullOrBlank(et_phone.getEditTextStr())) {
                    et_phone.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    et_phone.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivet.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_phone.getEditTextStr()) && Util.isNullOrBlank(ivet.getEditTextStr())) {
                    btn_bind.setEnabled(Boolean.FALSE);
                    btn_bind.setBackgroundResource(R.drawable.shape_com_bg_gray);

                } else {
                    btn_bind.setEnabled(Boolean.TRUE);
                    btn_bind.setBackgroundResource(R.drawable.shape_com_bg_red);
                    et_phone.getImageViewRight().setVisibility(View.VISIBLE);
                }
                if (Util.isNullOrBlank(ivet.getEditTextStr())) {
                    ivet.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    ivet.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivet.setGetVerifyListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivet.getVerify(et_phone.getEditTextStr(),TypeConstants.GET_VERIFY_TYPE.BIND_NEW_PHONE,tv_error);
            }
        });
        btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneNum(et_phone.getEditTextStr())) {
                    tv_error.setText(R.string.input_valid_eleven_number);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (!StringUtils.isPhoneVerify(ivet.getEditTextStr())) {
                    tv_error.setText(R.string.verify_form_error);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                String sskey = AccountManager.getInstance().getSskey();


                OnModelCallback<Object> callBack = new OnModelCallback<Object>() {
                    @Override
                    public void onModelSuccessed(Object o) {
                        ott.setCurrentUI(3);
                        if (!Util.isNull(dialog) && !dialog.isShowing())
                            dialog.show();
                    }

                    @Override
                    public void onModelFailed(String failedMsg) {
                        tv_error.setText(failedMsg);
                        tv_error.setVisibility(View.VISIBLE);
                    }
                };
                ChangeBindPhoneLogic.doBindNewPhone(sskey, et_phone.getEditTextStr(), ivet.getEditTextStr(), callBack);
            }
        });

    }



    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ChangePhoneBindActvity2.class);
        intent.putExtra("params", data1);
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
}
