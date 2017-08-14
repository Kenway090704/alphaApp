package com.alpha.lib_stub.uikit.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.alpha.lib_stub.R;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;
import com.alpha.lib_stub.uikit.adapter.base.RVSimpleAdapter;

/**
 * Created by kenway on 17/8/11 18:19
 * Email : xiaokai090704@126.com
 */

public class EmptyCell extends RVAbsStateCell {
    public EmptyCell(Object o) {
        super(o);
    }


    @Override
    public int getItemType() {
        return RVSimpleAdapter.EMPTY_TYPE;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_empty_layout,null);
    }

}
