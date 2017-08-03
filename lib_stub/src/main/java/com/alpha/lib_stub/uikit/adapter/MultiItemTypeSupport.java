package com.alpha.lib_stub.uikit.adapter;

public interface MultiItemTypeSupport<T>
{
    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);
}