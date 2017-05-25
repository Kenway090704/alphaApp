package com.alpha.lib_sdk.app.sync;

import java.io.InterruptedIOException;

/**
 * Created by kenway on 17/5/22 15:58
 * Email : xiaokai090704@126.com
 * 同步锁
 */

public class LockEntity {

    private Object mLock;
    private boolean[] mUnlockConditions;

    public LockEntity() {
        this(0);
    }

    public LockEntity(int unlockconditionCount) {
        if (unlockconditionCount > 0) {
            mUnlockConditions = new boolean[unlockconditionCount];
        }
        mLock = new Object();
    }

    public boolean needLock() {
        boolean needLock = false;
        for (int i = 0; i < mUnlockConditions.length; i++) {
            if (!mUnlockConditions[i]) {
                needLock = true;
                break;
            }
        }
        return needLock;
    }

    public void lock() throws InterruptedException {
        triggerLock(0);
    }

    /**
     * @param millis
     * @throws InterruptedException
     * @see Object#wait(long)
     */
    public void lock(long millis) throws InterruptedException {
        triggerLock(millis);
    }

    public void unlock() {
        triggerUnlock();
    }

    public void updateLockCondition(int pos, boolean state)
            throws InterruptedException {
        if (updateCondition(mUnlockConditions, pos, state)) {
            triggerLock(0);
        }
    }

    public void updateUnlockCondition(int pos, boolean state) {
        if (updateCondition(mUnlockConditions, pos, state)) {
            triggerUnlock();
        }
    }

    private void triggerLock(long millis) throws InterruptedException {
        synchronized (mLock) {
            mLock.wait(millis);
        }
    }

    private void triggerUnlock() {
        synchronized (mLock) {
            mLock.notifyAll();
        }
    }

    private boolean updateCondition(boolean[] conditions, int pos, boolean state) {
        if (conditions != null && pos >= 0 && pos < conditions.length) {
            conditions[pos] = state;
        }
        boolean match = state;
        if (state) {
            for (int i = 0; i < conditions.length; i++) {
                if (!conditions[i]) {
                    match = false;
                    break;
                }
            }
        }
        return match;
    }

}
