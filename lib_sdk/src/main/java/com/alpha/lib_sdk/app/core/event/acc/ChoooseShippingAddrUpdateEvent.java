package com.alpha.lib_sdk.app.core.event.acc;

import com.alpha.lib_sdk.app.core.event.RxEvent;

/**
 * Created by kenway on 17/7/19 17:50
 * Email : xiaokai090704@126.com
 */

public class ChoooseShippingAddrUpdateEvent<T> extends RxEvent {

    public final static String ID = "ChoooseShippingAddrUpdateEvent";


    private T t;

    public T getT() {
        return t;
    }

    public ChoooseShippingAddrUpdateEvent(T t) {
        this.action = ID;
        this.t = t;

    }


    public ChoooseShippingAddrUpdateEvent(Callback callback) {
        this.action = ID;
        this.callBack = callback;
    }
}
