package com.alpha.alphaapp.ui.v_1_2.ui.topic;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.EmotionBean;
import com.alpha.alphaapp.model.v_1_2.logic.emoj.EmotionLogic;
import com.alpha.alphaapp.model.v_1_2.logic.topic.TopicLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_2.ui.adapter.ChoosePhotoAdapter;
import com.alpha.alphaapp.ui.v_1_2.ui.adapter.EmotionAdapter;
import com.alpha.alphaapp.ui.v_1_2.ui.decoration.PhotoSpacesItemDecoration;
import com.alpha.alphaapp.ui.widget.addtheme.AddThemeLayout;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/9/28 11:07
 * Email : xiaokai090704@126.com
 */

public class TopicCreateActivity extends BaseActivity {

    private static final String FID = "fid";
    private static final int TAKE_PHOTO = 0;
    private static final int SELECT_PHOTO = 1;
    private TextView  tv_cancel,tv_nowCreat;
    private EditText et_subject, et_content;
    private AddThemeLayout atl;
    private TextView tv_emotion;
    private ImageView iv_camera;
    private CheckBox cb_bold, cb_theme;


    private RecyclerView rv_emotion, rv_pic;




    private List<EmotionBean> list_emotion = new ArrayList<>();

    private List<String> list_choosePics = new ArrayList<>();


    //编辑的内容
    private String content = null;

    private ChoosePhotoAdapter adapter_choosePic;


    private String fid;



    @Override
    protected int getLayoutId() {
        fid = getIntent().getStringExtra(FID);
        return R.layout.activity_create_topic;
    }

    @Override
    protected void initView() {
        tv_cancel = (TextView) findViewById(R.id.acty_create_topic_tv_cancel);
        tv_nowCreat = (TextView) findViewById(R.id.acty_create_topic_tv_nowcreate);

        et_subject = (EditText) findViewById(R.id.acty_create_topic_et_subject);
        et_content = (EditText) findViewById(R.id.acty_create_topic_et_content);

        atl = (AddThemeLayout) findViewById(R.id.acty_create_topic_atl);

        tv_emotion = (TextView) findViewById(R.id.acty_create_topic_tv_emotion);
        iv_camera = (ImageView) findViewById(R.id.acty_create_topic_iv_camera);
        cb_bold = (CheckBox) findViewById(R.id.acty_create_topic_cb_bold);
        cb_theme = (CheckBox) findViewById(R.id.acty_create_topic_cb_theme);

        rv_emotion = (RecyclerView) findViewById(R.id.acty_create_topic_rv_choose_emtion);
        initRvEmotion();

        rv_pic = (RecyclerView) findViewById(R.id.acty_create_topic_rv_choose_pic);
        initRvPic();



    }

    private void initRvPic() {
        rv_pic.setLayoutManager(new GridLayoutManager(this, 4));
        rv_pic.addItemDecoration(new PhotoSpacesItemDecoration(8));
        adapter_choosePic = new ChoosePhotoAdapter(this, list_choosePics);
        rv_pic.setAdapter(adapter_choosePic);
    }


    /**
     * 初始化表情列表
     */
    private void initRvEmotion() {

        rv_emotion.setLayoutManager(new GridLayoutManager(this, 8));
        //获取表情数据
        EmotionAdapter.OnItemClickCallback call = new EmotionAdapter.OnItemClickCallback() {
            @Override
            public void onItemCallback(String emotion, int position) {
                //发布内容添加表情字符
                if (Util.isNullOrBlank(content)) {
                    content = emotion;
                } else {
                    int index = et_content.getSelectionStart();
                    Editable editable = et_content.getText();
                    editable.insert(index, emotion);
                    content = et_content.getText().toString();
                }

            }
        };
        final EmotionAdapter adapter = new EmotionAdapter(this, list_emotion, call);
        rv_emotion.setAdapter(adapter);

        EmotionLogic.getAllEmotionList(new OnModelCallback<List<EmotionBean>>() {
            @Override
            public void onModelSuccessed(List<EmotionBean> emotionBeens) {
                adapter.refreshData(emotionBeens);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_nowCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSendNewTopic();

            }
        });

        et_subject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rv_emotion.setVisibility(View.GONE);
                    rv_pic.setVisibility(View.GONE);

                }
            }
        });
        et_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rv_emotion.setVisibility(View.GONE);
                    rv_pic.setVisibility(View.GONE);

                }
            }
        });
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                content = et_content.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tv_emotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击显示表情rv
                rv_emotion.setVisibility(View.VISIBLE);
                rv_pic.setVisibility(View.GONE);
                KeyBoardUtils.closeKeybord(et_content, TopicCreateActivity.this);
                KeyBoardUtils.closeKeybord(et_subject, TopicCreateActivity.this);
            }
        });
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelectorActivity.OnChoosePicUrisCallBack call = new MultiImageSelectorActivity.OnChoosePicUrisCallBack() {
                    @Override
                    public void onChoosePics(List<String> list) {
                        upLoadPics(list);
                    }
                };
                MultiImageSelectorActivity.actionStart(TopicCreateActivity.this, call);
            }
        });

        //内容加粗
        cb_bold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SpannableString ss = new SpannableString(et_content.getText().toString());
                    ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    et_content.getText().clear();
                    et_content.setText(ss);
                    et_content.setSelection(ss.length());
                } else {
                    SpannableString ss = new SpannableString(et_content.getText().toString());
                    ss.setSpan(new StyleSpan(Typeface.NORMAL), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    et_content.getText().clear();
                    et_content.setText(ss);
                    et_content.setSelection(ss.length());
                }
            }
        });

        cb_theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    atl.setVisibility(View.VISIBLE);
                } else {
                    atl.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     */
    public static void actionStart(Context context, String fid) {
        Intent intent = new Intent(context, TopicCreateActivity.class);
        intent.putExtra(FID, fid);
        context.startActivity(intent);
    }






    @Override
    protected void onPause() {
        super.onPause();


    }

    /**
     * 上传图片
     *
     * @param list
     */
    private void upLoadPics(List<String> list) {
        //先显示图片
        rv_emotion.setVisibility(View.GONE);
        adapter_choosePic.refreshUI(list);
        rv_pic.setVisibility(View.VISIBLE);
    }


    /**
     * 发送新帖子
     */
    private void doSendNewTopic() {
        //判断是否有主题,没有主题不可以发帖
        if (Util.isNullOrBlank(et_subject.getText().toString())) {
            ToastUtils.showToast(TopicCreateActivity.this, "主题必须填写");
            return;
        }
        OnModelCallback<Object> callback = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                ToastUtils.showToast(TopicCreateActivity.this, "发帖成功");
                finish();
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        };
        //编辑文本内容
        TopicLogic.sendNewTopic( fid, et_subject.getText().toString(), content,adapter_choosePic.getUploadBeans(), callback);
    }
}
