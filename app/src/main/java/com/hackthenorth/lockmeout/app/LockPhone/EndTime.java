package com.hackthenorth.lockmeout.app.LockPhone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hackthenorth.lockmeout.app.R;
import com.hackthenorth.lockmeout.app.Timer.TimerService;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by Berries on 2014-09-19.
 */
public class EndTime extends LockPhoneFragment {

    RelativeLayout layout;
    private OnLockSelectedListener listener;
    public long startTime;
    public Context ctx;
    public String startTimeFile = "com.hackthenorth.lockmeout.startTime";
    public String endTimeFile = "com.hackthenorth.lockmeout.endTime";
    public SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = (View) inflater.inflate(
                R.layout.fragment_endtime, container, false);
        prefs = ctx.getSharedPreferences(
                "com.hackthenorth.lockmeout", Context.MODE_PRIVATE);

        timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);

        try {
            Field f[] = datePicker.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner")) {
                    field.setAccessible(true);
                    Object yearPicker = new Object();
                    yearPicker = field.get(datePicker);
                    ((View) yearPicker).setVisibility(View.GONE);
                }
            }
        }
        catch (SecurityException e) {
            Log.d("ERROR", e.getMessage());
        }
        catch (IllegalArgumentException e) {
            Log.d("ERROR", e.getMessage());
        }
        catch (IllegalAccessException e) {
            Log.d("ERROR", e.getMessage());
        }

        Button backButton = (Button) view.findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveClicked(timePicker.getCurrentMinute(), timePicker.getCurrentHour(), datePicker.getDayOfMonth(), datePicker.getMonth(), "back");
            }
        });

        Button lockButton = (Button) view.findViewById(R.id.lock_button);

        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minute = timePicker.getCurrentMinute();
                int hour = timePicker.getCurrentHour();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();

                Toast.makeText(ctx.getApplicationContext(), "TESTING", Toast.LENGTH_LONG).show();

                java.util.Calendar calendar = java.util.Calendar.getInstance();

                Calendar cl = Calendar.getInstance();
                Calendar cl2 = Calendar.getInstance();

                switch (datePicker.getMonth()) {
                    case 0:
                        calendar.set(datePicker.getYear(), Calendar.JANUARY, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 1:
                        calendar.set(datePicker.getYear(), Calendar.FEBRUARY, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 2:
                        calendar.set(datePicker.getYear(), Calendar.MARCH, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 3:
                        calendar.set(datePicker.getYear(), Calendar.APRIL, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 4:
                        calendar.set(datePicker.getYear(), Calendar.MAY, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 5:
                        calendar.set(datePicker.getYear(), Calendar.JUNE, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 6:
                        calendar.set(datePicker.getYear(), Calendar.JULY, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 7:
                        calendar.set(datePicker.getYear(), Calendar.AUGUST, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 8:
                        calendar.set(datePicker.getYear(), Calendar.SEPTEMBER, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 9:
                        calendar.set(datePicker.getYear(), Calendar.OCTOBER, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 10:
                        calendar.set(datePicker.getYear(), Calendar.NOVEMBER, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    case 11:
                        calendar.set(datePicker.getYear(), Calendar.DECEMBER, datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                        break;
                    default:
                        break;
                }
                long endTime = calendar.getTimeInMillis();
                Log.d("endTime", "The very first endTIme: " + endTime);
                long startTime = prefs.getLong(startTimeFile, 0);

                if (endTime < startTime) {
                    switch (datePicker.getMonth()) {
                        case 0:
                            calendar.set(datePicker.getYear() + 1, Calendar.JANUARY, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 1:
                            calendar.set(datePicker.getYear() + 1, Calendar.FEBRUARY, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 2:
                            calendar.set(datePicker.getYear() + 1, Calendar.MARCH, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 3:
                            calendar.set(datePicker.getYear() + 1, Calendar.APRIL, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 4:
                            calendar.set(datePicker.getYear() + 1, Calendar.MAY, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 5:
                            calendar.set(datePicker.getYear() + 1, Calendar.JUNE, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 6:
                            calendar.set(datePicker.getYear() + 1, Calendar.JULY, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 7:
                            calendar.set(datePicker.getYear() + 1, Calendar.AUGUST, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 8:
                            calendar.set(datePicker.getYear() + 1, Calendar.SEPTEMBER, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 9:
                            calendar.set(datePicker.getYear() + 1, Calendar.OCTOBER, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 10:
                            calendar.set(datePicker.getYear() + 1, Calendar.NOVEMBER, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        case 11:
                            calendar.set(datePicker.getYear() + 1, Calendar.DECEMBER, datePicker.getDayOfMonth(),
                                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                            break;
                        default:
                            break;
                    }

                    endTime = calendar.getTimeInMillis();
                    Log.d("endTime", "The very second endTIme: " + endTime);
                }
                prefs.edit().putLong(endTimeFile, endTime).apply();
                long tmpEndTime = prefs.getLong(endTimeFile, 0);

                //saveClicked(minute, hour, day, month, "");
                listener.handleLock(datePicker, timePicker );
            }
        });

        return view;
    }


    public interface OnLockSelectedListener{
        public void handleLock(DatePicker datePicker, TimePicker timePicker);
    }
/*
    public handleLockTimer implements handleLock(){
        endTimeFile = "com.hackthenorth.lockmeout.startTime";
        java.util.Calendar calendar =   java.util.Calendar.getInstance();
        SharedPreferences prefs = ctx.getSharedPreferences(
                "com.hackthenorth.lockmeout", Context.MODE_PRIVATE);

        Calendar cl = Calendar.getInstance();
        Calendar cl2 = Calendar.getInstance();

        switch (datePicker.getMonth()) {
            case 0:
                calendar.set(datePicker.getYear(), Calendar.JANUARY, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 1:
                calendar.set(datePicker.getYear(), Calendar.FEBRUARY, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 2:
                calendar.set(datePicker.getYear(), Calendar.MARCH, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 3:
                calendar.set(datePicker.getYear(), Calendar.APRIL, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 4:
                calendar.set(datePicker.getYear(), Calendar.MAY, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 5:
                calendar.set(datePicker.getYear(), Calendar.JUNE, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 6:
                calendar.set(datePicker.getYear(), Calendar.JULY, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 7:
                calendar.set(datePicker.getYear(), Calendar.AUGUST, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 8:
                calendar.set(datePicker.getYear(), Calendar.SEPTEMBER, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 9:
                calendar.set(datePicker.getYear(), Calendar.OCTOBER, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 10:
                calendar.set(datePicker.getYear(), Calendar.NOVEMBER, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            case 11:
                calendar.set(datePicker.getYear(), Calendar.DECEMBER, datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                break;
            default:
                break;
        }
        long endTime = calendar.getTimeInMillis();
        cl.setTimeInMillis(endTime);  //here your time in miliseconds
        startTime = prefs.getLong(startTimeFile, 0);
        String date = "cl date:" + cl.get(Calendar.DAY_OF_MONTH) + ":" + cl.get(Calendar.MONTH) + ":" + cl.get(Calendar.YEAR);
        String time = "cl time" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);

        Log.d("service", date + time);

        if(endTime<startTime){
            switch (datePicker.getMonth()) {
                case 0:
                    calendar.set(datePicker.getYear()+1, Calendar.JANUARY, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 1:
                    calendar.set(datePicker.getYear()+1, Calendar.FEBRUARY, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 2:
                    calendar.set(datePicker.getYear()+1, Calendar.MARCH, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 3:
                    calendar.set(datePicker.getYear()+1, Calendar.APRIL, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 4:
                    calendar.set(datePicker.getYear()+1, Calendar.MAY, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 5:
                    calendar.set(datePicker.getYear()+1, Calendar.JUNE, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 6:
                    calendar.set(datePicker.getYear()+1, Calendar.JULY, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 7:
                    calendar.set(datePicker.getYear()+1, Calendar.AUGUST, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 8:
                    calendar.set(datePicker.getYear()+1, Calendar.SEPTEMBER, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 9:
                    calendar.set(datePicker.getYear()+1, Calendar.OCTOBER, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 10:
                    calendar.set(datePicker.getYear()+1, Calendar.NOVEMBER, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                case 11:
                    calendar.set(datePicker.getYear()+1, Calendar.DECEMBER, datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                    break;
                default:
                    break;
            }

            endTime = calendar.getTimeInMillis();
        }
        Log.d("service", "The service has begun!");
        prefs.edit().putLong(endTimeFile, endTime).apply();
        ctx.startService(serviceIntent);
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity.getApplicationContext();
        if (activity instanceof OnLockSelectedListener) {
            listener = (OnLockSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}