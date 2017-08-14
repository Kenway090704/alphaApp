package com.alpha.lib_stub.uikit.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SimpleAdapter;

import com.alpha.lib_stub.R;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;
import com.alpha.lib_stub.uikit.adapter.base.RVSimpleAdapter;

/**
 * Created by kenway on 17/8/11 18:38
 * Email : xiaokai090704@126.com
 */

public class ErrorCell extends  RVAbsStateCell {
    public ErrorCell(Object o) {
        super(o);
    }

    @Override
    protected View getDefaultView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.rv_error_layout,null);
    }

    @Override
    public int getItemType() {
        return RVSimpleAdapter.ERROR_TYPE;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
