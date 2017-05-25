package com.alpha.alphaapp.model.other;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.DeviceConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/5/25 15:00
 * Email : xiaokai090704@126.com
 */

public class GetPhoneVerifyInfo {


    private String account;
    private String user_ip;
    private int ts;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }



    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }


    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    /**
     * 注册时
     * 获取手机验证码时的数据
     *
     * @return
     */
    public String getJsonStrPhoneVerifyForRegiser() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + getAccount() + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + DeviceConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + DeviceConstants.GET_VERIFY.REGISTER + ",")
                .append("\"get_verify\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 登录时
     * 获取手机验证码时的数据
     *
     * @return
     */
    public String getJsonStrPhoneVerifyForLogin() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + getAccount() + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + DeviceConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + DeviceConstants.GET_VERIFY.LOGIN + ",")
                .append("\"get_verify\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

}
