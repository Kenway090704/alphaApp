package com.alpha.alphaapp.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alpha.alphaapp.R;


/**
 * Created by HP on 2016/8/25.
 */
public class WebActivity extends BaseActivity {
    public static final String EXTRA_URL = "http://club.auldey.com";
    private ImageView ivBack, ivShare;
    private TextView tvTitle;
    private ProgressBar progressBar;
    private WebView webView;
    private String url;

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            //获取网页的标题
            String title = view.getTitle();
            tvTitle.setText(title);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            super.onProgressChanged(view, newProgress);

            if (newProgress == 100) {
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                progressBar.setProgress(newProgress);
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.web_back_iv);
        ivShare = (ImageView) findViewById(R.id.web_share_ic);
        tvTitle = (TextView) findViewById(R.id.web_title);
        progressBar = (ProgressBar) findViewById(R.id.web_pb);
        webView = (WebView) findViewById(R.id.web_content_wv);


        //===========================监听事件==================================//
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回的方法
//                onBackPressed();
                finish();
            }
        });
//        url = getIntent().getStringExtra(EXTRA_URL);
        url = getIntent().getStringExtra("url");
        Log.e("tag", "webactivity================" + url);
        WebSettings settings = webView.getSettings();
        //允许运行脚本语言
        settings.setJavaScriptEnabled(true);
        //添加供web使用的接口
//        webView.addJavascriptInterface(new PlayInterface(),"playinterface1");
//        webView.addJavascriptInterface(new Play2Interface(),"playinterface2");

        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
        webView.loadUrl(url);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }


    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
