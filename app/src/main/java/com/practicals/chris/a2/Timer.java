package com.practicals.chris.a2;

import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer extends CountDownTimer {

    private TextView timer;

    Timer(long millisInFuture, long countDownInterval, TextView timer_text) {
        super(millisInFuture, countDownInterval);
        this.timer = timer_text;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        timer.setText(String.valueOf(millisUntilFinished).substring(0, String.valueOf(millisUntilFinished).length() - 3));
    }

    @Override
    public void onFinish() {

    }
}
