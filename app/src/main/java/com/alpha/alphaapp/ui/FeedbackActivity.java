package com.alpha.alphaapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.alpha.alphaapp.R;
import com.alpha.alphaapp.ui.widget.TitleLayout;
import com.alpha.lib_sdk.app.log.LogCatch;

import java.io.File;

public class FeedbackActivity extends BaseActivity {


    private Button btn_submit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        TitleLayout titleLayout = (TitleLayout) findViewById(R.id.feedback_titlelayout);

        btn_submit = (Button) findViewById(R.id.feedback_btn_submit);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail("254903810@qq.com");
            }
        });
    }

    public void sendEmail(String adress) {
        Intent email = new Intent(Intent.ACTION_SEND);
        // 附件
        File file = new File(LogCatch.getInstance(this).GetFileInfo());
        LogUtils.e("附件文件为：" + LogCatch.getInstance(this).GetFileInfo());
        //邮件发送类型：带附件的邮件
        email.setType("application/octet-stream");
        //邮件接收者（数组，可以是多位接收者）
        LogUtils.e("adress = " + adress);
        String[] emailReciver = new String[]{adress};

        String emailTitle = "tuoluoGame LOG日志";
        //String emailContent = "内容";
        //设置邮件地址
        email.putExtra(Intent.EXTRA_EMAIL, emailReciver);
        //设置邮件标题
        email.putExtra(Intent.EXTRA_SUBJECT, emailTitle);
        //附件
        email.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        //调用系统的邮件系统
        startActivity(Intent.createChooser(email, "请选择邮件发送软件"));
    }
}
