package com.alpha.alphaapp.ui.widget.addtheme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.ThemeBean;
import com.alpha.lib_sdk.app.tool.Util;

import java.util.List;

/**
 * Created by kenway on 17/10/13 11:28
 * Email : xiaokai090704@126.com
 */

public class ChooseThemesAdapter extends RecyclerView.Adapter<ChooseThemesAdapter.MyViewHolder> {

    private Context context;
    private List<ThemeBean> list;

    public ChooseThemesAdapter(Context context, List<ThemeBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_choose_theme, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(list.get(position).getTag_name());

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNull(listenr))
                    listenr.onClickItem(list.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.adapter_choose_themes_tv);
        }
    }

    private OnClickItemListener listenr;

    public void addOnClickItemListener(OnClickItemListener listenr) {
        this.listenr = listenr;
    }

    interface OnClickItemListener {
        void onClickItem(ThemeBean bean, int postion);
    }
}
