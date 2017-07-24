package com.alpha.alphaapp.ui.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.GetExchangeRecordLogic;
import com.alpha.alphaapp.model.mall.bean.OrderBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/17 14:08
 * Email : xiaokai090704@126.com
 * 变动记录
 */

public class ExchangeRecordActivity extends BaseActivity {
    private static final String TAG = "ExchangeRecordActivity";
    private TextView tv_info;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mall_exchange_record;
    }

    @Override
    protected void initView() {
        tv_info = (TextView) findViewById(R.id.exchange_record_tv_info);
    }

    @Override
    public void initData() {
        //获取订单列表
        GetExchangeRecordLogic.doGetScoreExchangeRecordList(TypeConstants.PRODUCT_ID.NONE_PRODUCT, new OnModelCallback<List<OrderBean>>() {
            @Override
            public void onModelSuccessed(List<OrderBean> orderBeen) {
                if (orderBeen.size() == 0) {
                    tv_info.setText("获取订单列表Ok(兑换记录),当前没有数据!");
                } else {
                    tv_info.setText(orderBeen.toString());
                }
            }

            @Override
            public void onModelFailed(String failMsg) {
                tv_info.setText(failMsg);
            }
        });
        //获取订单详情
        GetExchangeRecordLogic.doGetScoreExchangeRecord("sdsjfksfkjdsfjskdf", TypeConstants.PRODUCT_ID.NONE_PRODUCT, new OnModelCallback<OrderBean>() {
            @Override
            public void onModelSuccessed(OrderBean orderBean) {
                if (Util.isNull(orderBean)) {
                    Log.e(TAG, "订单详情(兑换详情)数据获取ok,但是当前没有订单");
                } else {
                    Log.e(TAG, "orderBean==" + orderBean.toString());
                }

            }

            @Override
            public void onModelFailed(String failMsg) {
                Log.e(TAG, "failMsg==" + failMsg);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ExchangeRecordActivity.class);
        context.startActivity(intent);
    }
}
