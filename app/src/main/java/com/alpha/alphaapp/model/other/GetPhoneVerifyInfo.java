package com.alpha.alphaapp.model.other;

import android.widget.EditText;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;

/**
 * Created by kenway on 17/5/25 15:00
 * Email : xiaokai090704@126.com
 * 获取手机验证码
 */

public class GetPhoneVerifyInfo {
    /**
     * 注册时
     * 获取手机验证码时的数据
     *
     * @return
     */
    public static String getJsonStrPhoneVerifyForRegiser(EditText et_phone) {
        if (et_phone == null) {
            return null;
        }
        String account = et_phone.getText().toString();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(et_phone.getContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + TypeConstants.GET_VERIFY.REGISTER + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 登录时
     * 获取手机验证码时的数据
     *
     * @param et_phone 手机号输入框对象
     * @return
     */
    public static String getJsonStrPhoneVerifyForLogin(EditText et_phone) {
        if (et_phone == null) {
            return null;
        }
        String account = et_phone.getText().toString();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(et_phone.getContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + TypeConstants.GET_VERIFY.LOGIN + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * qq或微信 绑定手机号
     * 获取手机验证码时的数据
     *
     * @param et_phone 手机号输入框对象
     * @return
     */
    public static String getJsonStrPhoneVerifyForBind(EditText et_phone) {
        if (et_phone == null) {
            return null;
        }
        String account = et_phone.getText().toString();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(et_phone.getContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + TypeConstants.GET_VERIFY.BIND_PHONE + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 手机号找回密码
     * 获取手机验证码时的数据
     * @param et_phone 手机号输入框对象
     * @return
     */
    public static String getJsonStrPhoneVerifyForGetPW(EditText et_phone) {
        if (et_phone == null) {
            return null;
        }
        String account = et_phone.getText().toString();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(et_phone.getContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + TypeConstants.GET_VERIFY.GET_PW + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * When binding the phone number,
     * get the verify code from the server
     */
    public static String getJsonStrPhoneVerifyForBindPhone(EditText et_phone) {
        if (et_phone == null) {
            return null;
        }
        String account = et_phone.getText().toString();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(et_phone.getContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + TypeConstants.GET_VERIFY.BIND_PHONE + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * When modifying the password,
     * get the verify code from the server
     */
    public static String getJsonStrPhoneVerifyForModifyPwd(EditText et_phone) {
        if (et_phone == null) {
            return null;
        }
        String account = et_phone.getText().toString();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + account + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(et_phone.getContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + TypeConstants.GET_VERIFY.MODIFY_PWD_BY_PHONE + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

}
