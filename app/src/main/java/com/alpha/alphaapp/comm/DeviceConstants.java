package com.alpha.alphaapp.comm;

/**
 * Created by kenway on 17/5/25 14:11
 * Email : xiaokai090704@126.com
 * 关于设备的常数
 */

public class DeviceConstants {
    public interface TERMINAL_TYPE {
        String PHONE = "PHONE";
    }

    /**
     * 获取手机验证码的时候使用
     */
    public interface GET_VERIFY {
        int LOGIN = 1;
        int REGISTER = 2;

    }

}
