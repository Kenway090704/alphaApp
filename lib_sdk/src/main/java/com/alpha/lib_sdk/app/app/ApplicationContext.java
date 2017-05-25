package com.alpha.lib_sdk.app.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

//import com.bemetoy.bp.sdk.tools.LogUtil;

import java.lang.ref.WeakReference;

/**
 *
 * @author Kenway
 * application ,context上下文对象弱引用
 *
 */
public class ApplicationContext {

	private static final String TAG = "App.ApplicationContext";

	private static WeakReference<Application> sApplicationWeakRef;
	private static WeakReference<Context> sCurrContextWeakRef;

	public static Application getApplication() {
		return sApplicationWeakRef != null ? sApplicationWeakRef.get() : null;
	}

	public static void setApplication(Application application) {
		if (application == null) {
			ApplicationContext.sApplicationWeakRef = null;
			return;
		}
		ApplicationContext.sApplicationWeakRef = new WeakReference<Application>(application);
	}

	public static Context getCurrentContext() {
		return sCurrContextWeakRef != null ? sCurrContextWeakRef.get() : null;
	}

	public static void setCurrentContext(Context context) {
		if (context == null) {
			ApplicationContext.sCurrContextWeakRef = null;
			return;
		}
		ApplicationContext.sCurrContextWeakRef = new WeakReference<Context>(context);
	}

	public static Context getContext() {
		Context c = getCurrentContext();
		if (c == null) {
			Log.e(TAG, "ApplicationContext's currentContext is null.");
			c = getApplication();
		}
		return c;
	}

	/**
	 * 不知道为什么?返回的是"@MAIN"
	 * @param context
	 * @param pid
     * @return
     */
	public static String getPidKey(Context context, int pid) {
		// TODO: 16/3/8 albieliang 
		return "@MAIN";
	}
}
