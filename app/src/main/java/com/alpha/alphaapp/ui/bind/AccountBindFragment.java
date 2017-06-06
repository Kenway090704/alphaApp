package com.alpha.alphaapp.ui.bind;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.bind.BindInfo;
import com.alpha.alphaapp.model.login.LoginInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.network.ResponseCallBack;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import static com.alpha.alphaapp.comm.CommStants.LOGIN_RESULT.RESULT_ACCOUNT_OR_PW_ERROR;
import static com.alpha.alphaapp.comm.CommStants.LOGIN_RESULT.RESULT_LOGIN_OK;

/**
 * Created by kenway on 17/6/5 15:52
 * Email : xiaokai090704@126.com
 * 绑定帐号
 */

public class AccountBindFragment extends BaseFragment {
    private static final String TAG = "AccountBindFragment";
    private EditText et_account, et_pw;
    private Button btn_submit;
    private TextView tv_error;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_account;
    }

    @Override
    protected void initViews(View root) {
        et_account = (EditText) root.findViewById(R.id.bind_ac_et_accout);
        et_pw = (EditText) root.findViewById(R.id.bind_ac_et_pw);
        tv_error = (TextView) root.findViewById(R.id.bind_ac_tv_error);
        btn_submit = (Button) root.findViewById(R.id.bind_ac_btn_submit);
    }

    @Override
    protected void initEnvent() {
        et_account.addTextChangedListener(new TextWatcher() {
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
        et_pw.addTextChangedListener(new TextWatcher() {
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
        String sskey = ((BindAccountActivity) getActivity()).getSskey();
        String data = BindInfo.getJsonforBindAccount(sskey, et_account.getText().toString(), et_pw.getText().toString());
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                //如果绑定成功,弹出对话框
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_OK:
                        //弹出对话框提示绑定帐号成功
                        Log.e(TAG, result);
                        ToastUtils.showShort(getContext(),R.string.bind_success_you_use_wechat_phone_login);
                        break;
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_ACCOUT_HAD:
                        tv_error.setText(info.getMsg());
                        tv_error.setVisibility(View.VISIBLE);
                        //帐号已经存在
                        Log.e(TAG, result);
                        break;
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_RELOGIN:
                        tv_error.setText(info.getMsg());
                        tv_error.setVisibility(View.VISIBLE);
                        //重新登录
                        break;
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_GETVERIFY_TOO_MUCH:
//                        手机验证码手机号错误
                        break;
//                    case  CommStants.BIND_ACOUNT_RESULT.RESULT_PHONE_IS_ERROR:
//                        break;

                    case CommStants.BIND_ACOUNT_RESULT.RESULT_VERIFY_IS_ERROR:
                        break;
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_PHONE_HAD_BIND:
                        break;
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.BIND_ACCOUNT, json, callBack);
    }

}
