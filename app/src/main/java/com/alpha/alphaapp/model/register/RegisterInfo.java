package com.alpha.alphaapp.model.register;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.DeviceConstants;
import com.alpha.lib_sdk.app.arithmetic.MD5;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/5/22 15:13
 * Email : xiaokai090704@126.com
 * 帐号注册类,现在还没有但三分授权登录
 */

public class RegisterInfo {

    private String account;


    private int account_type;
    private String pw;
    private String user_ip;
    private String terminal_type;

    private String phone_verify;
    private String tuiguang_id;
    private String openid_qq;


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

    public static RegisterInfo objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getJSONObject(key).toString(), RegisterInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RegisterInfo> arrayRegisterAccountInfoFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RegisterInfo>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getJSONArray(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAccount_type() {
        return account_type;
    }

//    public void setAccount_type(int account_type) {
//        this.account_type = account_type;
//    }

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

    public String getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
    }


    /**
     * 获取帐号注册的json字符串
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
                .append("\"terminal_type\":").append("\"" + getTerminal_type() + "\"")
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
                .append("\"terminal_type\":").append("\"" + getTerminal_type() + "\",")
                .append("\"tuiguang_id\":").append("\"" + getTuiguang_id() + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 电话注册的json字符串
     * <p>
     * ex:{"account":"13883765487","account_type":1,"pw":"2b04edd88488d253d760ca03f4cd0f25","user_ip":"187.12.33.44","terminal_type":"PC","phone_verify":"123456"}
     * </p>
     *
     * @return
     */
    public String getJsonStrforPhone() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + getAccount() + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"pw\":").append("\"" + MD5.getMD5FromStr(getPw()) + "\",")
                .append("\"user_ip\":").append("\"" + getUser_ip() + "\",")
                .append("\"terminal_type\":").append("\"" + DeviceConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"phone_verify\":").append("\"" + getPhone_verify() + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 第三方授权注册  qq_logo,微信,sina   account是openid
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
                .append("\"terminal_type\":").append("\"" + getTerminal_type() + "\"")
                .append("}");
        return sb.toString();
    }

}
