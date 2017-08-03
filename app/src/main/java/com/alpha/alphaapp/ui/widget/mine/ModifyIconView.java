package com.alpha.alphaapp.ui.widget.mine;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_0.bean.GetIconBean;
import com.alpha.alphaapp.ui.v_1_0.mine.adapter.IconListVPAdapter;
import com.alpha.alphaapp.ui.v_1_0.mine.adapter.IconRecylcerAdapter;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tandong.bottomview.view.BottomView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kenway on 17/6/15 13:37
 * Email : xiaokai090704@126.com
 * 修改头像的自定义控件
 */

public class ModifyIconView extends LinearLayout {
    private static final String TAG = "ModifyIconView";


    private Context context;
    private RoundedImageView riv_icon;
    private ImageView iv_right;
    private BottomView bottomView;
    //底部Button;
    private View btmView;
    private TabLayout tabLayout;
    private ViewPager vp;
    private Button btn;
    private View view;

    public ModifyIconView(Context context) {
        super(context);
    }

    public ModifyIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        initBottomView();
    }

    /**
     * 初始化底部View
     */
    private void initBottomView() {
        bottomView = new BottomView(context,
                R.style.BottomViewTheme_Defalut, R.layout.widget_bottom_view_mod_icon);
        bottomView.setAnimation(R.style.BottomToTopAnim);//设置动画，可选
        btmView = bottomView.getView();
        tabLayout = (TabLayout) btmView.findViewById(R.id.dialog_mod_icon_tablayout);
        vp = (ViewPager) btmView.findViewById(R.id.dialog_mod_icon_vp);
        btn = (Button) btmView.findViewById(R.id.dialog_mod_icon_btn);
    }

    private void initView() {

        view = LayoutInflater.from(context).inflate(R.layout.widget_modify_icon_item, this);
        riv_icon = (RoundedImageView) view.findViewById(R.id.widget_mod_icon_riv_msg);
        iv_right = (ImageView) view.findViewById(R.id.widget_mod_icon_iv_right);
    }

    /**
     * @param baseUrl
     * @param icon
     */
    public void setIcon(String baseUrl, String icon) {
        String url = baseUrl + icon;
        ImageLoader.loadCircle(context, url, riv_icon);
    }

    public void setRightIVOnClicklistener(OnClickListener listener) {
        if (!Util.isNull(listener)){
            view.setOnClickListener(listener);
            iv_right.setOnClickListener(listener);
        }

    }

    /**
     * 弹出的对话框底部btn的监听事件
     *
     * @param listnenr
     */
    public void setBtnSaveOnClickListnenr(OnClickListener listnenr) {
        btn.setOnClickListener(listnenr);
    }

    /**
     * 弹出底部对话框
     * ,展示前请先设置数据
     *
     * @param canceledOnTouchOutside 是否可以点击对话框未取消
     */
    public void show(boolean canceledOnTouchOutside) {
        if (!Util.isNull(bottomView)) {
            ViewGroup parent = (ViewGroup) bottomView.getView().getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            bottomView.showBottomView(canceledOnTouchOutside);
        }

    }

    public void dismiss() {
        if (!Util.isNull(bottomView))
            bottomView.dismissBottomView();
    }

    /**
     * 设置底部弹出的数据
     *
     * @param listData
     */
    public void setBottomViewData(List<GetIconBean.IconListBean.CategoryBean> listData, Map<String, Boolean> map) {
        List<RecyclerView> list = new ArrayList<>();
        tabLayout.removeAllTabs();
        for (int i = 0; i < listData.size(); i++) {
            addCustomTab(listData, i);
//            tabLayout.addTab(tabLayout.newTab().setText(listData.get(i).getName()));
            Log.e(TAG, "添加" + (i + 1) + "tab");
            RecyclerView recyclerView = new RecyclerView(context);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            IconRecylcerAdapter adapter2 = new IconRecylcerAdapter(context, listData.get(i), map);
            recyclerView.setAdapter(adapter2);
            list.add(recyclerView);
            //遍历每一个图片的名字
            for (int j = 0; j < listData.get(i).getIcons().size(); j++) {
                map.put(listData.get(i).getIcons().get(j), false);
            }
        }
        IconListVPAdapter adapter = new IconListVPAdapter(list);
        vp.setAdapter(adapter);


        initEvent();

    }

    private void addCustomTab(List<GetIconBean.IconListBean.CategoryBean> listData, int i) {
        TabLayout.Tab tab = tabLayout.newTab();
        View view = LayoutInflater.from(context).inflate(R.layout.widget_choose_icon_tab_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.choose_icon_tab_tv);
        tv.setText(listData.get(i).getName());
        tab.setCustomView(view);
        tabLayout.addTab(tab);
    }

    /**
     * tab与ViewPager的监听
     */
    private void initEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tv = (TextView) tab.getCustomView().findViewById(R.id.choose_icon_tab_tv);
                tv.setTextColor(getResources().getColor(R.color.common_tv_dark_red));
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tv = (TextView) tab.getCustomView().findViewById(R.id.choose_icon_tab_tv);
                tv.setTextColor(getResources().getColor(R.color.common_tv_dark_gray));
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
                TabLayout.Tab tab2 = tabLayout.getTabAt(position);
                if (!Util.isNull(tab2))
                    tab2.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
