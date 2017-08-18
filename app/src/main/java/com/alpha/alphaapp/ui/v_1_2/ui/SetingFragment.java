package com.alpha.alphaapp.ui.v_1_2.ui;

import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.base.BaseFragment;
import com.alpha.lib_sdk.app.cache.DataCleanManager;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.unitily.ResourceUtil;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/8/17 10:20
 * Email : xiaokai090704@126.com
 * <p>
 * 版本1.2中设置
 */

public class SetingFragment extends BaseFragment {

    Button btn_clean_chace;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_forum_setting;
    }

    @Override
    protected void initViews(View root) {
        btn_clean_chace = (Button) root.findViewById(R.id.frag_forum_setting_btn_clear_cache);


    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            btn_clean_chace.setText(ResourceUtil.resToStr(R.string.clear_cache)+"("+DataCleanManager.getTotalCacheSize(getContext())+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initEnvent() {
        btn_clean_chace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DataCleanManager.clearAllCache(getContext());
                    btn_clean_chace.setText(ResourceUtil.resToStr(R.string.clear_cache)+"("+DataCleanManager.getTotalCacheSize(getContext())+")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
