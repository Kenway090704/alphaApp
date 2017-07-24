package com.alpha.alphaapp.ui.mall.exchange;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.ExchangeGoodsLogic;
import com.alpha.alphaapp.model.mall.ShippingAddrLogic;
import com.alpha.alphaapp.model.mall.UserScoreLogic;
import com.alpha.alphaapp.model.mall.bean.GoodsBean;
import com.alpha.alphaapp.model.mall.bean.ShippingAddrBean;
import com.alpha.alphaapp.model.mall.bean.UserScoreBean;
import com.alpha.alphaapp.ui.BaseChooseAddrActivity;
import com.alpha.alphaapp.ui.mall.addr.ChooseShippingAddrActivity;
import com.alpha.alphaapp.ui.widget.mall.OrderShippingAdrrItem;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.List;

/**
 * Created by kenway on 17/7/18 11:12
 * Email : xiaokai090704@126.com
 * <p>兑换商品页面</p>
 */

public class GoodsExchangeActivity extends BaseChooseAddrActivity {
    private static final String TAG = "GoodsExchangeActivity";
    private OrderShippingAdrrItem iosa_addr;
    private ImageView iv_product;
    private TextView tv_goods_name;
    private TextView tv_goods_type;
    private TextView tv_need_score;
    private TextView tv_exchange_count;
    private TextView tv_my_score;
    private Button btn_now_exchange;

    private GoodsBean bean;


    @Override
    protected int getLayoutId() {
        bean = (GoodsBean) getIntent().getSerializableExtra("bean");
        return R.layout.activity_mall_goods_exchange;
    }

    @Override
    protected void initView() {
        iosa_addr = (OrderShippingAdrrItem) findViewById(R.id.goods_exchange_iosa_addr);
        iv_product = (ImageView) findViewById(R.id.goods_exchange_iv_product);
        tv_goods_name = (TextView) findViewById(R.id.goods_exchange_tv_goods_name);
        tv_goods_type = (TextView) findViewById(R.id.goods_exchange_tv_goods_type);
        tv_need_score = (TextView) findViewById(R.id.goods_exchange_tv_need_score);
        tv_exchange_count = (TextView) findViewById(R.id.goods_exchange_tv_exchange_count);
        tv_my_score = (TextView) findViewById(R.id.goods_exchange_tv_my_score);
        btn_now_exchange = (Button) findViewById(R.id.goods_exchange_btn_now_exchange);
    }

    @Override
    public void initData() {
        if (!Util.isNull(bean)) {
            ImageLoader.load(this, "http://120.76.27.29:8085/" + bean.getPictures().get(0), iv_product);

            //设置数据
            tv_goods_name.setText(bean.getGoods_name());
            tv_goods_type.setText(bean.getGoods_type()+"");
            tv_need_score.setText(bean.getScore()+"积分");
            tv_exchange_count.setText("1");
            UserScoreLogic.doGetUserScoreInfo(new OnModelCallback<UserScoreBean>() {
                @Override
                public void onModelSuccessed(UserScoreBean userScoreBean) {
                    if (Util.isNull(userScoreBean)) {
                        tv_my_score.setText(0 + "积分");
                    } else {
                        tv_my_score.setText(userScoreBean.getScore() + "积分");
                    }

                }

                @Override
                public void onModelFailed(String failedMsg) {
                    ToastUtils.showToast(GoodsExchangeActivity.this, "无法获取个人积分");
                }
            });

        }


        ShippingAddrLogic.doGetAddress(new OnModelCallback<List<ShippingAddrBean>>() {
            @Override
            public void onModelSuccessed(List<ShippingAddrBean> list) {
                ShippingAddrBean shippingBean = ShippingAddrLogic.getDefaultShippingAdd(list);
                iosa_addr.setDataforView(shippingBean);
            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showToast(GoodsExchangeActivity.this, failMsg);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initListener() {
        iosa_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入选择收获地址页面
                ChooseShippingAddrActivity.actionStart(GoodsExchangeActivity.this);
            }
        });


        final OnModelCallback<Object> callback = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                ToastUtils.showLong(GoodsExchangeActivity.this, "兑换成功");
            }

            @Override
            public void onModelFailed(String failMsg) {
                ToastUtils.showLong(GoodsExchangeActivity.this, failMsg);
            }
        };
        btn_now_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExchangeGoodsLogic.doExchangeGoods(bean.getGoods_id(), 2, 1, callback);
            }
        });
    }


    public static void actionStart(Context context, GoodsBean bean) {
        Intent intent = new Intent(context, GoodsExchangeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }


    @Override
    public void onShippingAddrUpdate(ShippingAddrBean bean) {
        if (!Util.isNull(bean)) {
            Log.e(TAG, "GoodsExchangeActivity===============" + bean.toString());
            iosa_addr.setDataforView(bean);
        }
    }
}
