package com.alpha.alphaapp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alpha.alphaapp.R;
import com.alpha.lib_stub.comm.URLConstans;
import com.bumptech.glide.Glide;


/**
 * Created by kenway on 17/6/21 14:52
 * Email : xiaokai090704@126.com
 */

public class SignDialog extends Dialog {
    private Context context;
    private ImageView iv_sign;
    private Button btn_ikown, btn_look;


    public SignDialog(Context context) {
        this(context, R.style.dialog_alert);
    }

    public SignDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        setContentView(R.layout.dialog_sign);
        iv_sign = (ImageView) findViewById(R.id.dialog_sign_iv_sign);
        btn_ikown = (Button) findViewById(R.id.dialog_sign_btn_ikown);
        btn_look = (Button) findViewById(R.id.dialog_sign_btn_look);

        btn_ikown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setBtnLookOnClickListener(View.OnClickListener listener) {
        btn_look.setOnClickListener(listener);

    }

    /**
     * 设置选中的签到图片
     * @param iconUrl
     */
    public void setSignIcon(String iconUrl) {
        //使用Glide展示图片
        Glide.with(context).load(iconUrl).into(iv_sign);
    }
}
