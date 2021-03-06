package com.alpha.alphaapp.ui.widget.mine;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.userinfo.ModifyUserInfoLogic;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.tandong.bottomview.view.BottomView;

/**
 * Created by kenway on 17/6/15 13:37
 * Email : xiaokai090704@126.com
 * 修改性别
 */

public class ModifySexView extends LinearLayout {
    private static final String TAG = "ModifySexView";
    private Context context;
    private String txt_left;
    private TextView tv_left, tv_info;
    private ImageView iv_right;

    //底部弹出布局
    private BottomView bottomView;
    private View btmView;
    private TextView tv_men, tv_women, tv_sercret;
    private Button btn_cancel;
    private View view;

    public ModifySexView(Context context) {
        super(context);
    }

    public ModifySexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ModifySexView);
        txt_left = array.getString(R.styleable.ModifySexView_msv_txt_left);
        array.recycle();
        initView();
        initBottomView();
        initEvent();
    }


    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.widget_modify_info_item, this);
        tv_left = (TextView) view.findViewById(R.id.widget_mod_info_tv_left);
        tv_info = (TextView) view.findViewById(R.id.widget_mod_info_tv_msg);
        iv_right = (ImageView) view.findViewById(R.id.widget_mod_info_iv_right);
        if (!Util.isNullOrBlank(txt_left))
            tv_left.setText(txt_left);
    }

    /**
     * 初始化底部View
     */
    private void initBottomView() {
        bottomView = new BottomView(context,
                R.style.BottomViewTheme_Sex, R.layout.widget_bottom_view_mod_sex);
        bottomView.setAnimation(R.style.BottomToTopAnim);//设置动画，可选
        btmView = bottomView.getView();
        tv_men = (TextView) btmView.findViewById(R.id.widget_mod_set_tv_men);
        tv_women = (TextView) btmView.findViewById(R.id.widget_mod_set_tv_women);
        tv_sercret = (TextView) btmView.findViewById(R.id.widget_mod_set_tv_secret);
        btn_cancel = (Button) btmView.findViewById(R.id.widget_mod_set_btn_cancel);

    }

    private void initEvent() {
        //这个可能要放到外面
        iv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_men.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doModifySex(1);
                dismiss();
            }
        });

        tv_women.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doModifySex(0);
                dismiss();
            }
        });

        tv_sercret.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doModifySex(2);
                dismiss();
            }
        });

    }

    /**
     * 右边箭头的点击事件
     *
     * @param listener
     */
    public void setRightIVOnClicklistener(OnClickListener listener) {

        if (!Util.isNull(listener)){
            view.setOnClickListener(listener);
            iv_right.setOnClickListener(listener);
        }
    }

    /**
     * 设置性别
     *
     * @param sex
     */
    public void setMsg(int sex) {

        if (sex == 0) {
            tv_info.setText("女");
            tv_women.setTextColor(context.getResources().getColor(R.color.common_tv_dark_red));
            tv_men.setTextColor(context.getResources().getColor(R.color.common_tv_black));
            tv_sercret.setTextColor(context.getResources().getColor(R.color.common_tv_black));

        } else if (sex == 1) {
            tv_info.setText("男");
            tv_men.setTextColor(context.getResources().getColor(R.color.common_tv_dark_red));
            tv_women.setTextColor(context.getResources().getColor(R.color.common_tv_black));
            tv_sercret.setTextColor(context.getResources().getColor(R.color.common_tv_black));
        } else {
            tv_info.setText("保密");

            tv_sercret.setTextColor(context.getResources().getColor(R.color.common_tv_dark_red));

            tv_men.setTextColor(context.getResources().getColor(R.color.common_tv_black));
            tv_women.setTextColor(context.getResources().getColor(R.color.common_tv_black));
        }
    }

    /**
     * 弹出底部对话框
     * ,展示前请先设置数据
     */
    public void show() {
        if (!Util.isNull(bottomView)) {
            ViewGroup parent = (ViewGroup) bottomView.getView().getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            bottomView.showBottomView(false);
        }

    }

    /**
     * 底部弹出对话框消失
     */
    public void dismiss() {
        if (!Util.isNull(bottomView))
            bottomView.dismissBottomView();
    }

    /**
     * 修改性别
     */
    public void doModifySex(final int sex) {

        final UserInfo info = AccountManager.getInstance().getUserInfo();
        int sex_Server = info.getSex();
        if (sex_Server == sex) {
            setMsg(sex);
        } else {
            String sskey = AccountManager.getInstance().getSskey();
            final UserInfo info2 = new UserInfo();
            info2.setSex(sex);

            OnModelCallback<Object> back = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {
                    setMsg(sex);
                }

                @Override
                public void onModelFailed(String failedMsg) {
                    setMsg(info.getSex());
                    LogUtils.e(TAG, "failed==" + failedMsg);
                }
            };
            ModifyUserInfoLogic.doModifyUserInfo(sskey, info2, ModifyUserInfoLogic.MODIFY_SEX, back);
        }

    }


}
