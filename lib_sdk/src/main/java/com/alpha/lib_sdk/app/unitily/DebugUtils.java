package com.alpha.lib_sdk.app.unitily;

/**
 * 可以获取测试打印的的日志行位置和方法名
 * Created by kenway on 17/3/9 17:22
 * Email : xiaokai090704@126.com
 */

public class DebugUtils {
    /**
     * 获取打印的日志line,须传入new Exception()
     * @param e
     * @return line--num
     */
    public static String line(Exception e){
        StackTraceElement[] traceElements=e.getStackTrace();
        if (traceElements==null||traceElements.length==0)
            return "null ,";
        return "[line--"+traceElements[0].getLineNumber()+"]";
    }

    /**
     * 获取打印的日志的fun,须传入new Exception()
     * @param e
     * @return
     */
    public static String fun(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null)
            return ""; //
        return "[fun--"+trace[0].getMethodName()+"]";
    }

    /**
     * 获取打印Log的方法与行数
     * @param e  必须为new Exception()
     * @return
     */
    public  static  String funOrLine(Exception e){
        StringBuffer sb= new StringBuffer();
        StackTraceElement[] trace=e.getStackTrace();
        if (trace==null||trace.length==0)
            return "null ,";
           sb.append("[******function:").append(trace[0].getMethodName())
              .append("(),line:").append(trace[0].getLineNumber()).append("******]");
        return sb.toString();
    }
}
