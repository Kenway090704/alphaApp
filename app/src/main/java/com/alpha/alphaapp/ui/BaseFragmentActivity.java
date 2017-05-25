package com.alpha.alphaapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.Log;

/**
 * Created by kenway on 17/5/25 10:36
 * Email : xiaokai090704@126.com
 */

public abstract class BaseFragmentActivity extends FragmentActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        initListener();
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
        Log.e(TAG, "onTrimMemory invoked the level is", level);
    }
}
