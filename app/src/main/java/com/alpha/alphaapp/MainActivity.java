package com.alpha.alphaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.login.LoginInfo;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.bumptech.glide.Glide;

import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView iv;
    private Button regitser;
    private String url = "http://ww3.sinaimg.cn/bmiddle/6e91531djw1e8l3c7wo7xj20f00qo755.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regitser = (Button) findViewById(R.id.btn_register);
        iv = (ImageView) findViewById(R.id.iv);
        Glide.with(this).load(url).into(iv);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAccount("account001");
        loginInfo.setPw("e10adc3949ba59abbe56e057f20f883e");
        loginInfo.setUser_ip("127.0.0.1");
        String data = loginInfo.getJsonStrforAccount();
        final String json = JsonUtil.getPostJsonSignString(data);
        final ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo responseInfo = ResponseInfo.getRespInfoFromJsonStr(result, true);
                Log.e(TAG, responseInfo.getSskey() + "," + responseInfo.getMsg() + "," + responseInfo.getResult());
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        regitser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestManager.getInstance(MainActivity.this).requestPostByJsonAsyn(URLConstans.URL.LOGIN, json, null);
            }
        });


    }

}
