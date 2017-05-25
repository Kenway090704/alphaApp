package com.alpha.lib_sdk.app.tool;

import com.alpha.lib_sdk.app.log.Log;

import java.util.Random;

/**
 * Created by kenway on 17/5/22 16:40
 * Email : xiaokai090704@126.com
 */

public class ValueOptUtils {

    private static final String TAG = "Util.ValueOptUtils";


    /**
     * Generate a random string.
     *
     * @param length
     * @param src
     * @return
     */
    public static final String genRandomString(int length, String src) {
        if (Util.isNullOrNil(src) || length <= 0) {
            Log.e(TAG, "Target string length(%d) or source string(%s) is illegaled.", length, src);
            return null;
        }
        int count = src.length();
        int index;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            index = random.nextInt(count);
            builder.append(src.charAt(index));
        }
        return builder.toString();
    }

    /**
     * Chain an string array with the given separator.
     *
     * @param separator
     * @param name
     * @return
     *

     * @see #join(String, Object...)
     */
    public static String chain(String separator, String... name) {
        return join(separator, (Object[]) name);
    }

    /**
     * Join the object array with the given separator to a string.
     *
     * @param separator
     * @param name
     * @return
     */
    public static String join(String separator, Object...name) {
        if (name == null || name.length == 0) {
            return null;
        }
        final String sep = Util.nullAsNil(separator);
        StringBuilder builder = new StringBuilder();
        builder.append(name[0]);
        for (int i = 1; i < name.length; i++) {
            builder.append(sep);
            builder.append(name[i]);
        }
        return builder.toString();
    }
    /**
     * Generate a random string base on
     * 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'.
     *
     * @param length
     * @return
     *
     * @see #genRandomString(int, String)
     */
    public static final String genRandomString(int length) {
        return genRandomString(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /**
     * Generate a random JavaScript variable string.
     *
     * @param length
     * @return
     */
    public static final String genRandomJsVariable(int length) {
        String str = "_$abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int count = str.length();
        int index;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        index = random.nextInt(count - 10);
        builder.append(str.charAt(index));
        for (int i = 1; i < length; i++) {
            index = random.nextInt(count);
            builder.append(str.charAt(index));
        }
        return builder.toString();
    }

    /**
     * Returns a pseudo-random uniformly distributed int in the range [min, max].
     * @param min
     * @param max
     * @return
     */
    public static final int getRandomInt(int min, int max) {
        if (max < min) {
            return min;
        }
        Random random = new Random();
        return random.nextInt(max - min + 2) + min;
    }

    private static final char[] HEX_SEEDS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


    /**
     * Convert bytes to hex.
     *
     * @param bytes
     * @return
     */
    public static String bytes2Hex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        int len = bytes.length;
        char[] str = new char[len * 2];
        int k = 0;
        for (int i = 0; i < len; i++) {
            byte b = bytes[i];
            str[k++] = HEX_SEEDS[b >>> 4 & 0x0f];
            str[k++] = HEX_SEEDS[b & 0x0f];
        }
        return new String(str);
    }

    /**
     * Returns a localized formatted string, using the supplied format and
     * arguments, using the user's default locale.
     *
     * @param format
     * @param args
     * @return
     * @see String#format(String, Object...)
     */
    public static String format(String format, Object... args) {
        String msg = format;
        if (!Util.isNullOrNil(format)) {
            msg = (args == null || args.length == 0) ? format : String.format(format, args);
        }
        return msg;
    }

}
