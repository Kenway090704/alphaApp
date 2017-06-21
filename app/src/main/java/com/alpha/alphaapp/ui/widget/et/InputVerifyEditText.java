package com.alpha.alphaapp.ui.widget.et;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_stub.model.CountDownManager;

/**
 * Created by kenway on 17/6/13 17:04
 * Email : xiaokai090704@126.com
 * 输入验证码的对话框
 */

public class InputVerifyEditText extends LinearLayout {

    private static final String TAG = "InputVerifyEditText";
    private Context context;
    private EditText et;
    private ImageView iv_del;
    private TextView tv_getVerify;
    private CountDownManager cmd;

    public InputVerifyEditText(Context context) {
        super(context);
    }

    public InputVerifyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();

    }

    private void initViews() {
        cmd = new CountDownManager();
        View view = LayoutInflater.from(context).inflate(R.layout.widget_verify_et, this);
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


    public void setGetVerifyTextViewListener(OnClickListener listener) {
        if (Util.isNull(tv_getVerify))
            return;
        tv_getVerify.setOnClickListener(listener);
    }

    public void start() {
        cmd.start();

    }

    public void cancel() {
        cmd.cancel();
    }
}
