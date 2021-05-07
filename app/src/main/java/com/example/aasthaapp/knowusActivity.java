package com.example.aasthaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class knowusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowus);
        getSupportActionBar().hide();
    }
}