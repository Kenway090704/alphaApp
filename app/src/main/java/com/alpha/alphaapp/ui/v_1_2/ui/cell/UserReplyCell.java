package com.alpha.alphaapp.ui.v_1_2.ui.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/10/17 11:37
 * Email : xiaokai090704@126.com
 * TA的回复
 */

public class UserReplyCell extends RVBaseCell<String> {
    public UserReplyCell(String s) {
        super(s);
    }

    @Override
    public int getItemType() {
        return Entry.USERREPLYCELL;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_user_reply, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        //进行参数设置
        holder.setText(R.id.cell_reply_tv_time, "2017-10-17");
        holder.setText(R.id.cell_reply_tv_content, "添加回复内容");
        TextView tv_subject = holder.getTextView(R.id.cell_user_reply_tv_subject);
        tv_subject.setText("添加帖子subject");
    }
}
