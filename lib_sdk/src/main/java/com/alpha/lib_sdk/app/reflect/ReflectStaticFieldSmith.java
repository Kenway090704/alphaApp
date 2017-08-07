package com.alpha.lib_sdk.app.reflect;



import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;

import java.lang.reflect.Field;

/**
 * Reflect a static field of the given class.
 *
 * @param <FieldType>
 * @author AlbieLiang
 */
public class ReflectStaticFieldSmith<FieldType> {

    private static final String TAG = "SDK.ReflectStaticFieldSmith";
    private Class<?> mClazz;
    private String mFieldName;

    private boolean mInited;
    private Field mField;

    public ReflectStaticFieldSmith(Class<?> clazz, String fieldName) {
        if (clazz == null || Util.isNullOrNil(fieldName)) {
            throw new IllegalArgumentException("Both of invoker and fieldName can not be null or nil.");
        }
        this.mClazz = clazz;
        this.mFieldName = fieldName;
    }

    private synchronized void prepare() {
        if (mInited) {
            return;
        }
        Class<?> clazz = mClazz;
        while (clazz != null) {
            try {
                Field f = clazz.getDeclaredField(mFieldName);
                f.setAccessible(true);
                mField = f;
                break;
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
        }
        mInited = true;
    }

    public synchronized FieldType get() throws NoSuchFieldException, IllegalAccessException,
            IllegalArgumentException {
        return get(false);
    }

    @SuppressWarnings("unchecked")
    public synchronized FieldType get(boolean ignoreFieldNoExist)
            throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        prepare();
        if (mField == null) {
            if (!ignoreFieldNoExist) {
                throw new NoSuchFieldException();
            }
            LogUtils.w(TAG, "Field %s is no exists.", mFieldName);
            return null;
        }
        FieldType fieldVal = null;
        try {
            fieldVal = (FieldType) mField.get(null);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("unable to cast object");
        }
        return fieldVal;
    }

    /**
     * 没有抛出异常时获取这个成员属性
     *
     *
     * @return
     */
    public synchronized FieldType getWithoutThrow() {
        FieldType fieldVal = null;
        try {
            fieldVal = get(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return fieldVal;
    }

    public synchronized void set(FieldType val) throws NoSuchFieldException, IllegalAccessException,
            IllegalArgumentException {
        set(val, false);
    }

    public synchronized boolean set(FieldType val, boolean ignoreFieldNoExist)
            throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        prepare();
        if (mField == null) {
            if (!ignoreFieldNoExist) {
                throw new NoSuchFieldException("Method " + mFieldName + " is not exists.");
            }
            LogUtils.w(TAG, "Field %s is no exists.", mFieldName);
            return false;
        }
        mField.set(null, val);
        return true;
    }

    public synchronized boolean setWithoutThrow(FieldType val) {
        boolean result = false;
        try {
            result = set(val, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return result;
    }

}
