package com.alpha.alphaapp.ui.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.ScoreChangeListLogic;
import com.alpha.alphaapp.model.mall.bean.OrderBean;
import com.alpha.alphaapp.model.mall.bean.ScoreLogBean;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.mall.adapter.ChangeRecordAdapter;
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
    private RecyclerView rv;
    private List<ScoreLogBean> list;
    private ChangeRecordAdapter adapter;


    @Override
    protected int getLayoutId() {
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
        ScoreChangeListLogic.doGetUserScoreChange(TypeConstants.PRODUCT_ID.NONE_PRODUCT, new OnModelCallback<List<ScoreLogBean>>() {
            @Override
            public void onModelSuccessed(List<ScoreLogBean> scoreLogBeens) {
//                list.clear();
//                list.addAll(scoreLogBeens);
//                adapter.notifyDataSetChanged();

                //测试数据
                for (int i = 0; i < 20; i++) {
                    ScoreLogBean bean = new ScoreLogBean();
                    bean.setChannel_id_text("爆裂飞车");
                    bean.setPre_score(100 * i);
                    bean.setOp_score(300);
                    bean.setCur_score(100 * i + 300);
                    bean.setOp_time("2016-09-08 18:51:45");
                    list.add(bean);
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

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ScoreChangeRecordActivity.class);
        context.startActivity(intent);
    }
}
