package com.alpha.alphaapp.ui.widget.mine;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.model.StringUtils;
import com.alpha.alphaapp.model.modifyinfo.ModifyUserInfoLogic;
import com.alpha.lib_sdk.app.log.Log;
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
        //如果点击其他取悦判断是否有内容,如果有内容,则修改名字,如果没有内容,则不修改内容,
        final String nickname = getEditTextStr();
        final UserInfo info = AccountManager.getInstance().getUserInfo();
        if (Util.isNullOrBlank(nickname) || info.getName().equals(nickname)) {
            setMsg(info.getName());
        } else {
            String sskey = AccountManager.getInstance().getSskey();
            final UserInfo info2 = new UserInfo();
            info2.setName(nickname);
            ModifyUserInfoLogic.EditInfoCallBack call = new ModifyUserInfoLogic.EditInfoCallBack() {
                @Override
                public void onEditInfoSuccuss() {
                    setMsg(nickname);
                }

                @Override
                public void onEditInfoFailed(String failMsg) {
                    setMsg(info.getName());
                }
            };
            ModifyUserInfoLogic.doModifyUserInfo(sskey, info2, ModifyUserInfoLogic.MODIFY_NICK_NAME, call);
        }
        //修改完成
        finishEdit();
    }

    /**
     * 修改真实名字
     */
    public void doModifyTruename() {
        //如果点击其他取悦判断是否有内容,如果有内容,则修改名字,如果没有内容,则不修改内容,
        final String name = getEditTextStr();
        final UserInfo info = AccountManager.getInstance().getUserInfo();
        if (Util.isNullOrBlank(name) || info.getTrue_name().equals(name)) {
            setMsg(info.getTrue_name());
        } else {
            String sskey = AccountManager.getInstance().getSskey();
            final UserInfo info2 = new UserInfo();
            info2.setTrue_name(name);
            ModifyUserInfoLogic.EditInfoCallBack call = new ModifyUserInfoLogic.EditInfoCallBack() {
                @Override
                public void onEditInfoSuccuss() {
                    setMsg(name);
                }

                @Override
                public void onEditInfoFailed(String failMsg) {
                    setMsg(info.getTrue_name());
                }
            };
            ModifyUserInfoLogic.doModifyUserInfo(sskey, info2, ModifyUserInfoLogic.MODIFY_TRUE_NAME, call);
        }
        //修改完成
        finishEdit();
    }

    /**
     * 修改QQ号码
     */
    public void doModifyQQ() {
        //如果点击其他取悦判断是否有内容,如果有内容,则修改名字,如果没有内容,则不修改内容,
        final String qq = getEditTextStr();
        final UserInfo info = AccountManager.getInstance().getUserInfo();
        if (Util.isNullOrBlank(qq) || info.getQq().equals(qq)||!StringUtils.isQQ(qq)) {
            setMsg(info.getQq());
        } else {
            String sskey = AccountManager.getInstance().getSskey();
            final UserInfo info2 = new UserInfo();
            info2.setQq(qq);
            ModifyUserInfoLogic.EditInfoCallBack call = new ModifyUserInfoLogic.EditInfoCallBack() {
                @Override
                public void onEditInfoSuccuss() {
                    setMsg(qq);
                }

                @Override
                public void onEditInfoFailed(String failMsg) {
                    setMsg(info.getTrue_name());
                }
            };
            ModifyUserInfoLogic.doModifyUserInfo(sskey, info2, ModifyUserInfoLogic.MODIFY_QQ, call);
        }
        //修改完成
        finishEdit();
    }

    /**
     * 修改通讯电话
     */
    public void doModifyContactPhone() {
        //如果点击其他判断是否有内容,如果有内容,且符合手机号规则,则修改修改,如果没有内容,则不修改内容,
        final String phone = getEditTextStr();
        final UserInfo info = AccountManager.getInstance().getUserInfo();
        if (Util.isNullOrBlank(phone) || info.getContact_phone().equals(phone)|| !StringUtils.isPhoneNum(phone)) {
            setMsg(info.getContact_phone());
        } else {
            String sskey = AccountManager.getInstance().getSskey();
            final UserInfo info2 = new UserInfo();
            info2.setContact_phone(phone);
            ModifyUserInfoLogic.EditInfoCallBack call = new ModifyUserInfoLogic.EditInfoCallBack() {
                @Override
                public void onEditInfoSuccuss() {
                    setMsg(phone);
                }

                @Override
                public void onEditInfoFailed(String failMsg) {
                    setMsg(info.getContact_phone());
                }
            };
            ModifyUserInfoLogic.doModifyUserInfo(sskey, info2, ModifyUserInfoLogic.MODIFY_CONTACT_PHONE, call);
        }
        //修改完成
        finishEdit();
    }

}
