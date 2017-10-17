package com.alpha.alphaapp.ui.v_1_2.ui.todayhot;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.BannerBean;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.model.v_1_2.logic.section.BannerLogic;
import com.alpha.alphaapp.model.v_1_2.logic.section.SectionLogic;
import com.alpha.alphaapp.ui.v_1_2.ui.cell.AllHitHotCell;
import com.alpha.alphaapp.ui.v_1_2.ui.cell.BannerCell;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.uikit.adapter.base.Cell;
import com.alpha.lib_stub.uikit.adapter.fragment.AbsMyBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/10/11 15:00
 * Email : xiaokai090704@126.com
 * 今日热门fragment
 */

public class AllSectionHotFragment extends AbsMyBaseFragment {

    private int offset = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all_section_hot;
    }

    @Override
    public RecyclerView initRecyclerView(View view) {
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.frag_all_section_hot_rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return rv;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        SwipeRefreshLayout swpr = (SwipeRefreshLayout) getActivity().findViewById(R.id.frag_all_section_hot_swlayout);
        return swpr;
    }


    @Override
    public void onRecyclerViewInitialized() {
        //初始化View和数据加载
        setColorSchemeResources(R.color.common_red);
        loadData();
    }

    private void loadData() {
        mBaseAdapter.showLoading();
        OnModelCallback<List<BannerBean>> callBeans = new OnModelCallback<List<BannerBean>>() {
            @Override
            public void onModelSuccessed(List<BannerBean> banList) {
                mBaseAdapter.hideLoading();


                if (Util.isNull(banList) || banList.size() == 0) {
                    mBaseAdapter.showEmpty();
//                    mBaseAdapter.showError();
                } else {
                    mBaseAdapter.add(new BannerCell(banList));
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        };

        BannerLogic.getTodayHotBanners(callBeans);


        OnModelCallback<List<TopicListBean>> call = new OnModelCallback<List<TopicListBean>>() {
            @Override
            public void onModelSuccessed(List<TopicListBean> topics) {
                mBaseAdapter.hideLoading();

                if (Util.isNull(topics) || topics.size() == 0) {
                    mBaseAdapter.showEmpty();
//                    mBaseAdapter.showError();
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
        SectionLogic.getAllSectionHotTopicList(offset, call);
    }

    @Override
    public void onPullRefresh() {
        offset = 0;

        OnModelCallback<List<BannerBean>> callBeans = new OnModelCallback<List<BannerBean>>() {
            @Override
            public void onModelSuccessed(List<BannerBean> banList) {
                mBaseAdapter.hideLoading();


                if (Util.isNull(banList) || banList.size() == 0) {
                    mBaseAdapter.showEmpty();
//                    mBaseAdapter.showError();
                } else {
                    mBaseAdapter.clear();
                    mBaseAdapter.add(new BannerCell(banList));
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        };

        BannerLogic.getTodayHotBanners(callBeans);
        OnModelCallback<List<TopicListBean>> call = new OnModelCallback<List<TopicListBean>>() {
            @Override
            public void onModelSuccessed(final List<TopicListBean> topics) {

                if (Util.isNull(topics) || topics.size() == 0) {
                    mBaseAdapter.showEmpty();
                } else {
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setRefreshing(false);

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
        SectionLogic.getAllSectionHotTopicList(offset, call);

    }

    @Override
    public void onLoadMore() {
        offset += 20;
        OnModelCallback<List<TopicListBean>> call = new OnModelCallback<List<TopicListBean>>() {
            @Override
            public void onModelSuccessed(final List<TopicListBean> topics) {

                if (Util.isNull(topics) || topics.size() == 0) {

                    mBaseAdapter.showEmpty();
//                    mBaseAdapter.showError();
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


        SectionLogic.getAllSectionHotTopicList(offset, call);

    }

    @Override
    protected List<Cell> getCells(List list) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof TopicListBean) {
                cells.add(new AllHitHotCell((TopicListBean) list.get(i)));
            }
        }
        return cells;
    }


}
