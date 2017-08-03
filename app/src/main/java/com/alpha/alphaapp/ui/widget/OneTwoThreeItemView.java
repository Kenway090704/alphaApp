package com.alpha.alphaapp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/8 18:10
 * Email : xiaokai090704@126.com
 * 手机注册,找回密码中的一步一步的控件
 */

public class OneTwoThreeItemView extends LinearLayout {

    private Context context;
    private ImageView ott_iv_one, ott_iv_two, ott_iv_three;
    private TextView tv_first, tv_second, tv_third;
    private LinearLayout layout_three;
    private String txt_first, txt_second, txt_third;
    /**
     * 当前是第几个
     */
    private int currentSelects;

    public OneTwoThreeItemView(Context context) {
        super(context);
    }

    public OneTwoThreeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.OneTwoThreeItemView);
        txt_first = array.getString(R.styleable.OneTwoThreeItemView_txt_first);
        txt_second = array.getString(R.styleable.OneTwoThreeItemView_txt_second);
        txt_third = array.getString(R.styleable.OneTwoThreeItemView_txt_third);

        currentSelects = array.getInt(R.styleable.OneTwoThreeItemView_curent_select, 0);
        array.recycle();
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_onetwothree_item, this);
        ott_iv_one = (ImageView) view.findViewById(R.id.ott_iv_one);
        ott_iv_two = (ImageView) view.findViewById(R.id.ott_iv_two);
        ott_iv_three = (ImageView) view.findViewById(R.id.ott_iv_three);

        tv_first = (TextView) view.findViewById(R.id.ott_tv_first_msg);
        tv_second = (TextView) view.findViewById(R.id.ott_tv_second_msg);
        tv_third = (TextView) view.findViewById(R.id.ott_tv_third_msg);
        layout_three = (LinearLayout) view.findViewById(R.id.ott_layout_three);
        setCurrentUI(currentSelects);

    }

    public void setCurrentUI(int currentSelects) {
        updateUI();
        tv_first.setText(txt_first);
        tv_second.setText(txt_second);

        if (!Util.isNullOrBlank(txt_third)) {
            tv_third.setText(txt_third);
        } else {
            layout_three.setVisibility(GONE);

        }
        switch (currentSelects) {
            case 1:
                tv_first.setTextColor(getResources().getColor(R.color.common_tv_dark_red));
                ott_iv_one.setImageResource(R.drawable.icon_select_1);
                break;
            case 2:


                tv_second.setTextColor(getResources().getColor(R.color.common_tv_dark_red));
                ott_iv_two.setImageResource(R.drawable.icon_select_2);
                break;
            case 3:
                tv_third.setTextColor(getResources().getColor(R.color.common_tv_dark_red));
                ott_iv_three.setImageResource(R.drawable.icon_select_3);
                break;
        }


    }
    /**
     * 将所有都变为未选中颜色
     *
     */
    public void updateUI() {
        tv_first.setTextColor(getResources().getColor(R.color.common_tv_dark_gray));
        ott_iv_one.setBackground(getResources().getDrawable(R.drawable.icon_normal_1));
        tv_second.setTextColor(getResources().getColor(R.color.common_tv_dark_gray));
        ott_iv_two.setBackground(getResources().getDrawable(R.drawable.icon_normal_2));
        tv_third.setTextColor(getResources().getColor(R.color.common_tv_dark_gray));
        ott_iv_three.setBackground(getResources().getDrawable(R.drawable.icon_normal_3));
    }
}
