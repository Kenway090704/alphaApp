package com.alpha.alphaapp.ui.forgetpw.wx.noregister;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.check.CheckAccoutLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.forgetpw.wx.WxGetPwActivity3;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.dialog.CustomLoadingDialog;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/20 14:12
 * Email : xiaokai090704@126.com
 */

public class WxGetPwActivityNoAccount extends BaseActivity {
    private static final String TAG = "WxGetPwActivityNoAccount";
    private TitleLayout titleLayout;
    private AccountEditText et_account;
    private TextView tv_error;
    private Button btn_resetpw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_getpw_no_account_2;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.wx_getpw_no_account_titlelayout);
        et_account = (AccountEditText) findViewById(R.id.wx_getpw_no_account_aet_account);
        tv_error = (TextView) findViewById(R.id.wx_getpw_no_account_tv_error);
        btn_resetpw = (Button) findViewById(R.id.wx_getpw_no_account_btn_resetpw);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

        et_account.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_account.getText().toString())) {
                    btn_resetpw.setEnabled(Boolean.FALSE);
                    btn_resetpw.setBackgroundResource(R.drawable.shape_btn_bg_gray);
                    et_account.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    btn_resetpw.setEnabled(Boolean.TRUE);
                    btn_resetpw.setBackgroundResource(R.drawable.shape_btn_bg_blue);
                    et_account.getImageViewRight().setVisibility(View.INVISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
                tv_error.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_resetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNext();
            }
        });

    }

    /**
     * 检测帐号是否存在,不存在则进入下一个界面
     */
    private void doNext() {
        if (!StringUtils.isAccountLine(et_account.getText().toString())) {
            tv_error.setText(R.string.account_format);
            tv_error.setVisibility(View.VISIBLE);
        }
        CheckAccoutLogic.OnCheckAccountListener listener = new CheckAccoutLogic.OnCheckAccountListener() {
            @Override
            public void checkSucessed(boolean isHas, String result) {
                if (isHas) {
                    tv_error.setText(result);
                    tv_error.setVisibility(View.VISIBLE);
                } else {
                    WxGetPwActivity3.actionStart(WxGetPwActivityNoAccount.this, et_account.getText().toString(), WxGetPwActivity3.UNBIND);
                }
            }

            @Override
            public void checkFailed(String errorMsg) {

            }
        };
        CheckAccoutLogic.checkAccountIsHas(et_account.getText().toString(), CommStants.ACCOUNT_TYPE.ACCOUNT, listener);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WxGetPwActivityNoAccount.class);
        context.startActivity(intent);
    }

}
