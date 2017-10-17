package com.alpha.alphaapp.ui.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.githang.statusbar.StatusBarCompat;


/**
 * Created by Tom on 2016/3/10.
 */
public abstract class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        // 设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setAlphaToolbar();
        initView();
        initData();
        initListener();
    }

    /**
     * 设置状态栏
     */
    private void setAlphaToolbar() {

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.common_red), false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ApplicationContext.setCurrentContext(this);
    }

    /**
     * @return the layout resource id
     */
    protected abstract int getLayoutId();

    /**
     * init the view.
     */
    protected abstract void initView();

    public abstract void initData();

    /**
     * register the listener to the view.
     */
    protected abstract void initListener();

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtils.e("onTrimMemory invoked the level is", level);
    }


}
