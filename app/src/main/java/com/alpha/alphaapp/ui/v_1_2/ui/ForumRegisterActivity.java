package com.alpha.alphaapp.ui.v_1_2.ui;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.logic.login.ForumLoginLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.widget.et.AccountEditText;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/9/19 16:48
 * Email : xiaokai090704@126.com
 */

public class ForumRegisterActivity extends BaseActivity {
    private AccountEditText  aet_name;
    private Button btn_insure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forum_register;
    }

    @Override
    protected void initView() {
          aet_name= (AccountEditText) findViewById(R.id.activity_forum_register_aet_name);
          btn_insure= (Button) findViewById(R.id.activity_forum_register_btn_insure);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

        aet_name.setWatcherListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(aet_name.getEditTextStr())) {
                    aet_name.getImageViewRight().setVisibility(View.INVISIBLE);
                } else {
                    aet_name.getImageViewRight().setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_insure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnModelCallback<String> callback=new OnModelCallback<String>() {
                    @Override
                    public void onModelSuccessed(String s) {
                        finish();
                    }

                    @Override
                    public void onModelFailed(String failedMsg) {
                        finish();
                    }
                };
                ForumLoginLogic.doSetForumName(ForumRegisterActivity.this,aet_name.getEditTextStr(),callback);

            }
        });
    }

    /**
     * 从其它页面跳转到HomeActivity
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ForumRegisterActivity.class);
        context.startActivity(intent);
    }
}
