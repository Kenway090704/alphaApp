package com.alpha.lib_sdk.app.net;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.alpha.lib_sdk.app.log.Log;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by kenway on 17/5/22 14:09
 * Email : xiaokai090704@126.com
 * 请求管理类  okHttpClient
 */

public class RequestManager {

    private static final String TAG = "RequestManager";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static RequestManager mInstance;//单例引用

    public static final int TYPE_GET = 0;//get请求

    public static final int TYPE_POST_JSON = 1;//post请求参数为json

    public static final int TYPE_POST_FORM = 2;//post请求参数为表单

    private OkHttpClient mOkHttpClient;//okHttpClient;

    private Handler okHttpHandler;//全局处理子线程和M线程通信

    private RequestManager(Context context) {
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        okHttpHandler = new Handler(context.getMainLooper());//属于主线程的Handler
    }

    /**
     * 获取网络请求管理类实例
     *
     * @param context
     * @return
     */
    public static RequestManager getInstance(Context context) {
        RequestManager inst = mInstance;
        if (inst == null) {
            synchronized (RequestManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new RequestManager(context.getApplicationContext());
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    public <T> Call requestGetWXData(String actionUrl, final ReqCallBack<T> callBack) {

        try {
            String requestUrl = actionUrl;

            Request request = new Request.Builder().url(actionUrl).get().build();

            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    com.alpha.lib_sdk.app.log.Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        com.alpha.lib_sdk.app.log.Log.e(TAG, "response ----->" + string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });

            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());

        }
        return null;
    }

    /**
     * 获取省市县的get请求
     *
     * @param actionUrl
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Call requestGet(String actionUrl, final ReqCallBack<T> callBack) {

        try {
            String requestUrl = actionUrl;

            Request request = new Request.Builder().url(actionUrl).get().build();

            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    com.alpha.lib_sdk.app.log.Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        com.alpha.lib_sdk.app.log.Log.e(TAG, "response ----->" + string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });

            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());

        }
        return null;
    }


    /**
     * okHttp post异步请求 发送json数据
     *
     * @param actionUrl 接口地址
     * @param json      请求参数json 数据
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> Call requestPostByJsonAsyn(String actionUrl, String json, final ReqCallBack<T> callBack) {
        try {
            Log.e(TAG, "url==" + actionUrl);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(actionUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.e(TAG, "response ----->" + string);

                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }
    /**
     * okHttp post异步请求表单提交
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack 请求返回数据回调
     * @param <T> 数据泛型
     * @return
     */
    public  <T> Call requestPostByAsynWithForm(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                builder.add(key, paramsMap.get(key));
            }
            RequestBody formBody = builder.build();
            String requestUrl = actionUrl;
            Request request = new Request.Builder().url(actionUrl).post(formBody).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }



    /**
     * 统一同意处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onReqSuccess(result);
                }
            }
        });
    }

    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onReqFailed(errorMsg);
                }
            }
        });
    }


}
