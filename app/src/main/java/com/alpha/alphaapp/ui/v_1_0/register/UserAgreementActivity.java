package com.alpha.alphaapp.ui.v_1_0.register;

import android.content.Context;
import android.content.Intent;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.BaseActivity;

/**
 * Created by kenway on 17/6/27 10:21
 * Email : xiaokai090704@126.com
 */

public class UserAgreementActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_agreement;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserAgreementActivity.class);
        context.startActivity(intent);
    }
}
