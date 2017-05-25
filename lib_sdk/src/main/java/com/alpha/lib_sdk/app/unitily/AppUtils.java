package com.alpha.lib_sdk.app.unitily;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 *
 * App相关辅助类
 * Created by kenway on 17/3/9 18:15
 * Email : xiaokai090704@126.com
 */

public class AppUtils {
   private AppUtils(){

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
     * [获取应用程序版本名称信息]
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
}
