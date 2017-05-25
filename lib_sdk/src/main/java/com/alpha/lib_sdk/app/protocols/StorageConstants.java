package com.alpha.lib_sdk.app.protocols;

import android.os.Environment;

import java.io.File;

/**
 * Created by kenway on 17/5/22 18:28
 * Email : xiaokai090704@126.com
 */

public class StorageConstants {


    public interface SDCard {
        //File.separator 实际上等同于一个 '/'
        String STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
        String APP_STORAGE_ROOT = STORAGE_ROOT + File.separator + ProtocolConstants.APP_NAME;
        String LOG_STORAGE_DIR = APP_STORAGE_ROOT + File.separator + "log";
        String CRASH_STORAGE_DIR = APP_STORAGE_ROOT + File.separator + "crash";
    }
}
