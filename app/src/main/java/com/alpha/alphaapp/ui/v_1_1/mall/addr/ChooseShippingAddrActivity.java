package com.alpha.alphaapp.ui.v_1_1.mall.addr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.logic.ShippingAddrLogic;
import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.ChooseShipingAddrAdapter;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/19 16:39
 * Email : xiaokai090704@126.com
 */

public class ChooseShippingAddrActivity extends BaseActivity {

    private static final String TAG = "ChooseShippingAddrActivity";
    private TitleLayout titleLayout;
    private RecyclerView rv_shipping_addr;
    private List<ShippingAddrBean> alist;
    private ChooseShipingAddrAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_shipping_addr;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.choose_shipping_title);
        rv_shipping_addr = (RecyclerView) findViewById(R.id.choose_shipping_rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_shipping_addr.setLayoutManager(manager);
        alist = new ArrayList<>();
        adapter = new ChooseShipingAddrAdapter(ChooseShippingAddrActivity.this, alist);
        rv_shipping_addr.setAdapter(adapter);
    }

    @Override
    public void initData() {
        ShippingAddrLogic.doGetAddress(new OnModelCallback<List<ShippingAddrBean>>() {
            @Override
            public void onModelSuccessed(List<ShippingAddrBean> list) {
                alist.clear();
                alist.addAll(list);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onModelFailed(String failMsg) {

                LogUtils.e(failMsg);
            }
        });
    }

    @Override
    protected void initListener() {

    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChooseShippingAddrActivity.class);
        context.startActivity(intent);
    }
}
