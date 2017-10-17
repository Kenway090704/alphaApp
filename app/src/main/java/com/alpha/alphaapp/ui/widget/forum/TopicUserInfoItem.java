package com.alpha.alphaapp.ui.widget.forum;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.TopicBean;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;

/**
 * Created by kenway on 17/9/22 17:50
 * Email : xiaokai090704@126.com
 * 帖子内容展示中的用户信息item
 */

public class TopicUserInfoItem extends LinearLayout {
    private Context context;
    private ImageView iv_icon;
    private TextView tv_author;
    private TextView tv_time;
    private TextView tv_post;
    private TextView tv_hints;
    private CheckBox cb_likecount;

    public TopicUserInfoItem(Context context) {
        super(context);
        this.context = context;
        initViews();
    }


    public TopicUserInfoItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public TopicUserInfoItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_forum_topic_serinfo_item, this);
        iv_icon = (ImageView) view.findViewById(R.id.widget_topic_iv_icon);
        tv_author = (TextView) view.findViewById(R.id.widget_topic_tv_author);
        tv_time = (TextView) view.findViewById(R.id.widget_topic_tv_time);
        tv_post = (TextView) view.findViewById(R.id.widget_topic_tv_post);
        tv_hints = (TextView) view.findViewById(R.id.widget_topic_tv_hits);
        cb_likecount = (CheckBox) view.findViewById(R.id.widget_topic_cb_likecount);
    }


    public void setData(TopicListBean bean) {
        ImageLoader.load(context, bean.getIcon(), iv_icon);
        tv_author.setText(bean.getAuthor());
        tv_time.setText(DateUtils.getDateFormat(" yyyy-MM-dd", Long.parseLong(bean.getPostdate()) * 1000));
        tv_post.setText(ResourceUtil.resToStr(R.string.commend) + " " + bean.getReplies());
        tv_hints.setText(bean.getHits());
        cb_likecount.setText(bean.getLike_count());
    }


    public void setData(TopicBean bean) {
        ImageLoader.load(context, bean.getIcon(), iv_icon);
        tv_author.setText(bean.getAuthor());
//        tv_time.setText(DateUtils.getDateFormat(" yyyy-MM-dd", Long.parseLong(bean.getPostdate()) * 1000));
        tv_post.setText(ResourceUtil.resToStr(R.string.commend) + " " + bean.getReplies());
        tv_hints.setText(bean.getHits());
        //因为现在没有该字段,后续添加后就可以使用该字段了
//        cb_likecount.setText(bean.getLike_count());
    }


}
