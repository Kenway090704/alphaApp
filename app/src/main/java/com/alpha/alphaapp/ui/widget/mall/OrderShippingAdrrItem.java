package com.alpha.alphaapp.ui.widget.mall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.mall.bean.ShippingAddrBean;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/7/19 15:55
 * Email : xiaokai090704@126.com
 * <p>
 * 确认订单中的收获地址控件
 */

public class OrderShippingAdrrItem extends LinearLayout {
    private Context context;
    private TextView tv_name;
    private TextView tv_mobile;
    private TextView tv_addr;

    public OrderShippingAdrrItem(Context context) {
        super(context);
        this.context = context;
        initViews();
    }


    public OrderShippingAdrrItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public OrderShippingAdrrItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {

        View view = LayoutInflater.from(context).inflate(R.layout.widget_order_shiping_addr_item, this);
        tv_name = (TextView) view.findViewById(R.id.order_shiping_addr_item_tv_name);
        tv_mobile = (TextView) view.findViewById(R.id.order_shiping_addr_item_tv_mobile);
        tv_addr = (TextView) view.findViewById(R.id.order_shiping_addr_item_tv_addr);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong(context, "进入到选择页面");
            }
        });


    }

    public void setDataforView(final ShippingAddrBean bean) {
        tv_name.setText(bean.getName());
        tv_mobile.setText(bean.getMobile());
        tv_addr.setText(bean.getAddrAll());

    }
}
