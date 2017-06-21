package com.alpha.alphaapp.ui.widget.set;

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

/**
 * Created by kenway on 17/6/8 11:20
 * Email : xiaokai090704@126.com
 * 设置页面中的通用组合控件
 */

public class SetingItemView extends LinearLayout {

    private Context context;

    private Drawable left_icon;
    private String text;

    public SetingItemView(Context context) {
        super(context);
    }

    public SetingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //获取左边的小图标和文字信息
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SetingItemView);
        left_icon = array.getDrawable(R.styleable.SetingItemView_left_icon);
        text = array.getString(R.styleable.SetingItemView_text);
        array.recycle();
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_set_item, this);
        ImageView left_iv = (ImageView) view.findViewById(R.id.widget_set_item_leftiv_icon);
        TextView tv_text = (TextView) view.findViewById(R.id.widget_set_item_tv);
        left_iv.setImageDrawable(left_icon);
        tv_text.setText(text);
    }
}
