package com.alpha.alphaapp.ui.widget.mall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.mall.bean.ScoreExchangeEnterItemInfo;
import com.alpha.alphaapp.ui.WebActivity;
import com.alpha.alphaapp.ui.mall.exchange.ExchangeRecordActivity;
import com.alpha.alphaapp.ui.mall.exchange.GoodsListsActivity;
import com.alpha.lib_sdk.app.log.Log;

/**
 * Created by kenway on 17/7/17 14:43
 * Email : xiaokai090704@126.com
 */

public class ScoreExchangeEnterItem extends LinearLayout {
    private static final String TAG = "MyScoreItem";
    private Context context;
    private ImageView iv_logo;
    private TextView tv_curr_score;
    private TextView tv_exchange;
    private TextView tv_exchnage_record;
    private TextView tv_web;

    public ScoreExchangeEnterItem(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public ScoreExchangeEnterItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }


    public ScoreExchangeEnterItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_mall_score_exchange_enter_item, this);
        iv_logo = (ImageView) view.findViewById(R.id.my_score_item_iv_logo);
        tv_curr_score = (TextView) view.findViewById(R.id.my_score_item_tv_curr_score);
        tv_exchange = (TextView) view.findViewById(R.id.my_score_item_tv_exchange);
        tv_exchnage_record = (TextView) view.findViewById(R.id.my_score_item_tv_review_exchange_record);
        tv_web = (TextView) view.findViewById(R.id.my_score_item_tv_look_web);
    }

    public void setDataforView(final ScoreExchangeEnterItemInfo info) {
        Log.e(TAG,"info==" + info.toString());
        iv_logo.setImageResource(info.getSrcId());
        tv_curr_score.setText(info.getTv_curr_score());
        tv_exchange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
           GoodsListsActivity.actionStart(context,info.getProduct_id());
            }
        });
        tv_exchnage_record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳到兑换记录页面
                ExchangeRecordActivity.actionStart(context);
            }
        });
        tv_web.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(context, info.getWeb_url());
            }
        });
    }


}
