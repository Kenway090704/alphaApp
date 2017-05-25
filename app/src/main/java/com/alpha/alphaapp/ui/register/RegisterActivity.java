package com.alpha.alphaapp.ui.register;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.BaseFragmentActivity;
import com.alpha.alphaapp.ui.register.adapter.BaseFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/5/24 14:58
 * Email : xiaokai090704@126.com
 */

public class RegisterActivity extends BaseFragmentActivity {
    private static final String TAG = "RegisterActivity";

    private TabLayout tabLayout;
    private ViewPager vp;

    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        tabLayout = (TabLayout) findViewById(R.id.register_tablayout);
        vp = (ViewPager) findViewById(R.id.register_vp);
    }

    @Override
    public void initData() {
        initTabLayout();
        initFragments();


    }

    private void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new AccountRegisterFragment());
        fragmentList.add(new PhoneRegisterFragment());


        BaseFragmentPageAdapter adapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), fragmentList);
        vp.setAdapter(adapter);
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("帐号注册"));
        tabLayout.addTab(tabLayout.newTab().setText("手机注册"));
    }


    @Override
    protected void initListener() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position % fragmentList.size()).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
}
