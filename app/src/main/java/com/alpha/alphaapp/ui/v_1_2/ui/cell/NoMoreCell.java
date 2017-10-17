package com.alpha.alphaapp.ui.v_1_2.ui.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alpha.alphaapp.R;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/10/16 16:48
 * Email : xiaokai090704@126.com
 */

public class NoMoreCell extends RVBaseCell<String> {
    public NoMoreCell(String s) {
        super(s);
    }

    @Override
    public int getItemType() {
        return Entry.NOMORECELL;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_no_more,null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
