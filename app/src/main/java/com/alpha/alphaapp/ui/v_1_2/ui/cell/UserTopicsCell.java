package com.alpha.alphaapp.ui.v_1_2.ui.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.UserTopicListBean;
import com.alpha.alphaapp.ui.v_1_2.ui.topic.TopicActivity;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/10/16 16:06
 * Email : xiaokai090704@126.com
 */

public class UserTopicsCell extends RVBaseCell<UserTopicListBean> {
    public UserTopicsCell(UserTopicListBean userTopicListBean) {
        super(userTopicListBean);
    }

    @Override
    public int getItemType() {
        return Entry.USERTOPICSCELL;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_user_topics, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        TextView tv_title = holder.getTextView(R.id.cell_user_topics_tv_title);
        tv_title.setText(mData.getSubject());
        holder.setText(R.id.cell_user_topics_tv_author, mData.getAuthor());
        holder.setText(R.id.cell_user_topics_tv_time, DateUtils.getDateFormat(" yyyy-MM-dd", Long.parseLong(mData.getPostdate()) * 1000));

        holder.setText(R.id.cell_user_topics_tv_like, "0");
        holder.setText(R.id.cell_user_topics_tv_post, "字段不存在");
        holder.setText(R.id.cell_user_topics_tv_look, "0");


        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入帖子详情页中
//                TopicActivity.actionStart(v.getContext(),);
            }
        });


    }
}
