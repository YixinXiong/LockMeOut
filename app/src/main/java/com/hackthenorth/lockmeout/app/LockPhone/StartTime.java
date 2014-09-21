package com.hackthenorth.lockmeout.app.LockPhone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hackthenorth.lockmeout.app.R;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by Berries on 2014-09-19.
 */
public class StartTime extends LockPhoneFragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String HOUR = "HOUR";
    public static final String MINUTE = "MINUTE";
    public static final String DAY = "DAY";
    public static final String MONTH = "MONTH";
    public String startTimeFile = "com.hackthenorth.lockmeout.startTime";
    public String iFile = "com.hackthenorth.lockmeout.i";

    private int hour;
    private int minute;
    private int day;
    private int month;
    int i=0;
    int result=0;

    private TimePicker timePicker;
    private DatePicker datePicker;
    private Button nextButton;
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = (View) inflater.inflate(
                R.layout.fragment_starttime, container, false);

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

        nextButton = (Button) view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                java.util.Calendar calendar =   java.util.Calendar.getInstance();
                SharedPreferences prefs = ctx.getSharedPreferences(
                        "com.hackthenorth.lockmeout", Context.MODE_PRIVATE);

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
                long startTime = calendar.getTimeInMillis();
                String a = calendar.toString();
                Toast.makeText(ctx.getApplicationContext(), "bang", Toast.LENGTH_LONG).show();
                i++;
                Log.d("service", "The startTime From startTime.java: "+a);
                prefs.edit().putLong(startTimeFile, startTime).apply();
                prefs.edit().putInt(iFile, i).apply();
                result = prefs.getInt(iFile, 0);
                Log.d("service", "i "+i);
                //Toast.makeText(ctx.getApplicationContext(), result, Toast.LENGTH_LONG).show();

                saveClicked(timePicker.getCurrentMinute(), timePicker.getCurrentHour(), datePicker.getDayOfMonth(), datePicker.getMonth(), "next");

            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity.getApplicationContext();
        if (activity instanceof OnSaveSelectedListener) {
            listener = (OnSaveSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(HOUR, hour);
        outState.putInt(MINUTE, minute);
        outState.putInt(DAY, day);
        outState.putInt(MONTH, month);
    }
}