package com.alpha.alphaapp.ui.v_1_1.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.bean.CdkDatasBean;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.base.EmptyViewHolder;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/21 12:03
 * Email : xiaokai090704@126.com
 * 激活记录
 */

public class ActiveRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ActiveRecordAdapter";
    private static final int VIEW_NO_DATA = -1;
    private Context context;
    private List<CdkDatasBean.CdkItemsBean> beanList;

    public ActiveRecordAdapter(Context context, List<CdkDatasBean.CdkItemsBean> beanList) {
        this.context = context;
        this.beanList = beanList;
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
        view = inflater.inflate(R.layout.adapter_active_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (null == beanList || beanList.size() <= 0) {
            return VIEW_NO_DATA;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ActiveRecordAdapter.ViewHolder) {
            ((ActiveRecordAdapter.ViewHolder) holder).setData(beanList.get(position));
        }

        if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).setHintText(R.string.empty_active_data_hint);
        }
    }

    @Override
    public int getItemCount() {
        //这里是主要是为了显示没有数据时展示的界面
        return (null != beanList && beanList.size() > 0) ? beanList.size() : 1;
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
                LogUtils.e(TAG, "TestBean===" + bean.toString());
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
