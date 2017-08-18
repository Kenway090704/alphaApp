package com.alpha.alphaapp.ui.v_1_2.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.ForumBean;
import com.alpha.alphaapp.ui.WebActivity;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.tool.SystemUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/8/15 15:12
 * Email : xiaokai090704@126.com
 */

public class HitHotCell extends RVBaseCell<ForumBean> {
    public HitHotCell(ForumBean forumBean) {
        super(forumBean);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_hithot, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        holder.setText(R.id.cell_hothit_tv_title, mData.getTitle());
        holder.setText(R.id.cell_hothit_tv_author, mData.getAuthor());
        holder.setText(R.id.cell_hothit_tv_model, mData.getAuthor());
        holder.setText(R.id.cell_hothit_tv_time, DateUtils.getDateFormat(" yyyy-MM-dd", SystemUtils.getCurrentTimeMillisLong()));
        holder.setText(R.id.cell_hothit_tv_commend, ResourceUtil.resToStr(R.string.commend)+" " + mData.getReplies());
        holder.setText(R.id.cell_hothit_tv_like, mData.getLike());
        holder.setText(R.id.cell_hothit_tv_look_num, mData.getHits());


        TextView tv_hint = holder.getTextView(R.id.cell_hothit_tv_title);
        tv_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(v.getContext(), mData.getUrl());
            }
        });
    }
}
