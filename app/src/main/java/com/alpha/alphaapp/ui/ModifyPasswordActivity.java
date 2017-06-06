package com.alpha.alphaapp.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.register.AccountRegisterFragment;
import com.alpha.alphaapp.ui.register.PhoneRegisterFragment;

import java.util.ArrayList;
import java.util.List;

public class ModifyPasswordActivity extends BaseFragmentActivity {

    private static final String TAG = "ModifyPasswordActivity";

    private TabLayout tabLayout;
    private ViewPager vp;

    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initView() {
        tabLayout = (TabLayout) findViewById(R.id.modify_password_tablayout);
        vp = (ViewPager) findViewById(R.id.modify_password_vp);

    }

    @Override
    public void initData() {
        initTabLayout();
        initFragments();
    }

    private void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ModifyPasswordByPasswordFragment());
        fragmentList.add(new ModifyPasswordByPhoneFragment());
        fragmentList.add(new ModifyPasswordByPasswordFragment());


        BaseFragmentPageAdapter adapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), fragmentList);
        vp.setAdapter(adapter);
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.by_password));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.by_phone));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.by_wechat));
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
}
