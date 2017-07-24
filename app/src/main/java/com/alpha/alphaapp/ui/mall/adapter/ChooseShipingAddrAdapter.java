package com.alpha.alphaapp.ui.mall.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.mall.bean.ShippingAddrBean;
import com.alpha.alphaapp.ui.mall.addr.ChooseShippingAddrActivity;
import com.alpha.lib_sdk.app.core.event.RxEventBus;
import com.alpha.lib_sdk.app.core.event.acc.ChoooseShippingAddrUpdateEvent;
import com.alpha.lib_sdk.app.log.Log;

import java.util.List;

/**
 * Created by kenway on 17/7/18 14:57
 * Email : xiaokai090704@126.com
 * 兑换商品选择地址
 */

public class ChooseShipingAddrAdapter extends RecyclerView.Adapter<ChooseShipingAddrAdapter.ViewHolder> {
    private static final String TAG = "ChooseShipingAddrAdapter";
    private Context context;
    private List<ShippingAddrBean> alist;

    private Dialog dialog;

    public ChooseShipingAddrAdapter(Context context, List<ShippingAddrBean> list) {
        this.context = context;
        this.alist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.widget_order_shiping_addr_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(alist.get(position));
    }

    @Override
    public int getItemCount() {
        return alist == null ? 0 : alist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_mobile, tv_addr;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.order_shiping_addr_item_tv_name);
            tv_mobile = (TextView) itemView.findViewById(R.id.order_shiping_addr_item_tv_mobile);
            tv_addr = (TextView) itemView.findViewById(R.id.order_shiping_addr_item_tv_addr);

        }

        void setData(final ShippingAddrBean bean) {
            tv_name.setText(bean.getName());
            tv_mobile.setText(bean.getMobile());
            tv_addr.setText(bean.getAddrAll());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //通知相关的注册页面进行用户信息更新
                    RxEventBus.getBus().publish(new ChoooseShippingAddrUpdateEvent<>(bean));
                    ((ChooseShippingAddrActivity) context).finish();
                }
            });
        }
    }


}
