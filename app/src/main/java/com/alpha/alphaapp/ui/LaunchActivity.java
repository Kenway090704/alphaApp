package com.alpha.alphaapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alpha.alphaapp.BuildConfig;
import com.alpha.alphaapp.model.v_1_0.login.LoginLogic;
import com.alpha.alphaapp.ui.v_1_0.login.LoginActivity;
import com.alpha.alphaapp.R;
import com.alpha.alphaapp.version.UpdateVersionUtil;
import com.alpha.lib_sdk.app.core.thread.ThreadPool;


/**
 * Created by kenway on 17/5/23 18:25
 * Email : xiaokai090704@126.com
 * 启动页面
 */

public class LaunchActivity extends AppCompatActivity {
    private static final String TAG = "LaunchActivity";
    private TextView tv_version,tv_buildtime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉标题栏
        setContentView(R.layout.activity_launch);

        initViews();




        //判断最近的一次登录是什么登录,如果是有密码的登录,如果有直接登录,如果没有三秒后跳转到登录界面
        if (!LoginLogic.isAutoLogin(this)) {
            ThreadPool.post(new Runnable() {
                @Override
                public void run() {
                    LoginActivity.actionStartClearStack(LaunchActivity.this, null, null);
                }
            }, 3000);
        }

        //测试版本更新
        Button btn_version = (Button) findViewById(R.id.launch_btn_version);
        btn_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //本地测试是否有新版本发布
                UpdateVersionUtil.doCheckVersionUpdate(LaunchActivity.this, null, false);
            }
        });


    }

    private void initViews() {
        tv_version = (TextView) findViewById(R.id.launch_tv_version);
        tv_buildtime = (TextView) findViewById(R.id.launch_tv_buildtime);

        tv_version.setText(BuildConfig.VERSION_NAME);

        if (BuildConfig.DEBUG){
            tv_buildtime.setText(String.valueOf(BuildConfig.buildTime));
        }else {
            tv_buildtime.setVisibility(View.GONE);
        }
    }

//    private void doCheckxUpdate() {
//        UpdateVersionUtil.UpdateListener listener = new UpdateVersionUtil.UpdateListener() {
//            @Override
//            public void onUpdateReturned(int updateStatus, final VersionInfo info) {
//                //判断回调过来的版本检测状态
//                switch (updateStatus) {
//                    case UpdateStatus.YES:
//                        //弹出更新提示
//                        UpdateVersionUtil.showDialog(LaunchActivity.this, info);
//                        break;
//                    case UpdateStatus.NO:
//                        //没有新版本
//                        ToastUtils.showToast(getApplicationContext(), "已经是最新版本了!");
//                        break;
//                    case UpdateStatus.NOWIFI:
//                        //当前是非Wifi网络
//                        ToastUtils.showToast(getApplicationContext(), "只有在wifi下更新");
//                        UpdateVersionUtil.showDialog(LaunchActivity.this, info);
////                                DialogUtils.showDialog(MainActivity.this, "温馨提示","当前非wifi网络,下载会消耗手机流量!", "确定", "取消",new DialogOnClickListenner() {
////                              @Override
////                              public void btnConfirmClick(Dialog dialog) {
////                                  dialog.dismiss();
////                                  //点击确定之后弹出更新对话框
////                                  UpdateVersionUtil.showDialog(LaunchActivity.this,info);
////                              }
////
////                              @Override
////                              public void btnCancelClick(Dialog dialog) {
////                                  dialog.dismiss();
////                              }
////                          });
//                        break;
//                    case UpdateStatus.ERROR:
//                        //检测失败
//                        ToastUtils.showToast(getApplicationContext(), "检测失败,请稍后重试!");
//                        break;
//                    case UpdateStatus.TIMEOUT:
//                        //链接超时
//                        ToastUtils.showToast(getApplicationContext(), "链接超时");
//                        break;
//
//                }
//            }
//        };
//
//        UpdateVersionUtil.localCheckVersion(LaunchActivity.this, listener);
//    }
}
