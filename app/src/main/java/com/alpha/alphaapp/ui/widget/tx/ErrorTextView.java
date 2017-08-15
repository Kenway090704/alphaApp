package com.alpha.alphaapp.ui.widget.tx;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;

/**
 * Created by kenway on 17/7/31 11:51
 * Email : xiaokai090704@126.com
 * <p>
 * 错误信息提示TextView
 */

public class ErrorTextView extends LinearLayout {

    private static final String TAG = "ErrorTextView";
    private Context context;
    private TextView tv_error;

    public ErrorTextView(Context context) {
        super(context);
        this.context = context;
        initViews();
    }


    public ErrorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public ErrorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }


    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_error_hint_tv, this);
        tv_error = (TextView) view.findViewById(R.id.error_hint_tv);
    }

    public void setText(int strID) {
        tv_error.setText(strID);
        setVisibility(VISIBLE);
    }

    public void setText(String str) {
        tv_error.setText(str);
        setVisibility(VISIBLE);
    }

    public void setViewInVisible() {
        setVisibility(INVISIBLE);
    }


}
