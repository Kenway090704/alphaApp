package com.alpha.alphaapp.ui.mine.addr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.account.AccountManager;
import com.alpha.alphaapp.account.UserInfo;

import com.alpha.alphaapp.bean.CityBean;
import com.alpha.alphaapp.bean.JsonBean;
import com.alpha.alphaapp.bean.ProviceBean;
import com.alpha.alphaapp.bean.SchoolBean;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.model.modifyinfo.ModifyUserInfoLogic;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.mine.MineInfoActivity;
import com.alpha.alphaapp.ui.mine.logic.GetPCityAreaLogic;
import com.alpha.alphaapp.ui.mine.logic.GetSchoolLogic;
import com.alpha.alphaapp.ui.mine.school.ModifySchoolActivity;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.alphaapp.ui.widget.mine.ModifyInfoItemView;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.GetJsonDataUtil;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.KeyBoardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kenway on 17/6/16 19:14
 * Email : xiaokai090704@126.com
 * 修改通信地址的界面
 */

public class ModifyContactAddrActivity extends BaseActivity {
    private static final String TAG = "ModifyContactAddrActivity";
    public static final int RESQUEST_CODE = 0;
    public static final String CONTACT = "contact";
    private TitleLayout titlelayout;
    private ModifyInfoItemView miiv_pca, miiv_street;
    private LinearLayout layout;
    private EditText et_detail;
    private ImageView iv_del;
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
        miiv_street = (ModifyInfoItemView) findViewById(R.id.mod_contact_addr_miiv_street);
        layout = (LinearLayout) findViewById(R.id.mod_contact_addr_layout);
        et_detail = (EditText) findViewById(R.id.mod_contact_addr_et_detail);
        iv_del = (ImageView) findViewById(R.id.mod_contact_addr_et_detail_iv_del);
        logic_pca = new GetPCityAreaLogic(this);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

        titlelayout.setOnBackListener(new TitleLayout.OnBackListener() {
            @Override
            public void onBackLister() {
                Intent intent = new Intent();
                intent.putExtra(CONTACT, miiv_pca.getMsg() + et_detail.getText().toString());
                setResult(1, intent);
                finish();

            }


        });


        miiv_pca.setIvRightOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeybord(et_detail, ModifyContactAddrActivity.this);
                layout.setVisibility(View.GONE);
                //弹出底部对话框
                logic_pca.show();

            }
        });


        miiv_street.setIvRightOnClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNullOrBlank(miiv_pca.getMsg()))
                    layout.setVisibility(View.VISIBLE);
            }
        });
        et_detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isNullOrBlank(et_detail.getText().toString())) {
                    iv_del.setVisibility(View.GONE);
                } else {
                    iv_del.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_detail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (Util.isNullOrBlank(et_detail.getText().toString())) {
                        iv_del.setVisibility(View.GONE);
                    } else {
                        iv_del.setVisibility(View.VISIBLE);
                    }
                } else {
                    iv_del.setVisibility(View.GONE);

                }
            }
        });
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_detail.getText().clear();
            }
        });

        logic_pca.setBtnSaveOnListener(new GetPCityAreaLogic.OnBtnSaveListener() {
            @Override
            public void onSubmit(String province, String city, String area) {
                miiv_pca.setMsg(province + city + area);
                //保存该数据为用户的通信地址
                doModifyContactAddr(province + city + area);
            }
        });

    }

    /**
     * 修改联系方式
     */
    private void doModifyContactAddr(String contact_addr) {
        UserInfo info = new UserInfo();
        info.setContact_addr(contact_addr);
        ModifyUserInfoLogic.EditInfoCallBack call = new ModifyUserInfoLogic.EditInfoCallBack() {
            @Override
            public void onEditInfoSuccuss() {
                ToastUtils.showShort(ModifyContactAddrActivity.this, "修改地址信息成功");
            }

            @Override
            public void onEditInfoFailed(String failMsg) {

            }
        };
        String sskey = AccountManager.getInstance().getSskey();
        ModifyUserInfoLogic.doModifyUserInfo(sskey, info, ModifyUserInfoLogic.MODIFY_CONTACT_ADDR, call);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (event.getY() > (et_detail.getY() + et_detail.getHeight()) || event.getY() < et_detail.getY()) {
                    //判断是否输入框中有内容,如果有,隐藏输入框,将文字显示到街道位置
                    layout.setVisibility(View.GONE);
                    KeyBoardUtils.closeKeybord(et_detail, this);
                    miiv_street.setMsg(et_detail.getText().toString());
                }
        }
        return super.onTouchEvent(event);
    }

    public static void actionStart(Context context, int reuqest_code, String data2) {
        Intent intent = new Intent(context, ModifyContactAddrActivity.class);
        intent.putExtra("params", data2);
        ((MineInfoActivity) context).startActivityForResult(intent, reuqest_code);
    }
}
