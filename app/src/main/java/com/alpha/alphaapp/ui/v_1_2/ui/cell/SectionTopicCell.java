package com.alpha.alphaapp.ui.v_1_2.ui.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.ui.v_1_2.ui.topic.TopicActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/9/21 14:11
 * Email : xiaokai090704@126.com
 */

public class SectionTopicCell extends RVBaseCell<TopicListBean> {
    public SectionTopicCell(TopicListBean Bean) {
        super(Bean);
    }

    @Override
    public int getItemType() {
        return Entry.SECTIONTOPICCELL;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_section_topic, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        holder.setText(R.id.cell_section_topic_tv_subject, mData.getSubject());
        holder.setText(R.id.cell_section_topic_tv_author, mData.getAuthor());
        holder.setText(R.id.cell_section_tv_time, DateUtils.getDateFormat(" yyyy-MM-dd", Long.parseLong(mData.getPostdate()) * 1000));
        holder.setText(R.id.cell_section_topic_tv_reply, ResourceUtil.resToStr(holder.getItemView().getContext(),R.string.commend) + " " + mData.getReplies());
        holder.setText(R.id.cell_section_topic_tv_like, mData.getLike_count());
        holder.setText(R.id.cell_section_topic_tv_look_num, mData.getHits());

        ImageView iv_icon = holder.getImageView(R.id.cell_section_topic_iv_icon);
        ImageLoader.load(holder.getItemView().getContext(), mData.getIcon(), iv_icon);

        //点击标题时的点击事件。
        final TextView tv_subject = holder.getTextView(R.id.cell_section_topic_tv_subject);
        tv_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopicActivity.actionStart(tv_subject.getContext(), mData);
            }
        });
    }
}
