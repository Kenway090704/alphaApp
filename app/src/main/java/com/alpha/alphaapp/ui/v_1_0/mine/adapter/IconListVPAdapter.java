package com.alpha.alphaapp.ui.v_1_0.mine.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/6/15 14:53
 * Email : xiaokai090704@126.com
 * 该Viewpager主要是加载的RecyclerView
 */

public class IconListVPAdapter extends PagerAdapter {
    private static final String TAG = "IconListVPAdapter";
    public static List<RecyclerView> recyclerViews;

    public IconListVPAdapter(List<RecyclerView> listRecyclerView) {
        this.recyclerViews = listRecyclerView;
    }

    @Override
    public int getCount() {
        return Util.isNull(recyclerViews) ? 0 : recyclerViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RecyclerView rcv = recyclerViews.get(position);
        ViewGroup parent = (ViewGroup) rcv.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(rcv, 0);
        return rcv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(recyclerViews.get(position));
    }




}
