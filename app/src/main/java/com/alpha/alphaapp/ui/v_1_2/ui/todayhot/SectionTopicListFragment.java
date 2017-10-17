package com.alpha.alphaapp.ui.v_1_2.ui.todayhot;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.model.v_1_2.logic.section.SectionLogic;
import com.alpha.alphaapp.ui.v_1_2.ui.cell.SectionTopicCell;
import com.alpha.alphaapp.ui.v_1_2.ui.topic.TopicCreateActivity;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.uikit.adapter.base.Cell;
import com.alpha.lib_stub.uikit.adapter.fragment.AbsMyBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/10/11 18:00
 * Email : xiaokai090704@126.com
 */

public class SectionTopicListFragment extends AbsMyBaseFragment {


    private FloatingActionButton fab_resfresh, fab_create;
    private int offset = 0;
    private String fid;
    private String topicType;

    public void setFid(String fid, String topicType) {
        this.fid = fid;
        this.topicType = topicType;
    }


    @Override
    public void initViews(View root) {
        super.initViews(root);
        fab_resfresh = (FloatingActionButton) root.findViewById(R.id.frag_section_topic_list_fab_refresh);
        fab_create = (FloatingActionButton) root.findViewById(R.id.frag_section_topic_list_fab_create);


    }

    @Override
    public void initEnvent() {
        super.initEnvent();
        fab_resfresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(fab_resfresh.getContext(), "刷新界面");
            }
        });
        fab_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AccountManager.getInstance().isLogin(getContext()))
                    TopicCreateActivity.actionStart(fab_create.getContext(), fid);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_section_topic_list;
    }

    @Override
    public RecyclerView initRecyclerView(View view) {
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.frag_section_topic_list_rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return rv;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        SwipeRefreshLayout sw = (SwipeRefreshLayout) getActivity().findViewById(R.id.frag_section_topic_list_swlayout);
        return sw;
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
        OnModelCallback<List<TopicListBean>> call = new OnModelCallback<List<TopicListBean>>() {
            @Override
            public void onModelSuccessed(List<TopicListBean> topics) {
                mBaseAdapter.hideLoading();
                if (Util.isNull(topics) || topics.size() == 0) {
                    mBaseAdapter.showEmpty();
                } else {
                    mBaseAdapter.addAll(getCells(topics));
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                mBaseAdapter.showError();
                LogUtils.e(failedMsg);
            }
        };
        SectionLogic.getSectionHotTopicList(fid, topicType, offset, call);
    }

    @Override
    public void onPullRefresh() {
        offset = 0;
        OnModelCallback<List<TopicListBean>> call = new OnModelCallback<List<TopicListBean>>() {
            @Override
            public void onModelSuccessed(final List<TopicListBean> topics) {
                if (Util.isNull(topics) || topics.size() == 0) {
                    mBaseAdapter.showEmpty();
                } else {

                    setRefreshing(false);
                    mBaseAdapter.clear();
                    mBaseAdapter.addAll(getCells(topics));
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                mBaseAdapter.showError();
                LogUtils.e(failedMsg);
            }
        };
        SectionLogic.getSectionHotTopicList(fid, topicType, offset, call);

    }

    @Override
    public void onLoadMore() {
        offset += 20;
        OnModelCallback<List<TopicListBean>> call = new OnModelCallback<List<TopicListBean>>() {
            @Override
            public void onModelSuccessed(final List<TopicListBean> topics) {
                if (Util.isNull(topics) || topics.size() == 0) {
                    mBaseAdapter.showEmpty();
                } else {

                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBaseAdapter.hideLoadMore();
                            mBaseAdapter.addAll(getCells(topics));
                        }
                    }, 2000);
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                mBaseAdapter.showError();
                LogUtils.e(failedMsg);
            }
        };
        SectionLogic.getSectionHotTopicList(fid, topicType, offset, call);


    }

    @Override
    protected List<Cell> getCells(List list) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            cells.add(new SectionTopicCell((TopicListBean) list.get(i)));
        }
        return cells;
    }
}
