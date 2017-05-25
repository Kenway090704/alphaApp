package com.alpha.lib_sdk.app.log;


import com.alpha.lib_sdk.app.log.log.DefaultLogPrinter;
import com.alpha.lib_sdk.app.log.log.ILogPrinter;

/**
 * A Log tools. Invoked {@link #setLogPrinter(ILogPrinter)} to set a
 * {@link ILogPrinter} to do a real Log action.
 *
 * @author AlbieLiang
 */
public  class Log {

    public static final int VERBOSE = android.util.Log.VERBOSE;
    public static final int DEBUG = android.util.Log.DEBUG;
    public static final int INFO = android.util.Log.INFO;
    public static final int WARN = android.util.Log.WARN;
    public static final int ERROR = android.util.Log.ERROR;
    public static final int ASSERT = android.util.Log.ASSERT;

    private static ILogPrinter sLogPrinter = new DefaultLogPrinter();

    public static boolean setLogPrinter(ILogPrinter printer) {
        if (printer == null) {
            return false;
        }
        sLogPrinter = printer;
        return true;
    }

    /**
     * Info log
     *
     * @param format
     * @param args
     */

    public static void i(String tag, String format, Object... args) {
        sLogPrinter.printLog(INFO, tag, format, args);
    }

    /**
     * error log
     *
     * @param format
     * @param args
     */

    public static void e(String tag, String format, Object... args) {
        sLogPrinter.printLog(ERROR, tag, format, args);
    }

    /**
     * Warming
     *
     * @param tag
     * @param format
     * @param args
     */


    public static void w(String tag, String format, Object... args) {
        sLogPrinter.printLog(WARN, tag, format, args);
    }

    /**
     * Description
     *
     * @param tag
     * @param format
     * @param args
     */

    public static void d(String tag, String format, Object... args) {
        sLogPrinter.printLog(DEBUG, tag, format, args);
    }

    /**
     * @param tag
     * @param format
     * @param args
     */
    public static void v(String tag, String format, Object... args) {
        sLogPrinter.printLog(VERBOSE, tag, format, args);
    }

    /**
     * @param priority
     * @param tag
     * @param format
     * @param args
     */
    public static void printLog(int priority, String tag, String format, Object... args) {
        sLogPrinter.printLog(priority, tag, format, args);
    }


    public static String formatTag(Object o) {
        if (o == null) {
            return null;
        }
        return formatTag(o.getClass());
    }

    public static String formatTag(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return clazz.getSimpleName();
    }
}
