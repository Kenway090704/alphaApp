package com.alpha.alphaapp.ui.register;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.other.GetPhoneVerifyInfo;
import com.alpha.alphaapp.model.register.RegisterInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.lib_sdk.app.core.thread.ThreadPool;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.model.CountDownManager;

/**
 * Created by kenway on 17/5/24 16:39
 * Email : xiaokai090704@126.com
 */

public class PhoneRegisterFragment extends BaseFragment {
    private static final String TAG = "PhoneRegisterFragment";
    private EditText et_phone, et_pw, et_insurepw, et_verify, et_phoneVerify;
    private Button btn_register, TextView;
    private TextView tv_get_verify;
    private CountDownManager cdm;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_phone;
    }

    @Override
    protected void initViews(View root) {
        et_phone = (EditText) root.findViewById(R.id.reg_ph_et_phone);
        et_pw = (EditText) root.findViewById(R.id.reg_ph_et_pw);
        et_insurepw = (EditText) root.findViewById(R.id.reg_ph_et_pw_insurepw);
        et_phoneVerify = (EditText) root.findViewById(R.id.reg_ph_et_phoneverify);
        btn_register = (Button) root.findViewById(R.id.reg_ph_btn_register);
        tv_get_verify = (TextView) root.findViewById(R.id.reg_ph_get_verify);
    }

    @Override
    protected void initEnvent() {
        tv_get_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNullOrNil(et_phone.getText().toString()) || !StringUtils.isPhoneNum(et_phone.getText().toString())) {
                    //如果输入的手机号码为空或着无效的手机号码提示
                    Log.e(TAG, "phone=" + et_phone.getText().toString());
                    ToastUtils.showShort(getActivity(), R.string.input_valid_eleven_number);
                } else {

                    tv_get_verify.setEnabled(false);
                    cdm.start();
                    //获取验证码
                    final String data = GetPhoneVerifyInfo.getJsonStrPhoneVerifyForRegiser(et_phone.getText().toString());
                    String json = JsonUtil.getPostJsonSignString(data);
                    ReqCallBack<String> callBack = new ReqCallBack<String>() {
                        @Override
                        public void onReqSuccess(String result) {


                            ResponseInfo info1 = ResponseInfo.getRespInfoFromJsonStr(result);
                            switch (info1.getResult()) {
                                case CommStants.GET_PHONEVERIFY_RESULT.RESUTL_OK:
                                    //获取验证码成功
                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.PHONE_HAD_REGISTER:
                                    //当手机号存在时怎么处理,跳转到登录页面
                                    ToastUtils.showLong(getContext(), R.string.phone_had);
                                    break;
                            }


                        }

                        @Override
                        public void onReqFailed(String errorMsg) {

                        }
                    };
                    RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.PHONEVERIFY, json, callBack);
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneNum(et_phone.getText().toString())) {
                    ToastUtils.showLong(getContext(), R.string.input_valid_eleven_number);
                } else {
                    //判读验证码是符合要求
                    if (Util.isNullOrNil(et_phoneVerify.getText().toString())) {
                        ToastUtils.showLong(getContext(), R.string.input_verify);
                        return;
                    }
                    if (!StringUtils.isPhoneVerify(et_phoneVerify.getText().toString())) {
                        ToastUtils.showLong(getContext(), R.string.verify_form_error);
                    } else {
                        if (Util.isNullOrNil(et_pw.getText().toString()) || Util.isNullOrNil(et_insurepw.getText().toString())) {
                            ToastUtils.showLong(getContext(), R.string.input_pw);
                        } else {
                            if (!StringUtils.isPWLine(et_pw.getText().toString()) || !StringUtils.isPWLine(et_insurepw.getText().toString())) {
                                //判读密码格式错误

                                ToastUtils.showLong(getContext(), R.string.pw_error_format);

                            } else {
                                if (et_pw.getText().toString().equals(et_insurepw.getText().toString())) {
                                    //两次输入的密码相同
                                    ToastUtils.showLong(getContext(), "开始注册");
                                    final RegisterInfo registerInfo = new RegisterInfo();
                                    registerInfo.setAccount(et_phone.getText().toString());
                                    registerInfo.setUser_ip(IPAdressUtils.getIpAdress(getContext()));
                                    registerInfo.setPhone_verify(et_phoneVerify.getText().toString());
                                    registerInfo.setPw(et_pw.getText().toString());
                                    String data = registerInfo.getJsonStrforPhone();
                                    String json = JsonUtil.getPostJsonSignString(data);

                                    ReqCallBack<String> callBack = new ReqCallBack<String>() {
                                        @Override
                                        public void onReqSuccess(String result) {
                                            ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result);
                                            switch (responseInfo.getResult()) {
                                                case CommStants.REGISTER_RESULT.RESULT_REGISTER_OK:
                                                    ToastUtils.showLong(getContext(), R.string.register_success);
                                                    //点击登录
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onReqFailed(String errorMsg) {

                                        }
                                    };
                                    RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.REGISTER, json, callBack);
                                } else {
                                    ToastUtils.showLong(getContext(), R.string.pw_different);
                                }
                            }
                        }


                    }
                }


            }
        });

    }

    @Override
    protected void initData() {
        cdm = new CountDownManager();
        cdm.setTextView(tv_get_verify);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        cdm.cancel();
    }
}
