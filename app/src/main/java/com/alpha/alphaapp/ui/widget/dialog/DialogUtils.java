package com.alpha.alphaapp.ui.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.widget.dialog.CustomLoadingDialog;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/13 15:17
 * Email : xiaokai090704@126.com
 */

public class DialogUtils {
    private static final String TAG = "DialogUtils";

    /**
     * 创建单选提示对话框
     *
     * @param context
     * @param stringId
     * @param btnlistener
     * @return
     */
    public static Dialog createSingleChoiceDialog(Context context, int stringId, View.OnClickListener btnlistener) {
        Dialog dialog = new Dialog(context, R.style.dialog_alert);
        dialog.setContentView(R.layout.dialog_alert_singlechoice);
        TextView tv_msg1 = (TextView) dialog.findViewById(R.id.dialog_alert_single_tv_msg_1);
        TextView tv_insure = (TextView) dialog.findViewById(R.id.dialog_alert_single_tv_insure);
        if (!Util.isNullOrBlank(stringId+"")) {
            tv_msg1.setText(stringId);
        } else {
            tv_msg1.setVisibility(View.GONE);
        }
        tv_msg1.getLineCount();
        Log.e(TAG, "tv_msg1.getLineCount()==" + tv_msg1.getLineCount());
        if (!Util.isNull(btnlistener))
            tv_insure.setOnClickListener(btnlistener);
        //点击屏幕外不可取消
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    /**
     * 创建两个按钮的提示对话框
     * "确认""取消"
     * <p>取消按钮默认取消</p>
     * @param context
     * @param stringId
     * @param insurelistener
     * @return
     */
    public static Dialog createTwoChoiceDialog(Context context, int stringId, View.OnClickListener insurelistener) {
        final Dialog dialog = new Dialog(context, R.style.dialog_alert);
        dialog.setContentView(R.layout.dialog_alert_twochoice);
        TextView tv_msg1 = (TextView) dialog.findViewById(R.id.dialog_alert_two_tv_msg_1);
        TextView tv_insure = (TextView) dialog.findViewById(R.id.dialog_alert_two_tv_insure);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.dialog_alert_two_tv_cancel);
        if (!Util.isNullOrBlank(stringId+"")) {
            tv_msg1.setText(stringId);
        } else {
            tv_msg1.setVisibility(View.GONE);
        }

        if (!Util.isNull(insurelistener))
            tv_insure.setOnClickListener(insurelistener);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //点击屏幕外不可取消
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * 创建LoadingDialog
     *
     * @param context
     * @param animRes
     * @return
     */
    public static CustomLoadingDialog createLoadingDialog(Context context, int animRes) {
        CustomLoadingDialog loadingDialog =new CustomLoadingDialog(context);
        return loadingDialog;
    }


}
