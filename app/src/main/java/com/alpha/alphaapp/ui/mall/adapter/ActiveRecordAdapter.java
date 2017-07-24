package com.alpha.alphaapp.ui.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.mall.bean.CdkDatasBean;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/21 12:03
 * Email : xiaokai090704@126.com
 * 激活记录
 */

public class ActiveRecordAdapter extends RecyclerView.Adapter<ActiveRecordAdapter.ViewHolder> {

    private static final String TAG = "ActiveRecordAdapter";
    private Context context;
    private List<CdkDatasBean.CdkItemsBean> beanList;

    public ActiveRecordAdapter(Context context, List<CdkDatasBean.CdkItemsBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.adapter_active_record_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(beanList.get(position));
    }

    @Override
    public int getItemCount() {
        return null == beanList ? 0 : beanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_active_code;
        private TextView tv_code_type;
        private TextView tv_product;
        private TextView tv_active_time;
        private TextView tv_active_channel;
        private TextView tv_active_status;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_active_code = (TextView) itemView.findViewById(R.id.adapter_active_record_item_tv_active_code);
            tv_code_type = (TextView) itemView.findViewById(R.id.adapter_active_record_item_tv_code_type);
            tv_product = (TextView) itemView.findViewById(R.id.adapter_active_record_item_tv_product);
            tv_active_time = (TextView) itemView.findViewById(R.id.adapter_active_record_item_tv_active_time);
            tv_active_channel = (TextView) itemView.findViewById(R.id.adapter_active_record_item_tv_active_channel);
            tv_active_status = (TextView) itemView.findViewById(R.id.adapter_active_record_item_tv_active_status);
        }

        public void setData(CdkDatasBean.CdkItemsBean bean) {
            if (!Util.isNull(bean)) {
                tv_active_code.setText(bean.getCode());
                tv_code_type.setText(bean.getLot_number());
                tv_product.setText(bean.getLot_number());
                tv_active_time.setText(bean.getActive_time());
                tv_active_channel.setText(bean.getChannel_id() + "");
                if (bean.getStatus() == 1) {
                    tv_active_status.setText(R.string.has_active);
                } else {
                    tv_active_status.setText(R.string.no_active);

                }

            }
        }
    }
}
