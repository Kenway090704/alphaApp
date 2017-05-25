package com.alpha.alphaapp.app;

import android.app.Application;

import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.crashhandler.CrashHandler;
import com.bumptech.glide.Glide;

/**
 * Created by kenway on 17/5/22 18:53
 * Email : xiaokai090704@126.com
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //使上下文对象弱引用
        ApplicationContext.setApplication(this);
        //初始化崩溃日志类
        CrashHandler.getInstance().init();
        //glide初始化


    }
}
