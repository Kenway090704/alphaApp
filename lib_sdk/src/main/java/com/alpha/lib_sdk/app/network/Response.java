package com.alpha.lib_sdk.app.network;

/**
 * Created by kenway on 17/5/22 11:42
 * Email : xiaokai090704@126.com
 * 网络请求响应类
 */

public class Response {

    /**
     * 响应数据
     */
    private byte[] mRealBody;

    private Object mBody;

    public byte[] getmRealBody() {
        return mRealBody;
    }

    public void setmRealBody(byte[] mRealBody) {
        this.mRealBody = mRealBody;
    }

    public Object getmBody() {
        return mBody;
    }

    public void setmBody(Object mBody) {
        this.mBody = mBody;
    }
}
