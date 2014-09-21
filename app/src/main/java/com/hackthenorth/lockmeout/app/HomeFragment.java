package com.hackthenorth.lockmeout.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Berries on 2014-09-19.
 */
public class HomeFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    final FragmentManager fragmentManager = getFragmentManager();
    private OnButtonClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_main, container, false);

        Button lockPhone = (Button) rootView.findViewById(R.id.lockphone);
//        Button lockApp = (Button) rootView.findViewById(R.id.lockapps);

        lockPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(1);
            }
        });
//
//        lockApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonClicked(2);
//            }
//        });

        return rootView;
    }

    public void buttonClicked(int i){
        listener.handleButtonClicked(i);
    }

    public interface OnButtonClickListener{
        public void handleButtonClicked(int i);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnButtonClickListener) {
            listener = (OnButtonClickListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }


    public static final HomeFragment newInstance(String message){
        HomeFragment f = new HomeFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }
}
