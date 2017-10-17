package com.alpha.alphaapp.ui.v_1_2.ui.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.BannerBean;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.adapter.CBViewHolderCreator;

import java.util.List;

/**
 * Created by kenway on 17/8/14 14:30
 * Email : xiaokai090704@126.com
 * 广告的cell
 */

public class BannerCell extends RVBaseCell<List<BannerBean>> {

    private ConvenientBanner mConvenientBanner;
    public BannerCell(List<BannerBean> list) {
        super(list);
    }
    @Override
    public int getItemType() {
        return Entry.BANNERCELL;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()
        ).inflate(R.layout.cell_banner, null));


    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        mConvenientBanner = (ConvenientBanner) holder.getView(R.id.cell_banner_cban);
        mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, mData)
                .setPointViewVisible(true)//设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.icon_indictor_normal, R.drawable.icon_indictor_select})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)


        ;//设置指示器的方向（左、中、右）
        if (!mConvenientBanner.isTurning()) {
            mConvenientBanner.startTurning(2000);
        }
    }


    public static class NetworkImageHolderView implements CBPageAdapter.Holder<BannerBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerBean data) {
            ImageLoader.load(context, data.getThumb(), imageView);
        }
    }
}
