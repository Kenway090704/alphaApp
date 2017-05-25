package com.alpha.lib_sdk.app.log.thread;

import com.alpha.lib_sdk.app.core.thread.ArgsTransferHelper;

/**
 * 
 * @author AlbieLiang
 *
 */
public abstract class TransferArgsRunnable extends ArgsTransferHelper implements
		Runnable {
	public TransferArgsRunnable(Object... args) {
		super(args);
	}
}
