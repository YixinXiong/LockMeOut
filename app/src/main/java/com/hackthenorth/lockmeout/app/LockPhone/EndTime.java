package com.hackthenorth.lockmeout.app.LockPhone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.hackthenorth.lockmeout.app.R;

import java.lang.reflect.Field;

/**
 * Created by Berries on 2014-09-19.
 */
public class EndTime extends LockPhoneFragment {

    RelativeLayout layout;
    private OnLockSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = (View) inflater.inflate(
                R.layout.fragment_endtime, container, false);

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

                saveClicked(minute, hour, day, month, "");
                listener.promptLock();
            }
        });

        return view;
    }


    public interface OnLockSelectedListener{
        public void promptLock();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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