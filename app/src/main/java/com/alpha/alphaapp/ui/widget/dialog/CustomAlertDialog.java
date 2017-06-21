package com.alpha.alphaapp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.alpha.alphaapp.R;

/**
 * Created by kenway on 17/6/13 09:55
 * Email : xiaokai090704@126.com
 * 提示对话框
 */

public class CustomAlertDialog extends Dialog {
    private Context context;
    private TextView tv_insure;
    private TextView tv_msg;

    public CustomAlertDialog(Context context) {
        this(context, R.style.dialog_alert);
    }

    public CustomAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        setContentView(R.layout.dialog_alert);
        tv_insure = (TextView) findViewById(R.id.dialog_alert_tv_insure);
        tv_msg = (TextView) findViewById(R.id.dialog_alert_tv_msg);
    }



    public void setOnClickListener(View.OnClickListener listener) {
        tv_insure.setOnClickListener(listener);

    }

    public void setTxtMsg(String msg) {
        tv_msg.setText(msg);
    }

    public void setTxtMsg(int resouredId) {
        tv_msg.setText(context.getString(resouredId));
    }
}
