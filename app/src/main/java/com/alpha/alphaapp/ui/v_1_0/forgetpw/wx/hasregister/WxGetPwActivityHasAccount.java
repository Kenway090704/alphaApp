package com.alpha.alphaapp.ui.v_1_0.forgetpw.wx.hasregister;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.forgetpw.wx.WxGetPwActivity3;
import com.alpha.alphaapp.ui.widget.TitleLayout;

/**
 * Created by kenway on 17/6/20 14:12
 * Email : xiaokai090704@126.com
 */

public class WxGetPwActivityHasAccount extends BaseActivity {
    private static final String TAG = "WxGetPwActivityHasAccount";
    private TitleLayout titleLayout;
    private TextView tv_msg;
    private Button btn_reset;
    private UserInfo info;

    @Override
    protected int getLayoutId() {
        info = AccountManager.getInstance().getUserInfo();
        return R.layout.activity_wx_getpw_has_account_2;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.wx_getpw_has_account_titlelayout);
        tv_msg = (TextView) findViewById(R.id.wx_getpw_has_account_tv);
        String format = getResources().getString(R.string.your_alpha_account_format);
        tv_msg.setText(format + info.getAccount());
        btn_reset = (Button) findViewById(R.id.wx_getpw_has_account_btn_reset);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入到第三个界面
                WxGetPwActivity3.actionStart(WxGetPwActivityHasAccount.this, info.getAccount(), WxGetPwActivity3.BIND);
            }
        });

    }

    public static void actionStart(Context context, String account, String data2) {
        Intent intent = new Intent(context, WxGetPwActivityHasAccount.class);
        intent.putExtra("account", account);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

}
