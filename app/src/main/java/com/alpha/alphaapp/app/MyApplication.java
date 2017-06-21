package com.alpha.alphaapp.app;

import android.app.Application;

import com.alpha.alphaapp.ui.mine.logic.GetPCityAreaData;
import com.alpha.alphaapp.wxapi.WXManager;
import com.alpha.alphaapp.wxapi.WxAccessTokenInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.crashhandler.CrashHandler;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by kenway on 17/5/22 18:53
 * Email : xiaokai090704@126.com
 */

public class MyApplication extends Application {
    private static MyApplication ins;
    // wechat appid  app_sercet
    public static final String WX_APP_ID = "wx88888888";
    public static final String WX_APP_SECRET = "申请的app_secret";
    //wechat tokeninfo
    private WxAccessTokenInfo wxAccessTokenInfo;


    public static MyApplication getIns() {
        return ins;
    }

    /**
     * set Application instance
     */
    private void setIns() {
        ins = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //使上下文对象弱引用
        ApplicationContext.setApplication(this);
        //初始化崩溃日志类
        CrashHandler.getInstance().init();
        //注册微信接口
        setIns();
        regToWx();
        //glide初始化

        //加载网络地址数据
        GetPCityAreaData getPCityAreaData = new GetPCityAreaData();
        getPCityAreaData.init(this);

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
