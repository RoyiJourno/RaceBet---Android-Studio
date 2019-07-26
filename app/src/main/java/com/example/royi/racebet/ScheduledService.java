package com.example.royi.racebet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class ScheduledService extends Service {

        private Timer timer = new Timer();

        @Override
        public void onCreate() {
            super.onCreate();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                   // sendRequestToServer(); //Your code here
                }
            }, 0, 5 * 60 * 1000);//5 Minutes
        }


        @Override
        public void onDestroy() {
            super.onDestroy();
        }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}