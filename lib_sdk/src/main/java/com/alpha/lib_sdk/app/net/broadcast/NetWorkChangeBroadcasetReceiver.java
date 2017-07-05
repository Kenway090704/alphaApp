package com.alpha.lib_sdk.app.net.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.alpha.lib_sdk.R;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.unitily.NetWorkUtil;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/7/3 18:02
 * Email : xiaokai090704@126.com
 */

public class NetWorkChangeBroadcasetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if(ApplicationContext.getCurrentContext() == null || !(ApplicationContext.getCurrentContext() instanceof Activity)) {
//            ToastUtil.show(R.string.local_network_error);
//            return;
//        }
//        NetworkIssueDialog dialog = new NetworkIssueDialog(ApplicationContext.getCurrentContext());
//        dialog.show();

        if (!NetWorkUtil.isConnected(ApplicationContext.getApplication())) {
            //做你想做的事,弹出对话框登录
            ToastUtils.showLong(context, R.string.local_network_error);
        }
    }

}
