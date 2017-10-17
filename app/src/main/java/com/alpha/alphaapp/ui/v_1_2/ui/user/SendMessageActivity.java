package com.alpha.alphaapp.ui.v_1_2.ui.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.model.v_1_2.logic.message.MessageLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/10/16 18:12
 * Email : xiaokai090704@126.com
 * 发送私信的页面
 */

public class SendMessageActivity extends BaseActivity {
    private  static Context mContext;

    private static final String TOPICBEAN = "topicbean";
    private ImageView iv_back;
    private TextView tv_send, tv_targetuid;
    private EditText et;

    private TopicListBean bean;

    @Override
    protected int getLayoutId() {
        bean = (TopicListBean) getIntent().getSerializableExtra(TOPICBEAN);
        return R.layout.activity_send_message;
    }

    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.acty_send_message_iv_back);
        tv_send = (TextView) findViewById(R.id.acty_send_message_tv_send);
        tv_targetuid = (TextView) findViewById(R.id.acty_send_message_tv_targetuid);
        tv_targetuid.setText(bean.getAuthor());
        et = (EditText) findViewById(R.id.acty_send_message_et);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNullOrBlank(et.getText().toString())) {
                    ToastUtils.showToast(tv_send.getContext(), "内容不能为空");
                    return;
                }
                //发送私信
                OnModelCallback<Object> call = new OnModelCallback<Object>() {
                    @Override
                    public void onModelSuccessed(Object o) {
                        finish();
                        ToastUtils.showToast(mContext, "发送成功");
                    }

                    @Override
                    public void onModelFailed(String failedMsg) {

                    }
                };
                MessageLogic.sendMessage(bean.getAuthorid(), et.getText().toString(), call);
            }
        });


    }

    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     */
    public static void actionStart(Context context, TopicListBean bean) {
        mContext = context;
        Intent intent = new Intent(context, SendMessageActivity.class);
        intent.putExtra(TOPICBEAN, bean);
        context.startActivity(intent);

    }
}
