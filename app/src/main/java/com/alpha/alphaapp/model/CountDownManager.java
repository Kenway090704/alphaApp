package com.alpha.alphaapp.model;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.alpha.alphaapp.R;

/**
 * Created by kenway on 17/5/26 12:11
 * Email : xiaokai090704@126.com
 * 倒计时控件的管理类
 */

public class CountDownManager {
    private TextView tv;
    // 实现倒计时功能
    CountDownTimer timer = new CountDownTimer(5000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tv.setClickable(false);
            tv.setText(millisUntilFinished / 1000 + "s后重新发送");

        }

        @Override
        public void onFinish() {
            tv.setClickable(true);
            tv.setBackgroundResource(R.drawable.shape_com_bg_red);
            tv.setText("重新发送");
        }
    };

    public void setTextView(TextView tv) {
        this.tv = tv;
    }

    public void start() {
        timer.start();
    }

    public void cancel() {
        timer.cancel();
    }

}
