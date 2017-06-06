package com.alpha.alphaapp.model.other;

import android.widget.EditText;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.DeviceConstants;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
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
                .append("\"terminal_type\":").append("\"" + DeviceConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + DeviceConstants.GET_VERIFY.REGISTER + ",")
                .append("\"get_verify\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 登录时
     * 获取手机验证码时的数据
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
                .append("\"terminal_type\":").append("\"" + DeviceConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + DeviceConstants.GET_VERIFY.LOGIN + ",")
                .append("\"get_verify\":").append(System.currentTimeMillis())
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
                .append("\"terminal_type\":").append("\"" + DeviceConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + DeviceConstants.GET_VERIFY.MODIFY_PWD_BY_PHONE + ",")
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
                .append("\"terminal_type\":").append("\"" + DeviceConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + DeviceConstants.GET_VERIFY.BIND_PHONE + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

}
