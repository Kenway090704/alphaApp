package com.alpha.alphaapp.ui.set;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.bind.account.EnAccountSetingHasPwActivity;
import com.alpha.alphaapp.ui.bind.account.EnAccountSetingNoPwActivity;
import com.alpha.alphaapp.ui.bind.phone.change.ChangePhoneBindActvity1;
import com.alpha.alphaapp.ui.bind.phone.first.NewPhoneBindActvity1;
import com.alpha.alphaapp.ui.modifypw.phone.ModifyPwByPhoneActivity1;
import com.alpha.alphaapp.ui.modifypw.ModifyPwByPwActivity;
import com.alpha.alphaapp.ui.widget.AccountBindItemView;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * 帐号管理页面
 */
public class AccountSecurityActivity extends BaseActivity {
    private static final String TAG = "AccountSecurityActivity";
    private TitleLayout titleLayout;

    private TextView tv_curent;

    private AccountBindItemView abi_alpha, abi_phone, abi_wx, abi_qq, abi_pweditpw, abi_phoneeditpw;

    private UserInfo info;


    @Override
    protected int getLayoutId() {
        info = AccountManager.getInstance().getUserInfo();
        return R.layout.activity_account_security;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.account_sec_titlelayout);
        titleLayout.setTitleText(R.string.settings_account_bind_set);
        tv_curent = (TextView) findViewById(R.id.account_sec_tv_current);
        abi_alpha = (AccountBindItemView) findViewById(R.id.account_sec_abi_alpha_acc);
        abi_phone = (AccountBindItemView) findViewById(R.id.account_sec_abi_phone);
        abi_wx = (AccountBindItemView) findViewById(R.id.account_sec_abi_weixin);
        abi_qq = (AccountBindItemView) findViewById(R.id.account_sec_abi_qq);
        abi_pweditpw = (AccountBindItemView) findViewById(R.id.account_sec_abi_pweditpw);
        abi_phoneeditpw = (AccountBindItemView) findViewById(R.id.account_sec_abi_phoneeditpw);
        setViewMsg();
    }

    private void setViewMsg() {
        // 判断是登录的类型,然后选择对应的帐号显示
        String format = getResources().getString(R.string.current_account);
        switch (AccountManager.getInstance().getLoginType()) {
            case TypeConstants.LOGIN_TYPE.PHONE_QUICK:
                //手机快捷登录
                break;
            case TypeConstants.LOGIN_TYPE.ACCONUT_PW:
                tv_curent.setText(format + info.getAccount());
                break;
            case TypeConstants.LOGIN_TYPE.PHONE_PW:
                tv_curent.setText(format + info.getMobile());
                break;
            case TypeConstants.LOGIN_TYPE.AUTH_WX:
//                tv_curent.setText(format + info.getName());
                tv_curent.setText(format + info.getOpenid_weixin());
                break;
            case TypeConstants.LOGIN_TYPE.AUTH_QQ:
                //tv_curent.setText(format + info.getQq());
                tv_curent.setText(format + info.getOpenid_qq());
                break;

        }
        if (!Util.isNullOrBlank(info.getAccount())) {
            abi_alpha.setMsg(info.getAccount());
            abi_alpha.setRightTxtAndBg("已设定", R.drawable.shape_btn_bg_gray);
        } else {
            abi_alpha.setRightTxtAndBg("立即设置", R.drawable.shape_btn_bg_blue);
        }
        if (!Util.isNullOrBlank(info.getMobile())) {
            abi_phone.setMsg(info.getMobile());
            abi_phone.setRightTxtAndBg("更改绑定", R.drawable.shape_btn_bg_blue);
        } else {
            abi_phone.setRightTxtAndBg("+ 绑定", R.drawable.shape_btn_bg_blue);
        }
        if (!Util.isNullOrBlank(info.getOpenid_qq())) {
            abi_qq.setMsg(info.getOpenid_qq());
            abi_qq.setRightTxtAndBg("已绑定", R.drawable.shape_btn_bg_gray);
        } else {
            abi_qq.setRightTxtAndBg("+ 绑定", R.drawable.shape_btn_bg_blue);
        }
        //微信这里要使用getName,现在使用openid
        if (!Util.isNullOrBlank(info.getOpenid_weixin())) {
            abi_wx.setMsg(info.getOpenid_weixin());
            abi_wx.setRightTxtAndBg("已绑定", R.drawable.shape_btn_bg_gray);
        } else {
            abi_wx.setRightTxtAndBg("+ 绑定", R.drawable.shape_btn_bg_blue);
        }
//        if (!Util.isNullOrBlank(info.getName())) {
//            abi_wx.setMsg(info.getName());
//            abi_wx.setRightTxtAndBg("已绑定", R.drawable.shape_btn_bg_gray);
//        } else {
//            abi_wx.setRightTxtAndBg("+ 绑定", R.drawable.shape_btn_bg_blue);
//        }
    }

    @Override
    public void initData() {
        //获取当前帐号的信息

    }

    @Override
    protected void initListener() {
        abi_alpha.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typeLogin = AccountManager.getInstance().getLoginType();
                if (typeLogin == TypeConstants.LOGIN_TYPE.PHONE_PW) {
                    EnAccountSetingHasPwActivity.actionStart(AccountSecurityActivity.this, null, null);
                }
                if (typeLogin == TypeConstants.LOGIN_TYPE.AUTH_QQ) {
                    EnAccountSetingNoPwActivity.actionStart(AccountSecurityActivity.this, null, null);
                }

            }
        });
        abi_phone.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNullOrBlank(info.getMobile())) {
                    ChangePhoneBindActvity1.actionStart(AccountSecurityActivity.this, null, null);
                } else {
                    NewPhoneBindActvity1.actionStart(AccountSecurityActivity.this, null, null);
                }
            }
        });

        abi_wx.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        abi_qq.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        abi_pweditpw.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPwByPwActivity.actionStart(AccountSecurityActivity.this, null, null);
            }
        });
        abi_phoneeditpw.setOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNullOrBlank(info.getMobile()))
                ModifyPwByPhoneActivity1.actionStart(AccountSecurityActivity.this, null, null);
            }
        });
    }

    public static void actionStartClearStack(Context context, String data1, String data2) {
        Intent intent = new Intent(context, AccountSecurityActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

}
