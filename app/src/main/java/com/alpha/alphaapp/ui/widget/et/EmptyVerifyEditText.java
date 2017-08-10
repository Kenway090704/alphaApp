package com.alpha.alphaapp.ui.widget.et;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.CountDownManager;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/13 17:04
 * Email : xiaokai090704@126.com
 * 没有左边icon的输入对话框
 */

public class EmptyVerifyEditText extends LinearLayout {

    private static final String TAG = "EmptyVerifyEditText";
    private Context context;
    private EditText et;
    private ImageView iv_del;
    private TextView tv_getVerify;
    private CountDownManager cmd;

    public EmptyVerifyEditText(Context context) {
        super(context);
    }

    public EmptyVerifyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();

    }

    private void initViews() {
        cmd = new CountDownManager();
        View view = LayoutInflater.from(context).inflate(R.layout.widget_et_empty_verify, this);
        iv_del = (ImageView) view.findViewById(R.id.verify_edit_iv_del);
        et = (EditText) view.findViewById(R.id.verify_edit_et);
        tv_getVerify = (TextView) view.findViewById(R.id.verify_edit_tv_getVerify);
        cmd.setTextView(tv_getVerify);
        iv_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et.getText().clear();
            }
        });

        et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (Util.isNullOrBlank(et.getText().toString())) {
                        iv_del.setVisibility(INVISIBLE);
                    } else {
                        iv_del.setVisibility(VISIBLE);
                    }
                } else {
                    iv_del.setVisibility(INVISIBLE);
                }
            }
        });
    }

    /**
     * 对EditText 设置监听事件
     *
     * @param textWatcher
     */
    public void setWatcherListener(TextWatcher textWatcher) {
        if (et != null && textWatcher != null) {
            et.addTextChangedListener(textWatcher);
        }
    }


    /**
     * 获取右边删除内容的icon 对象
     *
     * @return
     */
    public ImageView getImageViewRight() {
        if (Util.isNull(iv_del))
            return null;
        return iv_del;
    }

    public Editable getText() {
        if (et == null) {
            return null;
        }
        return et.getText();
    }

    /**
     * 获取输入框中的文字(前后空格已去除)
     *
     * @return
     */
    public String getEditTextStr() {
        if (!Util.isNull(et)) {
            return et.getText().toString().trim();
        } else {
            return null;
        }

    }

    public void setGetVerifyTextViewListener(OnClickListener listener) {
        if (Util.isNull(tv_getVerify))
            return;
        tv_getVerify.setOnClickListener(listener);
    }


    /**
     * 清楚输入框的文字
     */
    public void clear() {
        if (!Util.isNull(et)) {
            et.getText().clear();
        }
    }

    public void start() {
        if (!Util.isNull(cmd))
            cmd.start();
        if (!Util.isNull(tv_getVerify))
            tv_getVerify.setBackgroundResource(R.drawable.shape_com_bg_gray);

    }

    public void cancel() {
        if (!Util.isNull(cmd))
            cmd.cancel();
        if (!Util.isNull(tv_getVerify))
            tv_getVerify.setBackgroundResource(R.drawable.shape_com_bg_red);
    }
}
