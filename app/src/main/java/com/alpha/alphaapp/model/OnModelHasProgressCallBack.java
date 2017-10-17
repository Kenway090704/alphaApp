package com.alpha.alphaapp.model;

/**
 * Created by kenway on 17/10/10 18:06
 * Email : xiaokai090704@126.com
 * 有进度的接口回调
 */

public interface OnModelHasProgressCallBack<T>  extends OnModelCallback<T> {
    void onModelProgress(long total, long current);
}
