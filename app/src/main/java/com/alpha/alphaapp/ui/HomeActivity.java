package com.alpha.alphaapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

    private Button btnSettings;

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
        btnSettings = (Button)findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
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

    public static void actionStartClearStack(Context context, String data1, String data2) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }
}
