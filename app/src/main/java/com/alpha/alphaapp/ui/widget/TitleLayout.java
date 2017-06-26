package com.alpha.alphaapp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/5/27 14:53
 * Email : xiaokai090704@126.com
 * 标题栏
 */

public class TitleLayout extends LinearLayout {
    private ImageView iv_back;
    private TextView tv_title;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout);
        String title = array.getString(R.styleable.TitleLayout_txt_title);
        array.recycle();
        LayoutInflater.from(context).inflate(R.layout.widget_titlelayout, this);
        iv_back = (ImageView) findViewById(R.id.title_iv_back);
        tv_title = (TextView) findViewById(R.id.title_tv_title);
        if (!Util.isNullOrBlank(title))
            tv_title.setText(title);
        setOnBackListener(null);

    }

    /**
     * 设置主题
     *
     * @param title
     */
    public void setTitleText(String title) {
        tv_title.setText(title);
    }

    /**
     * 使用strings中的文字字段
     *
     * @param strignId
     */
    public void setTitleText(int strignId) {
        tv_title.setText(strignId);
    }

    /**
     * 对Button Back 设置监听
     * <p>如果没有对标题中的back设置监听,则默认是finish当前页面<p/>
     *
     * @param onBackListener
     */
    public void setOnBackListener(final OnBackListener onBackListener) {
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackListener != null) {
                    onBackListener.onBackLister();
                } else {
                    ((Activity) iv_back.getContext()).finish();
                }


            }
        });
    }

    /**
     * btn_back接口
     */
    public interface OnBackListener {
        void onBackLister();
    }
}
