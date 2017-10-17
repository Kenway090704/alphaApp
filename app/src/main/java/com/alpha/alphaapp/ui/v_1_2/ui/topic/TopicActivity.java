package com.alpha.alphaapp.ui.v_1_2.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.ReplyBean;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.model.v_1_2.logic.reply.ReplyLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_2.ui.topic.adapter.ReplyListAdapter;
import com.alpha.alphaapp.ui.v_1_2.ui.topic.reply.ITopicView;
import com.alpha.alphaapp.ui.widget.forum.TopicHeader;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.util.List;

/**
 * Created by kenway on 17/9/25 17:39
 * Email : xiaokai090704@126.com
 */

public class TopicActivity extends BaseActivity implements ITopicView, SwipeRefreshLayout.OnRefreshListener {


    private HeaderAndFooterRecyclerView rv;
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton fabReply;


    private static final String TOPIC = "topic";
    private TopicListBean topic;
    private ReplyListAdapter adapter;
    private TopicHeader header;

    @Override
    protected int getLayoutId() {
        topic = (TopicListBean) getIntent().getSerializableExtra(TOPIC);
        return R.layout.activity_topic;
    }

    @Override
    protected void initView() {
        rv = (HeaderAndFooterRecyclerView) findViewById(R.id.acty_topic_recycler_view);
        initRecyclerView();
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.acty_topic_refresh_layout);
        initSwipeRefreshLayout();
        fabReply = (FloatingActionButton) findViewById(R.id.acty_topic_fab_reply);
    }

    private void initSwipeRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.color_accent);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        onRefresh();

    }

    private void initRecyclerView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        header = new TopicHeader(this);
        header.updateViews(topic);
        rv.addHeaderView(header);
        adapter = new ReplyListAdapter(this);
        rv.setAdapter(adapter);


    }

    @Override
    public void initData() {
        OnModelCallback<List<ReplyBean>> callback = new OnModelCallback<List<ReplyBean>>() {
            @Override
            public void onModelSuccessed(List<ReplyBean> replyBeans) {
                adapter.setReplyListAndNotify(replyBeans);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onModelFailed(String failedMsg) {
                refreshLayout.setRefreshing(false);
            }
        };
        ReplyLogic.getReplyList(topic.getTid(), 0, callback);
    }

    @Override
    protected void initListener() {

        fabReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topic != null && !Util.isNull(AccountManager.getInstance().getUid())) {
                    CreateReplyDialog createReplyView = CreateReplyDialog.CreateWithAutoTheme(TopicActivity.this, topic.getTid(), TopicActivity.this);
                    createReplyView.showWindow();
                }else {
                    ToastUtils.showToast(fabReply.getContext(), ResourceUtil.resToStr(fabReply.getContext(),R.string.no_had_login));
                }
            }
        });
    }
    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     */
    public static void actionStart(Context context, TopicListBean topic) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.putExtra(TOPIC, topic);
        context.startActivity(intent);
    }

    @Override
    public void onGetTopicOk(@NonNull Object topic) {

    }

    @Override
    public void onGetTopicFinish() {

    }

    @Override
    public void appendReplyAndUpdateViews(@NonNull List<ReplyBean> replyBeans) {

        //更新基本信息部分

        //更新评论列表
        adapter.setReplyListAndNotify(replyBeans);
        //定位到最后一条
        rv.smoothScrollToPosition(replyBeans.size());
        header.updateCreateUserInfo(topic.getTid());

    }

    @Override
    public void onRefresh() {
        header.updateCreateUserInfo(topic.getTid());
        initData();
    }
}
