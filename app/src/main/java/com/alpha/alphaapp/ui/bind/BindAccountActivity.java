package com.alpha.alphaapp.ui.bind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.sp.SharePLoginInfo;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.BaseFragmentActivity;
import com.alpha.alphaapp.ui.BaseFragmentPageAdapter;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/6/5 15:18
 * Email : xiaokai090704@126.com
 * <p>当首次授权第三方登录时,跳到该页面关联帐号,如果点击了暂不关联,
 * 直接创建新帐号,该app不再提示用户关联直接登录进入主页面
 * </p>
 */

public class BindAccountActivity extends BaseFragmentActivity {
    private static final String TAG = "BindAccountActivity";
    private String sskey;
    private ViewPager vp;
    private TabLayout tabLayout;
    private TextView tv;
    private List<Fragment> fragmentList;

    @Override
    protected int getLayoutId() {
        sskey = getIntent().getStringExtra("sskey");
        return R.layout.activity_bind;
    }

    @Override
    protected void initView() {


        vp = (ViewPager) findViewById(R.id.bind_vp);
        tabLayout = (TabLayout) findViewById(R.id.bind_tablayout);
        tv = (TextView) findViewById(R.id.bind_tv_no_bind);
    }

    @Override
    public void initData() {
        initFragments();
        initTabLayout();

    }


    private void initFragments() {

        fragmentList = new ArrayList<>();
        fragmentList.add(new AccountBindFragment());
        fragmentList.add(new PhoneBindFragment());
        BaseFragmentPageAdapter adapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), fragmentList);
        vp.setAdapter(adapter);
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("帐号"));
        tabLayout.addTab(tabLayout.newTab().setText("手机号"));

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
                tabLayout.getTabAt(position % fragmentList.size()).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePLoginInfo.getInstance(getApplicationContext()).saveIsBindAccount(false);
                HomeActivity.actionStart(BindAccountActivity.this, null, null);
            }
        });
    }

    public static void actionStart(Context context, String sskey, String data2) {
        Intent intent = new Intent(context, BindAccountActivity.class);
        intent.putExtra("sskey", sskey);
        intent.putExtra("params", data2);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }

    }

    public String getSskey() {
        return sskey;
    }

}
