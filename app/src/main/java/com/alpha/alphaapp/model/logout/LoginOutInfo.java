package com.alpha.alphaapp.model.logout;


/**
 * Created by kenway on 17/5/23 16:59
 * Email : xiaokai090704@126.com
 */

public class LoginOutInfo {

    private String sskey;
    private String user_ip;
    private String terminal_type;


    public String getSskey() {
        return sskey;
    }

    public void setSskey(String sskey) {
        this.sskey = sskey;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
    }

    /**
     * 获取用户登出post json字符串
     *
     * @return
     */
    public String getJSONStrforLoginOut() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"sskey\":").append("\"" + getSskey() + "\",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + getTerminal_type() + "\"")
                .append("}");
        return sb.toString();
    }
}
