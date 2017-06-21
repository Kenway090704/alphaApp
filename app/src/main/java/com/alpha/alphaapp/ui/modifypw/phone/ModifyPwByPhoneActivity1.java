package com.alpha.alphaapp.ui.modifypw.phone;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.other.GetPhoneVerifyInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/12 15:46
 * Email : xiaokai090704@126.com
 */

public class ModifyPwByPhoneActivity1 extends BaseActivity implements TextWatcher {
    private static final String TAG = "ModifyPwByPhoneActivity1";
    private AccountEditText et_phone;
    private TextView tv_error;
    private Button btn_getVerify;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modifypw_by_phone_1;
    }

    @Override
    protected void initView() {
        TitleLayout titleLayout = (TitleLayout) findViewById(R.id.modify_pbph1_titlelayou);
        titleLayout.setTitleText(R.string.has_bind_phone_edit_pw);
        et_phone = (AccountEditText) findViewById(R.id.modify_pbph1_aet_phone);
        tv_error = (TextView) findViewById(R.id.modify_pbph1_tv_error);
        btn_getVerify = (Button) findViewById(R.id.modify_pbph1_btn_getVerify);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        et_phone.setWatcherListener(this);
        btn_getVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isPhoneNum(et_phone.getText().toString())) {
                    tv_error.setText(R.string.input_valid_eleven_number);
                    tv_error.setVisibility(View.VISIBLE);
                    return;
                }
                String data = GetPhoneVerifyInfo.getJsonStrPhoneVerifyForModifyPwd(et_phone.getText().toString());
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

    private void dealgetVerifyRep(String result) {
        Log.e(TAG, result);
        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);

        switch (info.getResult()) {
            case CommStants.GET_PHONEVERIFY_RESULT.RESUTL_OK:
                //进入第二个页面
                ModifyPwByPhoneActivity2.actionStart(this, et_phone.getText().toString(), null);

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

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ModifyPwByPhoneActivity1.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (Util.isNullOrBlank(et_phone.getText().toString())) {
            btn_getVerify.setEnabled(Boolean.FALSE);
            btn_getVerify.setBackgroundResource(R.drawable.shape_btn_bg_gray);
            et_phone.getImageViewRight().setVisibility(View.INVISIBLE);
        } else {
            btn_getVerify.setEnabled(Boolean.TRUE);
            btn_getVerify.setBackgroundResource(R.drawable.shape_btn_bg_blue);
            et_phone.getImageViewRight().setVisibility(View.VISIBLE);
        }
        tv_error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
