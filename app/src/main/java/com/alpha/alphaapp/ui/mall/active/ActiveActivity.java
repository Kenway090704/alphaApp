package com.alpha.alphaapp.ui.mall.active;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.BindActiveLogic;
import com.alpha.alphaapp.model.mall.UserScoreLogic;
import com.alpha.alphaapp.model.mall.bean.UserScoreBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/7/20 15:33
 * Email : xiaokai090704@126.com
 * 激活页面
 */

public class ActiveActivity extends BaseActivity {
    private static final String TAG = "ActiveActivity";
    private EditText et_code_1;
    private EditText et_code_2;
    private Button btn_active;

    private int product_id;

    private Dialog dialog;


    @Override
    protected int getLayoutId() {
        product_id = getIntent().getIntExtra("product_id", -1);
        return R.layout.activity_active;
    }

    @Override
    protected void initView() {
        et_code_1 = (EditText) findViewById(R.id.active_et_active_code);
        et_code_2 = (EditText) findViewById(R.id.active_et_active_code_2);
        btn_active = (Button) findViewById(R.id.active_btn_active);
        dialog = DialogUtils.createTwoChoiceDialog(this, R.string.insure_active_this_code, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!Util.isNullOrBlank(et_code_1.getText().toString()) && !Util.isNullOrBlank(et_code_2.getText().toString())) {
                    doActiveProduct();
                    Log.e(TAG, "激活有产品ID");
                } else if (!Util.isNullOrBlank(et_code_1.getText().toString())) {
                    Log.e(TAG, "激活没有产品ID");
                    doActivieNoProduct();
                } else {
                    //取消对话框
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        btn_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isActiveCode(et_code_1.getText().toString())) {
                    dialog.show();
                } else {
                    ToastUtils.showLong(ActiveActivity.this, "激活码格式错误!");
                }
            }
        });
    }

    public void doActivieNoProduct() {
        BindActiveLogic.doBindActiveCode("CA" + et_code_1.getText().toString().trim(), TypeConstants.PRODUCT_ID.NONE_PRODUCT, new OnModelCallback<UserScoreBean>() {
            @Override
            public void onModelSuccessed(UserScoreBean bean) {

                ToastUtils.showLong(ActiveActivity.this, "绑定激活成功");
                UserScoreLogic.doGetUserScoreInfo(null);
            }

            @Override
            public void onModelFailed(String failMsg) {

                ToastUtils.showLong(ActiveActivity.this, failMsg);
            }
        });
    }

    /**
     * 激活对应产品的激活码
     */
    public void doActiveProduct() {

        BindActiveLogic.doBindActiveCode("CA" + et_code_1.getText().toString().trim(), Integer.parseInt(et_code_2.getText().toString().trim()), new OnModelCallback<UserScoreBean>() {
            @Override
            public void onModelSuccessed(UserScoreBean bean) {
                ToastUtils.showLong(ActiveActivity.this, "绑定激活成功");
                UserScoreLogic.doGetUserScoreInfo(null);
            }

            @Override
            public void onModelFailed(String failMsg) {

                ToastUtils.showLong(ActiveActivity.this, failMsg);
            }
        });
    }

    public static void actionStart(Context context, int product_id) {
        Intent intent = new Intent(context, ActiveActivity.class);
        intent.putExtra("product_id", product_id);
        context.startActivity(intent);
    }
}
