package com.alpha.alphaapp.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.Log;
import com.githang.statusbar.StatusBarCompat;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setAlphaToolbar();
        initView();
        initData();
        initListener();
    }


    /**
     * 设置全透明状态栏
     */
    private void setAlphaToolbar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.common_red), false);
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
