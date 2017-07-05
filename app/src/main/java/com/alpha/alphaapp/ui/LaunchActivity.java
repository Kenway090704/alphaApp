package com.alpha.alphaapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.login.LoginLogic;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.login.LoginActivity;
import com.alpha.alphaapp.ui.register.RegisterGuideActivity;
import com.alpha.lib_sdk.app.core.thread.ThreadPool;


/**
 * Created by kenway on 17/5/23 18:25
 * Email : xiaokai090704@126.com
 * 启动页面
 */

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉标题栏
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.launcher);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setContentView(imageView);
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
}
