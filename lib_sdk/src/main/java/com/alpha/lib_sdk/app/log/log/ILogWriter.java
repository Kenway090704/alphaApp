package com.alpha.lib_sdk.app.log.log;

/**
 * 写日志的接口
 * boolean start(String dir, String fileName, int pid, long mainTid, long msec);
 * void shutdown();
 * void writeLog(long tid, long msec, int priority, String tag, String log);
 * @author AlbieLiang
 *
 */
public interface ILogWriter {

	/**
	 * 开始创建一个文件名包含线程信息的log日志文件
	 * @param dir  log日志存储位置
	 * @param fileName   存储的文件名
	 * @param pid        线程
	 * @param mainTid      线程ID
	 * @param msec        时间
     * @return
     */
	boolean start(String dir, String fileName, int pid, long mainTid, long msec);

    /**
	 * 关闭上面创建的文件
	 */
	void shutdown();

	/**
	 * 将异常信息写入log日志文件
	 * @param tid
	 * @param msec
	 * @param priority
	 * @param tag
     * @param log
     */
	void writeLog(long tid, long msec, int priority, String tag, String log);
}