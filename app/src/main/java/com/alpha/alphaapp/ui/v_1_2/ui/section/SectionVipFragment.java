package com.alpha.alphaapp.ui.v_1_2.ui.section;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.lib_stub.uikit.adapter.base.Cell;
import com.alpha.lib_stub.uikit.adapter.fragment.AbsMyBaseFragment;

import java.util.List;

/**
 * Created by kenway on 17/10/12 17:30
 * Email : xiaokai090704@126.com
 *
 * 版块中会员fragment
 */

public class SectionVipFragment extends AbsMyBaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.frag_section_vip;
    }

    @Override
    public RecyclerView initRecyclerView(View view) {
        RecyclerView rv= (RecyclerView) view.findViewById(R.id.frag_section_vip_rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return rv;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        SwipeRefreshLayout swpr = (SwipeRefreshLayout) getActivity().findViewById(R.id.frag_section_vip_swlayout);
        return swpr;
    }

    @Override
    public void onRecyclerViewInitialized() {

    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected List<Cell> getCells(List list) {
        return null;
    }
}
