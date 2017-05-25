
package com.alpha.lib_sdk.app.protocols;

/**
 * 
 * @author AlbieLiang
 *编码格式
 */
public class LocalDataConstants {

	public static final String CHARSET = "UTF-8";
	
	public interface Charset {
		String STRING_CHARSET = CHARSET;
		String JSAPI_CHARSET = CHARSET;
		String URL_CHARSET = CHARSET;
		String XML_CHARSET = CHARSET;
		/**
		 * LOG的编码格式为UTF-8
		 */
		String LOG_CHARSET = CHARSET;
		String HTTP_CHARSET = CHARSET;
		String HTTPS_CHARSET = CHARSET;
	}
}
