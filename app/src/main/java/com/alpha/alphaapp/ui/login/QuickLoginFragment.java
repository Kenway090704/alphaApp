package com.alpha.alphaapp.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.DeviceConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.login.LoginInfo;
import com.alpha.alphaapp.model.other.GetPhoneVerifyInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.DeviceUtils;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/5/26 11:28
 * Email : xiaokai090704@126.com
 */

public class QuickLoginFragment extends BaseFragment {
    private static final String TAG = "QuickLoginFragment";
    private EditText et_phone, et_verify;
    private TextView tv_getVerify;
    private Button btn_login;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_quick;
    }

    @Override
    protected void initViews(View root) {
        et_phone = (EditText) root.findViewById(R.id.log_fast_et_phone);
        et_verify = (EditText) root.findViewById(R.id.log_fast_et_phoneverify);
        tv_getVerify = (TextView) root.findViewById(R.id.log_fast_tv_getVerify);
        btn_login = (Button) root.findViewById(R.id.log_fast_btn_login);

    }

    @Override
    protected void initEnvent() {
        tv_getVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneNum(et_phone.getText().toString())) {
                    //验证手机号码格式是否正确
                    ToastUtils.showLong(getContext(), R.string.input_valid_eleven_number);
                    return;
                }
                //获取手机验证码
                String data = GetPhoneVerifyInfo.getJsonStrPhoneVerifyForLogin(et_phone);
                String json = JsonUtil.getPostJsonSignString(data);
                RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.PHONEVERIFY, json, null);


            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneNum(et_phone.getText().toString()) && !StringUtils.isPhoneVerify(et_verify.getText().toString())) {
                    //验证手机号和验证码格式是否正确
                    ToastUtils.showLong(getContext(), R.string.account_or_verify_error);
                    return;
                }
                //登录帐号
                final LoginInfo loginInfo = new LoginInfo();
                loginInfo.setUser_ip(IPAdressUtils.getIpAdress(getContext()));
                loginInfo.setPhone_verify(et_verify.getText().toString());
                loginInfo.setAccount(et_phone.getText().toString());
                String data = loginInfo.getJsonStrforPhone();
                String json = JsonUtil.getPostJsonSignString(data);
                ReqCallBack<String> loginCallback = new ReqCallBack<String>() {
                    @Override
                    public void onReqSuccess(String result) {
                        ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result);
                        switch (responseInfo.getResult()) {
                            case CommStants.LOGIN_RESULT.RESULT_LOGIN_OK:
                                //登录成功
                                //登录成功
                                loginInfo.setSessKey(responseInfo.getSskey());
                                loginInfo.setLastLoginType(DeviceConstants.LASTLOGIN_TYPE.PHONE_QUICK);
                                SharePLoginInfo.getInstance(getContext()).saveLoginInfo(loginInfo);
                                ToastUtils.showLong(getContext(), R.string.login_success);
                                //跳转到主页面,后面可携带参数
                                HomeActivity.actionStart(getContext(), null, null);
                                break;
                            case CommStants.LOGIN_RESULT.RESULT_VERIFY_ERROR_OR_EMPTY:
                                //验证码错误
                                ToastUtils.showLong(getContext(), responseInfo.getMsg());
                                break;
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                };
                RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, loginCallback);


            }
        });
    }

    @Override
    protected void initData() {

    }
}