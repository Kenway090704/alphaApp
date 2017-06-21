package com.alpha.alphaapp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;

/**
 * Created by kenway on 17/6/21 14:52
 * Email : xiaokai090704@126.com
 */

public class MySignDialog extends Dialog {
    private Context context;
    private Button btn_ikown;
    private TextView tv_msg;

    public MySignDialog(Context context) {
        this(context, R.style.dialog_alert);
    }

    public MySignDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_my_sign);
        btn_ikown = (Button) findViewById(R.id.dialog_my_sign_btn_ikown);

        btn_ikown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
