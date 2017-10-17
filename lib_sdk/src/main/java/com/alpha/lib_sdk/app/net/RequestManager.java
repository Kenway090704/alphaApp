package com.alpha.lib_sdk.app.net;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alpha.lib_sdk.app.log.LogUtils;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by kenway on 17/5/22 14:09
 * Email : xiaokai090704@126.com
 * 请求管理类  okHttpClient
 */

public class RequestManager {

    private static final String TAG = "RequestManager";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");//mdiatype 这个需要和服务端保持一致 你需要看下你们服务器设置的ContentType 是不是这个，他们设置的是哪个 我们要和他们保持一致

    private static RequestManager mInstance;//单例引用


    private OkHttpClient mOkHttpClient;//okHttpClient;

    private Handler okHttpHandler;//全局处理子线程和M线程通信

    private RequestManager(Context context) {
        //初始化OkHttpClient
        mOkHttpClient = gettUnsafeOkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        okHttpHandler = new Handler(context.getMainLooper());//属于主线程的Handler
    }

    private static OkHttpClient gettUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                    LogUtils.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        LogUtils.e(TAG, "response ----->" + string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });

            return call;
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());

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
            final Request request = new Request.Builder().url(actionUrl).get().build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    LogUtils.e(TAG, "这里发生了错误==" + e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        LogUtils.e(TAG, "response ----->" + string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());

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
            LogUtils.e(TAG, "url==" + actionUrl);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(actionUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    LogUtils.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        LogUtils.e(TAG, "response ----->" + string);

                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return null;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> Call requestPostByAsynWithForm(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
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
                    LogUtils.e(TAG, e.toString());
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
            LogUtils.e(TAG, e.toString());
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
     * okHttp post异步上传文件。带参数
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> Call uploadFile(String actionUrl, HashMap<String, Object> paramsMap, final ReqCallBack<T> callBack) {
        try {

            //带参数上传文件
            MultipartBody.Builder builder_pic = new MultipartBody.Builder();
            builder_pic.setType(MultipartBody.FORM);

            LogUtils.e(paramsMap.toString());
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder_pic.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder_pic.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                }
            }

            //创建RequestBody
            RequestBody body = builder_pic.build();

            //创建Request
            final Request request = new Request.Builder().url(actionUrl).post(body).build();


            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    LogUtils.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        LogUtils.e("onResponse--->"+string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("服务器错误", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return null;
    }

    /**
     * 带参数带进度上传文件
     * @param actionUrl
     * @param paramsMap
     * @param callBack
     * @param <T>
     */
    public  <T>  void  upLoadFileHasProgress  (String actionUrl, HashMap<String, Object> paramsMap, final ReqProgressCallBack<T> callBack){
        try {
            //补全请求地址

            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_OBJECT_STREAM, file, callBack));
                }
            }
            //创建RequestBody
            RequestBody body = builder.build();
            //创建Request
            final Request request = new Request.Builder().url(actionUrl).post(body).build();
            final Call call = mOkHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtils.e( e.toString());
                    failedCallBack("上传失败", callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        LogUtils.e( "response ----->" + string);
                        successCallBack((T) string, callBack);
                    } else {
                        failedCallBack("上传失败", callBack);
                    }
                }
            });
        } catch (Exception e) {
            LogUtils.e( e.toString());
        }


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

    public  <T>  RequestBody createProgressRequestBody(final  MediaType contentType,final File file, final ReqProgressCallBack<T> callBack){
        return  new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() throws IOException {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                       progressCallBack(remaining, current, callBack);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    /**
     * 统一处理进度信息
     * @param total    总计大小
     * @param current  当前进度
     * @param callBack
     * @param <T>
     */
    private <T> void progressCallBack(final long total, final long current, final ReqProgressCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onProgress(total, current);
                }
            }
        });
    }





}
