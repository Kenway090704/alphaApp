package com.alpha.lib_sdk.app.net;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
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

    /**
     * 同步请求统一入口
     *
     * @param actionUrl
     * @param requestType
     * @param json
     */
    public void requestSyn(String actionUrl, int requestType, String json) {
        switch (requestType) {
            case TYPE_GET:
                //get请求
                break;
            case TYPE_POST_JSON:
                //post请求 发送json数据
                requsetPostByJSONSyn(actionUrl, json);
                break;

            case TYPE_POST_FORM:
                //post请求 发送表单
                break;
        }
    }

    /**
     * okHttp post同步请求 发送json字段
     *
     * @param actionUrl
     * @param json
     */
    public void requsetPostByJSONSyn(String actionUrl, String json) {


        try {
            //创建一个请求实体对象 RequestBody
            RequestBody body = RequestBody.create(JSON, json);
            //创建一个请求
            Request request = new Request.Builder().url(actionUrl).post(body).build();
            //在这里加入请求队列
            //创建一个call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            if (response.isSuccessful()) {
                //获取返回数据,可以是String,bytes,byteStream
                Log.e(TAG, "response--->" + response.body().string());
            } else {
                Log.e(TAG, "response-->failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
