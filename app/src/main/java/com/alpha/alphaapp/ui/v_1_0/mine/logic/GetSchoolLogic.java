package com.alpha.alphaapp.ui.v_1_0.mine.logic;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alpha.alphaapp.R;

import com.alpha.alphaapp.model.v_1_0.bean.SchoolBean;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.ui.widget.wheel.WheelView;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.tandong.bottomview.view.BottomView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by kenway on 17/6/19 16:41
 * Email : xiaokai090704@126.com
 * 获取学校的的logic
 */

public class GetSchoolLogic {

    private static final String TAG = "GetSchoolLogic";

    private ArrayList<SchoolBean> options1Items = new ArrayList<>();

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;

    private Context context;
    private OnBtnSaveListener listener;
    private SchoolBean bean;

    private OptionsPickerView pvOptions;

    boolean isStop = false;

    public GetSchoolLogic(Context context, SchoolBean bean) {
        this.context = context;
        this.bean = bean;
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                          //开始解析数据
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                getSchoolList(bean.getProvince(), bean.getCity(), bean.getArea(), bean.getTxt());
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
//                  //解析数据成功
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    //解析数据失败
                    break;

            }
        }
    };

    public void show() {
        //弹出底部对话框
        if (isLoaded) {
            // ShowPickerView();
            //自定义滚轮选取学校
            showWheelView();
        } else {
            LogUtils.e("数据暂未解析成功，请等待");

        }
    }

    /**
     * 自定义的WheelView
     */
    private void showWheelView() {
        final BottomView bottomView = new BottomView(context,
                R.style.BottomViewTheme_Defalut, R.layout.widget_my_options_wheel_school);
        bottomView.setAnimation(R.style.BottomToTopAnim);//设置动画，可选
        View btmView = bottomView.getView();
        EditText et = (EditText) btmView.findViewById(R.id.dialog_my_wheel_opt_school_et_school);
        et.setFocusable(false);
        final WheelView wheelView = (WheelView) btmView.findViewById(R.id.dialog_my_wheel_opt_wheelView);
        final Button btnSave = (Button) btmView.findViewById(R.id.dialog_my_wheel_opt_school_btn_save);
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < options1Items.size(); i++) {
            lists.add(options1Items.get(i).getSchool());
        }
        wheelView.lists(lists)
                .fontSize(18)
                .showCount(5)
                .select(0)
                .setSelectLocation(false)
                .listener(new WheelView.OnWheelViewItemSelectListener() {
                    @Override
                    public void onItemSelect(final int index) {
                       LogUtils.e(TAG, "current select" + wheelView.getSelectItem());
                    }
                }).build();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = wheelView.getSelectItem();
                String school = options1Items.get(index).getPickerViewText();
                if (listener != null) {
                    bottomView.dismissBottomView();
                    listener.onSubmit(school);
                }
            }
        });
        bottomView.showBottomView(true);
    }


    /**
     * 点击确认按钮时事件监听
     *
     * @param listener
     */
    public void setBtnSaveOnListener(OnBtnSaveListener listener) {
        this.listener = listener;
    }

    public interface OnBtnSaveListener {
        void onSubmit(String school);
    }

    /**
     * 弹出选择器
     */
    private void ShowPickerView() {// 弹出选择器

        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别选中的学校
                String school = options1Items.get(options1).getPickerViewText();
                if (listener != null) {
                    listener.onSubmit(school);
                }

            }
        }).setLayoutRes(R.layout.widget_options_school, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final Button btn_save = (Button) v.findViewById(R.id.dialog_opt_school_btn_save);
                EditText et_school = (EditText) v.findViewById(R.id.dialog_opt_school_et_school);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvOptions.returnData();
                        pvOptions.dismiss();
                    }
                });


            }
        })
                .setBgColor(0xFFF0F0F0)//滚轮背景颜色 Night mode
                .setContentTextSize(16)
                .setOutSideCancelable(true)// default is true
                .build();

        pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.show();
    }

    /**
     * 搜索学校
     *
     * @param province
     * @param city
     * @param area
     * @param searchMsg
     * @return
     */
    private void getSchoolList(String province, String city, String area, String searchMsg) {
        HashMap<String, String> map = new HashMap<>();
        map.put("p", province);
        map.put("c", city);
        map.put("area", area);
        map.put("text", searchMsg);
        LogUtils.e(TAG, "province==" + province + ",city==" + city + ",area==" + area + ",searchMsg==" + searchMsg);
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                options1Items = (ArrayList<SchoolBean>) SchoolBean.arraySchoolBeanFromData(result);
                LogUtils.e(TAG, options1Items.toString());
                mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
            }

            @Override
            public void onReqFailed(String errorMsg) {

            }
        };
        RequestManager.getInstance(ApplicationContext.getCurrentContext()).requestPostByAsynWithForm(URLConstans.GET_ADDR_URL.SCHOOL, map, call);

    }

}
