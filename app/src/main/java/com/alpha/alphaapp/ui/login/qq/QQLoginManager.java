package com.alpha.alphaapp.ui.login.qq;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.check.CheckAccoutLogic;
import com.alpha.alphaapp.model.login.LoginLogic;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.bind.firstbind.BindAccountActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kenway on 17/6/28 14:21
 * Email : xiaokai090704@126.com
 */

public class QQLoginManager {
    private static final String TAG = "QQLoginManager";
    private static QQLoginManager manager;
    private Activity activity;
    private OnQQAuthLoginCallBack logincall;
    private Tencent mTencent;
    private IUiListener iUiListener;

    private QQLoginManager() {

    }

    /**
     * 获取qq登录单例模式
     *
     * @return
     */
    public static QQLoginManager getInstance() {
        QQLoginManager inst = manager;
        if (inst == null) {
            synchronized (QQLoginManager.class) {
                inst = manager;
                if (inst == null) {
                    inst = new QQLoginManager();
                    manager = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 第一步:qq授权登录,获取Openid,Accesstoken等
     * <p>
     * <p>
     * /登录成功时的回调，这里的o是登录授权成功以后返回的数据
     * {
     * "ret":0,
     * "pay_token":"xxxxxxxxxxxxxxxx",
     * "pf":"openmobile_android",
     * "expires_in":"7776000",
     * "openid":"xxxxxxxxxxxxxxxxxxx",
     * "pfkey":"xxxxxxxxxxxxxxxxxxx",
     * "msg":"sucess",
     * "access_token":"xxxxxxxxxxxxxxxxxxxxx"
     * }
     * </p>
     */
    public void loginQQAuth(final Activity activity, OnQQAuthLoginCallBack callBack) {
        this.activity = activity;
        this.logincall = callBack;
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("1105613845", activity);
        if (!mTencent.isSessionValid()) {
            //可以获取Openid和AccessToken
            iUiListener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    try {
                        JSONObject jsonObject = new JSONObject(o.toString());
                        int ret = jsonObject.getInt("ret");
                        if (ret == 0) {
                            initQQOpenidAndToken(jsonObject);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(UiError uiError) {
                    if (!Util.isNull(logincall)) {
                        logincall.onQQAuthFailed("授权失败");
                    }
                    Log.e(TAG, uiError.toString());
                }

                @Override
                public void onCancel() {
                    if (!Util.isNull(logincall)) {
                        logincall.onQQAuthFailed("授权取消");
                    }
                    Log.e(TAG, "cancel");
                }
            };
            mTencent.login(activity, "all", iUiListener);
        }
    }

    /**
     * 第二步:获取到openid和token
     *
     * @param @param jsonObject
     * @return void
     * @throws
     * @Title: initOpenidAndToken
     * @Description: 初始化OPENID以及TOKEN身份验证。
     */
    private void initQQOpenidAndToken(JSONObject jsonObject) {
        try {
            //这里的Constants类，是 com.tencent.connect.common.Constants类，下面的几个参数也是固定的
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            Log.e(TAG, "openid==" + openId + ",token==" + token);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                //设置身份的token
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
                //获取用户信息
                getQQUserInfo(openId);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 获取QQ用户信息
     */
    private void getQQUserInfo(final String openId) {
        IUiListener userInfoListener = new IUiListener() {

            /**
             * 返回用户信息样例
             *
             * {"is_yellow_year_vip":"0","ret":0,
             * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
             * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "nickname":"攀爬←蜗牛","yellow_vip_level":"0","is_lost":0,"msg":"",
             * "city":"黄冈","
             * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
             * "vip":"0","level":"0",
             * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "province":"湖北",
             * "is_yellow_vip":"0","gender":"男",
             * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
             */
            @Override
            public void onComplete(Object o) {
                if (Util.isNull(o)) {
                    return;
                }
                try {
                    Log.e(TAG, "获取信息==" + o.toString());
                    JSONObject jo = (JSONObject) o;
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    ToastUtils.showShort(activity, "你好，" + nickName);

                    if (!Util.isNull(logincall)) {
                        if (!Util.isNull(nickName)) {
                            AccountManager.getInstance().setAuthNickName(nickName);
                        }
                        logincall.onQQAuthSuccessed(openId, nickName);
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }


            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "获取信息==UiError" + uiError.toString());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "获取信息==onCancel");
            }
        };
        if (mTencent != null && mTencent.isSessionValid()) {
            UserInfo userInfo = new UserInfo(activity, mTencent.getQQToken());
            userInfo.getUserInfo(userInfoListener);
        }

    }


    /**
     * 获取qq登录的回调接口
     *
     * @return
     */
    public IUiListener getQQIUiListener() {
        return iUiListener;
    }

    public interface OnQQAuthLoginCallBack {
        /**
         * 授权成功后可以获取qq_openid
         *
         * @param qq_openid
         */
        void onQQAuthSuccessed(String qq_openid, String nickName);


        void onQQAuthFailed(String failedMsg);

    }

    /**
     * 退出qq登录
     */
    public void logitout() {
        if (!Util.isNull(mTencent))
            mTencent.logout(activity);
    }


}
