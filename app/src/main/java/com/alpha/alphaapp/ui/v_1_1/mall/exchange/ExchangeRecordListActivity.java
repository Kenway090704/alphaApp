package com.alpha.alphaapp.ui.v_1_1.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.logic.GetExchangeRecordLogic;
import com.alpha.alphaapp.model.v_1_1.bean.OrderBean;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.ExchangeRecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/17 14:08
 * Email : xiaokai090704@126.com
 * 兑换记录(商品订单详情)
 */

public class ExchangeRecordListActivity extends BaseActivity {
    private static final String TAG = "ExchangeRecordListActivity";
    private static final String PRODUCT_ID = "PRODUCT_ID";
    private int product_id;

    private RecyclerView rv;
    private List<OrderBean> list;
    private ExchangeRecordAdapter adapter;

    @Override
    protected int getLayoutId() {
        product_id = getIntent().getIntExtra(PRODUCT_ID, -1);
        return R.layout.activity_mall_exchange_record_list;
    }

    @Override
    protected void initView() {
        rv = (RecyclerView) findViewById(R.id.exchange_record_rv);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new ExchangeRecordAdapter(ExchangeRecordListActivity.this, list);
        rv.setAdapter(adapter);

    }

    @Override
    public void initData() {

        //获取订单列表
        GetExchangeRecordLogic.doGetScoreExchangeRecordList(product_id, new OnModelCallback<List<OrderBean>>() {
            @Override
            public void onModelSuccessed(List<OrderBean> listBean) {
                if (!Util.isNull(listBean)) {
                    list.clear();
                    list.addAll(listBean);
                } else {
                    list.clear();
                }
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

    public static void actionStart(Context context, int product_id) {
        Intent intent = new Intent(context, ExchangeRecordListActivity.class);
        intent.putExtra(PRODUCT_ID, product_id);
        context.startActivity(intent);
    }
}
