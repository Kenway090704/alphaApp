package com.alpha.lib_stub.uikit.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.alpha.lib_stub.R;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;
import com.alpha.lib_stub.uikit.adapter.base.RVSimpleAdapter;

/**
 * Created by kenway on 17/8/11 18:40
 * Email : xiaokai090704@126.com
 */

public class LoadingCell extends RVAbsStateCell {
    public LoadingCell(Object o) {
        super(o);
    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_loading_layout,null);
    }

    @Override
    public int getItemType() {
        return RVSimpleAdapter.LOADING_TYPE;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }

}
