package com.hackthenorth.lockmeout.app;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginFragment extends Fragment {

    private static Boolean hasAlreadyAccessed;
    final FragmentManager fragmentManager = getFragmentManager();
    private final String PASS_FILENAME = "lockmeoutpass";
    private final String EMAIL_FILENAME ="lockmeoutemail";
    private Context ctx;
    private String email;
    private EditText emailEdt;
    private EditText passEdt;
    private EditText passEdt2;
    private EditText passEdtMain;
    private OnButtonClickListener listener;

    public interface OnButtonClickListener{
        public void handleButtonClicked(int i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if( hasAlreadyAccessed ){

            View trueLoginView = inflater.inflate(R.layout.fragment_login_password_only, container, false);
            Button goBtn = (Button) trueLoginView.findViewById(R.id.btn_go);
            passEdtMain = (EditText) trueLoginView.findViewById(R.id.passwordInput);
            goBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( checkCredentials() ){
                        listener.handleButtonClicked(1000);
                    }else{
                        //show pass error
                    }
                }
            });

            return trueLoginView;
        } else {

            View firstLoginView = inflater.inflate(R.layout.fragment_login, container, false);
            emailEdt = (EditText) firstLoginView.findViewById(R.id.email_input);
            passEdt = (EditText) firstLoginView.findViewById(R.id.passwordInputNew);
            passEdt2 = (EditText) firstLoginView.findViewById(R.id.passwordInputNew2);
            Button loginBtn = (Button) firstLoginView.findViewById(R.id.btn_login);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if( checkPasswordSame() ) {
                        if( setCredentials() ) {
                            listener.handleButtonClicked(1000);
                        }else{
                            //
                        }
                    }else{
                        //show pass not same error
                    }
                }
            });
            return firstLoginView;
        }
    }

    private boolean checkPasswordSame() {

        String pass1 = passEdt.getText().toString();
        String pass2 = passEdt2.getText().toString();

        return pass1.equals(pass2);
    }

    private boolean setCredentials() {

        try{
            FileOutputStream outputStreamPass = ctx.openFileOutput(PASS_FILENAME, Context.MODE_PRIVATE);
            FileOutputStream outputStreamEmail = ctx.openFileOutput(EMAIL_FILENAME, Context.MODE_PRIVATE);
            String email = emailEdt.getText().toString();
            if( isEmailValid(email) ) {
                outputStreamEmail.write(email.getBytes());
                outputStreamPass.write(passEdt.getText().toString().getBytes());
                return true;
            }else{
                //show error
                return false;
            }

        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean checkCredentials() {

        try{
            FileInputStream inputStream = ctx.openFileInput(PASS_FILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String pass = bufferedReader.readLine();
            if(pass.equals(passEdtMain.getText().toString()))
                return true;
        } catch ( FileNotFoundException e ){
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity.getApplicationContext();
        if (activity instanceof OnButtonClickListener) {
            listener = (OnButtonClickListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implemenet MyListFragment.OnItemSelectedListener");
        }
    }

    public void onButtonPressed(Uri uri) {
    }


    public static LoginFragment newInstance(Boolean hasAccessed, String emailFileName) {
        LoginFragment fragment = new LoginFragment();
        hasAlreadyAccessed = hasAccessed;
        return fragment;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
