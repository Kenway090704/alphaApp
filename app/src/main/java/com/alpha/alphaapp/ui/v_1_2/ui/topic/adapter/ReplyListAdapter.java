package com.alpha.alphaapp.ui.v_1_2.ui.topic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.ReplyBean;
import com.alpha.alphaapp.model.v_1_2.logic.reply.ReplyStringUtil;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/9/25 18:32
 * Email : xiaokai090704@126.com
 */

public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.MyViewHolder> {
    private Context context;
    private final List<ReplyBean> replyList = new ArrayList<>();

    public ReplyListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    public List<ReplyBean> getReplyList() {
        return replyList;
    }

    public void setReplyListAndNotify(@NonNull List<ReplyBean> replyList) {
        this.replyList.clear();
        this.replyList.addAll(replyList);

        for (int n = 0; n < replyList.size(); n++) {
            ReplyBean reply = replyList.get(n);

        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return replyList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        private ReplyBean reply;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        void update(int position) {
            reply = replyList.get(position);
            updateReplyViews(reply, position);
        }


        void updateReplyViews(@NonNull ReplyBean reply, int position) {
            ImageView iv_logo = (ImageView) itemView.findViewById(R.id.cell_reply_iv_userIcon);
            ImageLoader.load(context, reply.getIcon(), iv_logo);
            TextView tv_userName = (TextView) itemView.findViewById(R.id.cell_reply_tv_userName);
            tv_userName.setText(reply.getCreated_username());
            TextView tv_userRank = (TextView) itemView.findViewById(R.id.cell_reply_tv_userRank);
            tv_userRank.setText("玩具收藏家");
            TextView tv_time = (TextView) itemView.findViewById(R.id.cell_reply_tv_time);
            tv_time.setText(DateUtils.getDateFormat("yyyy-MM-dd",Long.parseLong(reply.getCreated_time())*1000));

            TextView tv_floor = (TextView) itemView.findViewById(R.id.cell_reply_tv_floor);
            String floor = "";
            if (position == 0) {
                floor = "沙发";
            } else if (position == 1) {
                floor = "板凳";
            } else if (position == 2) {
                floor = "地板";
            } else {
                floor = (position + 1) + "楼";
            }
            tv_floor.setText(floor);
            //只有一条文章内容
            TextView tv_content = (TextView) itemView.findViewById(R.id.cell_reply_tv_content);


            //对某人的评论进行评论
            LinearLayout layout_two_reply = (LinearLayout) itemView.findViewById(R.id.cell_reply_layout_two_reply);
            TextView tv_hint_reply = (TextView) itemView.findViewById(R.id.cell_reply_tv_hint_reply);
            TextView tv_replyForPost = (TextView) itemView.findViewById(R.id.cell_reply_tv_reply_message);


            doDealReplyContent(reply.getContent(), tv_content, layout_two_reply, tv_hint_reply, tv_replyForPost);
//


            TextView tv_replyCount = (TextView) itemView.findViewById(R.id.cell_reply_tv_replyCount);
            tv_replyCount.setText(ResourceUtil.resToStr(context, R.string.commend) + reply.getReplies());

            TextView tv_likeCount = (TextView) itemView.findViewById(R.id.cell_reply_tv_likeCount);
            tv_likeCount.setText(reply.getLike_count());
        }


        public void doDealReplyContent(String content, TextView tv_content, LinearLayout layout_two_reply, TextView tv_hint_reply, TextView tv_replyPost) {
            //让TextView 如果有链接的时候可以使用默认的浏览器打开该链接
            ReplyStringUtil.textHtmlClick(context,tv_content);
            ReplyStringUtil.textHtmlClick(context,tv_hint_reply);
            ReplyStringUtil.textHtmlClick(context,tv_replyPost);


            if (content.indexOf(ReplyStringUtil.BLOCK_START) != -1) {
                //判断内容中是否有[quote,如果有,将[]中内容取出,然后以"," 分为两个数组,取出name,和uid,
                tv_content.setVisibility(View.GONE);
                layout_two_reply.setVisibility(View.VISIBLE);

                int start = content.indexOf(ReplyStringUtil.BLOCK_START);
                int end = content.indexOf(ReplyStringUtil.BLOCK_END);
                String first = content.substring(start + ReplyStringUtil.BLOCK_START.length(), end);

                //让html标签可以在TextView中展示。
                Spanned text_first = Html.fromHtml(first);

                tv_hint_reply.setText(text_first);


                String second = content.substring(end + ReplyStringUtil.BLOCK_END.length());
                Spanned text_second = Html.fromHtml(second);
                tv_replyPost.setText(text_second);

            } else {
                Spanned text_content = Html.fromHtml(content);
                tv_content.setText(text_content);
                tv_content.setVisibility(View.VISIBLE);
                layout_two_reply.setVisibility(View.GONE);
            }
        }
    }
}
