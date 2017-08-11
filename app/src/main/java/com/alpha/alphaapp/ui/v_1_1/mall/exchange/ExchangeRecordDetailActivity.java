package com.alpha.alphaapp.ui.v_1_1.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.bean.OrderBean;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/24 14:26
 * Email : xiaokai090704@126.com
 * 兑换记录详情页(订单详情页)
 */

public class ExchangeRecordDetailActivity extends BaseActivity {

    private static final String TAG = "ExchangeRecordDetailActivity";
    private static final String ORDERBEAN = "ORDERBEAN";
    private TextView tv_info;
    private OrderBean bean;

    @Override
    protected int getLayoutId() {
        bean = (OrderBean) getIntent().getSerializableExtra(ORDERBEAN);
        return R.layout.activity_mall_exchange_record_detail;
    }

    @Override
    protected void initView() {
        tv_info = (TextView) findViewById(R.id.exchange_record_detail_tv_info);
    }

    @Override
    public void initData() {
        //获取订单详情(现在时没有产品ID)
        if (Util.isNull(bean)) {
            tv_info.setText(R.string.empty_order_data_hint);

        } else {
            tv_info.setText(bean.toString());
        }
    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context, OrderBean orderBean) {
        Intent intent = new Intent(context, ExchangeRecordDetailActivity.class);
        intent.putExtra(ORDERBEAN, orderBean);
        context.startActivity(intent);
    }
}
