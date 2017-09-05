package com.alpha.alphaapp.ui.v_1_2.web.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.v_1_2.web.util.FormatUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/9/4 15:07
 * Email : xiaokai090704@126.com
 *
 */

public class Navigator {

    public  static   boolean openStandardLink(@NonNull Context context, @Nullable String url){
        if (FormatUtils.isUserLinkUrl(url)){
            ToastUtils.showToast(context,"进入论坛个人主页");
            return  true;
        }else  if (FormatUtils.isTopicLinkUrl(url)){

            ToastUtils.showToast(context,"进入文章显示页面");
            return  true;
        }else {
            return  false;
        }
    }

    /**
     * 在浏览器中打开页面
     * @param context
     * @param url
     */
    public  static  void  openInBrowser(@NonNull  Context context,@Nullable String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.showLong(context,R.string.no_browser_install_in_system);
        }
    }
}
