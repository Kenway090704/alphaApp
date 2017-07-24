package com.alpha.alphaapp.ui.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.GetExchangeRecordLogic;
import com.alpha.alphaapp.model.mall.bean.OrderBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.mall.adapter.ExchangeRecordAdapter;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/17 14:08
 * Email : xiaokai090704@126.com
 * 兑换记录(商品订单详情)
 */

public class ExchangeRecordListActivity extends BaseActivity {
    private static final String TAG = "ExchangeRecordListActivity";

    private RecyclerView rv;

    private List<OrderBean> list;
    private ExchangeRecordAdapter adapter;

    @Override
    protected int getLayoutId() {
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
        GetExchangeRecordLogic.doGetScoreExchangeRecordList(TypeConstants.PRODUCT_ID.NONE_PRODUCT, new OnModelCallback<List<OrderBean>>() {
            @Override
            public void onModelSuccessed(List<OrderBean> listBean) {
//                list.clear();
//                list.addAll(listBean);
//                adapter.notifyDataSetChanged();

                for (int i = 0; i < 20; i++) {
                    OrderBean bean = new OrderBean();
                    bean.setOrder_id(System.currentTimeMillis() + 1);
                    bean.setGoods_name("爆裂机甲--" + i);
                    bean.setCount(i);
                    bean.setGoods_id(10002);


                    list.add(bean);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showToast(ExchangeRecordListActivity.this, failMsg);
            }
        });

    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ExchangeRecordListActivity.class);
        context.startActivity(intent);
    }
}
