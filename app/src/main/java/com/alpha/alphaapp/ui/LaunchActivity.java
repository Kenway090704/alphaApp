package com.alpha.alphaapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.BuildConfig;
import com.alpha.alphaapp.model.v_1_0.login.LoginLogic;
import com.alpha.alphaapp.ui.v_1_0.login.LoginActivity;
import com.alpha.alphaapp.R;
import com.alpha.alphaapp.version.UpdateVersionUtil;
import com.alpha.lib_sdk.app.core.thread.ThreadPool;
import com.alpha.lib_stub.comm.URLConstans;
import com.tencent.bugly.crashreport.CrashReport;


/**
 * Created by kenway on 17/5/23 18:25
 * Email : xiaokai090704@126.com
 * 启动页面
 */

public class LaunchActivity extends AppCompatActivity {
    private static final String TAG = "LaunchActivity";
    private TextView tv_version, tv_buildtime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉标题栏
        setContentView(R.layout.activity_launch);
        initViews();
        //判断最近的一次登录是什么登录,如果是有密码的登录,如果有直接登录,如果没有三秒后跳转到登录界面
        if (!LoginLogic.isAutoLogin(this)) {
            ThreadPool.post(new Runnable() {
                @Override
                public void run() {
                    LoginActivity.actionStartClearStack(LaunchActivity.this, null, null);
                }
            }, 3000);
        }
    }

    private void initViews() {
        tv_version = (TextView) findViewById(R.id.launch_tv_version);
        tv_buildtime = (TextView) findViewById(R.id.launch_tv_buildtime);

        tv_version.setText(BuildConfig.VERSION_NAME);

        if (BuildConfig.DEBUG) {
            tv_buildtime.setText(String.valueOf(BuildConfig.buildTime));
        } else {
            tv_buildtime.setVisibility(View.GONE);
        }
    }


}
