package com.alpha.alphaapp.ui.v_1_1.mall.active;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.logic.BindActiveLogic;
import com.alpha.alphaapp.model.v_1_1.logic.UserScoreLogic;
import com.alpha.alphaapp.model.v_1_1.bean.UserScoreBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/7/20 15:33
 * Email : xiaokai090704@126.com
 * 激活页面
 */

public class ActiveActivity extends BaseActivity {
    private static final String TAG = "ActiveActivity";
    private static final String PRODUCT_ID = "product_id";
    private EditText et_active_code;
    private TextView tv_product_name;
    private Button btn_active;

    private int product_id;

    private Dialog dialog;


    @Override
    protected int getLayoutId() {
        product_id = getIntent().getIntExtra(PRODUCT_ID, -1);
        return R.layout.activity_active;
    }

    @Override
    protected void initView() {
        et_active_code = (EditText) findViewById(R.id.active_et_active_code);
        tv_product_name = (TextView) findViewById(R.id.active_tv_product_name);
        btn_active = (Button) findViewById(R.id.active_btn_active);
        dialog = DialogUtils.createTwoChoiceDialog(this, R.string.insure_active_this_code, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!Util.isNullOrBlank(et_active_code.getText().toString()) && product_id != -1) {
                    doActiveProduct(product_id);

                    LogUtils.e(TAG, "激活有产品ID");
                } else if (!Util.isNullOrBlank(et_active_code.getText().toString())) {
                    LogUtils.e(TAG, "激活没有产品ID");
                    doActivieNoProduct();
                }
            }
        });

        switch (product_id) {
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                tv_product_name.setText("爆裂飞车");
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
                tv_product_name.setText("零速争霸");
                break;
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                tv_product_name.setText("超级飞侠");
                break;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        btn_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNullOrBlank(et_active_code.getText().toString())) {
                    dialog.show();
                } else {
                    LogUtils.e("激活码不能为空!");
                }
            }
        });
    }

    public void doActivieNoProduct() {
        BindActiveLogic.doBindActiveCode(et_active_code.getText().toString().trim(), TypeConstants.PRODUCT_ID.NONE_PRODUCT, new OnModelCallback<UserScoreBean>() {
            @Override
            public void onModelSuccessed(UserScoreBean bean) {
                UserScoreLogic.doGetUserScoreInfo(TypeConstants.PRODUCT_ID.NONE_PRODUCT, null);
            }

            @Override
            public void onModelFailed(String failMsg) {
                LogUtils.e(failMsg);
            }
        });
    }

    /**
     * 激活对应产品的激活码
     */
    public void doActiveProduct(int product_id) {

        BindActiveLogic.doBindActiveCode(et_active_code.getText().toString().trim(), product_id, new OnModelCallback<UserScoreBean>() {
            @Override
            public void onModelSuccessed(UserScoreBean bean) {

                UserScoreLogic.doGetUserScoreInfo(TypeConstants.PRODUCT_ID.NONE_PRODUCT, null);
            }

            @Override
            public void onModelFailed(String failMsg) {
                LogUtils.e( failMsg);

            }
        });
    }

    public static void actionStart(Context context, int product_id) {
        Intent intent = new Intent(context, ActiveActivity.class);
        intent.putExtra(PRODUCT_ID, product_id);
        context.startActivity(intent);
    }
}
