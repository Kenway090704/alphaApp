package com.alpha.alphaapp.ui;

import android.os.Bundle;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.lib_sdk.app.core.event.AccurateEventObserver;
import com.alpha.lib_sdk.app.core.event.EventObserver;
import com.alpha.lib_sdk.app.core.event.RxEventBus;
import com.alpha.lib_sdk.app.core.event.acc.AccountUpdataEvent;

/**
 * Created by kenway on 17/6/26 13:50
 * Email : xiaokai090704@126.com
 * 当用户信息修改时,界面信息也修改
 */

public abstract class AccountChangeActivity extends BaseActivity {
    private EventObserver eventObserver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventObserver = new AccurateEventObserver<AccountUpdataEvent>() {

            @Override
            public void onReceiveEvent(AccountUpdataEvent event) {
                onAccountUpdate(AccountManager.getInstance().getUserInfo());
            }
        };
        RxEventBus.getBus().register(AccountUpdataEvent.ID, eventObserver);
    }


    @Override
    protected void onDestroy() {
        RxEventBus.getBus().unregister(AccountUpdataEvent.ID, eventObserver);
        eventObserver = null;
        super.onDestroy();
    }
    /**
     * 当用户信息发生修改时,更新UI
     * @param info
     */
    public abstract void onAccountUpdate(UserInfo info);



}
