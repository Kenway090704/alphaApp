package com.alpha.alphaapp.ui.v_1_2.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_2.bean.SectionSimpleBean;
import com.alpha.alphaapp.ui.v_1_2.ui.section.SectionActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;

import java.util.List;

/**
 * Created by kenway on 17/10/12 10:46
 * Email : xiaokai090704@126.com
 * 当没有关注版块的时候推荐版块adapter
 */

public class RecomSectionAdapter extends RecyclerView.Adapter<RecomSectionAdapter.MyViewHolder> {
    private Context context;
    private List<SectionSimpleBean> list;

    public RecomSectionAdapter(Context context, List<SectionSimpleBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.wid_recom_section_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final SectionSimpleBean.ChildBean childBean = list.get(position).getChild().get(0);


        ImageLoader.load(context, childBean.getIcon(), holder.iv_logo);

        final String name = childBean.getForumname();
        holder.tv_name.setText(childBean.getForumname());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SectionActivity.actionStart(context, name, childBean.getFid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_logo;
        private TextView tv_name;
        private View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            iv_logo = (ImageView) itemView.findViewById(R.id.wid_recom_section_item_iv_logo);
            tv_name = (TextView) itemView.findViewById(R.id.wid_recom_section_item_tv_name);

        }
    }
}
