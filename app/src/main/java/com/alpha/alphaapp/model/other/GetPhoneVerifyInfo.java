package com.alpha.alphaapp.model.other;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.JsonUtil;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.alphaapp.ui.bind.phone.first.NewPhoneBindActvity1;
import com.alpha.alphaapp.ui.bind.phone.first.NewPhoneBindActvity2;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/5/25 15:00
 * Email : xiaokai090704@126.com
 * 获取手机验证码
 */

public class GetPhoneVerifyInfo {
    /**
     * 获取手机验证码时
     *
     * @param phone
     * @param verifyType 获取验证码类型
     * @return
     */

    public static String getJsonStrPhoneVerify(String phone, int verifyType) {
        if (Util.isNullOrBlank(phone)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + verifyType + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @param verifyType
     * @param back       GetPhoneVerify内部的CallBack
     */
    public static void getPhoneVerify(String phone, int verifyType, final CallBack back) {

        String data = GetPhoneVerifyInfo.getJsonStrPhoneVerify(phone, verifyType);
        String json = JsonUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                switch (info.getResult()) {
                    case CommStants.GET_PHONEVERIFY_RESULT.RESUTL_OK:
                        // 进入第二个页面
                        back.onGetVerifySuccess();
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.PHOEN_ERROR:
                        back.onGetVerifyFailed(info.getMsg());

                        //提示手机号码错误
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.PHONE_HAD_REGISTER:
                        back.onGetVerifyFailed(info.getMsg());
                        //提示手机号码已经注册
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.PHONE_NO_REGISTER:
                        back.onGetVerifyFailed(info.getMsg());
                        //提示手机号码没有注册
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.VERIFY_HAD:
                        back.onGetVerifyFailed(info.getMsg());
                        //提示验证码已经存在
                        break;
                    case CommStants.GET_PHONEVERIFY_RESULT.TOO_MUCH_MESSAGE:
                        back.onGetVerifyFailed(info.getMsg());
                        //提示获取验证码次数太多
                        break;
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                back.onGetVerifyFailed(errorMsg);
            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.PHONEVERIFY, json, callBack);
    }

    public interface CallBack {
        void onGetVerifySuccess();

        void onGetVerifyFailed(String failMsg);
    }

    /**
     * 登录时获取的手机验证码
     *
     * @param phone
     * @return
     */
    public static String getJsonStrPhoneVerifyForRegiser(String phone) {
        if (Util.isNullOrBlank(phone)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
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
     * @param phone 手机号输入框对象
     * @return
     */
    public static String getJsonStrPhoneVerifyForLogin(String phone) {
        if (Util.isNullOrBlank(phone)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
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
     * @param phone 手机号输入框对象
     * @return
     */
    public static String getJsonStrPhoneVerifyForBind(String phone) {
        if (Util.isNullOrBlank(phone)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + TypeConstants.GET_VERIFY.BIND_PHONE + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * 手机号找回密码
     * 获取手机验证码时的数据
     *
     * @param phone 手机号输入框对象
     * @return
     */
    public static String getJsonStrPhoneVerifyForGetPW(String phone) {
        if (Util.isNullOrBlank(phone)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
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
    public static String getJsonStrPhoneVerifyForBindPhone(String phone) {
        if (Util.isNullOrBlank(phone)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + TypeConstants.GET_VERIFY.BIND_PHONE + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

    /**
     * When modifying the password,
     * get the verify code from the server
     * 获取手机验证码,修改密码
     */
    public static String getJsonStrPhoneVerifyForModifyPwd(String phone) {
        if (Util.isNullOrBlank(phone)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":").append("\"" + phone + "\",")
                .append("\"account_type\":").append(CommStants.ACCOUNT_TYPE.PHONE + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\",")
                .append("\"get_verify\":").append("" + TypeConstants.GET_VERIFY.MODIFY_PWD_BY_PHONE + ",")
                .append("\"ts\":").append(System.currentTimeMillis())
                .append("}");
        return sb.toString();
    }

}
