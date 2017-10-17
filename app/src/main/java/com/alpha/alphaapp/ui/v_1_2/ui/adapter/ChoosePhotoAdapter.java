package com.alpha.alphaapp.ui.v_1_2.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelHasProgressCallBack;
import com.alpha.alphaapp.model.v_1_2.bean.UploadPicBean;
import com.alpha.alphaapp.model.v_1_2.logic.topic.TopicLogic;
import com.alpha.lib_sdk.app.glide.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kenway on 17/10/10 15:55
 * Email : xiaokai090704@126.com
 */

public class ChoosePhotoAdapter extends RecyclerView.Adapter<ChoosePhotoAdapter.MyViewHolder> {

    private Context context;
    private List<String> list_choosePic = new ArrayList<>();

    private List<UploadPicBean> beanList = new ArrayList<>();
    private HashMap<String, Boolean> map_isUpload = new HashMap<>();


    public ChoosePhotoAdapter(Context context, List<String> list_choosePic) {
        this.context = context;
        this.list_choosePic = list_choosePic;
    }

    public void refreshUI(List<String> list_pis) {
        //b
        for (int i = 0; i < list_pis.size(); i++) {
            boolean isHas = false;
            for (int j = 0; j < list_choosePic.size(); j++) {
                if (list_pis.get(i).equals(list_choosePic.get(j))) {
                    isHas = true;
                }
            }
            if (isHas) {
                map_isUpload.put(list_pis.get(i), true);

            } else {
                map_isUpload.put(list_pis.get(i), false);
                list_choosePic.add(list_pis.get(i));
            }
        }

        notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.widget_choose_photo_item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageLoader.load(context, list_choosePic.get(position), holder.iv);

        if (!map_isUpload.get(list_choosePic.get(position))) {
            OnModelHasProgressCallBack<UploadPicBean> callBack = new OnModelHasProgressCallBack<UploadPicBean>() {
                @Override
                public void onModelProgress(long total, long current) {
                    if (total > current) {
                        holder.tv_progress.setText(current / total * 100 + "%");
                    } else {
                        holder.tv_progress.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onModelSuccessed(UploadPicBean uploadPicBean) {
                    beanList.add(uploadPicBean);
                    map_isUpload.put(list_choosePic.get(position), true);
                }

                @Override
                public void onModelFailed(String failedMsg) {

                }

            };
            //显示图片
            TopicLogic.sendNewTopicUploadPicHasProgress(2 + "", list_choosePic.get(position), callBack);
        }


    }

    @Override
    public int getItemCount() {
        return list_choosePic.size();
    }


    public List<UploadPicBean> getUploadBeans() {
        return beanList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv_progress;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.wid_choose_photo_item_iv);
            tv_progress = (TextView) itemView.findViewById(R.id.wid_choose_photo_item_tv_progress);
        }
    }

}
