package com.alpha.lib_sdk.app.unitily;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

/**
 *
 * App相关辅助类
 * Created by kenway on 17/3/9 18:15
 * Email : xiaokai090704@126.com
 */

public class ApkUtils {
   private ApkUtils(){

       /* cannot be instantiated */
       throw new UnsupportedOperationException("cannot be instantiated");
   }
    /**
     * 获取应用程序名称
     */
    public  static  String getAppName(Context context){
        try {
            PackageManager manager= context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
            int labeRes= info.applicationInfo.labelRes;
            return  context.getResources().getString(labeRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get App versionName
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get App versionCode
     * @param context
     * @return   当前应用的版本Code
     */
    public static int getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 得到安装的intent
     * @param apkFile
     * @return
     */
    public static Intent getInstallIntent(File apkFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkFile.getAbsolutePath())),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }


}
