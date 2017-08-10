package com.alpha.alphaapp.ui.v_1_0.mine;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.ui.AccountChangeFragment;
import com.alpha.alphaapp.ui.v_1_0.set.SettingsActivity;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by kenway on 17/6/8 10:26
 * Email : xiaokai090704@126.com
 */

public class MineFragment extends AccountChangeFragment {
    private LinearLayout layout_userinfo, layout_sign;
    private RoundedImageView riv_icon;
    private TextView tv_name;
    private LinearLayout layout_set;
    private ImageView iv_set;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews(View root) {
        layout_userinfo = (LinearLayout) root.findViewById(R.id.frag_mine_layout_userinfo);
        riv_icon = (RoundedImageView) root.findViewById(R.id.frag_mine_riv);
        tv_name = (TextView) root.findViewById(R.id.frag_mine_tv_name);
        layout_sign = (LinearLayout) root.findViewById(R.id.frag_mine_layout_sign);
        iv_set = (ImageView) root.findViewById(R.id.frag_mine_iv_set);
        layout_set = (LinearLayout) root.findViewById(R.id.frag_mine_layout_set);
        UserInfo info = AccountManager.getInstance().getUserInfo();
        updateUI(info);
    }

    @Override
    protected void initEnvent() {
        layout_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入个人信息设置页面
                MineInfoActivity.actionStart(getActivity(), null, null);
            }
        });
        iv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.actionStart(getActivity(), null, null);
            }
        });
        layout_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.actionStart(getActivity(), null, null);
            }
        });
        layout_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入签到页面
//                SignActivity.actionStart(getActivity());

                //进入到demo页面
//              StaggerActivity.actionStart(getActivity());
            }
        });
    }

    @Override
    protected void initData() {
        UserInfo info = AccountManager.getInstance().getUserInfo();
        updateUI(info);
    }

    @Override
    public void onAccountUpdate(UserInfo info) {
        updateUI(info);
    }

    /**
     * 更新界面
     *
     * @param info
     */
    private void updateUI(UserInfo info) {
        if (!Util.isNull(info)) {
            //使用Glide展示图片
            if (Util.isNullOrBlank(info.getIcon())) {
                ImageLoader.loadCircle(getActivity(), URLConstans.GET_ICON.ICON_DEFAULT, riv_icon);
            } else {
                ImageLoader.loadCircle(getActivity(), URLConstans.GET_ICON.ICON60 + info.getIcon(), riv_icon);
            }


        }
        //如果是微信/QQ登录,使用为微信或者QQ的昵称,如果是手机或者帐号登录,先判断是否有
        if (AccountManager.getInstance().getLoginType() == TypeConstants.LOGIN_TYPE.AUTH_QQ || AccountManager.getInstance().getLoginType() == TypeConstants.LOGIN_TYPE.AUTH_WX) {
            String nickname = AccountManager.getInstance().getAuthNickName();
            if (!Util.isNull(nickname)) {
                tv_name.setText(nickname);
            }
        } else {
            //是否有昵称,有显示,没有,显示手机号,如果没有手机号,如果没有,显示帐号


            if (!Util.isNullOrBlank(info.getName())) {
                tv_name.setText(info.getName());
            } else if (!Util.isNullOrBlank(info.getMobile())) {
                tv_name.setText(info.getMobile());
            } else if (!Util.isNullOrBlank(info.getAccount())) {
                tv_name.setText(info.getAccount());
            }
        }

    }
}
