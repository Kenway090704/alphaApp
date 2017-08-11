package com.alpha.alphaapp.ui.v_1_0.register;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.base.BaseFragmentActivity;
import com.alpha.alphaapp.ui.v_1_0.login.LoginActivity;
import com.alpha.alphaapp.ui.v_1_0.register.account.RegisterAccountActivity;
import com.alpha.alphaapp.ui.v_1_0.register.phone.RegisterPhoneActivity1;

/**
 * Created by kenway on 17/5/24 14:58
 * Email : xiaokai090704@126.com
 */

public class RegisterGuideActivity extends BaseFragmentActivity {
    private static final String TAG = "RegisterGuideActivity";
    private ImageView iv_back;
    private LinearLayout layout_acc, layout_phone;
    private TextView tv_has;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_guide;
    }

    @Override
    protected void initView() {

        layout_acc = (LinearLayout) findViewById(R.id.register_guide_layout_acc);
        layout_phone = (LinearLayout) findViewById(R.id.register_guide_layout_phone);
        tv_has = (TextView) findViewById(R.id.register_guide_tv_hasAccount);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        layout_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterAccountActivity.actionStart(RegisterGuideActivity.this);
            }
        });
        layout_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterPhoneActivity1.actionStart(RegisterGuideActivity.this);
            }
        });

        tv_has.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.actionStartClearStack(RegisterGuideActivity.this, null, null);
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterGuideActivity.class);
        context.startActivity(intent);
    }
}
