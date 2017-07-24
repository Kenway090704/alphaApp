package com.alpha.alphaapp.ui.mall.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.mall.ShippingAddrLogic;
import com.alpha.alphaapp.model.mall.bean.ShippingAddrBean;
import com.alpha.alphaapp.ui.mall.addr.EditShippingAddrActvity;
import com.alpha.alphaapp.ui.widget.dialog.DialogUtils;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.List;

/**
 * Created by kenway on 17/7/18 14:57
 * Email : xiaokai090704@126.com
 */

public class ShipingAddrAdapter extends RecyclerView.Adapter<ShipingAddrAdapter.ViewHolder> {
    private static final String TAG = "ShipingAddrAdapter";
    private Context context;
    private List<ShippingAddrBean> alist;

    private Dialog dialog;

    public ShipingAddrAdapter(Context context, List<ShippingAddrBean> list) {
        this.context = context;
        this.alist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.widget_shiping_addr_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(alist.get(position));
    }

    @Override
    public int getItemCount() {
        return alist == null ? 0 : alist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_mobile, tv_addr;
        private RadioButton rb_setdefault;
        private TextView tv_edit, tv_del;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.shiping_addr_item_tv_name);
            tv_mobile = (TextView) itemView.findViewById(R.id.shiping_addr_item_tv_mobile);
            tv_addr = (TextView) itemView.findViewById(R.id.shiping_addr_item_tv_addr);
            rb_setdefault = (RadioButton) itemView.findViewById(R.id.shiping_addr_item_rb_setdefault);
            tv_edit = (TextView) itemView.findViewById(R.id.shiping_addr_item_tv_edit);
            tv_del = (TextView) itemView.findViewById(R.id.shiping_addr_item_tv_del);
        }

        void setData(final ShippingAddrBean bean) {
            tv_name.setText(bean.getName());
            tv_mobile.setText(bean.getMobile());
            tv_addr.setText(bean.getAddrAll());
            if (bean.getDefault_addr() == 1) {
                rb_setdefault.setChecked(true);
                rb_setdefault.setTextColor(Color.RED);
                rb_setdefault.setText(R.string.default_shipping_adrr);
            } else {
                rb_setdefault.setChecked(false);
                rb_setdefault.setTextColor(Color.BLACK);
                rb_setdefault.setText(R.string.set_default);
            }
            rb_setdefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        doSetDefaultShippingAddr(bean);
                    }
                }
            });
            tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditShippingAddrActvity.actionStart(context, bean);
                }
            });

            tv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doDelShippingAddr(bean);
                }
            });
        }
    }

    /**
     * 设置为默认收获地址
     *
     * @param bean
     */
    private void doSetDefaultShippingAddr(ShippingAddrBean bean) {
        bean.setDefault_addr(1);


        ShippingAddrLogic.doSetDefaultAddress(bean, new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                doRefreshShippingAddr();
            }

            @Override
            public void onModelFailed(String failedMsg) {
                ToastUtils.showLong(context, failedMsg);
            }
        });
    }

    /**
     * 删除收货地址逻辑
     *
     * @param bean
     */
    private void doDelShippingAddr(final ShippingAddrBean bean) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行删除操作
                ShippingAddrLogic.doDelAddress(bean, new OnModelCallback<Object>() {
                    @Override
                    public void onModelSuccessed(Object o) {
                        if (!Util.isNull(dialog) && dialog.isShowing())
                            dialog.dismiss();
                        doRefreshShippingAddr();
                    }

                    @Override
                    public void onModelFailed(String failedMsg) {
                        if (!Util.isNull(dialog) && dialog.isShowing())
                            dialog.dismiss();
                        ToastUtils.showShort(context, failedMsg);
                    }
                });
            }
        };
        //弹出对话框,提示用户是否确认删除
        dialog = DialogUtils.createTwoChoiceDialog(context, R.string.insure_del_addr, onClickListener);
        if (!Util.isNull(dialog) && !dialog.isShowing())
            dialog.show();
    }

    /**
     * 刷新收货地址
     */
    private void doRefreshShippingAddr() {

        //重新刷新收货地址

        ShippingAddrLogic.doGetAddress(new OnModelCallback<List<ShippingAddrBean>>() {
            @Override
            public void onModelSuccessed(List<ShippingAddrBean> list) {
                alist.clear();
                alist.addAll(list);
                notifyDataSetChanged();
            }

            @Override
            public void onModelFailed(String failMsg) {

                ToastUtils.showToast(context,"无法刷新收货地址");
                Log.e(TAG, "list===" + failMsg);
            }
        });
    }

}
