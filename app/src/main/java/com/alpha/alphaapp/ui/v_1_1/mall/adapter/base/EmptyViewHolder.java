package com.alpha.alphaapp.ui.v_1_1.mall.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alpha.alphaapp.R;

/**
 * Created by kenway on 17/7/27 14:01
 * Email : xiaokai090704@126.com
 */

public class EmptyViewHolder extends ViewHolder {


    private TextView tv_hint;

    public EmptyViewHolder(Context mContext, View itemView) {
        super(mContext, itemView);

        tv_hint = (TextView) itemView.findViewById(R.id.adapter_empty_tv_hint);
    }


    public void setHintText(String txt) {
        tv_hint.setText(txt);
    }

    public void setHintText(int strID) {
        tv_hint.setText(strID);
    }
}
