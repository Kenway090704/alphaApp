package com.alpha.alphaapp.ui.v_1_0.sign;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.SignInfo;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_stub.comm.URLConstans;
import com.alpha.alphaapp.model.v_1_0.bean.GetIconBean;
import com.alpha.alphaapp.model.v_1_0.userinfo.GetIconListLogic;
import com.alpha.alphaapp.model.v_1_0.sign.SignLogic;
import com.alpha.alphaapp.ui.base.BaseFragmentActivity;
import com.alpha.alphaapp.ui.v_1_0.sign.adapter.LayoutVPAdapter;
import com.alpha.alphaapp.ui.v_1_0.sign.adapter.SpacesItemDecoration;
import com.alpha.alphaapp.ui.v_1_0.sign.adapter.StaggerRecylcerAdapter;
import com.alpha.alphaapp.ui.widget.dialog.SignDialog;
import com.alpha.lib_sdk.app.tool.Util;
import com.androidkun.xtablayout.XTabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenway on 17/6/21 13:48
 * Email : xiaokai090704@126.com
 * 签到页面
 */

public class SignActivity extends BaseFragmentActivity {
    private static final String TAG = "StaggerActivity";
    private XTabLayout tab_1,tab_2;
    private ViewPager vp;
    private Button btn_sign;

    private Map<String, Boolean> map;
    //保存图片的高度
    private Map<String, Integer> map_heights;

    //保存四个RecyclerView 的adapter

    private List<StaggerRecylcerAdapter> adapters;

    private SignDialog signDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        tab_1 = (XTabLayout) findViewById(R.id.sign_tab_1);
        tab_2 = (XTabLayout) findViewById(R.id.sign_tablayout);
        vp = (ViewPager) findViewById(R.id.sign_vp);
        btn_sign = (Button) findViewById(R.id.sign_btn_sign);
        signDialog = new SignDialog(this);

    }

    @Override
    public void initData() {
        map = new HashMap<>();
        map_heights = new HashMap<>();
        initTabs();
        initVps();

    }

    /**
     * 初始化ViewPager
     */
    private void initVps() {
        getIconList(this);
    }

    /**
     * 模拟获取图片后的签到,这个模拟的
     *
     * @param context
     */
    private void getIconList(final Context context) {


        OnModelCallback<List<GetIconBean.IconListBean.CategoryBean>> callBack = new OnModelCallback<List<GetIconBean.IconListBean.CategoryBean>>() {
            @Override
            public void onModelSuccessed(List<GetIconBean.IconListBean.CategoryBean> list) {
                List<LinearLayout> layouts = new ArrayList<>();
                //所有RecyclerView的Adapter
                adapters = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    //======创建ViewPager中的每一个界面=======//
                    final LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.widget_sign_item, null);
                    RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.sign_item_recyclerView);
                    //设置瀑布流LayoutManager
                    final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    //RecyclerView滑动过程中不断请求layout的Request，不断调整item见的间隙，并且是在item尺寸显示前预处理，因此解决RecyclerView滑动到顶部时仍会出现移动问题
//                    layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setPadding(0, 0, 0, 0);

                    //RecyclerView的Adapter
                    StaggerRecylcerAdapter adapter = new StaggerRecylcerAdapter(context, list.get(i), map, map_heights);
                    recyclerView.setAdapter(adapter);

                    //设置item之间的间隔
                    SpacesItemDecoration decoration = new SpacesItemDecoration(16);
                    recyclerView.addItemDecoration(decoration);
                    layouts.add(layout);
                    adapters.add(adapter);
                    //遍历每一个图片的名字
                    for (int j = 0; j < list.get(i).getIcons().size(); j++) {
                        map.put(list.get(i).getIcons().get(j), false);
                        int random_height;
//                        if (j == 0 || j == 1) {
//                            random_height = 200;
//                        } else {
                        random_height = (int) (100 + Math.random() * 400);
//                        }
                        map_heights.put(list.get(i).getIcons().get(j), random_height);
                    }
                }
                LayoutVPAdapter adapter = new LayoutVPAdapter(layouts);
                vp.setAdapter(adapter);

            }

            @Override
            public void onModelFailed(String failedMsg) {
                LogUtils.e( failedMsg);

            }
        };
        GetIconListLogic.doGetIconList(AccountManager.getInstance().getSskey(), callBack);
    }

    /**
     * 初始化标题参数
     */
    private void initTabs() {
        tab_1.addTab(tab_1.newTab().setText("零速争霸"));
        tab_1.addTab(tab_1.newTab().setText("超级飞侠"));


        tab_2.addTab(tab_2.newTab().setText("零速争霸"));
        tab_2.addTab(tab_2.newTab().setText("爆裂飞车"));
        tab_2.addTab(tab_2.newTab().setText("超级飞侠"));
        tab_2.addTab(tab_2.newTab().setText("喜羊羊与灰太郎"));
    }

    @Override
    protected void initListener() {

        tab_2.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                XTabLayout.Tab tablayout = tab_2.getTabAt(position);
                if (!Util.isNull(tablayout))
                    tablayout.select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (final String str : map.keySet()) {
                    //map.keySet()返回的是所有key的值
                    Boolean isSelect = map.get(str);//得到每个key对应value的值
                    if (isSelect) {
                        SignLogic.OnGetSignInfoCallBack callBack = new SignLogic.OnGetSignInfoCallBack() {
                            @Override
                            public void onGetSignInfoSuccessed(SignInfo info) {

                            }

                            @Override
                            public void onGetSignInfoFailed(String failMsg) {

                            }
                        };
                        SignLogic.doGetSignInfo(callBack);
                        if (!Util.isNull(signDialog)) {
                            signDialog.setSignIcon(URLConstans.getICONUrl(SignActivity.this) + str);
                            signDialog.show();
                            signDialog.setBtnLookOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    signDialog.dismiss();
                                    MySignActivity.actionStart(SignActivity.this, str);
                                }
                            });
                        }
                    }
                }

            }
        });

    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SignActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!Util.isNull(signDialog)) {
            signDialog.dismiss();
            //进入到查看记录页面
        }
    }

    public List<StaggerRecylcerAdapter> getListAdapters() {
        return adapters;
    }
}
