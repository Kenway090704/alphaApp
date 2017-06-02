package com.alpha.alphaapp.model.modifyPassword;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.lib_sdk.app.arithmetic.MD5;

/**
 * Created by hanshuming on 2017/6/1.
 */

public class ModifyPwdByPwdInfo {

    private String sskey;
    private String oldPwd;
    private String newPwd;
    private String userIP;
    private String terminalType;

    public void setSskey(String sskey) {
        this.sskey = sskey;
    }

    public String getSskey() {
        return sskey;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setNewPwd(String newPwd) { this.newPwd = newPwd; }

    public String getNewPwd() { return newPwd; }

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

    public String getJsonModifyPwdByPwdInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + getSskey() + "\",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(oldPwd) + "\",")
                .append("\"newpw\":").append("\"" + MD5.getMD5FromStr(newPwd) + "\",")
                .append("\"user_ip\":").append("\"" + userIP + "\",")
                .append("\"terminal_type\":").append("\"" + terminalType + "\"")
                .append("}");
        return sb.toString();
    }


}
