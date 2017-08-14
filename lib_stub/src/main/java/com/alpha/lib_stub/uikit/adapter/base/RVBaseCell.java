package com.alpha.lib_stub.uikit.adapter.base;

/**
 * Created by kenway on 17/8/11 17:29
 * Email : xiaokai090704@126.com
 */

public abstract class RVBaseCell<T> implements Cell {
    public  T mData;
    public  RVBaseCell(T t){
        mData=t;
    }

    @Override
    public void releaseResourse() {
        //do nothing
        //如果有需要回收的资源,子类自己实现
    }
}


