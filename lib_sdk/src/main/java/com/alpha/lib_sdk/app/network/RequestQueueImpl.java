package com.alpha.lib_sdk.app.network;




/**
 * Created by kenway on 17/5/22 12:03
 * Email : xiaokai090704@126.com
 * newwork request  queue
 */

public class RequestQueueImpl implements RequestQueue {
    @Override
    public void addRequest(IRequest request) {

    }

    @Override
    public void cancelRequest(IRequest request) {

    }

    @Override
    public void cancelAllRequest() {

    }

    @Override
    public boolean hasAuthRequestInQueue() {
        return false;
    }

//    private static final String TAG = "network.RequestQueueImpl";
//
//    public static RequestQueueImpl instance = null;
//
//    private com.android.volley.RequestQueue queue;
//
//    private static byte[] lock = new byte[0];
//
//    private volatile boolean hasAuthRequest = false;
//
//    private volatile List<StreamRequest> requestList = new ArrayList<>();
//
//    private RequestQueueImpl(Context context) {
//        queue = Volley.newRequestQueue(context, new HurlStack() {
//            @Override
//            protected HttpURLConnection createConnection(URL url) throws IOException {
//                HttpURLConnection connection = super.createConnection(url);
//                connection.setRequestProperty("Accept-Encoding", "");
//                return connection;
//            }
//        });
//        queue.addRequestFinishedListener(new com.android.volley.RequestQueue.RequestFinishedListener() {
//
//            @Override
//            public void onRequestFinished(Request request) {
//                if (request instanceof StreamRequest) {
//                    StreamRequest resp = (StreamRequest) request;
//                    requestList.remove(request);
//                    LogUtil.i(TAG, "request which cmdId is %d is out of the queue", resp.getIRequest().getCMDId());
//                    synchronized (lock) {
//                        //如果是奥飞登录
////                        if (resp.getIRequset().getCMDId() ==) {
////                            hasAuthRequest = false;
////                        }
//                    }
//                }
//            }
//        });
//        queue.start();
//    }
//
//    /**
//     * get  RequestQueueImpl  obj
//     *
//     * @param context
//     * @return
//     */
//    public static RequestQueueImpl getInstance(Context context) {
//        synchronized (lock) {
//            if (instance == null) {
//                instance = new RequestQueueImpl(context);
//            }
//        }
//        return instance;
//    }
//
//    @Override
//    public void addRequest(IRequest request) {
//        if (Util.isNull(request)) {
//            LogUtil.e(TAG, "request is null");
//            return;
//        }
//        StreamRequest streamRequest = request.getStreamRequest();
//        if (!Util.isNull(queue) && Util.isNullOrNil(streamRequest.getData())) {
//            request.setStreamRequest(streamRequest);
//            synchronized (lock) {
//                //是否是登录命令
//                if (request.getCMDId() ==) {
//                    hasAuthRequest = true;
//                }
//            }
//            requestList.add(streamRequest);
//            queue.add(streamRequest);
//            LogUtil.i(TAG, "request which cmdId is %d enter the queue", request.getCMDId());
//        } else if (Util.isNull(queue)) {
//            LogUtil.e(TAG, "request queue is null");
////        } else if (Util.isNullOrNil(streamRequest.getData())) {
////            LogUtil.e(TAG, "the data of the request is null");
////        }
//    }
//
//    @Override
//    public void cancelRequest(IRequest request) {
//        if (Util.isNull(request) || Util.isNull(request.getStreamRequest())) {
//            LogUtil.e(TAG, "request or streamRequest is null");
//            return;
//        }
//        if (request.getStreamRequest().isCanceled()) {
//            return;
//        }
//        request.getStreamRequest().cancel();
//    }
//
//    @Override
//    public void cancelAllRequest() {
//        if (requestList == null) {
//            return;
//        }
//        Iterator<StreamRequest> iterator = requestList.iterator();
//        while (iterator.hasNext()) {
//            StreamRequest streamRequest = iterator.next();
//            streamRequest.cancel();
//            //可以打印日志查看是否取消
//        }
//    }
//
//    @Override
//    public boolean hasAuthRequestInQueue() {
//        return hasAuthRequest;
//    }
}

