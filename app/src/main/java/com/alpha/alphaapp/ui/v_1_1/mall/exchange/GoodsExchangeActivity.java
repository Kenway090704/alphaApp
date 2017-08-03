package com.alpha.alphaapp.ui.v_1_1.mall.exchange;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_1.logic.ExchangeGoodsLogic;
import com.alpha.alphaapp.model.v_1_1.logic.GetExchangeRecordLogic;
import com.alpha.alphaapp.model.v_1_1.logic.ShippingAddrLogic;
import com.alpha.alphaapp.model.v_1_1.logic.UserScoreLogic;
import com.alpha.alphaapp.model.v_1_1.bean.GoodsBean;
import com.alpha.alphaapp.model.v_1_1.bean.OrderBean;
import com.alpha.alphaapp.model.v_1_1.bean.ShippingAddrBean;
import com.alpha.alphaapp.model.v_1_1.bean.UserScoreBean;
import com.alpha.alphaapp.ui.BaseChooseAddrActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.Tools;
import com.alpha.alphaapp.ui.v_1_1.mall.addr.ChooseShippingAddrActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.addr.ShippingAddrActivity;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.alphaapp.ui.widget.mall.OrderAdrrItem;
import com.alpha.lib_sdk.app.glide.ImageLoader;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.comm.URLConstans;

import java.util.List;

/**
 * Created by kenway on 17/7/18 11:12
 * Email : xiaokai090704@126.com
 * <p>兑换商品页面</p>
 */

public class GoodsExchangeActivity extends BaseChooseAddrActivity {
    private static final String TAG = "GoodsExchangeActivity";
    private static final String BEAN = "bean";
    private static final String PRODUCT_ID = "product_id";

    private GoodsBean bean;
    private int product_id;


    private OrderAdrrItem iosa_addr;
    private ImageView iv_product;
    private TextView tv_goods_name;
    private TextView tv_goods_type;
    private TextView tv_need_score;
    private TextView tv_exchange_count;
    private TextView tv_my_score;
    private TextView tv_exchange_declare;
    private Button btn_now_exchange;
    private ShippingAddrBean shippingBean;


    private int user_score;

    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        bean = (GoodsBean) getIntent().getSerializableExtra(BEAN);
        product_id = getIntent().getIntExtra(PRODUCT_ID, -1);
        return R.layout.activity_mall_goods_exchange;
    }

    @Override
    protected void initView() {
        iosa_addr = (OrderAdrrItem) findViewById(R.id.goods_exchange_iosa_addr);
        iv_product = (ImageView) findViewById(R.id.goods_exchange_iv_product);
        tv_goods_name = (TextView) findViewById(R.id.goods_exchange_tv_goods_name);
        tv_goods_type = (TextView) findViewById(R.id.goods_exchange_tv_goods_type);
        tv_need_score = (TextView) findViewById(R.id.goods_exchange_tv_need_score);
        tv_exchange_count = (TextView) findViewById(R.id.goods_exchange_tv_exchange_count);
        tv_my_score = (TextView) findViewById(R.id.goods_exchange_tv_my_score);
        tv_exchange_declare = (TextView) findViewById(R.id.goods_exchange_tv_exchange_declare);
        btn_now_exchange = (Button) findViewById(R.id.goods_exchange_btn_now_exchange);
    }

    @Override
    public void initData() {
        if (!Util.isNull(bean)) {
            ImageLoader.load(this, URLConstans.GOODS_PICTURE_URL.GOODS_ICON + bean.getPictures().get(0), iv_product);
            //设置数据
            tv_goods_name.setText(bean.getGoods_name());
            Tools.setTextGoodType(tv_goods_type, bean.getGoods_type());
            tv_need_score.setText(bean.getScore() + "积分");
            tv_exchange_count.setText("1");
            tv_exchange_declare.setText(bean.getDetail_text());
            UserScoreLogic.doGetUserScoreInfo(product_id, new OnModelCallback<UserScoreBean>() {
                @Override
                public void onModelSuccessed(UserScoreBean userScoreBean) {
                    if (Util.isNull(userScoreBean)) {
                        tv_my_score.setText(0 + "积分");

                    } else {
                        tv_my_score.setText(userScoreBean.getScore() + "积分");
                        user_score = userScoreBean.getScore();
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
                shippingBean = ShippingAddrLogic.getDefaultShippingAdd(list);
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
        initData();
    }

    @Override
    protected void initListener() {
        iosa_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNull(shippingBean)) {
                    //进入地址管理页面
                    ShippingAddrActivity.actionStart(GoodsExchangeActivity.this);
                } else {
                    //进入选择收获地址页面
                    ChooseShippingAddrActivity.actionStart(GoodsExchangeActivity.this);
                }
            }
        });


        btn_now_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doExchang();


            }
        });
    }

    /**
     * 兑换商品
     */
    private void doExchang() {

        final OnModelCallback<Object> callback = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                dialog.dismiss();
                ToastUtils.showLong(GoodsExchangeActivity.this, "兑换成功");
                //获取刚生成的订单
                GetExchangeRecordLogic.doGetScoreExchangeRecordList(product_id, new OnModelCallback<List<OrderBean>>() {
                    @Override
                    public void onModelSuccessed(List<OrderBean> listBean) {
                        if (!Util.isNull(listBean)) {
                            //确认是不是刚生成的订单
                            if (listBean.get(0).getGoods_id() == bean.getGoods_id()) {
                                ExchangeRecordDetailActivity.actionStart(GoodsExchangeActivity.this, listBean.get(0));
                                finish();
                            } else {
                                ToastUtils.showToast(GoodsExchangeActivity.this, "订单信息获取失败");
                            }
                        } else {
                            ToastUtils.showToast(GoodsExchangeActivity.this, "订单信息获取失败");
                        }

                    }

                    @Override
                    public void onModelFailed(String failMsg) {
                        ToastUtils.showToast(GoodsExchangeActivity.this, failMsg);
                    }
                });
            }

            @Override
            public void onModelFailed(String failMsg) {
                dialog.dismiss();
                ToastUtils.showLong(GoodsExchangeActivity.this, failMsg);
            }
        };
        //是否有收货地址
        if (!Util.isNull(shippingBean)) {
            //当前积分是否足够兑换
            if (user_score >= bean.getScore()) {

                //弹出兑换框
                dialog = DialogUtils.createTwoChoiceDialog(GoodsExchangeActivity.this, R.string.insure_exchange_this_goods, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExchangeGoodsLogic.doExchangeGoods(bean.getGoods_id(), 1, shippingBean.getId(), product_id, callback);
                    }
                });
                dialog.show();

            } else {
                ToastUtils.showToast(GoodsExchangeActivity.this, "用户积分不足");
            }

        } else {
            ToastUtils.showToast(GoodsExchangeActivity.this, "当前没有收获地址,请添加收货");
        }
    }


    public static void actionStart(Context context, GoodsBean bean, int proudct_id) {
        Intent intent = new Intent(context, GoodsExchangeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(BEAN, bean);
        intent.putExtra(PRODUCT_ID, proudct_id);

        context.startActivity(intent);
    }


    @Override
    public void onShippingAddrUpdate(ShippingAddrBean bean) {
        if (!Util.isNull(bean)) {

            iosa_addr.setDataforView(bean);
        }
    }
}
