package com.alpha.lib_sdk.app.crashhandler;

import android.os.Build;

import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.log.log.DefaultCrashLogWriter;
import com.alpha.lib_sdk.app.log.log.ILogWriter;
import com.alpha.lib_sdk.app.protocols.StorageConstants;
import com.alpha.lib_sdk.app.sync.LockEntity;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.tool.ValueOptUtils;


/**
 * Created by Tom on 2016/3/23.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "skd.crashHandler";
    private static final CrashHandler crashHandler = new CrashHandler();
    //异常文件存储路径
    private static final String CRASH_LOG_DIR = StorageConstants.SDCard.CRASH_STORAGE_DIR;
    // 异常文件名
    private static final String CRASH_LOG_FILE_NAME = "Crash";
    //存储的文件类型
    private static final String LOG_FILE_SUFFIX = "txt";

    private ILogWriter mILogWriter;

    private CrashHandler() {
        // 异常写入处理类,主要是将信息写入文件
        mILogWriter = new DefaultCrashLogWriter(new LockEntity());
    }

    /**
     * 饿汉式单例模式
     *
     * @return
     */
    public static CrashHandler getInstance() {
        return crashHandler;
    }

    /**
     * 初始化未捕捉异常
     */
    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        long msec = System.currentTimeMillis();
        // 打印异常日志
        android.util.Log.e(TAG, android.util.Log.getStackTraceString(ex));
        //创建文件名,包含apk信息,时间,文件格式
        String fileName = ValueOptUtils.chain("", CRASH_LOG_FILE_NAME,
                ApplicationContext.getPidKey(ApplicationContext.getApplication(),
                        android.os.Process.myPid()), "_", DateUtils.getDateFormat("yyyy-MM-dd", msec), ".", LOG_FILE_SUFFIX);
        mILogWriter.start(CRASH_LOG_DIR, fileName, android.os.Process.myPid(), thread.getId(), msec);
        mILogWriter.writeLog(thread.getId(), msec, Log.ASSERT, TAG, getCrashInfoFormat(thread, ex));
        mILogWriter.shutdown();
        uploadCrashLog();

        //TODO need refine the process will restart!  发生崩溃关闭app
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 获取异常信息字符串
     *
     * @param thread
     * @param throwable
     * @return
     */
    private String getCrashInfoFormat(Thread thread, Throwable throwable) {
        StringBuffer sb = new StringBuffer();
        sb.append("[Time]=").append(DateUtils.getDateFormat("yyyy-MM-dd hh:mm:ss", System.currentTimeMillis())).append("\r\n");
        sb.append("[Device]=").append(Build.MANUFACTURER + " ").append(Build.MODEL + " ").append(Build.VERSION.RELEASE).append("\r\n");
        sb.append(throwable.getClass().getName());
        if (throwable.getMessage() != null) {
            sb.append(" : ").append(throwable.getMessage());
        }
        sb.append("\r\n");
        StackTraceElement[] stackTraceElement = throwable.getStackTrace();
        for (int i = 0; i < stackTraceElement.length; i++) {
            sb.append("    ");
            sb.append("at ");
            sb.append(stackTraceElement[i].getClassName()).append(".");
            sb.append(stackTraceElement[i].getMethodName());
            sb.append("(").append(stackTraceElement[i].getClassName()).append(":")
                    .append(stackTraceElement[i].getLineNumber()).append(")");
            sb.append("\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }

    //TODO upload the log to the server

    /**
     * upload the log to the server
     * 提交crash 信息到服务器
     */
    private void uploadCrashLog() {

    }

}
