package com.alpha.alphaapp.ui.widget.mall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;

import com.alpha.alphaapp.ui.WebActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.exchange.ExchangeRecordListActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.exchange.GoodsListsActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.exchange.ScoreChangeRecordActivity;
import com.alpha.lib_stub.comm.TypeConstants;
import com.alpha.lib_stub.comm.URLConstans;

/**
 * Created by kenway on 17/7/17 14:43
 * Email : xiaokai090704@126.com
 */

public class ScoreEnterItem extends LinearLayout {
    private static final String TAG = "ScoreEnterItem";
    private Context context;
    private ImageView iv_logo;
    private TextView tv_hint;
    private TextView tv_curr_score;
    private TextView tv_exchange;
    private TextView tv_exchnage_record;
    private TextView tv_web;
    private TextView tv_score_log;

    public ScoreEnterItem(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public ScoreEnterItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }


    public ScoreEnterItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_mall_score_exchange_enter_item, this);
        iv_logo = (ImageView) view.findViewById(R.id.my_score_item_iv_logo);

        tv_hint = (TextView) view.findViewById(R.id.my_score_item_tv_hint);
        tv_curr_score = (TextView) view.findViewById(R.id.my_score_item_tv_curr_score);
        tv_exchange = (TextView) view.findViewById(R.id.my_score_item_tv_exchange);
        tv_exchnage_record = (TextView) view.findViewById(R.id.my_score_item_tv_review_exchange_record);
        tv_web = (TextView) view.findViewById(R.id.my_score_item_tv_look_web);
        tv_score_log= (TextView) view.findViewById(R.id.my_score_item_tv_score_log);
    }

    public void setDataforView(final int product_id, int score) {
        switch (product_id) {
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                setDataForTranform(product_id, score);
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
                setDataForSpeed(product_id, score);
                break;
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                setDataForSuperWaving(product_id, score);
                break;
        }


    }

    /**
     * 爆裂飞车数据设置
     */
    private void setDataForTranform(final int product_id, int score) {
        iv_logo.setImageResource(R.drawable.tranform_logo);
        tv_hint.setText(R.string.score_);
        tv_curr_score.setText(score + " 积分");
        //进入产品列表页
        tv_exchange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsListsActivity.actionStart(context, product_id);
            }
        });
        //跳到兑换记录页面
        tv_exchnage_record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExchangeRecordListActivity.actionStart(context, product_id);
            }
        });
        //使用WebView打开官网
        tv_web.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(context, URLConstans.OFFICAL_WEB_URL.TRANSFORM_CAR);
            }
        });

        tv_score_log.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreChangeRecordActivity.actionStart(context,product_id);
            }
        });
    }

    /**
     * 零速争霸数据设置
     */
    private void setDataForSpeed(final int product_id, int score) {

        iv_logo.setImageResource(R.drawable.speed_logo);
        tv_hint.setText(R.string.score_);
        tv_curr_score.setText(score + " 积分");
        //进入产品列表页
        tv_exchange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsListsActivity.actionStart(context, product_id);
            }
        });
        //跳到兑换记录页面
        tv_exchnage_record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExchangeRecordListActivity.actionStart(context,product_id);
            }
        });
        //使用WebView打开官网
        tv_web.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(context, URLConstans.OFFICAL_WEB_URL.SPEED);
            }
        });
        tv_score_log.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreChangeRecordActivity.actionStart(context,product_id);
            }
        });
    }


    /**
     * 超级飞侠数据设置
     *
     * @param product_id
     * @param score
     */
    private void setDataForSuperWaving(final int product_id, int score) {
        iv_logo.setImageResource(R.drawable.super_logo);
        tv_hint.setText(R.string.super_coin_);
        tv_curr_score.setText(score + " 个");
        //进入产品列表页
        tv_exchange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsListsActivity.actionStart(context, product_id);
            }
        });


        //跳到兑换记录页面
        tv_exchnage_record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExchangeRecordListActivity.actionStart(context, product_id);
            }
        });
        //使用WebView打开官网
        tv_web.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(context, URLConstans.OFFICAL_WEB_URL.SUPER_WAVING);
            }
        });

        tv_score_log.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreChangeRecordActivity.actionStart(context,product_id);
            }
        });
    }

}
