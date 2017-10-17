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
import com.alpha.alphaapp.ui.v_1_2.ui.cell.UserReplyCell;
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
 * TA的喜欢页面
 */

public class UserLikeActivtiy extends AbsBaseListActivity {

    private static final String UID = "uid";
    private String uid;
    private int offset = 0;
    private boolean isLoadMore=true;

    @Override
    public int getLayoutId() {

        uid = getIntent().getStringExtra(UID);
        return R.layout.activity_user_like;
    }

    @Override
    public void initViews(View root) {

    }

    @Override
    public RecyclerView initRecylerView(View view) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.acty_user_like_rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        return rv;
    }

    @Override
    public SwipeRefreshLayout initSwipeRefreshLayout(View view) {
        SwipeRefreshLayout sw = (SwipeRefreshLayout) findViewById(R.id.acty_user_like_sw);
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
        offset = 0;

        OnModelCallback<List<UserTopicListBean>> callback = new OnModelCallback<List<UserTopicListBean>>() {
            @Override
            public void onModelSuccessed(List<UserTopicListBean> topicListBeen) {


                if (Util.isNull(topicListBeen) || topicListBeen.size() == 0) {
                    mBaseAdapter.showEmpty();
                } else {

                    setRefreshing(false);
                    mBaseAdapter.clear();
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
    public void onLoadMore() {


        if (isLoadMore) {
            offset += 20;

            OnModelCallback<List<UserTopicListBean>> callback = new OnModelCallback<List<UserTopicListBean>>() {
                @Override
                public void onModelSuccessed(final List<UserTopicListBean> topicListBeen) {


                    if (Util.isNull(topicListBeen) || topicListBeen.size() == 0) {
                        mBaseAdapter.hideLoadMore();
                        //当全部数据都加载后,没有数据时,添加一条没有更多了
                        mBaseAdapter.add(new NoMoreCell("sf"));
                        isLoadMore = false;


                    } else {
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mBaseAdapter.hideLoadMore();
                                mBaseAdapter.addAll(getCells(topicListBeen));
                            }
                        }, 2000);
                    }
                }

                @Override
                public void onModelFailed(String failedMsg) {
                    mBaseAdapter.showError();
                }
            };
            ForumUserLogic.getUserTopics(uid, offset, callback);
        }else {
            mBaseAdapter.hideLoadMore();
        }

    }

    @Override
    public List<Cell> getCells(List list) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof UserTopicListBean) {
                cells.add(new UserTopicsCell((UserTopicListBean) list.get(i)));
            }
        }
        return cells;
    }

    public static void actionStart(Context context, String uid) {
        Intent intent = new Intent(context, UserLikeActivtiy.class);
        intent.putExtra(UID, uid);
        context.startActivity(intent);
    }
}
