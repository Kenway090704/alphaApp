package com.alpha.alphaapp.ui.v_1_0.mine.addr;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;
import com.alpha.alphaapp.model.OnModelCallback;
import com.alpha.alphaapp.model.v_1_0.userinfo.ModifyUserInfoLogic;
import com.alpha.alphaapp.ui.base.BaseActivity;
import com.alpha.alphaapp.ui.v_1_0.mine.MineInfoActivity;
import com.alpha.alphaapp.ui.v_1_0.mine.logic.GetPCityAreaLogic;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.mine.ModifyInfoItemView;
import com.alpha.alphaapp.ui.widget.tx.ErrorTextView;
import com.alpha.lib_sdk.app.log.LogUtils;
import com.alpha.lib_sdk.app.tool.StringUtils;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;


/**
 * Created by kenway on 17/6/16 19:14
 * Email : xiaokai090704@126.com
 * 修改通信地址的界面
 */

public class ModifyContactAddrActivity extends BaseActivity {
    private static final String TAG = "ModifyContactAddrActivity";
    private TitleLayout titlelayout;
    private ModifyInfoItemView miiv_pca;
    private LinearLayout layout;
    private EditText et_detail;
    private ErrorTextView tv_error;
    private Button btn_save;

    /**
     * 地区选中器
     */
    private GetPCityAreaLogic logic_pca;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_contact_addr;
    }

    @Override
    protected void initView() {
        titlelayout = (TitleLayout) findViewById(R.id.mod_contact_addr_titlelayout);
        miiv_pca = (ModifyInfoItemView) findViewById(R.id.mod_contact_addr_miiv_pca);

        layout = (LinearLayout) findViewById(R.id.mod_contact_addr_layout);
        et_detail = (EditText) findViewById(R.id.mod_contact_addr_et_detail);

        tv_error = (ErrorTextView) findViewById(R.id.mod_contact_addr_tv_error);
        btn_save = (Button) findViewById(R.id.mod_contact_addr_btn_save);
        logic_pca = new GetPCityAreaLogic(this);


    }

    @Override
    public void initData() {
        String contact_addr = AccountManager.getInstance().getUserInfo().getContact_addr();
        String pca = StringUtils.getPCAString(contact_addr);
        String detail = StringUtils.getDetailAddrString(contact_addr);
        miiv_pca.setMsg(pca);
        et_detail.setText(detail);
        KeyBoardUtils.setFoucus(et_detail,this);

    }

    @Override
    protected void initListener() {

        titlelayout.setOnBackListener(new TitleLayout.OnBackListener() {
            @Override
            public void onBackLister() {
                finish();
            }
        });

        miiv_pca.setIvRightOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeybord(et_detail, ModifyContactAddrActivity.this);
                //弹出底部对话框
                logic_pca.show();
            }
        });
        et_detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_detail.getText().toString())) {
                    btn_save.setEnabled(Boolean.FALSE);
                    btn_save.setBackgroundResource(R.drawable.shape_com_bg_gray);
                } else {
                    btn_save.setEnabled(Boolean.TRUE);
                    btn_save.setBackgroundResource(R.drawable.shape_com_bg_red);
                }
                tv_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        logic_pca.setBtnSaveOnListener(new GetPCityAreaLogic.OnBtnSaveListener() {
            @Override
            public void onSubmit(String province, String city, String area) {
                miiv_pca.setMsg(province + city + area);
                if (!miiv_pca.getMsg().equals(AccountManager.getInstance().getUserInfo().getContact_addr())){
                    btn_save.setEnabled(Boolean.TRUE);
                    btn_save.setBackgroundResource(R.drawable.shape_com_bg_red);
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeybord(et_detail, ModifyContactAddrActivity.this);
                // 将信息提交修改信息

                //保存该数据为用户的通信地址,如果信息与填写的内容相同,则不修改
                String addr = miiv_pca.getMsg() + et_detail.getText().toString();
                //地址是否与当前地址相同
                boolean isSame = addr.equals(AccountManager.getInstance().getUserInfo().getContact_addr());
                if (!Util.isNullOrBlank(et_detail.getText().toString()) && !isSame) {
                    doModifyContactAddr(addr);
                }
            }
        });

    }

    /**
     * 修改联系方式
     */
    private void doModifyContactAddr(String contact_addr) {
        UserInfo info = new UserInfo();
        info.setContact_addr(contact_addr);

        String sskey = AccountManager.getInstance().getSskey();

        OnModelCallback<Object> back = new OnModelCallback<Object>() {
            @Override
            public void onModelSuccessed(Object o) {
                MineInfoActivity.actionStart(ModifyContactAddrActivity.this, null, null);
            }

            @Override
            public void onModelFailed(String failedMsg) {
                tv_error.setText(failedMsg);
                LogUtils.e(TAG, "failed==" + failedMsg);
            }
        };
        ModifyUserInfoLogic.doModifyUserInfo(sskey, info, ModifyUserInfoLogic.MODIFY_CONTACT_ADDR, back);
    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ModifyContactAddrActivity.class);
        context.startActivity(intent);
    }
}
