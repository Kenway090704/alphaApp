package com.alpha.alphaapp.ui.v_1_1.mall.adapter;

import android.content.Context;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.bean.GoodsBean;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.base.BaseAdapter;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.base.EmptyViewHolder;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.base.ViewHolder;
import com.alpha.alphaapp.ui.v_1_1.mall.exchange.GoodsExchangeActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;

import com.alpha.lib_stub.comm.URLConstans;

import java.util.List;

/**
 * Created by kenway on 17/7/17 17:21
 * Email : xiaokai090704@126.com
 */

public class GoodsListTextAdapter extends BaseAdapter<GoodsBean> {

    private Context context;
    private int product_id;

    public GoodsListTextAdapter(Context context, List<GoodsBean> beanList, int product_id) {
        super(context, beanList);
        this.context = context;
        this.product_id = product_id;
    }


    @Override
    public void onBindEmptyViewHolder(EmptyViewHolder holder) {
        holder.setHintText("我是测试空数据提示语");
    }

    @Override
    public int getLayoutID() {
        return R.layout.adapter_mall_goods_list_item;
    }

    @Override
    public void setItemViewData(ViewHolder holder, final GoodsBean goodsBean) {

        TextView tv_title = holder.getView(R.id.score_exchange_item_tv_title);
        ImageView iv_logo = holder.getView(R.id.score_exchange_item_iv_logo);
        TextView tv_need_score_count = holder.getView(R.id.score_exchange_item_tv_need_score_count);
        TextView tv_has_exchange_count = holder.getView(R.id.score_exchange_item_tv_has_exchange_count);
        TextView tv_remain_count = holder.getView(R.id.score_exchange_item_tv_remain_count);
        Button btn_exchange = holder.getView(R.id.score_exchange_item_btn_exchange);

        tv_title.setText(goodsBean.getGoods_name());
        ImageLoader.load(context, URLConstans.GOODS_PICTURE_URL.GOODS_ICON + goodsBean.getPictures().get(0), iv_logo);
        tv_need_score_count.setText("兑换积分 : " + goodsBean.getScore() + "分");
        tv_has_exchange_count.setText("已兑换 : " + goodsBean.getExchange_count() + "件");
        tv_remain_count.setText("库存 : " + goodsBean.getRemain_count() + "件");
        btn_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行兑换
                GoodsExchangeActivity.actionStart(context, goodsBean, product_id);
            }
        });
    }
}
