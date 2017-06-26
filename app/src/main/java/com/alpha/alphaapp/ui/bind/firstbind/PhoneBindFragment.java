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
import com.alpha.alphaapp.model.other.GetPhoneVerifyLogic;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.et.InputVerifyEditText;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/5 15:52
 * Email : xiaokai090704@126.com
 * 绑定手机号码
 */

public class PhoneBindFragment extends BaseFragment {
    private static final String TAG = "PhoneBindFragment";
    private AccountEditText et_phone;
    private InputVerifyEditText ivet;
    private TextView tv_error;
    private Button btn_submit;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_phone;
    }

    @Override
    protected void initViews(View root) {
        et_phone = (AccountEditText) root.findViewById(R.id.bind_phone_aet_phone);
        ivet = (InputVerifyEditText) root.findViewById(R.id.bind_phone_ivet);
        tv_error = (TextView) root.findViewById(R.id.bind_phone_tv_error);
        btn_submit = (Button) root.findViewById(R.id.bind_phone_btn_submit);
    }

    @Override
    protected void initEnvent() {
        et_phone.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_phone.getText()) || TextUtils.isEmpty(ivet.getText())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_blue);

                }

                if (Util.isNullOrBlank(et_phone.getText().toString())) {
                    et_phone.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    et_phone.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivet.setGetVerifyTextViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerify();
            }
        });
        ivet.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_phone.getText()) || TextUtils.isEmpty(ivet.getText())) {
                    btn_submit.setEnabled(Boolean.FALSE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_submit.setEnabled(Boolean.TRUE);
                    btn_submit.setBackgroundResource(R.drawable.shape_btn_bg_blue);
                }

                if (Util.isNullOrBlank(ivet.getText().toString())) {
                    ivet.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    ivet.getImageViewRight().setVisibility(View.VISIBLE);
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
                userPhonePwBind();

            }
        });
    }

    /**
     * 获取验证码
     */
    private void getVerify() {
        if (!StringUtils.isPhoneNum(et_phone.getText().toString())) {
            //验证手机号码格式是否正确
            tv_error.setText(R.string.input_valid_eleven_number);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        //获取手机验证码
        String phone = et_phone.getText().toString();
        GetPhoneVerifyLogic.OnGetVerifyCallBack callBack = new GetPhoneVerifyLogic.OnGetVerifyCallBack() {
            @Override
            public void onGetVerifySuccess() {
                //启动多少秒内不可获取验证码
                ivet.start();
            }

            @Override
            public void onGetVerifyFailed(String failMsg) {
                tv_error.setText(failMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        GetPhoneVerifyLogic.doGetPhoneVerify(phone, TypeConstants.GET_VERIFY.BIND_PHONE, callBack);
    }


    @Override
    protected void initData() {

    }

    /**
     * 绑定帐号
     */
    private void userPhonePwBind() {
        if (!StringUtils.isPhoneNum(et_phone.getText().toString())) {
            tv_error.setText(R.string.input_valid_eleven_number);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPhoneVerify(ivet.getText().toString())) {
            tv_error.setText(R.string.verify_form_error);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        showNormalDialog();
    }

    /**
     * 绑定提交后对话框的显示
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
     * 提交绑定信息后返回对应的字段进行处理
     */
    private void doDealRepSuccess() {
        String sskey = AccountManager.getInstance().getSskey();
        String phone = et_phone.getText().toString();
        String verify = ivet.getText().toString();
        BindLogic.OnBindCallBack call = new BindLogic.OnBindCallBack() {
            @Override
            public void onBindSuccessed() {
                //弹出对话框架提示绑定成功
                ToastUtils.showShort(getContext(), R.string.bind_success_you_use_wechat_account_login);
                HomeActivity.actionStart(getActivity(), null, null);
            }

            @Override
            public void onBindFailed(String failMsg) {
                tv_error.setText(failMsg);
                tv_error.setVisibility(View.VISIBLE);
                //帐号已经存在
            }
        };
        BindLogic.doBindAccountOrPhone(sskey, phone, verify, TypeConstants.ACCOUNT_TYPE.PHONE, call);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!Util.isNull(ivet))
            ivet.cancel();
    }
}
