package com.alpha.lib_stub.uikit.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kenway on 17/8/11 17:32
 * Email : xiaokai090704@126.com
 */

public class RVBaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View mItemView;

    public RVBaseViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        mItemView = itemView;
    }

    /**
     * 获取ItemView
     * @return
     */
    public  View getItemView(){
        return  mItemView;
    }
    public  View getView(int resId){
        return  retrieveView(resId);
    }

    public TextView getTextView(int resId){
        return retrieveView(resId);
    }

    public ImageView getImageView(int resId){
        return retrieveView(resId);
    }



    public Button getButton(int resId){
        return retrieveView(resId);
    }

    /**
     * 获取itemView中的控件
     * @param viewId
     * @param <V>
     * @return
     */
    private <V extends View>  V retrieveView(int viewId) {
        View view=views.get(viewId);
        if (view==null){
            view=mItemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (V)view;
    }

    public void setText(int resId,CharSequence text){
        getTextView(resId).setText(text);
    }

    public void setText(int resId,int strId){
        getTextView(resId).setText(strId);
    }


}
