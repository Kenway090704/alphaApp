package com.alpha.alphaapp.ui.login;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.login.LoginLogic;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.bind.firstbind.BindAccountActivity;
import com.alpha.alphaapp.ui.forgetpw.ForgetPWGuideActivity;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.alphaapp.ui.widget.dialog.CustomLoadingDialog;
import com.alpha.alphaapp.wxapi.WXManager;
import com.alpha.alphaapp.ui.register.RegisterGuideActivity;
import com.alpha.alphaapp.wxapi.WechatAuthCallBack;
import com.alpha.alphaapp.wxapi.WxAccessTokenInfo;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kenway on 17/5/26 11:27
 * Email : xiaokai090704@126.com
 */

public class AccountLoginFragment extends BaseFragment {
    private static final String TAG = "AccountLoginFragment";
    private AccountEditText aet_user, aet_pw;

    private TextView tv_error;
    private Button btn_login;
    private TextView tv_register, tv_forget;
    private ImageView iv_weixin, iv_qq;
    private Tencent mTencent;
    private IUiListener iUiListener;
    private CustomLoadingDialog loadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_account;
    }

    @Override
    protected void initViews(View root) {
        aet_user = (AccountEditText) root.findViewById(R.id.log_ac_aet_accout);
        aet_pw = (AccountEditText) root.findViewById(R.id.log_ac_aet_pw);
        tv_error = (TextView) root.findViewById(R.id.log_ac_tv_error);
        btn_login = (Button) root.findViewById(R.id.log_ac_btn_login);
        tv_register = (TextView) root.findViewById(R.id.log_ac_tv_register);
        tv_forget = (TextView) root.findViewById(R.id.log_ac_tv_forgetpw);
        iv_weixin = (ImageView) root.findViewById(R.id.log_ac_iv_auth_weixin);
        iv_qq = (ImageView) root.findViewById(R.id.log_ac_iv_auth_qq);

        loadingDialog = new CustomLoadingDialog(getActivity());
        loadingDialog.setCancelable(false);
    }

    @Override
    protected void initEnvent() {
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterGuideActivity.actionStart(getContext());
            }
        });
        aet_user.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(aet_user.getText()) || TextUtils.isEmpty(aet_pw.getText())) {
                    btn_login.setEnabled(Boolean.FALSE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_login.setEnabled(Boolean.TRUE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_blue);

                }
                if (Util.isNullOrBlank(aet_user.getText().toString())) {
                    aet_user.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    aet_user.getImageViewRight().setVisibility(View.VISIBLE);
                }

                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aet_pw.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(aet_user.getText()) || TextUtils.isEmpty(aet_pw.getText())) {
                    btn_login.setEnabled(Boolean.FALSE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_gray);


                } else {
                    btn_login.setEnabled(Boolean.TRUE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_blue);
                }

                if (Util.isNullOrBlank(aet_pw.getText().toString())) {
                    aet_pw.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    aet_pw.getImageViewRight().setVisibility(View.VISIBLE);
                }
                tv_error.setVisibility(View.INVISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAccountPwLogin();
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPWGuideActivity.actionStart(getActivity(), null, null);
            }
        });
        iv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginQQAuth();
                //此处暂时使用测试代
                debugQQFunc(TypeConstants.LOGIN_TYPE.AUTH_QQ);
            }
        });
        iv_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginWxAuth();
                debugQQFunc(TypeConstants.LOGIN_TYPE.AUTH_WX);
            }
        });
    }

    /**
     * 使用帐号密码登录
     */
    private void userAccountPwLogin() {
        //是不是手机号
        if (aet_user.getText().toString().startsWith("1")) {
            if (!StringUtils.isPhoneNum(aet_user.getText().toString())) {
                tv_error.setText(R.string.input_valid_eleven_number);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            if (!StringUtils.isPWLine(aet_pw.getText().toString())) {
                tv_error.setText(R.string.pw_error_format);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            if (loadingDialog != null) {
                loadingDialog.show();
            }
            loginAccontPw(TypeConstants.LOGIN_TYPE.PHONE_PW);

        } else {

            if (!StringUtils.isAccountLine(aet_user.getText().toString())) {
                tv_error.setText(R.string.account_format);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            if (!StringUtils.isPWLine(aet_pw.getText().toString())) {
                tv_error.setText(R.string.pw_error_format);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            if (loadingDialog != null) {
                loadingDialog.show();
            }

            loginAccontPw(TypeConstants.LOGIN_TYPE.ACCONUT_PW);
        }
    }

    /**
     * 使用帐号_手机和密码登录
     *
     * @param loginTpe
     */
    private void loginAccontPw(int loginTpe) {
        String account = aet_user.getText().toString();
        String pw = aet_pw.getText().toString();
        LoginLogic.OnLoginCallBack callBack = new LoginLogic.OnLoginCallBack() {
            @Override
            public void onLoginSuccessed(String sskey) {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                HomeActivity.actionStart(getContext(), null, null);
            }

            @Override
            public void onLoginFailed(String errorMsg) {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                tv_error.setText(errorMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        LoginLogic.doLogin(account, pw, loginTpe, callBack);
    }


    /**
     * 这是一个测试代码,后面要删除
     */
    private void debugQQFunc(int loginType) {
        String openid_qq = "AFGHR9188";
        LoginLogic.OnLoginCallBack callBack = new LoginLogic.OnLoginCallBack() {
            @Override
            public void onLoginSuccessed(String sskey) {
                //判断是否在第一次授权中取消绑定帐号
//                if (SharePLoginInfo.getInstance(getContext()).getIsBindAccount()) {
                BindAccountActivity.actionStart(getActivity(), sskey, null);
//                } else {
//                    HomeActivity.actionStar(getActivity(), null, null);
//                }
            }

            @Override
            public void onLoginFailed(String errorMsg) {
                tv_error.setText(errorMsg);
                tv_error.setVisibility(View.VISIBLE);
            }
        };
        LoginLogic.doLogin(openid_qq, null, loginType, callBack);
    }

    @Override
    protected void initData() {

    }

    /**
     * 第一步:qq授权登录
     */
    private void loginQQAuth() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance("222222", getActivity());
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

                ToastUtils.showShort(getActivity(), json);
                initQQOpenidAndToken((JSONObject) o);
                updateQQUserInfo();
            }

            @Override
            public void onError(UiError uiError) {
                ToastUtils.showShort(getActivity(), uiError.toString());
                Log.e("onError", uiError.toString());
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort(getContext(), "onCancel");
                Log.e(TAG, "cancel");
            }
        };
        mTencent.login(getActivity(), "all", iUiListener);

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
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                //设置身份的token
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
                UserInfo userInfo = new UserInfo(getActivity(), mTencent.getQQToken());
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

                //使用获取的openid进行登录
                if (!Util.isNullOrBlank(openId)) {
                    LoginLogic.OnLoginCallBack call = new LoginLogic.OnLoginCallBack() {
                        @Override
                        public void onLoginSuccessed(String sskey) {
                            //判断是否在第一次授权中取消绑定帐号
                            if (SharePLoginInfo.getInstance(getContext()).getIsBindAccount()) {
                                BindAccountActivity.actionStart(getActivity(), sskey, null);
                            } else {
                                HomeActivity.actionStartClearStack(getActivity(), null, null);
                            }
                        }

                        @Override
                        public void onLoginFailed(String errorMsg) {

                        }
                    };
                    LoginLogic.doLogin(openId, null, TypeConstants.LOGIN_TYPE.AUTH_QQ, call);

                }

            }
        } catch (Exception e) {
        }
    }

    /**
     * 第三步:在回调中可以获取用户信息数据
     */
    private void updateQQUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            UserInfo mInfo = new UserInfo(getContext(), mTencent.getQQToken());
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
//                    MyProgressDialog. closeDialog();
                }

                // 用户的信息回调在此处
                @Override
                public void onComplete(final Object response) {
                    // 返回Bitmap对象。
                    try {
                        JSONObject obj = new JSONObject(response.toString());
//                        thirdUser.setNickName(obj.optString( "nickname"));
//                        thirdUser.setIcon(obj.optString( "figureurl_qq_2"));
//                        thirdUser.setGender(obj.optString( "gender"));
//                        ThirdUserVerify. verifyUser(LoginActivity.this,thirdUser, 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancel() {
//                    MyProgressDialog. closeDialog();
                }
            };
            mInfo.getUserInfo(listener);
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

    /**
     * 微信授权登录
     */
    private void loginWxAuth() {
        if (WXManager.instance().isWXAppInstalled()) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            WechatAuthCallBack callBack = new WechatAuthCallBack() {

                @Override
                public void onAuthSuccess(WxAccessTokenInfo info) {
                    userWxOpenidLogin(info);
                }

                @Override
                public void onAuthFailed(String errmsg) {

                }
            };
            //拉起微信授权，授权结果在WXEntryActivity中接收处理
            WXManager.instance().sendReq(req, callBack);
        } else {
            ToastUtils.showShort(getActivity(), R.string.wechat_not_installed);
        }
    }

    /**
     * 通过获取的wx openid登录
     */
    private void userWxOpenidLogin(WxAccessTokenInfo info) {
        String wxopenid = info.getOpenId();
        if (!Util.isNullOrBlank(wxopenid)) {
            LoginLogic.OnLoginCallBack call = new LoginLogic.OnLoginCallBack() {
                @Override
                public void onLoginSuccessed(String sskey) {

                }

                @Override
                public void onLoginFailed(String errorMsg) {

                }
            };
            LoginLogic.doLogin(wxopenid, null, TypeConstants.LOGIN_TYPE.AUTH_WX, call);
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


}
