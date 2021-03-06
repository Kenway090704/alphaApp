package com.alpha.alphaapp.ui.v_1_1.mall;

import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.base.BaseFragment;
import com.alpha.alphaapp.ui.v_1_1.mall.active.ActiveEnterActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.addr.ShippingAddrActivity;
import com.alpha.alphaapp.ui.v_1_1.mall.exchange.ScoreExchangeEnterActivity;

/**
 * Created by kenway on 17/7/17 10:38
 * Email : xiaokai090704@126.com
 */

public class ScoreMallFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "ScoreMallFragment";

    private Button btn_my_score;


    private Button btn_shiping_addr;
    private Button btn_active;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_score_mall;
    }

    @Override
    protected void initViews(View root) {
        btn_my_score = (Button) root.findViewById(R.id.frag_score_mall_btn_my_score);

        btn_shiping_addr = (Button) root.findViewById(R.id.frag_score_mall_btn_shiping_addr);
        btn_active = (Button) root.findViewById(R.id.frag_score_mall_btn_active);
    }

    @Override
    protected void initEnvent() {
        btn_my_score.setOnClickListener(this);


        btn_shiping_addr.setOnClickListener(this);
        btn_active.setOnClickListener(this);
    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_score_mall_btn_my_score:
                ScoreExchangeEnterActivity.actionStart(getActivity());
                break;
            case R.id.frag_score_mall_btn_shiping_addr:
                ShippingAddrActivity.actionStart(getActivity());
                break;
            case R.id.frag_score_mall_btn_active:
                ActiveEnterActivity.actionStart(getActivity());
                break;
        }
    }
}
