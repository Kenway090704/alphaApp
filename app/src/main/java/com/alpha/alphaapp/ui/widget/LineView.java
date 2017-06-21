package com.alpha.alphaapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.alpha.alphaapp.R;

/**
 * Created by kenway on 17/6/12 11:26
 * Email : xiaokai090704@126.com
 * 一条直线
 */

public class LineView extends LinearLayout {
   private Context context;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_line_view, this);
    }
}
