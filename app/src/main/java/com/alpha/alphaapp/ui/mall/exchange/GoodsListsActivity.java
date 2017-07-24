package com.alpha.alphaapp.ui.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.GetGoodsListLogic;
import com.alpha.alphaapp.model.mall.bean.GoodsBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.mall.adapter.GoodsListAdapter;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.log.Log;

import java.util.List;

/**
 * Created by kenway on 17/7/17 16:44
 * Email : xiaokai090704@126.com
 */

public class GoodsListsActivity extends BaseActivity {
    private static final String TAG = "GoodsListsActivity";
    private TitleLayout titleLayout;
    private RecyclerView rv;
    private int ip_type;


    @Override
    protected int getLayoutId() {
        ip_type = getIntent().getIntExtra("product_id", -1);
        return R.layout.activity_mall_goods_lists;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.goods_list_title);
        rv = (RecyclerView) findViewById(R.id.goods_list_rv);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(manager);
    }

    @Override
    public void initData() {
        //获取数据

        OnModelCallback<List<GoodsBean>> callBack=new OnModelCallback<List<GoodsBean>>() {
            @Override
            public void onModelSuccessed(List<GoodsBean> goodsBeen) {
                Log.e(TAG, "list==" + goodsBeen.toString());
                GoodsListAdapter adapter = new GoodsListAdapter(GoodsListsActivity.this, goodsBeen);
                rv.setAdapter(adapter);
            }

            @Override
            public void onModelFailed(String failMsg) {

            }
        };
        GetGoodsListLogic.doGetGoodsList(TypeConstants.GET_GOODS_LIST_TYPE.ALL, callBack);

    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context, int product_id) {
        Intent intent = new Intent(context, GoodsListsActivity.class);
        intent.putExtra("product_id", product_id);
        context.startActivity(intent);
    }
}
