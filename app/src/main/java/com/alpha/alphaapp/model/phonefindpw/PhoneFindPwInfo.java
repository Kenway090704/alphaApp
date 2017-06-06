package com.alpha.alphaapp.model.phonefindpw;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.DeviceConstants;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;

/**
 * Created by kenway on 17/6/6 16:40
 * Email : xiaokai090704@126.com
 * 使用手机找回密码
 */

public class PhoneFindPwInfo {
    /**
     * 用手机找回密码的字段
     *
     * @param phone
     * @param verify
     * @param newPw
     * @return
     */
    public static String getJsonStrforPhoneFindPW(String phone, String verify, String newPw) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + DeviceConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + verify + "\",")
                .append("\"newpw\":").append("\"" + MD5.getMD5FromStr(newPw) + "\"")
                .append("}");
        return sb.toString();
    }

}
