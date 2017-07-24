package com.alpha.alphaapp.version;

/**
 * Created by kenway on 17/7/12 18:07
 * Email : xiaokai090704@126.com
 */

public interface UpdateStatus {
    /**
     * 没有新版本
     */
    int NO = 1;
    /**
     * 有新版本
     */
    int YES = 2;
    /**
     * 连接超时
     */
    int TIMEOUT = 3;
    /**
     * 没有wifi
     */
    int NOWIFI = 4;
    /**
     * 数据解析出错
     */
    int ERROR = -1;
}
