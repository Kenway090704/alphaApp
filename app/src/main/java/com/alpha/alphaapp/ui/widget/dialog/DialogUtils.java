package com.alpha.alphaapp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.R;
import com.alpha.lib_sdk.app.tool.Util;

/**
 * Created by kenway on 17/7/13 15:17
 * Email : xiaokai090704@126.com
 */

public class DialogUtils {
    private static final String TAG = "DialogUtils";

    /**
     * 提示文字有一行
     *
     * @param context
     * @param stringId
     * @param btnlistener
     * @return
     */
    public static Dialog createSingleChoiceDialog(Context context, int stringId, View.OnClickListener btnlistener) {
        final Dialog dialog = new Dialog(context, R.style.dialog_alert);
        dialog.setContentView(R.layout.dialog_alert_singlechoice_one_line);
        TextView tv_msg1 = (TextView) dialog.findViewById(R.id.dialog_alert_single_one_line_tv_msg_1);
        TextView tv_insure = (TextView) dialog.findViewById(R.id.dialog_alert_single_one_line_tv_insure);
        if (!Util.isNullOrBlank(stringId + "")) {
            tv_msg1.setText(stringId);
        } else {
            tv_msg1.setVisibility(View.GONE);
        }
        if (!Util.isNull(btnlistener)) {
            tv_insure.setOnClickListener(btnlistener);
        } else {
            tv_insure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Util.isNull(dialog))
                        dialog.dismiss();
                }
            });
        }

        //点击屏幕外不可取消
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * 创建单选提示对话框
     * <p>
     * 提示文字有两行
     *
     * @param context
     * @param stringId
     * @param stringId2   第一个文字的id ,第二个文字的id
     * @param btnlistener
     * @return
     */

    public static Dialog createSingleChoiceDialog(Context context, int stringId, int stringId2, View.OnClickListener btnlistener) {
        final Dialog dialog = new Dialog(context, R.style.dialog_alert);
        dialog.setContentView(R.layout.dialog_alert_singlechoice);
        TextView tv_msg1 = (TextView) dialog.findViewById(R.id.dialog_alert_single_tv_msg_1);
        TextView tv_msg2 = (TextView) dialog.findViewById(R.id.dialog_alert_single_tv_msg_2);
        Button btn_insure = (Button) dialog.findViewById(R.id.dialog_alert_single_btn_insure);
        tv_msg1.setText(stringId);
        tv_msg2.setText(stringId2);
        if (!Util.isNull(btnlistener)) {
            btn_insure.setOnClickListener(btnlistener);
        } else {
            btn_insure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Util.isNull(dialog))
                        dialog.dismiss();
                }
            });
        }

        //点击屏幕外不可取消
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * 创建两个按钮的提示对话框
     * 只有一句话
     * "确认""取消"
     * <p>取消按钮默认取消</p>
     *
     * @param context
     * @param strId
     * @param insurelistener
     * @return
     */
    public static Dialog createTwoChoiceDialog(Context context, int strId, View.OnClickListener insurelistener) {
        final Dialog dialog = new Dialog(context, R.style.dialog_alert);
        dialog.setContentView(R.layout.dialog_alert_twochoice_one);
        TextView tv_msg1 = (TextView) dialog.findViewById(R.id.dialog_alert_two_one_tv_msg_1);
        Button btn_insure = (Button) dialog.findViewById(R.id.dialog_alert_two_one_btn_insure);
        Button btn_cancel = (Button) dialog.findViewById(R.id.dialog_alert_two_one_btn_cancel);

        tv_msg1.setText(strId);


        if (!Util.isNull(insurelistener)) {
            btn_insure.setOnClickListener(insurelistener);
        } else {
            btn_insure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Util.isNull(dialog))
                        dialog.dismiss();
                }
            });
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
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
     * 有两行的提示
     * "确认""取消"
     *
     * @param context
     * @param strId
     * @param strId2
     * @param insurelistener
     * @return
     */
    public static Dialog createTwoChoiceDialog(Context context, int strId, int strId2, View.OnClickListener insurelistener) {
        final Dialog dialog = new Dialog(context, R.style.dialog_alert);
        dialog.setContentView(R.layout.dialog_alert_twochoice);
        TextView tv_msg1 = (TextView) dialog.findViewById(R.id.dialog_alert_two_tv_msg_1);
        TextView tv_msg2 = (TextView) dialog.findViewById(R.id.dialog_alert_two_tv_msg_2);
        Button btn_insure = (Button) dialog.findViewById(R.id.dialog_alert_two_btn_insure);
        Button btn_cancel = (Button) dialog.findViewById(R.id.dialog_alert_two_btn_cancel);
        tv_msg1.setText(strId);
        tv_msg2.setText(strId2);
        if (!Util.isNull(insurelistener)) {
            btn_insure.setOnClickListener(insurelistener);
        } else {
            btn_insure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Util.isNull(dialog))
                        dialog.dismiss();
                }
            });
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
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
     *  有帐号的提示框(只在绑定页面有使用)
     * "确认""取消"
     * <p>取消按钮默认取消</p>
     *
     * @param context
     * @param strId
     * @param strId3
     * @param insurelistener
     * @return
     */
    public static Dialog createTwoChoiceDialog(Context context, int strId, int strId3, String account, View.OnClickListener insurelistener) {
        final Dialog dialog = new Dialog(context, R.style.dialog_alert);
        dialog.setContentView(R.layout.dialog_alert_twochoice_has_account);
        TextView tv_msg1 = (TextView) dialog.findViewById(R.id.dialog_alert_two_ac_tv_msg_1);
        TextView tv_msg3 = (TextView) dialog.findViewById(R.id.dialog_alert_two_ac_tv_msg_3);
        TextView tv_ac = (TextView) dialog.findViewById(R.id.dialog_alert_two_ac_tv_ac);
        Button btn_insure = (Button) dialog.findViewById(R.id.dialog_alert_two_ac_btn_insure);
        Button btn_cancel = (Button) dialog.findViewById(R.id.dialog_alert_two_ac_btn_cancel);

        tv_msg1.setText(strId);
        tv_msg3.setText(strId3);
        tv_ac.setText(account);

        if (!Util.isNull(insurelistener)) {
            btn_insure.setOnClickListener(insurelistener);
        }


        btn_cancel.setOnClickListener(new View.OnClickListener() {
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
        CustomLoadingDialog loadingDialog = new CustomLoadingDialog(context);
        return loadingDialog;
    }


}
