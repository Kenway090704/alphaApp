package com.alpha.alphaapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.bean.ProviceBean;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.ui.BaseFragmentActivity;
import com.alpha.alphaapp.ui.BaseFragmentPageAdapter;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/5/26 11:09
 * Email : xiaokai090704@126.com
 */

public class LoginActivity extends BaseFragmentActivity {
    private static final String TAG = "LoginActivity";
    private TitleLayout titlelayout;
    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        titlelayout = (TitleLayout) findViewById(R.id.login_titlelayout);
        titlelayout.setTitleText(R.string.login);
        tabLayout = (TabLayout) findViewById(R.id.login_tablayout);
        vp = (ViewPager) findViewById(R.id.login_vp);
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
        tabLayout.addTab(tabLayout.newTab().setText("普通登录"));
        tabLayout.addTab(tabLayout.newTab().setText("快速登录"));
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
                tabLayout.getTabAt(position % fragments.size()).select();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //qq授权登录使用
        Tencent.onActivityResultData(requestCode, resultCode, data, ((AccountLoginFragment) fragments.get(0)).getQQIUiListener());
    }


}
