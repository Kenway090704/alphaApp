package com.alpha.lib_stub.uikit.adapter.base;

import android.view.ViewGroup;

/**
 * Created by kenway on 17/8/11 17:22
 * Email : xiaokai090704@126.com
 * 文章地址:http://www.jianshu.com/p/727c18f4bf20
 */

public interface Cell {

    /**
     * 回收资源
     */

    public  void releaseResourse();

    /**
     * 获取ViewType
     *
     * @return
     */
    public int getItemType();

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 数据绑定
     *
     * @param holder
     * @param position
     */
    public void onBindViewHolder(RVBaseViewHolder holder, int position);

}
