package com.alpha.lib_stub.uikit.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.alpha.lib_sdk.app.tool.DeviceUtils;
import com.alpha.lib_sdk.app.unitily.DensityUtils;
import com.alpha.lib_stub.R;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;
import com.alpha.lib_stub.uikit.adapter.base.RVSimpleAdapter;

/**
 * Created by kenway on 17/8/11 18:43
 * Email : xiaokai090704@126.com
 */

public class LoadMoreCell extends RVAbsStateCell {
    public static final int mDefaultHeight = 80;//dp
    public LoadMoreCell(Object o) {
        super(o);
    }

    @Override
    protected View getDefaultView(Context context) {
        // 设置LoadMore View显示的默认高度
        setHeight(DensityUtils.dp2px(context,mDefaultHeight));
        return LayoutInflater.from(context).inflate(R.layout.rv_load_more_layout,null);

    }

    @Override
    public int getItemType() {
        return RVSimpleAdapter.LOAD_MORE_TYPE;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

    }
}
