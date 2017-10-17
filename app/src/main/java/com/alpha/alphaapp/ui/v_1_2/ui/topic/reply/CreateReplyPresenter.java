package com.alpha.alphaapp.ui.v_1_2.ui.topic.reply;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.logic.reply.ReplyLogic;

/**
 * Created by kenway on 17/9/26 14:45
 * Email : xiaokai090704@126.com
 */

public class CreateReplyPresenter implements ICreateReplyPresenter {

    private Context context;
    private ICreateReplyView createReplyView;

    public CreateReplyPresenter(Context context, ICreateReplyView createReplyView) {
        this.context = context;
        this.createReplyView = createReplyView;
    }

    @Override
    public void createReplyAsyncTask(@NonNull String topicId, String content, String targetId) {
        if (TextUtils.isEmpty(content)) {
            createReplyView.onContentError(context.getString(R.string.content_empty_error_tip));
        } else {
            final String finalContent;
            //这里可以判断是否要添加小尾巴
            finalContent = content;
            createReplyView.onReplyTopicStart();

            OnModelCallback<Object> callback = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {

                    createReplyView.onReplyTopicOk(null);
                }

                @Override
                public void onModelFailed(String failedMsg) {

                }
            };
            String uid = AccountManager.getInstance().getUid();


            ReplyLogic.sendNewRelpy(topicId, uid, "title", content, callback);
        }


    }
}
