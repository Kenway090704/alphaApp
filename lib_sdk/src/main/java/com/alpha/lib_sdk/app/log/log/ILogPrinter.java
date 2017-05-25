package com.alpha.lib_sdk.app.log.log;

/**
 * 
 * @author AlbieLiang
 * Log打印接口
 *
 */
public interface ILogPrinter {
	/**
	 * 打印log
	 * @param priority
	 * @param tag
	 * @param format
     * @param args
     */
	void printLog(int priority, String tag, String format, Object... args);
    //该方法未调用
	boolean isLoggable(String tag, int priority);

	/**
	 *  获取优先级
	 * @return
     */
	int getPriority();

	/**
	 * 写入log接口,该方法未调用
	 * @return
     */
	ILogWriter getLogWriter();
}
