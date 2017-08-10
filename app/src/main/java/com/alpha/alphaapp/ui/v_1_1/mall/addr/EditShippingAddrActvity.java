package com.alpha.alphaapp.ui.v_1_1.mall.addr;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.logic.ShippingAddrLogic;
import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.mine.logic.GetPCityAreaLogic;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/7/19 13:56
 * Email : xiaokai090704@126.com
 * 编辑收货地址
 */

public class EditShippingAddrActvity extends BaseActivity {

    private static final String TAG = "EditShippingAddrActvity";

    private TitleLayout titleLayout;
    private EditText et_name;
    private EditText et_phone;
    private LinearLayout layout_area;
    private TextView tv_area;
    private LinearLayout layout_detail;
    private EditText et_postcode;
    private EditText et_detail;
    private ToggleButton toggleButton;
    private Button btn_save;
    /**
     * 地区选中器
     */
    private GetPCityAreaLogic logic_pca;
    private Dialog dialog;

    private ShippingAddrBean bean;

    @Override
    protected int getLayoutId() {
        bean = (ShippingAddrBean) getIntent().getSerializableExtra("bean");
        return R.layout.activity_edit_shipping_addr;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.edit_shiping_addr_title);
        et_name = (EditText) findViewById(R.id.edit_shiping_addr_et_name);
        et_phone = (EditText) findViewById(R.id.edit_shiping_addr_et_phone);
        layout_area = (LinearLayout) findViewById(R.id.edit_shiping_addr_liner_area);
        tv_area = (TextView) findViewById(R.id.edit_shiping_addr_tv_area);
        layout_detail = (LinearLayout) findViewById(R.id.edit_shiping_addr_liner_detail);
        et_postcode = (EditText) findViewById(R.id.edit_shiping_addr_et_postcode);
        et_detail = (EditText) findViewById(R.id.edit_shiping_addr_et_detail);
        toggleButton = (ToggleButton) findViewById(R.id.edit_shiping_addr_toggle_setdefault);
        btn_save = (Button) findViewById(R.id.edit_shiping_addr_btn_save);
        logic_pca = new GetPCityAreaLogic(this);


    }

    @Override
    public void initData() {
        et_name.setText(bean.getName());
        et_phone.setText(bean.getMobile());
        tv_area.setText(bean.getProvince() + bean.getCity() + bean.getArea());
        et_postcode.setText(bean.getPost_code());
        et_detail.setText(bean.getAddr_detail());
        if (bean.getDefault_addr() == 1) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }

    }

    @Override
    protected void initListener() {
        //判读是否有信息修改,如果没有,直接退回到上一页,如果有弹出提示框
        titleLayout.setOnBackListener(new TitleLayout.OnBackListener() {
            @Override
            public void onBackLister() {
                if (isModifyInfo()) {
                    dialog = DialogUtils.createTwoChoiceDialog(EditShippingAddrActvity.this, R.string.info_had_modify_if_return, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    dialog.show();
                } else {
                    finish();
                }
            }
        });
        layout_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出底部对话框显示省市区
                logic_pca.show();
            }
        });

        logic_pca.setBtnSaveOnListener(new GetPCityAreaLogic.OnBtnSaveListener() {
            @Override
            public void onSubmit(String province, String city, String area) {
                tv_area.setText(province + city + area);
                tv_area.setTextColor(Color.BLACK);
                bean.setProvince(province);
                bean.setCity(city);
                bean.setArea(area);
            }
        });

        layout_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.setFoucus(et_detail, EditShippingAddrActvity.this);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doEditShippingAddr();

            }
        });
    }


    /**
     * 判读是否有修改信息==判断是否与传递过来信息相同
     * 1.判断收货人是否修改
     * 2.判断电话号码是否修改
     * 3.判断是否选择的区域是否
     * 4.判断详细地址是否修改
     * 5.判断邮编是否修改
     *
     * @return
     */
    public boolean isModifyInfo() {
        if (!et_name.getText().toString().equals(bean.getName())) {

            return true;
        }
        if (!et_phone.getText().toString().equals(bean.getMobile())) {
            return true;
        }

        if (!tv_area.getText().toString().equals(bean.getProvince() + bean.getCity() + bean.getArea())) {

            return true;
        }

        if (!et_detail.getText().toString().equals(bean.getAddr_detail())) {
            return true;
        }
        if (!et_postcode.getText().toString().equals(bean.getPost_code())) {

            return true;
        }
        return false;
    }

    /**
     * 修改地址
     * 1.判断是否有收货人
     * 2.判断电话号码是否正确
     * 3.判断是否选择的区域不为空
     * 4.判断邮编是否为空
     * 5.详细地址不小于5个字
     * 6.判断是否设置为默认地址
     */
    private void doEditShippingAddr() {

        //1.判断是否有收货人
        if (Util.isNullOrBlank(et_name.getText().toString())) {

            LogUtils.e("收货人不能为空");

            return;
        } else {
            bean.setName(et_name.getText().toString());
        }
        // 2.判断电话号码是否正确
        if (!StringUtils.isPhoneNum(et_phone.getText().toString())) {
            LogUtils.e("请填写11位有效手机号");

            return;
        } else {
            bean.setMobile(et_phone.getText().toString());
        }

        // 3.判断是否选择的区域不为空
        if (tv_area.getText().toString().length() < 3 || tv_area.getText().toString().length() == 3) {

            LogUtils.e( "请选择省市区");
            return;
        }
        // 4.判断邮编是否为空
        if (!StringUtils.isPost_Code(et_postcode.getText().toString())) {
            LogUtils.e("请填写邮编");
            return;
        } else {
            bean.setPost_code(et_postcode.getText().toString());
        }
        //5.详细地址不小于5个字
        if (et_detail.getText().toString().length() < 5) {
            LogUtils.e("详细地址信息要大于5个字");

            return;
        } else {
            bean.setAddr_detail(et_detail.getText().toString());
        }
        //  6.判断是否设置为默认地址
        if (toggleButton.isChecked()) {
            bean.setDefault_addr(1);
        } else {
            bean.setDefault_addr(0);
        }



        ShippingAddrLogic.doEditAddress(bean, new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                //回到管理收货地址页面并Toast

                finish();
            }

            @Override
            public void onModelFailed(String failedMsg) {
                //Toast失败信息
                LogUtils.e(failedMsg);

            }
        });

    }

    public static void actionStart(Context context, ShippingAddrBean bean) {
        Intent intent = new Intent(context, EditShippingAddrActvity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }
}
