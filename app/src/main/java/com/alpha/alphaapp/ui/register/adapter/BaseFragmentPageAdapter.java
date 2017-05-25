package com.alpha.alphaapp.ui.register.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 发现页面Viewpager的FragmentPagerAdapter
 * Created by HanLei on 2016/8/8 0008.
 */
public class BaseFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public BaseFragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        if (fragmentList == null) {

            throw new NullPointerException("fragmentList can not be null!");
        }
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
