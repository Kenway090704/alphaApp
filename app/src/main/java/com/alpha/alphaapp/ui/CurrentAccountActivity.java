package com.alpha.alphaapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.login.LoginInfo;
import com.alpha.alphaapp.sp.SharePLoginInfo;

public class CurrentAccountActivity extends AppCompatActivity {

    private TextView tvCurrentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_account);

        String strCurrentAccount;
        LoginInfo info = SharePLoginInfo.getInstance(getBaseContext()).getLoginInfo();
        strCurrentAccount = "current account : " + info.getAccount() + "\r\n"
                + "password : " + info.getPw() + "\r\n"
                + "sskey : " + info.getSessKey() + "\r\n"
                + "user IP : " + info.getUser_ip();

        tvCurrentAccount = (TextView)findViewById(R.id.textCurrentAccount);
        tvCurrentAccount.setText(strCurrentAccount);
    }
}
