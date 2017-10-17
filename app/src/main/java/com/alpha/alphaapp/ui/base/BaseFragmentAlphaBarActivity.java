package com.alpha.alphaapp.ui.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.LogUtils;

/**
 * Created by kenway on 17/5/25 10:36
 * Email : xiaokai090704@126.com
 *
 * 透明状态栏
 */

public abstract class BaseFragmentAlphaBarActivity extends FragmentActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置透明状态栏
        setAlphaBar();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        initData();
        initListener();
    }

    /**
     * 设置透明状态栏
     */
    private void setAlphaBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
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
        LogUtils.e(TAG, "onTrimMemory invoked the level is", level);
    }
}
