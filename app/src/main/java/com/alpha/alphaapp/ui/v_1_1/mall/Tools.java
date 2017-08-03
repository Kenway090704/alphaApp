package com.alpha.alphaapp.ui.v_1_1.mall;

import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.app.ApplicationContext;
import com.alpha.lib_stub.comm.TypeConstants;

/**
 * Created by kenway on 17/7/26 18:28
 * Email : xiaokai090704@126.com
 */

public class Tools {

    /**
     * 给TextView中设置当前产品是实物还是虚拟物品
     *
     * @param view
     * @param goods_type
     */
    public static void setTextGoodType(TextView view, int goods_type) {
        if (goods_type == TypeConstants.GOODS_TYPE.ACTUALS_GOODS) {
            view.setText(R.string.actual_goods);
        } else if (goods_type == TypeConstants.GOODS_TYPE.VIRTUAL_GOODS) {
            view.setText(R.string.virtual_goods);
        } else if (goods_type % 2 == 0) {
            view.setText(R.string.actual_goods);
        } else {
            view.setText(R.string.virtual_goods);
        }
    }

    /**
     * 给订单状态设置文字
     *
     * @param tv_status
     * @param status
     */
    public static void setTextShippingStatus(TextView tv_status, int status) {
        switch (status) {
            case TypeConstants.OREDER_STATUS.READY_SEND_GOODS:
                tv_status.setText(R.string.ready_send_goods);
                break;
            case TypeConstants.OREDER_STATUS.HAD_BEEN_SHIPPING:
                tv_status.setText(R.string.had_been_shipping);
                break;
            case TypeConstants.OREDER_STATUS.HAVE_THE_GOODS:
                tv_status.setText(R.string.have_the_goods);
                break;
            case TypeConstants.OREDER_STATUS.ORDER_CLOSE:
                tv_status.setText(R.string.order_close);
                break;
        }
    }

    /**
     * 通过产品id获取对应的字段
     */
    public static String getProduct_Text(int product_id) {
        String str = null;
        switch (product_id) {
            case TypeConstants.PRODUCT_ID.TRANSFROM_CAR:

                str = ApplicationContext.getCurrentContext().getResources().getString(R.string.transforms_car_score_exchange);
                break;
            case TypeConstants.PRODUCT_ID.SPEED:
                str = ApplicationContext.getCurrentContext().getResources().getString(R.string.speed_score_exchange);

                break;
            case TypeConstants.PRODUCT_ID.SUPER_WAVING:
                str = ApplicationContext.getCurrentContext().getResources().getString(R.string.super_score_exchange);

                break;
            default:
                str = ApplicationContext.getCurrentContext().getResources().getString(R.string.none_product_id);

                break;
        }

        return str;
    }
}
