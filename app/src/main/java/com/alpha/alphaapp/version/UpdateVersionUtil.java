package com.alpha.alphaapp.version;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.LaunchActivity;
import com.alpha.lib_sdk.app.log.Log;
import com.alpha.lib_sdk.app.net.ReqCallBack;
import com.alpha.lib_sdk.app.net.RequestManager;
import com.alpha.lib_sdk.app.tool.JsonUtil;
import com.alpha.lib_sdk.app.unitily.ApkUtils;
import com.alpha.lib_sdk.app.unitily.NetUtils;
import com.alpha.lib_sdk.app.unitily.SDCardUtils;
import com.alpha.lib_sdk.app.unitily.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by kenway on 17/7/12 17:28
 * Email : xiaokai090704@126.com
 */

public class UpdateVersionUtil {
    private static final String TAG = "UpdateVersionUtil";

    /**
     * 接口回调
     */
    public interface UpdateListener {
        void onUpdateReturned(int updateStatus, VersionInfo info);
    }

    public UpdateListener updateListener;

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    /**
     * 检测是否有新版本
     *
     * @param context
     * @param url      获取版本信息
     * @param isRelese 是否为正式版
     */
    public static void doCheckVersionUpdate(final Context context, String url, boolean isRelese) {


        //本地测试是否有新版本发布
        UpdateVersionUtil.UpdateListener listener = new UpdateVersionUtil.UpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, final VersionInfo info) {
                //判断回调过来的版本检测状态
                switch (updateStatus) {
                    case UpdateStatus.YES:
                        //弹出更新提示
                        UpdateVersionUtil.showDialog(context, info);
                        break;
                    case UpdateStatus.NO:
                        //没有新版本
                        ToastUtils.showToast(context, "已经是最新版本了!");
                        break;
                    case UpdateStatus.NOWIFI:
                        //当前是非Wifi网络
                        ToastUtils.showToast(context, "只有在wifi下更新");
                        UpdateVersionUtil.showDialog(context, info);
//                                DialogUtils.showDialog(MainActivity.this, "温馨提示","当前非wifi网络,下载会消耗手机流量!", "确定", "取消",new DialogOnClickListenner() {
//                              @Override
//                              public void btnConfirmClick(Dialog dialog) {
//                                  dialog.dismiss();
//                                  //点击确定之后弹出更新对话框
//                                  UpdateVersionUtil.showDialog(LaunchActivity.this,info);
//                              }
//
//                              @Override
//                              public void btnCancelClick(Dialog dialog) {
//                                  dialog.dismiss();
//                              }
//                          });
                        break;
                    case UpdateStatus.ERROR:
                        //检测失败
                        ToastUtils.showToast(context, "检测失败,请稍后重试!");
                        break;
                    case UpdateStatus.TIMEOUT:
                        //链接超时
                        ToastUtils.showToast(context, "链接超时");
                        break;

                }
            }
        };

        if (isRelese) {
            //正式版
            checkVersion(context, url, listener);
        } else {
            //debug
            localCheckVersion(context, listener);
        }
    }

    /**
     * 网络测试 检查版本
     */
    private static void checkVersion(final Context context, String url, final UpdateListener listener) {
        ReqCallBack<String> call = new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                try {
                    JSONObject jsonObject = JsonUtil.stringToJson(result);
                    JSONArray array = jsonObject.getJSONArray("data");
                    VersionInfo mVersionInfo = JsonUtil.jsonToBean(array.getJSONObject(0).toString(), VersionInfo.class);
                    int clientVersion = ApkUtils.getVersionCode(context);
                    int serverVersion = mVersionInfo.getVersionCode();
                    //有新版本
                    if (clientVersion < serverVersion) {
                        if (!NetUtils.isWifi(context)) {
                            listener.onUpdateReturned(UpdateStatus.NOWIFI, mVersionInfo);
                        } else {
                            listener.onUpdateReturned(UpdateStatus.YES, mVersionInfo);
                        }
                    } else {
                        listener.onUpdateReturned(UpdateStatus.NO, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onUpdateReturned(UpdateStatus.ERROR, null);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                listener.onUpdateReturned(UpdateStatus.TIMEOUT, null);
            }
        };
        RequestManager.getInstance(context).requestGet(url, call);
    }

    private static void localCheckVersion(Context context, UpdateListener listener) {
        try {
            VersionInfo mVersionInfo = new VersionInfo();
            mVersionInfo.setDownloadUrl("http://gdown.baidu.com/data/wisegame/57a788487345e938/QQ_358.apk");
            mVersionInfo.setVersionDesc("\n更新内容：\n1、增加更新功能\n2、增加apk下载！\n3、用户界面优化！\n4、修改了不知道什么功能！");
            mVersionInfo.setVersionCode(2);
            mVersionInfo.setVersionName("v 1.0.4");
            mVersionInfo.setVersionSize("9.3M");
            mVersionInfo.setId("1");
            int clientVersionCode = ApkUtils.getVersionCode(context);
            int serverVersionCode = mVersionInfo.getVersionCode();

            Log.e(TAG, "clientVersionCode==" + clientVersionCode + ",serverVersionCode==" + serverVersionCode);
            //有新版本
            if (clientVersionCode < serverVersionCode) {

                if (!NetUtils.isWifi(context)) {
                    listener.onUpdateReturned(UpdateStatus.NOWIFI, mVersionInfo);
                } else {
                    listener.onUpdateReturned(UpdateStatus.YES, mVersionInfo);
                }
            } else {
                //无新本
                listener.onUpdateReturned(UpdateStatus.NO, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onUpdateReturned(UpdateStatus.ERROR, null);
        }
    }


    private static void showDialog(final Context context, final VersionInfo versionInfo) {
        final Dialog dialog = new AlertDialog.Builder(context).create();
        //这里的名字可以修改
        final File file = new File(SDCardUtils.getRootDirectory() + "/updateVersion/gdmsaec-app.apk");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_version, null);
        dialog.setContentView(view);

        final Button btnOk = (Button) view.findViewById(R.id.btn_update_id_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_update_id_cancel);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_update_content);
        TextView tvUpdateTitle = (TextView) view.findViewById(R.id.tv_update_title);
        TextView tvUpdateMsgSize = (TextView) view.findViewById(R.id.tv_update_msg_size);

        tvContent.setText(versionInfo.getVersionDesc());
        tvUpdateTitle.setText("最新版本: " + versionInfo.getVersionName());

        if (file.exists() && file.getName().equals("gdmsaec-app.apk")) {
            tvUpdateMsgSize.setText("新版本已经下载,是否安装");
        } else {
            tvUpdateMsgSize.setText("新版本大小: " + versionInfo.getVersionSize());
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (v.getId() == R.id.btn_update_id_ok) {

                    //新版本已经下载
                    if (file.exists() && file.getName().equals("gdmsaec-app.apk")) {
                        Intent intent = ApkUtils.getInstallIntent(file);
                        context.startActivity(intent);
                    } else {
                        //没有下载,则开启服务下载最新版本
                        Log.e(TAG, "新版本即将开始下载。。。。");
                        Intent intent = new Intent(context, UpdateVersionService.class);
                        intent.putExtra("downloadUrl", versionInfo.getDownloadUrl());
                        context.startService(intent);
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

//    /**
//     * 收起通知栏
//     */
//
//    public  static  void  collapsingNotification(Context context){
//        //这里更改为新的
////        Object service = context.getSystemService("statusbar");
//        Object service=null;
//        if (null == service)
//            return;
//        try {
//            Class<?> clazz = Class.forName("android.app.StatusBarManager");
//            int sdkVersion = android.os.Build.VERSION.SDK_INT;
//            Method collapse;
//            if (sdkVersion <= 16) {
//                collapse = clazz.getMethod("collapse");
//            } else {
//                collapse = clazz.getMethod("collapsePanels");
//            }
//            collapse.setAccessible(true);
//            collapse.invoke(service);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 收起通知栏
     *
     * @param context
     */
    public static void collapseStatusBar(Context context) {
        try {
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;
            if (Build.VERSION.SDK_INT <= 16) {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else {
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

}
