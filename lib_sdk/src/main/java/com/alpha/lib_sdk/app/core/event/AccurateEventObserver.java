package com.alpha.lib_sdk.app.core.event;

import com.alpha.lib_sdk.app.log.LogUtils;

/**
 * Created by kenway on 17/6/26 13:56
 * Email : xiaokai090704@126.com
 */

public abstract class AccurateEventObserver<EventType extends RxEvent> implements EventObserver {
    private static final String TAG = "AccurateEventObserver";

    @Override
    public void onReceive(RxEvent rxEvent) {
        EventType event = null;

        try {

            event = (EventType) rxEvent;
        } catch (Exception e) {
            LogUtils.v(TAG, "cast RxEvent failed. %s", e);
            return;
        }
        if (event != null) {
            onReceiveEvent(event);
        }

    }

    public abstract void onReceiveEvent(EventType event);
}
