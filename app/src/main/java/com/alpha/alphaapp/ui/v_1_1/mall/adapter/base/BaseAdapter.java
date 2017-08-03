package com.alpha.alphaapp.ui.v_1_1.mall.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/7/27 17:58
 * Email : xiaokai090704@126.com
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = "BaseAdapter";
    private static final int VIEW_NO_DATA = -1;
    private Context context;
    private LayoutInflater inflater;
    private List<T> beanList;

    public BaseAdapter(Context context, List<T> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        inflater = LayoutInflater.from(context);
        //当没有数据的时候显示空数据提示
        if (viewType == VIEW_NO_DATA) {
            view = inflater.inflate(R.layout.adapter_empty, parent, false);
            return new EmptyViewHolder(context, view);
        }
        //有数据时,绑定数据
        view = inflater.inflate(getLayoutID(), parent, false);
        return ViewHolder.createViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder instanceof EmptyViewHolder) {
            onBindEmptyViewHolder((EmptyViewHolder) holder);
        } else {
            setItemViewData(holder, beanList.get(position));

        }

    }

    /**
     * 当list 为空或者没有数据的时候绑定提示布局
     *
     * @param holder
     */
    public abstract void onBindEmptyViewHolder(EmptyViewHolder holder);

    /**
     * item布局文件
     *
     * @return
     */
    public abstract int getLayoutID();

    /**
     * 给每一个ItemView设置数据
     *
     * @param t
     */
    public abstract void setItemViewData(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        //这里是主要是为了显示没有数据时展示的界面
        return (null != beanList && beanList.size() > 0) ? beanList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (null == beanList || beanList.size() <= 0) {
            return VIEW_NO_DATA;
        }
        return super.getItemViewType(position);
    }


}
