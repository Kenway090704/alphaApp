package com.alpha.alphaapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.login.LoginInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.lib_sdk.app.log.Log;

/**
 * Created by kenway on 17/5/24 14:59
 * Email : xiaokai090704@126.com
 */

public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAccount("account001");
        loginInfo.setPw("e10adc3949ba59abbe56e057f20f883e");
        loginInfo.setUser_ip("127.0.0.1");


        SharePLoginInfo.getInstance(this).saveLoginInfo(loginInfo);
        Log.e(TAG, "loginInfo=" + SharePLoginInfo.getInstance(this).getLoginInfo().toString());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
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

    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     * @param data1
     * @param data2
     */
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }

    }
}
