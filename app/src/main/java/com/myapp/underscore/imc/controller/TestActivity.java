package com.myapp.underscore.imc.controller;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myapp.underscore.imc.FormFragment;
import com.myapp.underscore.imc.R;

public class TestActivity extends AppCompatActivity implements FormFragment.onFormFragmentListener {

    public static FragmentManager fragmentManager;
    public FrameLayout frameLayout;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        frameLayout = findViewById(R.id.fram_one);
        textView = findViewById(R.id.txt_one);
        if(frameLayout != null){
            if(savedInstanceState != null){
                return;
            }

            fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fram_one,new FormFragment(),null);
            transaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(String value) {
        textView.setText(textView.getText().toString() + "\n"+value);
    }
}
