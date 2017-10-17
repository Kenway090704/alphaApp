package com.alpha.alphaapp.ui.v_1_2.ui.topic.reply;

import android.support.annotation.NonNull;

import com.alpha.alphaapp.model.v_1_2.bean.ReplyBean;

import java.util.List;

/**
 * Created by kenway on 17/9/26 14:20
 * Email : xiaokai090704@126.com
 */

public interface ITopicView {

    void onGetTopicOk(@NonNull Object topic);

    void onGetTopicFinish();

    void appendReplyAndUpdateViews(@NonNull List<ReplyBean> replyBeans);
}
