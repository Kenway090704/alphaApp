package com.alpha.alphaapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.lib_sdk.app.core.event.AccurateEventObserver;
import com.alpha.lib_sdk.app.core.event.EventObserver;
import com.alpha.lib_sdk.app.core.event.RxEventBus;
import com.alpha.lib_sdk.app.core.event.acc.AccountUpdataEvent;

/**
 * Created by kenway on 17/6/26 15:18
 * Email : xiaokai090704@126.com
 * 需要当帐号中信息改变时,进行对应的操作
 */

public abstract class AccountChangeFragment extends BaseFragment {
    private EventObserver eventObserver = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 加载根布局
        root = inflater.inflate(getLayoutId(), null);
        // 初始化控件
        initViews(root);
        // 初始化监听事件
        initEnvent();
        //注册信息更新
        eventObserver = new AccurateEventObserver<AccountUpdataEvent>() {
            @Override
            public void onReceiveEvent(AccountUpdataEvent event) {
                onAccountUpdate(AccountManager.getInstance().getUserInfo());
            }
        };
        RxEventBus.getBus().register(AccountUpdataEvent.ID, eventObserver);
        return root;
    }


    @Override
    public void onDestroy() {
        RxEventBus.getBus().unregister(AccountUpdataEvent.ID, eventObserver);
        eventObserver = null;
        super.onDestroy();
    }

    public abstract void onAccountUpdate(UserInfo info);


}
