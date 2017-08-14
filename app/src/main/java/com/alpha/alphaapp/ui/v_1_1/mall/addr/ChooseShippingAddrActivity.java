package com.alpha.alphaapp.ui.v_1_1.mall.addr;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.alphaapp.model.v_1_1.logic.ShippingAddrLogic;
import com.alpha.alphaapp.ui.v_1_1.mall.cell.ShippingAddrCell;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.uikit.adapter.activity.AbsBaseActivity;
import com.alpha.lib_stub.uikit.adapter.base.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/8/14 17:17
 * Email : xiaokai090704@126.com
 * 兑换物品时更换收获地址页面
 */

public class ChooseShippingAddrActivity extends AbsBaseActivity {
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
        }, 2000);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public List<Cell> getCells(List list) {
        List<Cell> cells = new ArrayList<>();
      for (int i=0;i<list.size(); i++){
          cells.add(new ShippingAddrCell((ShippingAddrBean) list.get(i)));
      }
        return cells;
    }




    private void loadData() {
        View loadingView = LayoutInflater.from(this).inflate(R.layout.manu_loading_layout, null);
        mBaseAdapter.showLoading(loadingView);

        ShippingAddrLogic.doGetAddress(new OnModelCallback<List<ShippingAddrBean>>() {
            @Override
            public void onModelSuccessed(final List<ShippingAddrBean> list) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBaseAdapter.hideLoading();
                        mBaseAdapter.addAll(getCells(list));
                    }
                }, 0);
            }

            @Override
            public void onModelFailed(String failMsg) {
                mBaseAdapter.hideLoading();
                mBaseAdapter.showError();
                LogUtils.e(failMsg);
            }
        });


    }


    @Override
    protected View addToolbar() {
        return new TitleLayout(this, ResourceUtil.resToStr(this,R.string.choose_shipping_addr));
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChooseShippingAddrActivity.class);
        context.startActivity(intent);
    }
}
