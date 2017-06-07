package com.alpha.alphaapp.model.login;

import android.widget.EditText;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;

/**
 * Created by kenway on 17/5/23 15:11
 * Email : xiaokai090704@126.com
 * 登录信息
 */

public class LoginInfo {
    /**
     * 最近登录类型
     * phone ,account ,auth
     */
    private int lastLoginType;
    /**
     * 登录后返回的sesskey
     * 可以保存24小时
     */
    private String sessKey;

    public String getSessKey() {
        return sessKey;
    }

    public void setSessKey(String sessKey) {
        this.sessKey = sessKey;
    }

    private String account;
    private int account_type;
    private String pw;
    private String user_ip;
    private String terminal_type;

    private String phone_verify;
    private String tuiguang_id;
    private String openid_qq;


    public int getLastLoginType() {
        return lastLoginType;
    }

    public void setLastLoginType(int lastLoginType) {
        this.lastLoginType = lastLoginType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }


    public String getPhone_verify() {
        return phone_verify;
    }

    public void setPhone_verify(String phone_verify) {
        this.phone_verify = phone_verify;
    }

    public String getTuiguang_id() {
        return tuiguang_id;
    }

    public void setTuiguang_id(String tuiguang_id) {
        this.tuiguang_id = tuiguang_id;
    }

    public String getOpenid_qq() {
        return openid_qq;
    }

    public void setOpenid_qq(String openid_qq) {
        this.openid_qq = openid_qq;
    }

    /**
     * 一般帐号登录
     * <p>
     * ex:{"account":"kenway","account_type":0,"pw":"123456","user_ip":"","terminal_type":""}
     * </p>
     *
     * @return
     */
    public String getJsonStrforAccount() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + getAccount() + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(getPw()) + "\",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 手机号与密码登录
     * <p>
     * ex:{"account":"kenway","account_type":0,"pw":"123456","user_ip":"","terminal_type":""}
     * </p>
     *
     * @param et_uesrname 用户名输入框架
     * @param et_pw       密码输入框架
     * @return
     */
    public static String getJsonStrforphoneInAccount(EditText et_uesrname, EditText et_pw) {
        if (et_pw == null || et_uesrname == null) {
            return null;
        }

        String account = et_uesrname.getText().toString();
        String pw = et_pw.getText().toString();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(pw) + "\",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(et_uesrname.getContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 获取帐号注册的json字符串
     * <p>
     * ex:{“account":"tonywu","account_type":0,"pw":"8a017188e9a2803466f951f160a36c7a ",”user_ip”:"187.12.33.44","terminal_type":"PC",”tuiguang_id”:””}
     * </p>
     *
     * @return
     */
    public String getJsonStrforAccountHasTuiguangID() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + getAccount() + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.ACCOUNT + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(getPw()) + "\",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"tuiguang_id\":").append("\"" + getTuiguang_id() + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 手机账号登录
     * <p>
     * ex:{"account":"13883765487","account_type":1,"pw":"2b04edd88488d253d760ca03f4cd0f25","user_ip":"187.12.33.44","terminal_type":"PC","phone_verify":"123456"}
     * </p>
     *
     * @return
     */
    public String getJsonStrforPhone() {

        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(getPw()) + "\",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + getPhone_verify() + "\"")
                .append("}");
        return sb.toString();
    }


    /**
     * 第三方登录  qq_logo,微信,sina account是openid
     * <p>
     * {“account":AFGHR9080,"account_type":3,user_ip:"187.12.33.44",terminal_type:"PC"}
     * <p/>
     *
     * @return
     */
    public String getJsonStrforAuth() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + getOpenid_qq() + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.AUTH + ",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }


    @Override
    public String toString() {
        return "LoginInfo{" +
                "lastLoginType=" + lastLoginType +
                ", sessKey='" + sessKey + '\'' +
                ", account='" + account + '\'' +
                ", account_type=" + account_type +
                ", pw='" + pw + '\'' +
                ", user_ip='" + user_ip + '\'' +
                ", terminal_type='" + terminal_type + '\'' +
                ", phone_verify='" + phone_verify + '\'' +
                ", tuiguang_id='" + tuiguang_id + '\'' +
                ", openid_qq='" + openid_qq + '\'' +
                '}';
    }
}
