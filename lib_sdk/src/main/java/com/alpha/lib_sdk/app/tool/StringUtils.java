package com.alpha.lib_sdk.app.tool;

import com.alpha.lib_sdk.app.log.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kenway on 17/5/24 11:50
 * Email : xiaokai090704@126.com
 * 判断密码和帐号是否正确的
 */

public class StringUtils {
    /**
     * 找到字符中位置不同的地方
     *
     * @param str1
     * @param str2
     */
    public static void get2StrDiff(String str1, String str2) {
        char[] a1 = str1.toCharArray();
        char[] b1 = str2.toCharArray();
        int c = a1.length < b1.length ? a1.length : b1.length;
        for (int i = 0; i < c; i++) {
            if (a1[i] != b1[i]) {
                Log.e("xk", "错误位置" + i + ",str1=" + a1[i - 2] + a1[i - 1] + a1[i] + ",str2=" + b1[i - 2] + b1[i - 1] + b1[i]);
                break;
            }
        }
    }

    /**
     * 帐号注册时的验证帐号是否正确
     */
    public static boolean isAccountLine(String account) {
        account = account.trim();
        Pattern p = Pattern
                .compile("[a-z]{1}[A-Za-z0-9]{5,11}");
        Matcher m = p.matcher(account);
        return m.matches();
    }

    /**
     * 帐号注册时的密码是否符合
     * 密码判读有误
     */
    public static boolean isPWLine(String pw) {
        pw = pw.trim();
        Pattern p = Pattern
                .compile("[A-Za-z0-9]{6,12}");
        Matcher m = p.matcher(pw);
        return m.matches();
    }

    /**
     * 判断是否是手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhoneNum(String mobiles) {
        mobiles = mobiles.trim();
        Pattern p = Pattern
                .compile("^(13[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否是手机验证码
     * 手机验证码也存在问题
     *
     * @param verify
     * @return
     */
    public static boolean isPhoneVerify(String verify) {
        verify = verify.trim();
        Pattern p = Pattern
                .compile("\\d{6}");
        Matcher m = p.matcher(verify);
        return m.matches();
    }

    /**
     * 验证是否为qq号
     *
     * @param qq
     * @return
     */
    public static boolean isQQ(String qq) {
        qq = qq.trim();
        Pattern p = Pattern
                .compile("^[1-9][0-9]{4,9}");
        Matcher m = p.matcher(qq);
        return m.matches();
    }


    /**
     * 判断是否为昵称
     */
    public static boolean isName(String name) {
        name = name.trim();
        Pattern p = Pattern
                .compile("[\\u4e00-\\u9fa5a-zA-Z0-9\\-]{4,12}");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 判断是否为真实姓名
     *
     * @param name
     * @return
     */
    public static boolean isTrueName(String name) {
        name = name.trim();
        Pattern p = Pattern
                .compile("[\\u4e00-\\u9fa5a-zA-Z0-9\\-]{1,12}");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 判断是否为真实姓名
     *
     * @param postCode
     * @return
     */
    public static boolean isPost_Code(String postCode) {
        postCode = postCode.trim();
        Pattern p = Pattern
                .compile( "[1-9]\\d{5}");
        Matcher m = p.matcher(postCode);
        return m.matches();
    }

    /**
     * 隐藏手机的中间四位的字符串
     *
     * @param phone
     * @return
     */
    public static String getHideMidPhone(String phone) {
        char[] chars = phone.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i > 2 & i < 7) {
                sb.append("*");
            } else {
                sb.append(chars[i]);
            }

        }
        return sb.toString();
    }

    /**
     * 获取授权登录时的昵称字符串
     * @param nickName
     * @return
     */
    public static String getNickName(String nickName) {
        if (Util.isNull(nickName)) {
            return null;
        }
        nickName = nickName.trim();
        int size = nickName.length();
        if (size > 4 && size < 12) {
            return nickName;
        } else if (size < 4) {
            return "奥飞用户" + nickName;
        } else {
            return nickName.substring(0, 12);
        }
    }


    /**
     * 判断是否为激活码
     *
     * @param active_code
     * @return
     */
    public static boolean isActiveCode(String active_code) {
        active_code = active_code.trim();
        Pattern p = Pattern
                .compile("[a-zA-Z0-9\\-]{10}");
        Matcher m = p.matcher(active_code);
        return m.matches();
    }
}
