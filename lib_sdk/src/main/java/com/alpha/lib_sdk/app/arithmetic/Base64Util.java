package com.alpha.lib_sdk.app.arithmetic;

import android.util.Base64;

/**
 * Created by kenway on 17/5/23 12:52
 * Email : xiaokai090704@126.com
 */

public class Base64Util {
    /**
     * base64加密
     *
     * @param data
     * @return
     */
    public static String encodeToString(String data) {
        String encodedString = Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
        //去除换行符
        return encodedString.replaceAll("\r|\n", "");
    }
}
