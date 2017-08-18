package com.alpha.lib_sdk.app.tool;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by kenway on 17/7/25 11:43
 * Email : xiaokai090704@126.com
 */

public class SystemUtils {
    /**
     * 获取当前时间
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis()/1000;
    }

    /**
     * 获取当前时间
     */
    public static long getCurrentTimeMillisLong() {
        return System.currentTimeMillis();
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
