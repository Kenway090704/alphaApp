package com.alpha.lib_stub.uikit.adapter.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.alpha.lib_stub.R;
import com.alpha.lib_stub.uikit.adapter.base.Cell;
import com.alpha.lib_stub.uikit.adapter.base.RVSimpleAdapter;
import com.githang.statusbar.StatusBarCompat;

import java.util.List;

/**
 * Created by kenway on 17/8/14 16:27
 * Email : xiaokai090704@126.com
 * RecyclerView   activity抽象类
 * <p>
 * http://www.jianshu.com/p/727c18f4bf20
 */

public abstract class AbsBaseListActivity extends Activity {
    public static final String TAG = "AbsBaseActivity";

    private FrameLayout mToolbarContainer;
    private FrameLayout mContentView;
    protected RecyclerView mRecyclerView;
    protected RVSimpleAdapter mBaseAdapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;


    /**
     * RecyclerView最后可见Item在Adapter中的位置
     */
    private int mLastVisiblePosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.rv_base_list_activity_layout);
        // 设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.common_red), false);
        init();
    }

    /**
     * 初始化布局
     * @return
     */
    public abstract int getLayoutId();


    /**
     * 非列表控件
     * @param root
     */
    public  abstract  void  initViews(View root);

    /**
     * 初始化RecyclerView 的对象LayoutManager,ItemDecoration
     * @param view --recycler所在的布局
     * @return
     */
    public  abstract   RecyclerView initRecylerView(View view);


    /**
     * 初始化下拉刷新
     * @param view
     * @return
     */
    public   abstract  SwipeRefreshLayout initSwipeRefreshLayout(View view);

    /**
     * 非列表控件事件监听
     */
    public   abstract  void  initEvent();

    /**
     * 非列表控件数据设置
     */
    public  abstract  void  initData();

    /**
     * RecyclerView 初始化完毕,可以在这个方法里绑定数据
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
     * @param list
     * @return
     */
    public   abstract List<Cell> getCells(List list);



    protected void init() {

        mToolbarContainer = (FrameLayout) findViewById(R.id.rv_my_base_acty_list_toolbar_container);
        mContentView = (FrameLayout)findViewById(R.id.rv_my_base_acty_list_layout_content);

        //添加标题栏
        View toolbarView = addToolbar();
        if (toolbarView != null && mToolbarContainer != null) {
            mToolbarContainer.addView(toolbarView);
        }

        //添加主布局

        View contentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        if (contentView != null && mContentView != null) {
            mContentView.addView(contentView);
        }

        //初始化非列表布局布局
        initViews(contentView);
        initEvent();
        initData();

        //下拉刷新
        mSwipeRefreshLayout = initSwipeRefreshLayout(contentView);

        //recycler view  初始化
        mRecyclerView = initRecylerView(contentView);
        mBaseAdapter = initAdapter();
        mRecyclerView.setAdapter(mBaseAdapter);


        //设置监听
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefreshing(true);
                onPullRefresh();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                View firstView = recyclerView.getChildAt(0);
                int top = firstView.getTop();
                int topEdge = recyclerView.getPaddingTop();
                //判断RecyclerView 的ItemView是否满屏，如果不满一屏，上拉不会触发加载更多
                boolean isFullScreen = top < topEdge;

                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                int itemCount = manager.getItemCount();
                //因为LoadMore View  是Adapter的一个Item,显示LoadMore 的时候，Item数量＋1了，导致 mLastVisibalePosition == itemCount-1
                // 判断两次都成立，因此必须加一个判断条件 !mBaseAdapter.isShowLoadMore()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisiblePosition == itemCount - 1 && isFullScreen && canShowLoadMore()) {
                    //最后一个Item了
                    showLoadMore();
                    onLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    mLastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof GridLayoutManager) {
                    mLastVisiblePosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    mLastVisiblePosition = findMax(lastPositions);
                }
            }
        });

        onRecyclerViewInitialized();
    }





    /**
     * 添加TitleBar
     *
     * @return
     */
    protected View addToolbar() {
        //如果需要Toolbar,子类返回ToolbarView
        return null;
    }


    /**
     * 判断是否可以显示LoadMore
     * @return
     */
    private boolean canShowLoadMore(){
        if(mBaseAdapter.isShowEmpty() || mBaseAdapter.isShowLoadMore()|| mBaseAdapter.isShowError() || mBaseAdapter.isShowLoading()){
            Log.i(TAG,"can not show loadMore");
            return false;
        }
        return true;
    }
    /**
     * hide load more progress
     */
    public void hideLoadMore(){
        if(mBaseAdapter!=null){
            mBaseAdapter.hideLoadMore();
        }
    }

    /**
     * show load more progress
     */
    protected void showLoadMore() {
        View loadMoreView = customLoadMoreView();
        if (loadMoreView == null) {
            mBaseAdapter.showLoadMore();
        } else {
            mBaseAdapter.showLoadMore(loadMoreView);
        }
    }

    protected View customLoadMoreView() {
        //如果需要自定义LoadMore View,子类实现这个方法
        return null;
    }

    /**
     * 获取数组中的最大值
     *
     * @param lastPositions
     * @return
     */
    protected int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    /**
     * 设置刷新进度条的颜色
     * see{@link SwipeRefreshLayout#setColorSchemeResources(int...)}
     * @param colorResIds
     */
    public void setColorSchemeResources(@ColorRes int... colorResIds){
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
        }
    }

    /**
     * 设置刷新进度条的颜色
     * see{@link SwipeRefreshLayout#setColorSchemeColors(int...)}
     * @param colors
     */
    public void setColorSchemeColors(int... colors){
        if(mSwipeRefreshLayout!=null){
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
     * 子类可以自己指定Adapter,如果不指定默认RVSimpleAdapter
     *
     * @return
     */
    protected RVSimpleAdapter initAdapter() {
        return new RVSimpleAdapter();
    }


    /**
     * 设置刷新状态
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing) {

        if (mSwipeRefreshLayout == null) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(refreshing);

    }


}
