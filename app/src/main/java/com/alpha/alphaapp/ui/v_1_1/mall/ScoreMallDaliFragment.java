package com.alpha.alphaapp.ui.v_1_1.mall;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.bean.GetIconBean;
import com.alpha.alphaapp.model.v_1_1.bean.GoodsBean;
import com.alpha.alphaapp.model.v_1_1.logic.GetGoodsListLogic;

import com.alpha.alphaapp.ui.v_1_1.mall.cell.GoodsCell;
import com.alpha.alphaapp.ui.v_1_1.mall.decoration.GoodsSpacesItemDecoration;
import com.alpha.alphaapp.ui.widget.mall.RSHTabLayout;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.uikit.adapter.base.Cell;
import com.alpha.lib_stub.uikit.adapter.decoration.SpacesItemDecoration;
import com.alpha.lib_stub.uikit.adapter.fragment.AbsMyBaseFragment;
import com.alpha.lib_stub.uikit.tab.TabUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/9/12 16:25
 * Email : xiaokai090704@126.com
 *
 * 积分商城fragment
 */

public class ScoreMallDaliFragment  extends AbsMyBaseFragment {

    private RSHTabLayout rsh_layout;
    private RecyclerView   rv;

    private int product_id_trans=91;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_score_mall_dali;
    }

    @Override
    public RecyclerView initRecyclerView(View view) {
        rv= (RecyclerView) view.findViewById(R.id.frag_score_mall_dali_rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        rv.addItemDecoration(new GoodsSpacesItemDecoration(8));
        return rv;
    }




    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        SwipeRefreshLayout sw = (SwipeRefreshLayout) getActivity().findViewById(R.id.frag_score_mall_dali_swlayout);
        return sw;
    }

    @Override
    public View addToolbar() {
        return null;
    }


    protected RecyclerView.LayoutManager initLayoutManger() {
        return   new GridLayoutManager(getContext(),2);
    }

    @Override
    public void onRecyclerViewInitialized() {
      //初始化View和数据加载

        //设置刷新进度条颜色

        setColorSchemeResources(R.color.common_red);
        loadData();
    }

    private void loadData() {
        mBaseAdapter.showLoading();

        //获取数据

        OnModelCallback<List<GoodsBean>> callBack = new OnModelCallback<List<GoodsBean>>() {
            @Override
            public void onModelSuccessed(List<GoodsBean> goodsBeens) {

                mBaseAdapter.hideLoading();
                if (Util.isNull(goodsBeens)||goodsBeens.size()==0){

                    mBaseAdapter.showEmpty();
//                    mBaseAdapter.showError();
                }else {
                    mBaseAdapter.addAll(getCells(goodsBeens));
                }
            }

            @Override
            public void onModelFailed(String failMsg) {
                mBaseAdapter.showError();
                LogUtils.e(failMsg);

            }
        };
        GetGoodsListLogic.doGetGoodsList(product_id_trans, TypeConstants.GET_GOODS_LIST_TYPE.EXCHANGE_COUNT, callBack);

    }

    @Override
    public void onPullRefresh() {
         //下拉刷新回调
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
    protected List<Cell> getCells(List list) {
        List<Cell> cells = new ArrayList<>();
        for (int i=0;i<list.size(); i++){
            cells.add(new GoodsCell((GoodsBean) list.get(i)));
        }
        return cells;
    }

    @Override
    public void initViews(View root) {
        rsh_layout= (RSHTabLayout) root.findViewById(R.id.frag_score_mall_dali_rsh_layout);
        rsh_layout.setSelectIndex(2);
        rv= (RecyclerView) root.findViewById(R.id.frag_score_mall_dali_rv);
    }

    @Override
    public void initEnvent() {

    }


    protected RecyclerView.ItemDecoration addItemDecoration() {
        return new GoodsSpacesItemDecoration(8);
    }
}
