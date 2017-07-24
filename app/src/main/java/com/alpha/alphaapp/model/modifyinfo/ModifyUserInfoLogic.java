package com.alpha.alphaapp.model.modifyinfo;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.CommStants;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.lib_sdk.app.tool.JsonEncryptUtil;
import com.alpha.alphaapp.model.result.ResponseInfo;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.IPAdressUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/5/23 15:19
 * Email : xiaokai090704@126.com
 * 一般用户信息修改
 */

public class ModifyUserInfoLogic {

    public static final String TAG = "ModifyUserInfoLogic";

    public static final int MODIFY_ICON = 0;
    public static final int MODIFY_NICK_NAME = 1;
    public static final int MODIFY_TRUE_NAME = 2;
    public static final int MODIFY_SEX = 3;
    public static final int MODIFY_BIRTHDAY = 4;
    public static final int MODIFY_SCHOOL = 5;
    public static final int MODIFY_QQ = 6;
    public static final int MODIFY_CONTACT_PHONE = 7;
    public static final int MODIFY_CONTACT_ADDR = 8;

    /**
     * 修改用户头像
     *
     * @return
     */
    private static String getJsonStrModifyIcon(String sskey, String icon) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_info\":{")
                .append("\"icon\":").append("\"" + icon + "\"")
                .append("},")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 修改昵称
     *
     * @param sskey
     * @param nickname
     * @return
     */
    private static String getJsonStrModifyNickName(String sskey, String nickname) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_info\":{")
                .append("\"name\":").append("\"" + nickname + "\"")
                .append("},")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 修改姓名
     *
     * @param sskey
     * @param true_name
     * @return
     */
    private static String getJsonStrModifyTrueName(String sskey, String true_name) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_info\":{")
                .append("\"true_name\":").append("\"" + true_name + "\"")
                .append("},")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 修改性别
     *
     * @param sskey
     * @param sex
     * @return
     */
    private static String getJsonStrModifySex(String sskey, int sex) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_info\":{")
                .append("\"sex\":").append(sex)
                .append("},")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 修改生日
     *
     * @param sskey
     * @param birth_day
     * @param birth_month
     * @param birth_year
     * @return
     */
    private static String getJsonStrModifyBirthday(String sskey, int birth_day, int birth_month, int birth_year) {
        StringBuilder sb = new StringBuilder();

        //这里的日期需要进行拆分
        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_info\":{")
                .append("\"birthday_year\":").append("" + birth_year + ",")
                .append("\"birthday_mon\":").append("" + birth_month + ",")
                .append("\"birthday_day\":").append("" + birth_day + "")
                .append("},")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 修改学校
     *
     * @param sskey
     * @param school
     * @return
     */
    private static String getJsonStrModifySchool(String sskey, String school) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_info\":{")
                .append("\"school\":").append("\"" + school + "\"")
                .append("},")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 修改QQ
     *
     * @param sskey
     * @param qq
     * @return
     */
    private static String getJsonStrModifyQQ(String sskey, String qq) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_info\":{")
                .append("\"qq\":").append("\"" + qq + "\"")
                .append("},")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 修改联系电话
     *
     * @param sskey
     * @param contact_phone
     * @return
     */
    private static String getJsonStrModifyContactPhone(String sskey, String contact_phone) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_info\":{")
                .append("\"contact_phone\":").append("\"" + contact_phone + "\"")
                .append("},")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 修改联系地址
     *
     * @param sskey
     * @param contact_addr
     * @return
     */
    private static String getJsonStrModifyContactAddr(String sskey, String contact_addr) {
        StringBuilder sb = new StringBuilder();

        sb.append("{\"sskey\":").append("\"" + sskey + "\",")
                .append("\"user_info\":{")
                .append("\"contact_addr\":").append("\"" + contact_addr + "\"")
                .append("},")
                .append("\"account_type\":").append(TypeConstants.ACCOUNT_TYPE.AUTH_QQ + ",")
                .append("\"user_ip\":").append("\"" + IPAdressUtils.getIpAdress(ApplicationContext.getCurrentContext()) + "\",")
                .append("\"terminal_type\":").append("\"" + TypeConstants.TERMINAL_TYPE.PHONE + "\"")
                .append("}");
        return sb.toString();
    }

    /**
     * 修改用户信息
     *
     * @param
     * @param
     */
    public static void doModifyUserInfo(String sskey, UserInfo info, int typeModify, final EditInfoCallBack back) {
        String data = null;
        switch (typeModify) {
            case MODIFY_ICON:
                data = getJsonStrModifyIcon(sskey, info.getIcon());
                break;
            case MODIFY_NICK_NAME:
                data = getJsonStrModifyNickName(sskey, info.getName());
                break;
            case MODIFY_TRUE_NAME:
                data = getJsonStrModifyTrueName(sskey, info.getTrue_name());
                break;
            case MODIFY_SEX:
                data = getJsonStrModifySex(sskey, info.getSex());
                break;
            case MODIFY_BIRTHDAY:
                data = getJsonStrModifyBirthday(sskey, info.getBirthday_day(), info.getBirthday_mon(), info.getBirthday_year());
                break;
            case MODIFY_SCHOOL:
                data = getJsonStrModifySchool(sskey, info.getSchool());
                break;
            case MODIFY_QQ:
                data = getJsonStrModifyQQ(sskey, info.getQq());
                break;
            case MODIFY_CONTACT_PHONE:
                data = getJsonStrModifyContactPhone(sskey, info.getContact_phone());
                break;
            case MODIFY_CONTACT_ADDR:
                data = getJsonStrModifyContactAddr(sskey, info.getContact_addr());
                break;
        }

        String json = JsonEncryptUtil.getPostJsonSignString(data);
        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                ResponseInfo info = ResponseInfo.getRespInfoFromJsonStr(result);
                //不论sskey是否过期都可以回到登录页面
                if (Util.isNull(info)) return;
                switch (info.getResult()) {
                    case CommStants.ACCOUNT_MODIFY_RESULT.RESULT_MODIFY_OK:
                        //修改信息完成后要重新加载信息
                        AccountManager.getInstance().loadUserinfo();
                        if (!Util.isNull(back))
                            back.onEditInfoSuccuss();
                        break;
                    case CommStants.ACCOUNT_MODIFY_RESULT.RESULT_RELOGIN:
                        if (!Util.isNull(back))
                            back.onEditInfoFailed(info.getMsg());
                        break;
                    case CommStants.ACCOUNT_MODIFY_RESULT.RESULT_MODIFY_FAILED:
                        if (!Util.isNull(back))
                            back.onEditInfoFailed(info.getMsg());
                        break;
                    default:
                        if (!Util.isNull(back))
                            back.onEditInfoFailed(info.getMsg());
                        break;
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                if (!Util.isNull(back))
                    back.onEditInfoFailed(errorMsg);
            }
        };

        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByJsonAsyn(URLConstans.URL.MODIFY_ACCOUNTINFO, json, callBack);

    }


    public interface EditInfoCallBack {
        void onEditInfoSuccuss();

        void onEditInfoFailed(String failMsg);
    }


}
