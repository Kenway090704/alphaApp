package com.alpha.alphaapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.v_1_1.mall.ScoreMallFragment;
import com.alpha.alphaapp.ui.v_1_0.mine.MineFragment;
import com.alpha.alphaapp.ui.v_1_0.recom.RecomFragment;
import com.alpha.lib_sdk.app.log.LogUtils;


/**
 * Created by kenway on 17/5/24 14:59
 * Email : xiaokai090704@126.com
 */

public class HomeActivity extends BaseFragmentActivity {
    private static final String TAG = "HomeActivity";
    private RadioGroup radioGroup;
    private RadioButton rb_mine;
    private BaseFragment[] fragments;
    private int lastIndex = 2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
    @Override
    protected void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.home_rg);
        rb_mine = (RadioButton) findViewById(R.id.home_rb_mine);
//        //定义底部标签图片大小
//        Drawable drawableWeiHui = getResources().getDrawable(R.drawable.icon_mine);
//        drawableWeiHui.setBounds(2, 0, 30, 20);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        rb_mine.setCompoundDrawables(null, drawableWeiHui, null, null);//只放上面
    }


    @Override
    public void initData() {
        initFragments();
    }

    @Override
    protected void initListener() {
        // 监听底部RadioButton
        initRadioButton();


    }

    /**
     * 初始化fragment
     */
    private void initFragments() {
        fragments = new BaseFragment[]{
                new RecomFragment(), new ScoreMallFragment(), new MineFragment()
        };
        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
        for (BaseFragment frag : fragments) {
            tran.add(R.id.home_content, frag);
            tran.hide(frag);
        }
        // 默认显示第一个,这里显示的是最后一个
        tran.show(fragments[2]);
        // 提交事务
        tran.commit();

    }

    private void initRadioButton() {
        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int index = -1;
                    switch (checkedId) {
                        case R.id.home_rb_recom:
                            index = 0;
                            break;
                        case R.id.home_rb_score:
                            index = 1;
                            break;
                        case R.id.home_rb_mine:
                            index = 2;
                            break;
                    }
                    //判断若点击的是上一次的页面则不操作
                    if (index == lastIndex) {
                        return;
                    }
                    FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
                    //隐藏上一个
                    if (lastIndex != -1) {
                        tran.hide(fragments[lastIndex]);
                    }
                    //显示点击的framment
                    tran.show(fragments[index]);
                    tran.commit();
                    //记录上一个索引
                    lastIndex = index;

                }
            });
        }
    }


    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     * @param data1
     * @param data2
     */
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }

    }

    public static void actionStartClearStack(Context context, String data1, String data2) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("params", data1);
        intent.putExtra("params", data2);
        context.startActivity(intent);
    }
}
