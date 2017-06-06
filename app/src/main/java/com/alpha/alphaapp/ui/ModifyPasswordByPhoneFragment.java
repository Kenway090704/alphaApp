package com.alpha.alphaapp.ui;


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
import com.alpha.alphaapp.model.modifyPassword.ModifyPwdByPhoneInfo;
import com.alpha.alphaapp.model.modifyPassword.ModifyPwdByPwdInfo;
import com.alpha.alphaapp.model.other.GetPhoneVerifyInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.login.LoginActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

public class ModifyPasswordByPhoneFragment extends BaseFragment {

    private static final String TAG = "ModifyPasswordByPhoneFragment";

    private Button btnGetPhoneVerifyCode;
    private EditText editPhoneNumber;
    private EditText editPhoneVerifyCode;
    private EditText editNewPwd;
    private EditText editConfirmPwd;
    private Button btnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_password_by_phone;
    }

    @Override
    protected void initViews(View root) {
        btnGetPhoneVerifyCode = (Button)root.findViewById(R.id.btnGetPhoneVerifyCode);
        btnSubmit = (Button)root.findViewById(R.id.btnModifyPasswordSubmitInPhone);
        editPhoneNumber = (EditText)root.findViewById(R.id.editPhoneNumber);
        editPhoneVerifyCode = (EditText)root.findViewById(R.id.editPhoneVerifyCode);
        editNewPwd = (EditText)root.findViewById(R.id.editNewPasswordInPhone);
        editConfirmPwd = (EditText)root.findViewById(R.id.editNewPassword2InPhone);
    }

    @Override
    protected void initEnvent() {

        btnGetPhoneVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //the phone number should be all digitals
                String strPhoneNumber = editPhoneNumber.getText().toString();
                if (Util.isNullOrNil(strPhoneNumber) || !StringUtils.isPhoneNum(strPhoneNumber)) {
                    //如果输入的手机号码为空或着无效的手机号码提示
                    Log.e(TAG, "phone = " + strPhoneNumber);
                    ToastUtils.showShort(getActivity(), R.string.input_valid_eleven_number);
                } else {
                    String data = GetPhoneVerifyInfo.getJsonStrPhoneVerifyForModifyPwd(editPhoneNumber);
                    String json = JsonUtil.getPostJsonSignString(data);

                    ReqCallBack<String> callBack = new ReqCallBack<String>() {
                        @Override
                        public void onReqSuccess(String result) {
                            ResponseInfo info1 = ResponseInfo.getRespInfoFromJsonStr(result);
                            switch (info1.getResult()) {
                                case CommStants.GET_PHONEVERIFY_RESULT.RESUTL_OK:
                                    //获取验证码成功
                                    ToastUtils.showLong(getContext(), R.string.sucess_send_verify_code);
                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.PHONE_HAD_REGISTER:
                                    ToastUtils.showLong(getContext(), R.string.phone_has_registered);
                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.PHONE_NO_REGISTER:
                                    ToastUtils.showLong(getContext(), R.string.phone_not_registered);
                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.VERIFY_HAD:
                                    ToastUtils.showLong(getContext(), R.string.verify_code_has_existed);
                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.PHOEN_ERROR:
                                    ToastUtils.showLong(getContext(), R.string.phone_number_wrong);
                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.TOO_MUCH_MESSAGE:
                                    ToastUtils.showLong(getContext(), R.string.messages_exceed_max);
                                    break;
                                default:
                                    Log.e(TAG, "Unknown result code : " + info1.getResult() + " msg : " + info1.getMsg());
                                    break;
                            }

                        }

                        @Override
                        public void onReqFailed(String errorMsg) {
                            Log.e(TAG, "Unknown error when sending the request : " + errorMsg);
                        }
                    };
                    RequestManager.getInstance(view.getContext()).requestPostByJsonAsyn(URLConstans.URL.PHONEVERIFY, json, callBack);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPhoneNumber = editPhoneNumber.getText().toString();
                String strNewPwd = editNewPwd.getText().toString();
                String strConfirmPwd = editConfirmPwd.getText().toString();
                String strVerifyCode = editPhoneVerifyCode.getText().toString();

                if (strVerifyCode.isEmpty()) {
                    ToastUtils.showLong(getContext(), R.string.plz_input_verify_code);
                    return;
                }

                if (strNewPwd.equals(strConfirmPwd)) {
                    ModifyPwdByPhoneInfo info = new ModifyPwdByPhoneInfo();
                    info.setSsKey(SharePLoginInfo.getInstance(getContext()).getSskey());
                    info.setAccount(strPhoneNumber);
                    info.setAccountType(Integer.toString(CommStants.ACCOUNT_TYPE.PHONE));
                    info.setUserIP(IPAdressUtils.getIpAdress(view.getContext()));
                    info.setTerminalType(DeviceConstants.TERMINAL_TYPE.PHONE);
                    info.setPhoneVerifyCode(strVerifyCode);
                    info.setNewPwd(strNewPwd);

                    String data = info.getJsonModifyPwdByPhoneInfo();
                    String json = JsonUtil.getPostJsonSignString(data);

                    ReqCallBack<String> callBack = new ReqCallBack<String>() {
                        @Override
                        public void onReqSuccess(String result) {
                            ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result);
                            int resultCode = responseInfo.getResult();
                            switch (resultCode) {
                                case CommStants.FIND_PWD_BY_PHONE_RESULT.RESULT_OK:
                                    ToastUtils.showLong(getContext(), R.string.success_change_pwd);
                                    getActivity().finish();
                                    break;
                                case CommStants.FIND_PWD_BY_PHONE_RESULT.USER_NOT_EXIST:
                                    ToastUtils.showLong(getContext(), R.string.user_not_exist);
                                    break;
                                case CommStants.FIND_PWD_BY_PHONE_RESULT.PHONE_WRONG:
                                    ToastUtils.showLong(getContext(), R.string.phone_number_wrong);
                                    break;
                                case CommStants.FIND_PWD_BY_PHONE_RESULT.DATA_PACKAGE_WRONG:
                                    ToastUtils.showLong(getContext(), R.string.data_package_wrong);
                                    break;
                                case CommStants.FIND_PWD_BY_PHONE_RESULT.PWD_FORMAT_WRONG:
                                    ToastUtils.showLong(getContext(), R.string.pwd_format_wrong);
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
                    RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.MODIFYPWDBYPHONE, json, callBack);

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
