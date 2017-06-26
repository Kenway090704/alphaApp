package com.alpha.lib_sdk.app.core.event;

import com.alpha.lib_sdk.app.unitily.HttpUtils;

/**
 * Created by kenway on 17/6/26 13:52
 * Email : xiaokai090704@126.com
 */

public class RxEvent {
    protected String action;
    protected Callback callBack;

    protected RxEvent() {

    }

    public RxEvent(String action) {
        this.action = action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    public void setCallBack(Callback callBack) {
        this.callBack = callBack;
    }
    protected  interface Callback {
        void onCallback(RxEvent event);
    }
}
