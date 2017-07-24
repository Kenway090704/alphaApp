package com.alpha.alphaapp.ui.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.ScoreChangeListLogic;
import com.alpha.alphaapp.model.mall.bean.ScoreLogBean;
import com.alpha.alphaapp.ui.BaseActivity;

import java.util.List;

/**
 * Created by kenway on 17/7/17 14:06
 * Email : xiaokai090704@126.com
 * 兑换记录
 */

public class ScoreChangeRecordActivity extends BaseActivity {

    private static final String TAG = "ScoreChangeRecordActivity";

    private TextView tv_change_info;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mall_change_record;
    }

    @Override
    protected void initView() {
        tv_change_info = (TextView) findViewById(R.id.score_change_record_tv_info);
    }

    @Override
    public void initData() {
        ScoreChangeListLogic.doGetUserScoreChange(TypeConstants.PRODUCT_ID.NONE_PRODUCT, new OnModelCallback<List<ScoreLogBean>>() {
            @Override
            public void onModelSuccessed(List<ScoreLogBean> scoreLogBeen) {
                if (scoreLogBeen.size() == 0) {
                    tv_change_info.setText("返回数据OK,无积分变动");
                } else {
                    tv_change_info.setText(scoreLogBeen.toString());
                }
            }

            @Override
            public void onModelFailed(String failMsg) {
                tv_change_info.setText(failMsg);
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
