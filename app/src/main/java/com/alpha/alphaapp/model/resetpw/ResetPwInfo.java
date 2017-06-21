package com.alpha.alphaapp.model.resetpw;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;

/**
 * Created by kenway on 17/6/9 18:12
 * Email : xiaokai090704@126.com
 * 重置密码,在找回密码中使用当微信有绑定英文帐号时使用
 */

public class ResetPwInfo {
    /**
     * 获取重置密码的字符串,微信绑定的是帐号
     *
     * @param account
     * @param pw
     * @return
     */
    public static String getJsonStrForAccount(String account, String pw) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 获取重置密码的字符串,微信绑定的是手机
     *
     * @param account
     * @param pw
     * @return
     */
    public static String getJsonStrForPhone(String account, String pw) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

}
