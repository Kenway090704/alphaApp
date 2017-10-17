package com.alpha.alphaapp.ui.v_1_2.ui.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.ui.v_1_2.ui.topic.TopicActivity;
import com.alpha.alphaapp.ui.v_1_2.ui.user.ForumPersionInfoActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/8/15 15:12
 * Email : xiaokai090704@126.com
 */

public class AllHitHotCell extends RVBaseCell<TopicListBean> {
    public AllHitHotCell(TopicListBean hitHotTopicBean) {
        super(hitHotTopicBean);
    }

    @Override
    public int getItemType() {
        return Entry.ALLHITHOTCELL;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_hithot, null));
    }

    @Override
    public void onBindViewHolder(final RVBaseViewHolder holder, int position) {
        holder.setText(R.id.cell_hothit_tv_title, mData.getSubject());
        holder.setText(R.id.cell_hothit_tv_author, mData.getAuthor());
        holder.setText(R.id.cell_hothit_tv_model, mData.getForumname());
        holder.setText(R.id.cell_hothit_tv_time, DateUtils.getDateFormat(" yyyy-MM-dd", Long.parseLong(mData.getPostdate()) * 1000));
        holder.setText(R.id.cell_hothit_tv_commend, ResourceUtil.resToStr(holder.getItemView().getContext(), R.string.commend) + " " + mData.getReplies());
        holder.setText(R.id.cell_hothit_tv_like, mData.getLike_count());
        holder.setText(R.id.cell_hothit_tv_look_num, mData.getHits());

        TextView tv_author = holder.getTextView(R.id.cell_hothit_tv_author);
        tv_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForumPersionInfoActivity.actionStart(v.getContext(),mData);
            }
        });

        ImageView iv = holder.getImageView(R.id.cell_hothit_iv_aid);
        if (Util.isNull(mData.getAttach()) || mData.getAttach().size() == 0) {
            iv.setVisibility(View.GONE);
        } else {
            ImageLoader.load(iv.getContext(), mData.getAttach().get(0), iv);
            iv.setVisibility(View.VISIBLE);
        }


        final TextView tv_hint = holder.getTextView(R.id.cell_hothit_tv_title);
        tv_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //展示帖子详情
                TopicActivity.actionStart(tv_hint.getContext(), mData);
            }
        });
    }
}
