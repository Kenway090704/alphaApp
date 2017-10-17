package com.alpha.alphaapp.ui.v_1_2.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kenway on 17/10/10 13:49
 * Email : xiaokai090704@126.com
 */

public class AlbumPhotoAdapter extends RecyclerView.Adapter<AlbumPhotoAdapter.MyViewHolder> {


    private Context context;
    private List<String> list;

    private Map<String, Boolean> map;

    public AlbumPhotoAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }

    public void setMapChoose(Map<String, Boolean> map) {
        this.map = map;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder holder = new MyViewHolder((LayoutInflater.from(context).inflate(R.layout.widget_album_photo_item, parent, false)));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ImageLoader.load(context, list.get(position), holder.iv);

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String path = list.get(position);
                if (isChecked) {
                    map.put(path, true);
                } else {
                    map.put(path, false);
                }
            }
        });
    }

    public List<String> getChoosePhotoUris() {

        List<String> list_choose = new ArrayList<>();
        for (String path : map.keySet()) {
            //判断map中该图片是否选中
            Boolean isSelect = map.get(path);
            if (isSelect) {
                list_choose.add(path);
            }
        }

        return list_choose;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cb;
        private ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            cb = (CheckBox) itemView.findViewById(R.id.widget_album_photo_item_cb);
            iv = (ImageView) itemView.findViewById(R.id.widget_album_photo_item_iv);
        }
    }
}
