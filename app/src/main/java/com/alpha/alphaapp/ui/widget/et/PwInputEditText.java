package com.alpha.alphaapp.ui.widget.et;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/27 18:31
 * Email : xiaokai090704@126.com
 */

public class PwInputEditText extends LinearLayout {
    private static final String TAG = "PwInputEditText";
    private Context context;
    private Drawable icon_left;
    private String txt_hint;

    private ImageView iv_left, iv_del;
    private EditText et;
    private ToggleButton tbtn;
    private boolean isShowPW = false;//是否显示密码


    private OnFoucusableListener onFocusableListener;//当获取焦点的时候回调接口



    private OnToggleBtnCheckedChangeListener onToggleBtnCheckedChangeListener;//显示密码与隐藏密码回调接口

    public PwInputEditText(Context context) {
        super(context);
    }

    public PwInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AccountEditText);
        icon_left = array.getDrawable(R.styleable.AccountEditText_icon_type);
        txt_hint = array.getString(R.styleable.AccountEditText_hint);
        array.recycle();
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_pwinput_et, this);
        iv_left = (ImageView) view.findViewById(R.id.pw_edit_iv_lock);
        et = (EditText) view.findViewById(R.id.pw_edit_et_hint);
        iv_del = (ImageView) view.findViewById(R.id.pw_edit_iv_del);
        tbtn = (ToggleButton) view.findViewById(R.id.pw_edit_toggleButton);


        if (!Util.isNull(icon_left))
            iv_left.setImageDrawable(icon_left);
        if (!Util.isNullOrBlank(txt_hint))
            et.setHint(txt_hint);
        tbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //设置EditText文本为可见的
                    tbtn.setBackground(getResources().getDrawable(R.drawable.icon_toggle_show));
                    et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPW = true;
                } else {
                    //设置EditText文本为隐藏的
                    tbtn.setBackground(getResources().getDrawable(R.drawable.icon_toggle_hide));
                    et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShowPW = false;
                }
                if(!Util.isNull(onToggleBtnCheckedChangeListener))
                    onToggleBtnCheckedChangeListener.onCheckedChanged(buttonView,isChecked);
                et.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = et.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });


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
               if(!Util.isNull(onFocusableListener))
                onFocusableListener.onFocusChange(v, hasFocus);

            }
        });
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
     * 是否显示密码
     *
     * @return
     */
    public boolean isShowPW() {
        return isShowPW;
    }
    public void setOnFocusableListener(OnFoucusableListener onFocusableListener) {
        this.onFocusableListener = onFocusableListener;
    }
    public void setOnToggleBtnCheckedChangeListener(OnToggleBtnCheckedChangeListener onToggleBtnCheckedChangeListener) {
        this.onToggleBtnCheckedChangeListener = onToggleBtnCheckedChangeListener;
    }

    public interface OnFoucusableListener {
        void onFocusChange(View v, boolean hasFocus);
    }

    public  interface  OnToggleBtnCheckedChangeListener{
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked);
    }


}
