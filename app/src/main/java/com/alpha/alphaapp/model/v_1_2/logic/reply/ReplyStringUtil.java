package com.alpha.alphaapp.model.v_1_2.logic.reply;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/9/26 17:53
 * Email : xiaokai090704@126.com
 * 评论内容处理工具
 */

public class ReplyStringUtil {
    /**
     *
     */
    public static final String BLOCK_START = "<span class=\"fl\">";
    public static final String BLOCK_END = "</span></blockquote>";


    /**
     * 使用TextView 显示Html时,让超链接有点击事件,跳转到默认的浏览器显示该网页。。
     * @param context
     * @param tv
     */
    public static void textHtmlClick(Context context, TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();

        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) text;
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();//should clear old spans
            for (URLSpan url : urls) {
                MyURLSpan myURLSpan =new MyURLSpan(url.getURL(), context);
                style.setSpan(myURLSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }

            tv.setText(style);

        }

    }

    private static class MyURLSpan extends ClickableSpan {
        private String mUrl;
        private Context mContext;

        MyURLSpan(String url, Context context) {
            mContext = context;
            mUrl = url;
        }

        @Override
        public void onClick(View widget) {

            //点击跳转到网页显示

        }
    }


}
