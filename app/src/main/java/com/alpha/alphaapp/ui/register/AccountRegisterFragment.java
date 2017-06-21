package com.alpha.alphaapp.ui.register;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.check.CheckAccoutLogic;
import com.alpha.alphaapp.model.register.RegisterInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/5/24 16:38
 * Email : xiaokai090704@126.com
 */

public class AccountRegisterFragment extends BaseFragment {
    private static final String TAG = "AccountRegisterFragment";
    private EditText et_accout, et_pw, et_insurepw;
    private Button btn_register;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_accout;
    }

    @Override
    protected void initViews(View root) {
        et_accout = (EditText) root.findViewById(R.id.reg_ac_et_accout);
        et_pw = (EditText) root.findViewById(R.id.reg_ac__et_pw);
        et_insurepw = (EditText) root.findViewById(R.id.reg_ac_et_insurepw);
        btn_register = (Button) root.findViewById(R.id.reg_ac_btn_register);
    }

    @Override
    protected void initEnvent() {

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //帐号的判断
                if (Util.isNullOrNil(et_accout.getText().toString())) {
                    ToastUtils.show(getActivity(), R.string.input_account, Toast.LENGTH_SHORT);
                    et_accout.setFocusable(true);
                }
                if (!StringUtils.isAccountLine(et_accout.getText().toString())) {
                    ToastUtils.show(getActivity(), R.string.account_format, Toast.LENGTH_SHORT);
                    et_accout.setFocusable(true);
                } else {
                    //判断是否存在该帐号
                    String account = et_accout.getText().toString();
                    int type = CommStants.ACCOUNT_TYPE.ACCOUNT;

                    CheckAccoutLogic.OnCheckAccountListener listener = new CheckAccoutLogic.OnCheckAccountListener() {
                        @Override
                        public void checkSucessed(boolean isHas, String result) {
                            if (isHas) {
                                et_accout.setFocusable(true);
                                et_accout.setFocusableInTouchMode(true);
                                et_accout.requestFocus();
                                KeyBoardUtils.openKeybord(et_accout, getActivity());
                            } else {
                                doRegisterAccount();

                            }
                        }

                        @Override
                        public void checkFailed(String errorMsg) {

                        }
                    };
                    CheckAccoutLogic.checkAccountIsHas(account, type, listener);
                }

            }
        });
    }

    /**
     * 注册该帐号
     */
    private void doRegisterAccount() {
        //密码为空
        if (Util.isNullOrNil(et_pw.getText().toString()) || Util.isNullOrNil(et_insurepw.getText().toString())) {
            ToastUtils.showShort(getActivity(), R.string.pw_empty);
        }
        //判断两次密码是相同
        if (StringUtils.isPWLine(et_pw.getText().toString()) && StringUtils.isPWLine(et_insurepw.getText().toString())) {
            if (et_pw.getText().toString().equals(et_insurepw.getText().toString())) {
                //两次密码相同时注册该账号
                RegisterInfo registerInfo = new RegisterInfo();
                registerInfo.setAccount(et_accout.getText().toString());
                registerInfo.setPw(et_pw.getText().toString());
                registerInfo.setUser_ip(IPAdressUtils.getIpAdress(getContext()));
                registerInfo.setTerminal_type(TypeConstants.TERMINAL_TYPE.PHONE);
                String data = registerInfo.getJsonStrforAccount();
                String json = JsonUtil.getPostJsonSignString(data);
                ReqCallBack<String> callBack = new ReqCallBack<String>() {
                    @Override
                    public void onReqSuccess(String result) {
                        ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result);
                        if (responseInfo.getResult() == CommStants.CHECKOUT_ACCOUNT_RESULT.RESUTL_OK) {
                            //注册成功
                            ToastUtils.showLong(getContext(), R.string.register_success);
                            //点击登录
                            HomeActivity.actionStartClearStack(getContext(), null, null);
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        Log.e(TAG, "注册失败");
                    }
                };
                RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.REGISTER, json, callBack);
            } else {
                ToastUtils.showShort(getActivity(), R.string.pw_different);
            }
        } else {
            ToastUtils.showShort(getActivity(), R.string.pw_format);
        }
    }

    @Override
    protected void initData() {

    }
}
