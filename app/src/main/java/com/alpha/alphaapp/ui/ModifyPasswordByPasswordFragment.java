package com.alpha.alphaapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.DeviceConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.modifyPassword.ModifyPwdByPwdInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.login.LoginActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

public class ModifyPasswordByPasswordFragment extends BaseFragment {

    private static final String TAG = "ModifyPasswordByPasswordFragment";

    private EditText editTextOldPwd;
    private EditText editTextNewPwd;
    private EditText editTextConfirmPwd;
    private Button btnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_password_by_password;
    }

    @Override
    protected void initViews(View root) {
        editTextOldPwd = (EditText)root.findViewById(R.id.editOldPassword);
        editTextNewPwd = (EditText)root.findViewById(R.id.editNewPassword);
        editTextConfirmPwd = (EditText)root.findViewById(R.id.editNewPassword2);
        btnSubmit = (Button)root.findViewById(R.id.btnModifyPasswordSubmit);
    }

    @Override
    protected void initEnvent() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strOldPwd = editTextOldPwd.getText().toString();
                String strNewPwd = editTextNewPwd.getText().toString();
                String strConfirmPwd = editTextConfirmPwd.getText().toString();

                //check whether the new password equals the confirm password or not.
                if (strNewPwd.equals(strConfirmPwd)) {
                    if (StringUtils.isPWLine(strNewPwd)) {
                        ModifyPwdByPwdInfo info = new ModifyPwdByPwdInfo();
                        info.setSskey(SharePLoginInfo.getInstance(view.getContext()).getSskey());
                        info.setOldPwd(strOldPwd);
                        info.setNewPwd(strNewPwd);
                        info.setUserIP(IPAdressUtils.getIpAdress(view.getContext()));
                        info.setTerminalType(DeviceConstants.TERMINAL_TYPE.PHONE);

                        String rawData = info.getJsonModifyPwdByPwdInfo();
                        String json = JsonUtil.getPostJsonSignString(rawData);

                        ReqCallBack<String> callBack = new ReqCallBack<String>() {
                            @Override
                            public void onReqSuccess(String result) {
                                ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result);
                                int resultCode = responseInfo.getResult();
                                switch (resultCode) {
                                    case CommStants.CHANGE_PWD_BY_PWD_RESULT.RESULT_OK:
                                        ToastUtils.showLong(getContext(), R.string.success_change_pwd);
                                        getActivity().finish();
                                        break;
                                    case CommStants.CHANGE_PWD_BY_PWD_RESULT.RESULT_RELOGIN:
                                        ToastUtils.showLong(getContext(), R.string.please_relogin);
                                        LoginActivity.actionStartClearStack(getContext(), null, null);
                                        break;
                                    case CommStants.CHANGE_PWD_BY_PWD_RESULT.RESULT_FAIL_TO_MODIFY:
                                        ToastUtils.showLong(getContext(), R.string.fail_to_modify_pwd);
                                        break;
                                    default:
                                        ToastUtils.showLong(getContext(), responseInfo.getMsg());
                                        break;

                                }
                            }

                            @Override
                            public void onReqFailed(String errorMsg) {
                                Log.e(TAG, "修改密码时，发送到服务器失败！");
                            }
                        };
                        RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.CHANGEPWDBYPWD, json, callBack);

                    } else {
                        ToastUtils.showShort(getActivity(), R.string.pw_format);
                    }

                } else {
                    ToastUtils.showShort(getActivity(), R.string.pw_different);
                    return;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

}
