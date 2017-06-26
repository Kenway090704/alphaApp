package com.alpha.lib_sdk.app.core.event.acc;

import com.alpha.lib_sdk.app.core.event.RxEvent;

/**
 * Created by kenway on 17/6/26 14:49
 * Email : xiaokai090704@126.com
 *
 */

public class AccountUpdataEvent extends RxEvent {

    public final static String ID = "AutoGenEvent.AccountUpdate";

    public AccountUpdataEvent() {
        this.action = ID;
    }

    public AccountUpdataEvent(Callback callback) {
        this.action = ID;
        this.callBack = callback;
    }
}
