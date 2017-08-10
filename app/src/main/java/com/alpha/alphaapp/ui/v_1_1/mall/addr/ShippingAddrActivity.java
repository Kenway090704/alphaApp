package com.alpha.alphaapp.ui.v_1_1.mall.addr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.logic.ShippingAddrLogic;
import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.ShipingAddrAdapter;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/17 14:03
 * Email : xiaokai090704@126.com
 * 收货地址管理
 */

public class ShippingAddrActivity extends BaseActivity {

    private static final String TAG = "ShippingAddrActivity";
    private Button btn_add_addr;
    private RecyclerView rv_addr_list;
    private ShipingAddrAdapter addrAdapter;
    private List<ShippingAddrBean> alist;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shipping_addr;
    }

    @Override
    protected void initView() {
        rv_addr_list = (RecyclerView) findViewById(R.id.shippingaddr_rv_addr_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_addr_list.setLayoutManager(manager);
        btn_add_addr = (Button) findViewById(R.id.shippingaddr_btn_add_addr);
        alist = new ArrayList<>();
        addrAdapter = new ShipingAddrAdapter(ShippingAddrActivity.this, alist);
        rv_addr_list.setAdapter(addrAdapter);
    }

    @Override
    public void initData() {


        ShippingAddrLogic.doGetAddress(new OnModelCallback<List<ShippingAddrBean>>() {
            @Override
            public void onModelSuccessed(List<ShippingAddrBean> list) {
                alist.clear();
                alist.addAll(list);
                addrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onModelFailed(String failMsg) {
                LogUtils.e(failMsg);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void initListener() {
        btn_add_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断收获地址是多少个,如果是5个,不允许添加
                if (alist.size()==5){
                    LogUtils.e("收货地址最多可以添加5个");

                }else {
                    AddShippingAddrActivity.actionStart(ShippingAddrActivity.this);
                }
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ShippingAddrActivity.class);
        context.startActivity(intent);
    }
}
