package com.alpha.alphaapp.ui.forgetpw;

import android.content.Context;
import android.content.Intent;
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
import com.alpha.alphaapp.model.other.GetPhoneVerifyInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/6 15:06
 * Email : xiaokai090704@126.com
 */

public class PhoneGetPwActivity1 extends BaseActivity {
    private static final String TAG = "PhoneGetPwActivity1";
    private TitleLayout titleLayout;
    private EditText et_phone;
    private TextView tv_error;
    private Button btn_getVerify;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_getpw_1;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.phone_get_pw_1_titlelayout);
        titleLayout.setTitleText(R.string.verify_msg);
        et_phone = (EditText) findViewById(R.id.phone_get_pw_1_et_phone);
        tv_error = (TextView) findViewById(R.id.phone_get_pw_1_tv_error);
        btn_getVerify = (Button) findViewById(R.id.phone_get_pw_1_btn_getVerify);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_phone.getText())) {
                    btn_getVerify.setEnabled(Boolean.FALSE);
                    btn_getVerify.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_getVerify.setEnabled(Boolean.TRUE);
                    btn_getVerify.setBackgroundResource(R.drawable.shape_btn_bg_blue);

                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_getVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneNum(et_phone.getText().toString())) {
                    //验证手机号和验证码格式是否正确
                    tv_error.setText(R.string.input_valid_eleven_number);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }

                String data = GetPhoneVerifyInfo.getJsonStrPhoneVerifyForGetPW(et_phone);
                String json = JsonUtil.getPostJsonSignString(data);
                ReqCallBack<String> callBack = new ReqCallBack<String>() {
                    @Override
                    public void onReqSuccess(String result) {
                        dealgetVerifyRep(result);
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                };
                RequestManager.getInstance(getApplicationContext()).requestPostByJsonAsyn(URLConstans.URL.PHONEVERIFY, json, callBack);
            }
        });
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, PhoneGetPwActivity1.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

    private void dealgetVerifyRep(String result) {
        Log.e(TAG, result);
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStrHadVerify(result, true);
        switch (info.getResult()) {
            case CommStants.GET_PHONEVERIFY_RESULT.RESUTL_OK:
//                cdm.start();//启动多少秒内不可获取验证码
                PhoneGetPwActivity2.actionStart(this, et_phone.getText().toString(), info.getPhone_verify());
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
}
