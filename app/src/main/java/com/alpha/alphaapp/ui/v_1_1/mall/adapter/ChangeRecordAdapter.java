package com.alpha.alphaapp.ui.v_1_1.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.bean.ScoreLogBean;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.base.EmptyViewHolder;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/24 10:37
 * Email : xiaokai090704@126.com
 * <p>积分变动记录adapter</p>
 */
public class ChangeRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ExchangeRecordAdapter";
    private static final int VIEW_NO_DATA = -1;
    private Context context;
    private List<ScoreLogBean> list;

    public ChangeRecordAdapter(Context context, List<ScoreLogBean> list) {
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
            return new EmptyViewHolder(context,view);
        }
        //有数据时,绑定数据
        view = inflater.inflate(R.layout.adapter_change_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setData(list.get(position));
        }

        if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).setHintText(R.string.empty_change_data_hint);
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
        return (null != list && list.size() > 0) ? list.size() : 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_channel;
        private TextView tv_type;
        private TextView tv_change_score;
        private TextView tv_change_before;
        private TextView tv_change_behind;
        private TextView tv_change_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_channel = (TextView) itemView.findViewById(R.id.adapter_change_record_item_tv_channel);
            tv_type = (TextView) itemView.findViewById(R.id.adapter_change_record_item_tv_type);
            tv_change_score = (TextView) itemView.findViewById(R.id.adapter_change_record_item_tv_change_score);
            tv_change_before = (TextView) itemView.findViewById(R.id.adapter_change_record_item_tv_change_before);
            tv_change_behind = (TextView) itemView.findViewById(R.id.adapter_change_record_item_tv_change_behind);
            tv_change_time = (TextView) itemView.findViewById(R.id.adapter_change_record_item_tv_change_time);
        }

        public void setData(final ScoreLogBean bean) {
            if (Util.isNull(bean)) return;
            tv_channel.setText(bean.getChannel_id() + "");
            //类型是什么?
            tv_type.setText(bean.getScore_desc());
            tv_change_score.setText(bean.getOp_score() + "");
            tv_change_before.setText(bean.getPre_score() + "");
            tv_change_behind.setText(bean.getCur_score() + "");
            tv_change_time.setText(bean.getOp_time());
        }
    }


}
