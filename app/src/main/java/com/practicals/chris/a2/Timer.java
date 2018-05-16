package com.practicals.chris.a2;

import android.os.CountDownTimer;
import android.widget.TextView;

class Timer extends CountDownTimer {

    private final TextView timer;

    Timer(long millisInFuture, TextView timer_text) {
        super(millisInFuture, (long) 1000);
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
