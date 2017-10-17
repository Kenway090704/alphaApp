package com.alpha.alphaapp.ui.v_1_2.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.EmotionBean;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/10/9 15:02
 * Email : xiaokai090704@126.com
 */

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.MyViewHolder> {

    private Context context;
    private List<EmotionBean> list;

    public EmotionAdapter(Context context, List<EmotionBean> list, OnItemClickCallback callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    private OnItemClickCallback callback;

    /**
     * 刷新数据
     *
     * @param list
     */
    public void refreshData(List<EmotionBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.adapter_emotion_item, parent,
                false));

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ImageLoader.loadGif(context, list.get(position).getUrl(), holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Util.isNull(callback))
                    callback.onItemCallback(list.get(position).getSign(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.adapter_emotion_item_iv);
        }
    }

    /**
     * 点击某个item时的回调接口
     */
    public interface OnItemClickCallback {
        void onItemCallback(String emotion, int position);
    }
}
