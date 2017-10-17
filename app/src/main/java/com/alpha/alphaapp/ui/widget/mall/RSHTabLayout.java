package com.alpha.alphaapp.ui.widget.mall;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;

/**
 * Created by kenway on 17/9/13 14:38
 * Email : xiaokai090704@126.com
 * 积分商城首页的推荐,积分,热度自定控件
 */

public class RSHTabLayout  extends LinearLayout  implements View.OnClickListener {

    private Context  context;

    private OnTabSelectedListener listener;
    private  int  selectIndex=-1;
    private View view;
    private LinearLayout[] layouts;

    public RSHTabLayout(Context context) {
        super(context);
        this.context=context;

    }

    public RSHTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

        initViews();
    }



    public RSHTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

        initViews();
    }

    private void initViews() {

        view = LayoutInflater.from(context).inflate(R.layout.widget_mall_rsh, this);

       final LinearLayout layout_recom= (LinearLayout) view.findViewById(R.id.widget_mall_rsh_remcomd_layout);
       final LinearLayout layout_score= (LinearLayout) view.findViewById(R.id.widget_mall_rsh_score_layout);
       final LinearLayout layout_hot= (LinearLayout) view.findViewById(R.id.widget_mall_rsh_hot_layout);
        layouts = new LinearLayout[]{layout_recom,layout_score,layout_hot};

        layout_recom.setOnClickListener(this);
        layout_hot.setOnClickListener(this);
        layout_score.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case  R.id.widget_mall_rsh_remcomd_layout:
                 selectIndex=0;
               break;
           case R.id.widget_mall_rsh_score_layout:
               selectIndex=1;
               break;
           case  R.id.widget_mall_rsh_hot_layout:
               selectIndex=2;
               break;
       }
        changeSelectIndex(selectIndex);
        if (!Util.isNull(listener)){
            listener.onTabSelected(selectIndex);
        }



    }


    /**
     * 改变选中状态
     * @param selectIndex
     */
    private  void   changeSelectIndex(int selectIndex){
        for (int i=0;i<layouts.length;i++){
            if (i==selectIndex){
                layouts[i].setBackgroundColor(ResourceUtil.resToColor(context,R.color.common_bg_dali_gray));
            }else {
                layouts[i].setBackgroundColor(ResourceUtil.resToColor(context,R.color.common_bg_dali_withe));
            }
        }

    }
    /**
     * 只能是0,1,2
     * @param position
     */
    public  void  setSelectIndex(int position){
          changeSelectIndex(position);
    }
   public  void  addOnTabSelectedListener(OnTabSelectedListener listener){
       this.listener=listener;
   }
    public interface  OnTabSelectedListener{
        void  onTabSelected(int position);
    }
}