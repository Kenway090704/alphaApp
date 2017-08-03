package com.alpha.alphaapp.ui;

import android.os.Bundle;

import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.lib_sdk.app.core.event.AccurateEventObserver;
import com.alpha.lib_sdk.app.core.event.EventObserver;
import com.alpha.lib_sdk.app.core.event.RxEventBus;
import com.alpha.lib_sdk.app.core.event.acc.ChoooseShippingAddrUpdateEvent;

/**
 * Created by kenway on 17/7/19 17:52
 * Email : xiaokai090704@126.com
 * <p>当在选择收货地址页面进行点击item后,更新兑换商品页面的UI</p>
 */

public abstract class BaseChooseAddrActivity extends BaseActivity {
    private EventObserver eventObserver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventObserver = new AccurateEventObserver<ChoooseShippingAddrUpdateEvent<ShippingAddrBean>>() {

            @Override
            public void onReceiveEvent(ChoooseShippingAddrUpdateEvent<ShippingAddrBean> event) {
                onShippingAddrUpdate(event.getT());
            }
        };
        RxEventBus.getBus().register(ChoooseShippingAddrUpdateEvent.ID, eventObserver);
    }


    @Override
    protected void onDestroy() {
        RxEventBus.getBus().unregister(ChoooseShippingAddrUpdateEvent.ID, eventObserver);
        eventObserver = null;
        super.onDestroy();
    }

    /**
     * 当选取的收获地址发生改变时,更新兑换页面的UI
     */
    public abstract void onShippingAddrUpdate(ShippingAddrBean bean);

}
