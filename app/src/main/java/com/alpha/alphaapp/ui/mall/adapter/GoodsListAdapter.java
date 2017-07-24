package com.alpha.alphaapp.ui.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.mall.bean.GoodsBean;
import com.alpha.alphaapp.ui.mall.exchange.GoodsExchangeActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;

import java.util.List;

/**
 * Created by kenway on 17/7/17 17:21
 * Email : xiaokai090704@126.com
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {
    private static final String TAG = "StaggerRecylcerAdapter";
    private Context context;
    private List<GoodsBean> list;

    public GoodsListAdapter(Context context, List<GoodsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GoodsListAdapter.ViewHolder holder = new GoodsListAdapter.ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.widget_mall_goods_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private ImageView iv_logo;
        private TextView tv_need_score_count, tv_has_exchange_count, tv_remain_count;
        private Button btn_exchange;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.score_exchange_item_tv_title);
            iv_logo = (ImageView) itemView.findViewById(R.id.score_exchange_item_iv_logo);
            tv_need_score_count = (TextView) itemView.findViewById(R.id.score_exchange_item_tv_need_score_count);
            tv_has_exchange_count = (TextView) itemView.findViewById(R.id.score_exchange_item_tv_has_exchange_count);
            tv_remain_count = (TextView) itemView.findViewById(R.id.score_exchange_item_tv_remain_count);
            btn_exchange = (Button) itemView.findViewById(R.id.score_exchange_item_btn_exchange);
        }

        void setData(final GoodsBean bean) {

            tv_title.setText(bean.getGoods_name());
            ImageLoader.load(context, "http://120.76.27.29:8085/" + bean.getPictures().get(0), iv_logo);
            tv_need_score_count.setText("兑换积分 : " + bean.getScore() + "分");
            tv_has_exchange_count.setText("已兑换 : " + bean.getExchange_count() + "件");
            tv_remain_count.setText("库存 : " + bean.getRemain_count() + "件");
            btn_exchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进行兑换
                    GoodsExchangeActivity.actionStart(context, bean);
                }
            });

        }
    }
}
