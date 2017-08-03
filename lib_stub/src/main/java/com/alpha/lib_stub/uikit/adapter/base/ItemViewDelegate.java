package com.alpha.lib_stub.uikit.adapter.base;

import com.alpha.lib_stub.uikit.adapter.base.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}