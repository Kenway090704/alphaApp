package com.alpha.alphaapp.ui.sign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.geticons.GetIconBean;
import com.alpha.alphaapp.model.geticons.GetIconListLogic;
import com.alpha.alphaapp.model.modifyinfo.ModifyUserInfoLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.mine.MineInfoActivity;
import com.alpha.alphaapp.ui.mine.adapter.IconListAdapter;
import com.alpha.alphaapp.ui.mine.adapter.IconRecylcerAdapter;
import com.alpha.alphaapp.ui.sign.adapter.LayoutAdapter;
import com.alpha.alphaapp.ui.sign.adapter.SignRecylcerAdapter;
import com.alpha.alphaapp.ui.widget.dialog.SignDialog;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenway on 17/6/21 13:48
 * Email : xiaokai090704@126.com
 * 签到页面
 */

public class SignActivity extends BaseActivity {
    private static final String TAG = "SignActivity";

    private TabLayout tab;
    private ViewPager vp;
    private Button btn_sign;

    private Map<String, Boolean> map;

    private SignDialog signDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        tab = (TabLayout) findViewById(R.id.sign_tablayout);
        vp = (ViewPager) findViewById(R.id.sign_vp);
        btn_sign = (Button) findViewById(R.id.sign_btn_sign);
        signDialog = new SignDialog(this);

    }

    @Override
    public void initData() {
        map = new HashMap<>();
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
     * 模拟获取图片后的签到
     *
     * @param context
     */
    private void getIconList(final Context context) {
        GetIconListLogic.GetIconCallBack callBack = new GetIconListLogic.GetIconCallBack() {
            @Override
            public void onGetIconListSuccuss(String baseUrl, List<GetIconBean.IconListBean.CategoryBean> list) {
                List<LinearLayout> layouts = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.widget_sign_item, null);
                    RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.sign_item_recyclerView);
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                    SignRecylcerAdapter adapter = new SignRecylcerAdapter(context, list.get(i), map);
                    recyclerView.setAdapter(adapter);
                    layouts.add(layout);

                    //遍历每一个图片的名字
                    for (int j = 0; j < list.get(i).getIcons().size(); j++) {
                        map.put(list.get(i).getIcons().get(j), false);
                    }
                }
                LayoutAdapter adapter = new LayoutAdapter(layouts);
                vp.setAdapter(adapter);

            }

            @Override
            public void onGetIconListFailed(String failMsg) {

            }
        };
        GetIconListLogic.doGetIconList(AccountManager.getInstance().getSskey(), callBack);
    }

    /**
     * 初始化标题参数
     */
    private void initTabs() {
        tab.addTab(tab.newTab().setText("零速争霸"));
        tab.addTab(tab.newTab().setText("爆裂飞车"));
        tab.addTab(tab.newTab().setText("超级飞侠"));
        tab.addTab(tab.newTab().setText("喜羊羊与灰太郎"));
    }

    @Override
    protected void initListener() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab.getTabAt(position).select();
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
                    if (isSelect == true) {
                        if (!Util.isNull(signDialog)) {
                            signDialog.setSignIcon(URLConstans.GET_ICON.ICON100 + str);
                            signDialog.show();

                            signDialog.setBtnLookOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    signDialog.dismiss();
                                    MySignActivity.actionStart(SignActivity.this,str);
                                }
                            });
                        }
                    }
                }

            }
        });

        signDialog.setBtnLookOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNull(signDialog)) {


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
}
