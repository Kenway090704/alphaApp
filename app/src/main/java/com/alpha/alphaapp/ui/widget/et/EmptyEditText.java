package com.alpha.alphaapp.ui.widget.et;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/13 11:36
 * Email : xiaokai090704@126.com
 * 输入用户名和帐号的输入框
 */

public class EmptyEditText extends LinearLayout {
    private static final String TAG = "AccountEditText";
    private Context context;
    private EditText et;
    private ImageView iv_del;
    private String txt_hint;
    private String input_type;


    public EmptyEditText(Context context) {
        super(context);
    }

    public EmptyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AccountEditText);

        txt_hint = array.getString(R.styleable.AccountEditText_hint);
        input_type = array.getString(R.styleable.AccountEditText_input_type);
        array.recycle();
        initViews();
    }

    /**
     * 获取右边删除内容的icon 对象
     *
     * @return
     */
    public ImageView getImageViewRight() {
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

    public EditText getEditText() {
        return et;
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_et_no_icon, this);

        et = (EditText) view.findViewById(R.id.acc_edit_et_hint);
        iv_del = (ImageView) view.findViewById(R.id.acc_edit_iv_del);

        if (!Util.isNullOrBlank(txt_hint))
            et.setHint(txt_hint);
        if (!Util.isNull(input_type))
            et.setTransformationMethod(PasswordTransformationMethod.getInstance());
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


}
