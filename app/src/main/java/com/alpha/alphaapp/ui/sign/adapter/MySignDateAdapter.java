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
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.geticons.GetIconBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created by kenway on 17/6/30 12:10
 * Email : xiaokai090704@126.com
 * 查看我的签到的adapter
 */

public class MySignDateAdapter extends RecyclerView.Adapter<MySignDateAdapter.MyViewHolder> {
    private Context context;
    private Map<Integer, Integer> datas;

    public MySignDateAdapter(Context context, Map<Integer, Integer> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.widget_my_sign_item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
              holder.tv_date.setText(position + 1+" ");
             if (datas.get(position+1)==1){
                 holder.tv_status.setText("√");
             }else {
                 holder.tv_status.setText("×");
             }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_status;


        MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.my_sign_item_tv_date);
            tv_status = (TextView) view.findViewById(R.id.my_sign_item_tv_status);


        }

        /**
         * 设置数据
         *
         * @param date
         * @param state
         */
        void setData(String date, int state) {

        }
    }
}
