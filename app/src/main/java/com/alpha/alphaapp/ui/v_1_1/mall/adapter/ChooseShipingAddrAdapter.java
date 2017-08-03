package com.alpha.alphaapp.ui.v_1_1.mall.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.base.EmptyViewHolder;
import com.alpha.alphaapp.ui.v_1_1.mall.addr.ChooseShippingAddrActivity;
import com.alpha.lib_sdk.app.core.event.RxEventBus;
import com.alpha.lib_sdk.app.core.event.acc.ChoooseShippingAddrUpdateEvent;

import java.util.List;

/**
 * Created by kenway on 17/7/18 14:57
 * Email : xiaokai090704@126.com
 * 兑换商品选择地址
 */

public class ChooseShipingAddrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ChooseShipingAddrAdapter";

    private static final int VIEW_NO_DATA = -1;
    private Context context;
    private List<ShippingAddrBean> alist;

    private Dialog dialog;

    public ChooseShipingAddrAdapter(Context context, List<ShippingAddrBean> list) {
        this.context = context;
        this.alist = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {




        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        //当没有数据的时候显示空数据提示
        if (viewType == VIEW_NO_DATA) {
            view = inflater.inflate(R.layout.adapter_empty, parent, false);
            return new EmptyViewHolder(context,view);
        }
        //有数据时,绑定数据
        view = inflater.inflate(R.layout.adapter_order_shiping_addr_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setData(alist.get(position));
        }

        if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).setHintText(R.string.empty_change_data_hint);
        }
    }



    @Override
    public int getItemViewType(int position) {
        if (null == alist || alist.size() <= 0) {
            return VIEW_NO_DATA;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {

        //这里是主要是为了显示没有数据时展示的界面
        return (null != alist && alist.size() > 0) ? alist.size() : 1;
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
