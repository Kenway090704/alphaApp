package com.alpha.alphaapp.ui.v_1_0.forgetpw.wx;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.userinfo.BindLogic;
import com.alpha.alphaapp.model.v_1_0.pw.ResetPwLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.login.LoginActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/6 15:06
 * Email : xiaokai090704@126.com
 * 找回密码最后一个界面
 */

public class WxGetPwActivity3 extends BaseActivity {
    private static final String TAG = "WxGetPwActivity3";
    public static final boolean BIND = true;
    public static final boolean UNBIND = false;
    private TitleLayout titleLayout;
    private AccountEditText aet_pw;
    private ErrorTextView tv_error;
    private Button btn_submit;
    private String accont;
    private boolean isBind;

    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        accont = getIntent().getStringExtra("account");
        isBind = getIntent().getBooleanExtra("isbind", false);
        return R.layout.activity_wx_getpw_3;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.wx_getpw3_titlelayout);

        aet_pw = (AccountEditText) findViewById(R.id.wx_getpw3_aet_pw);
        tv_error = (ErrorTextView) findViewById(R.id.wx_getpw3_tv_error);
        btn_submit = (Button) findViewById(R.id.wx_getpw3_btn_submit);

        dialog= DialogUtils.createSingleChoiceDialog(this, R.string.pw_reset_success,R.string.plz_use_new_login_now, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.actionStartClearStack(WxGetPwActivity3.this, null, null);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        aet_pw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(aet_pw.getEditTextStr())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_com_bg_gray);
                    aet_pw.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_com_bg_red);
                    aet_pw.getImageViewRight().setVisibility(View.VISIBLE);
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
                //如果是绑定的帐号,则调用重置密码,如果是新帐号则,创建新帐号
                if (isBind) {
                    resetPw();
                } else {
                    bindnewAccount();
                }
            }


        });

    }

    /**
     * 修改密码
     */
    private void resetPw() {
        if (Util.isNullOrBlank(aet_pw.getEditTextStr())) {
            tv_error.setText(R.string.pw_isnot_empty);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPWLine(aet_pw.getEditTextStr())) {
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }

        String pw = aet_pw.getEditTextStr();

        OnModelCallback<Object> call=new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                dialog.show();
            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e(failedMsg);

            }
        };
        ResetPwLogic.doResetPw(accont, pw, call);
    }

    /**
     * 微信未绑定帐号,因此执行的是绑定帐号
     */
    private void bindnewAccount() {
        if (Util.isNullOrBlank(aet_pw.getEditTextStr())) {
            tv_error.setText(R.string.pw_isnot_empty);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPWLine(aet_pw.getEditTextStr())) {
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        //判断sskey是否为空
        boolean isSskeyful = AccountManager.getInstance().isSskeyIsNul(WxGetPwActivity3.this);
        if (isSskeyful) {
            return;
        }
        String sskey = AccountManager.getInstance().getSskey();
        String pw = aet_pw.getEditTextStr();

        OnModelCallback<Object> call = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                dialog.show();
            }

            @Override
            public void onModelFailed(String failedMsg) {
                tv_error.setText(failedMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        BindLogic.doBindAccountOrPhone(sskey, accont, pw, TypeConstants.ACCOUNT_TYPE.ACCOUNT, call);
    }

    public static void actionStart(Context context, String account, boolean isBind) {
        Intent intent = new Intent(context, WxGetPwActivity3.class);
        intent.putExtra("account", account);
        intent.putExtra("isbind", isBind);
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
