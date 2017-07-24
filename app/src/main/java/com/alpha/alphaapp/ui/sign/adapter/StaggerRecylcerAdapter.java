package com.alpha.alphaapp.ui.sign.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.geticons.GetIconBean;
import com.alpha.alphaapp.ui.mine.school.SchoolListAdapter;
import com.alpha.alphaapp.ui.sign.SignActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;
import com.bumptech.glide.Glide;


import java.util.List;
import java.util.Map;

/**
 * Created by kenway on 17/6/15 15:36
 * Email : xiaokai090704@126.com
 * <p>
 * 瀑布流的adapter
 */

public class StaggerRecylcerAdapter extends RecyclerView.Adapter<StaggerRecylcerAdapter.MyViewHolder> {
    private static final String TAG = "StaggerRecylcerAdapter";
    private Context context;
    private List<String> list;
    private Map<String, Boolean> map;
    private Map<String, Integer> map_hetights;
    private String selectedIcon;


    public StaggerRecylcerAdapter(Context context, GetIconBean.IconListBean.CategoryBean bean, Map<String, Boolean> map, Map<String, Integer> map_hetights) {
        this.context = context;
        this.list = bean.getIcons();
        this.map = map;
        this.map_hetights = map_hetights;

    }

    @Override
    public StaggerRecylcerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.sign_item_recycler_iv, parent,
                false));
        return holder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        //判断是否为选中如果是选中,则显示checkbox(这里使用CheckBox进行边框设置)
        for (String icon : map.keySet()) {
            //判断map中与该页面中数据的Url相同的是否为选中
            if (list.get(position).equals(icon)) {
                //map.keySet()返回的是所有key的值
                Boolean isSelect = map.get(icon);
                if (isSelect) {
                    selectedIcon = icon;
                    holder.cb.setChecked(true);
                    holder.cb.setVisibility(View.VISIBLE);
                } else {
                    holder.cb.setChecked(false);
                    holder.cb.setVisibility(View.GONE);
                }
            }
        }
        //设置图片使用的是Glide框架
        for (String icon : map_hetights.keySet()) {
            if (list.get(position).equals(icon)) {
                //使用Glide展示图片
                holder.setData(list.get(position), map_hetights.get(icon));
            }
        }

        //点击图片进行选中与未选中状态切换
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果当前为选中的,那么让其不选中,如果是未选中那么让其变为选中
                for (String icon : map.keySet()) {
                    if (list.get(position).equals(icon)) {
//                            当第一次进入的时候,没有任何一个选中,此时,如果点击图片,就将该图片设置为选中的图片
                        if (Util.isNullOrBlank(selectedIcon)) {
                            selectedIcon = list.get(position);
                            holder.cb.setChecked(true);
                            holder.cb.setVisibility(View.VISIBLE);
                        }
                        map.put(icon, true);
                    } else {
                        map.put(icon, false);
                    }
                }
                refreshChangeItem(position, holder);


            }
        });
    }


    /**
     * 刷新指定的item
     *
     * @param position
     * @param holder
     */
    private void refreshChangeItem(int position, MyViewHolder holder) {
        //获取每一个Adapter,然后通知这些刷新界面
        List<LinearLayout> layouts = LayoutVPAdapter.layouts;
        List<StaggerRecylcerAdapter> adapters = ((SignActivity) context).getListAdapters();
        //这个部分主要是修改当前可见页面的选中与未选中的切换,防止有"闪屏"
        for (int k = 0; k < adapters.size(); k++) {
            if (StaggerRecylcerAdapter.this == adapters.get(k)) {
                RecyclerView recyclerView = (RecyclerView) layouts.get(k).findViewById(R.id.sign_item_recyclerView);
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).equals(selectedIcon)) {
                        //获取上一个选中的Item    //利用 RecyclerView 的 findViewHolderForLayoutPosition()方法，获取某个postion的ViewHolder
                        MyViewHolder viewHolder = (MyViewHolder) recyclerView.findViewHolderForLayoutPosition(j);
                        if (viewHolder != null) {//还在屏幕里
                            //将上一个选中的变为未选中
                            map.put(selectedIcon, false);
                            viewHolder.cb.setChecked(false);
                            viewHolder.cb.setVisibility(View.GONE);
                        } else {
                            //这一句代码导致item乱跳
//                            notifyItemChanged(j);
                        }
                        //将现在选中的变为选中
                        selectedIcon = list.get(position);//更新选中项
                        Log.e(TAG, "new selectedIcon==" + selectedIcon);
                        map.put(list.get(position), true);
                        holder.cb.setChecked(true);
                        holder.cb.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            } else {
                adapters.get(k).notifyDataSetChanged();
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        CheckBox cb;

        MyViewHolder(View view) {
            super(view);
            cb = (CheckBox) view.findViewById(R.id.item_recycler_cb);
            iv = (ImageView) view.findViewById(R.id.item_recycler_iv);
        }

        void setData(String icon, int height) {
            int width = ((Activity) iv.getContext()).getWindowManager().getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams params = iv.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width / 2;
            params.height = height;
            iv.setLayoutParams(params);
            ViewGroup.LayoutParams params_cb = cb.getLayoutParams();
            params_cb.width = width / 2;
            params_cb.height = height + 2;
            cb.setLayoutParams(params_cb);
            ImageLoader.load(context, URLConstans.GET_ICON.ICON100 + icon, iv);
        }
    }
}
