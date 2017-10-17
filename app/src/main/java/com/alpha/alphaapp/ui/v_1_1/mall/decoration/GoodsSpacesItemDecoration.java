package com.alpha.alphaapp.ui.v_1_1.mall.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GoodsSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public GoodsSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 8;
        outRect.right = 8;
        outRect.bottom = 8;
        outRect.top = 8;
        //为了让第一排的两个图片水平对齐
        if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1) {
            outRect.top = space;
        }
    }
}