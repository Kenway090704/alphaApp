package com.alpha.alphaapp.ui.v_1_1.mall.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.alphaapp.ui.v_1_1.mall.addr.ChooseShippingAddrActivity;
import com.alpha.lib_sdk.app.core.event.RxEventBus;
import com.alpha.lib_sdk.app.core.event.acc.ChoooseShippingAddrUpdateEvent;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/8/14 17:32
 * Email : xiaokai090704@126.com
 */

public class ShippingAddrCell extends RVBaseCell<ShippingAddrBean> {
    public ShippingAddrCell(ShippingAddrBean shippingAddrBean) {
        super(shippingAddrBean);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_shiping_addr_item, null));

    }

    @Override
    public void onBindViewHolder(final RVBaseViewHolder holder, int position) {
        holder.setText(R.id.order_shiping_addr_item_tv_name,mData.getName());
        holder.setText(R.id.order_shiping_addr_item_tv_mobile,mData.getMobile());
        holder.setText(R.id.order_shiping_addr_item_tv_addr,mData.getAddrAll());

        holder.getmItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通知相关的注册页面进行用户信息更新
                RxEventBus.getBus().publish(new ChoooseShippingAddrUpdateEvent<>(mData));
                ((ChooseShippingAddrActivity) holder.getmItemView().getContext()).finish();
            }
        });
    }
}
