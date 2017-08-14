package com.alpha.lib_stub.uikit.adapter.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.alpha.lib_stub.uikit.adapter.cell.EmptyCell;
import com.alpha.lib_stub.uikit.adapter.cell.ErrorCell;
import com.alpha.lib_stub.uikit.adapter.cell.LoadMoreCell;
import com.alpha.lib_stub.uikit.adapter.cell.LoadingCell;

/**
 * Created by kenway on 17/8/11 18:11
 * Email : xiaokai090704@126.com
 */

public class RVSimpleAdapter extends RVBaseAdapter {

    public static final int ERROR_TYPE = Integer.MAX_VALUE - 1;
    public static final int EMPTY_TYPE = Integer.MAX_VALUE - 2;
    public static final int LOADING_TYPE = Integer.MAX_VALUE - 3;
    public static final int LOAD_MORE_TYPE = Integer.MAX_VALUE - 4;


    private EmptyCell emptyCell;
    private ErrorCell mErrorCell;
    private LoadingCell mLoadingCell;
    private LoadMoreCell mLoadMoreCell;

    //loadMore 是否已显示

    private boolean mIsShowLoadMore = false;

    @Override
    protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        //处理GridView布局
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    return (viewType == ERROR_TYPE || viewType == EMPTY_TYPE || viewType == LOADING_TYPE
                            || viewType == LOAD_MORE_TYPE) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        // 处理StaggeredGridLayoutManager 显示这个Span
        int position = holder.getAdapterPosition();
        int viewType = getItemViewType(position);
        if (isStaggeredGridLayout(holder)) {
            if (viewType == ERROR_TYPE || viewType == EMPTY_TYPE || viewType == LOADING_TYPE
                    || viewType == LOAD_MORE_TYPE) {

                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                //设置显示整个span
                params.setFullSpan(true);
            }
        }

    }

    /**
     * 判断是不是瀑布流
     *
     * @param holder
     * @return
     */
    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    /**
     * 显示LoadingView
     * <p>请求数据时调用,数据请求完毕时调用{@link #hideLoading}</P>
     */
    public void showLoading() {
        clear();
        add(mLoadingCell);
    }

    public void showLoading(View loadingView) {
        if (loadingView == null) {
            showLoading();
        }
        clear();
        mLoadingCell.setLoadingView(loadingView);
        add(mLoadingCell);
    }

    /**
     * 显示LoadingView
     * <P> 列表显示LoadingView并保留keepCount个Item</P>
     *
     * @param keepCount
     */
    public void showLoadingKeepCount(int keepCount) {
        if (keepCount < 0 || keepCount > mData.size()) {
            return;
        }
        remove(keepCount, mData.size() - keepCount);
        if (mData.contains(mLoadingCell)) {
            mData.remove(mLoadingCell);
        }
        add(mLoadingCell);
    }

    /**
     * hide Loading View
     */
    public void hideLoading() {
        if (mData.contains(mLoadingCell)) {
            mData.remove(mLoadingCell);
        }
    }

    /**
     * 显示错误提示
     * <p>当网络请求发生错误,需要在界面给出错误提示时,调用{@link #showError()}</p>
     */
    public void showError() {
        clear();
        add(mErrorCell);
    }

    /**
     * 显示错误提示
     * <P>当网络发生错误,需要在界面给出错误提示时,调用{@link #showErrorKeepCount(int)} ,并保留keepCount 条Item</P>
     *
     * @param keepCount keepCount  保留Item数量
     */
    public void showErrorKeepCount(int keepCount) {
        if (keepCount < 0 || keepCount > mData.size()) {
            return;
        }
        remove(keepCount, mData.size() - keepCount);
        if (mData.contains(mErrorCell)) {
            mData.remove(mErrorCell);
        }
        add(mErrorCell);
    }

    /**
     * 显示LoadMoreView
     * <p>当列表滑动到底部时,调用{@link #showLoadMore()}提示加载更多,加载完数据,调用{@link @hideLoadMore(}</p>
     */
    public void showLoadMore() {
        if (mData.contains(mLoadMoreCell)) {
            return;
        }
        add(mLoadMoreCell);
        mIsShowLoadMore = true;
    }

    /**
     * 隐藏LoadMoreView
     * <p>调用{@link #showLoadMore()} 之后,加载数据完成,调用{@link #hideLoadMore()}隐藏LoadMoreView</p>
     */
    public void hideLoadMore() {
        if (mData.contains(mLoadMoreCell)) {
            remove(mLoadMoreCell);
            mIsShowLoadMore = false;
        }
    }

    /**
     * LoadMore 是否已经显示
     *
     * @return
     */
    public boolean isShowLoadMore() {
        return mIsShowLoadMore;
    }

    public void showEmptyKeepCount(int keepCount) {
        if (keepCount < 0 || keepCount > mData.size()) {
            return;
        }
        remove(keepCount, mData.size() - keepCount);
        if (mData.contains(emptyCell)) {
            mData.remove(emptyCell);
        }
        add(emptyCell);
    }

    /**
     * 显示空View
     * <P>当页面没有数据的时候,调用{@link #showEmpty()} 显示空View,给用户提示</P>
     */
    public void showEmpty() {
        clear();
        add(emptyCell);
    }

    /**
     * 隐藏空View
     */
    public void hideEmpty() {
        if (mData.contains(emptyCell)) {
            remove(emptyCell);
        }
    }
}
