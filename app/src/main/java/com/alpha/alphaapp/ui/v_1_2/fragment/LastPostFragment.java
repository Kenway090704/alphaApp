package com.alpha.alphaapp.ui.v_1_2.fragment;

import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.ForumBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumLogic;
import com.alpha.alphaapp.ui.v_1_2.cell.HitHotCell;
import com.alpha.alphaapp.ui.v_1_2.cell.LastPostCell;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.uikit.adapter.base.Cell;
import com.alpha.lib_stub.uikit.adapter.fragment.AbsMyBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/8/15 14:58
 * Email : xiaokai090704@126.com
 * 最新发帖
 */

public class LastPostFragment extends AbsMyBaseFragment<ForumBean> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_lastpost;
    }

    @Override
    public int getRecyclerView() {
        return R.id.frag_lastpost_rv;
    }

    @Override
    public int getSwipeRefreshLayout() {
        return R.id.frag_lastpost_refresh_layout;
    }

    @Override
    public View addToolbar() {
        return null;
    }

    @Override
    public void onRecyclerViewInitialized() {
        //初始化View和数据加载
        //设置刷新进度条颜色
        setColorSchemeResources(R.color.common_red);
        loadData();
    }



    @Override
    public void onPullRefresh() {
        //上啦加载
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        //下拉刷新回调
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaseAdapter.hideLoadMore();
            }
        }, 2000);
    }

    @Override
    protected List<Cell> getCells(List<ForumBean> list) {
        List<Cell> cells = new ArrayList<>();
        for (int i=0;i<list.size(); i++){
            cells.add(new LastPostCell( list.get(i)));
        }
        return cells;
    }

    private void loadData() {

        mBaseAdapter.showLoading();
        ForumLogic.getForumList(new OnModelCallback<List<ForumBean>>() {
            @Override
            public void onModelSuccessed(List<ForumBean> forumBeens) {
                mBaseAdapter.hideLoading();

                if (Util.isNull(forumBeens)&&forumBeens.size()==0){
//                    mBaseAdapter.showEmpty();
                }else {

                    mBaseAdapter.addAll(getCells(forumBeens));
                }

            }

            @Override
            public void onModelFailed(String failedMsg) {
                mBaseAdapter.showError();
                LogUtils.e(failedMsg);
            }
        });
    }

}
