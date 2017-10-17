package com.alpha.alphaapp.ui.v_1_2.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.v_1_2.web.ui.utils.Navigator;


import java.util.ArrayList;
import java.util.List;

public abstract class AlphaWebView extends WebView {

    private final WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            if (!TextUtils.isEmpty(url) && !Navigator.openStandardLink(webView.getContext(), url)) {
                Navigator.openInBrowser(webView.getContext(), url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            AlphaWebView.this.onPageFinished(url);
            //  html加载完成之后，调用js的方法
            imgReset();
        }

    };

    /**
     *這是一個有表情的帖子<img src="http://120.76.27.29:8090/res/images/emotion/feixia/08.gif" />
     * <img class="J_post_img" src="http://120.76.27.29:8090/attachment/1710/thread/2_40219_d3ca95a54a2de8e.png" border="0" onload="if(this.offsetWidth>700)this.width=700;" style="max-width:700px;" /><img class="J_post_img" src="http://120.76.27.29:8090/attachment/1710/thread/2_40219_ff3ab09c991369c.png" border="0" onload="if(this.offsetWidth>700)this.width=700;" style="max-width:700px;" />'
     */
    private void imgReset() {

        //class==J_post_img的图片使用自适应
        loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName('img'); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "var img = objs[i];"
                + "if(img.className==\"J_post_img\"){img.style.width = '100%';   "
                + "    img.style.height = 'auto'; } "
                + "}" + "})()");
    }


    private boolean darkTheme;
    private List<OnScrollListener> onScrollListenerList;

    public AlphaWebView(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public AlphaWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public AlphaWebView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AlphaWebView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlphaWebView, defStyleAttr, defStyleRes);
        darkTheme = a.getBoolean(R.styleable.AlphaWebView_darkTheme, false);
        a.recycle();

        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(webViewClient);
    }

    public boolean isDarkTheme() {
        return darkTheme;
    }

    protected void onPageFinished(String url) {
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListenerList != null && onScrollListenerList.size() > 0) {
            for (OnScrollListener onScrollListener : onScrollListenerList) {
                onScrollListener.onScrollChanged(l, t, oldl, oldt);
            }
        }
    }

    public void addOnScrollListener(OnScrollListener listener) {
        if (onScrollListenerList == null) {
            onScrollListenerList = new ArrayList<>();
        }
        onScrollListenerList.add(listener);
    }

    public void removeOnScrollListener(OnScrollListener listener) {
        if (onScrollListenerList != null) {
            onScrollListenerList.remove(listener);
        }
    }

    public void clearOnScrollListeners() {
        if (onScrollListenerList != null) {
            onScrollListenerList.clear();
        }
    }

    public interface OnScrollListener {

        void onScrollChanged(int l, int t, int oldl, int oldt);

    }

}
