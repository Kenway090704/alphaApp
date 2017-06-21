package com.alpha.alphaapp.ui.mine.school;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.bean.SchoolBean;
import com.alpha.alphaapp.model.modifyinfo.ModifyUserInfoLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.mine.MineInfoActivity;
import com.alpha.alphaapp.ui.mine.logic.GetPCityAreaLogic;
import com.alpha.alphaapp.ui.mine.logic.GetSchoolLogic;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.mine.ModifyInfoItemView;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

/**
 * Created by kenway on 17/6/16 19:14
 * Email : xiaokai090704@126.com
 */

public class ModifySchoolActivity extends BaseActivity {
    private static final String TAG = "ModifySchoolActivity";
    public static final int RESQUEST_CODE = 1;
    public static final String SCHOOL = "school";
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
                Intent intent = new Intent();
                intent.putExtra(SCHOOL, miiv_school.getMsg());
                setResult(1, intent);
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
                if (!Util.isNull(logic_school))
                    logic_school.show();
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
        ModifyUserInfoLogic.EditInfoCallBack callback = new ModifyUserInfoLogic.EditInfoCallBack() {
            @Override
            public void onEditInfoSuccuss() {


            }

            @Override
            public void onEditInfoFailed(String failMsg) {

            }
        };
        String sskey = AccountManager.getInstance().getSskey();
        ModifyUserInfoLogic.doModifyUserInfo(sskey, info, ModifyUserInfoLogic.MODIFY_SCHOOL, callback);
    }

    public static void actionStart(Context context, int request_code, String data2) {
        Intent intent = new Intent(context, ModifySchoolActivity.class);
        intent.putExtra("params", data2);
        ((MineInfoActivity) context).startActivityForResult(intent, request_code);

    }


}
