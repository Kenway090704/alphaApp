package com.alpha.lib_sdk.app.log;

import android.util.Log;

/**
 * 打印log日志的工具类
 * Created by kenway on 17/3/9 17:12
 * Email : xiaokai090704@126.com
 */

public class LogUtils {

    private LogUtils(){
        throw new UnsupportedOperationException("cannot b instantiated");
    }

    public  static  boolean isDebug=true;//默认是true,发布的时候要将其变为false
    private  static  final  String TAG="alpha";//固定标签

    /**
     * 该方法的tag---alpha
     * @param msg
     */
    public  static  void i(String msg){
        if (isDebug)
            Log.i(TAG,msg);
    }
    /**
     * 该方法的tag---alpha
     * @param msg
     */
    public  static  void d(String msg){
        if (isDebug)
            Log.d(TAG,msg);
    }
    /**
     * 该方法的tag---alpha
     * @param msg
     */
    public  static  void e(String msg){

        if (isDebug)
            Log.e(TAG,msg);

    }
    /**
     * 该方法的tag---alpha
     * @param msg
     */
    public  static  void v(String msg){
        if (isDebug)
            Log.v(TAG,msg);
    }

    public  static  final  void i(String tag,String msg){
        if (isDebug)
            Log.i(tag,msg);
    }
    public  static  final  void d(String tag,String msg){
        if (isDebug)
            Log.d(tag,msg);
    }
    public  static  final  void e(String tag,String msg){
        if (isDebug)
            Log.e(tag,msg);
    }
    public  static  final  void v(String tag,String msg){
        if (isDebug)
            Log.v(tag,msg);
    }
}
