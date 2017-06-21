package com.alpha.alphaapp.ui.forgetpw.wx;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.bind.BindLogic;
import com.alpha.alphaapp.model.resetpw.ResetPwInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.login.LoginActivity;
import com.alpha.alphaapp.ui.widget.dialog.CustomAlertDialog;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
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
    private EditText et_pw;
    private TextView tv_error;
    private Button btn_submit;
    private String accont;
    private boolean isBind;

    private CustomAlertDialog dialog;

    @Override
    protected int getLayoutId() {
        accont = getIntent().getStringExtra("account");
        isBind = getIntent().getBooleanExtra("isbind", false);
        return R.layout.activity_wx_getpw_3;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.wx_getpw3_titlelayout);

        et_pw = (EditText) findViewById(R.id.wx_getpw3_et_pw);
        tv_error = (TextView) findViewById(R.id.wx_getpw3_tv_error);
        btn_submit = (Button) findViewById(R.id.wx_getpw3_btn_submit);
        dialog = new CustomAlertDialog(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        et_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_pw.getText().toString())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_blue);
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
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.actionStartClearStack(WxGetPwActivity3.this, null, null);
            }
        });
    }

    /**
     * 修改密码
     */
    private void resetPw() {
        if (Util.isNullOrBlank(et_pw.getText().toString())) {
            tv_error.setText(R.string.pw_isnot_empty);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPWLine(et_pw.getText().toString())) {
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        String pw = et_pw.getText().toString();
        String data = ResetPwInfo.getJsonStrForAccount(accont, pw);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.RESET_PW_RESULT.RESULT_OK:
                        dialog.show();
                        break;
                    case CommStants.RESET_PW_RESULT.RESULT_ACCOUNT_NOHAD:
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
            }
        };
        RequestManager.getInstance(getApplicationContext()).requestPostByJsonAsyn(URLConstans.URL.RESET_PW, json, callBack);

    }

    /**
     * 微信未绑定帐号,因此执行的是绑定帐号
     */
    private void bindnewAccount() {
        if (Util.isNullOrBlank(et_pw.getText().toString())) {
            tv_error.setText(R.string.pw_isnot_empty);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPWLine(et_pw.getText().toString())) {
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
        String pw = et_pw.getText().toString();
        String data = BindLogic.getJsonforBindAccount(sskey, accont, pw);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);

                switch (info.getResult()) {
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_OK:
                        dialog.show();
                        break;
                }


            }

            @Override
            public void onReqFailed(String errorMsg) {
            }
        };
        RequestManager.getInstance(getApplicationContext()).requestPostByJsonAsyn(URLConstans.URL.BIND, json, callBack);
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
