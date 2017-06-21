package com.alpha.alphaapp.model;

import com.alpha.lib_sdk.app.log.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kenway on 17/5/24 11:50
 * Email : xiaokai090704@126.com
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
        Pattern p = Pattern
                .compile("^(13[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否是手机验证码
     * 手机验证码也存在问题
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhoneVerify(String mobiles) {
        Pattern p = Pattern
                .compile("\\d{6}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否为qq号
     * @param qq
     * @return
     */
    public static boolean isQQ(String qq) {
        Pattern p = Pattern
                .compile("^[1-9][0-9]{4,9}");
        Matcher m = p.matcher(qq);
        return m.matches();
    }

}
