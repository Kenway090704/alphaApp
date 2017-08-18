package com.alpha.lib_sdk.app.tool;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.EditText;

import com.alpha.lib_sdk.app.log.LogUtils;

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
                LogUtils.e("xk", "错误位置" + i + ",str1=" + a1[i - 2] + a1[i - 1] + a1[i] + ",str2=" + b1[i - 2] + b1[i - 1] + b1[i]);
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


       //将中文替换为连个英文字符,这样就实现了一个中文为两个字节的问题
        String regEx = "[\\u4e00-\\u9fa5]";
        name = name.replaceAll(regEx, "aa");

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

        //将中文替换为连个英文字符,这样就实现了一个中文为两个字节的问题
        String regEx = "[\\u4e00-\\u9fa5]";
        name = name.replaceAll(regEx, "aa");

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
                .compile("[1-9]\\d{5}");
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
       //当昵称小于四个字节的时候如果是中文,一个中午两个字节
        String regEx = "[\\u4e00-\\u9fa5]";
        String name = nickName.replaceAll(regEx, "aa");

        //字符串中有多少个中文
        int count = name.length()-nickName.length();
        int size = name.length();
        if (size > 4 && size < 12) {
            return nickName;
        } else if (size < 4) {
            return "奥飞用户" + nickName;
        } else {
            //如果昵称大于12个,可能需要重新判断,给出对应的值
            return nickName;
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

    /**
     * 从一个联系地址中获取省市区的字符串
     *
     * @param contact_addr
     * @return
     */
    public static String getPCAString(String contact_addr) {
        contact_addr = contact_addr.trim();
        //判断有没有区
        int index = contact_addr.indexOf("区");
        if (index != -1) {
            contact_addr = contact_addr.substring(0, index + 1);
            return contact_addr;
        }
        index = contact_addr.indexOf("县");
        if (index != -1) {
            contact_addr = contact_addr.substring(0, index + 1);
            return contact_addr;
        }

        index = contact_addr.indexOf("市");
        if (index != -1) {
            contact_addr = contact_addr.substring(0, index + 1);
            return contact_addr;
        }

        index = contact_addr.indexOf("旗");
        if (index != -1) {
            contact_addr = contact_addr.substring(0, index + 1);
            return contact_addr;
        }

        index = contact_addr.indexOf("镇");
        if (index != -1) {
            contact_addr = contact_addr.substring(0, index + 1);
            return contact_addr;
        }

        return "";

    }

    /**
     * 从一个联系地址中获取区以下的详细信息
     *
     * @param contact_addr
     * @return
     */
    public static String getDetailAddrString(String contact_addr) {
        contact_addr = contact_addr.trim();
        //判断有没有区

        int index = contact_addr.indexOf("区");
        if (index != -1) {
            contact_addr = contact_addr.substring(index + 1);
            return contact_addr;
        }

        index = contact_addr.indexOf("县");
        if (index != -1) {
            contact_addr = contact_addr.substring(index + 1);
            return contact_addr;
        }

        index = contact_addr.indexOf("市");
        if (index != -1) {
            contact_addr = contact_addr.substring(index + 1);
            return contact_addr;
        }

        index = contact_addr.indexOf("旗");
        if (index != -1) {
            contact_addr = contact_addr.substring(index + 1);
            return contact_addr;
        }

        index = contact_addr.indexOf("镇");
        if (index != -1) {
            contact_addr = contact_addr.substring(index + 1);
            return contact_addr;
        }
        return "";
    }

    /**
     * 输入框中的文字,如果有括号的将文字变小为正常的0.8
     * @param normal
     * @param change
     * @param et
     */

    public static  void  setHintTxtBySpannableString(String normal, String change, EditText et){
        SpannableString spanText = new SpannableString(normal+change);
        spanText.setSpan(new RelativeSizeSpan(0.8f), normal.length(), spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        et.setHint(spanText);
    }



}
