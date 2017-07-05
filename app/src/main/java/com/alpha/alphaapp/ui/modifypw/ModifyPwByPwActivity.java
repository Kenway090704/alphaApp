package com.alpha.alphaapp.ui.modifypw;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.modifyPassword.ModifyPwdLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.set.AccountSecurityActivity;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/12 15:45
 * Email : xiaokai090704@126.com
 */

public class ModifyPwByPwActivity extends BaseActivity {

    private static final String TAG = "ModifyPwByPwActivity";
    private TitleLayout titleLayout;
    private AccountEditText et_oldpw, et_newPw, et_insurePw;
    private TextView tv_error;
    private Button btn_save;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modifypw_by_pw;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.modify_pbpw_titlelayou);
        titleLayout.setTitleText(R.string.by_password);
        et_oldpw = (AccountEditText) findViewById(R.id.modify_pbpw_aet_oldpw);
        et_newPw = (AccountEditText) findViewById(R.id.modify_pbpw_aet_newpw);
        et_insurePw = (AccountEditText) findViewById(R.id.modify_pbpw_aet_insurepw);
        tv_error = (TextView) findViewById(R.id.modify_pbpw_tv_error);
        btn_save = (Button) findViewById(R.id.modify_pbpw_btn_save);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        et_oldpw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_oldpw.getEditTextStr()) || Util.isNullOrBlank(et_newPw.getEditTextStr()) || Util.isNullOrBlank(et_insurePw.getEditTextStr())) {
                    btn_save.setEnabled(Boolean.FALSE);
                    btn_save.setBackgroundResource(R.drawable.shape_btn_bg_gray);
                } else {
                    btn_save.setEnabled(Boolean.TRUE);
                    btn_save.setBackgroundResource(R.drawable.shape_btn_bg_blue);
                }
                //这里的监听方法有问题
                if (Util.isNullOrBlank(et_oldpw.getEditTextStr())) {
                    et_oldpw.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    et_oldpw.getImageViewRight().setVisibility(View.VISIBLE);
                }


                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_insurePw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_oldpw.getEditTextStr()) || Util.isNullOrBlank(et_newPw.getEditTextStr()) || Util.isNullOrBlank(et_insurePw.getEditTextStr())) {
                    btn_save.setEnabled(Boolean.FALSE);
                    btn_save.setBackgroundResource(R.drawable.shape_btn_bg_gray);
                } else {
                    btn_save.setEnabled(Boolean.TRUE);
                    btn_save.setBackgroundResource(R.drawable.shape_btn_bg_blue);
                }
                //这里的监听方法有问题
                if (Util.isNullOrBlank(et_insurePw.getEditTextStr())) {
                    et_insurePw.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    et_insurePw.getImageViewRight().setVisibility(View.VISIBLE);
                }


                tv_error.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_newPw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_oldpw.getEditTextStr()) || Util.isNullOrBlank(et_newPw.getEditTextStr()) || Util.isNullOrBlank(et_insurePw.getEditTextStr())) {
                    btn_save.setEnabled(Boolean.FALSE);
                    btn_save.setBackgroundResource(R.drawable.shape_btn_bg_gray);
                } else {
                    btn_save.setEnabled(Boolean.TRUE);
                    btn_save.setBackgroundResource(R.drawable.shape_btn_bg_blue);
                }
                //这里的监听方法有问题
                if (Util.isNullOrBlank(et_newPw.getEditTextStr())) {
                    et_newPw.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    et_newPw.getImageViewRight().setVisibility(View.VISIBLE);
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
                editpw();
            }
        });
    }

    /**
     * 修改密码
     */
    private void editpw() {
        if (!StringUtils.isPWLine(et_oldpw.getEditTextStr())) {
            tv_error.setText(R.string.old_pw_format_error);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }

        if (!StringUtils.isPWLine(et_newPw.getEditTextStr()) || !StringUtils.isPWLine(et_insurePw.getEditTextStr())) {
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!et_newPw.getEditTextStr().equals(et_insurePw.getEditTextStr())) {
            tv_error.setText(R.string.twice_newpw_diffrent);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        String sskey = AccountManager.getInstance().getSskey();
        String old_pw = et_oldpw.getEditTextStr();
        String new_pw = et_insurePw.getEditTextStr();
        ModifyPwdLogic.OnModifyPwCallBack callBack = new ModifyPwdLogic.OnModifyPwCallBack() {
            @Override
            public void modifyPwSuccess() {
                //弹出对话框
                ToastUtils.showShort(ModifyPwByPwActivity.this, "修改密码成功");
                AccountSecurityActivity.actionStar(ModifyPwByPwActivity.this, null, null);
                //将密码保存到本地
                finish();
            }

            @Override
            public void modifyPwFailed(String failMsg) {
                tv_error.setText(failMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        ModifyPwdLogic.doModifyPwByPw(sskey, old_pw, new_pw, callBack);
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ModifyPwByPwActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }


}
