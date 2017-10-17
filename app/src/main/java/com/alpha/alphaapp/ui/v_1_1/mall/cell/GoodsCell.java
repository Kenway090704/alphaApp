package com.alpha.alphaapp.ui.v_1_1.mall.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.bean.GoodsBean;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

import java.util.List;

/**
 * Created by kenway on 17/9/14 11:31
 * Email : xiaokai090704@126.com
 *
 * 商品cell
 */

public class GoodsCell   extends RVBaseCell<GoodsBean> {


    public GoodsCell(GoodsBean goodsBean) {
        super(goodsBean);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_goods, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
      ImageView iv_goods= holder.getImageView(R.id.widget_goods_iv_goods);
        Context  context=iv_goods.getContext();
       ImageLoader.load(context, URLConstans.GOODS_PICTURE_URL.GOODS_ICON + mData.getPictures().get(0), iv_goods);

      ImageView iv_isRecom= holder.getImageView(R.id.widget_goods_iv_isRecom);

        if (mData.getIs_recommend()==0){
            iv_isRecom.setVisibility(View.GONE);
        }else {
            iv_isRecom.setVisibility(View.VISIBLE);
        }
      holder.setText(R.id.widget_goods_tv_name,mData.getGoods_name());
      holder.setText(R.id.widget_goods_tv_score, mData.getScore()+"");
      holder.setText(R.id.widget_goods_tv_exchange_count,ResourceUtil.resToStr(context,R.string.has_exhange_)+mData.getExchange_count()+ResourceUtil.resToStr(context,R.string.part));
      holder.setText(R.id.widget_goods_tv_remain_count,ResourceUtil.resToStr(context,R.string.remain_)+mData.getRemain_count()+ResourceUtil.resToStr(context,R.string.part));



    }
}
