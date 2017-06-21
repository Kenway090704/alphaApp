package com.alpha.alphaapp.ui.mine;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.getuserinfo.GetUserInfoLogic;
import com.alpha.alphaapp.ui.BaseFragment;
import com.alpha.alphaapp.ui.set.SettingsActivity;
import com.alpha.alphaapp.ui.sign.SignActivity;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by kenway on 17/6/8 10:26
 * Email : xiaokai090704@126.com
 */

public class MineFragment extends BaseFragment {
    private LinearLayout layout_userinfo, layout_sign;
    private RoundedImageView riv_icon;
    private TextView tv_name;
    private TextView tv_set;
    private UserInfo info;

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
        tv_set = (TextView) root.findViewById(R.id.frag_mine_tv_set);

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
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.actionStart(getActivity(), null, null);
            }
        });
        layout_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入签到页面
                SignActivity.actionStart(getActivity());
            }
        });
    }

    @Override
    protected void initData() {
        info = AccountManager.getInstance().getUserInfo();
        if (!Util.isNullOrBlank(info.getIcon())) {
            //使用Glide展示图片
            final RequestBuilder<Drawable> thumbnailRequest = Glide.with(this).load(R.drawable.launcher);
            Glide.with(this).load(URLConstans.GET_ICON.ICON60 + info.getIcon()).thumbnail(thumbnailRequest).into(riv_icon);
        }
        if (!Util.isNullOrBlank(info.getName()))
            tv_name.setText(info.getName());

    }
}
