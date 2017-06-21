package com.alpha.alphaapp.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.geticons.GetIconBean;
import com.alpha.alphaapp.model.geticons.GetIconListLogic;
import com.alpha.alphaapp.model.modifyinfo.ModifyUserInfoLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.mine.addr.ModifyContactAddrActivity;
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

public class MineInfoActivity extends BaseActivity {
    private static final String TAG = "MineInfoActivity";
    private TitleLayout titleLayout;
    private ModifyIconView mod_icon;
    private ModifyInfoItemView mod_birthday, mod_school, mod_contact_addr;
    private ModifyInfoETItemView mieiv_nickname, mieiv_truename, mieiv_contact_phone, mieiv_qq;
    private ModifySexView msv_sex;
    private UserInfo info;
    private Map<String, Boolean> map;

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
            mod_birthday.setMsg(info.getBirthday_year() + "-" + info.getBirthday_mon() + "-" + info.getBirthday_day());
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
                                    if (isSelect == true) {
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
                                        String icon = str;
                                        Log.e(TAG, str);
                                        userInfo.setIcon(icon);
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
                ModifyContactAddrActivity.actionStart(MineInfoActivity.this, ModifyContactAddrActivity.RESQUEST_CODE, null);
            }
        });
        mod_school.setIvRightOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifySchoolActivity.actionStart(MineInfoActivity.this, ModifySchoolActivity.RESQUEST_CODE, null);
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

        boolean[] type = new boolean[]{true, true, true, false, false, false};
        TimePickerView pvTime = new TimePickerView.Builder(MineInfoActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(final Date date, View v) {//选中事件回调
                String sskey = AccountManager.getInstance().getSskey();
                UserInfo info = new UserInfo();
                final String format = "yyyy-MM-dd";
                String dateYMD = DateUtils.dateToString(date, format);
                String[] arr = dateYMD.split("-");
                info.setBirthday_year(Integer.parseInt(arr[0]));
                info.setBirthday_mon(Integer.parseInt(arr[1]));
                info.setBirthday_day(Integer.parseInt(arr[2]));
                ModifyUserInfoLogic.EditInfoCallBack call = new ModifyUserInfoLogic.EditInfoCallBack() {
                    @Override
                    public void onEditInfoSuccuss() {
                        mod_birthday.setMsg(DateUtils.dateToString(date, format));
                    }

                    @Override
                    public void onEditInfoFailed(String failMsg) {

                    }
                };
                ModifyUserInfoLogic.doModifyUserInfo(sskey, info, ModifyUserInfoLogic.MODIFY_BIRTHDAY, call);

            }
        })
                .setType(type)
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, MineInfoActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ModifyContactAddrActivity.RESQUEST_CODE) {
            String addr = data.getStringExtra(ModifyContactAddrActivity.CONTACT);
            if (!Util.isNullOrBlank(addr))
                mod_contact_addr.setMsg(addr);
        }
        if (requestCode == ModifySchoolActivity.RESQUEST_CODE) {
            String school = data.getStringExtra(ModifySchoolActivity.SCHOOL);
            if (!Util.isNullOrBlank(school))
                mod_school.setMsg(school);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        msv_sex.dismiss();
        mod_icon.dismiss();
    }
}
