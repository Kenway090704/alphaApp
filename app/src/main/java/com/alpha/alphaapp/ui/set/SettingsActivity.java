package com.alpha.alphaapp.ui.set;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.model.logout.LoginOutLogic;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.AboutUsActivity;
import com.alpha.alphaapp.ui.AccountChangeActivity;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.FeedbackActivity;
import com.alpha.alphaapp.ui.ScoreActivity;
import com.alpha.alphaapp.ui.login.LoginActivity;
import com.alpha.alphaapp.ui.widget.set.SetingItemView;
import com.alpha.alphaapp.ui.widget.TitleLayout;


public class SettingsActivity extends BaseActivity {
    private static final String TAG = "SettingsActivity";
    private TitleLayout titleLayout;
    private SetingItemView siv_bindset, siv_feedback, siv_score, siv_aboutus;
    private Button btn_exit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.seting_titlelayout);
        titleLayout.setTitleText(R.string.settings);
        siv_bindset = (SetingItemView) findViewById(R.id.seting_siv_bindset);
        siv_feedback = (SetingItemView) findViewById(R.id.seting_siv_feedback);
        siv_score = (SetingItemView) findViewById(R.id.seting_siv_score);
        siv_aboutus = (SetingItemView) findViewById(R.id.seting_siv_aboutus);
        btn_exit = (Button) findViewById(R.id.seting_btn_exit);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        siv_bindset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountSecurityActivity.actionStar(SettingsActivity.this, null, null);
            }
        });
        siv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });
        siv_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });
        siv_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginout();
            }
        });

    }

    /**
     * 退出当前帐号
     */
    private void loginout() {
        String sskey = AccountManager.getInstance().getSskey();
        if (sskey == null) {
            return;
        }
        LoginOutLogic.LoginOutCallBack callBack = new LoginOutLogic.LoginOutCallBack() {
            @Override
            public void onLoginOutSuccuss() {
                //弹出对话框提示
                AccountManager.getInstance().reset();//退出当前帐号
                SharePLoginInfo.getInstance(SettingsActivity.this).clear();
                LoginActivity.actionStartClearStack(getApplicationContext(), null, null);
            }

            @Override
            public void onLoginOutFailed(String failMsg) {

            }
        };
        LoginOutLogic.doLoginOut(sskey, callBack);
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

}
