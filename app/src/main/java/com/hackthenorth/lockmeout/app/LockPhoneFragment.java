package com.hackthenorth.lockmeout.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by Berries on 2014-09-19.
 */
public class LockPhoneFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String STARTHOUR = "STARTHOUR";
    public static final String STARTMINUTE = "STARTMINUTE";
    public static final String ENDHOUR = "ENDHOUR";
    public static final String ENDMINUTE = "ENDMINUTE";

    private int startHour;
    private int startMinute;
    private TimePicker startTimePicker;
    //private TimePicker endTimePicker;
    private Button lockButton;
    private OnLockSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            startHour = savedInstanceState.getInt(STARTHOUR, 0);
            startMinute = savedInstanceState.getInt(STARTMINUTE, 0);
        }

        View view = (View) inflater.inflate(
                R.layout.fragment_lockphone, container, false);

        startTimePicker = (TimePicker) view.findViewById(R.id.start_date);
        //endTimePicker = (TimePicker) view.findViewById(R.id.end_date);
        lockButton = (Button) view.findViewById(R.id.lock_button);

        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMinute = startTimePicker.getCurrentMinute();
                startHour = startTimePicker.getCurrentHour();
                lockClicked(startMinute, startHour);
            }
        });

        return view;
    }


    public void lockClicked(int startMinute, int startHour){
        listener.handleLock(startMinute, startHour);
    }

    public interface OnLockSelectedListener{
        public void handleLock(int startMinute, int startHour);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnLockSelectedListener) {
            listener = (OnLockSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implemenet MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STARTHOUR, startHour);
        outState.putInt(STARTMINUTE, startMinute);
    }

    public static final LockPhoneFragment newInstance(String message){
        LockPhoneFragment f = new LockPhoneFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }
}