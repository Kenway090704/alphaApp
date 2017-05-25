package com.alpha.alphaapp.ui.register;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.other.CheckAccoutInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/5/24 16:38
 * Email : xiaokai090704@126.com
 */

public class AccountRegisterFragment extends BaseFragment {
    private static final String TAG = "AccountRegisterFragment";
    private EditText et_accout, et_pw, et_insurepw, et_verify;
    private Button btn_register;

    private boolean isAccountHad;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_accout;
    }

    @Override
    protected void initViews(View root) {
        et_accout = (EditText) root.findViewById(R.id.reg_ac_et_accout);
        et_pw = (EditText) root.findViewById(R.id.reg_ac__et_pw);
        et_insurepw = (EditText) root.findViewById(R.id.reg_ac_et_insurepw);
        et_verify = (EditText) root.findViewById(R.id.reg_ac_et_insurepw);
        btn_register = (Button) root.findViewById(R.id.reg_ac_btn_register);
    }

    @Override
    protected void initEnvent() {
        et_accout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //判断是否符合帐号要求
                    if (StringUtils.isAccountLine(et_accout.getText().toString())) {

                    } else {
                        ToastUtils.show(getActivity(), R.string.account_format, Toast.LENGTH_SHORT);
                    }
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNullOrNil(et_accout.getText().toString())) {
                    ToastUtils.show(getActivity(), R.string.input_account, Toast.LENGTH_SHORT);
                    et_accout.setFocusable(true);
                }
                if (!StringUtils.isAccountLine(et_accout.getText().toString())) {
                    ToastUtils.show(getActivity(), R.string.account_format, Toast.LENGTH_SHORT);
                    et_accout.setFocusable(true);
                } else {
                    //判断是否存在该帐号
                    CheckAccoutInfo checkAccoutInfo = new CheckAccoutInfo();
                    checkAccoutInfo.setAccount(et_accout.getText().toString());
                    checkAccoutInfo.setAccount_type(CommStants.ACCOUNT_TYPE.ACCOUNT);
                    String data = checkAccoutInfo.getJsonStrCheckAccout();
                    String json = JsonUtil.getPostJsonSignString(data);
                    ReqCallBack<String> callBack = new ReqCallBack<String>() {
                        @Override
                        public void onReqSuccess(String result) {
                            ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result);
                            if (responseInfo.getResult() == CommStants.CHECKOUT_ACCOUNT_RESULT.RESUTL_OK) {
                                ToastUtils.showLong(getContext(), R.string.account_had);
                                //当用户名存在的时候,获取焦点并弹出软件盘
                                et_accout.setFocusable(true);
                                et_accout.setFocusableInTouchMode(true);
                                et_accout.requestFocus();
                                KeyBoardUtils.openKeybord(et_accout, getActivity());
                            }
                        }

                        @Override
                        public void onReqFailed(String errorMsg) {

                        }
                    };
                    RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL
                            .CHECKACCOUT, json, callBack);
                }

            }
        });
    }

    @Override
    protected void initData() {

    }
}
