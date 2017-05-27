package com.alpha.alphaapp.ui.login;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.BaseFragmentActivity;
import com.alpha.alphaapp.ui.BaseFragmentPageAdapter;
import com.alpha.alphaapp.ui.register.AccountRegisterFragment;
import com.alpha.alphaapp.ui.register.PhoneRegisterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/5/26 11:09
 * Email : xiaokai090704@126.com
 */

public class LoginActivity extends BaseFragmentActivity {
    private static final String TAG = "LoginActivity";
    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
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
                ;
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
}
