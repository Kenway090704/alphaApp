package com.alpha.alphaapp.ui.v_1_0.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.anim.GifView;
import com.alpha.alphaapp.ui.base.BaseFragmentNoBarActivity;
import com.alpha.alphaapp.ui.base.adapter.BaseFragmentPageAdapter;
import com.alpha.alphaapp.ui.v_1_0.login.qq.QQLoginManager;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/5/26 11:09
 * Email : xiaokai090704@126.com
 */

public class LoginActivity extends BaseFragmentNoBarActivity {
    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments;
    private GifView gif_sheep, gif_super;
    private Handler handler = new Handler();
    private Runnable runnable;
    private boolean isStop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        gif_sheep = (GifView) findViewById(R.id.login_iv_sheep);
        gif_super = (GifView) findViewById(R.id.login_iv_super);
        tabLayout = (TabLayout) findViewById(R.id.login_tablayout);
        vp = (ViewPager) findViewById(R.id.login_vp);
        startNormalAnim();

    }

    @Override
    public void initData() {
        initFragments();
        initTabLayout();
    }


    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new AccountLoginFragment());
        fragments.add(new QuickLoginFragment());
        BaseFragmentPageAdapter adapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(ResourceUtil.resToStr(this,R.string.normal_login)));
        tabLayout.addTab(tabLayout.newTab().setText(ResourceUtil.resToStr(this,R.string.quick_login)));
    }

    @Override
    protected void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = tabLayout.getTabAt(position % fragments.size());
                if (!Util.isNull(tab))
                    tab.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void actionStartClearStack(Context context, String data1, String data2) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    public void stopNormalAnim() {
        gif_sheep.stop();
        gif_super.stop();
        handler.removeCallbacks(runnable);
    }

    public void startNormalAnim() {
        gif_sheep.setMovieResource(R.raw.animation_sheep_normal);
        gif_super.setMovieResource(R.raw.animation_super_normal);
        isStop = false;
        runnable = new Runnable() {
            @Override
            public void run() {
                //动画
                gif_sheep.setPaused(isStop);
                gif_super.setPaused(isStop);
                isStop = !isStop;
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * 执行闭眼睛动画
     */
    public void doDealCloseEyesAnim() {
        //停止上一个动画
        stopNormalAnim();
        //执行闭眼的动画,替换成闭眼动画即可
        gif_sheep.setMovieResource(R.raw.animation_sheep_pw);
        gif_super.setMovieResource(R.raw.animation_super_pw);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //qq授权登录使用
        Tencent.onActivityResultData(requestCode, resultCode, data, QQLoginManager.getInstance().getQQIUiListener());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Util.isNull(gif_sheep)) {
            gif_sheep.setPaused(true);
        }
        if (!Util.isNull(gif_super)) {
            gif_super.setPaused(true);
        }
        if (!Util.isNull(handler)) {
            stopNormalAnim();
        }
    }

}
