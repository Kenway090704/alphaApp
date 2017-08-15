package com.alpha.alphaapp.ui.v_1_0.mine.school;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.model.v_1_0.bean.SchoolBean;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.userinfo.ModifyUserInfoLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.mine.logic.GetPCityAreaLogic;
import com.alpha.alphaapp.ui.v_1_0.mine.logic.GetSchoolLogic;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.mine.ModifyInfoItemView;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/6/16 19:14
 * Email : xiaokai090704@126.com
 */

public class ModifySchoolActivity extends BaseActivity {
    private static final String TAG = "ModifySchoolActivity";
    private TitleLayout titleLayout;
    private ModifyInfoItemView miiv_pca, miiv_school;
    private GetPCityAreaLogic logic_pca;
    private GetSchoolLogic logic_school;
    private SchoolBean school;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_school;
    }

    @Override
    protected void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.mod_school_titlelayout);
        miiv_pca = (ModifyInfoItemView) findViewById(R.id.mod_school_miiv_pca);
        miiv_school = (ModifyInfoItemView) findViewById(R.id.mod_school_miiv_school);
    }

    @Override
    public void initData() {
        logic_pca = new GetPCityAreaLogic(this);
    }

    @Override
    protected void initListener() {

        titleLayout.setOnBackListener(new TitleLayout.OnBackListener() {
            @Override
            public void onBackLister() {
                finish();
            }


        });
        logic_pca.setBtnSaveOnListener(new GetPCityAreaLogic.OnBtnSaveListener() {
            @Override
            public void onSubmit(String province, String city, String area) {
                miiv_pca.setMsg(province + city + area);
                miiv_school.setMsg("");
                school = new SchoolBean();
                school.setProvince(province);
                school.setCity(city);
                school.setArea(area);
                school.setTxt("");
                logic_school = new GetSchoolLogic(ModifySchoolActivity.this, school);
                logic_school.setBtnSaveOnListener(new GetSchoolLogic.OnBtnSaveListener() {
                    @Override
                    public void onSubmit(String school) {
                        miiv_school.setMsg(school);
                        doModifySchool(miiv_school.getMsg());
                    }
                });
            }


        });
        miiv_pca.setIvRightOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic_pca.show();
            }
        });
        miiv_school.setIvRightOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Util.isNullOrBlank(miiv_pca.getMsg())) {
                    LogUtils.e("请先选者省市区");
                } else {
                    if (!Util.isNull(logic_school)) {
                        logic_school.show();
                    }
                }
            }
        });
    }

    /**
     * 将修改的学校信息提交给服务器
     *
     * @param school
     */
    private void doModifySchool(String school) {
        UserInfo info = new UserInfo();
        info.setSchool(school);

        OnModelCallback<Object> back = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                    finish();
            }

            @Override
            public void onModelFailed(String failedMsg) {

                LogUtils.e( failedMsg);
            }
        };
        String sskey = AccountManager.getInstance().getSskey();
        ModifyUserInfoLogic.doModifyUserInfo(sskey, info, ModifyUserInfoLogic.MODIFY_SCHOOL, back);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ModifySchoolActivity.class);
        context.startActivity(intent);
    }


}
