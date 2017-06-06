package com.alpha.alphaapp.ui.login;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.login.LoginInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.bind.BindAccountActivity;
import com.alpha.alphaapp.ui.forgetpw.ForgetPWGuideActivity;
import com.alpha.alphaapp.ui.register.RegisterActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.UserDictionary.Words.APP_ID;
import static com.alpha.alphaapp.comm.CommStants.LOGIN_RESULT.RESULT_ACCOUNT_OR_PW_ERROR;
import static com.alpha.alphaapp.comm.CommStants.LOGIN_RESULT.RESULT_LOGIN_OK;

/**
 * Created by kenway on 17/5/26 11:27
 * Email : xiaokai090704@126.com
 */

public class AccountLoginFragment extends BaseFragment {
    private static final String TAG = "AccountLoginFragment";
    private EditText et_user, et_pw;
    private TextView tv_error;
    private Button btn_login;
    private TextView tv_register, tv_forget;
    private ImageView iv_weixin, iv_qq;
    private Tencent mTencent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_account;
    }

    @Override
    protected void initViews(View root) {
        et_user = (EditText) root.findViewById(R.id.log_ac_et_accout);
        et_pw = (EditText) root.findViewById(R.id.log_ac_et_pw);
        tv_error = (TextView) root.findViewById(R.id.log_ac_tv_error);
        btn_login = (Button) root.findViewById(R.id.log_ac_btn_login);
        tv_register = (TextView) root.findViewById(R.id.log_ac_tv_register);
        tv_forget = (TextView) root.findViewById(R.id.log_ac_tv_forgetpw);
        iv_weixin = (ImageView) root.findViewById(R.id.log_ac_iv_auth_weixin);
        iv_qq = (ImageView) root.findViewById(R.id.log_ac_iv_auth_qq);
    }

    @Override
    protected void initEnvent() {
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.actionStart(getContext());
            }
        });
        et_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_user.getText()) || TextUtils.isEmpty(et_pw.getText())) {
                    btn_login.setEnabled(Boolean.FALSE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_login.setEnabled(Boolean.TRUE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_blue);

                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_user.getText()) || TextUtils.isEmpty(et_pw.getText())) {
                    btn_login.setEnabled(Boolean.FALSE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_gray);

                } else {
                    btn_login.setEnabled(Boolean.TRUE);
                    btn_login.setBackgroundResource(R.drawable.shape_btn_bg_blue);
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
                //此处暂时使用测试代码
                debugQQFunc();


            }
        });
        iv_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "授权微信登录登录");
            }
        });
    }

    /**
     * 使用帐号密码登录
     */
    private void userAccountPwLogin() {
        //是不是手机号
        if (et_user.getText().toString().startsWith("1")) {
            if (!StringUtils.isPhoneNum(et_user.getText().toString())) {
                tv_error.setText(R.string.input_valid_eleven_number);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            if (!StringUtils.isPWLine(et_pw.getText().toString())) {
                tv_error.setText(R.string.pw_error_format);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            String data = LoginInfo.getJsonStrforphoneInAccount(et_user, et_pw);
            String json = JsonUtil.getPostJsonSignString(data);
            ReqCallBack<String> callBack = new ReqCallBack<String>() {
                @Override
                public void onReqSuccess(String result) {
                    ToastUtils.showShort(getContext(), result);
                }

                @Override
                public void onReqFailed(String errorMsg) {

                }
            };
            RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);

        } else {

            if (!StringUtils.isAccountLine(et_user.getText().toString())) {
                tv_error.setText(R.string.account_format);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            if (!StringUtils.isPWLine(et_pw.getText().toString())) {
                tv_error.setText(R.string.pw_error_format);
                tv_error.setVisibility(View.VISIBLE);
                return;
            }
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setAccount(et_user.getText().toString());
            loginInfo.setPw(et_pw.getText().toString());
            loginInfo.setUser_ip(IPAdressUtils.getIpAdress(getContext()));
            String data = loginInfo.getJsonStrforAccount();
            String json = JsonUtil.getPostJsonSignString(data);
            ReqCallBack<String> callBack = new ReqCallBack<String>() {
                @Override
                public void onReqSuccess(String result) {
                    ToastUtils.showShort(getContext(), result);
                    ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                    switch (info.getResult()) {
                        case RESULT_ACCOUNT_OR_PW_ERROR:

                            break;
                        case RESULT_LOGIN_OK:
                            HomeActivity.actionStart(getContext(), null, null);
                            //将密码帐号与登录,是什么登录存入sharedPerferrence
                            ResponseInfo infoSskey = ResponseInfo.getRespInfoFromJsonStr(result, true);
                            SharePLoginInfo.getInstance(getContext()).saveSskey(infoSskey.getSskey());
                            break;
                    }


                }

                @Override
                public void onReqFailed(String errorMsg) {

                }
            };
            RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);
        }
    }

    /**
     * 这是一个测试代码,后面要删除
     */
    private void debugQQFunc() {
        LoginInfo loginInfo = new LoginInfo();
        //使用获取的openid进行登录
        loginInfo.setOpenid_qq("AFGHR9080");
        String data = loginInfo.getJsonStrforAuth();
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                doDealQQAuthReqSuccess(result);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);
    }

    @Override
    protected void initData() {

    }

    /**
     * qq授权登录
     */
    private void loginQQAuth() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(APP_ID, getContext().getApplicationContext());
        IUiListener iUiListener = new IUiListener() {
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
                Log.e("onComplete", o.toString());
                initQQOpenidAndToken((JSONObject) o);
                updateQQUserInfo();
            }

            @Override
            public void onError(UiError uiError) {

                Log.e("onError", uiError.toString());
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort(getContext(), "onCancel");
            }
        };

        mTencent.login(getActivity(), "all", iUiListener);

//        //通过上面后获取qqopenid成功
//        LoginInfo loginInfo = new LoginInfo();
//        loginInfo.setOpenid_qq("AFGHR9088");
//        String data = loginInfo.getJsonStrforAuth();
//        String json = JsonUtil.getPostJsonSignString(data);
//        ReqCallBack<String> callBack = new ReqCallBack<String>() {
//            @Override
//            public void onReqSuccess(String result) {
//                doDealQQAuthReqSuccess(result);
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg) {
//
//            }
//        };
//        RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);
    }


    /**
     * @param @param jsonObject
     * @return void
     * @throws
     * @Title: initOpenidAndToken
     * @Description: 初始化OPENID以及TOKEN身份验证。
     */
    private void initQQOpenidAndToken(JSONObject jsonObject) {
        LoginInfo loginInfo = new LoginInfo();


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
                loginInfo.setOpenid_qq(openId);
                String data = loginInfo.getJsonStrforAuth();
                String json = JsonUtil.getPostJsonSignString(data);
                ReqCallBack<String> callBack = new ReqCallBack<String>() {
                    @Override
                    public void onReqSuccess(String result) {
                        doDealQQAuthReqSuccess(result);
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                };
                RequestManager.getInstance(getContext()).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, callBack);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 在回调中可以获取用户信息数据
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
     * 注册到alpha服务器的回调处理
     *
     * @param result
     */
    private void doDealQQAuthReqSuccess(String result) {
        ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result, true);

        switch (responseInfo.getResult()) {
            case CommStants.LOGIN_RESULT.RESULT_LOGIN_OK:
                ToastUtils.showShort(getContext(), "QQ授权登录成功");
//                if (SharePLoginInfo.getInstance(getContext()).getIsBindAccount()) {
                BindAccountActivity.actionStart(getActivity(), responseInfo.getSskey(), null);
//                } else {
//                    HomeActivity.actionStart(getActivity(), null, null);
//                }

                break;

        }
    }
}
