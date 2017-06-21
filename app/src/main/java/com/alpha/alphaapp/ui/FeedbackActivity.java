package com.alpha.alphaapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.widget.TitleLayout;

public class FeedbackActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        TitleLayout titleLayout = (TitleLayout) findViewById(R.id.feedback_titlelayout);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }
}
