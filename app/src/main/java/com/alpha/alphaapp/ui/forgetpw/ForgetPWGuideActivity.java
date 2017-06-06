package com.alpha.alphaapp.ui.forgetpw;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/6 14:20
 * Email : xiaokai090704@126.com
 */

public class ForgetPWGuideActivity extends BaseActivity {
    private static final String TAG = "ForgetPWGuideActivity";
    private Button btn_phone, btn_wechat;
    private TitleLayout titlelayout;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pw_guide;
    }

    @Override
    protected void initView() {
        btn_phone = (Button) findViewById(R.id.forget_pw_guide_btn_phone);
        btn_wechat = (Button) findViewById(R.id.forget_pw_guide_btn_weChat);
        titlelayout = (TitleLayout) findViewById(R.id.forget_pw_guide_titlelayout);
        titlelayout.setTitleText(R.string.find_pw);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneGetPwActivity1.actionStart(ForgetPWGuideActivity.this, null, null);
            }
        });
        btn_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(ForgetPWGuideActivity.this, "通过微信帐号设置");
            }
        });
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ForgetPWGuideActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }
}
