package com.alpha.lib_sdk.app.tool;

import com.alpha.lib_sdk.app.arithmetic.Base64Util;
import com.alpha.lib_sdk.app.log.Log;

/**
 * Created by kenway on 17/5/23 13:39
 * Email : xiaokai090704@126.com
 * <p>
 * 对jsondata进行签名和合并
 */

public class JsonUtil {

    private static final String TAG = "JsonUtil";
    private static final String APPKEY = "0lLYgBbML3axJk3z";
    private static final String APPID = "QmmVlsuaBMrnjKM2";

    /**
     * hash_mac签名,签名后使用Base64编码为字符串
     *
     * @param data
     * @return
     */
    public static String getPostJsonSignString(String data) {

        try {
            String has = HmacSHA1.getSignature(APPID + data, APPKEY);

            String sign = Base64Util.encodeToString(HmacSHA1.getSignature(APPID + data, APPKEY));


            StringBuffer sb = new StringBuffer();
            sb.append("{\"appid\":").append("\"" + APPID + "\",")
                    .append("\"sign\":").append("\"" + sign + "\",")
                    .append("\"data\":").append(data)
                    .append("}");
            Log.e(TAG, "josn=" + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
