package com.alpha.alphaapp.ui.widget.mall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/19 15:55
 * Email : xiaokai090704@126.com
 * <p>
 * 确认订单中的收获地址控件
 */

public class OrderAdrrItem extends LinearLayout {
    private static final  String TAG="OrderAdrrItem";
    private Context context;
    private LinearLayout layout;
    private TextView tv_name;
    private TextView tv_mobile;
    private TextView tv_addr;
    //当没有收货地址的时候
    private TextView tv_hint;

    public OrderAdrrItem(Context context) {
        super(context);
        this.context = context;
        initViews();
    }


    public OrderAdrrItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public OrderAdrrItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_order_shiping_addr_item, this);
        layout = (LinearLayout) view.findViewById(R.id.order_shiping_addr_item_layout);
        tv_name = (TextView) view.findViewById(R.id.order_shiping_addr_item_tv_name);
        tv_mobile = (TextView) view.findViewById(R.id.order_shiping_addr_item_tv_mobile);
        tv_addr = (TextView) view.findViewById(R.id.order_shiping_addr_item_tv_addr);
        tv_hint = (TextView) view.findViewById(R.id.order_shiping_addr_item_tv_hint);

    }

    public void setDataforView(final ShippingAddrBean bean) {

        if (Util.isNull(bean)) {
            layout.setVisibility(GONE);
            tv_hint.setVisibility(VISIBLE);
        } else {
            tv_name.setText(bean.getName());
            tv_mobile.setText(bean.getMobile());
            tv_addr.setText(bean.getAddrAll());
            tv_hint.setVisibility(GONE);
            layout.setVisibility(VISIBLE);
        }


    }
}
