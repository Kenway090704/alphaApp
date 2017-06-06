package com.alpha.alphaapp.model.login;

/**
 * Created by kenway on 17/6/5 11:28
 * Email : xiaokai090704@126.com
 * qq登录后获取的qq用户信息
 */

public class QQLoginInfo {

    private String nickName;
    private String gender;
    private String icon;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
