package com.alpha.lib_sdk.app.network;

import java.util.Arrays;

import javax.xml.transform.stream.StreamResult;

/**
 * Created by kenway on 17/5/22 11:14
 * Email : xiaokai090704@126.com
 * network request abstract class
 */

public abstract class IRequest {
    private StreamRequest streamRequest;

    /**
     * get request data
     *
     * @return
     */
    public abstract byte[] getRequestBody();

    /**
     * get request cmd
     *
     * @return
     */
    public abstract int getCMDId();

    /**
     * 获取AESkey
     */

    public abstract byte[] getAesKey();

    /**
     * 获取SessionKey
     */
    public abstract byte[] getSessionKey();


    /**
     * 获取网络请求响应接口
     */
    public abstract ResponseCallBack getResponseCallBack();

    /**
     * 获取请求流
     *
     * @return
     */
    StreamRequest getStreamRequest() {
        return streamRequest;
    }

    void setStreamRequest(StreamRequest streamRequest) {
        this.streamRequest = streamRequest;
    }

    /**
     * 获取网络请求URL
     *
     * @return
     */
    public abstract String getURL();

    /**
     * 获取请求的json字符串
     *
     * @param obj
     * @return
     */
    public abstract String getRequsetJSON(Object obj);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IRequest)) {
            return false;
        }
        IRequest other = (IRequest) obj;
        if (getCMDId() == other.getCMDId() && Arrays.equals(other.getRequestBody(), getRequestBody())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Arrays.hashCode(getRequestBody());
        result = 31 * result + getCMDId();
        return result;
    }
}
