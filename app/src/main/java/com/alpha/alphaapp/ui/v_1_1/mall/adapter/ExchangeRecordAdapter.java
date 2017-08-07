package com.alpha.alphaapp.ui.v_1_1.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.v_1_1.mall.Tools;
import com.alpha.alphaapp.model.v_1_1.bean.OrderBean;
import com.alpha.alphaapp.ui.v_1_1.mall.exchange.ExchangeRecordDetailActivity;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/24 10:37
 * Email : xiaokai090704@126.com
 * <p>兑换记录(订单列表)adapter</p>
 */
public class ExchangeRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ExchangeRecordAdapter";
    private static final int VIEW_NO_DATA = -1;
    private Context context;
    private List<OrderBean> list;

    public ExchangeRecordAdapter(Context context, List<OrderBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        //当没有数据的时候显示空数据提示
        if (viewType == VIEW_NO_DATA) {
            view = inflater.inflate(R.layout.adapter_empty, parent, false);

            return new EmptyViewHolder(view);
        }
        //有数据时,绑定数据
        view = inflater.inflate(R.layout.adapter_exchange_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setData(list.get(position));
        }

        if (holder instanceof EmptyViewHolder) {

            ((EmptyViewHolder) holder).setHintText(R.string.empty_order_data_hint);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null == list || list.size() <= 0) {

            return VIEW_NO_DATA;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {

        //这里是主要是为了显示没有数据时展示的界面
//        LogUtils.e(TAG, "list.size()==" + list.size());
        return (null != list && list.size() > 0) ? list.size() : 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_order_code;
        private TextView tv_product_name;
        private TextView tv_product_type;
        private TextView tv_exchange_count;
        private TextView tv_expense_score;
        private TextView tv_status;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_order_code = (TextView) itemView.findViewById(R.id.adapter_exchange_record_item_tv_order_code);
            tv_product_name = (TextView) itemView.findViewById(R.id.adapter_exchange_record_item_tv_prouduct_name);
            tv_product_type = (TextView) itemView.findViewById(R.id.adapter_exchange_record_item_tv_product_type);
            tv_exchange_count = (TextView) itemView.findViewById(R.id.adapter_exchange_record_item_tv_exchange_count);
            tv_expense_score = (TextView) itemView.findViewById(R.id.adapter_exchange_record_item_tv_expensse_score);
            tv_status = (TextView) itemView.findViewById(R.id.adapter_exchange_record_item_tv_status);
        }

        public void setData(final OrderBean bean) {
            if (Util.isNull(bean)) return;
            tv_order_code.setText(bean.getOrder_id() + "");
            tv_product_name.setText(bean.getGoods_name());
            //兑换数量
            tv_exchange_count.setText(bean.getCount() + "");
            //发货情况
            Tools.setTextShippingStatus(tv_status, bean.getStatus());
            tv_expense_score.setText(bean.getScore() + " 积分");
            Tools.setTextGoodType(tv_product_type, bean.getGoods_type());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入到订单详情页面
                    ExchangeRecordDetailActivity.actionStart(context, bean);
                }
            });

        }
    }

    /**
     * 当没有数据时,显示其他无数据的提示
     */
    class EmptyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_hint;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            tv_hint = (TextView) itemView.findViewById(R.id.adapter_empty_tv_hint);
        }

        public void setHintText(String txt) {
            tv_hint.setText(txt);
        }

        public void setHintText(int strID) {
            tv_hint.setText(strID);
        }
    }
}
