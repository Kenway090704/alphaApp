package com.alpha.alphaapp.ui.v_1_1.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.bean.GoodsBean;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.base.EmptyViewHolder;
import com.alpha.alphaapp.ui.v_1_1.mall.exchange.GoodsExchangeActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_stub.comm.URLConstans;

import java.util.List;

/**
 * Created by kenway on 17/7/17 17:21
 * Email : xiaokai090704@126.com
 */

public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "StaggerRecylcerAdapter";
    private static final int VIEW_NO_DATA = -1;
    private Context context;
    private int product_id;
    private List<GoodsBean> list;

    public GoodsListAdapter(Context context, List<GoodsBean> list,int product_id) {
        this.context = context;
        this.list = list;
        this.product_id = product_id;
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
        view = inflater.inflate(R.layout.adapter_mall_goods_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setData(list.get(position));
        }

        if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).setHintText(R.string.no_have_goods_list);
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
            LogUtils.e(TAG,"picture==="+URLConstans.GOODS_PICTURE_URL.GOODS_ICON + bean.getPictures().get(0));
            ImageLoader.load(context, URLConstans.GOODS_PICTURE_URL.GOODS_ICON + bean.getPictures().get(0), iv_logo);
            tv_need_score_count.setText("兑换积分 : " + bean.getScore() + "分");
            tv_has_exchange_count.setText("已兑换 : " + bean.getExchange_count() + "件");
            tv_remain_count.setText("库存 : " + bean.getRemain_count() + "件");
            btn_exchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进行兑换
                    GoodsExchangeActivity.actionStart(context, bean,product_id);
                }
            });

        }
    }
}
