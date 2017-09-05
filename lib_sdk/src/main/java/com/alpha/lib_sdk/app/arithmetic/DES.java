package com.alpha.lib_sdk.app.arithmetic;

import android.util.Base64;

import com.alpha.lib_sdk.BuildConfig;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by kenway on 17/8/22 12:05
 * Email : xiaokai090704@126.com
 */

public class DES {
//    /**
//     *加密
//     **/
//    private String encryptPassword(String clearText) {
//        try {
//            DESKeySpec keySpec = new DESKeySpec(
//                    BuildConfig.PASSWORD_ENC_SECRET.getBytes("UTF-8"));
//            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//            SecretKey key = keyFactory.generateSecret(keySpec);
//
//            Cipher cipher = Cipher.getInstance("DES");
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            String encrypedPwd = Base64.encodeToString(cipher.doFinal(clearText
//                    .getBytes("UTF-8")), Base64.DEFAULT);
//            return encrypedPwd;
//        } catch (Exception e) {
//        }
//        return clearText;
//    }
//
//    /**
//     *解密
//     **/
//    private String decryptPassword(String encryptedPwd) {
//        try {
//            DESKeySpec keySpec = new DESKeySpec(BuildConfig.PASSWORD_ENC_SECRET.getBytes("UTF-8"));
//            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//            SecretKey key = keyFactory.generateSecret(keySpec);
//
//            byte[] encryptedWithoutB64 = Base64.decode(encryptedPwd, Base64.DEFAULT);
//            Cipher cipher = Cipher.getInstance("DES");
//            cipher.init(Cipher.DECRYPT_MODE, key);
//            byte[] plainTextPwdBytes = cipher.doFinal(encryptedWithoutB64);
//            return new String(plainTextPwdBytes);
//        } catch (Exception e) {
//        }
//        return encryptedPwd;
//    }
}
