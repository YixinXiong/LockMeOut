package com.hackthenorth.lockmeout.app;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hackthenorth.lockmeout.app.LockPhone.EndTime;
import com.hackthenorth.lockmeout.app.LockPhone.LockPhoneFragment;
import com.hackthenorth.lockmeout.app.LockPhone.StartTime;
import com.hackthenorth.lockmeout.app.Timer.TimerService;
import com.hackthenorth.lockmeout.app.util.SystemUiHider;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class HomeActivity extends FragmentActivity implements HomeFragment.OnButtonClickListener, LoginFragment.OnButtonClickListener, LockPhoneFragment.OnSaveSelectedListener, EndTime.OnLockSelectedListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private static final String ADMINENABLED = "ADMINENABLED";

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    private StartTime startTimeFragment;
    private EndTime endTimeFragment;

    private LockAppFragment lockAppFragment;

    private HomeFragment homeFragment;

    private FragmentManager fragmentManager;

    private LoginFragment loginFragment;

    private DevicePolicyManager devicePolicyManager;

    private ComponentName deviceAdmin;

    private boolean adminEnabled;

    private int startMinute;
    private int startHour;
    private int startDay;
    private int startMonth;

    private int endMinute;
    private int endHour;
    private int endDay;
    private int endMonth;

    Intent serviceIntent;
    public String startTimeFile = "com.hackthenorth.lockmeout.startTime";
    public String endTimeFile = "com.hackthenorth.lockmeout.time.theEndTime";
    public String ENDTIME_FILENAME = "endtimefilename";

    private final String EMAIL_FILENAME = "lockmeoutemail";

    private boolean hasAlreadyAccessed() {


        FileInputStream inputStream;
        try {
            inputStream = openFileInput(EMAIL_FILENAME);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (savedInstanceState != null) {
            adminEnabled = savedInstanceState.getBoolean(ADMINENABLED);
        }

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        fragmentManager = getSupportFragmentManager();
        startTimeFragment = new StartTime();
        endTimeFragment = new EndTime();
        lockAppFragment = LockAppFragment.newInstance("hello");
        homeFragment = HomeFragment.newInstance("hello");
        loginFragment = LoginFragment.newInstance(hasAlreadyAccessed(), EMAIL_FILENAME);
        serviceIntent = new Intent(this, TimerService.class);

        setContentView(R.layout.fragment_layout_container);

        deviceAdmin = new ComponentName(this, DeviceAdmin.class);



        fragmentManager.beginTransaction().add(R.id.fragment_container, loginFragment).commit();

        if (!adminEnabled) {
            // Launch the activity to have the user enable our admin.
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdmin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "explanation");

            startActivityForResult(intent, 1);
        } else {
            devicePolicyManager.removeActiveAdmin(deviceAdmin);
            //enableDeviceCapabilitiesArea(false);
            adminEnabled = false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                adminEnabled = true;
            } else {
                adminEnabled = false;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current state
        savedInstanceState.putBoolean(ADMINENABLED, adminEnabled);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void handleButtonClicked(int i) {
        if (i == 1) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, startTimeFragment).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, lockAppFragment).commit();
        }
    }

    public void saveTime(int minute, int hour, int day, int month, String direction) {
        //Toast.makeText(getApplicationContext(), "Minute: " + minute + " Hour: " + hour + " Day: " + day + " Month: " + month,
        //        Toast.LENGTH_LONG).show();
        if (direction.equals("next")) {
            startMinute = minute;
            startHour = hour;
            startDay = day;
            startMonth = month;

            fragmentManager.beginTransaction().replace(R.id.fragment_container, endTimeFragment).commit();
        } else {
            endMinute = minute;
            endHour = hour;
            endDay = day;
            endMonth = month;

            if (direction.equals("back")) {
                fragmentManager.beginTransaction().replace(R.id.fragment_container, startTimeFragment).commit();
            }
        }
    }

    public void handleLock(DatePicker datePicker, TimePicker timePicker) {
        //devicePolicyManager.resetPassword("0000", 0);

        Log.d("service", "The service has begun!");

        startService(serviceIntent);
    public void promptLock(){
        Date startTime = new Date();
        EnterPasswordDialogue enterPasswordDialogue = new EnterPasswordDialogue();
        enterPasswordDialogue.show(fragmentManager, "dialog");
    }

    public void handleLock(String passcode){
        if(passcode.equals("")){
            //try to get rid of password altogether
        }else{

            Log.e("Passcode", passcode);
            devicePolicyManager.resetPassword(passcode, 0);
        }
    }

    @Override
    public void onBackPressed() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, loginFragment).commit();
    }
}