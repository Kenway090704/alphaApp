package com.alpha.alphaapp.ui.widget.mall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;

/**
 * Created by kenway on 17/9/13 16:57
 * Email : xiaokai090704@126.com
 */

public class TSSTabLayout extends LinearLayout implements View.OnClickListener {
    private Context context;
    private View view;
//    private LinearLayout layout_trans,layout_super,layout_speed;

    private  LinearLayout[] layouts;
    private  TextView[] textViews;
    private  View[]  views;

    private  int selectIndex=0;

    public TSSTabLayout(Context context) {
        super(context);
        this.context = context;
        initViews();
    }


    public TSSTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public TSSTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }

    private void initViews() {
        view = LayoutInflater.from(context).inflate(R.layout.widget_mall_tss, this);

        LinearLayout layout_trans = (LinearLayout) view.findViewById(R.id.widget_mall_tss_layout_trans);
        LinearLayout layout_super = (LinearLayout) view.findViewById(R.id.widget_mall_tss_layout_super);
        LinearLayout layout_speed = (LinearLayout) view.findViewById(R.id.widget_mall_tss_layout_speed);
        layouts=new LinearLayout[]{layout_trans,layout_super,layout_speed};

        TextView tv_trans = (TextView) view.findViewById(R.id.widget_mall_tss_tv_trans);
        TextView tv_super = (TextView) view.findViewById(R.id.widget_mall_tss_tv_super);
        TextView tv_speed = (TextView) view.findViewById(R.id.widget_mall_tss_tv_speed);
        tv_trans.setOnClickListener(this);
        tv_super.setOnClickListener(this);
        tv_speed.setOnClickListener(this);
        textViews=new TextView[]{tv_trans,tv_super,tv_speed};

        View indictor_tans=view.findViewById(R.id.widget_mall_tss_view_indictor);
        View indictor_super=view.findViewById(R.id.widget_mall_tss_view_indictor2);
        View indictor_speed=view.findViewById(R.id.widget_mall_tss_view_indictor3);
        views =new View[]{indictor_tans,indictor_super,indictor_speed};

        changeSelectIndex(0);

    }

    private  void  changeSelectIndex(int selectIndex){
            for (int i=0;i<3;i++){
                if (i==selectIndex){
                    textViews[i].setTextColor(ResourceUtil.resToColor(context,R.color.common_tv_dali_white));
                    views[i].setVisibility(VISIBLE);
                }else {
                    textViews[i].setTextColor(ResourceUtil.resToColor(context,R.color.common_tv_dali_alpha_white));
                    views[i].setVisibility(GONE);
                }
            }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.widget_mall_tss_tv_trans:
                selectIndex=0;
                break;
            case R.id.widget_mall_tss_tv_super:
                selectIndex=1;
                break;
            case R.id.widget_mall_tss_tv_speed:
                selectIndex=2;
                break;
        }

        changeSelectIndex(selectIndex);

    }
}
