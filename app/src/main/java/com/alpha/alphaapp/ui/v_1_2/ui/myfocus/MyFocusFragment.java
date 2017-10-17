package com.alpha.alphaapp.ui.v_1_2.ui.myfocus;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.SectionSimpleBean;
import com.alpha.alphaapp.model.v_1_2.logic.section.SectionLogic;
import com.alpha.alphaapp.ui.base.BaseFragment;
import com.alpha.alphaapp.ui.v_1_2.ui.decoration.PhotoSpacesItemDecoration;
import com.alpha.alphaapp.ui.v_1_2.ui.adapter.RecomSectionAdapter;
import com.alpha.lib_sdk.app.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/10/12 10:28
 * Email : xiaokai090704@126.com
 * 我的关注
 */

public class MyFocusFragment extends BaseFragment {


    private RecyclerView rv_recom;

    private List<SectionSimpleBean> list_recom = new ArrayList<>();
    private RecomSectionAdapter adapter_recom;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_my_focus;
    }

    @Override
    protected void initViews(View root) {
        rv_recom = (RecyclerView) root.findViewById(R.id.frag_my_focus_rv_recom_seciton);
        initRv_Recom();
    }

    private void initRv_Recom() {
        rv_recom.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rv_recom.addItemDecoration(new PhotoSpacesItemDecoration(8));
        adapter_recom = new RecomSectionAdapter(getContext(), list_recom);
        rv_recom.setAdapter(adapter_recom);
    }

    @Override
    protected void initEnvent() {

    }

    @Override
    protected void initData() {
        OnModelCallback<List<SectionSimpleBean>> call = new OnModelCallback<List<SectionSimpleBean>>() {
            @Override
            public void onModelSuccessed(List<SectionSimpleBean> beens) {

                list_recom.clear();
                list_recom.addAll(beens);
                adapter_recom.notifyDataSetChanged();
            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e(failedMsg);
            }
        };
        SectionLogic.getSectionSimpleInfoList(call);
    }
}
