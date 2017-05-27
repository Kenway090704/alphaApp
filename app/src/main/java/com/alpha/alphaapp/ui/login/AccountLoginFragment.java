package com.alpha.alphaapp.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.login.LoginInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.register.RegisterActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import static com.alpha.alphaapp.comm.CommStants.LOGIN_RESULT.RESULT_ACCOUNT_OR_PW_ERROR;
import static com.alpha.alphaapp.comm.CommStants.LOGIN_RESULT.RESULT_LOGIN_OK;

/**
 * Created by kenway on 17/5/26 11:27
 * Email : xiaokai090704@126.com
 */

public class AccountLoginFragment extends BaseFragment {
    private static final String TAG = "AccountLoginFragment";
    private EditText et_user, et_pw;
    private Button btn_login;
    private TextView tv_register, tv_forget;
    private ImageView iv_weixin, iv_qq;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_account;
    }

    @Override
    protected void initViews(View root) {
        et_user = (EditText) root.findViewById(R.id.log_ac_et_accout);
        et_pw = (EditText) root.findViewById(R.id.log_ac_et_pw);
        btn_login = (Button) root.findViewById(R.id.log_ac_btn_login);
        tv_register = (TextView) root.findViewById(R.id.log_ac_tv_register);
        tv_forget = (TextView) root.findViewById(R.id.log_ac_tv_forgetpw);
        iv_weixin = (ImageView) root.findViewById(R.id.log_ac_iv_auth_weixin);
        iv_qq = (ImageView) root.findViewById(R.id.log_ac_iv_auth_qq);
    }

    @Override
    protected void initEnvent() {
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.actionStart(getContext());
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是不是手机号
                if (et_user.getText().toString().startsWith("1")) {
                    if (!StringUtils.isPhoneNum(et_user.getText().toString())) {
                        ToastUtils.showShort(getContext(), R.string.input_valid_eleven_number);
                        return;
                    }
                    if (!StringUtils.isPWLine(et_pw.getText().toString())) {
                        ToastUtils.showShort(getContext(), R.string.pw_error_format);
                        return;
                    }
                    String data = LoginInfo.getJsonStrforphoneInAccount(et_user, et_pw);
                    String json = JsonUtil.getPostJsonSignString(data);
                    ReqCallBack<String> callBack = new ReqCallBack<String>() {
                        @Override
                        public void onReqSuccess(String result) {
                            ToastUtils.showShort(getContext(), result);
                        }

                        @Override
                        public void onReqFailed(String errorMsg) {

                        }
                    };
                    RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);

                } else {

                    if (!StringUtils.isAccountLine(et_user.getText().toString())) {
                        ToastUtils.showShort(getContext(), R.string.account_format);
                        return;
                    }
                    if (!StringUtils.isPWLine(et_pw.getText().toString())) {
                        if (et_pw.getText().toString().isEmpty()) {
                            ToastUtils.showShort(getContext(), R.string.input_pw);
                        } else {
                            ToastUtils.showShort(getContext(), R.string.pw_error_format);
                        }
                        return;
                    }
                    LoginInfo loginInfo = new LoginInfo();
                    loginInfo.setAccount(et_user.getText().toString());
                    loginInfo.setPw(et_pw.getText().toString());
                    loginInfo.setUser_ip(IPAdressUtils.getIpAdress(getContext()));
                    String data = loginInfo.getJsonStrforAccount();
                    Log.e(TAG, "data==" + data);
                    String json = JsonUtil.getPostJsonSignString(data);
                    ReqCallBack<String> callBack = new ReqCallBack<String>() {
                        @Override
                        public void onReqSuccess(String result) {
                            ToastUtils.showShort(getContext(), result);
                            ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                            switch (info.getResult()) {
                                case RESULT_ACCOUNT_OR_PW_ERROR:

                                    break;
                                case RESULT_LOGIN_OK:
                                    HomeActivity.actionStart(getContext(), null, null);
                                    //将密码帐号与登录,是什么登录存入sharedPerferrence
                                    break;
                            }


                        }

                        @Override
                        public void onReqFailed(String errorMsg) {

                        }
                    };
                    RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);
                }
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "授权qq登录");
            }
        });
        iv_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "授权微信登录登录");
            }
        });
    }

    @Override
    protected void initData() {

    }
}
