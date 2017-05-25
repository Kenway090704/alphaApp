package com.alpha.lib_sdk.app.log.thread;

import android.os.Handler;
import android.os.Looper;

import com.alpha.lib_sdk.app.core.thread.ArgsTransferHelper;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.reflect.ReflectStaticFieldSmith;
import com.alpha.lib_sdk.app.sync.LockEntity;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by kenway on 17/5/22 11:09
 * Email : xiaokai090704@126.com
 * <p>
 * 异步线程,主要作用是将崩溃日志和log日志写入到文件中
 * The WorkerThread has high extensibility ,You can invoke
 * {@link #addTask(DoTaskProxy)}to add a task into the worker queue and invoke
 * {@link #removeTask(DoTaskProxy) } or {@link #removeTask(DoTaskProxy)} to remove
 * task from the queue.
 * <p>
 * The {@link TaskType} can be a data or a real task, the {@link DoTaskProxy}
 * will be the proxy to help do task.
 */

public class WorkerThread<TaskType> extends Thread {
    private static final String TAG = "lib_sdk.WorkerThread";
    /**
     * 判读线程是否Stop
     */
    private volatile boolean mIsStop;

    private Queue<DoTaskProxy<TaskType>> mTasks;

    private LockEntity mLock;

    private Looper mLooper;

    private Handler mHandler;

    public WorkerThread() {
        this(null);

    }

    public WorkerThread(Looper looper) {
        mLooper = looper;
        mTasks = new LinkedList<DoTaskProxy<TaskType>>();
        mLock = new LockEntity();
    }


    @Override
    public void run() {
        try {
            if (mLooper != null) {
                //check Looper中有一个"sThreadLocal"成员属性
                ThreadLocal<Looper> tl = new ReflectStaticFieldSmith<ThreadLocal<Looper>>(Looper.class, "sThreadLocal").getWithoutThrow();
                if (tl != null && tl.get() == null) {
                    Log.d(TAG, "create a new Looper ThreadLocal variable.");
                    tl.set(mLooper);
                } else {
                    Log.d(TAG, "ThreadLocal Looper variable is null or has set.(%s)", tl);
                }
                mHandler = new Handler(mLooper);
            }
            DoTaskProxy<TaskType> task = null;
            while (!mIsStop) {
                if (mTasks.size() == 0) {
                    mLock.lock();
                }
                synchronized (mTasks) {
                    if (mTasks.size() == 0) {
                        continue;
                    }
                    //将头部的task取出
                    task = mTasks.poll();
                }
                if (task == null) {
                    continue;
                }
                if (task.isDoInLooper()) {
                    if (mHandler == null) {
                        Log.i(TAG, "Looper is null, can not do task in a null Looper.");
                        continue;
                    }
                    mHandler.post(new TransferArgsRunnable(task) {
                        @Override
                        public void run() {
                            //unchecked/
                            DoTaskProxy<TaskType> task = (DoTaskProxy<TaskType>) getArg(0);
                            task.doTask(task.getTaskType());
                        }
                    });
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * The method will shutdown the {@link WorkerThread} after the last task
     * finished.
     */
    public final synchronized void shutdown() {
        mIsStop = true;
        mLock.unlock();
    }

    /**
     * Add a {@link DoTaskProxy} into the {@link WorkerThread} task list.
     *
     * @param doTaskProxy
     * @return
     * @see #removeTask(DoTaskProxy)
     * @see #removeTask(DoTaskProxy)
     */
    public final boolean addTask(DoTaskProxy<TaskType> doTaskProxy) {
        boolean r = false;
        if (doTaskProxy != null) {
            synchronized (mTasks) {
                r = mTasks.add(doTaskProxy);
            }
            mLock.unlock();
        }
        return r;
    }

    /**
     * Remove the given task.
     *
     * @param doTaskProxy
     * @return
     * @see #removeTask(DoTaskProxy)
     */
    public final boolean removeTask(DoTaskProxy<TaskType> doTaskProxy) {
        boolean r = false;
        if (doTaskProxy != null) {
            synchronized (mTasks) {
                for (DoTaskProxy<TaskType> task : mTasks) {
                    if (doTaskProxy.equals(task)) {
                        r = mTasks.remove(doTaskProxy);
                        break;
                    }
                }
            }
        }
        return r;
    }

    public static abstract class DoTaskProxy<TaskType> extends ArgsTransferHelper {
        private TaskType taskType;
        private Object token;
        //是否运行在Looper中
        private boolean doInLooper;

        /**
         * @param token    the token can be used to remove task from the task queue,know more see{@link WorkerThread#removeTask(Object)}
         * @param taskType can be data or a real task to do in
         *                 {@link #doTask(Object)}
         * @param args     arguments to transfer into the {@link DoTaskProxy} and
         *                 invoke {@link #getArg(int)} to get them
         */
        public DoTaskProxy(Object token, TaskType taskType, Object... args) {
            super(args);
            this.taskType = taskType;
            this.token = token;
        }

        public TaskType getTaskType() {
            return taskType;
        }

        public Object getToken() {
            return token;
        }

        public void setDoInLooper(boolean doInLooper) {
            this.doInLooper = doInLooper;
        }

        public final boolean isDoInLooper() {
            return doInLooper;
        }

        public abstract void doTask(TaskType task);

        @Override
        public boolean equals(Object obj) {
            return this == obj || token == obj || (token != null && token.equals(obj));
        }
    }

}
