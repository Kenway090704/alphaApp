package com.alpha.alphaapp.app;

import android.app.Application;

import com.alpha.alphaapp.ui.v_1_0.mine.logic.GetPCityAreaData;
import com.alpha.alphaapp.wxapi.WXManager;
import com.alpha.alphaapp.wxapi.WxAccessTokenInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.app.EnvirenmentArgsHolder;
import com.alpha.lib_sdk.app.crashhandler.CrashHandler;
import com.alpha.lib_sdk.app.log.LogCatch;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.lib_stub.comm.URLConstans;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by kenway on 17/5/22 18:53
 * Email : xiaokai090704@126.com
 */

public class MyApplication extends Application {
    /**
     * 当前模式是否为debug
     */
    public static final boolean isDebug = true;
    private static MyApplication ins;
    // wechat appid  app_sercet
    public static final String WX_APP_ID = "wx6b2bf92fe1507d80";
    public static final String WX_APP_SECRET = "b0edfec14306808b50a7412aeda39dbe";
    //qq appid
    public static final String QQ_APP_ID = "1105613845";
    //bugly appkey
    public  static  final  String BUGLY_APPKEY="95a2a31773";

    //wechat tokeninfo
    private WxAccessTokenInfo wxAccessTokenInfo;




    @Override
    public void onCreate() {
        super.onCreate();

        //使上下文对象弱引用
        ApplicationContext.setApplication(this);
        EnvirenmentArgsHolder.setContext(this);

        //日志管理
        LogUtils.initLog(isDebug);
        LogCatch.getInstance(this).stop();
        LogCatch.getInstance(this).start();


        //初始化崩溃日志类
        CrashHandler.getInstance().init();

        //tencent bugly在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(getApplicationContext(), BUGLY_APPKEY, false);

        //注册微信接口
        setIns();
        regToWx();

        //glide初始化

        //加载地区地址数据
        GetPCityAreaData.getInstance().init(this);


        //URL_ROOT的初始化
        URLConstans.initURL_ROOT(false);

        //初始化appkey,appid
        JsonEncryptUtil.initAppkeyAndSercet(false);

    }


    //*************************微信登录*************************//
    public static MyApplication getIns() {
        return ins;
    }

    /**
     * set Application instance
     */
    private void setIns() {
        ins = this;
    }
    /**
     * 微信登录使用接口
     */
    private void regToWx() {
        //通过WXAPIFactory工厂,获取IWXAPI的实例
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        //将应用的appid注册到微信
        iwxapi.registerApp(WX_APP_ID);
        WXManager.instance().setApi(iwxapi);
    }

    /**
     * @return the wxAccessTokenInfo
     */
    public WxAccessTokenInfo getWxAccessTokenInfo() {
        return wxAccessTokenInfo;
    }

    /**
     * @param wxAccessTokenInfo the wxAccessTokenInfo to set
     */
    public void setWxAccessTokenInfo(WxAccessTokenInfo wxAccessTokenInfo) {
        this.wxAccessTokenInfo = wxAccessTokenInfo;
    }


}
