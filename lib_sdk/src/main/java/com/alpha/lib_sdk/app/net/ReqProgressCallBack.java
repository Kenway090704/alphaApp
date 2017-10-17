package com.alpha.lib_sdk.app.net;

/**
 * Created by kenway on 17/10/10 17:36
 * Email : xiaokai090704@126.com
 */

public interface ReqProgressCallBack<T> extends ReqCallBack<T> {

    /**
     * 响应进度更新
     *
     * @param total
     * @param current
     */
    void onProgress(long total, long current);
}
