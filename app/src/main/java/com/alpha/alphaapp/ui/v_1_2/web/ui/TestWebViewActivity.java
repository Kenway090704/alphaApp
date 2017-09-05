package com.alpha.alphaapp.ui.v_1_2.web.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.HomeActivity;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_2.web.ContentWebView;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;

/**
 * Created by kenway on 17/9/4 17:23
 * Email : xiaokai090704@126.com
 */

public class TestWebViewActivity extends BaseActivity {
    ContentWebView webview;

    TextView tv;
    private String url;

    private  String urldata="<div class=\"markdown-text\">\n" +
            "                                                                    <p>本来提供开发api，目的是为了开发第三方应用或客户端，如果大家用来学习也是好的，但现在很多人太过分了，随意发帖，at，严重影响了社区的用户，故而决定开始严查</p> \n" +
            "                                                                    <p>以下情况，直接封号</p> \n" +
            "                                                                    <ul> \n" +
            "                                                                     <li>测试标题</li> \n" +
            "                                                                     <li>无任何内容</li> \n" +
            "                                                                     <li>无意义回复</li> \n" +
            "                                                                     <li>测试帖，5分钟内没有删除</li> \n" +
            "                                                                    </ul> \n" +
            "                                                                    <p>欢迎大家监督</p> \n" +
            "                                                                    <p>封号</p> \n" +
            "                                                                    <ul> \n" +
            "                                                                     <li><a href=\"https://cnodejs.org/user/Mwangzhi\">https://cnodejs.org/user/Mwangzhi</a></li> \n" +
            "                                                                     <li><a href=\"https://cnodejs.org/user/lw6395\">https://cnodejs.org/user/lw6395</a></li> \n" +
            "                                                                     <li><a href=\"https://cnodejs.org/user/shengliang74\">https://cnodejs.org/user/shengliang74</a> 竟然挑衅，发帖说你来打我呀。。。。</li> \n" +
            "                                                                     <li><a href=\"https://cnodejs.org/user/h5-17\">https://cnodejs.org/user/h5-17</a> <a href=\"https://cnodejs.org/user/h5-17\">@h5-17</a></li> \n" +
            "                                                                     <li><a href=\"https://cnodejs.org/user/592php\">https://cnodejs.org/user/592php</a> <a href=\"https://cnodejs.org/user/592php\">@592php</a></li> \n" +
            "                                                                    </ul>  \n" +
            "                                                                    <p>20170601更新</p> \n" +
            "                                                                    <p><a href=\"https://cnodejs.org/?tab=dev\">https://cnodejs.org/?tab=dev</a> 目前开了一个『客户端测试』专区，以后开发新客户端的同学，帖子直接发到这个专区去。tab 的值是 dev。</p> \n" +
            "                                                                    <p><img src=\"https://dn-cnode.qbox.me/FundjyBuYk60yqQ-PdKstrPKY-7-\" alt=\"image.png\"></p> \n" +
            "                                                                   </div>";

    @Override
    protected int getLayoutId() {
        url = getIntent().getStringExtra("url");
        return R.layout.activity_webtest;
    }

    @Override
    protected void initView() {
        tv= (TextView) findViewById(R.id.webtest_tv);
      webview = (ContentWebView) findViewById(R.id.webtest_webview);
    }

    @Override
    public void initData() {

        ReqCallBack<String> callBack = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

                int start = result.indexOf("<div class=\"container\">");
                int end = result.indexOf("<div class=\"read-operate\">");
//             <div class="container">---<div class="operate-one">

              String   data=result.substring(start,end);
//                tv.setText(data);
                //将result中的<body>数据取出
                webview.loadRenderedContent(urldata);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(this).requestGet(url, callBack);
    }

    @Override
    protected void initListener() {

    }

    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     * @param url
     */
    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, TestWebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);


    }
}
