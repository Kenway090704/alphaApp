package com.alpha.alphaapp.ui.v_1_1.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.v_1_1.logic.GetGoodsListLogic;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.GoodsListAdapter;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.GoodsListTextAdapter;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;

import com.alpha.alphaapp.model.v_1_1.bean.GoodsBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/17 16:44
 * Email : xiaokai090704@126.com
 */

public class GoodsListsActivity extends BaseActivity {
    private static final String TAG = "GoodsListsActivity";
    private static final String PRODUCT_ID = "product_id";
    private TitleLayout titleLayout;
    private RecyclerView rv;
    private int product_id;
    private GoodsListAdapter adapter;
    private List<GoodsBean> listBeans;


    @Override
    protected int getLayoutId() {
        product_id = getIntent().getIntExtra(PRODUCT_ID, -1);
        return R.layout.activity_mall_goods_lists;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.goods_list_title);

        switch (product_id) {
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                titleLayout.setTitleText(R.string.transforms_car_score_exchange);
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
                titleLayout.setTitleText(R.string.speed_score_exchange);
                break;
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                titleLayout.setTitleText(R.string.super_score_exchange);
                break;
            default:
                titleLayout.setTitleText(R.string.none_product_id);
                break;
        }
        rv = (RecyclerView) findViewById(R.id.goods_list_rv);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(manager);
        listBeans = new ArrayList<>();
        adapter = new GoodsListAdapter(GoodsListsActivity.this, listBeans, product_id);
        rv.setAdapter(adapter);
    }

    @Override
    public void initData() {

        /**
         * TypeConstants.GET_GOODS_LIST_TYPE.ALL
         * {"result":0,"msg":"","goods_list":
         * [{"goods_id":10000,"goods_name":"测试商品","goods_type":0,"goods_classify":1,"pictures":["Public/uploads/CoverImages/2017-03-07/Ci_7324148888804510000.png"],"score":1,"total_count":3,"remain_count":1,"undercarriage_time":"","is_recommend":0,"recommend_num":0,"exchange_count":0,"detail_text":"app纯文本字段:","goods_no":"123"},
         */


        //获取数据

        OnModelCallback<List<GoodsBean>> callBack = new OnModelCallback<List<GoodsBean>>() {
            @Override
            public void onModelSuccessed(List<GoodsBean> goodsBeen) {
                if (!Util.isNull(goodsBeen)) {
                    listBeans.clear();
                    listBeans.addAll(goodsBeen);
                } else {
                    listBeans.clear();
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showToast(GoodsListsActivity.this, failMsg);
            }
        };
        GetGoodsListLogic.doGetGoodsList(product_id, TypeConstants.GET_GOODS_LIST_TYPE.EXCHANGE_COUNT, callBack);

    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context, int product_id) {
        Intent intent = new Intent(context, GoodsListsActivity.class);
        intent.putExtra(PRODUCT_ID, product_id);
        context.startActivity(intent);
    }
}
