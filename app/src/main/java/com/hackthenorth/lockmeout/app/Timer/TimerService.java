package com.hackthenorth.lockmeout.app.Timer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Jon on 20/09/2014.
 */
public class TimerService extends Service {
    TimeCounter timer;
    long startTime;
    long endTime;
    long currentTime;
    long timerMilliSeconds;
    Boolean zone;
    Date time = new Date();

    String startTimeFile = "com.hackthenorth.lockmeout.startTime";
    String endTimeFile = "com.hackthenorth.lockmeout.endTime";
    //String zoneFile = "com.hackthenorth.lockmeout.zoneFile";

    SharedPreferences prefs;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        prefs = getSharedPreferences(
                "com.hackthenorth.lockmeout", Context.MODE_PRIVATE);
        endTime = prefs.getLong(endTimeFile, 10);
        startTime = prefs.getLong(startTimeFile, 0);


        currentTime = time.getTime();
        Log.d("TimerService","Current time: "+currentTime );
        //Toast.makeText(getApplicationContext(), "Current time: "+currentTime, Toast.LENGTH_LONG).show();
        Log.d("TimerService", "Start Time: "+ startTime);
        //Toast.makeText(getApplicationContext(), "Start Time: "+ startTime, Toast.LENGTH_LONG).show();
        Log.d("TimerService", "THE SECOND CHECKING OF End Time: "+ endTime);

        while(currentTime < startTime){currentTime = time.getTime();}

        if(currentTime > startTime && currentTime < endTime){

            timerMilliSeconds = endTime - currentTime;
            timer = new TimeCounter(timerMilliSeconds,5000);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        timer.start();

        return START_FLAG_REDELIVERY;
    }
    private class TimeCounter extends CountDownTimer {

        public TimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            Log.d("aaa", "The service has been completed!");
            Toast.makeText(getApplicationContext(), "SERVICE COMPLETED", Toast.LENGTH_LONG).show();
            //Trigger the phone unlock event
            //Intent unlock = new Intent(this,unlock.class);
            stopSelf();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.d("aaa", String.valueOf(millisUntilFinished/1000));
            Toast.makeText(getApplicationContext(), (millisUntilFinished/1000)+"", Toast.LENGTH_LONG).show();
            System.out.println((millisUntilFinished / 1000));
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        timer.cancel();
        //stopSelf();
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}
