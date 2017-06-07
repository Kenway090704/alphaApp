package com.alpha.alphaapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.other.BindPhoneNumberInfo;
import com.alpha.alphaapp.model.other.GetPhoneVerifyInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

public class BindPhoneNumberActivity extends AppCompatActivity {

    private static final String TAG = "BindPhoneNumberActivity";

    private EditText editPhoneNumber;
    private Button btnGetVerifyCode;
    private EditText editVerifyCode;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone_number);

        editPhoneNumber = (EditText) findViewById(R.id.editPhoneNumberInBind);
        editVerifyCode = (EditText) findViewById(R.id.editPhoneVerifyCodeInBind);

        btnGetVerifyCode = (Button) findViewById(R.id.btnGetPhoneVerifyCodeInBind);
        btnGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //the phone number should be all digitals
                String strPhoneNumber = editPhoneNumber.getText().toString();
                if (Util.isNullOrNil(strPhoneNumber) || !StringUtils.isPhoneNum(strPhoneNumber)) {
                    //如果输入的手机号码为空或着无效的手机号码提示
                    Log.e(TAG, "phone = " + strPhoneNumber);
                    ToastUtils.showShort(view.getContext(), R.string.input_valid_eleven_number);
                } else {
                    String data = GetPhoneVerifyInfo.getJsonStrPhoneVerifyForBindPhone(editPhoneNumber);
                    String json = JsonUtil.getPostJsonSignString(data);

                    ReqCallBack<String> callBack = new ReqCallBack<String>() {
                        @Override
                        public void onReqSuccess(String result) {
                            ResponseInfo info1 = ResponseInfo.getRespInfoFromJsonStr(result);
                            switch (info1.getResult()) {
                                case CommStants.GET_PHONEVERIFY_RESULT.RESUTL_OK:
                                    //获取验证码成功
                                 ;
                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.PHONE_HAD_REGISTER:

                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.PHONE_NO_REGISTER:

                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.VERIFY_HAD:
                                   ;
                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.PHOEN_ERROR:

                                    break;
                                case CommStants.GET_PHONEVERIFY_RESULT.TOO_MUCH_MESSAGE:

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

        btnSubmit = (Button)findViewById(R.id.btnBindPhoneNumberSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strPhoneNumber = editPhoneNumber.getText().toString();
                String strVerifyCode = editVerifyCode.getText().toString();

                if (strVerifyCode.isEmpty()) {
                    ToastUtils.showLong(getBaseContext(), R.string.plz_input_verify_code);
                    return;
                }

                BindPhoneNumberInfo info = new BindPhoneNumberInfo();
                info.setSsKey(SharePLoginInfo.getInstance(getBaseContext()).getSskey());
                info.setAccount(strPhoneNumber);
                info.setAccountType(Integer.toString(CommStants.ACCOUNT_TYPE.PHONE));
                info.setUserIP(IPAdressUtils.getIpAdress(view.getContext()));
                info.setTerminalType(TypeConstants.TERMINAL_TYPE.PHONE);
                info.setPhoneVerifyCode(strVerifyCode);

                String data = info.getJsonBindPhoneNumberInfo();
                String json = JsonUtil.getPostJsonSignString(data);

                ReqCallBack<String> callBack = new ReqCallBack<String>() {
                    @Override
                    public void onReqSuccess(String result) {
                        ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result);
                        int resultCode = responseInfo.getResult();
                        switch (resultCode) {
                            case CommStants.BIND_ACOUNT_RESULT.RESULT_OK:
                                ToastUtils.showLong(getBaseContext(), R.string.success_bind_phone_number);
                                BindPhoneNumberActivity.this.finish();
                                break;
                            case CommStants.BIND_ACOUNT_RESULT.RESULT_RELOGIN:
                                ToastUtils.showLong(getBaseContext(), R.string.please_relogin);
                                break;
                            case CommStants.BIND_ACOUNT_RESULT.RESULT_PHONE_HAD_BIND:
                                ToastUtils.showLong(getBaseContext(), R.string.phone_bind_already);
                                break;
                            case CommStants.BIND_ACOUNT_RESULT.RESULT_VERIFY_IS_ERROR:
                                ToastUtils.showLong(getBaseContext(), R.string.phone_number_wrong);
                                break;
                            case CommStants.BIND_ACOUNT_RESULT.RESULT_GETVERIFY_TOO_MUCH:
                                ToastUtils.showLong(getBaseContext(), R.string.messages_exceed_max);
                                break;
                            case CommStants.BIND_ACOUNT_RESULT.RESULT_ACCOUT_HAD:
                                ToastUtils.showLong(getBaseContext(), R.string.account_exist_already);
                                break;
                            default:
                                ToastUtils.showLong(getBaseContext(), responseInfo.getMsg());
                                Log.e(TAG, "MSG : " + responseInfo.getMsg());
                                break;

                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        Log.e(TAG, "修改密码时，发送到服务器失败！");
                    }
                };
                RequestManager.getInstance(getBaseContext()).requestPostByJsonAsyn(URLConstans.URL.BIND, json, callBack);
            }
        });
    }
}

