package com.alpha.alphaapp.ui.sign;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.comm.URLConstans;
import com.alpha.alphaapp.ui.BaseActivity;
import com.alpha.alphaapp.ui.widget.dialog.MySignDialog;
import com.alpha.lib_sdk.app.tool.Util;
import com.alpha.lib_sdk.app.unitily.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

/**
 * Created by kenway on 17/6/21 15:57
 * Email : xiaokai090704@126.com
 */

public class MySignActivity extends BaseActivity {

    private static final String TAG = "MySignActivity";
    private TextView tv_signrule;
    private ImageView iv_signIcon;
    private String icon;
    private MySignDialog mySignDialog;


    @Override
    protected int getLayoutId() {
        icon = getIntent().getStringExtra("icon");
        return R.layout.activity_my_sign;
    }

    @Override
    protected void initView() {
        tv_signrule = (TextView) findViewById(R.id.my_sign_tv_signrule);
        iv_signIcon = (ImageView) findViewById(R.id.my_sign_iv_signicon);
        mySignDialog = new MySignDialog(this);
    }

    @Override
    public void initData() {
        if (!Util.isNullOrBlank(icon)) {
            //使用Glide展示图片
            final RequestBuilder<Drawable> thumbnailRequest = Glide.with(this).load(R.drawable.launcher);
            Glide.with(this).load(URLConstans.GET_ICON.ICON300 + icon).thumbnail(thumbnailRequest).into(iv_signIcon);
        }
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

    }

    public static void actionStart(Context context, String icon) {
        Intent intent = new Intent(context, MySignActivity.class);
        intent.putExtra("icon", icon);
        context.startActivity(intent);
    }
}
