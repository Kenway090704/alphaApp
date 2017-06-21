package com.alpha.alphaapp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;

import com.alpha.alphaapp.R;

/**
 * Created by kenway on 17/6/15 14:12
 * Email : xiaokai090704@126.com
 * 修改头像从底部弹出
 */

public class ModifyIconDialog extends Dialog {
    private Context context;

    public ModifyIconDialog(Context context) {
        this(context, R.style.dialog_alert);
        this.context = context;

    }

    public ModifyIconDialog(Context context, int themeResId) {
        super(context, themeResId);
        initViews();
    }

    private void initViews() {
    }
}
