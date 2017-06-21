package com.alpha.alphaapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.login.LoginLogic;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.register.RegisterActivity;


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
//
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.launcher);
        setContentView(imageView);
        //在这里判断是否登录帐号和密码 true--HomeActivity   false---RegisterActivity
        LoginLogic loginInfo = SharePLoginInfo.getInstance(this).getLoginInfo();
        if (loginInfo == null) {
            switch (loginInfo.getLastLoginType()) {
                case TypeConstants.LOGIN_TYPE.ACCONUT_PW:

                    break;
                case TypeConstants.LOGIN_TYPE.PHONE_PW:

                    break;
                case TypeConstants.LOGIN_TYPE.PHONE_QUICK:

                    break;
                case TypeConstants.LOGIN_TYPE.AUTH_QQ:

                    break;
                case TypeConstants.LOGIN_TYPE.AUTH_WX:

                    break;
            }
            HomeActivity.actionStart(LaunchActivity.this, null, null);
        } else {
            RegisterActivity.actionStart(this);
            finish();
        }


//        //等待三秒后跳转到HomeActivity
//        ThreadPool.post(new Runnable() {
//            @Override
//            public void run() {
//                HomeActivity.actionStartClearStack(LaunchActivity.this, null, null);

//            }
//        }, 3000);
    }
}
