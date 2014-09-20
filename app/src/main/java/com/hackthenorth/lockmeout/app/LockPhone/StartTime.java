package com.hackthenorth.lockmeout.app.LockPhone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.hackthenorth.lockmeout.app.R;

import java.lang.reflect.Field;

/**
 * Created by Berries on 2014-09-19.
 */
public class StartTime extends LockPhoneFragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String HOUR = "HOUR";
    public static final String MINUTE = "MINUTE";
    public static final String DAY = "DAY";
    public static final String MONTH = "MONTH";

    private int hour;
    private int minute;
    private int day;
    private int month;

    private TimePicker timePicker;
    private DatePicker datePicker;
    private Button nextButton;

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
                saveClicked(timePicker.getCurrentMinute(), timePicker.getCurrentHour(), datePicker.getDayOfMonth(), datePicker.getMonth(), "next");
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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