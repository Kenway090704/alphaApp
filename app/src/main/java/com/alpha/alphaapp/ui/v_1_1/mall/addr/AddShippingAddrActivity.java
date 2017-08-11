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
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.mine.logic.GetPCityAreaLogic;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/7/19 10:04
 * Email : xiaokai090704@126.com
 */

public class AddShippingAddrActivity extends BaseActivity {

    private static final String TAG = "AddShippingAddrActivity";

    private TitleLayout titleLayout;
    private EditText et_name;
    private EditText et_phone;
    private LinearLayout layout_area;
    private TextView tv_area;
    private EditText et_postcode;
    private LinearLayout layout_detail;
    private EditText et_detail;
    private ToggleButton toggleButton;
    private Button btn_save;
    /**
     * 地区选中器
     */
    private GetPCityAreaLogic logic_pca;
    private ShippingAddrBean addShippingBean;

    private Dialog dialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_shipping;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.add_shiping_addr_title);
        et_name = (EditText) findViewById(R.id.add_shiping_addr_et_name);
        et_phone = (EditText) findViewById(R.id.add_shiping_addr_et_phone);
        layout_area = (LinearLayout) findViewById(R.id.add_shiping_addr_liner_area);
        tv_area = (TextView) findViewById(R.id.add_shiping_addr_tv_area);
        et_postcode = (EditText) findViewById(R.id.add_shiping_addr_et_postcode);
        layout_detail = (LinearLayout) findViewById(R.id.add_shiping_addr_liner_detail);
        et_detail = (EditText) findViewById(R.id.add_shiping_addr_et_detail);
        toggleButton = (ToggleButton) findViewById(R.id.add_shiping_addr_toggle_setdefault);
        btn_save = (Button) findViewById(R.id.add_shiping_addr_btn_save);
        logic_pca = new GetPCityAreaLogic(this);
    }

    @Override
    public void initData() {
        addShippingBean = new ShippingAddrBean();
    }

    @Override
    protected void initListener() {
        titleLayout.setOnBackListener(new TitleLayout.OnBackListener() {
            @Override
            public void onBackLister() {
                if (isInputMessage()) {
                    dialog = DialogUtils.createTwoChoiceDialog(AddShippingAddrActivity.this, R.string.info_had_modify_if_return, new View.OnClickListener() {
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
                KeyBoardUtils.closeKeybord(et_name, AddShippingAddrActivity.this);
                KeyBoardUtils.closeKeybord(et_phone, AddShippingAddrActivity.this);
                //弹出底部对话框
                logic_pca.show();
            }
        });

        logic_pca.setBtnSaveOnListener(new GetPCityAreaLogic.OnBtnSaveListener() {
            @Override
            public void onSubmit(String province, String city, String area) {
                tv_area.setText(province + city + area);
                tv_area.setTextColor(Color.BLACK);
                addShippingBean.setProvince(province);
                addShippingBean.setCity(city);
                addShippingBean.setArea(area);
            }
        });

        layout_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.setFoucus(et_detail, AddShippingAddrActivity.this);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAddShippingAddr();

            }
        });
    }

    /**
     * 判读是否填写信息
     * 1.判断是否有收货人
     * 2.判断电话号码是否正确
     * 3.判断是否选择的区域不为空
     * 4.详细地址不小于5个字
     * 5.判断是否设置为默认地址
     *
     * @return
     */
    public boolean isInputMessage() {
        if (!Util.isNullOrBlank(et_name.getText().toString())) {
            return true;
        }
        if (!Util.isNullOrBlank(et_phone.getText().toString())) {
            return true;
        }

        if (!Util.isNullOrBlank(et_detail.getText().toString()) || tv_area.getText().toString().length() > 3) {
            return true;
        }

        if (!Util.isNullOrBlank(et_detail.getText().toString())) {
            return true;
        }

        if (!Util.isNullOrBlank(et_postcode.getText().toString())) {
            return true;
        }
        return false;
    }

    /**
     * 添加地址
     * 1.判断是否有收货人
     * 2.判断电话号码是否正确
     * 3.判断是否选择的区域不为空
     * 4.判断邮编是否为空
     * 5.详细地址不小于5个字
     * 6.判断是否设置为默认地址
     */
    private void doAddShippingAddr() {
        //1.判断是否有收货人
        if (Util.isNullOrBlank(et_name.getText().toString())) {
            ToastUtils.showToast(AddShippingAddrActivity.this, "收货人不能为空");
            return;
        } else {
            addShippingBean.setName(et_name.getText().toString());
        }
        // 2.判断电话号码是否正确
        if (!StringUtils.isPhoneNum(et_phone.getText().toString())) {
            ToastUtils.showToast(AddShippingAddrActivity.this, "请填写11位有效手机号");
            return;
        } else {
            addShippingBean.setMobile(et_phone.getText().toString());
        }

        // 3.判断是否选择的区域不为空
        if (tv_area.getText().toString().length() < 3 || tv_area.getText().toString().length() == 3) {
            ToastUtils.showToast(AddShippingAddrActivity.this, "请选择省市区");
            return;
        }
        // 4.判断邮编是否为空
        if (!StringUtils.isPost_Code(et_postcode.getText().toString())) {
            ToastUtils.showToast(AddShippingAddrActivity.this, "请填写邮编");
            return;
        } else {
            addShippingBean.setPost_code(et_postcode.getText().toString());
        }
        //5.详细地址不小于5个字
        if (et_detail.getText().toString().length() < 5) {
            ToastUtils.showToast(AddShippingAddrActivity.this, "详细地址信息要大于5个字");
            return;
        } else {
            addShippingBean.setAddr_detail(et_detail.getText().toString());
        }
        //6.判断是否设置为默认地址
        if (toggleButton.isChecked()) {
            addShippingBean.setDefault_addr(1);
        } else {
            addShippingBean.setDefault_addr(0);
        }



        ShippingAddrLogic.doAddAddress(addShippingBean, new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                //回到管理收货地址页面并Toast
                ToastUtils.showToast(AddShippingAddrActivity.this, "添加收货地址成功");
                finish();
            }

            @Override
            public void onModelFailed(String failMsg) {
                //Toast失败信息
                LogUtils.e(failMsg);

            }
        });


    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddShippingAddrActivity.class);
        context.startActivity(intent);
    }
}
