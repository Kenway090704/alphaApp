package com.alpha.alphaapp.ui.v_1_2.web.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alpha.lib_stub.comm.URLConstans;

/**
 * Created by kenway on 17/9/4 15:17
 * Email : xiaokai090704@126.com
 */

public class FormatUtils {



    /**
     * 判读是否为用户信息的Url
     */

    public static boolean isUserLinkUrl(@Nullable String url) {
        //这里后要修改为获取论坛中个人信息的接口
        return !TextUtils.isEmpty(url) && url.startsWith(URLConstans.URL_ROOT);
    }

    /**
     * 帖子的Url
     * @param url
     * @return
     */
    public static boolean isTopicLinkUrl(@Nullable String url) {
        return !TextUtils.isEmpty(url) && url.startsWith(URLConstans.URL_ROOT);
    }
}
