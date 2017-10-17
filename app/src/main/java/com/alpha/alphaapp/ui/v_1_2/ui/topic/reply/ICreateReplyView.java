package com.alpha.alphaapp.ui.v_1_2.ui.topic.reply;

import android.support.annotation.NonNull;

import com.alpha.alphaapp.model.v_1_2.bean.ReplyBean;

/**
 * Created by kenway on 17/9/26 14:06
 * Email : xiaokai090704@126.com
 */

public interface ICreateReplyView {

    void showWindow();

    void dismissWindow();

    //回复某一条评论
    void onAt(@NonNull ReplyBean target, @NonNull Integer targetPosition);

    void onContentError(@NonNull String message);

    void onReplyTopicOk(@NonNull ReplyBean reply);

    void onReplyTopicStart();

    void onReplyTopicFinish();
}
