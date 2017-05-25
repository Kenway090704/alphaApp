package com.alpha.lib_sdk.app.network;

/**
 * Created by kenway on 17/5/22 11:59
 * Email : xiaokai090704@126.com
 * 网络请求队列
 */

public interface RequestQueue {
    /**
     * add the request to the queue
     *
     * @param request
     */
    void addRequest(IRequest request);

    /**
     * mark the request as canceled
     *
     * @param request
     */
    void cancelRequest(IRequest request);

    /**
     * mark all request be cancelled;
     */
    void cancelAllRequest();

    /**
     * is there an auth request in the request queue or not
     */
    boolean hasAuthRequestInQueue();

}
