package com.alpha.alphaapp.ui.v_1_2.ui.section;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.alphaapp.ui.base.BaseFragmentActivity;
import com.alpha.alphaapp.ui.base.adapter.BaseFragmentPageAdapter;

import com.alpha.alphaapp.ui.v_1_2.ui.todayhot.SectionTopicListFragment;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/10/12 16:36
 * Email : xiaokai090704@126.com
 * 版块页
 */

public class SectionListActivity extends BaseFragmentActivity {


    private TitleLayout titleLayout;
    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments;

    private static final String FID = "fid";
    private static final String FORUMNAME = "forumname";

    private String fid;
    private String forumName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_section_list;
    }

    @Override
    protected void initView() {
        fid = getIntent().getStringExtra(FID);
        forumName = getIntent().getStringExtra(FORUMNAME);
        titleLayout = (TitleLayout) findViewById(R.id.acty_section_list_title);
        titleLayout.setTitleText(forumName);
        tabLayout = (TabLayout) findViewById(R.id.acty_section_list_tab);
        vp = (ViewPager) findViewById(R.id.acty_section_list_vp);
    }

    @Override
    public void initData() {
        initTabAndVps();
    }

    private void initTabAndVps() {
        tabLayout.addTab(tabLayout.newTab().setText(ResourceUtil.resToStr(this, R.string.hothit)));
        tabLayout.addTab(tabLayout.newTab().setText(ResourceUtil.resToStr(this, R.string.lastpost)));
        tabLayout.addTab(tabLayout.newTab().setText(ResourceUtil.resToStr(this, R.string.essence)));
        tabLayout.addTab(tabLayout.newTab().setText(ResourceUtil.resToStr(this, R.string.vip)));
        fragments = new ArrayList<>();

        //获取版块热门帖
        SectionTopicListFragment frag_hot = new SectionTopicListFragment();
        frag_hot.setFid(fid, ForumNetPostUtil.VALUE_LASTPOST);
        fragments.add(frag_hot);
        //获取版块最新发帖
        SectionTopicListFragment frag_lastpost = new SectionTopicListFragment();
        frag_lastpost.setFid(fid, ForumNetPostUtil.VALUE_POSTDATE);
        fragments.add(frag_lastpost);


        //获取版块精华帖
        SectionTopicListFragment frag_essence = new SectionTopicListFragment();
        frag_hot.setFid(fid, " ");
        fragments.add(frag_essence);


        //添加会员fragment
        SectionVipFragment frag_vip = new SectionVipFragment();
        fragments.add(frag_vip);


        //这需要使用子FragmetnManager来处理。
        final BaseFragmentPageAdapter adapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), fragments);

        vp.setAdapter(adapter);

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

    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     */
    public static void actionStart(Context context, String fid, String forumName) {
        Intent intent = new Intent(context, SectionListActivity.class);
        intent.putExtra(FID, fid);
        intent.putExtra(FORUMNAME, forumName);
        context.startActivity(intent);
    }
}
