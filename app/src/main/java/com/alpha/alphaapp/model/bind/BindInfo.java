package com.alpha.alphaapp.model.bind;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;

/**
 * Created by kenway on 17/6/5 17:24
 * Email : xiaokai090704@126.com
 * 该类是用于qq,weixin首次授权绑定帐号,和手机号
 */

public class BindInfo {
    private String sskey;
    private String account;


    public String getSskey() {
        return sskey;
    }

    public void setSskey(String sskey) {
        this.sskey = sskey;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public static String getJsonforBindAccount(String sskey, String account,String pw) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\"")
                .append("}");
        return sb.toString();
    }

    public static String getJsonforBindPhone(String sskey, String phone, String verifyCode) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + verifyCode + "\"")
                .append("}");
        return sb.toString();
    }

    public static String getJsonforBindQQ(String sskey, String openid) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"account\":").append("\"" + openid + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.AUTH + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    public static String getJsonforBindWechat(String sskey, String wechatopneid) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"account\":").append("\"" + wechatopneid + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.AUTH_WECHAT + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

}
