package com.alpha.alphaapp.ui;

import android.widget.TextView;

import com.alpha.alphaapp.BuildConfig;
import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.SystemUtils;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        TitleLayout titleLayout= (TitleLayout) findViewById(R.id.aboutus_titlelayout);
        titleLayout.setTitleText(R.string.about_us);
       TextView tv_version= (TextView) findViewById(R.id.aboutus_tv_version);
        tv_version.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }
}
