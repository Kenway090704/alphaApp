package com.alpha.alphaapp.ui.v_1_0.mine;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.model.v_1_0.bean.GetIconBean;
import com.alpha.alphaapp.model.v_1_0.userinfo.GetIconListLogic;
import com.alpha.alphaapp.model.v_1_0.userinfo.ModifyUserInfoLogic;
import com.alpha.alphaapp.ui.AccountChangeActivity;
import com.alpha.alphaapp.ui.v_1_0.mine.addr.ModifyContactAddrActivity;
import com.alpha.alphaapp.ui.v_1_0.mine.logic.GetBirthdayLogic;
import com.alpha.alphaapp.ui.v_1_0.mine.school.ModifySchoolActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.mine.ModifyIconView;
import com.alpha.alphaapp.ui.widget.mine.ModifyInfoETItemView;
import com.alpha.alphaapp.ui.widget.mine.ModifyInfoItemView;
import com.alpha.alphaapp.ui.widget.mine.ModifySexView;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.tool.Util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenway on 17/6/14 17:53
 * Email : xiaokai090704@126.com
 */

public class MineInfoActivity extends AccountChangeActivity {
    private static final String TAG = "MineInfoActivity";
    private TitleLayout titleLayout;
    private ModifyIconView mod_icon;
    private ModifyInfoItemView mod_birthday, mod_school, mod_contact_addr;
    private ModifyInfoETItemView mieiv_nickname, mieiv_truename, mieiv_contact_phone, mieiv_qq;
    private ModifySexView msv_sex;
    private ErrorTextView tv_error;
    private UserInfo info;
    private Map<String, Boolean> map;
    private GetBirthdayLogic logic_birday;//修改生日

    @Override
    protected int getLayoutId() {
        info = AccountManager.getInstance().getUserInfo();
        return R.layout.activity_mine_info;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.mine_info_titlelayout);
        mod_icon = (ModifyIconView) findViewById(R.id.mine_info_miv_icon);
        mieiv_nickname = (ModifyInfoETItemView) findViewById(R.id.mine_info_mieiv_nickname);
        mieiv_truename = (ModifyInfoETItemView) findViewById(R.id.mine_info_mieiv_truename);
        msv_sex = (ModifySexView) findViewById(R.id.mine_info_msv_sex);
        mod_birthday = (ModifyInfoItemView) findViewById(R.id.mine_info_birthday);
        mod_school = (ModifyInfoItemView) findViewById(R.id.mine_info_school);
        mieiv_qq = (ModifyInfoETItemView) findViewById(R.id.mine_info_mieiv_qq);
        mieiv_contact_phone = (ModifyInfoETItemView) findViewById(R.id.mine_info_mieiv_contact_phone);
        mod_contact_addr = (ModifyInfoItemView) findViewById(R.id.mine_info_contact_addr);
        tv_error = (ErrorTextView) findViewById(R.id.mine_info_tv_error);


        setViewData();
    }

    private void setViewData() {
        if (!Util.isNullOrBlank(info.getIcon())) {
            mod_icon.setIcon(URLConstans.GET_ICON.ICON60, info.getIcon());
        } else {
            mod_icon.setIcon(URLConstans.GET_ICON.ICON_DEFAULT);
        }

        if (!Util.isNullOrBlank(info.getName()))
            mieiv_nickname.setMsg(info.getName());
        if (!Util.isNullOrBlank(info.getTrue_name()))
            mieiv_truename.setMsg(info.getTrue_name());
        if (!Util.isNullOrBlank(info.getQq()))
            mieiv_qq.setMsg(info.getQq());
        if (!Util.isNullOrBlank(info.getContact_phone()))
            mieiv_contact_phone.setMsg(info.getContact_phone());
        if (!Util.isZore(info.getBirthday_year()) && !Util.isZore(info.getBirthday_mon()) && !Util.isZore(info.getBirthday_day()))
            mod_birthday.setMsg(DateUtils.getStringformYMD(info.getBirthday_year(), info.getBirthday_mon(), info.getBirthday_day()));
        msv_sex.setMsg(info.getSex());
        if (!Util.isNullOrBlank(info.getSchool()))
            mod_school.setMsg(info.getSchool());
        if (!Util.isNullOrBlank(info.getContact_addr()))
            mod_contact_addr.setMsg(info.getContact_addr());
    }

    @Override
    public void initData() {
        map = new HashMap<>();
        //修改头像
        doModifyIcon();
    }

    /**
     * 这个应该写在Activty中
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                if (event.getY() > (mieiv_nickname.getY() + mieiv_nickname.getHeight()) || event.getY() < mieiv_nickname.getY()) {
                    //修改昵称
                    mieiv_nickname.doModifyNickname(tv_error);
                }
                if (event.getY() > (mieiv_truename.getY() + mieiv_truename.getHeight()) || event.getY() < mieiv_truename.getY()) {
                    //修改昵称

                    mieiv_truename.doModifyTruename(tv_error);
                }
                if (event.getY() > (mieiv_qq.getY() + mieiv_qq.getHeight()) || event.getY() < mieiv_qq.getY()) {
                    //修改昵称

                    mieiv_qq.doModifyQQ(tv_error);
                }
                if (event.getY() > (mieiv_contact_phone.getY() + mieiv_contact_phone.getHeight()) || event.getY() < mieiv_contact_phone.getY()) {
                    //修改昵称

                    mieiv_contact_phone.doModifyContactPhone(tv_error);
                }

        }
        return super.onTouchEvent(event);
    }


    /**
     * 修改头像
     */
    private void doModifyIcon() {

        mod_icon.setRightIVOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mieivFinished();

                OnModelCallback<List<GetIconBean.IconListBean.CategoryBean>> callBack = new OnModelCallback<List<GetIconBean.IconListBean.CategoryBean>>() {
                    @Override
                    public void onModelSuccessed(List<GetIconBean.IconListBean.CategoryBean> list) {
                        mod_icon.setBottomViewData(list, map);
                        mod_icon.show(true);
                        mod_icon.setBtnSaveOnClickListnenr(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mod_icon.dismiss();
                                for (final String str : map.keySet()) {
                                    //map.keySet()返回的是所有key的值
                                    Boolean isSelect = map.get(str);//得到每个key对应value的值
                                    if (isSelect) {
                                        //提交选中的图片
                                        final String sskey = AccountManager.getInstance().getSskey();

                                        UserInfo userInfo = new UserInfo();
                                        //这里需要提交图片的名字
                                        LogUtils.e(TAG, str);
                                        userInfo.setIcon(str);

                                        OnModelCallback<Object> back = new OnModelCallback<Object>() {
                                            @Override
                                            public void onModelSuccessed(Object o) {
                                                mod_icon.setIcon(URLConstans.GET_ICON.ICON60, str);
                                            }

                                            @Override
                                            public void onModelFailed(String failedMsg) {
                                                LogUtils.e(TAG, "failed==" + failedMsg);
                                            }
                                        };
                                        ModifyUserInfoLogic.doModifyUserInfo(sskey, userInfo, ModifyUserInfoLogic.MODIFY_ICON, back);
                                    }
                                }


                            }
                        });
                    }

                    @Override
                    public void onModelFailed(String failedMsg) {
                        LogUtils.e(TAG, "failedMsg==" + failedMsg);
                        ToastUtils.showToast(MineInfoActivity.this, failedMsg);
                    }
                };

                GetIconListLogic.doGetIconList(AccountManager.getInstance().getSskey(), callBack);
            }
        });
    }

    @Override
    protected void initListener() {
        msv_sex.setRightIVOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msv_sex.show();
                mieivFinished();
            }
        });
        mod_birthday.setIvRightOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doModifyBirthday();
            }
        });

        mod_contact_addr.setIvRightOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyContactAddrActivity.actionStart(MineInfoActivity.this);
            }
        });
        mod_school.setIvRightOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifySchoolActivity.actionStart(MineInfoActivity.this);
            }
        });
    }


    /**
     * 让有输入的控件变为完成状态
     */
    private void mieivFinished() {
        mieiv_nickname.finishEdit();
        mieiv_truename.finishEdit();
        mieiv_qq.finishEdit();
        mieiv_contact_phone.finishEdit();
    }


    /**
     * 修改生日
     */
    private void doModifyBirthday() {

        logic_birday = new GetBirthdayLogic(this);
        logic_birday.setOnSubmitCallback(new GetBirthdayLogic.OnSubmitCallback() {
            @Override
            public void onSubmit(final int year, final int month, final int day) {
                String sskey = AccountManager.getInstance().getSskey();
                UserInfo info = new UserInfo();
                info.setBirthday_year(year);
                info.setBirthday_mon(month);
                info.setBirthday_day(day);
                //判读日期是否相等,不相同才修改


                OnModelCallback<Object> back = new OnModelCallback<Object>() {
                    @Override
                    public void onModelSuccessed(Object o) {
                        LogUtils.e(TAG, "DateUtils.getStringformYMD(year, month, day)==" + DateUtils.getStringformYMD(year, month, day));
                        mod_birthday.setMsg(DateUtils.getStringformYMD(year, month, day));
                    }

                    @Override
                    public void onModelFailed(String failedMsg) {
                        LogUtils.e(TAG, "failed==" + failedMsg);
                    }
                };
                ModifyUserInfoLogic.doModifyUserInfo(sskey, info, ModifyUserInfoLogic.MODIFY_BIRTHDAY, back);
            }
        });
        logic_birday.show();
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, MineInfoActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

    @Override
    public void onAccountUpdate(UserInfo info) {
        //当用户修改数据时回调修改页面数据
        LogUtils.e(TAG, info.toString());
        this.info = info;
        setViewData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //当在修改时,back上一个页面,关闭键盘
        mieiv_nickname.finishEdit();
        mieiv_qq.finishEdit();
        mieiv_qq.finishEdit();
        mieiv_contact_phone.finishEdit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Util.isNull(msv_sex))
            msv_sex.dismiss();
        if (!Util.isNull(mod_icon))
            mod_icon.dismiss();
        if (!Util.isNull(logic_birday))
            logic_birday.dismiss();
    }
}
