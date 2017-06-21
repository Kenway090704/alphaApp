package com.alpha.alphaapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.widget.TitleLayout;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        TitleLayout titleLayout= (TitleLayout) findViewById(R.id.aboutus_titlelayout);
        titleLayout.setTitleText(R.string.about_us);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }
}
