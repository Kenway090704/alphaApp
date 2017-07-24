package com.alpha.alphaapp.ui.widget.mall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.TypeConstants;
import com.alpha.alphaapp.model.mall.bean.ActiveEnterItemInfo;
import com.alpha.alphaapp.ui.WebActivity;
import com.alpha.alphaapp.ui.mall.active.ActiveActivity;
import com.alpha.alphaapp.ui.mall.active.ActiveRecordActivity;

/**
 * Created by kenway on 17/7/17 14:43
 * Email : xiaokai090704@126.com
 * <p>
 * 激活入口的三个产品对应的item
 */

public class ActiveEnterItem extends LinearLayout {
    private static final String TAG = "ActiveEnterItem";
    private Context context;
    private ImageView iv_logo;
    private TextView tv_active_time_1_hint;
    private TextView tv_active_time_1;

    private LinearLayout layout_active_time_2;
    private TextView tv_active_time_2_hint;
    private TextView tv_active_time_2;


    private TextView tv_active;
    private TextView tv_active_record;
    private TextView tv_web;

    public ActiveEnterItem(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public ActiveEnterItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initViews();

    }


    public ActiveEnterItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_mall_active_enter_item, this);
        iv_logo = (ImageView) view.findViewById(R.id.active_enter_item_iv_logo);
        tv_active_time_1_hint = (TextView) view.findViewById(R.id.active_enter_item_tv_active_time_1_hint);
        tv_active_time_1 = (TextView) view.findViewById(R.id.active_enter_item_tv_active_time_1);
        layout_active_time_2 = (LinearLayout) view.findViewById(R.id.active_enter_item_layout_active_time_2);
        tv_active_time_2_hint = (TextView) view.findViewById(R.id.active_enter_item_tv_active_time_2_hint);
        tv_active_time_2 = (TextView) view.findViewById(R.id.active_enter_item_tv_active_time_2);
        tv_active = (TextView) view.findViewById(R.id.active_enter_item_tv_active);
        tv_active_record = (TextView) view.findViewById(R.id.active_enter_item_tv_review_exchange_record);
        tv_web = (TextView) view.findViewById(R.id.active_enter_item_tv_look_web);
    }

    public void setDataforView(final ActiveEnterItemInfo info) {

        switch (info.getProduct_id()) {
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:
                setDataforTrans(info);
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
                setDataforSpeed(info);
                break;
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                setDataforSuperWaving(info);
                break;
            case TypeConstants.PRODUCT_ID.NONE_PRODUCT:
                break;
        }
        tv_active.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActiveActivity.actionStart(context, info.getProduct_id());
            }
        });
        tv_active_record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳到激活记录页面
                ActiveRecordActivity.actionStart(context, info.getProduct_id());
            }
        });
        tv_web.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(context, info.getWeb_url());
            }
        });
    }

    /**
     * 设置爆裂飞车的UI
     *
     * @param info
     */
    public void setDataforTrans(final ActiveEnterItemInfo info) {
        iv_logo.setImageResource(info.getSrcId());
        tv_active_time_1_hint.setText(R.string.chip_active_time);
        tv_active_time_1.setText(info.getTv_active_time() + "次");
        layout_active_time_2.setVisibility(GONE);
    }

    /**
     * 设置爆裂飞车的UI
     *
     * @param info
     */
    public void setDataforSpeed(final ActiveEnterItemInfo info) {
        iv_logo.setImageResource(info.getSrcId());
        tv_active_time_1_hint.setText(R.string.racingcar_code_active_time);
        tv_active_time_1.setText(info.getTv_active_time() + "次");
        tv_active_time_2_hint.setText(R.string.score_code_active_time);
        tv_active_time_2.setText(info.getTv_active_tiem_2() + "次");
    }

    /**
     * 设置爆裂飞车的UI
     *
     * @param info
     */
    public void setDataforSuperWaving(final ActiveEnterItemInfo info) {
        iv_logo.setImageResource(info.getSrcId());
        tv_active_time_1_hint.setText(R.string.security_code_active_time);
        tv_active_time_1.setText(info.getTv_active_time() + "次");
        layout_active_time_2.setVisibility(GONE);

    }
}
