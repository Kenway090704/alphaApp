package com.alpha.alphaapp.ui.widget.mine;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/8 11:16
 * Email : xiaokai090704@126.com
 * <p>
 * 修改信息的控件
 */

public class ModifyInfoItemView extends LinearLayout {
    private Context context;
    private String txt_left;
    private TextView tv_left, tv_info;
    private ImageView iv_right;
    private View view;

    public ModifyInfoItemView(Context context) {
        super(context);
    }

    public ModifyInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ModifyInfoItemView);
        txt_left = array.getString(R.styleable.ModifyInfoItemView_txt_item);
        array.recycle();
        initView();
    }

    private void initView() {

        view = LayoutInflater.from(context).inflate(R.layout.widget_modify_info_item, this);
        tv_left = (TextView) view.findViewById(R.id.widget_mod_info_tv_left);
        tv_info = (TextView) view.findViewById(R.id.widget_mod_info_tv_msg);
        iv_right = (ImageView) view.findViewById(R.id.widget_mod_info_iv_right);
        if (!Util.isNullOrBlank(txt_left))
            tv_left.setText(txt_left);
    }

    /**
     * 如果有信息,将信息设置到中间位置
     *
     * @param msg
     */
    public void setMsg(String msg) {
        tv_info.setText(msg);
    }

    /**
     * 获取信息部分的内容
     *
     * @return
     */
    public String getMsg() {
        return tv_info.getText().toString().trim();
    }

    /**
     * 右边图标的点击事件
     *
     * @param listener
     */
    public void setIvRightOnClicklistener(OnClickListener listener) {
        if (!Util.isNull(listener)){
            view.setOnClickListener(listener);
            iv_right.setOnClickListener(listener);
        }


    }

}
