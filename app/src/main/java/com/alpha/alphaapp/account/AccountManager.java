package com.alpha.alphaapp.account;

import android.app.Activity;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.userinfo.GetUserInfoLogic;
import com.alpha.alphaapp.ui.v_1_0.login.LoginActivity;
import com.alpha.lib_sdk.app.core.event.RxEventBus;
import com.alpha.lib_sdk.app.core.event.acc.AccountUpdataEvent;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/8 13:37
 * Email : xiaokai090704@126.com
 * 当前登录帐号管理类
 */

public class AccountManager {
    private static final String TAG = "AccountManager";
    private UserInfo accountInfo;
    private static AccountManager manager;
    private String sskey;



    private String authNickName;//微信或者QQ登录时的昵称
    /**
     * 登录的类型 account ,phone,auth_wx,auth_qq
     */
    private int loginType;


    private AccountManager() {

    }

    /**
     * 获取帐号管理类单例模式
     *
     * @return
     */
    public static AccountManager getInstance() {
        AccountManager inst = manager;
        if (inst == null) {
            synchronized (AccountManager.class) {
                inst = manager;
                if (inst == null) {
                    inst = new AccountManager();
                    manager = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 存储当前帐号信息
     *
     * @param info
     * @return
     */
    public void saveUserInfo(UserInfo info) {
        accountInfo = info;
    }


    /**
     * 获取当前帐号的用户信息
     */
    public UserInfo getUserInfo() {
        if (accountInfo != null) {
            return accountInfo;
        } else {
            loadUserinfo();
        }
        return null;
    }

    /**
     * 获取用户信息
     */
    public void loadUserinfo() {
        if (!Util.isNullOrBlank(sskey)) {

            OnModelCallback<UserInfo> back=new OnModelCallback<UserInfo>() {
                @Override
                public void onModelSuccessed(UserInfo info) {
                    saveUserInfo(info);
                    //通知相关的注册页面进行用户信息更新
                    RxEventBus.getBus().publish(new AccountUpdataEvent());
                }

                @Override
                public void onModelFailed(String failedMsg) {
                    LogUtils.e(TAG, "failedMsg==" + failedMsg);

                }
            };
            GetUserInfoLogic.doGetUserInfo(sskey, back);
        }
    }

    public String getSskey() {
        return sskey;
    }

    public void setSskey(String sskey) {
        this.sskey = sskey;
    }

    /**
     * 判断当前登录的sskey是否依然有效
     * 如果失效,则弹出对话框提示后,回到登录页面
     *
     * @param activity 当前页面
     */
    public  boolean isSskeyIsNul(Activity activity) {
        if (Util.isNullOrBlank(getInstance().getSskey())) {
            //弹出对话框提示
            LoginActivity.actionStartClearStack(activity, null, null);
            return true;
        }
        return false;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    /**
     * 将当前的sskey设置为空
     */
    public synchronized void reset() {
        sskey = null;
        if (accountInfo != null) {
            accountInfo = null;
        }
    }

    public void exitLogin(Activity activity) {
        if (Util.isNull(accountInfo))
            accountInfo = null;
        if (Util.isNullOrBlank(sskey))
            sskey = null;
        //弹出对话框提示
        LoginActivity.actionStartClearStack(activity, null, null);

    }

    public String getAuthNickName() {
        return authNickName;
    }

    public void setAuthNickName(String authNickName) {
        this.authNickName =  StringUtils.getNickName(authNickName);
    }

}
