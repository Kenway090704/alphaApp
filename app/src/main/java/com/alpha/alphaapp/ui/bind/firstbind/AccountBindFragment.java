package com.alpha.alphaapp.ui.bind.firstbind;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.bind.BindLogic;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/5 15:52
 * Email : xiaokai090704@126.com
 * 绑定帐号
 */

public class AccountBindFragment extends BaseFragment {
    private static final String TAG = "AccountBindFragment";
    private AccountEditText et_account;
    private AccountEditText et_pw;
    private Button btn_submit;
    private TextView tv_error;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_account;
    }

    @Override
    protected void initViews(View root) {
        et_account = (AccountEditText) root.findViewById(R.id.bind_ac_aet_accout);
        et_pw = (AccountEditText) root.findViewById(R.id.bind_ac_aet_pw);
        tv_error = (TextView) root.findViewById(R.id.bind_ac_tv_error);
        btn_submit = (Button) root.findViewById(R.id.bind_ac_btn_submit);
    }

    @Override
    protected void initEnvent() {
        et_account.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_account.getText()) || TextUtils.isEmpty(et_pw.getText())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_blue);

                }
                if (Util.isNullOrBlank(et_account.getText().toString())) {
                    et_account.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    et_account.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_pw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_account.getText()) || TextUtils.isEmpty(et_pw.getText())) {
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
                userAccountPwBind();
            }
        });
    }

    @Override
    protected void initData() {

    }


    /**
     * 绑定帐号
     */
    private void userAccountPwBind() {
        if (!StringUtils.isAccountLine(et_account.getText().toString())) {
            tv_error.setText(R.string.account_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPWLine(et_pw.getText().toString())) {
            tv_error.setText(R.string.pw_error_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        showNormalDialog();


    }

    /**
     * 弹出提示对话框
     */
    private void showNormalDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        normalDialog.setMessage(R.string.insure_bind_account);
        normalDialog.setCancelable(false);//设置不可取消
        DialogInterface.OnClickListener dialogInterface = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        doDealRepSuccess();
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        normalDialog.setPositiveButton("确认", dialogInterface);
        normalDialog.setNegativeButton("取消", dialogInterface);
        normalDialog.show();
    }

    /**
     * 处理响应成功后
     */
    private void doDealRepSuccess() {
        String sskey = AccountManager.getInstance().getSskey();
        String account = et_account.getText().toString();
        String pw = et_pw.getText().toString();
        Log.e(TAG,"account=="+account+",pw=="+pw);
        BindLogic.OnBindCallBack call = new BindLogic.OnBindCallBack() {
            @Override
            public void onBindSuccessed() {

                ToastUtils.showShort(getContext(), R.string.bind_success_you_use_wechat_phone_login);
                //进入主页
                HomeActivity.actionStart(getActivity(),null,null);
            }
            @Override
            public void onBindFailed(String failMsg) {
                tv_error.setText(failMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        BindLogic.doBindAccountOrPhone(sskey, account, pw, TypeConstants.ACCOUNT_TYPE.ACCOUNT, call);

    }

}
