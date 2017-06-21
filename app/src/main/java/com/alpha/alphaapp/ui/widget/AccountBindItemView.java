package com.alpha.alphaapp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/12 10:59
 * Email : xiaokai090704@126.com
 * 帐号和绑定设置
 */

public class AccountBindItemView extends LinearLayout {
    private Context context;
    private String left_txt;
    private String right_txt;
    private Button btn_right;
    private TextView tv_msg;


    private boolean isGray;//是否已经设置了

    public AccountBindItemView(Context context) {
        super(context);
    }

    public AccountBindItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AccountBindItemView);
        left_txt = array.getString(R.styleable.AccountBindItemView_txt_left);
        right_txt = array.getString(R.styleable.AccountBindItemView_txt_right);
        array.recycle();
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_acc_bind_item, this);
        TextView tv_left = (TextView) view.findViewById(R.id.widget_acc_bind_tv_left);
        tv_msg = (TextView) view.findViewById(R.id.widget_acc_bind_tv_msg);
        btn_right = (Button) view.findViewById(R.id.widget_acc_bind_btn_right);
        tv_left.setText(left_txt);
        if (!Util.isNullOrBlank(right_txt))
            btn_right.setText(right_txt);
    }


    public void setMsg(String msg) {
        tv_msg.setText(msg);
    }

    /**
     * 设置右边文字及bg颜色
     *
     * @param txt
     * @param bgResouredId
     */
    public void setRightTxtAndBg(String txt, int bgResouredId) {
        if (btn_right != null) {
            btn_right.setText(txt);
            btn_right.setBackground(getResources().getDrawable(bgResouredId));
        }
    }

    /**
     * 设置右边btn的点击事件
     * @param listener
     */
    public void setOnClicklistener(OnClickListener listener) {
        btn_right.setOnClickListener(listener);
    }

}
