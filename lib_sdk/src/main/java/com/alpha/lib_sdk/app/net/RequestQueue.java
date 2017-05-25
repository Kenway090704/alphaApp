package com.alpha.lib_sdk.app.net;

import okhttp3.Request;

/**
 * Created by Tom on 2016/3/8.
 */
public interface RequestQueue {

    /**
     * Add the request to the queue.
     * @param request
     * @return
     */
    void addRequest(Request request);


    /**
     * mark the request as canceled
     * @param request
     */
    void cancelRequest(Request request);

    /**
     * mark all request be cancelled;
     */
    void cancelAllRequest();

    /**
     * Is there an auth request in the request queue or not.
     * @return
     */
    boolean hasAuthRequestInQueue();

}
