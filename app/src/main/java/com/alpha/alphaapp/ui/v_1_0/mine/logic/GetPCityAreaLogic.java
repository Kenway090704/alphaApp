package com.alpha.alphaapp.ui.v_1_0.mine.logic;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import java.util.ArrayList;

/**
 * Created by kenway on 17/6/19 16:41
 * Email : xiaokai090704@126.com
 * <p>下载城市数据<p/>
 */

public class GetPCityAreaLogic {

    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private Context context;
    private OnBtnSaveListener listener;
    private OptionsPickerView pvOptions;

    public GetPCityAreaLogic(Context context) {
        this.context = context;
        options1Items.addAll(GetPCityAreaData.itme1datas);
        options2Items.addAll(GetPCityAreaData.item2datas);
        options3Items.addAll(GetPCityAreaData.item3datas);
    }

    public void show() {
        //弹出底部对话框
        ShowPickerView();
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
        void onSubmit(String province, String city, String area);
    }

    /**
     * 弹出选择器
     */
    private void ShowPickerView() {// 弹出选择器

        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (listener != null) {
                    listener.onSubmit(options1Items.get(options1), options2Items.get(options1).get(options2), options3Items.get(options1).get(options2).get(options3));
                }

            }
        })
                .setLayoutRes(R.layout.widget_options_pca, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final Button btn_save = (Button) v.findViewById(R.id.dialog_opt_pca_btn_save);
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
                .setContentTextSize(20)
                .setOutSideCancelable(true)// default is true
                .build();
        if (options1Items.size() == 0 || options2Items.size() == 0 || options3Items.size() == 0) {

            LogUtils.e("地址数据获取有误,无法获取数据");
            return;
        }
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


}
