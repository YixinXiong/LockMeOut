package com.hackthenorth.lockmeout.app.LockPhone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

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
    private AlertDialog.Builder pickDateDialog;
    private AlertDialog.Builder pickTimeDialog;
    private View timeLayout;
    private View dateLayout;
    private Button timeBtnDialog;
    private Button dateBtnDialog;
    private Activity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = (View) inflater.inflate(
                R.layout.fragment_starttime, container, false);

        timeLayout = inflater.inflate(R.layout.time_dialog, null);
        dateLayout = inflater.inflate(R.layout.date_dialog, null);
        pickTimeDialog.setView(timeLayout);
        pickDateDialog.setView(dateLayout);
        datePicker = (DatePicker) dateLayout.findViewById(R.id.date_picker);
        timePicker = (TimePicker) timeLayout.findViewById(R.id.time_picker);

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

        Button setDateBtn = (Button) view.findViewById(R.id.btn_set_date_start);
        setDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickDateDialog.show();

            }
        });

        Button setTimeBtn = (Button) view.findViewById(R.id.btn_set_time_start);
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickTimeDialog.show();

            }
        });


        nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClicked(minute, hour, day, month, "next");
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = activity;
        if (activity instanceof OnSaveSelectedListener) {
            listener = (OnSaveSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }

        pickDateDialog = new AlertDialog.Builder(activity)
                .setTitle("Set Date")
                .setMessage("Select the date you want to start.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        day = datePicker.getDayOfMonth();
                        month = datePicker.getMonth();

                        Toast toast = Toast.makeText(mainActivity.getApplicationContext(), "Set Month: " + month + " Days: " + day, Toast.LENGTH_LONG);
                        toast.show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        pickTimeDialog = new AlertDialog.Builder(activity)
                .setTitle("Set Time")
                .setMessage("Select the time you want to start.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        minute = timePicker.getCurrentMinute();
                        hour = timePicker.getCurrentHour();

                        Toast toast = Toast.makeText(mainActivity.getApplicationContext(), "Set Hour: " + hour + " Minutes: " + minute, Toast.LENGTH_LONG);
                        toast.show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
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