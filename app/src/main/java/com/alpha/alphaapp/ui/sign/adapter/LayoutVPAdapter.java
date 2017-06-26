package com.alpha.alphaapp.ui.sign.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by kenway on 17/6/21 14:37
 * Email : xiaokai090704@126.com
 */

public class LayoutVPAdapter extends PagerAdapter {

    private static final String TAG = "LayoutVPAdapter";
    public static List<LinearLayout> layouts;

    public LayoutVPAdapter(List<LinearLayout> layouts) {
        this.layouts = layouts;
    }


    @Override
    public int getCount() {
        return layouts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout layout = layouts.get(position);
        ViewGroup parent = (ViewGroup) layout.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(layout, 0);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(layouts.get(position));
    }
}
