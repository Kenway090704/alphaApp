package com.alpha.alphaapp.ui.v_1_2.cell;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.v_1_2.TestBean;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseCell;
import com.alpha.lib_stub.uikit.adapter.base.RVBaseViewHolder;

/**
 * Created by kenway on 17/8/14 15:45
 * Email : xiaokai090704@126.com
 */

public class CustomCell extends RVBaseCell<TestBean> {

    public CustomCell(TestBean testBean) {
        super(testBean);
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_test_layout, null));
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {
        TextView tv_name = (TextView) holder.getView(R.id.adapter_test_tv_name);
        TextView tv_age = (TextView) holder.getView(R.id.adapter_test_tv_age);

        tv_name.setText(mData.getTitle());
        tv_age.setText(mData.getAge() + "");
    }
}
