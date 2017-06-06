package com.alpha.alphaapp.model.modifyPassword;

import com.alpha.lib_sdk.app.arithmetic.MD5;

/**
 * Created by hanshuming on 2017/6/5.
 */

public class ModifyPwdByPhoneInfo {

    private String ssKey;
    private String account;
    private String accountType;
    private String userIP;
    private String terminalType;
    private String phoneVerifyCode;
    private String newPwd;

    public void setSsKey(String ssKey) { this.ssKey = ssKey; }

    public String getSsKey() { return ssKey; }

    public void setAccount(String account) { this.account = account; }

    public String getAccount() { return account; }

    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getAccountType() { return accountType; }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setPhoneVerifyCode(String phoneVerifyCode) { this.phoneVerifyCode = phoneVerifyCode; }

    public String getPhoneVerifyCode() { return phoneVerifyCode; }

    public void setNewPwd(String newPwd) { this.newPwd = newPwd; }

    public String getNewPwd() { return newPwd; }

    public String getJsonModifyPwdByPhoneInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + ssKey + "\",")
                .append("\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append("\"" + accountType + "\",")
                .append("\"user_ip\":").append("\"" + userIP + "\",")
                .append("\"terminal_type\":").append("\"" + terminalType + "\",")
                .append("\"phone_verify\":").append("\"" + phoneVerifyCode + "\",")
                .append("\"newpw\":").append("\"" + MD5.getMD5FromStr(newPwd) + "\"")
                .append("}");
        return sb.toString();
    }
}
