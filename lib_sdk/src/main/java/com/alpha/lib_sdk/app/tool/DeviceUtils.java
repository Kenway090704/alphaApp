package com.alpha.lib_sdk.app.tool;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

import com.alpha.lib_sdk.app.log.LogUtils;


/**
 * 设备工具类
 * @author AlbieLiang
 */
public class DeviceUtils {

    private static final String TAG = "Util.DeviceUtils";

    /**
     * 获取设备型号
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        if (context != null) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    String deviceId = tm.getDeviceId();
                    return deviceId == null ? "UNKNOWN DEVICE ID" : deviceId.trim();
                }
            } catch (Exception e) {
                LogUtils.e(TAG, "getDeviceId failed, exception : %s.", e);
            }
        }
        return "UNKNOWN DEVICE ID";
    }

    /**
     * @param context
     * @return
     */
    public static String getLine1Number(final Context context) {
        if (context != null) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    return tm.getLine1Number();
                }
                LogUtils.i(TAG, "get line1 number failed, null tm");
            } catch (SecurityException e) {
                LogUtils.e(TAG, "getLine1Number failed, exception : %s.", e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Is TabletDevice or not.
     *
     * @param context
     * @return
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Check whether the device has a SDCard.
     * @return
     */
    public static boolean hasSDCard() {
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    private static final long[] VIRBRATOR_PATTERN = new long[]{320, 200, 320, 200};

    /**
     * {@link #vibrate(Context, boolean, long[], int)}
     * 让手机震动
     * @param context
     * @param shake
     * @see #vibrate(Context, boolean, long[], int)
     */
    public static void vibrate(Context context, boolean shake) {
        vibrate(context, shake, VIRBRATOR_PATTERN, -1);
    }

    /**
     * see {@link Vibrator#vibrate(long[], int)}.
     * 是否让手机震动
     *
     * @param context
     * @param shake
     * @param pattern
     * @param repeat
     */
    public static void vibrate(Context context, boolean shake, long[] pattern, int repeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (shake) {
                vibrator.vibrate(pattern, repeat);
            } else {
                vibrator.cancel();
            }
        }
    }
}
