package com.hackthenorth.lockmeout.app;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.hackthenorth.lockmeout.app.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class HomeActivity extends FragmentActivity implements HomeFragment.OnButtonClickListener, LockPhoneFragment.OnLockSelectedListener{
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

    private LockPhoneFragment lockPhoneFragment;

    private LockAppFragment lockAppFragment;

    private HomeFragment homeFragment;

    private FragmentManager fragmentManager;

    private DevicePolicyManager devicePolicyManager;

    private ComponentName deviceAdmin;

    private boolean adminEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            adminEnabled = savedInstanceState.getBoolean(ADMINENABLED);
        }

        devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);

        fragmentManager = getSupportFragmentManager();
        lockPhoneFragment = LockPhoneFragment.newInstance("hi");
        lockAppFragment = LockAppFragment.newInstance("hello");
        homeFragment = HomeFragment.newInstance("hello");

        setContentView(R.layout.fragment_layout_container);

        deviceAdmin = new ComponentName(this, DeviceAdmin.class);

        //setContentView(R.layout.view_pager_home);

        fragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment).commit();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if (resultCode == RESULT_OK) {
                adminEnabled = true;
            } else{
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

    public void handleButtonClicked(int i){
        if(i == 1){
            fragmentManager.beginTransaction().replace(R.id.fragment_container, lockPhoneFragment).commit();
        } else{
            fragmentManager.beginTransaction().replace(R.id.fragment_container, lockAppFragment).commit();
        }
    }

    public void handleLock(int startMinute, int startHour){
        Toast.makeText(getApplicationContext(), "Start minute: " + startMinute + " Start hour: " + startHour,
                Toast.LENGTH_LONG).show();
        devicePolicyManager.resetPassword("0000", 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}