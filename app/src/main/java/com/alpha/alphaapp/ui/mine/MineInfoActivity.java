package com.alpha.alphaapp.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.geticons.GetIconBean;
import com.alpha.alphaapp.model.geticons.GetIconListLogic;
import com.alpha.alphaapp.model.modifyinfo.ModifyUserInfoLogic;
import com.alpha.alphaapp.ui.AccountChangeActivity;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.mine.addr.ModifyContactAddrActivity;
import com.alpha.alphaapp.ui.mine.logic.GetBirthdayLogic;
import com.alpha.alphaapp.ui.mine.school.ModifySchoolActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.mine.ModifyIconView;
import com.alpha.alphaapp.ui.widget.mine.ModifyInfoETItemView;
import com.alpha.alphaapp.ui.widget.mine.ModifyInfoItemView;
import com.alpha.alphaapp.ui.widget.mine.ModifySexView;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.bigkoo.pickerview.TimePickerView;


import java.util.Calendar;
import java.util.Date;
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

        setViewData();
    }

    private void setViewData() {
        if (!Util.isNullOrBlank(info.getIcon()))
            mod_icon.setIcon(URLConstans.GET_ICON.ICON60, info.getIcon());
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
                    mieiv_nickname.doModifyNickname();
                }
                if (event.getY() > (mieiv_truename.getY() + mieiv_truename.getHeight()) || event.getY() < mieiv_truename.getY()) {
                    //修改昵称
                    mieiv_truename.doModifyTruename();
                }
                if (event.getY() > (mieiv_qq.getY() + mieiv_qq.getHeight()) || event.getY() < mieiv_qq.getY()) {
                    //修改昵称
                    mieiv_qq.doModifyQQ();
                }
                if (event.getY() > (mieiv_contact_phone.getY() + mieiv_contact_phone.getHeight()) || event.getY() < mieiv_contact_phone.getY()) {
                    //修改昵称
                    mieiv_contact_phone.doModifyContactPhone();
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
                GetIconListLogic.GetIconCallBack callBack = new GetIconListLogic.GetIconCallBack() {
                    @Override
                    public void onGetIconListSuccuss(String baseUrl, List<GetIconBean.IconListBean.CategoryBean> list) {
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
                                        ModifyUserInfoLogic.EditInfoCallBack callBack1 = new ModifyUserInfoLogic.EditInfoCallBack() {
                                            @Override
                                            public void onEditInfoSuccuss() {
                                                mod_icon.setIcon(URLConstans.GET_ICON.ICON60, str);
                                            }

                                            @Override
                                            public void onEditInfoFailed(String failMsg) {

                                            }
                                        };
                                        UserInfo userInfo = new UserInfo();
                                        //这里需要提交图片的名字
                                        Log.e(TAG, str);
                                        userInfo.setIcon(str);
                                        ModifyUserInfoLogic.doModifyUserInfo(sskey, userInfo, ModifyUserInfoLogic.MODIFY_ICON, callBack1);
                                    }
                                }


                            }
                        });
                    }

                    @Override
                    public void onGetIconListFailed(String failMsg) {

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
                ModifyUserInfoLogic.EditInfoCallBack call = new ModifyUserInfoLogic.EditInfoCallBack() {
                    @Override
                    public void onEditInfoSuccuss() {

                        Log.e(TAG, "DateUtils.getStringformYMD(year, month, day)==" + DateUtils.getStringformYMD(year, month, day));
                        mod_birthday.setMsg(DateUtils.getStringformYMD(year, month, day));
                    }

                    @Override
                    public void onEditInfoFailed(String failMsg) {

                    }
                };
                ModifyUserInfoLogic.doModifyUserInfo(sskey, info, ModifyUserInfoLogic.MODIFY_BIRTHDAY, call);
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
        Log.e(TAG, info.toString());
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
