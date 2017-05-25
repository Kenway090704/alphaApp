package com.alpha.lib_sdk.app.log.log;


import com.alpha.lib_sdk.app.core.thread.ThreadPool;
import com.alpha.lib_sdk.app.log.thread.WorkerThread;
import com.alpha.lib_sdk.app.protocols.LocalDataConstants;
import com.alpha.lib_sdk.app.sync.LockEntity;
import com.alpha.lib_sdk.app.tool.DeviceUtils;
import com.alpha.lib_sdk.app.tool.FileUtils;
import com.alpha.lib_sdk.app.tool.ValueOptUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Tom on 2016/3/24.
 */
public class DefaultCrashLogWriter implements ILogWriter {

    private static final String TAG = "DefaultCrashLogWriter";
    /**
     * 是否开始
     */
    private volatile boolean mHasStarted;
    /**
     * 写出流
     */
    private FileOutputStream mFileOutputStream;
    /**
     * 进程pid
     */
    private int pid;
    /**
     * 非UI线程
     */
    private WorkerThread<String> mWorkThread;
    /**
     * 同步锁
     */
    private LockEntity mLockEntity;

    public DefaultCrashLogWriter(LockEntity lockEntity) {
        pid = android.os.Process.myPid();//获取主进程
        this.mLockEntity = lockEntity;
    }

    @Override
    public synchronized boolean start(String dir, String fileName, int pid, long mainTid, long msec) {
        if (mHasStarted) {
            android.util.Log.e(TAG, "CrashLogWriter has started");
            return false;
        }

        String path = dir + File.separator + fileName;
        if (!DeviceUtils.hasSDCard()) {
            android.util.Log.w(TAG, ValueOptUtils.format("try to create file failed, sdcard do not exist.(%s)", path));
            return false;
        }

        String filePath = dir + File.separator + fileName;
        File file = new File(filePath);
        FileUtils.createFileIfNeed(file);
        try {
            mFileOutputStream = new FileOutputStream(file, true);
            mWorkThread = ThreadPool.newWorkerThread(String.class);
            mWorkThread.setName("Thread-CrashLogWriter");
            mWorkThread.start();
            mHasStarted = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void shutdown() {
        if (!mHasStarted) {
            android.util.Log.e(TAG, "CrashLogWriter has been shutdown");
        }
        try {
            mLockEntity.lock(); // make sure that before the output stream closed the log has been written to the file.
            mFileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mHasStarted = false;
            mWorkThread.shutdown();
            mFileOutputStream = null;
            android.util.Log.i(TAG, "Shutdown Crash Log writer successfully.");
        }
    }

    @Override
    public void writeLog(long tid, long msec, int priority, String tag, String log) {
        if (!mHasStarted) {
            android.util.Log.w(TAG, "File output stream has been closed or the writer has not started at all.");
            return;
        }
        mWorkThread.addTask(new DoTaskProxyImpl(log, mFileOutputStream,mLockEntity));
    }


    private static final class DoTaskProxyImpl extends WorkerThread.DoTaskProxy<String> {

        private static final String TAG = "Log.CrashLogWriterTask";

        private FileOutputStream mFileOutputStream;
        private LockEntity mLockEntity;

        public DoTaskProxyImpl(String data, FileOutputStream fos, LockEntity entity) {
            super(data, data);
            this.mFileOutputStream = fos;
            this.mLockEntity = entity;
        }

        @Override
        public void doTask(String task) {
            if (mFileOutputStream == null) {
                android.util.Log.e(TAG, "mFileOutputStream is null");
                return;
            }
            byte[] buffer = null;
            try {
                buffer = task.toString().getBytes(LocalDataConstants.Charset.LOG_CHARSET);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            if (buffer == null) {
                return;
            }
            try {
                mFileOutputStream.write(buffer);
                mFileOutputStream.flush();
                mLockEntity.unlock(); // after finishing the output should do unlock.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
