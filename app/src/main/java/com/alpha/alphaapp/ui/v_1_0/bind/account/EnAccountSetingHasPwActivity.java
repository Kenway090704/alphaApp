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
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.set.AccountSecurityActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/14 14:18
 * Email : xiaokai090704@126.com
 * 手机+密码登录 设置未注册的英文帐号
 */

public class EnAccountSetingHasPwActivity extends BaseActivity {
    private static final String TAG = "EnAccountSetingHasPwActivity";
    private TitleLayout titleLayout;
    private EmptyEditText aet_account;
    private ErrorTextView tv_error;
    private Button btn_save;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_account_haspw;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.set_acc_haspw_titlelayout);
        aet_account = (EmptyEditText) findViewById(R.id.set_acc_haspw_aet_account);
        tv_error = (ErrorTextView) findViewById(R.id.set_acc_haspw_tv_error);
        btn_save = (Button) findViewById(R.id.set_acc_haspw_btn_save);
        dialog = DialogUtils.createSingleChoiceDialog(this, R.string.set_en_account_sucess, R.string.you_use_account_login, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountSecurityActivity.actionStar(EnAccountSetingHasPwActivity.this, null, null);
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
                if (Util.isNullOrBlank(aet_account.getEditTextStr())) {
                    btn_save.setEnabled(Boolean.FALSE);
                    btn_save.setBackgroundResource(R.drawable.shape_com_bg_gray);
                    aet_account.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    btn_save.setEnabled(Boolean.TRUE);
                    btn_save.setBackgroundResource(R.drawable.shape_com_bg_red);
                    aet_account.getImageViewRight().setVisibility(View.VISIBLE);
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
                    //验证手机号格式是否正确
                    tv_error.setText(R.string.account_format);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                String sskey = AccountManager.getInstance().getSskey();
                String account = aet_account.getEditTextStr();

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
                SetEnAccountLogic.doSetEnAccountForHasPw(sskey, account, back);
            }
        });


    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, EnAccountSetingHasPwActivity.class);
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
