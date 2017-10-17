package com.alpha.alphaapp.ui.v_1_2.ui.topic.reply;

import android.support.annotation.NonNull;

/**
 * Created by kenway on 17/9/26 14:29
 * Email : xiaokai090704@126.com
 */

public interface ICreateReplyPresenter {

    void createReplyAsyncTask(@NonNull String topicId, String content, String targetId);
}
