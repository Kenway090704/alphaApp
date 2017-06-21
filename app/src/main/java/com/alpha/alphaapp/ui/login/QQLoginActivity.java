package com.alpha.alphaapp.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.login.LoginLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by kenway on 17/6/7 12:00
 * Email : xiaokai090704@126.com
 */

public class QQLoginActivity extends BaseActivity {
    private Button qqlogin_btn;
    private Tencent mTencent;
    private static final String TAG = "QQLoginActivity";
    private IUiListener iUiListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qqlogin;
    }

    @Override
    protected void initView() {
        qqlogin_btn = (Button) findViewById(R.id.qqlogin_btn);
        qqlogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginQQAuth();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }

    /**
     * qq授权登录
     */
    private void loginQQAuth() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("222222", this);
        iUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                /**
                 * /登录成功时的回调，这里的o是登录授权成功以后返回的数据
                 *{
                 *"ret":0,
                 *"pay_token":"xxxxxxxxxxxxxxxx",
                 *"pf":"openmobile_android",
                 *"expires_in":"7776000",
                 *"openid":"xxxxxxxxxxxxxxxxxxx",
                 *"pfkey":"xxxxxxxxxxxxxxxxxxx",
                 *"msg":"sucess",
                 *"access_token":"xxxxxxxxxxxxxxxxxxxxx"
                 *}
                 */
                String json = ((JSONObject) o).toString();
                Log.e("onComplete", json);

                ToastUtils.showShort(QQLoginActivity.this, json);
                initQQOpenidAndToken((JSONObject) o);
//                updateQQUserInfo();
            }

            @Override
            public void onError(UiError uiError) {
                ToastUtils.showShort(QQLoginActivity.this, uiError.toString());
                Log.e("onError", uiError.toString());
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort(QQLoginActivity.this, "onCancel");
                Log.e(TAG, "cancel");
            }
        };
        mTencent.login(QQLoginActivity.this, "all", iUiListener);

    }


    /**
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
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                //设置身份的token
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
                UserInfo userInfo = new UserInfo(QQLoginActivity.this, mTencent.getQQToken());
                userInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
/**
 *{
 *"is_yellow_year_vip": "0",
 *"ret": 0,
 *"figureurl_qq_1":
 *"http://q.qlogo.cn/qqapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/40",这是QQ头像，分辨率低
 *"figureurl_qq_2":
 *"http://q.qlogo.cn/qqapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/100",QQ头像，分辨率高
 *"nickname": "小罗",                                                   昵称
 *"yellow_vip_level": "0",
 *"msg": "",
 *"figureurl_1":
 *"http://qzapp.qlogo.cn/qzapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/50",
 *"vip": "0",
 *"level": "0",
 *"figureurl_2":
 *"http://qzapp.qlogo.cn/qzapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/100",
 *"is_yellow_vip": "0",
 *"gender": "男",
 *"figureurl":
 *"http://qzapp.qlogo.cn/qzapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/30"
 *}
 */
                    }

                    @Override
                    public void onError(UiError uiError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });

                //使用获取的openid进行登
                LoginLogic.OnLoginCallBack callBack = new LoginLogic.OnLoginCallBack() {
                    @Override
                    public void onLoginSuccessed(String sskey) {

                    }

                    @Override
                    public void onLoginFailed(String errorMsg) {

                    }
                };
                LoginLogic.doLogin(openId, null, TypeConstants.LOGIN_TYPE.AUTH_QQ, callBack);
            }
        } catch (Exception e) {
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //qq授权登录使用
        Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
    }
}
