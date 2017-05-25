package com.alpha.alphaapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
public class WebActivity extends AppCompatActivity {
    public  static  final String EXTRA_URL="url";
    private ImageView ivBack,ivShare;
    private TextView tvTitle;
    private ProgressBar progressBar;
    private WebView  webView;
    private  String url;

    private WebViewClient webViewClient= new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {
           //获取网页的标题
            String title=view.getTitle();
            tvTitle.setText(title);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    private WebChromeClient webChromeClient= new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            super.onProgressChanged(view, newProgress);

            if(newProgress==100){
                progressBar.setVisibility(View.INVISIBLE);
            }else {
                progressBar.setProgress(newProgress);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        ivBack= (ImageView) findViewById(R.id.web_back_iv);
        ivShare= (ImageView) findViewById(R.id.web_share_ic);
        tvTitle= (TextView) findViewById(R.id.web_title);
        progressBar = (ProgressBar) findViewById(R.id.web_pb);
        webView= (WebView) findViewById(R.id.web_content_wv);



        //===========================监听事件==================================//
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回的方法
//                onBackPressed();
                finish();
            }
        });
        url = getIntent().getStringExtra(EXTRA_URL);
        Log.e("tag","webactivity================"+url);
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
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

}
