package com.hackthenorth.lockmeout.app.AlertDialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.hackthenorth.lockmeout.app.R;

/**
 * Created by Berries on 2014-09-20.
 */
public class EnterPasswordDialogue extends DialogFragment {

    OnLockTypeSelectedListener listener;
    String passcode = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View npView = inflater.inflate(R.layout.dialog_numberpicker, null);

        final NumberPicker numPick1 = (NumberPicker) npView.findViewById(R.id.numpick1);
        numPick1.setMaxValue(9);
        numPick1.setMinValue(0);
        numPick1.setWrapSelectorWheel(true);

        final NumberPicker numPick2 = (NumberPicker) npView.findViewById(R.id.numpick2);
        numPick2.setMaxValue(9);
        numPick2.setMinValue(0);
        numPick2.setWrapSelectorWheel(true);

        final NumberPicker numPick3 = (NumberPicker) npView.findViewById(R.id.numpick3);
        numPick3.setMaxValue(9);
        numPick3.setMinValue(0);
        numPick3.setWrapSelectorWheel(true);

        final NumberPicker numPick4 = (NumberPicker) npView.findViewById(R.id.numpick4);
        numPick4.setMaxValue(9);
        numPick4.setMinValue(0);
        numPick4.setWrapSelectorWheel(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(npView).setMessage(R.string.enter_password)
                .setPositiveButton(R.string.lock_me_out, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        passcode += numPick1.getValue();
                        passcode += numPick2.getValue();
                        passcode += numPick3.getValue();
                        passcode += numPick4.getValue();

                        listener.handleLock(passcode);
                    }
                })
                .setNegativeButton(R.string.wait, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.handleLock(passcode);
                    }
                });

        return builder.create();
    }

    public interface OnLockTypeSelectedListener{
        public void handleLock(String passcode);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnLockTypeSelectedListener) {
            listener = (OnLockTypeSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }
}
