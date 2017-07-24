package com.alpha.alphaapp.ui.mall.exchange;

import android.content.Context;
import android.content.Intent;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.UserScoreLogic;
import com.alpha.alphaapp.model.mall.bean.UserScoreBean;
import com.alpha.alphaapp.model.mall.bean.ScoreExchangeEnterItemInfo;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.mall.ScoreExchangeEnterItem;

/**
 * Created by kenway on 17/7/17 13:58
 * Email : xiaokai090704@126.com
 * 积分兑换入口
 */

public class ScoreExchangeEnterActivity extends BaseActivity {
    private static final String TAG = "ScoreExchangeEnterActivity";

    private ScoreExchangeEnterItem msi_tranform_car, msi_speed, msi_super;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mall_score_exchange_enter;
    }

    @Override
    protected void initView() {
        msi_tranform_car = (ScoreExchangeEnterItem) findViewById(R.id.score_exchange_enter_msi_tranform_car);
        msi_speed = (ScoreExchangeEnterItem) findViewById(R.id.score_exchange_enter_msi_speed);
        msi_super = (ScoreExchangeEnterItem) findViewById(R.id.score_exchange_enter_msi_super);
    }

    @Override
    public void initData() {


        final ScoreExchangeEnterItemInfo info_tranform_car = new ScoreExchangeEnterItemInfo();
        info_tranform_car.setSrcId(R.drawable.tranform_logo);
        info_tranform_car.setWeb_url(URLConstans.OFFICAL_WEB_URL.TRANSFORM_CAR);

        final ScoreExchangeEnterItemInfo info_speed = new ScoreExchangeEnterItemInfo();
        info_speed.setSrcId(R.drawable.speed_logo);
        info_speed.setWeb_url(URLConstans.OFFICAL_WEB_URL.SPEED);



        final ScoreExchangeEnterItemInfo info_super = new ScoreExchangeEnterItemInfo();
        info_super.setSrcId(R.drawable.super_logo);
        info_super.setWeb_url(URLConstans.OFFICAL_WEB_URL.SUPER_WAVING);

        UserScoreLogic.doGetUserScoreInfo(new OnModelCallback<UserScoreBean>() {
            @Override
            public void onModelSuccessed(UserScoreBean bean) {

                info_tranform_car.setTv_curr_score(bean.getScore() + "积分");
                msi_tranform_car.setDataforView(info_tranform_car);


                info_speed.setTv_curr_score(bean.getScore() + "积分");
                msi_speed.setDataforView(info_speed);

                info_super.setTv_curr_score(bean.getScore() + "积分");
                msi_super.setDataforView(info_super);
            }

            @Override
            public void onModelFailed(String failMsg) {

            }
        });




    }

    @Override
    protected void initListener() {

    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ScoreExchangeEnterActivity.class);
        context.startActivity(intent);
    }
}
