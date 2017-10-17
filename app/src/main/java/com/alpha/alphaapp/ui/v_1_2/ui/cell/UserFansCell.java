package com.alpha.alphaapp.ui.v_1_2.ui.cell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.logic.user.AttentionLogic;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/10/17 11:37
 * Email : xiaokai090704@126.com
 * TA的粉丝
 */

public class UserFansCell extends RVBaseCell<String> {
    private boolean isAttention;

    public UserFansCell(String s) {
        super(s);
    }

    @Override
    public int getItemType() {
        return Entry.USERFANSCELL;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_user_fans, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        ImageView iv_avatar = holder.getImageView(R.id.cell_user_fans_iv_avatar);
        holder.setText(R.id.cell_user_fans_tv_name, "名称");
        holder.setText(R.id.cell_user_fans_tv_rank, "初识玩具(是什么?)");
        holder.setText(R.id.cell_user_fans_tv_attention_count, "关注29");
        holder.setText(R.id.cell_user_fans_tv_fans_count, "粉丝29");
        holder.setText(R.id.cell_user_fans_tv_topic_count, "帖子29");

        final TextView tv_add_attention = holder.getTextView(R.id.cell_user_fans_tv_add_attention);
        tv_add_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加关注
                //

                ToastUtils.showToast(tv_add_attention.getContext(), "当前数据为测试数据无法进行关注");
//                addAttenttion(tv_add_attention,targeruid);
            }
        });

    }

    /**
     * 添加,取消关注
     */
    private void addAttenttion(final TextView tv_add_attent, String targeruid) {
        if (!isAttention) {
            //添加关注
            OnModelCallback<Object> callback = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {
                    ToastUtils.showToast(tv_add_attent.getContext(), "关注成功");
                    tv_add_attent.setText("取消关注");
                    isAttention = true;
                    tv_add_attent.setClickable(true);
                }

                @Override
                public void onModelFailed(String failedMsg) {

                }
            };
            AttentionLogic.addUserAttention(targeruid, callback);
        } else {
            //取消关注
            OnModelCallback<Object> callback = new OnModelCallback<Object>() {
                @Override
                public void onModelSuccessed(Object o) {
                    ToastUtils.showToast(tv_add_attent.getContext(), "取消关注");
                    tv_add_attent.setText("+加关注");
                    isAttention = false;
                    tv_add_attent.setClickable(true);
                }

                @Override
                public void onModelFailed(String failedMsg) {

                }
            };
            AttentionLogic.deleteUserAttention(targeruid, callback);
        }

        tv_add_attent.setClickable(false);
    }
}
