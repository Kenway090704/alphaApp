package com.alpha.alphaapp.ui.v_1_2.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.base.BaseFragment;
import com.alpha.alphaapp.ui.base.adapter.BaseFragmentPageAdapter;
import com.alpha.alphaapp.ui.v_1_0.bind.firstbind.AccountBindFragment;
import com.alpha.alphaapp.ui.v_1_0.bind.firstbind.PhoneBindFragment;
import com.alpha.alphaapp.ui.v_1_2.cell.BannerCell;

import com.alpha.alphaapp.ui.v_1_2.fragment.HitHotFragment;
import com.alpha.alphaapp.ui.v_1_2.fragment.LastPostFragment;
import com.alpha.alphaapp.ui.v_1_2.fragment.MyForumFragment;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.uikit.adapter.base.Cell;
import com.alpha.lib_stub.uikit.adapter.fragment.AbsBaseFragment;
import com.alpha.lib_stub.uikit.adapter.fragment.AbsMyBaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kenway on 17/8/14 14:14
 * Email : xiaokai090704@126.com
 * 论坛Fragment
 */

public class ForumFragment extends BaseFragment {


    private TabLayout tablayout;
    private ViewPager vp;

    private List<Fragment> fragments;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void initViews(View root) {
        tablayout = (TabLayout) root.findViewById(R.id.fragment_forum_tablayout);
        vp = (ViewPager) root.findViewById(R.id.fragment_forum_vp);

        initFragments();
        initTabLayout();
    }

    @Override
    protected void initEnvent() {
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                TabLayout.Tab tab = tablayout.getTabAt(position % fragments.size());
                if (!Util.isNull(tab))
                    tab.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTabLayout() {
        tablayout.addTab(tablayout.newTab().setText(ResourceUtil.resToStr(getContext(),R.string.today_hithot)));
        tablayout.addTab(tablayout.newTab().setText(ResourceUtil.resToStr(getContext(),R.string.latest_post)));
        tablayout.addTab(tablayout.newTab().setText(ResourceUtil.resToStr(getContext(),R.string.bbs_forum_my)));
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new HitHotFragment());
        fragments.add(new LastPostFragment());
        fragments.add(new MyForumFragment());

        BaseFragmentPageAdapter  adapter=new BaseFragmentPageAdapter(getFragmentManager(), fragments);
        vp.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
