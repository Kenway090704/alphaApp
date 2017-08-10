package com.alpha.alphaapp.ui.v_1_0.forgetpw.wx.hasregister;

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
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.register.CheckAccoutLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.forgetpw.wx.WxGetPwActivity3;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/20 14:12
 * Email : xiaokai090704@126.com
 */

public class WxGetPwActivityNoAccount extends BaseActivity {
    private static final String TAG = "WxGetPwActivityNoAccount";
    private TitleLayout titleLayout;
    private AccountEditText et_account;

    private ErrorTextView tv_error;

    private Button btn_resetpw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_getpw_no_account_2;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.wx_getpw_no_account_titlelayout);
        et_account = (AccountEditText) findViewById(R.id.wx_getpw_no_account_aet_account);

        tv_error = (ErrorTextView) findViewById(R.id.wx_getpw_no_account_tv_error);

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
                if (Util.isNullOrBlank(et_account.getEditTextStr())) {
                    btn_resetpw.setEnabled(Boolean.FALSE);
                    btn_resetpw.setBackgroundResource(R.drawable.shape_com_bg_gray);
                    et_account.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    btn_resetpw.setEnabled(Boolean.TRUE);
                    btn_resetpw.setBackgroundResource(R.drawable.shape_com_bg_red);
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
        if (!StringUtils.isAccountLine(et_account.getEditTextStr())) {
            tv_error.setText(R.string.account_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }


        OnModelCallback<Boolean> callback = new OnModelCallback<Boolean>() {
            @Override
            public void onModelSuccessed(Boolean isHas) {
                if (isHas) {
                    tv_error.setText(R.string.account_had);
                    tv_error.setVisibility(View.VISIBLE);
                } else {
                    WxGetPwActivity3.actionStart(WxGetPwActivityNoAccount.this, et_account.getEditTextStr(), WxGetPwActivity3.UNBIND);
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e(TAG, "failedMsg==" + failedMsg);
                ToastUtils.showToast(WxGetPwActivityNoAccount.this, failedMsg);
            }
        };
        CheckAccoutLogic.checkAccountIsHas(et_account.getEditTextStr(), TypeConstants.ACCOUNT_TYPE.ACCOUNT, callback);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WxGetPwActivityNoAccount.class);
        context.startActivity(intent);
    }

}
