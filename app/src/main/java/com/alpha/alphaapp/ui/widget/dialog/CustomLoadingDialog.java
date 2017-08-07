package com.alpha.alphaapp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.alpha.alphaapp.R;

/**
 * Created by kenway on 17/6/8 16:49
 * Email : xiaokai090704@126.com
 * 自定义加载对话框
 */

public class CustomLoadingDialog extends Dialog {
    private Context mContext;
    private Animation anim;

    public CustomLoadingDialog(Context context) {
        this(context, R.style.dialog_loading);
        mContext = context;
        ImageView imageView = (ImageView) findViewById(R.id.dialog_loading_circle_iv);
        anim = AnimationUtils.loadAnimation(context, R.anim.anim_loading_rotate);
        imageView.startAnimation(anim);
    }

    public CustomLoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        // 设置对话框的布局
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
    }
    @Override
    public void dismiss() {
        anim.cancel();
        super.dismiss();

    }
}
