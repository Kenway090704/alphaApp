package com.alpha.alphaapp.ui.widget.forum;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.TopicBean;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.model.v_1_2.logic.topic.TopicLogic;
import com.alpha.alphaapp.ui.v_1_2.web.ContentWebView;
import com.alpha.lib_sdk.app.log.LogUtils;

/**
 * Created by kenway on 17/9/25 17:43
 * Email : xiaokai090704@126.com
 */

public class TopicHeader extends LinearLayout {
    private Context context;
    private TextView tv_title;
    private TopicUserInfoItem tuii;
    private ContentWebView web;

    public TopicHeader(Context context) {
        super(context);
        this.context = context;

        initViews();
    }


    public TopicHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public TopicHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_topic_header, this);
        tv_title = (TextView) view.findViewById(R.id.cell_topic_content_tv_title);
        tuii = (TopicUserInfoItem) view.findViewById(R.id.cell_topic_content_tuii_userinfo);
        web = (ContentWebView) view.findViewById(R.id.cell_topic_content_web_content);

    }

    /**
     * 更新UI
     *
     * @param bean
     */
    public void updateViews(TopicListBean bean) {

        tv_title.setText(bean.getSubject());
        tuii.setData(bean);
        doWebViewData(web);

        //添加文章内容
        OnModelCallback<TopicBean> call = new OnModelCallback<TopicBean>() {
            @Override
            public void onModelSuccessed(TopicBean topicBean) {
                web.loadRenderedContent(topicBean.getContent());
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        };
        TopicLogic.getTopic(bean.getTid(), call);

    }


    /**
     * 设置WebView不滚动
     */
    private void doWebViewData(WebView web) {
        //去掉滚动条
        web.setHorizontalScrollBarEnabled(false);//水平不显示
        web.setVerticalScrollBarEnabled(false); //垂直不显示

        //声明WebSettings子类
        WebSettings webSettings = web.getSettings();
//
//
////        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应内容大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕大小
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);//可以缩放

// android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            web.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

    }

    public void updateCreateUserInfo(String tid) {
        //添加文章内容
        OnModelCallback<TopicBean> call = new OnModelCallback<TopicBean>() {
            @Override
            public void onModelSuccessed(TopicBean topicBean) {
                tuii.setData(topicBean);
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        };
        TopicLogic.getTopic(tid, call);
    }


}
