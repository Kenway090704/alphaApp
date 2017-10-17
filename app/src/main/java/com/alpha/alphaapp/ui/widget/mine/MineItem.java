package com.alpha.alphaapp.ui.widget.mine;

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
 * Created by kenway on 17/10/17 16:11
 * Email : xiaokai090704@126.com
 * <p>
 * minefragment 中主题登录item
 */

public class MineItem extends LinearLayout {
    private Context context;
    private View view;
    private String mi_name;
    private Drawable mi_icon;

    public MineItem(Context context) {
        super(context);
        this.context = context;

        initViews();
    }

    public MineItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MineItem);
        mi_name = array.getString(R.styleable.MineItem_mi_name);
        mi_icon = array.getDrawable(R.styleable.MineItem_mi_icon);
        initViews();
    }

    public MineItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        view = LayoutInflater.from(context).inflate(R.layout.widget_mine_item, this);
        TextView tv_name = (TextView) view.findViewById(R.id.mine_item_tv_name);
        ImageView iv_icon = (ImageView) view.findViewById(R.id.mine_item_iv_icon);
        tv_name.setText(mi_name);
        iv_icon.setImageDrawable(mi_icon);
    }


}
