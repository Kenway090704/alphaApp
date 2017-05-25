package com.alpha.lib_sdk.app.network;

import android.app.DownloadManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by kenway on 17/5/22 11:40
 * Email : xiaokai090704@126.com
 */

public class StreamRequest extends Request<Response> implements Response.Listener<Response> {
    private static final String TAG = "network.StreamRequest";
    private IRequest mRequest;
    private byte[] data;  //请求发送的数据

    public StreamRequest(IRequest request) {
        super(Method.POST, request.getURL(), request.getResponseCallBack() != null ? new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //判断错误信息
            }
        } : null);
        mRequest = request;
//        data = PacketManager.packToJson(request);//将rquest中的数据打包为json
        setRetryPolicy(new DefaultRetryPolicy());
    }

    @Override
    protected Response<Response> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(Response response) {

    }

    @Override
    public void onResponse(Response response) {

    }
}
