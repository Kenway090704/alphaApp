package com.alpha.alphaapp.ui.v_1_2.ui.topic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.ReplyBean;
import com.alpha.alphaapp.model.v_1_2.logic.reply.ReplyLogic;
import com.alpha.alphaapp.ui.v_1_2.ui.topic.reply.CreateReplyPresenter;
import com.alpha.alphaapp.ui.v_1_2.ui.topic.reply.ICreateReplyView;
import com.alpha.alphaapp.ui.v_1_2.ui.topic.reply.ITopicView;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.List;

/**
 * Created by kenway on 17/9/26 11:23
 * Email : xiaokai090704@126.com
 * <p>
 * 创建帖子评论对话框
 */

public class CreateReplyDialog extends AppCompatDialog implements ICreateReplyView {


    private final CreateReplyPresenter createReplyPresenter;

    public static CreateReplyDialog CreateWithAutoTheme(@NonNull Context context, @NonNull String topicId, @NonNull ITopicView topicView) {
        return new CreateReplyDialog(
                context, R.style.AppDialogLight_TopicReply,
                topicId,
                topicView
        );
    }


    private ImageView btn_tool_close;
    private ImageView btn_tool_send;
    private ImageView btn_clear_target;
    private ViewGroup editorBar;
    private ViewGroup layoutTarget;
    private TextView tvTarget;
    private EditText edtContent;

    private String topicId;
    private String targetId = null;

    private ITopicView topicView;

    private CreateReplyDialog(@NonNull Context context, @StyleRes int theme, @NonNull String topicId, @NonNull ITopicView topicView) {
        super(context, theme);
        setContentView(R.layout.dialog_create_reply);
        this.topicId = topicId;
        this.topicView = topicView;
        initViews();
        initEvents();


        createReplyPresenter = new CreateReplyPresenter(context, this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
    }

    private void initViews() {
        editorBar = (ViewGroup) findViewById(R.id.layout_editor_bar);
        layoutTarget = (ViewGroup) findViewById(R.id.layout_target);
        tvTarget = (TextView) findViewById(R.id.tv_target);
        edtContent = (EditText) findViewById(R.id.edt_content);
        btn_tool_close = (ImageView) findViewById(R.id.btn_tool_close);
        btn_tool_send = (ImageView) findViewById(R.id.btn_tool_send);
        btn_clear_target = (ImageView) findViewById(R.id.btn_clear_target);
    }

    private void initEvents() {
        btn_tool_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissWindow();
            }
        });

        btn_tool_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送评论
                createReplyPresenter.createReplyAsyncTask(topicId, edtContent.getText().toString().trim(), targetId);

            }
        });
        btn_clear_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetId = null;
                layoutTarget.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void showWindow() {
        show();
    }

    @Override
    public void dismissWindow() {
        dismiss();
    }


    @Override
    public void onAt(@NonNull ReplyBean target, @NonNull Integer targetPosition) {
        //回复某一条评论
        targetId = target.getTid();
        layoutTarget.setVisibility(View.VISIBLE);
        tvTarget.setText(getContext().getString(R.string.reply_$d_floor, targetPosition + 1));
        edtContent.getText().insert(edtContent.getSelectionEnd(), "@" + target.getCreated_username() + " ");
        showWindow();
    }

    @Override
    public void onContentError(@NonNull String message) {
        ToastUtils.showToast(getContext(), message);
        edtContent.requestFocus();

    }

    @Override
    public void onReplyTopicOk(@NonNull ReplyBean reply) {
        //回复成功后执行的内容
        LogUtils.e("评论发送成功");
        OnModelCallback<List<ReplyBean>> callback = new OnModelCallback<List<ReplyBean>>() {
            @Override
            public void onModelSuccessed(List<ReplyBean> replyBeans) {

                //更新Topic的UI
                topicView.appendReplyAndUpdateViews(replyBeans);
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        };
        ReplyLogic.getReplyList(topicId, 0, callback);





        dismissWindow();
        targetId = null;
        layoutTarget.setVisibility(View.GONE);
        edtContent.setText(null);
        ToastUtils.showToast(getContext(), ResourceUtil.resToStr(getContext(), R.string.post_success));
    }

    @Override
    public void onReplyTopicStart() {

    }

    @Override
    public void onReplyTopicFinish() {

    }
}
