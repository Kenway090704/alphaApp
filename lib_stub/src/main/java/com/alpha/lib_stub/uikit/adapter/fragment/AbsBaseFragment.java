package com.alpha.lib_stub.uikit.adapter.fragment;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alpha.lib_stub.R;
import com.alpha.lib_stub.uikit.adapter.base.Cell;
import com.alpha.lib_stub.uikit.adapter.base.RVSimpleAdapter;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by kenway on 17/8/14 10:34
 * Email : xiaokai090704@126.com
 */

public abstract class AbsBaseFragment<T> extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RVSimpleAdapter mBaseAdapter;
    private FrameLayout mToolbarContainer;
    protected SwipeRefreshLayout mSwipeRefreshLayout;


    /**
     * RecyclerView最后可见Item在Adapter中的位置。
     */
    private int mLastVisibilePosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.base_fragement_layout, null);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.base_refresh_layout);
        mToolbarContainer = (FrameLayout) view.findViewById(R.id.toolbar_container);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_fragment_rv);
        mRecyclerView.setLayoutManager(initLayoutManger());

        mBaseAdapter = initAdapter();
        mRecyclerView.setAdapter(mBaseAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefreshing(true);
                onPullRefresh();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    mLastVisibilePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof GridLayoutManager) {
                    mLastVisibilePosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager stagerGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] lastPosition = new int[stagerGridLayoutManager.getSpanCount()];
                    stagerGridLayoutManager.findLastVisibleItemPositions(lastPosition);
                    mLastVisibilePosition = findMax(lastPosition);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View firstView = recyclerView.getChildAt(0);
                int top = firstView.getTop();
                int topEdge = recyclerView.getPaddingTop();
                //判断RecyclerView的itemView是否满屏,如果不满屏,上啦不会触发加载更多
                boolean isFullscreen = top < topEdge;
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                int itemCount = manager.getItemCount();
                //因为LoadMore View 是Adapter 的一个Item,显示LoadMore的时候,Item数量+1了,导致mLastVisibalePosition=itemCount-1

                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibilePosition == itemCount - 1
                        && isFullscreen && !mBaseAdapter.isShowLoadMore()) {
                    mBaseAdapter.showLoadMore();
                    onLoadMore();
                }
            }
        });
        View toolbarView = addToolbar();
        if (toolbarView != null && mToolbarContainer != null) {
            mToolbarContainer.addView(toolbarView);
        }
        onRecyclerViewInitialized();
    }

    /**
     * hide load more Progress
     */
    public void hideLoadMore() {
        if (mBaseAdapter != null) {
            mBaseAdapter.hideLoadMore();
        }
    }

    /**
     * 获取数组最大值
     *
     * @param lastPositon
     * @return
     */
    private int findMax(int[] lastPositon) {
        int max = lastPositon[0];
        for (int value : lastPositon) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 设置刷新进度条的颜色
     * @param colorResIds
     */
    public void setColorSchemeResoures(@ColorRes int... colorResIds) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
        }
    }

    /**
     * 设置刷新进度条的颜色
     * @param colors
     */
    public  void  setColorSchemeColor(int...colors){
        if (mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setColorSchemeColors(colors);
        }
    }

    /**
     * 设置刷新进度条背景色
     *  see{@link SwipeRefreshLayout#setProgressBackgroundColorSchemeResource(int)} (int)}
     * @param colorRes
     */
    public void setProgressBackgroundColorSchemeResource(@ColorRes int colorRes) {
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(colorRes);
        }
    }

    /**
     * 设置刷新进度条背景色
     *  see{@link SwipeRefreshLayout#setProgressBackgroundColorSchemeColor(int)}
     * @param color
     */
    public void setProgressBackgroundColorSchemeColor(@ColorInt int color) {
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(color);
        }
    }


    /**
     * Notify the widget that refresh state has changed. Do not call this when
     * refresh is triggered by a swipe gesture.
     *
     * @param refreshing Whether or not the view should show refresh progress.
     */
    public void setRefreshing(boolean refreshing){
        if(mSwipeRefreshLayout== null){
            return;
        }
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    /**
     * 子类可以自己指定Adapter,如果不指定默认RVSimpleAdapter
     * @return
     */
    protected RVSimpleAdapter initAdapter(){
        return new RVSimpleAdapter();
    }


    /**
     * 子类自己指定RecyclerView的LayoutManager,如果不指定，默认为LinearLayoutManager,VERTICAL 方向
     * @return
     */
    protected RecyclerView.LayoutManager initLayoutManger(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }


    /**
     * 添加TitleBar
     * @param
     */
    public View addToolbar(){
        //如果需要Toolbar,子类返回Toolbar View
        return null;
    }


    /**
     *RecyclerView 初始化完毕，可以在这个方法里绑定数据
     */
    public abstract void onRecyclerViewInitialized();


    /**
     * 下拉刷新
     */
    public abstract void onPullRefresh();

    /**
     * 上拉加载更多
     */
    public abstract void onLoadMore();

    /**
     *  根据实体生成对应的Cell
     * @param list  实体列表
     * @return cell列表
     */
    protected abstract List<Cell> getCells(List<T> list);

}
