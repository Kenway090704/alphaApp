package com.alpha.alphaapp.ui.v_1_2;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.v_1_2.cell.BannerCell;
import com.alpha.alphaapp.ui.v_1_2.cell.CustomCell;
import com.alpha.lib_stub.uikit.adapter.base.Cell;
import com.alpha.lib_stub.uikit.adapter.fragment.AbsBaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kenway on 17/8/14 14:14
 * Email : xiaokai090704@126.com
 * 论坛Fragment
 */

public class ForumFragment extends AbsBaseFragment<Entry> {

    @Override
    public void onRecyclerViewInitialized() {
        //初始化View和数据加载
        //设置刷新进度条颜色
        setColorSchemeResources(R.color.colorAccent);
        loadData();
    }



    @Override
    public void onPullRefresh() {
        //下拉刷新回调
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshing(false);
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        //上拉加载回调
//        loadMore();
    }



    protected List<Cell> getCells(List<Entry> list) {
        //根据实体生成Cell
        List<Cell> cells = new ArrayList<>();
//        for (int i=0;i<20;i++){
//            TestBean bean=new TestBean();
//            bean.setTitle("title"+i);
//            bean.setAge(i);
//            cells.add(new CustomCell(bean));
//        }
        cells.add(new BannerCell(Arrays.asList(DataMocker.images)));

        return cells;
    }

    private void loadData() {
        View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.manu_loading_layout,null);
        mBaseAdapter.showLoading(loadingView);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaseAdapter.hideLoading();
                mBaseAdapter.addAll(getCells(null));
            }
        },10000);

    }


}
