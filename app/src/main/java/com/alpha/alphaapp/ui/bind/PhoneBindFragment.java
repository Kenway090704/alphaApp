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
import com.alpha.alphaapp.model.other.GetPhoneVerifyInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.model.CountDownManager;

/**
 * Created by kenway on 17/6/5 15:52
 * Email : xiaokai090704@126.com
 * 绑定手机号码
 */

public class PhoneBindFragment extends BaseFragment {
    private static final String TAG = "PhoneBindFragment";
    private EditText et_phone, et_verify;
    private TextView tv_getVerify;
    private TextView tv_error;
    private Button btn_submit;

    private CountDownManager cdm;//获取验证码控件

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_phone;
    }

    @Override
    protected void initViews(View root) {
        et_phone = (EditText) root.findViewById(R.id.bind_phone_et_phone);
        et_verify = (EditText) root.findViewById(R.id.bind_phone_et_phoneverify);
        tv_getVerify = (TextView) root.findViewById(R.id.bind_phone_tv_getVerify);
        tv_error = (TextView) root.findViewById(R.id.bind_phone_tv_error);
        btn_submit = (Button) root.findViewById(R.id.bind_phone_btn_submit);
    }

    @Override
    protected void initEnvent() {
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_phone.getText()) || TextUtils.isEmpty(et_verify.getText())) {
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
        tv_getVerify.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                getVerify();
            }
        });
        et_verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_phone.getText()) || TextUtils.isEmpty(et_verify.getText())) {
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
        String data = GetPhoneVerifyInfo.getJsonStrPhoneVerifyForBind(et_phone);
        String json = JsonUtil.getPostJsonSignString(data);

        ReqCallBack<String> callback = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                dealgetVerifyRep(result);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.PHONEVERIFY, json, callback);


    }


    @Override
    protected void initData() {
        cdm = new CountDownManager();
        cdm.setTextView(tv_getVerify);
    }

    /**
     * 绑定帐号
     */
    private void userAccountPwBind() {
        if (!StringUtils.isAccountLine(et_phone.getText().toString())) {
            tv_error.setText(R.string.account_format);
            tv_error.setVisibility(View.VISIBLE);
            return;
        }
        if (!StringUtils.isPhoneVerify(et_verify.getText().toString())) {
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
     * 处理获取验证码的的响应
     */


    private void dealgetVerifyRep(String result) {
        Log.e(TAG, result);
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
        switch (info.getResult()) {
            case CommStants.GET_PHONEVERIFY_RESULT.RESUTL_OK:
                cdm.start();//启动多少秒内不可获取验证码
                break;
            case CommStants.GET_PHONEVERIFY_RESULT.PHOEN_ERROR:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                //提示手机号码错误
                break;
            case CommStants.GET_PHONEVERIFY_RESULT.PHONE_HAD_REGISTER:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                //提示手机号码已经注册
                break;
            case CommStants.GET_PHONEVERIFY_RESULT.PHONE_NO_REGISTER:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                //提示手机号码没有注册
                break;
            case CommStants.GET_PHONEVERIFY_RESULT.VERIFY_HAD:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                //提示验证码已经存在
                break;
            case CommStants.GET_PHONEVERIFY_RESULT.TOO_MUCH_MESSAGE:
                tv_error.setText(info.getMsg());
                tv_error.setVisibility(View.VISIBLE);
                //提示获取验证码次数太多
                break;
        }
    }

    /**
     * 提交绑定信息后返回对应的字段进行处理
     */
    private void doDealRepSuccess() {
        String sskey = ((BindAccountActivity) getActivity()).getSskey();
        String data = BindInfo.getJsonforBindAccount(sskey, et_phone.getText().toString(), et_verify
                .getText().toString());
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                //如果绑定成功,弹出对话框

                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_OK:
                        //弹出对话框架提示绑定成功
                        Log.e(TAG, result);
                        ToastUtils.showShort(getContext(), R.string.bind_success_you_use_wechat_account_login);
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
                        //请重新登录
                        break;
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_GETVERIFY_TOO_MUCH:
                        tv_error.setText(info.getMsg());
                        tv_error.setVisibility(View.VISIBLE);
//                        手机验证码手机号错误
                        break;
//                    case  CommStants.BIND_ACOUNT_RESULT.RESULT_PHONE_IS_ERROR:
//                        break;
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_VERIFY_IS_ERROR:
                        tv_error.setText(info.getMsg());
                        tv_error.setVisibility(View.VISIBLE);
                        //验证码错误
                        break;
                    case CommStants.BIND_ACOUNT_RESULT.RESULT_PHONE_HAD_BIND:
                        tv_error.setText(info.getMsg());
                        tv_error.setVisibility(View.VISIBLE);
                        //手机号已经绑定
                        break;
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.BIND, json, callBack);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cdm.cancel();
    }
}
