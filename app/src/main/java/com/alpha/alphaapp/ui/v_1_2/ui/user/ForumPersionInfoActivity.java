package com.alpha.alphaapp.ui.v_1_2.ui.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.model.v_1_2.logic.user.AttentionLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/10/16 10:31
 * Email : xiaokai090704@126.com
 * <p>
 * 论坛个人信息页
 */

public class ForumPersionInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TOPICBEAN = "topicbean";
    private ImageView iv_avatar;
    private TextView tv_userName, tv_rank, tv_add_attent;
    private TextView tv_message, tv_sign;

    private LinearLayout layout_userTopics, layout_reply, layout_like, layout_fans, layout_attention;
    private TopicListBean bean;

    private boolean isAttention;

    @Override
    protected int getLayoutId() {
        bean = (TopicListBean) getIntent().getSerializableExtra(TOPICBEAN);
        return R.layout.activity_forum_persion_info;
    }

    @Override
    protected void initView() {
        iv_avatar = (ImageView) findViewById(R.id.acty_forum_person_info_iv_avatar);
        tv_userName = (TextView) findViewById(R.id.acty_forum_person_info_tv_username);
        tv_rank = (TextView) findViewById(R.id.acty_forum_person_info_tv_rank);
        tv_add_attent = (TextView) findViewById(R.id.acty_forum_person_info_tv_add_attent);
        tv_message = (TextView) findViewById(R.id.acty_forum_person_info_tv_message);
        tv_sign = (TextView) findViewById(R.id.acty_forum_person_info_tv_sign);

        layout_userTopics = (LinearLayout) findViewById(R.id.acty_forum_person_info_layout_themes);
        layout_reply = (LinearLayout) findViewById(R.id.acty_forum_person_info_layout_reply);
        layout_like = (LinearLayout) findViewById(R.id.acty_forum_person_info_layout_like);
        layout_fans = (LinearLayout) findViewById(R.id.acty_forum_person_info_layout_fans);
        layout_attention = (LinearLayout) findViewById(R.id.acty_forum_person_info_layout_attention);
    }

    @Override
    public void initData() {
        ImageLoader.load(this, bean.getIcon(), iv_avatar);
        tv_userName.setText(bean.getAuthor());
    }

    @Override
    protected void initListener() {
        tv_add_attent.setOnClickListener(this);
        tv_message.setOnClickListener(this);
        layout_userTopics.setOnClickListener(this);
        layout_reply.setOnClickListener(this);
        layout_like.setOnClickListener(this);
        layout_fans.setOnClickListener(this);
        layout_attention.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.acty_forum_person_info_tv_add_attent:
                //添加关注
                addAttenttion();
                break;
            case R.id.acty_forum_person_info_tv_message:
                //写私信
                SendMessageActivity.actionStart(v.getContext(), bean);
                break;
            case R.id.acty_forum_person_info_layout_themes:
                //进入TA的主题页面
                UserTopicsActivtiy.actionStart(v.getContext(), bean.getAuthorid());
                break;
            case R.id.acty_forum_person_info_layout_reply:
                //TA的回复
                UserReplyActivtiy.actionStart(this, bean.getAuthorid());
                break;
            case R.id.acty_forum_person_info_layout_like:

                UserLikeActivtiy.actionStart(this, bean.getAuthorid());
                break;
            case R.id.acty_forum_person_info_layout_fans:
                UserFansActivtiy.actionStart(this, bean.getAuthorid());
                break;
            case R.id.acty_forum_person_info_layout_attention:
                UserAttentionActivtiy.actionStart(this, bean.getAuthorid());
                break;
            default:
                break;
        }
    }

    /**
     * 添加关注
     */
    private void addAttenttion() {
        if (!isAttention) {
            //添加关注
            OnModelCallback<Object> callback = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {
                    ToastUtils.showToast(tv_add_attent.getContext(), "关注成功");
                    tv_add_attent.setText("取消关注");
                    isAttention = true;
                    tv_add_attent.setClickable(true);
                }

                @Override
                public void onModelFailed(String failedMsg) {

                }
            };
            AttentionLogic.addUserAttention(bean.getAuthorid(), callback);
        } else {
            //取消关注
            OnModelCallback<Object> callback = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {
                    ToastUtils.showToast(tv_add_attent.getContext(), "取消关注");
                    tv_add_attent.setText("+加关注");
                    isAttention = false;
                    tv_add_attent.setClickable(true);
                }

                @Override
                public void onModelFailed(String failedMsg) {

                }
            };
            AttentionLogic.deleteUserAttention(bean.getAuthorid(), callback);
        }

        tv_add_attent.setClickable(false);
    }

    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     */
    public static void actionStart(Context context, TopicListBean bean) {
        Intent intent = new Intent(context, ForumPersionInfoActivity.class);
        intent.putExtra(TOPICBEAN, bean);
        context.startActivity(intent);
    }


}
