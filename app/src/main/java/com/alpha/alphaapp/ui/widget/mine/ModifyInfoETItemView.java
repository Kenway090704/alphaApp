package com.alpha.alphaapp.ui.widget.mine;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.userinfo.ModifyUserInfoLogic;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by kenway on 17/6/8 11:16
 * Email : xiaokai090704@126.com
 * <p>
 * 修改有输入框的控件,当需要修改内容事,变为输入框格式
 */

public class ModifyInfoETItemView extends LinearLayout {
    private static final String TAG = "ModifyInfoETItemView";
    private Context context;
    private String txt_left;
    //第一个视图
    private LinearLayout layout_one;
    private TextView one_tv_left, one_tv_info;
    private ImageView one_iv_right;

    //第二个视图
    private LinearLayout layout_two;
    private EditText two_et;
    private ImageView two_iv;


    private int modifyType;


    private ErrorTextView tv_error;

    public ModifyInfoETItemView(Context context) {
        super(context);
    }

    public ModifyInfoETItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ModifyInfoETItemView);
        txt_left = array.getString(R.styleable.ModifyInfoETItemView_mieiv_txt_left);
        array.recycle();
        initView();
        initEvents();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_modify_info_has_et_item, this);
        layout_one = (LinearLayout) view.findViewById(R.id.mod_info_has_et_layout_one);
        one_tv_left = (TextView) view.findViewById(R.id.mod_info_has_et_layout_one_tv_left);
        one_tv_info = (TextView) view.findViewById(R.id.mod_info_has_et_layout_one_tv_msg);
        one_iv_right = (ImageView) view.findViewById(R.id.mod_info_has_et_layout_one_iv_right);
        if (!Util.isNullOrBlank(txt_left))
            one_tv_left.setText(txt_left);
        layout_two = (LinearLayout) view.findViewById(R.id.mod_info_has_et_layout_two);
        two_et = (EditText) view.findViewById(R.id.mod_info_has_et_layout_two_et);
        two_iv = (ImageView) view.findViewById(R.id.mod_info_has_et_layout_two_iv_del);


    }

    /**
     * 设置监听事件
     */
    private void initEvents() {
        layout_one.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_one.setVisibility(INVISIBLE);
                layout_two.setVisibility(VISIBLE);
                two_et.setText(one_tv_info.getText().toString());
                //强制使输入框获取焦点
                if (layout_two.getVisibility() == VISIBLE) {
                    two_et.setFocusable(true);
                    two_et.setFocusableInTouchMode(true);
                    two_et.requestFocus();
                    two_et.setSelection(two_et.getText().length());
                    KeyBoardUtils.openKeybord(two_et, context);
                }
            }
        });
        one_iv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_one.setVisibility(INVISIBLE);
                layout_two.setVisibility(VISIBLE);
                two_et.setText(one_tv_info.getText().toString());
                //强制使输入框获取焦点
                if (layout_two.getVisibility() == VISIBLE) {
                    two_et.setFocusable(true);
                    two_et.setFocusableInTouchMode(true);
                    two_et.requestFocus();
                    two_et.setSelection(two_et.getText().length());
                    KeyBoardUtils.openKeybord(two_et, context);
                }
            }
        });
        two_et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!Util.isNullOrBlank(two_et.getText().toString())) {
                        two_iv.setVisibility(VISIBLE);
                    } else {
                        two_iv.setVisibility(INVISIBLE);
                    }
                    //当item获取焦点的时候,不显示
                    tv_error.setViewGone();
                } else {
                    layout_two.setVisibility(GONE);
                    layout_one.setVisibility(VISIBLE);
                    KeyBoardUtils.closeKeybord(two_et, context);
                    //当失去焦点的时候执行修改信息的动作
                    doModifyInfo(modifyType);
                }
            }
        });
        two_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Util.isNullOrBlank(two_et.getText().toString())) {
                    two_iv.setVisibility(VISIBLE);
                } else {
                    two_iv.setVisibility(INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        two_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                two_et.getText().clear();
            }
        });
    }

    /**
     * 设置昵称
     *
     * @param info
     */
    public void setMsg(String info) {
        if (!Util.isNullOrBlank(info)) {
            one_tv_info.setText(info);
        }
    }


    /**
     * 设置修改类型,只有以下四种
     * <p>ModifyUserInfoLogic.MODIFY_NICK_NAME</p>
     * <p>ModifyUserInfoLogic.MODIFY_TRUE_NAME</p>
     * <p>ModifyUserInfoLogic.MODIFY_CONTACT_PHONE</p>
     * <p>ModifyUserInfoLogic.MODIFY_QQ</p>
     *
     * @param modifyType
     * @param tv_error   显示错误信息的TextView
     */
    public void setModifyType(int modifyType, ErrorTextView tv_error) {
        this.modifyType = modifyType;
        this.tv_error = tv_error;
    }

    /**
     * 获取输入框控件中的字符串
     *
     * @return
     */
    public String getEditTextStr() {


        if (!Util.isNull(two_et)) {
            return two_et.getText().toString();
        }
//        return one_tv_info.getText().toString();
        return "";
    }

    /**
     * 完成修改
     */
    public void finishEdit() {

        layout_two.setVisibility(GONE);
        layout_one.setVisibility(VISIBLE);
    }


    /**
     * 修改通讯电话
     */

    private void doModifyInfo(int modifyType) {


        final UserInfo defaultInfo = AccountManager.getInstance().getUserInfo();
        final String msg = getEditTextStr();
        UserInfo modifyInfo = new UserInfo();
        switch (modifyType) {

            case ModifyUserInfoLogic.MODIFY_NICK_NAME:
                if (Util.isNullOrBlank(msg) || !StringUtils.isName(msg)) {
                    setMsg(defaultInfo.getName());
                    tv_error.setText(ResourceUtil.resToStr(R.string.nickname_format));
                    return;
                } else {
                    modifyInfo.setName(msg);
                }
                break;
            case ModifyUserInfoLogic.MODIFY_TRUE_NAME:
                if (Util.isNullOrBlank(msg) || !StringUtils.isTrueName(msg)) {
                    setMsg(defaultInfo.getTrue_name());
                    tv_error.setText(ResourceUtil.resToStr(R.string.truename_format));
                    return;
                } else {
                    modifyInfo.setTrue_name(msg);
                }
                break;
            case ModifyUserInfoLogic.MODIFY_CONTACT_PHONE:

                if (Util.isNullOrBlank(msg) || !StringUtils.isPhoneNum(msg)) {
                    setMsg(defaultInfo.getContact_phone());

                    tv_error.setText(ResourceUtil.resToStr(R.string.contact_phone_format));
                    return;
                } else {
                    modifyInfo.setContact_phone(msg);
                }
                break;
            case ModifyUserInfoLogic.MODIFY_QQ:

                if (Util.isNullOrBlank(msg) || !StringUtils.isQQ(msg)) {
                    setMsg(defaultInfo.getQq());
                    tv_error.setText(ResourceUtil.resToStr(R.string.qq_format));
                    return;
                } else {
                    modifyInfo.setQq(msg);
                }
                break;

        }

        if (msg.equals(one_tv_info.getText().toString())) {
            tv_error.setViewGone();
        } else if (Util.isNullOrBlank(msg)) {
            //修改信息
            tv_error.setViewGone();
        } else {
            doModify(modifyInfo, modifyType);
        }


    }

    private void doModify(final UserInfo info, final int modifyType) {

        String sskey = AccountManager.getInstance().getSskey();
        OnModelCallback<Object> back = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                switch (modifyType) {
                    case ModifyUserInfoLogic.MODIFY_NICK_NAME:

                        setMsg(info.getName());
                        break;
                    case ModifyUserInfoLogic.MODIFY_TRUE_NAME:
                        setMsg(info.getTrue_name());
                        break;
                    case ModifyUserInfoLogic.MODIFY_CONTACT_PHONE:
                        setMsg(info.getContact_phone());
                        break;
                    case ModifyUserInfoLogic.MODIFY_QQ:
                        setMsg(info.getQq());
                        break;
                }
                tv_error.setViewGone();
            }

            @Override
            public void onModelFailed(String failedMsg) {
                setMsg(info.getContact_phone());
                tv_error.setText(failedMsg);
                LogUtils.e(failedMsg);
            }
        };

        ModifyUserInfoLogic.doModifyUserInfo(sskey, info, modifyType, back);
        //修改完成
        finishEdit();
    }


}
