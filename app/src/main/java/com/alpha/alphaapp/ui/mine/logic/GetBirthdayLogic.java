package com.alpha.alphaapp.ui.mine.logic;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.model.modifyinfo.ModifyUserInfoLogic;
import com.alpha.alphaapp.ui.mine.MineInfoActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kenway on 17/7/4 15:17
 * Email : xiaokai090704@126.com
 * 选择日期滚动轮自定义
 */

public class GetBirthdayLogic {
    private static final String TAG = "GetBirthdayLogic";
    private Context context;
    private OnSubmitCallback call;
    private TimePickerView pvCustomTime;

    public GetBirthdayLogic(Context context) {
        this.context = context;
    }

    public void show() {
        //弹出底部对话框
        ShowPickerView();
    }

    public void dismiss() {
        if (!Util.isNull(pvCustomTime)) {
            pvCustomTime.dismiss();
        }
    }

    /**
     * 点击确认按钮时事件监听
     *
     * @param call
     */
    public void setOnSubmitCallback(OnSubmitCallback call) {
        this.call = call;
    }

    public interface OnSubmitCallback {
        void onSubmit(int year, int month, int day);
    }

    /**
     * 弹出选择器
     */
    private void ShowPickerView() {// 弹出选择器
        boolean[] type = new boolean[]{true, true, true, false, false, false};
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        //更新为今天
        Calendar endDate = Calendar.getInstance();
        endDate.set(2050, 7, 4);
        pvCustomTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(final Date date, View v) {//选中事件回调
                final String format = "yyyy-MM-dd";
                String dateYMD = DateUtils.dateToString(date, format);
                String[] arr = dateYMD.split("-");
//                //修改日期
//                doModifyBirthdy(arr);
                if (!Util.isNull(call))
                    call.onSubmit(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));

            }
        })
                .setLayoutRes(R.layout.widget_options_birthday, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
                        final TextView tv_submit = (TextView) v.findViewById(R.id.tv_submit);
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                        tv_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(type)
                .setContentSize(20)//滚轮文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvCustomTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvCustomTime.show();

    }



}
