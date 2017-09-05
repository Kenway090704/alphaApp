package com.alpha.lib_sdk.app.tool;

import android.content.Context;

import com.alpha.lib_sdk.BuildConfig;
import com.alpha.lib_sdk.R;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.Base64Util;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;

/**
 * Created by kenway on 17/5/23 13:39
 * Email : xiaokai090704@126.com
 * <p>
 * 对jsondata进行签名和合并
 */

public class JsonEncryptUtil {

    private static final String TAG = "JsonEncryptUtil";
    //release
//    private static final String APPKEY = "y0PB0CUDHldY8sOz";
//    private static final String APPID = "9FprO7RiGjFknxPo";
    //debug
//    private static final String APPKEY = "0lLYgBbML3axJk3z";
//    private static final String APPID = "QmmVlsuaBMrnjKM2";


    private static String APPKEY = null;
    private static String APPID = null;



    /**
     * hash_mac签名,签名后使用Base64编码为字符串
     *
     * @param data
     * @return
     */
    public static String getPostJsonSignString(String data) {
        try {
            //初始化APPKEY,APPID

            String sign = Base64Util.encodeToString(HmacSHA1.getSignature(APPID + data, APPKEY));
            StringBuffer sb = new StringBuffer();
            sb.append("{\"appid\":").append("\"" + APPID + "\",")
                    .append("\"sign\":").append("\"" + sign + "\",")
                    .append("\"data\":").append(data)
                    .append("}");
            LogUtils.e(TAG, "josn=" + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化appkey  和appid
     * @param isdebug  根据当前是debug--true  release--false
     */

    public static void initAppkeyAndSercet(boolean isdebug) {

        if (isdebug) {
            //debug
            APPKEY = "0lLYgBbML3axJk3z";
            APPID = "QmmVlsuaBMrnjKM2";
        } else {
            //release
            //APPKEY = "y0PB0CUDHldY8sOz";
            //APPID = "9FprO7RiGjFknxPo";

            StringBuffer sb = new StringBuffer();
            sb.append(getbk1()).append(getbk2()).append(getbk3()).append(getbk4());
            APPKEY = sb.toString();
            APPID = "9FprO7RiGjFknxPo";
        }
        LogUtils.e("APPKEY=="+ APPKEY+",APPID=="+APPID);
    }

    /**
     * 第一部分gradle
     *
     * @return
     */
    private static String getbk1() {
        return "y0PB";
    }

    /**
     * 第二部分硬编码
     *
     * @return
     */
    private static String getbk2() {
        return "0CUD";
    }

    /**
     * 第三部分硬编码
     *
     * @return
     */
    private static String getbk3() {
        return "HldY";
    }

    /**
     * 第四部分string.xml
     *
     * @return
     */
    private static String getbk4() {

        return ResourceUtil.resToStr(ApplicationContext.getApplication(), R.string.bk4);
    }


}
