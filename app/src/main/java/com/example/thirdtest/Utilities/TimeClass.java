package com.example.thirdtest.Utilities;

import android.os.CountDownTimer;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class TimeClass {

    private static final long INITIAL = 7000;
    private String timeCount;
    private CountDownTimer mTimeCount;
    private boolean mTimeRun;
    public boolean client = false;
    public long mTimeMilis = INITIAL;


    public void starTime(){
        mTimeCount = new CountDownTimer(mTimeMilis,1000) {
            @Override
            public void onTick(long millisUntil) {
                mTimeMilis = millisUntil;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                    mTimeRun = false;
                    resetTime();

            }
        }.start();
        mTimeRun = true;
    }


    public void resetTime(){
        mTimeMilis = INITIAL;
        updateCountDown();
    }

    public void updateCountDown(){
        int seconds = (int)(mTimeMilis/1000)% 60;
        int minute = (int)(mTimeMilis/1000)/ 60;
        String timeLeft = String.format(Locale.getDefault(),"%02d:%02d",minute, seconds);

        timeCount = timeLeft;

    }

    public void TimeDown (){
        starTime();
    }
}
