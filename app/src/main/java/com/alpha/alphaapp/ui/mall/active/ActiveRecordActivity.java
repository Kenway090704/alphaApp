package com.alpha.alphaapp.ui.mall.active;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.BindActiveLogic;
import com.alpha.alphaapp.model.mall.bean.CdkDatasBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.mall.adapter.ActiveRecordAdapter;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/20 18:19
 * Email : xiaokai090704@126.com
 */

public class ActiveRecordActivity extends BaseActivity {
    private static final String TAG = "ActiveRecordActivity";
    private static final String PRODUCT_ID = "product_id";
    private int product_id;
    private TextView tv_product;
    private RecyclerView rv;
    private ActiveRecordAdapter adapter;
    private List<CdkDatasBean.CdkItemsBean> itemsBeen;

    @Override
    protected int getLayoutId() {
        product_id = getIntent().getIntExtra(PRODUCT_ID, -1);
        return R.layout.activity_active_record;
    }
    @Override
    protected void initView() {
        tv_product = (TextView) findViewById(R.id.active_record_tv_product);
        setProductText();
        rv = (RecyclerView) findViewById(R.id.active_record_rv);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        itemsBeen = new ArrayList<>();
        adapter = new ActiveRecordAdapter(this, itemsBeen);
        rv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        if (product_id != -1) {
            doGetActiveRecord();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        doGetActiveRecord();
    }

    @Override
    protected void initListener() {

    }

    protected void setProductText() {
        switch (product_id) {
            case TypeConstants.PRODUCT_ID.NONE_PRODUCT:
                tv_product.setText("当前为debug模式");
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
                tv_product.setText("零速争霸");
                break;
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                tv_product.setText("爆裂飞车");
                break;
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                tv_product.setText("超级飞侠");
                break;
            default:
                tv_product.setText("激活记录");
                break;

        }

    }


    /**
     * 获取激活记录
     */
    private void doGetActiveRecord() {
        BindActiveLogic.doGetActiveRecord(product_id, new OnModelCallback<List<CdkDatasBean>>() {
            @Override
            public void onModelSuccessed(List<CdkDatasBean> beanList) {
                itemsBeen.clear();
                for (int i = 0; i < beanList.size(); i++) {
                    String lot_num = beanList.get(i).getLot_number();
                    CdkDatasBean bean = beanList.get(i);
                    for (int j = 0; j < bean.getCdk_items().size(); j++) {
                        CdkDatasBean.CdkItemsBean cdkItemsBean = bean.getCdk_items().get(j);
                        cdkItemsBean.setLot_number(lot_num);
                        itemsBeen.add(cdkItemsBean);
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showLong(ActiveRecordActivity.this, failMsg);
            }
        });
    }


    public static void actionStart(Context context, int product_id) {
        Intent intent = new Intent(context, ActiveRecordActivity.class);
        intent.putExtra(PRODUCT_ID, product_id);
        context.startActivity(intent);
    }
}