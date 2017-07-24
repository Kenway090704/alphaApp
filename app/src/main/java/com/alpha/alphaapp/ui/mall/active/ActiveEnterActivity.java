package com.alpha.alphaapp.ui.mall.active;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.BindActiveLogic;
import com.alpha.alphaapp.model.mall.bean.ActiveEnterItemInfo;
import com.alpha.alphaapp.model.mall.bean.CdkDatasBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.mall.ActiveEnterItem;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.List;

/**
 * Created by kenway on 17/7/20 13:59
 * Email : xiaokai090704@126.com
 * 激活入口
 */

public class ActiveEnterActivity extends BaseActivity {

    private static final String TAG = "ActiveEnterActivity";
    private ActiveEnterItem aei_tranform_car, aei_speed, aei_super;
    private ActiveEnterItemInfo info_tranformcar;
    private ActiveEnterItemInfo info_speed;
    private ActiveEnterItemInfo info_super;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_active_enter;
    }

    @Override
    protected void initView() {
        aei_tranform_car = (ActiveEnterItem) findViewById(R.id.active_enter_aei_tranform_car);
        aei_speed = (ActiveEnterItem) findViewById(R.id.active_enter_aei_speed);
        aei_super = (ActiveEnterItem) findViewById(R.id.active_enter_aei_super);
    }

    @Override
    public void initData() {
        info_tranformcar = new ActiveEnterItemInfo();
        info_tranformcar.setSrcId(R.drawable.tranform_logo);
        info_tranformcar.setProduct_id(TypeConstants.PRODUCT_ID.TRANSFROM_CAR);
        info_tranformcar.setWeb_url(URLConstans.OFFICAL_WEB_URL.TRANSFORM_CAR);


        info_speed = new ActiveEnterItemInfo();
        info_speed.setSrcId(R.drawable.speed_logo);
        info_speed.setProduct_id(TypeConstants.PRODUCT_ID.SPEED);
        info_speed.setWeb_url(URLConstans.OFFICAL_WEB_URL.SPEED);


        info_super = new ActiveEnterItemInfo();
        info_super.setSrcId(R.drawable.super_logo);
        info_super.setProduct_id(TypeConstants.PRODUCT_ID.SUPER_WAVING);
        info_super.setWeb_url(URLConstans.OFFICAL_WEB_URL.SUPER_WAVING);


        doGetActiveCount(aei_tranform_car, info_tranformcar, TypeConstants.PRODUCT_ID.TRANSFROM_CAR);
        doGetActiveCount(aei_speed, info_speed, TypeConstants.PRODUCT_ID.SPEED);
        doGetActiveCount(aei_super, info_super, TypeConstants.PRODUCT_ID.SUPER_WAVING);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void doGetActiveCount(final ActiveEnterItem enterItem, final ActiveEnterItemInfo info, int product_id) {
        BindActiveLogic.doGetActiveRecord(product_id, new OnModelCallback<List<CdkDatasBean>>() {
            @Override
            public void onModelSuccessed(List<CdkDatasBean> beanList) {
                int active_count = 0;
                for (int i = 0; i < beanList.size(); i++) {
                    active_count += beanList.get(i).getCdk_items().size();
                }
                info.setTv_active_time(active_count + "");
                enterItem.setDataforView(info);
            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showToast(ActiveEnterActivity.this, failMsg);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ActiveEnterActivity.class);
        context.startActivity(intent);
    }
}
