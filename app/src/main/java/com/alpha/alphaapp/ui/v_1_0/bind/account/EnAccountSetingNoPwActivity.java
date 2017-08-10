package com.alpha.alphaapp.ui.v_1_0.bind.account;

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
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.alphaapp.ui.widget.et.EmptyEditText;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.userinfo.SetEnAccountLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.set.AccountSecurityActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/14 14:18
 * Email : xiaokai090704@126.com
 * 社交帐号授权登录,设置未注册的英文帐号
 */

public class EnAccountSetingNoPwActivity extends BaseActivity {
    private static final String TAG = "EnAccountSetingNoPwActivity";
    private TitleLayout titleLayout;
    private EmptyEditText aet_account, aet_pw;
    private ErrorTextView tv_error;
    private Button btn_save;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_account_nopw;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.set_acc_nopw_titlelayout);
        aet_account = (EmptyEditText) findViewById(R.id.set_acc_nopw_aet_account);
        aet_pw = (EmptyEditText) findViewById(R.id.set_acc_nopw_aet_pw);
        tv_error = (ErrorTextView) findViewById(R.id.set_acc_nopw_tv_error);
        btn_save = (Button) findViewById(R.id.set_acc_nopw_btn_save);
        dialog = DialogUtils.createSingleChoiceDialog(this, R.string.set_en_account_sucess, R.string.you_use_account_login, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNull(dialog) && dialog.isShowing())
                    dialog.dismiss();
                AccountSecurityActivity.actionStar(EnAccountSetingNoPwActivity.this, null, null);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        aet_account.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Util.isNullOrBlank(aet_account.getEditTextStr()) || Util.isNullOrBlank(aet_pw.getEditTextStr())) {
                    btn_save.setEnabled(Boolean.FALSE);
                    btn_save.setBackgroundResource(R.drawable.shape_com_bg_gray);

                } else {
                    btn_save.setEnabled(Boolean.TRUE);
                    btn_save.setBackgroundResource(R.drawable.shape_com_bg_red);

                }
                if (Util.isNullOrBlank(aet_account.getEditTextStr())) {
                    aet_account.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    aet_account.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aet_pw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Util.isNullOrBlank(aet_account.getEditTextStr()) || Util.isNullOrBlank(aet_pw.getEditTextStr())) {
                    btn_save.setEnabled(Boolean.FALSE);
                    btn_save.setBackgroundResource(R.drawable.shape_com_bg_gray);

                } else {
                    btn_save.setEnabled(Boolean.TRUE);
                    btn_save.setBackgroundResource(R.drawable.shape_com_bg_red);

                }
                if (Util.isNullOrBlank(aet_pw.getEditTextStr())) {
                    aet_pw.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    aet_pw.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isAccountLine(aet_account.getEditTextStr())) {
                    //验证帐号格式是否ok
                    tv_error.setText(R.string.account_alpha_format);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                if (!StringUtils.isPWLine(aet_pw.getEditTextStr())) {
                    tv_error.setText(R.string.pw_error_format);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                String sskey = AccountManager.getInstance().getSskey();
                String account = aet_account.getEditTextStr();
                String pw = aet_pw.getEditTextStr();


                OnModelCallback<Object> back = new OnModelCallback<Object>() {
                    @Override
                    public void onModelSuccessed(Object o) {
                        AccountManager.getInstance().loadUserinfo();
                        dialog.show();
                    }

                    @Override
                    public void onModelFailed(String failedMsg) {
                        tv_error.setText(failedMsg);
                        tv_error.setVisibility(View.VISIBLE);
                    }
                };
                SetEnAccountLogic.doSetEnAccountForNoPw(sskey, account, pw, back);
            }
        });

    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, EnAccountSetingNoPwActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Util.isNull(dialog))
            dialog.dismiss();
    }
}
