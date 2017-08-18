package com.alpha.alphaapp.ui.v_1_2.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.ForumBean;
import com.alpha.alphaapp.ui.WebActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.tool.SystemUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by kenway on 17/8/15 15:12
 * Email : xiaokai090704@126.com
 * 最新发帖
 */

public class LastPostCell extends RVBaseCell<ForumBean> {
    public LastPostCell(ForumBean forumBean) {
        super(forumBean);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_lastpost, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        holder.setText(R.id.cell_lastpost_tv_title, mData.getTitle());
        RoundedImageView riv = (RoundedImageView) holder.getView(R.id.cell_lastpost_riv_author_icon);
        ImageLoader.loadCircle(riv.getContext(), mData.getAvatar_s(), riv);
        holder.setText(R.id.cell_lastpost_tv_author, mData.getAuthor());
        holder.setText(R.id.cell_lastpost_tv_time, DateUtils.getDateFormat(" yyyy-MM-dd", SystemUtils.getCurrentTimeMillisLong()));
        holder.setText(R.id.cell_lastpost_tv_model, mData.getAuthor());
        holder.setText(R.id.cell_lastpost_tv_commend, mData.getReplies());
        holder.setText(R.id.cell_lastpost_tv_look_num, mData.getHits());

        //点击标题跳转详细页面
        TextView tv_hint = holder.getTextView(R.id.cell_lastpost_tv_title);
        tv_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(v.getContext(), mData.getUrl());
            }
        });
    }
}
