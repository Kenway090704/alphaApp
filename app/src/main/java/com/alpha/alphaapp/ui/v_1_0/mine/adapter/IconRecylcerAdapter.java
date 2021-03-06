package com.alpha.alphaapp.ui.v_1_0.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.model.v_1_0.bean.GetIconBean;
import com.alpha.lib_sdk.app.glide.ImageLoader;


import java.util.List;
import java.util.Map;

/**
 * Created by kenway on 17/6/15 15:36
 * Email : xiaokai090704@126.com
 */

public class IconRecylcerAdapter extends RecyclerView.Adapter<IconRecylcerAdapter.MyViewHolder> {
    private static final String TAG = "StaggerRecylcerAdapter";
    private Context context;
    private List<String> list;
    private Map<String, Boolean> map;

    public IconRecylcerAdapter(Context context, GetIconBean.IconListBean.CategoryBean bean, Map<String, Boolean> map) {
        this.context = context;
        this.list = bean.getIcons();
        this.map = map;


    }

    @Override
    public IconRecylcerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.adapter_icon_item_recycler_iv, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //判断是否为选中如果是选中,则,显示checkbox
        for (String icon : map.keySet()) {
            //map.keySet()返回的是所有key的值
            if (list.get(position).equals(icon)) {
                //map.keySet()返回的是所有key的值
                Boolean isSelect = map.get(icon);//得到每个key对应value的值
                if (isSelect) {
                    holder.cb.setChecked(true);
                    holder.cb.setVisibility(View.VISIBLE);
                } else {
                    holder.cb.setChecked(false);
                    holder.cb.setVisibility(View.GONE);
                }
            }
        }
//        //使用Glide展示图片
        ImageLoader.load(context, URLConstans.getICONUrl(context) + list.get(position), holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果当前为选中的,那么让其不选中,如果是未选中那么让其变为选中
                if (holder.cb.isChecked()) {
                    holder.cb.setVisibility(View.GONE);
                    holder.cb.setChecked(false);
                    //便利map,将所有数据变为false
                    for (String icon : map.keySet()) {
                        map.put(icon, false);
                    }
                } else {
                    holder.cb.setChecked(true);
                    holder.cb.setVisibility(View.VISIBLE);
                    //如果是选中,那么就将该key对应的值改为true
                    for (String icon : map.keySet()) {
                        //map.keySet()返回的是所有key的值
                        if (list.get(position).equals(icon)) {
                            map.put(list.get(position), true);
                        } else {
                            map.put(icon, false);
                        }
                    }
                }
                //获取每一个Adapter,然后通知这些刷新界面
                List<RecyclerView> recyclerViews = IconListVPAdapter.recyclerViews;
                for (int k = 0; k < recyclerViews.size(); k++) {
                    recyclerViews.get(k).getAdapter().notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        CheckBox cb;

        private MyViewHolder(View view) {
            super(view);
            cb = (CheckBox) view.findViewById(R.id.item_recycler_cb);
            iv = (ImageView) view.findViewById(R.id.item_recycler_iv);
        }
    }
}
