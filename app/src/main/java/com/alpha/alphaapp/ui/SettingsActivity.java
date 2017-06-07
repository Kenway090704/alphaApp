package com.alpha.alphaapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.logout.LoginOutInfo;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.login.LoginActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;


public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    private Button btnExitSession;
    private Button btnAboutUs;
    private Button btnFeedback;
    private Button btnScore;
    private Button btnAccountSecurity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnExitSession = (Button)findViewById(R.id.exit_session);
        btnExitSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginOutInfo logoutInfo = new LoginOutInfo();
                logoutInfo.setSskey(SharePLoginInfo.getInstance(getBaseContext()).getSskey());
                logoutInfo.setUser_ip(IPAdressUtils.getIpAdress(view.getContext()));
                logoutInfo.setTerminal_type(TypeConstants.TERMINAL_TYPE.PHONE);

                String data = logoutInfo.getJSONStrforLoginOut();
                Log.e(TAG, "logout data = " + data);
                String json = JsonUtil.getPostJsonSignString(data);
                ReqCallBack<String> callBack = new ReqCallBack<String>() {
                    @Override
                    public void onReqSuccess(String result) {
                        ToastUtils.showShort(getApplicationContext(), result);

                        ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                        switch (info.getResult()) {
                            case CommStants.LOGINOUT_RESULT.RESULT_LOGINOUT_OK:
                                Log.e(TAG, "退出当前账号成功！ result = " + result);

                                LoginActivity.actionStartClearStack(getApplicationContext(), null, null);
                                break;
                            default:
                                Log.e(TAG, "退出当前账号失败！ result = " + result);
                                break;
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        Log.e(TAG, "onReqFailed！errorMsg = " + errorMsg);
                    }
                };

                RequestManager.getInstance(view.getContext()).requestPostByJsonAsyn(URLConstans.URL.LOGINOUT, json, callBack);
            }
        });

        btnAboutUs = (Button)findViewById(R.id.about_us);
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        btnFeedback = (Button)findViewById(R.id.feedback);
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        btnScore = (Button)findViewById(R.id.score);
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });

        btnAccountSecurity = (Button)findViewById(R.id.account_security);
        btnAccountSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AccountSecurityActivity.class);
                startActivity(intent);
            }
        });
    }
}
