package com.alpha.alphaapp.account;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.userinfo.GetUserInfoLogic;
import com.alpha.alphaapp.ui.v_1_0.login.LoginActivity;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.core.event.RxEventBus;
import com.alpha.lib_sdk.app.core.event.acc.AccountUpdataEvent;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

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


    /**
     * 登录论坛的时候会有uid
     */
    private String uid;


    private String authNickName;//微信或者QQ登录时的昵称
    /**
     * 登录的类型 account ,phone,auth_wx,auth_qq
     */
    private int loginType;
    private Dialog dialog_login_hint;


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

            OnModelCallback<UserInfo> back = new OnModelCallback<UserInfo>() {
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
    public boolean isSskeyIsNul(Activity activity) {
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
        this.authNickName = StringUtils.getNickName(authNickName);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public boolean isLogin(final Context context) {

        //没有登录的时候弹出对话框
        //sskey为空的时候就是没有登录
        if (Util.isNullOrBlank(sskey)) {

            Dialog dialog =  DialogUtils.createTwoChoiceDialog(context, " 奥飞用户中心未登录", "请先登录论", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(context, "跳转到登录页面");
                }
            });
            dialog.show();
            return false;
        }
        //sskey不为空,uid为空,登录了奥飞用户中心,未登录论坛
        if (!Util.isNullOrBlank(sskey) && Util.isNullOrBlank(uid)) {


            View.OnClickListener  clickListener =new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.actionStart(context);
                    dialog_login_hint.dismiss();
                }
            };
            dialog_login_hint = DialogUtils.createTwoChoiceDialog(context, "论坛未登录", "请先登录论坛",clickListener);
            dialog_login_hint.show();
            return false;
        }


        //sskey不为空,并且uid也不为空,即登录成功
        if (!Util.isNullOrBlank(sskey) && !Util.isNullOrBlank(uid)) {
            return true;
        }
        return false;

    }

}
