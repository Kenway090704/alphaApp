package com.alpha.lib_sdk.app.core.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;


import com.alpha.lib_sdk.app.log.thread.WorkerThread;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by kenway on 17/5/22 10:27
 * Email : xiaokai090704@126.com
 * 线程池  post/postToMainThread   让子线程/主线程执行
 */

public class ThreadPool {
    /**
     * UI Handler
     */
    private static Handler sUiThreadHandler;
    /**
     * Work Handler
     */
    private static Handler sWorkerThreadHandler;

    /**
     * 初始化UI线程和非UI线程
     */
    static {

        //获取UI线程的Handler
        sUiThreadHandler = new Handler(Looper.getMainLooper());
        //获取非UI线程的Hanler
        HandlerThread thread = newHanderThread("WorkerThread-Core");
        thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        sWorkerThreadHandler = new Handler(thread.getLooper());
    }

    /**
     * 通过名称获取异步线程的Handler
     *
     * @param name string
     * @return HandlerThread
     */
    public static HandlerThread newHanderThread(String name) {
        return new HandlerThread(name);
    }

    public static HandlerThread newHandlerThread(String name, int priority) {
        // TODO
        return new HandlerThread(name, priority);
    }

    public static <T> WorkerThread<T> newWorkerThread(Class<T> clazz) {
        // TODO
        return new WorkerThread<T>();
    }

    public static <T> WorkerThread<T> newWorkerThread(Class<T> clazz, Looper looper) {
        // TODO
        return new WorkerThread<T>(looper);
    }

    public static Thread newThread(Runnable run) {
        // TODO
        return new Thread(run);
    }

    /**
     * Post task into UI thread tasks lists
     *
     * @param run
     */
    public static void postToMainThread(Runnable run) {
        if (run == null) {
            return;
        }
        sUiThreadHandler.post(run);
    }

    /**
     * post task into UI thread tasks list
     *
     * @param run
     * @param delay
     */
    public static void postToMainThread(Runnable run, long delay) {
        if (run == null) {
            return;
        }
        sUiThreadHandler.postDelayed(run, delay);
    }

    /**
     * post task into  worker thread tasks list
     *
     * @param run
     */
    public static void post(Runnable run) {
        if (run == null) {
            return;
        }
        sWorkerThreadHandler.post(run);
    }

    /**
     * post task into work thread tasks list
     *
     * @param run
     * @param delay
     */
    public static void post(Runnable run, long delay) {
        if (run == null) {
            return;
        }
        sWorkerThreadHandler.postDelayed(run, delay);
    }

    /**
     * Remove the callback  form UI Thread
     */
    public static void removeOnMainThread(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        sUiThreadHandler.removeCallbacks(runnable);
    }
}
