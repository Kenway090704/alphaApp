package com.alpha.alphaapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.login.LoginInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.register.RegisterActivity;
import com.alpha.lib_sdk.app.log.Log;


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
        LoginInfo loginInfo = SharePLoginInfo.getInstance(this).getLoginInfo();
        if (loginInfo == null) {
            switch (loginInfo.getLastLoginType()) {
                case CommStants.ACCOUNT_TYPE.ACCOUNT:

                    break;
                case CommStants.ACCOUNT_TYPE.AUTH:

                    break;
                case CommStants.ACCOUNT_TYPE.PHONE:

                    break;
            }
            HomeActivity.actionStart(LaunchActivity.this, null, null);
        } else {

            Log.e("zk","is phone"+ StringUtils.isPhoneNum("13128914595"));
            RegisterActivity.actionStart(this);
            finish();
        }


//        //等待三秒后跳转到HomeActivity
//        ThreadPool.post(new Runnable() {
//            @Override
//            public void run() {
//                HomeActivity.actionStart(LaunchActivity.this, null, null);

//            }
//        }, 3000);
    }
}
