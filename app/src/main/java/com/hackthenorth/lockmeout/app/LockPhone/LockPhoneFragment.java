package com.hackthenorth.lockmeout.app.LockPhone;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Created by Berries on 2014-09-19.
 */
public class LockPhoneFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String HOUR = "HOUR";
    public static final String MINUTE = "MINUTE";
    public static final String DAY = "DAY";
    public static final String MONTH = "MONTH";

    private int hour;
    private int minute;
    private int day;
    private int month;

    protected TimePicker timePicker;
    protected DatePicker datePicker;
    private Button lockButton;
    protected OnSaveSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            hour = savedInstanceState.getInt(HOUR, 0);
            minute = savedInstanceState.getInt(MINUTE, 0);
            day = savedInstanceState.getInt(DAY, 0);
            month = savedInstanceState.getInt(MONTH, 0);
        }

        return null;
    }


    public void saveClicked(int minute, int hour, int day, int month, String direction){
        listener.saveTime(minute, hour, day, month, direction);
    }

    public interface OnSaveSelectedListener {
        public void saveTime(int minute, int hour, int day, int month, String direction);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnSaveSelectedListener) {
            listener = (OnSaveSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implemenet MyListFragment.OnItemSelectedListener");
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