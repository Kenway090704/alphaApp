package com.alpha.alphaapp.ui.v_1_2.ui.user;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.UserTopicListBean;
import com.alpha.alphaapp.model.v_1_2.logic.user.ForumUserLogic;
import com.alpha.alphaapp.ui.v_1_2.ui.cell.NoMoreCell;
import com.alpha.alphaapp.ui.v_1_2.ui.cell.UserFansCell;
import com.alpha.alphaapp.ui.v_1_2.ui.cell.UserTopicsCell;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.uikit.adapter.activity.AbsBaseListActivity;
import com.alpha.lib_stub.uikit.adapter.base.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/10/16 14:48
 * Email : xiaokai090704@126.com
 * TA的粉丝页面
 */

public class UserFansActivtiy extends AbsBaseListActivity {

    private static final String UID = "uid";
    private String uid;
    private int offset = 0;
    private boolean isLoadMore=true;

    @Override
    public int getLayoutId() {

        uid = getIntent().getStringExtra(UID);
        return R.layout.activity_user_fans;
    }

    @Override
    public void initViews(View root) {

    }

    @Override
    public RecyclerView initRecylerView(View view) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.acty_user_fans_rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        return rv;
    }

    @Override
    public SwipeRefreshLayout initSwipeRefreshLayout(View view) {
        SwipeRefreshLayout sw = (SwipeRefreshLayout) findViewById(R.id.acty_user_fans_sw);
        return sw;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onRecyclerViewInitialized() {
        //初始化View和数据加载
        setColorSchemeResources(R.color.common_red);
        loadData();
    }

    private void loadData() {
        offset = 0;
        mBaseAdapter.showLoading();
        //现在没有接口,到时候要换为TA的粉丝的接口
        OnModelCallback<List<UserTopicListBean>> callback = new OnModelCallback<List<UserTopicListBean>>() {
            @Override
            public void onModelSuccessed(List<UserTopicListBean> topicListBeen) {
                LogUtils.e(topicListBeen.toString());
                mBaseAdapter.hideLoading();
                if (Util.isNull(topicListBeen) || topicListBeen.size() == 0) {
                    mBaseAdapter.showEmpty();
                } else {
                    mBaseAdapter.addAll(getCells(topicListBeen));
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                mBaseAdapter.showError();
            }
        };
        ForumUserLogic.getUserTopics(uid, offset, callback);

    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onLoadMore() {


    }

    @Override
    public List<Cell> getCells(List list) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
                cells.add(new UserFansCell(("aad")));
        }
        return cells;
    }

    public static void actionStart(Context context, String uid) {
        Intent intent = new Intent(context, UserFansActivtiy.class);
        intent.putExtra(UID, uid);
        context.startActivity(intent);
    }
}
