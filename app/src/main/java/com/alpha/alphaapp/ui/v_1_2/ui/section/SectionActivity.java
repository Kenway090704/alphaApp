package com.alpha.alphaapp.ui.v_1_2.ui.section;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_2.bean.SectionDeatalBean;
import com.alpha.alphaapp.model.v_1_2.bean.TopicListBean;
import com.alpha.alphaapp.model.v_1_2.logic.ForumNetPostUtil;
import com.alpha.alphaapp.model.v_1_2.logic.section.SectionLogic;
import com.alpha.alphaapp.ui.v_1_2.ui.cell.SectionHeaderCell;
import com.alpha.alphaapp.ui.v_1_2.ui.cell.SectionTopicCell;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.alpha.lib_stub.uikit.adapter.activity.AbsBaseListActivity;
import com.alpha.lib_stub.uikit.adapter.base.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenway on 17/9/21 11:15
 * Email : xiaokai090704@126.com
 * 版块简介页
 */

public class SectionActivity extends AbsBaseListActivity {
    public static final String FID = "fid";
    public static final String FORUMNAME = "forumname";

    private int offset = 0;

    private TitleLayout titleLayout;

    private Button btn_enter;
    private Button btn_attention;
    private String fid;
    private String forumName;

    @Override
    public int getLayoutId() {
        fid = getIntent().getStringExtra(FID);
        forumName = getIntent().getStringExtra(FORUMNAME);
        return R.layout.activity_section;
    }

    @Override
    public void initViews(View root) {
        titleLayout = (TitleLayout) findViewById(R.id.acty_section_title);
        btn_enter = (Button) findViewById(R.id.acty_section_btn_enter);
        btn_attention = (Button) findViewById(R.id.acty_section_btn_attention);
    }

    @Override
    public RecyclerView initRecylerView(View view) {

        RecyclerView rv = (RecyclerView) findViewById(R.id.acty_section_rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        return rv;
    }

    @Override
    public SwipeRefreshLayout initSwipeRefreshLayout(View view) {
        SwipeRefreshLayout swip = (SwipeRefreshLayout) findViewById(R.id.acty_section_swlayout);
        return swip;
    }

    @Override
    public void initEvent() {
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SectionListActivity.actionStart(btn_enter.getContext(), fid, forumName);

            }
        });

        btn_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(btn_attention.getContext(), "关注接口未调试");
            }
        });
    }

    @Override
    public void initData() {
        titleLayout.setTitleText(forumName);
    }

    @Override
    public void onRecyclerViewInitialized() {
        //初始化View和数据加载
        setColorSchemeResources(R.color.common_red);
        loadData();
    }

    private void loadData() {
        offset = 0;
        mBaseAdapter.showLoading();
        //加载版块简介头部
        OnModelCallback<SectionDeatalBean> callback = new OnModelCallback<SectionDeatalBean>() {
            @Override
            public void onModelSuccessed(SectionDeatalBean sectionDeatalBean) {
                mBaseAdapter.hideLoading();
                mBaseAdapter.add(new SectionHeaderCell(sectionDeatalBean));
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        };
        SectionLogic.getSectionDetailInfo(fid, callback);

        //加载版块热贴
        OnModelCallback<List<TopicListBean>> call = new OnModelCallback<List<TopicListBean>>() {
            @Override
            public void onModelSuccessed(List<TopicListBean> topics) {
                mBaseAdapter.hideLoading();
                if (Util.isNull(topics) || topics.size() == 0) {
                    mBaseAdapter.showEmpty();
                } else {
                    mBaseAdapter.addAll(getCells(topics));
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                mBaseAdapter.showError();
                LogUtils.e(failedMsg);
            }
        };
        SectionLogic.getSectionHotTopicList(fid, ForumNetPostUtil.VALUE_LASTPOST, offset, call);

    }

    @Override
    public void onPullRefresh() {
        offset = 0;
        mBaseAdapter.showLoading();
        //加载版块简介头部
        OnModelCallback<SectionDeatalBean> callback = new OnModelCallback<SectionDeatalBean>() {
            @Override
            public void onModelSuccessed(SectionDeatalBean sectionDeatalBean) {
                mBaseAdapter.hideLoading();
                mBaseAdapter.clear();
                mBaseAdapter.add(new SectionHeaderCell(sectionDeatalBean));
            }

            @Override
            public void onModelFailed(String failedMsg) {

            }
        };
        SectionLogic.getSectionDetailInfo(fid, callback);

        //加载版块热贴
        OnModelCallback<List<TopicListBean>> call = new OnModelCallback<List<TopicListBean>>() {
            @Override
            public void onModelSuccessed(final List<TopicListBean> topics) {
                mBaseAdapter.hideLoading();
                if (Util.isNull(topics) || topics.size() == 0) {
                    mBaseAdapter.showEmpty();
                } else {

                    setRefreshing(false);
                    mBaseAdapter.addAll(getCells(topics));
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                mBaseAdapter.showError();
                LogUtils.e(failedMsg);
            }
        };
        SectionLogic.getSectionHotTopicList(fid, ForumNetPostUtil.VALUE_LASTPOST, offset, call);

    }

    @Override
    public void onLoadMore() {

        offset += 20;
        OnModelCallback<List<TopicListBean>> call = new OnModelCallback<List<TopicListBean>>() {
            @Override
            public void onModelSuccessed(final List<TopicListBean> topics) {
                if (Util.isNull(topics) || topics.size() == 0) {
                    mBaseAdapter.showEmpty();
                } else {

                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBaseAdapter.hideLoadMore();
                            mBaseAdapter.addAll(getCells(topics));
                        }
                    }, 2000);
                }
            }

            @Override
            public void onModelFailed(String failedMsg) {
                mBaseAdapter.showError();
                LogUtils.e(failedMsg);
            }
        };
        SectionLogic.getSectionHotTopicList(fid,ForumNetPostUtil.VALUE_LASTPOST, offset, call);

    }

    @Override
    public List<Cell> getCells(List list) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            cells.add(new SectionTopicCell((TopicListBean) list.get(i)));
        }
        return cells;
    }

    /**
     * 从其它页面跳转到HomeActivity
     *
     * @param context
     */
    public static void actionStart(Context context, String forumName, String fid) {
        Intent intent = new Intent(context, SectionActivity.class);
        intent.putExtra(FID, fid);
        intent.putExtra(FORUMNAME, forumName);
        context.startActivity(intent);
    }
}
