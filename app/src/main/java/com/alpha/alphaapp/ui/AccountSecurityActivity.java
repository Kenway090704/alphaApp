package com.alpha.alphaapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;

public class AccountSecurityActivity extends AppCompatActivity {

    private Button btnCurrentAccount;
    private Button btnModifyPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);

        btnCurrentAccount = (Button)findViewById(R.id.btnCurrentAccount);
        btnCurrentAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSecurityActivity.this, CurrentAccountActivity.class);
                startActivity(intent);
            }
        });

        btnModifyPassword = (Button)findViewById(R.id.btnModifyPassword);
        btnModifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSecurityActivity.this, ModifyPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
