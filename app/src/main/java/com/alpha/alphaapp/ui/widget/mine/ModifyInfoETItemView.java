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
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.alphaapp.model.v_1_0.userinfo.ModifyUserInfoLogic;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

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
                } else {
                    layout_two.setVisibility(GONE);
                    layout_one.setVisibility(VISIBLE);
                    KeyBoardUtils.closeKeybord(two_et, context);
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
     * 获取输入框控件中的字符串
     *
     * @return
     */
    public String getEditTextStr() {
        return two_et.getText().toString();
    }

    /**
     * 完成修改
     */
    public void finishEdit() {
        layout_two.setVisibility(GONE);
        layout_one.setVisibility(VISIBLE);
    }

    /**
     * 修改昵称
     */
    public void doModifyNickname() {
        if (layout_two.getVisibility() != VISIBLE) return;
        //如果点击其他区域判断是否有内容,如果有内容,则修改名字,如果没有内容,则不修改内容,
        final String nickname = getEditTextStr();
        final UserInfo info = AccountManager.getInstance().getUserInfo();
        if (Util.isNullOrBlank(nickname) || !StringUtils.isName(nickname) || info.getName().equals(nickname)) {
            ToastUtils.showShort(context, "昵称输入有误");
            setMsg(info.getName());
        } else {
            String sskey = AccountManager.getInstance().getSskey();
            final UserInfo info2 = new UserInfo();
            info2.setName(nickname);



            OnModelCallback<Object> back = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {

                }

                @Override
                public void onModelFailed(String failedMsg) {
                    setMsg(info.getName());
                    LogUtils.e(TAG, "failed==" + failedMsg);
                }
            };
            ModifyUserInfoLogic.doModifyUserInfo(sskey, info2, ModifyUserInfoLogic.MODIFY_NICK_NAME, back);
        }
        //修改完成
        finishEdit();
    }

    /**
     * 修改真实名字
     */
    public void doModifyTruename() {
        if (layout_two.getVisibility() != VISIBLE) return;
        //如果点击其他取悦判断是否有内容,如果有内容,则修改名字,如果没有内容,则不修改内容,
        final String name = getEditTextStr();
        final UserInfo info = AccountManager.getInstance().getUserInfo();
        if (Util.isNullOrBlank(name) || !StringUtils.isTrueName(name) || info.getTrue_name().equals(name)) {
            ToastUtils.showShort(context, "姓名输入有误");
            setMsg(info.getTrue_name());
        } else {
            String sskey = AccountManager.getInstance().getSskey();
            final UserInfo info2 = new UserInfo();
            info2.setTrue_name(name);


            OnModelCallback<Object> back = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {
                    setMsg(name);
                }

                @Override
                public void onModelFailed(String failedMsg) {
                    setMsg(info.getTrue_name());
                    LogUtils.e(TAG, "failed==" + failedMsg);
                }
            };
            ModifyUserInfoLogic.doModifyUserInfo(sskey, info2, ModifyUserInfoLogic.MODIFY_TRUE_NAME, back);
        }
        //修改完成
        finishEdit();
    }

    /**
     * 修改QQ号码
     */
    public void doModifyQQ() {
        if (layout_two.getVisibility() != VISIBLE) return;
        //如果点击其他取悦判断是否有内容,如果有内容,则修改名字,如果没有内容,则不修改内容,
        final String qq = getEditTextStr();
        final UserInfo info = AccountManager.getInstance().getUserInfo();
        if (Util.isNullOrBlank(qq) || info.getQq().equals(qq) || !StringUtils.isQQ(qq)) {
            setMsg(info.getQq());
            ToastUtils.showShort(context, "QQ号码输入有误");
        } else {
            String sskey = AccountManager.getInstance().getSskey();
            final UserInfo info2 = new UserInfo();
            info2.setQq(qq);



            OnModelCallback<Object> back = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {
                    setMsg(qq);
                }

                @Override
                public void onModelFailed(String failedMsg) {
                    setMsg(info.getTrue_name());
                    LogUtils.e(TAG, "failed==" + failedMsg);
                }
            };
            ModifyUserInfoLogic.doModifyUserInfo(sskey, info2, ModifyUserInfoLogic.MODIFY_QQ, back);
        }
        //修改完成
        finishEdit();
    }

    /**
     * 修改通讯电话
     */
    public void doModifyContactPhone() {
        if (layout_two.getVisibility() != VISIBLE) return;
        //如果点击其他判断是否有内容,如果有内容,且符合手机号规则,则修改修改,如果没有内容,则不修改内容,
        final String phone = getEditTextStr();
        final UserInfo info = AccountManager.getInstance().getUserInfo();
        if (Util.isNullOrBlank(phone) || info.getContact_phone().equals(phone) || !StringUtils.isPhoneNum(phone)) {
            setMsg(info.getContact_phone());
            ToastUtils.showShort(context, "手机号码输入有误");
        } else {
            String sskey = AccountManager.getInstance().getSskey();
            final UserInfo info2 = new UserInfo();
            info2.setContact_phone(phone);

            OnModelCallback<Object> back = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {
                    setMsg(phone);
                }

                @Override
                public void onModelFailed(String failedMsg) {
                    setMsg(info.getContact_phone());
                    LogUtils.e(TAG, "failed==" + failedMsg);
                }
            };

            ModifyUserInfoLogic.doModifyUserInfo(sskey, info2, ModifyUserInfoLogic.MODIFY_CONTACT_PHONE, back);
        }
        //修改完成
        finishEdit();
    }


}
