package com.alpha.alphaapp.ui.forgetpw.wx.hasregister;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.check.CheckAccoutLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.forgetpw.wx.WxGetPwActivity3;
import com.alpha.alphaapp.ui.widget.TitleLayout;
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
    private LinearLayout tv_error_layout;
    private TextView tv_error;
    private ImageView tv_error_iv;
    private Button btn_resetpw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_getpw_no_account_2;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.wx_getpw_no_account_titlelayout);
        et_account = (AccountEditText) findViewById(R.id.wx_getpw_no_account_aet_account);
        tv_error_layout = (LinearLayout) findViewById(R.id.wx_getpw_no_account_tv_error_layout);
        tv_error = (TextView) findViewById(R.id.wx_getpw_no_account_tv_error);
        tv_error_iv = (ImageView) findViewById(R.id.wx_getpw_no_account_tv_error_iv);
        btn_resetpw = (Button) findViewById(R.id.wx_getpw_no_account_btn_resetpw);

        //设置两行提示文字的图标
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, (int) (tv_error.getLineHeight() * 0.3), 0, 0);
        tv_error_iv.setLayoutParams(lp);
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
                    btn_resetpw.setBackgroundResource(R.drawable.shape_btn_bg_gray);
                    et_account.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    btn_resetpw.setEnabled(Boolean.TRUE);
                    btn_resetpw.setBackgroundResource(R.drawable.shape_btn_bg_blue);
                    et_account.getImageViewRight().setVisibility(View.INVISIBLE);
                }
                tv_error_layout.setVisibility(View.INVISIBLE);
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
            tv_error_layout.setVisibility(View.VISIBLE);
        }
        CheckAccoutLogic.OnCheckAccountCallBack listener = new CheckAccoutLogic.OnCheckAccountCallBack() {
            @Override
            public void checkSucessed(boolean isHas, String result) {
                if (isHas) {
                    tv_error.setText(result);
                    tv_error_layout.setVisibility(View.VISIBLE);
                } else {
                    WxGetPwActivity3.actionStart(WxGetPwActivityNoAccount.this, et_account.getEditTextStr(), WxGetPwActivity3.UNBIND);
                }
            }

            @Override
            public void checkFailed(String errorMsg) {

            }
        };
        CheckAccoutLogic.checkAccountIsHas(et_account.getEditTextStr(), TypeConstants.ACCOUNT_TYPE.ACCOUNT, listener);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WxGetPwActivityNoAccount.class);
        context.startActivity(intent);
    }

}
