package com.alpha.alphaapp.ui.widget.addtheme;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.ThemeBean;
import com.alpha.alphaapp.model.v_1_2.logic.theme.ThemeLogic;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/10/12 18:54
 * Email : xiaokai090704@126.com
 * 添加话题的控件
 */

public class AddThemeLayout extends LinearLayout {

    private Context context;
    private TextView tv_hint;
    private RecyclerView rv_themes;
    private RecyclerView rv_choose;
    private ChooseThemesAdapter adapter_choose;
    private List<ThemeBean> list_choose;
    private ThemesAdapter adapter_all;
    private List<ThemeBean> list_all;


    public AddThemeLayout(Context context) {
        super(context);
        this.context = context;
        initViews();
    }


    public AddThemeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public AddThemeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {

        View view = LayoutInflater.from(context).inflate(R.layout.widget_add_them, this);
        tv_hint = (TextView) view.findViewById(R.id.wid_add_theme_tv_hint);
        rv_choose = (RecyclerView) view.findViewById(R.id.wid_add_theme_rv_choose);
        rv_themes = (RecyclerView) view.findViewById(R.id.wid_add_theme_rv_themes);

        initRv_Choose();
        initRv_Themes();
    }

    /**
     * 初始化选中的话题rv
     */
    private void initRv_Choose() {
        rv_choose.setLayoutManager(new GridLayoutManager(context, 3));
        list_choose = new ArrayList<>();
        adapter_choose = new ChooseThemesAdapter(context, list_choose);
        rv_choose.setAdapter(adapter_choose);
        adapter_choose.addOnClickItemListener(new ChooseThemesAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(ThemeBean bean, int postion) {
                list_choose.remove(postion);
                adapter_choose.notifyDataSetChanged();
                list_all.add(bean);
                adapter_all.notifyDataSetChanged();

                if (list_choose.size() == 0) {

                    rv_choose.setVisibility(GONE);
                    tv_hint.setVisibility(VISIBLE);
                }

            }
        });
    }


    /**
     * 初始化全部话题rv
     */
    private void initRv_Themes() {
        rv_themes.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        list_all = new ArrayList<>();
        adapter_all = new ThemesAdapter(context, list_all);

        rv_themes.setAdapter(adapter_all);

        OnModelCallback<List<ThemeBean>> callback = new OnModelCallback<List<ThemeBean>>() {
            @Override
            public void onModelSuccessed(List<ThemeBean> list) {


                list_all.clear();
                list_all.addAll(list);
                adapter_all.notifyDataSetChanged();
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        };
        ThemeLogic.getThemes(callback);


        adapter_all.addOnClickItemListener(new ThemesAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(ThemeBean bean, int postion) {


                if (list_choose.size() < 5) {
                    list_all.remove(list_all.get(postion));
                    adapter_all.notifyDataSetChanged();
                    list_choose.add(bean);
                    adapter_choose.notifyDataSetChanged();
                    tv_hint.setVisibility(GONE);
                    rv_choose.setVisibility(VISIBLE);
                } else {
                    ToastUtils.showToast(context, "最多可以添加5个话题");
                }


            }
        });

    }

    /**
     * 获取选择的话题
     * @return
     */
    public List<ThemeBean> getChooseThemes() {
        return list_choose;
    }


}
