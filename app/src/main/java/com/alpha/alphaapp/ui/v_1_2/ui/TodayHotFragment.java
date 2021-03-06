package com.alpha.alphaapp.ui.v_1_2.ui;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.SectionSimpleBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.alphaapp.model.v_1_2.logic.section.SectionLogic;
import com.alpha.alphaapp.ui.base.BaseFragment;
import com.alpha.alphaapp.ui.base.adapter.BaseFragmentPageAdapter;
import com.alpha.alphaapp.ui.v_1_2.ui.todayhot.AllSectionHotFragment;
import com.alpha.alphaapp.ui.v_1_2.ui.todayhot.SectionTopicListFragment;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kenway on 17/8/14 14:14
 * Email : xiaokai090704@126.com
 * 今日热门
 */

public class TodayHotFragment extends BaseFragment {

    private TabLayout tabLayout;

    private ViewPager vp;

    private List<Fragment> fragments;
    private boolean isLoad = false;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_today_hot;
    }

    @Override
    protected void initViews(View root) {
        tabLayout = (TabLayout) root.findViewById(R.id.frag_today_hot_tab);
        vp = (ViewPager) root.findViewById(R.id.frag_today_hot_vp);

    }


    @Override
    protected void initData() {
        if (!isLoad) {
            initTabAndVps();
        }

    }


    private void initTabAndVps() {
        //添加tab文字     //添加Fragment

        //添加今日头条的内容
        tabLayout.addTab(tabLayout.newTab().setText(ResourceUtil.resToStr(getContext(), R.string.today_hithot)));
        fragments = new ArrayList<>();
        fragments.add(new AllSectionHotFragment());

        //这需要使用子FragmetnManager来处理。
        final BaseFragmentPageAdapter adapter = new BaseFragmentPageAdapter(getChildFragmentManager(), fragments);

        vp.setAdapter(adapter);


        OnModelCallback<List<SectionSimpleBean>> call = new OnModelCallback<List<SectionSimpleBean>>() {
            @Override
            public void onModelSuccessed(List<SectionSimpleBean> beens) {

                for (int i = 0; i < beens.size(); i++) {
                    tabLayout.addTab(tabLayout.newTab().setText(beens.get(i).getForumname()));
                    SectionTopicListFragment sectionTopicListFragment = new SectionTopicListFragment();
                    // 因为父亲版块没有帖子数据
                    sectionTopicListFragment.setFid(Integer.parseInt(beens.get(i).getFid())+1+"", ForumNetPostUtil.VALUE_LASTPOST);
                    fragments.add(sectionTopicListFragment);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e(failedMsg);
            }
        };
        SectionLogic.getSectionSimpleInfoList(call);

        isLoad = true;

    }


    @Override
    protected void initEnvent() {
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


}
