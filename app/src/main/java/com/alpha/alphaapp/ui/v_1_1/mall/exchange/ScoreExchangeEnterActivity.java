package com.alpha.alphaapp.ui.v_1_1.mall.exchange;

import android.content.Context;
import android.content.Intent;

import com.alpha.alphaapp.R;

import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.logic.UserScoreLogic;
import com.alpha.alphaapp.model.v_1_1.bean.UserScoreBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.mall.ScoreEnterItem;

/**
 * Created by kenway on 17/7/17 13:58
 * Email : xiaokai090704@126.com
 * 积分兑换入口
 */

public class ScoreExchangeEnterActivity extends BaseActivity {
    private static final String TAG = "ScoreExchangeEnterActivity";

    private ScoreEnterItem msi_tranform_car, msi_speed, msi_super;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mall_score_exchange_enter;
    }

    @Override
    protected void initView() {
        msi_tranform_car = (ScoreEnterItem) findViewById(R.id.score_exchange_enter_msi_tranform_car);
        msi_speed = (ScoreEnterItem) findViewById(R.id.score_exchange_enter_msi_speed);
        msi_super = (ScoreEnterItem) findViewById(R.id.score_exchange_enter_msi_super);
    }

    @Override
    public void initData() {

//        获取爆裂飞车的积分
        UserScoreLogic.doGetUserScoreInfo(TypeConstants.PRODUCT_ID.TRANSFROM_CAR, new OnModelCallback<UserScoreBean>() {
            @Override
            public void onModelSuccessed(UserScoreBean bean) {
                msi_tranform_car.setDataforView(TypeConstants.PRODUCT_ID.TRANSFROM_CAR, bean.getScore());
            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showToast(ScoreExchangeEnterActivity.this, failMsg);
            }
        });
        //获取超级飞侠的积分
        UserScoreLogic.doGetUserScoreInfo(TypeConstants.PRODUCT_ID.SPEED, new OnModelCallback<UserScoreBean>() {
            @Override
            public void onModelSuccessed(UserScoreBean bean) {
                msi_speed.setDataforView(TypeConstants.PRODUCT_ID.SPEED, bean.getScore());
            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showToast(ScoreExchangeEnterActivity.this, failMsg);
            }
        });
////        //获取零速争霸的积分
        UserScoreLogic.doGetUserScoreInfo(TypeConstants.PRODUCT_ID.SUPER_WAVING, new OnModelCallback<UserScoreBean>() {
            @Override
            public void onModelSuccessed(UserScoreBean bean) {
                msi_super.setDataforView(TypeConstants.PRODUCT_ID.SUPER_WAVING, bean.getScore());
            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showToast(ScoreExchangeEnterActivity.this, failMsg);
            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ScoreExchangeEnterActivity.class);
        context.startActivity(intent);
    }
}
