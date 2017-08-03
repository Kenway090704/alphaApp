package com.alpha.alphaapp.ui.v_1_1.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.logic.ScoreChangeListLogic;
import com.alpha.alphaapp.model.v_1_1.bean.ScoreLogBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.adapter.ChangeRecordAdapter;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/7/17 14:06
 * Email : xiaokai090704@126.com
 * 积分变动记录
 */

public class ScoreChangeRecordActivity extends BaseActivity {

    private static final String TAG = "ScoreChangeRecordActivity";
    private static final String PRODUCT_ID = "product_id";
    private int product_id;
    private RecyclerView rv;
    private List<ScoreLogBean> list;
    private ChangeRecordAdapter adapter;


    @Override
    protected int getLayoutId() {
        product_id = getIntent().getIntExtra(PRODUCT_ID, -1);
        return R.layout.activity_mall_change_record_list;
    }

    @Override
    protected void initView() {
        rv = (RecyclerView) findViewById(R.id.score_change_record_list_rv);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new ChangeRecordAdapter(this, list);
        rv.setAdapter(adapter);

    }

    @Override
    public void initData() {
        ScoreChangeListLogic.doGetUserScoreChange(product_id, new OnModelCallback<List<ScoreLogBean>>() {
            @Override
            public void onModelSuccessed(List<ScoreLogBean> scoreLogBeens) {
//
                if (!Util.isNull(scoreLogBeens)) {
                    list.clear();
                    list.addAll(scoreLogBeens);
                } else {
                    list.clear();
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showToast(ScoreChangeRecordActivity.this, failMsg);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context, int product_id) {
        Intent intent = new Intent(context, ScoreChangeRecordActivity.class);
        intent.putExtra(PRODUCT_ID, product_id);
        context.startActivity(intent);
    }
}
