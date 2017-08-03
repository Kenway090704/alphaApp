package com.alpha.alphaapp.ui.v_1_0.sign;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.sign.adapter.MySignDateAdapter;
import com.alpha.alphaapp.ui.widget.dialog.MySignDialog;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.DateUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kenway on 17/6/21 15:57
 * Email : xiaokai090704@126.com
 */

public class MySignActivity extends BaseActivity {

    private static final String TAG = "MySignActivity";
    private TextView tv_signrule;
    private ImageView iv_before, iv_next;
    private TextView tv_date;
    private RecyclerView rv_data;
    private ImageView iv_signIcon;
    private String icon;
    private Map<Integer, Integer> map;
    private MySignDateAdapter adapter;
    private MySignDialog mySignDialog;
    //界面展示的事件
    private Date date;
    private int[] selectDate;


    @Override
    protected int getLayoutId() {
        icon = getIntent().getStringExtra("icon");
        return R.layout.activity_my_sign;
    }

    @Override
    protected void initView() {
        tv_signrule = (TextView) findViewById(R.id.my_sign_tv_signrule);
        iv_before = (ImageView) findViewById(R.id.my_sign_iv_before);
        iv_next = (ImageView) findViewById(R.id.my_sign_iv_next);
        tv_date = (TextView) findViewById(R.id.my_sign_tv_date);
        rv_data = (RecyclerView) findViewById(R.id.my_sign_rv_sign);
        iv_signIcon = (ImageView) findViewById(R.id.my_sign_iv_signicon);
        mySignDialog = new MySignDialog(this);
        tv_date.setText(DateUtils.doArrToString(DateUtils.getNowDate()));

    }

    @Override
    public void initData() {

        selectDate = DateUtils.getNowDate();
        if (!Util.isNullOrBlank(icon)) {
            //使用Glide展示图片

            Glide.with(this).load(URLConstans.GET_ICON.ICON300 + icon).into(iv_signIcon);
        }
        //初始化Recyclerview的数据
        map = new HashMap<>();
        selectDate=DateUtils.getNowDate();
        String date = DateUtils.doArrToString(selectDate);
        int maxDay = DateUtils.getDaysOfMonth(date);
        for (int i = 1; i < maxDay+1; i++) {
            if (i % 2 == 0) {
                map.put(i, 0);
            } else {
                map.put(i, 1);
            }

        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_data.setLayoutManager(manager);
        adapter = new MySignDateAdapter(this, map);
        rv_data.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        tv_signrule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNull(mySignDialog))
                    mySignDialog.show();
            }
        });

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取下个月
                selectDate = DateUtils.getNextMonth(selectDate);

                String date = DateUtils.doArrToString(selectDate);
                int maxDay = DateUtils.getDaysOfMonth(date);
                Log.e(TAG,"maxday=="+maxDay);
                map.clear();
                for (int i = 1; i < maxDay+1; i++) {
                        map.put(i, 0);
                }
                tv_date.setText(date);
                adapter.notifyDataSetChanged();
            }
        });
        iv_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取上个月
                selectDate = DateUtils.getBeforeMonth(selectDate);
                String date = DateUtils.doArrToString(selectDate);
                int maxDay = DateUtils.getDaysOfMonth(date);
                Log.e(TAG,"maxday=="+maxDay);
                map.clear();
                for (int i = 1; i < maxDay+1; i++) {
                    if (i % 2 == 0) {
                        map.put(i, 0);
                    } else {
                        map.put(i, 1);
                    }

                }
                tv_date.setText(date);
                adapter.notifyDataSetChanged();
            }
        });
        rv_data.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码,更新日期

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dx > 0) {
                    //大于0表示正在向右滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0表示停止或向左滚动
                    isSlidingToLast = false;
                }
            }
        });

    }

    public static void actionStart(Context context, String icon) {
        Intent intent = new Intent(context, MySignActivity.class);
        intent.putExtra("icon", icon);
        context.startActivity(intent);
    }
}
